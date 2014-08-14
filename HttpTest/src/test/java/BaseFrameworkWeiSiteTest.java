
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
public class BaseFrameworkWeiSiteTest {

    // private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static HttpConnection httpClient;
    private static CryptoManager cryptoManager;
    private static String key;
    private static String session;
    private static CookieStore cookie;

    public BaseFrameworkWeiSiteTest() {
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

//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);

        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //@Test

    public void testMemberRegisterService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"realName", "weixinNo", "qqNO", "email", "mobile", "birthday", "memberNO", "address", "integration
        map.put("token", "c2FsZXMtMQ==");
        map.put("realName", "nelsonyang");
        map.put("weixinNo", "nelsonyang");
        map.put("qqNO", "8648671868");
        map.put("email", "8648671868@qq.com");
        map.put("mobile", "18559871816");
        map.put("birthday", "1988-01-12");
        map.put("memberNO", "18559871816");
        map.put("address", "test");
        map.put("integration", "100");
        map.put("password", "123456");

        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);

        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=registerMember&sign=").concat(mdtjsonJson), parameters, cookie);

        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertMerchantService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"merchantName", "lag", "lat", "matchType", "keyword", "backgroupImageURL","merchantConfigId", "telphone", "address"
        map.put("keyword", "阿迪达斯");
        map.put("matchType", "1");
        map.put("merchantName", "阿迪达斯");
        map.put("lag", "10.0");
        map.put("lat", "10.0");
        map.put("merchantConfigId", "1");
        map.put("telphone", "18559871814");
        map.put("address", "苍山万达");
        map.put("backgroupImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMerchant"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertWeiSiteConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"weiSiteName", "backgroupMusicURL", "keyword", "matchType", "telphone", "isDialOpen", "title", "backgroupImageURL"
        map.put("weiSiteConfigId", "3");
        map.put("weiSiteName", "美甲2");
        map.put("backgroupMusicURL", "http://www.google.com.com");
        map.put("keyword", "美甲1");
        map.put("matchType", "1");
        map.put("telphone", "18559871814");
        map.put("isDialOpen", "1");
        map.put("messageTitle", "美甲");
        map.put("backgroupImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiSiteConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertMerchantConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"merchantName", "keyword", "matchType", "messageTitle", "messageImageURL"
        map.put("merchantConfigId", "8");
        map.put("merchantName", "美甲1");
        map.put("keyword", "美甲1");
        map.put("matchType", "1");
        map.put("messageTitle", "美甲1");
        map.put("messageImageURL", "http://www.google.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMerchantConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//
    //@Test

    public void testinsertWeiSiteStyleTemplateService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"reservationConfigId", "reservationAddress", "reservationTelephone", "reservationDesc"
        map.put("weiSiteStyleTemplateCfgId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiSiteStyleTemplate"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertWeiSiteSytleTemplateConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"weiSiteSytleTemplateName", "weiSiteSytleTemplateURL", "type"
        map.put("weiSiteSytleTemplateName", "微网站");
        map.put("weiSiteSytleTemplateURL", "http://www.baidu.com");
        map.put("type", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiSiteSytleTemplateConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testupdateMerchantService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"merchantName", "lag", "lat", "matchType", "keyword", "backgroupImageURL", "merchantConfigId", "telphone", "address"
        map.put("merchantId", "3");
        map.put("keyword", "达斯");
        map.put("matchType", "2");
        map.put("merchantName", "达斯");
        map.put("lag", "10.0");
        map.put("lat", "10.0");
        map.put("merchantConfigId", "1");
        map.put("telphone", "18559871815");
        map.put("address", "万达");
        map.put("backgroupImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMerchant"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //
    //@Test

    public void testupdateWeiSiteConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"reservationMemberName", "reservationMemberTelephone", "reservationMemberDateTime", "reservationMemberAddress", "reservationMemberDesc"
        map.put("weiSiteConfigId", "1");
        map.put("weiSiteName", "美容美甲");
        map.put("backgroupMusicURL", "http://www.google.com");
        map.put("keyword", "美容美甲");
        map.put("matchType", "2");
        map.put("telphone", "18559871816");
        map.put("isDialOpen", "0");
        map.put("title", "美容美甲");
        map.put("backgroupImageURL", "http://www.google.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateWeiSiteConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//

    //@Test
    public void testiinquirePageWeiSiteSytleTemplateConfigListservice() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageWeiSiteSytleTemplateConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageWeiSiteConfigListservice() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageWeiSiteConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    @Test
    public void testinquirePageMerchantListservice() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageMerchantList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiSiteConfigByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("weiSiteConfigId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteConfigById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireMerchantConfigListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMerchantConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiSiteConfigListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiSiteSytleTemplateConfigListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteSytleTemplateConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireCompanyInfoByTokenService() throws IOException {
        //"token", "weixinId", "eventId", "awardId"
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCompanyInfoByToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

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

   // @Test
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
    //@Test
    public void testinquireWeiSiteMenuListByToken() {
        //inquireWeiSiteMenuListByToken
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteMenuListByToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test
    public void testinquireWeiSiteMenuListByIdToken() {
        //inquireWeiSiteMenuListByToken
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weiSiteMenuId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiSiteMenuByIdAndToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    
    //@Test
     public void testinquireMerchanntByIdToken() {
        //inquireWeiSiteMenuListByToken
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("merchantId", "6");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMerchantByToken"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }


       //@Test
     public void testinsertWeixinMessageByToken() {
        //inquireWeiSiteMenuListByToken
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "c2FsZXMtMQ==");
        map.put("weixinId", "nelsonyang");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeixinMessageByToken"), parameters, cookie);
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