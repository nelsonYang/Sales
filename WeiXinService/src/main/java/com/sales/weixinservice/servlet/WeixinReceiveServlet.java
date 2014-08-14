package com.sales.weixinservice.servlet;

import com.framework.context.ApplicationContext;
import com.framework.crypto.CryptoManager;
import com.framework.crypto.ICrypto;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.HttpConnectionEnum;
import com.frameworkLog.factory.LogFactory;
import com.sales.datacache.MessageBlockQueueCache;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import com.sales.weixin.api.WeixinAPI;
import com.sales.weixin.api.WeixinReceiveAPI;
import com.sales.weixin.api.WeixinResponseAPI;
import com.sales.weixin.api.impl.WeixinAPIImpl;
import com.sales.weixin.api.impl.WeixinReceiveAPIImpl;
import com.sales.weixin.api.impl.WeixinResponseAPIImpl;
import com.sales.weixin.context.InterfaceContext;
import com.sales.weixin.context.builder.InterfaceContextBuilder;
import com.sales.weixin.utils.MessageUtils;
import com.sales.weixinservice.message.handler.MessageHandler;
import com.sales.weixinservice.message.handler.MessageManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;

/**
 *
 *
 * @author nelson
 */
@WebServlet(name = "weiXinServlet", urlPatterns = {"/weiXinService"}, loadOnStartup = 1)
public class WeixinReceiveServlet extends HttpServlet {

    private final Logger logger = LogFactory.getInstance().getLogger(WeixinReceiveServlet.class);
    private final WeixinResponseAPI weixinResponseAPI = new WeixinResponseAPIImpl();
    private final WeixinReceiveAPI weixinReceiveAPI = new WeixinReceiveAPIImpl();
    private final MessageManager messageManager = new MessageManager();
    private final String TOKEN = "yjh123456";
    private static WeixinAPI weixinApi;

    public static WeixinAPI getWeiXinAPI() {
        return weixinApi;
    }

    @Override
    public void init() {
        //解析威信api
        System.out.println("开始解析微信api");
        InterfaceContextBuilder interfaceContexBuilder = new InterfaceContextBuilder();
        InterfaceContext interfaceContext = interfaceContexBuilder.build();
        weixinApi = new WeixinAPIImpl(ApplicationContext.CTX.getHttpConnectionFactory().getHttpClient(HttpConnectionEnum.MULTION), interfaceContext);
        System.out.println("解析微信api");
        ResponseMessageCache responseMessageCache = ResponseMessageCache.getInstance();
        responseMessageCache.init();
        ApplicationContext applicationContext = ApplicationContext.CTX;
        EntityDao<ResponseMessageCfg> entityDao = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        List<ResponseMessageCfg> responseMessageCfgList = entityDao.inquireByCondition(new ArrayList<Condition>(0));
        for (ResponseMessageCfg responseMessageCfg : responseMessageCfgList) {
            responseMessageCache.putResponseMessageCfgWithKeyWord(responseMessageCfg);
        }
        responseMessageCache.convertToXml();
        System.out.println("初始化消息队列");
        MessageBlockQueueCache messageBlockQueueCache = MessageBlockQueueCache.getInstance();
        messageBlockQueueCache.takeMessage();
        System.out.println("初始化消息队列完成");

    }

    /**
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");//
        List<String> parameterList = new ArrayList<String>(3) {
            private static final long serialVersionUID = 2621444383666420433L;

            @Override
            public String toString() {
                return this.get(0) + this.get(1) + this.get(2);
            }
        };
        parameterList.add(TOKEN);
        parameterList.add(timestamp);
        parameterList.add(nonce);
        Collections.sort(parameterList);// 排序
        ICrypto shaCrypto = CryptoManager.getInstance().getCrypto(CryptEnum.SHA);
        String tmpStr = shaCrypto.encrypt(parameterList.toString(), null);
        Writer out = response.getWriter();
        logger.debug("signature={}", signature);
        logger.debug("tmpStr={}", tmpStr);
        logger.debug("echostr={}", echostr);
        if (signature.equals(tmpStr)) {
            out.write(echostr);// 请求验证成功，返回随机码
        } else {
            out.write("");
        }
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        logger.debug("start:{}", System.currentTimeMillis());
        response.setContentType("text/xml");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        String token = request.getParameter("token");
//        logger.debug("token:{}", token);
        InputStream bf = request.getInputStream();
//        logger.debug("contentLength:{}", request.getContentLength());
//        logger.debug("contentType:{}", request.getContentType());
//        logger.debug("queryString:{}", request.getQueryString());
//        logger.debug("parameters:{}", request.getParameterNames());
        byte receiveByte[] = new byte[request.getContentLength()];
        bf.read(receiveByte);
        bf.close();
        //将收到消息内容打印出来
        String receiveMsg = new String(receiveByte, "utf-8");
//        logger.debug("receiveMsg:{}", receiveMsg);
//        System.out.println("receiveMsg=" + receiveMsg);
        Map<String, String> resultMap = MessageUtils.handleReceivedMessage(receiveMsg);
        resultMap.put("token", token);
//        String fromUser = resultMap.get("FromUserName");
//        String toUser = resultMap.get("ToUserName");
        String msgType = resultMap.get("MsgType");
//        String content = resultMap.get("Content");
//        logger.debug("fromUser:{}", fromUser);
//        logger.debug("toUser:{}", toUser);
//        logger.debug("msgType:{}", msgType);
//        logger.debug("content:{}", content);
        //判断消息内容选择回复信息
        if ("event".equals(msgType)) {
            msgType = resultMap.get("Event");
        }
//        logger.debug("end:{}", System.currentTimeMillis());
////        logger.debug("msgType:{}", msgType);
        MessageHandler messageHandler = messageManager.getMessageHandler(msgType);
//        logger.debug("end:{}", System.currentTimeMillis());
        String responseXML = messageHandler.handleMessage(resultMap, weixinReceiveAPI, weixinResponseAPI);
        logger.debug("responseXML:{}", responseXML);
        pw.print(responseXML);
        pw.flush();
        pw.close();
    }

    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();
        // 读取输入流  
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素  
        Element root = document.getRootElement();
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();

        // 遍历所有子节点  
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        // 释放资源  
        inputStream.close();
        inputStream = null;

        return map;
    }
}
