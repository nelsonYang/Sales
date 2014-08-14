
import com.framework.crypto.CryptoManager;
import com.framework.crypto.ICrypto;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.HttpConnectionEnum;
import com.framework.httpclient.HttpConnection;
import com.framework.httpclient.HttpConnectionFactory;
import com.framework.httpclient.HttpResponseEntity;
import com.framework.utils.JsonUtils;
import java.io.IOException;
import java.util.HashMap;
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
public class BaseFrameworkTest {

    // private static final String url = "http://localhost:8080/Sales/SalesService?";
    //    private static final String url = "http://115.28.18.123/Sales/SalesService?";
    private static final String url = "http://localhost:8080/AppTreasure/AppTreasureService?";
   // private static final String url = "http://115.28.18.123/AppTreasure/AppTreasureService?";
    private static HttpConnection httpClient;
    private static CryptoManager cryptoManager;
    private static String key;
    private static String session;
    private static CookieStore cookie;

    public BaseFrameworkTest() {
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
        map.put("userName", "780562434");
        map.put("password", "123456");

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
        session = JsonUtils.parseJsonToMap(jsonStr).get("session");
        System.out.println("session=" + session);
        key = JsonUtils.parseJsonToMap(jsonStr).get("key");
        cookie = response.getCookie();
        System.out.println("content=" + content);

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
    public void testRegisterService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", "123456");

        String json = JsonUtils.mapToJson(map);
        //        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        //        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=register&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testLoginService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", "780562434");
        map.put("password", "123456");

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
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInquireCompanyInfoService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("userName", "aaa");
//        map.put("password", "123456");
//
//        String json = JsonUtils.mapToJson(map);
        //        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        //        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, "{}", "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCompanyInfo"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testUpdateCompanyInfoService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", "qq@qq.com");
        map.put("qqNO", "8648671868");
//
        String json = JsonUtils.mapToJson(map);
        //        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        //        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateCompanyInfo"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    // @Test
    public void testUpdatePasswordService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
        Map<String, String> map = new HashMap<String, String>();
        map.put("oldPassword", "123456");
        map.put("password", "123456a");
//
        String json = JsonUtils.mapToJson(map);
        //        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        //        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updatePassword"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    private String buildNoCryptJson(String session, String dataJson, String ecryptType) {
        StringBuilder request = new StringBuilder();
        request.append("{\"session\":\"").append(session).append("\",\"encryptType\":\"").append(ecryptType).append("\",\"data\":").append(dataJson).append("}");
        System.out.println(request.toString());
        return request.toString();
    }

    //@Test
    public void testInsertFeebbackService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("content", "xxxxxx");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertFeedback"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    // @Test
    public void testInsertGoldService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("channel", "3");
        map.put("rewardAmount", "400");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertGold"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInsertEventQualificationService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("productId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertEventQualification"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //@Test

    public void testInquireConsumeChannelByMoneyService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("money", "100");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireConsumeChannelByMoney"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInquireConsumeChannelByProductService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("productId", "1");
        map.put("num", "1");
        map.put("eventQualificationId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireConsumeChannelByProduct"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //
    //@Test
    public void testinquireEventQualificationService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("pageCount", "10");
        map.put("pageNo", "1");
        map.put("type", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventQualification"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //

    //@Test
    public void testinquireConsumeIntegrationService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("channel", "3");
        map.put("amount", "100");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=consumeIntegration"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireEventQualificationByIdService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("eventQualificationId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventQualificationById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInsertDrawMoneyFlowService() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("amount", "100");
        map.put("payway", "1");
        map.put("payAccount", "864867186@qq.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertDrawMoneyFlow"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //
  //  @Test
    public void testUpdateEventQualificationStatusService() throws IOException {
        //"eventQualificationId", "num",
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventQualificationId", "1");
        map.put("num", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateEventQualificationStatus"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    
    //
    
     //@Test
    public void testinquireDrawMoneyFlowService() throws IOException {
        //"pageCount", "pageNo",
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageCount", "10");
        map.put("pageNo", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireDrawMoneyFlow"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    
    //@Test
    public void testinquireVersionService() throws IOException {
        //"pageCount", "pageNo",
        Map<String, String> map = new HashMap<String, String>();
     
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session,"{}", "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireVersion"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    
    @Test
    public void testinquireProductListService() throws IOException {
        //"pageCount", "pageNo",
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageCount", "10");
        map.put("pageNo", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireProductList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
     
     @Test
    public void testinquireProductByIdService() throws IOException {
        //"pageCount", "pageNo",
        Map<String, String> map = new HashMap<String, String>();
        map.put("productId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);

        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireProudctById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //eventQualificationId
    //   @Test
    public void testCreateMenuService() throws IOException {
        //"userName", "password", "province", "city", "region",/* "street",*/ "linkMobile", "email"
        //"currentIndex", "pageCount", "memberDegree",
        //"appId", "appSecret"
        Map<String, String> map = new HashMap<String, String>();
        // "menuName", "menuParent", "menuType", "menuURL", "menuKey"
        map.put("menuName", "设置");
        map.put("menuParent", "1");
        map.put("menuType", "1");
        map.put("menuURL", "http://www.baidu.com");
        map.put("menuKey", "xx-uu-dd-jj");
//
        String json = JsonUtils.mapToJson(map);
        //        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        //        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=createMenu"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //@Test

    public void testInquireMenuByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("menuId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMenuById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testUpdateMenuService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("menuId", "1");
        map.put("menuName", "首页");
        map.put("menuURL", "http://www.google.com");
        map.put("menuKey", "zzzzzzz");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMenu"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //
    //@Test

    public void testInquireMenuListService() throws IOException {
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, "{}", "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMenuList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testDeleteMenuService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("menuId", "3");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteMenu"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //

    //@Test
    public void testInsertEventService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventName", "瓜瓜卡活动");
        map.put("eventDesc", "瓜瓜卡活动");
        map.put("eventStartURL", "http://www.baidu.com");
        map.put("eventEndImageURL", "http://www.baidu.com");
        map.put("eventEffectiveStartTime", "2013-12-20 00:00:00");
        map.put("eventEffectiveEndTime", "2014-01-10 00:00:00");
        map.put("type", "2");
        map.put("keyword", "瓜瓜卡");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertEvent"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testPauseEventService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventId", "3");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=pauseEvent"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testDeleteEventService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventId", "3");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteEvent"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testStartEventService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventId", "3");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=startEvent"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireEventByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireEventListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireEventList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testUpdateEventService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("eventId", "1");
        map.put("eventName", "大转盘活动");
        map.put("keyword", "抽奖");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateEvent"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    // "awardName", "eventId", "awardNum", "awardNumPerPerson"

    // @Test
    public void testInsertAwardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("awardName", "三等奖");
        map.put("eventId", "1");
        map.put("awardNum", "10");
        map.put("awardNumPerPerson", "3");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertAward"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testUpdateAwardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("awardId", "3");
        map.put("awardNum", "9");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateAward"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireAwardByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("awardId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireAwardById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireWardListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireAwardList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testDeleteAwardByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("awardId", "3");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteAward"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInsertMemberCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("memberCardName", "VIP1");
        map.put("memberLogoURL", "http://www.google.com.hk");
        map.put("memberCardURL", "http://www.google.com.hk");
        map.put("memberCardDesc", "xxxxx");
        map.put("effectiveStartTime", "2013-11-10 00:00:00");
        map.put("effectiveEndTime", "2014-11-10 00:00:00");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMemberCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testUpdateMemberCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("memberCardId", "1");
        map.put("memberCardName", "VIP1");
//        map.put("clickCount", "0");
//        map.put("readCount", "0");
//        map.put("consumeMoney", "0");
//        map.put("balance", "0");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMemberCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInqurieMemberCardByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("memberCardId", "1");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testDeleteMemberCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("memberCardId", "2");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteMemberCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireMemberCardListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInsertPersonCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //personName", "personMobile", "personEmail", "qqNO", "weiboNo"},
        // minorParameters = {"personIconURL", "companyName", "companyAddress"},
        map.put("personName", "nelson");
        map.put("personMobile", "1855987184");
        map.put("personEmail", "864867186@qq.com");
        map.put("qqNO", "864867186");
        map.put("weiboNo", "nelsonyang");
        map.put("companyName", "nelson");
        map.put("personIconURL", "http://www.google.com.hk");
        map.put("companyAddress", "仓山万达");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertPersonCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //updatePersonCard
    //@Test

    public void testUpdatePersonCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //personName", "personMobile", "personEmail", "qqNO", "weiboNo"},
        // minorParameters = {"personIconURL", "companyName", "companyAddress"},
        map.put("personCardId", "1");
        map.put("personMobile", "1855987184");
        map.put("personEmail", "864867187@qq.com");
        map.put("qqNO", "864867187");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updatePersonCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //  @Test
    public void testInquriePersonCardByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("personCardId", "1");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePersonCardById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test

    public void testDeletePersonCardByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("personCardId", "1");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deletePersonCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInsertResponseMessageService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        map.put("responseContent", "<xml><fromUser>nelson</fromUser></xml>");
        map.put("keyword", "瓜瓜卡");
        map.put("type", "1");
        map.put("responseImageURL", "http://www.google.com.hk");
        map.put("responseAudio", "http://www.google.com.hk");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertResponseMessage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testUpdateResponseMessageService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        map.put("responseMessageCfgId", "1");
        map.put("keyword", "奖品");
        map.put("type", "1");
        map.put("responseImageURL", "http://www.google.com.hk");
        map.put("responseAudio", "http://www.google.com.hk");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateResponseMessage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireResponseMessageListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("type", "1");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireResponseMessageList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testinquireResponseMessageByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("responseMessageCfgId", "1");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireResponseMessageById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testDeleteResponseMessageByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("responseMessageCfgId", "2");
        //consumeMoney
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteResponseMessage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test

    public void testCreateWeiSiteMenuService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("weiSiteMenuName", "新闻");
        map.put("weiSiteMenuParentId", "1");
        map.put("weiSiteMenuImageURL", "http://www.google.com.hk");
        map.put("weiSiteMenuLinkWebSite", "http://www.google.com.hk");
        map.put("weiSiteMenuDesc", "菜单描述");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=createWeiSiteMenu"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testUpdateWeiSiteMenuService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("weiSiteMenuId", "1");
        map.put("weiSiteMenuName", "首页");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateWeiSiteMenu"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testInquireWeiSiteMenuByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("weiSiteMenuId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteMenuById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //
    // @Test

    public void testinquireWeiSiteMenuListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, "{}", "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteMenuList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    /**
     * importantParameters = {"session", "encryptType", "weiSiteTitle",
     * "weiSiteContent", "bindId"}, minorParameters = {"weiSiteImageURL",
     * "weiSitelinkWebSite"},
     *
     */
    //@Test
    public void testInsetWeiSiteArticleService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        map.put("weiSiteTitle", "新闻1");
        map.put("weiSiteContent", "新闻1");
        map.put("bindId", "1");
        map.put("weiSiteImageURL", "http://www.google.com.hk");
        map.put("weiSitelinkWebSite", "http://www.google.com.hk");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiSiteArticle"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInsetWeiSiteImageService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteImageName", "新闻1");
        map.put("weiSiteImageDesc", "xxxxxx");
        map.put("bindId", "1");
        map.put("weiSiteImageURL", "http://www.google.com.hk");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiSiteImage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    // @Test

    public void testInsetWeiSiteTemplateService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteTemplateCfgId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiSiteTemplate"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testUpdateWeiSiteArticleService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteArticleId", "1");
        //"weiSiteImageURL", "weiSitelinkWebSite", "weiSiteTitle", "weiSiteContent", "bindId"
        map.put("weiSiteImageURL", "http://v89.com");
        map.put("weiSiteTitle", "title");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateWeiSiteArticle"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testUpdateWeiSiteImageService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteImageId", "1");
        //"weiSiteImageURL", "weiSitelinkWebSite", "weiSiteTitle", "weiSiteContent", "bindId"
        map.put("weiSiteImageURL", "http://v89.com");
        map.put("weiSiteImageType", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateWeiSiteImage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testinquireWeiSiteArticleListService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteArticleList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiSiteArticleByIdService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteArticleId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteArticleById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testinquireWeiSiteImageListService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteImageList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //     @Test

    public void testinquireWeiSiteMenuByIdService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteMenuId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteMenuById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testDeleteWeiSiteArticleByIdService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteArticleId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteWeiSiteArticle"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testDeleteWeiSiteImageByIdService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteImageId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteWeiSiteImage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testDeleteWeiSiteMenuByIdService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteMenuId", "2");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteWeiSiteMenu"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //    @Test

    public void testDeleteWeiSiteTemplateByIdService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("weiSiteTemplateId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=deleteWeiSiteTemplate"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //??@Test
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
//

    
    //
    //@Test

    public void testinquireAwardCurrentNumAndDrawCountByTokenService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("eventId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireAwardCurrentNumAndDrawCountByToken"), parameters, cookie);
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
    public void testinquireWeiSiteArticleByTokenService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("weiSiteArticleId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteArticleByToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiSiteArticleListByTokenService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteArticleListByToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //
    // @Test

    public void testtakeMemberCardByTokenService() throws IOException {
        //"realName", "weixinNo", "qqNo", "mobile", "email", "birthday", "memeberCardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        map.put("realName", "nelson");
        map.put("weixinNo", "864867186");
        map.put("qqNo", "864867186");
        map.put("mobile", "18559871814");
        map.put("email", "864867186@qq.com");
        map.put("birthday", "1988-11-15");
        map.put("memberCardId", "1");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=takeMemberCardByToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireAwardResultListService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireAwardResultList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //settleAwardResult
    // @Test

    public void testsettleAwardResultService() throws IOException {
        //"memberCardName", "memberLogoURL", "memberCardURL" "memberCardDesc", "effectiveStartTime", "effectiveEndTime"
        Map<String, String> map = new HashMap<String, String>();
        map.put("awardResultId", "5");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=settleAwardResult"), parameters, cookie);
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
}