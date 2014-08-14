
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
public class BaseFrameworkweiapplicationTest {

     private static final String url = "http://115.29.137.120/Sales/SalesService?";
    //private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static HttpConnection httpClient;
    private static CryptoManager cryptoManager;
    private static String key;
    private static String session;
    private static CookieStore cookie;

    public BaseFrameworkweiapplicationTest() {
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
    public void testinsertReservationConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"keyword", "matchType", "messageTitle", "messageImageURL"
        map.put("reservationConfigId", "2");
        map.put("keyword", "预约");
        map.put("matchType", "1");
        map.put("messageTitle", "在线订购");
        map.put("messageImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertReservationConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertReservationMemberByTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>(2, 1);
        //"reservationMemberName", "reservationMemberTelephone", "reservationMemberDateTime", "reservationMemberAddress", "reservationMemberDesc"
        map.put("token", "c2FsZXMtMQ==");
        map.put("reservationMemberName", "nelson");
        map.put("reservationMemberTelephone", "18559871816");
        map.put("reservationMemberDateTime", "2013-01-14 00:00:00");
        map.put("reservationMemberAddress", "苍山万达");
        map.put("reservationMemberDesc", "在线订购");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertReservationMemberByToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertReservationMemberService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberId", "companyId", "couponConfigId"
        map.put("reservationMemberName", "nelson");
        map.put("reservationMemberTelephone", "18559871816");
        map.put("reservationMemberDateTime", "2013-01-14 00:00:00");
        map.put("reservationMemberAddress", "苍山万达");
        map.put("reservationMemberDesc", "在线订购");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertReservationMember"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//
     //@Test

    public void testinsertReservationService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"reservationConfigId", "reservationAddress", "reservationTelephone", "reservationDesc"
        map.put("reservationConfigId", "1");
        map.put("messageTitle", "预约");
           map.put("keyword", "预约");
        map.put("messageImageURL", "http://www.baidu.com");
        map.put("reservationTelephone", "18559871814");
        map.put("reservationAddress", "鼓楼区");
        map.put("reservationDesc", "丰收");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertReservation"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertWeiBarByTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>(2, 1);
        //"weiBarConfigId", "weiBarTitle", "weiBarContent", "memberId", "parentId", "memberNo", "memberName"
        map.put("token", "c2FsZXMtMQ==");
        map.put("weiBarConfigId", "1");
        map.put("weiBarTitle", "微吧");
        map.put("weiBarContent", "微吧微吧");
        map.put("memberId", "1");
        map.put("parentId", "-1");
        map.put("memberNo", "681111");
        map.put("memberName", "nelson");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiBarByToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertWeiBarConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"keyword", "matchType", "matchType", "messageImageURL"
           map.put("weiBarConfigId", "2");
        map.put("keyword", "微bar");
        map.put("messageImageURL", "http://www.google.com");
        map.put("matchType", "1");
        map.put("messageTitle", "微吧");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiBarConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinsertWeiBarService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberCardLevelMin", "memberCardLevelMax", "memberCardLevel", "memberCardBackgroupURLmemberCardLevelMin", "memberCardLevelMax", "memberCardLevel", "memberCardBackgroupURL
        map.put("weiBarConfigId", "1");
        map.put("weiBarTitle", "微吧");
        map.put("weiBarContent", "微吧微吧");
        map.put("memberId", "1");
        map.put("parentId", "-1");
        map.put("memberNo", "681111");
        map.put("memberName", "nelson");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertWeiBar"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //@Test

    public void testupdateReservationConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"keyword", "matchType", "messageTitle", "messageImageURL"
        map.put("reservationConfigId", "1");
        map.put("keyword", "订购");
        map.put("matchType", "1");
        map.put("messageTitle", "订购");
        map.put("messageImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateReservationConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //
    //@Test

    public void testupdateReservationMemberService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"reservationMemberName", "reservationMemberTelephone", "reservationMemberDateTime", "reservationMemberAddress", "reservationMemberDesc"
        map.put("reservationMemberId", "1");
        map.put("reservationMemberName", "nelsonyang");
        map.put("reservationMemberTelephone", "18559871814");
        map.put("reservationMemberDateTime", "2014-01-01 00:00:00");
        map.put("reservationMemberDesc", "订购");
        map.put("reservationMemberAddress", "fz");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateReservationMember"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//

   //@Test
    public void testupdateReservationService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"reservationConfigId", "reservationAddress", "reservationTelephone", "reservationDesc"
        map.put("reservationId", "3");
        map.put("keyword", "预约");
        map.put("reservationConfigId", "1");
        map.put("reservationAddress", "南京");
        map.put("messageTitle", "预约");
        map.put("messageImageURL", "http://www.baidu.com");
        map.put("reservationTelephone", "18559871814");
        map.put("reservationDesc", "订购");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateReservation"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//

    //@Test
    public void testupdateWeiBarConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"keyword", "matchType", "messageTitle", "messageImageURL", "companyId"
        map.put("weiBarConfigId", "1");
        map.put("keyword", "吧");
        map.put("matchType", "1");
        map.put("messageTitle", "尾巴");
        map.put("messageImageURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateWeiBarConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//
    //@Test

    public void testupdateWeiBarService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"weiBarConfigId", "weiBarTitle", "weiBarContent", "memberId", "parentId", "memberNo", "memberName"
        map.put("weiBarId", "1");
        map.put("weiBarConfigId", "1");
        map.put("weiBarContent", "你好");
        map.put("memberId", "1");
        map.put("parentId", "-1");
        map.put("memberNo", "18559871816");
        map.put("memberName", "nelson58");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateWeiBar"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testinquireReservationByIdAndTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"reservationId", "token"
        map.put("reservationId", "1");
        map.put("token", "c2FsZXMtMQ==");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationByIdAndToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiBarByIdAndTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"weiBarId", "token"
        map.put("weiBarId", "3");
        map.put("token", "c2FsZXMtMQ==");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiBarByIdAndToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageReservationConfigListervice() throws IOException {
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
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageReservationConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageReservationListservice() throws IOException {
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
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageReservationList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageReservationMemberListservice() throws IOException {
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
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageReservationMemberList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageWeiBarConfigListservice() throws IOException {
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
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageWeiBarConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageWeiBarListservice() throws IOException {
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
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageWeiBarList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePersonCardByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("personCardId", "2");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePersonCardById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireReservationByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("reservationId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testinquireReservationConfigByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("reservationConfigId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationConfigById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireReservationConfigListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testinquireReservationMemberListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationMemberList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

   // @Test
    public void testinquireWeiBarConfigListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiBarConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test

    public void testinquireWeiBarListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiBarList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireReservationListService() throws IOException {

        String json = "{}";

        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireReservationMemberByIdServiceService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("reservationMemberId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireReservationMemberById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    @Test
    public void testinquireWeiBarById() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("weiBarId", "3");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiBarById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireWeiBarConfigById() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("weiBarConfigId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireWeiBarConfigById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test

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