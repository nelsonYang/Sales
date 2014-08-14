
package com.sales.weixinservice.message.handler;

import com.sales.weixin.api.WeixinReceiveAPI;
import com.sales.weixin.api.WeixinResponseAPI;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface MessageHandler {
    public String handleMessage(Map<String,String> messageMap,WeixinReceiveAPI request,WeixinResponseAPI response);
}
