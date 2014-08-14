
import com.framework.crypto.CryptoManager;
import com.framework.crypto.ICrypto;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.HttpConnectionEnum;
import com.framework.httpclient.HttpConnection;
import com.framework.httpclient.HttpConnectionFactory;
import com.framework.httpclient.HttpResponseEntity;
import com.framework.utils.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.CookieStore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Administrator
 */
public class BaseFrameworkEventTest {

    private static final String url = "http://115.29.137.120/Sales/SalesService?";
   // private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static HttpConnection httpClient;
    private static CryptoManager cryptoManager;
    private static String key;
    private static String session;
    private static CookieStore cookie;

    public BaseFrameworkEventTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        // new ApplicationContextBuilder().baseBuild();
        httpClient = HttpConnectionFactory.getInstance().getHttpClient(HttpConnectionEnum.THREAD_SAFE_SINGTON);
        cryptoManager = CryptoManager.getInstance();
        loginService();

    }

    public static void loginService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", "dafenshou");
        map.put("password", "1234567");

        String json = JsonUtils.mapToJson(map);
        //        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        //        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=login&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        Map<String, String> dataMap = JsonUtils.parseJsonToMap(content);
        String jsonStr = dataMap.get("data");
        Map<String, String> sessionMap = JsonUtils.parseJsonToMap(jsonStr);
        session = sessionMap.get("session");
        System.out.println("session=" + session);
        cookie = response.getCookie();
        System.out.println("content=" + content);
        key = sessionMap.get("key");
        System.out.println("key=" + key);

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //@Test
    public void testMemberLoginService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"userName", "password"
        map.put("userName", "18559871816");
        map.put("password", "123456");
        map.put("token", "c2FsZXMtMQ==");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=loginMember&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    @Test
    public void testinsertAwardService() throws IOException {
        List<Map<String,String>> awardsList = new ArrayList<Map<String,String>>(2);
        Map<String, String> map = new HashMap<String, String>();
        // "awardName", "eventId", "awardNum", "awardNumPerPerson" awardDesc
        map.put("awardName", "一等奖打转盘");
        map.put("eventId", "1");
        map.put("awardNum", "1");
        map.put("awardNumPerPerson", "1");
        map.put("awardDesc", "打转盘");
        awardsList.add(map);
        map = new HashMap<String, String>();
        // "awardName", "eventId", "awardNum", "awardNumPerPerson" awardDesc
        map.put("awardName", "二等奖打转盘");
        map.put("eventId", "1");
        map.put("awardNum", "1");
        map.put("awardNumPerPerson", "1");
        map.put("awardDesc", "打转盘");
        awardsList.add(map);
        String json = JsonUtils.mapListToJsonArray(awardsList);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertAward"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    
    //@Test
    public void testinquireAwrdsListByEventIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        map.put("eventId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireAwardsListByEventId"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinsertEventService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "eventName", "eventDesc", "eventEffectiveStartTime", "eventEffectiveEndTime", "type", "keyword", "eventConfigId"
        //"eventStartURL", "eventEndImageURL"
        map.put("eventName", "大转盘");
        map.put("eventEffectiveStartTime", "2014-01-01 00:00:00");
        map.put("eventEffectiveEndTime", "2014-02-01 00:00:00");
        map.put("type", "1");
        map.put("keyword", "抽奖");
        map.put("eventDesc", "打转盘");
        map.put("eventConfigId", "4");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertEvent"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinsertEventConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        //map.put("eventConfigId", "4");
        map.put("keyword", "抽奖");
        map.put("isDialOpen", "1");
        map.put("matchType", "1");
        map.put("messageTitle", "大转盘");
        map.put("messageImageURL", "http://www.choujiang.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertEventConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testupdateEventConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        map.put("eventConfigId", "1");
        map.put("keyword", "aaa");
        map.put("isDialOpen", "1");
        map.put("matchType", "2");
        map.put("messageTitle", "aaa");
        map.put("messageImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateEventConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireEventConfigByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        map.put("eventConfigId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventConfigById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

   // @Test
    public void testinquireEventConfigListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        map.put("type", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    
     //@Test
    public void testinquireEventByTokenAndTypeService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("eventId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventByTokenAndType"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    
    //@Test
    public void testinquireEventListByTokenAndTypeService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("type", "1");
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventListByTokenAndType"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test
     public void testDrawLottoryService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("eventId", "1");
        map.put("awardId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=drawLottoryByTokenAndType"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }


    private String buildCryptJson(String session, String dataJson, String ecryptType) {
        StringBuilder request = new StringBuilder();
        request.append("{\"session\":\"").append(session).append("\",\"encryptType\":\"").append(ecryptType).append("\",\"data\":\"").append(dataJson).append("\"}");
        System.out.println(request.toString());
        return request.toString();
    }

    private static String buildNoSessionAndCryptJson(String dataJson, String ecryptType) {
        StringBuilder request = new StringBuilder();
        request.append("{\"encryptType\":\"").append(ecryptType).append("\",\"data\":").append(dataJson).append("}");
        System.out.println(request.toString());
        return request.toString();
    }

    private String buildNoCryptJson(String session, String dataJson, String ecryptType) {
        StringBuilder request = new StringBuilder();
        request.append("{\"session\":\"").append(session).append("\",\"encryptType\":\"").append(ecryptType).append("\",\"data\":").append(dataJson).append("}");
        System.out.println(request.toString());
        return request.toString();
    }
}