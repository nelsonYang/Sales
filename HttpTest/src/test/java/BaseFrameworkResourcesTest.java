
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
public class BaseFrameworkResourcesTest {

    // private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static HttpConnection httpClient;
    private static CryptoManager cryptoManager;
    private static String key;
    private static String session;
    private static CookieStore cookie;

    public BaseFrameworkResourcesTest() {
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
        map.put("userName", "dafenshou");
        map.put("password", "1234567");
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

    //@Test
    public void testinsertResourcesService() throws IOException {
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        //"resourcesType", "resourcesName", "resourcesURL", "resourceContent"
        map.put("resourcesType", "1");
        map.put("resourcesName", "图片");
        map.put("resourcesURL", "http://www.baidu.com");
        map.put("imageURL", "http://www.baidu.com");
        map.put("resourceContent", "大转盘");
        resultMapList.add(map);
        map = new HashMap<String, String>();
        //"resourcesType", "resourcesName", "resourcesURL", "resourceContent"
        map.put("resourcesType", "1");
        map.put("resourcesName", "图片1");
        map.put("resourcesURL", "http://www.google.com");
        map.put("imageURL", "http://www.baidu.com");
        map.put("resourceContent", "1大转盘");
        resultMapList.add(map);
        String json = JsonUtils.mapListToJsonArray(resultMapList);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertResources"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinsertResourcesImagervice() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"resoucesId", "resoucesImageURL", "resoucesContent", "imageType"
        map.put("resoucesId", "1");
        map.put("resoucesImageURL", "http://www.baidu.com");
        map.put("resoucesContent", "打转盘");
        map.put("imageType", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertResourcesImage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinsertResponseMessageCfgService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "responseContent", "responseImageURL", "responseAudio", "type", "relatedEventId", "keyword", "relatedURL", "isClose", "responseContentType", "relatedId"
        map.put("responseMessageCfgId", "32");
        map.put("responseContent", "优惠券");
        map.put("relatedEventId", "1");
        map.put("type", "12");
        map.put("responseAudio", "");
        map.put("responseImageURL", "http://www.google.com");
        map.put("keyword", "优惠券");
        map.put("relatedURL", "http://www.baidu.com");
        map.put("isClose", "1");
        map.put("responseContentType", "1");
        map.put("relatedId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertResponseMessageCfg"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinsertResponseToolConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "responseKeyword", "responseToolName", "type", "isClose"
        map.put("responseKeyword", "天气");
        map.put("responseToolName", "天气");
        map.put("type", "1");
        map.put("isClose", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertResponseToolConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testupdateResourcesImageService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "resoucesId", "resoucesImageURL", "resourcesContent", "imageType"
        map.put("resourcesImageId", "1");
        map.put("resoucesId", "1");
        map.put("resoucesImageURL", "http://www.google.com");
        map.put("resourcesContent", "xxxxx");
        map.put("imageType", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateResourcesImage"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    @Test
    public void testupdateResourcesService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "resourcesType", "resourcesName", "resourcesURL", "resourceContent"
        List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
        map.put("resourcesId", "1");
        map.put("resourcesType", "2");
        map.put("resourcesURL", "http://www.google.com");
        map.put("imageURL", "http://www.google.com");
        map.put("resourcesName", "链接");
        map.put("resourceContent", "链接");
        resultMapList.add(map);
        map.put("resourcesId", "1");
        map.put("resourcesType", "3");
        map.put("resourcesURL", "http://www.baidu.com");
        map.put("imageURL", "http://www.baidu.com");
        map.put("resourcesName", "链接");
        map.put("resourceContent", "链接");
        resultMapList.add(map);
        String json = JsonUtils.mapListToJsonArray(resultMapList);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateResources"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testupdateResponseMessageCfgService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"responseContent", "responseImageURL", "responseAudio", "type", "relatedEventId", "keyword", "relatedURL", "isClose", "responseContentType", "relatedId"
        map.put("responseMessageCfgId", "1");
        map.put("responseContent", "啊啊啊啊");
        map.put("keyword", "天气");
        map.put("relatedEventId", "2");
        map.put("type", "2");
        map.put("isClose", "2");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateResponseMessageCfg"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testupdateResponseToolConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"responseKeyword", "responseToolName", "type", "isClose"
        map.put("responseToolsConfigId", "1");
        map.put("responseKeyword", "天气");
        map.put("responseToolName", "天气");
        map.put("type", "1");
        map.put("isClose", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateResponseToolConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageResourcesListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        map.put("pageNo", "1");
        map.put("pageCount", "10");
        map.put("imageType", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageResourcesList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireResourcesByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen"
        map.put("resourcesId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireResourcesById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireResourcesMenuListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, "{}", "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireResourcesMenuList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireResponseMessageCfgListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireResponseMessageCfgList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireResponseToolConfigListService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, "{}", "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireResponseToolConfigList"), parameters, cookie);
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