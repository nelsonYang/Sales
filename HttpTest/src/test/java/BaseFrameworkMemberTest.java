
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
public class BaseFrameworkMemberTest {

    // private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static final String url = "http://localhost:8080/Sales/SalesService?";
    private static HttpConnection httpClient;
    private static CryptoManager cryptoManager;
    private static String key;
    private static String session;
    private static CookieStore cookie;

    public BaseFrameworkMemberTest() {
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

    // @Test
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
    public void testInsertCouponConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"keyword", "matchType", "messageTitle", "messageImageURL",, "couponNum", "couponCurrentNum", "effectiveStartTime", "effectiveEndTime", "couponName", "couponDesc"
        map.put("couponConfigId", "7");
        map.put("keyword", "优惠券");
        map.put("matchType", "1");
        map.put("messageTitle", "优惠券");
        map.put("messageImageURL", "http://www.google.com");
        map.put("couponNum", "10");
        map.put("couponCurrentNum", "6");
        map.put("effectiveStartTime", "2014-02-01 00:00:00");
        map.put("effectiveEndTime", "2015-05-01 00:00:00");
        map.put("couponName", "优惠券");
        map.put("couponDesc", "优惠券");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertCouponConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInsertCouponService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"memberId", "companyId", "couponConfigId" couponName
        map.put("keyword", "优惠券");
        map.put("couponName", "优惠券");
        map.put("messageTitle", "优惠券");
        map.put("messageURL", "http://www.baidu.com");
        map.put("memberId", "3");
        map.put("couponConfigId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertCoupon"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//
    //@Test

    public void testInsertMemberCardConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //merchantName", "isExperienceOpen", "telephone", "memberCardName", "merchantLogo", "memberCardBackgroupURL", "merchantAddress", "integerationPerSign", "keyword", "matchType", "title", "messageImageURL", "memberCardDesc"
        map.put("memberCardConfigId", "2");

        map.put("isExperienceOpen", "2");
        map.put("memberCardName", "金卡");
        map.put("merchantLogo", "http://www.google.com");
        map.put("memberCardBackgroupURL", "http://www.google.com");
        map.put("merchantAddress", "鼓楼区");
        map.put("integerationPerSign", "1");
        map.put("keyword", "鱼庄");
        map.put("matchType", "1");
        map.put("title", "丰收");
        map.put("messageImageURL", "http://www.baidu.com");
        map.put("memberCardDesc", "丰收");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMemberCardConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInsertMemberCardLevelService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberCardLevelMin", "memberCardLevelMax", "memberCardLevel", "memberCardBackgroupURLmemberCardLevelMin", "memberCardLevelMax", "memberCardLevel", "memberCardBackgroupURL
        map.put("memberCardLevelMin", "1");
        map.put("memberCardLevelMax", "10");
        map.put("memberCardLevel", "1");
        map.put("memberCardBackgroupURL", "http://www.baidu.com");
        map.put("memberCardLevelMax", "50");
        map.put("memberCardLevel", "1");
        map.put("memberCardBackgroupURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMemberCardLevel"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInsertMemberCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberLogoURL", "memberCardURL", "memberCardName", "memberCardDesc", "effectiveStartTime", "effectiveEndTime", "readCount", "clickCount", "companyId", "balance", "consumeMoney", "openId", "memberId", "memberCardConfigId"
        map.put("memberLogoURL", "http://www.baidu.com");
        map.put("memberCardURL", "http://www.baidu.com");
        map.put("memberCardName", "会员卡");
        map.put("memberCardDesc", "会员卡");
        map.put("keyword", "会员卡");
        map.put("effectiveStartTime", "2014-01-10 00:00:00");
        map.put("effectiveEndTime", "2015-01-10 00:00:00");
        map.put("readCount", "1");
        map.put("clickCount", "1");
        map.put("balance", "10");
        map.put("consumeMoney", "1");
        map.put("openId", "1");
        map.put("memberId", "3");
        map.put("memberCardConfigId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMemberCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

    //@Test
    public void testInsertMemberService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //realName", "weixinNo", "qqNO", "email", "mobile", "birthday", "memberNO", "address", "integration
        map.put("realName", "nelsonyang");
        map.put("weixinNo", "nelsonyang");
        map.put("qqNO", "864867186");
        map.put("email", "864867186@qq.com");
        map.put("mobile", "18559871814");
        map.put("birthday", "2013-01-02");
        map.put("memberNO", "18559871814");
        map.put("address", "xxxxx");
        map.put("integration", "1");
        map.put("password", "12233332");
//
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertMember"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }

//
//    //// newstest MemberLoginService
    //@Test
    public void testNewsTakeMemberCardByTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
//token", "weixinId", "realName", "weixinNo", "qqNo", "mobile", "email", "birthday", "memberCardId  
        map.put("token", "c2FsZXMtMQ==");
        map.put("memberId", "3");
        map.put("weixinId", "nelsonyang");
        map.put("weixinNo", "nelsonyang");
        map.put("realName", "nelsonyang");
        map.put("qqNo", "864867186");
        map.put("mobile", "18559871814");
        map.put("email", "864867186@qq.com");
        map.put("birthday", "2014-01-02");
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

    public void testInsertCouponMemberByTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>(2, 1);
        //"memberId", "couponId", "memberNo"
        map.put("token", "c2FsZXMtMQ==");
        map.put("couponId", "1");
        map.put("memberNo", "18559871816");
        map.put("memberId", "1");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=insertCouponMemberByToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //@Test

    public void testUpdateCouponMemberService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberId", "couponId", "memberNo
        map.put("couponMemberId", "1");
        map.put("memberId", "1");
        map.put("couponId", "1");
        map.put("memberNo", "18559871814");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateCouponMember"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
    //

    // @Test
    public void testUpdateCouponService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberId", "couponConfigId
        map.put("couponId", "3");
        map.put("keyword", "优惠");
        map.put("messageURL", "http://www.google.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateCoupon"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//
    //@Test

    public void testUpdateMemberCardConfigService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //merchantName", "isExperienceOpen", "telephone", "memberCardName", "merchantLogo", "memberCardBackgroupURL", "merchantAddress", "integerationPerSign", "keyword", "matchType", "title", "messageImageURL", "memberCardDesc
        map.put("memberCardConfigId", "1");
        map.put("merchantName", "大风收");
        map.put("isExperienceOpen", "1");
        map.put("telephone", "1");
        map.put("memberCardName", "VIP");
        map.put("merchantLogo", "http://www.baidu.com");
        map.put("memberCardBackgroupURL", "http://www.baidu.com");
        map.put("merchantAddress", "苍山万达");
        map.put("integerationPerSign", "10");
        map.put("keyword", "xx");
        map.put("matchType", "1");
        map.put("messageImageURL", "http://www.baidu.com");
        map.put("memberCardDesc", "大风收VIP");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMemberCardConfig"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//

    //@Test
    public void testUpdateMemberCardLevelService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberCardLevelMin", "memberCardLevelMax", "memberCardLevel", "memberCardBackgroupURL
        map.put("memberCardLevelId", "1");
        map.put("memberCardLevelMin", "1");
        map.put("memberCardLevelMax", "100");
        map.put("memberCardLevel", "1");
        map.put("memberCardBackgroupURL", "http://www.baidu.com");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMemberCardLevel"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//

    //@Test
    public void testUpdateMemberCardService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //memberLogoURL", "memberCardURL", "memberCardName", "memberCardDesc", "effectiveStartTime", "effectiveEndTime", "readCount", "clickCount", "balance", "consumeMoney", "openId", "memberId", "memberCardConfigId
        map.put("memberCardId", "4");
        map.put("memberLogoURL", "http://www.google.com");
        map.put("memberCardURL", "http://www.google.com");
        map.put("memberCardName", "VIP");
        map.put("memberCardDesc", "VIP");
        map.put("effectiveStartTime", "2014-01-01 00:00:00");
        map.put("effectiveEndTime", "2014-05-01 00:00:00");
        map.put("readCount", "1");
        map.put("keyword", "会员");
        map.put("clickCount", "1");
        map.put("balance", "1");
        map.put("consumeMoney", "1");
        map.put("openId", "1");
        map.put("memberId", "1");
        map.put("memberCardConfigId", "1");
//
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMemberCard"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);

    }
//

    //@Test
    public void testUpdateMemberService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //realName", "weixinNo", "qqNO", "email", "mobile", "birthday", "createTime", "memberNO", "address", "companyId", "integration"
        map.put("memberId", "1");
        map.put("realName", "nelsonyang");
        map.put("weixinNo", "18559871814");
        map.put("qqNO", "864867186");
        map.put("email", "864867186@qq.com");
        map.put("mobile", "18559871814");
        map.put("birthday", "2014-01-10");
        map.put("memberNO", "18559871814");
        map.put("address", "xxxx");
        map.put("companyId", "18559871814");
        map.put("integration", "100");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=updateMember"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireCouponByIdAndTokenService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("couponId", "10");
        map.put("token", "c2FsZXMtMQ==");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);

//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCouponByIdAndToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireCouponByIdervice() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("couponId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCouponById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireCouponConfigByIdService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("couponConfigId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCouponConfigById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireCouponConfigListService() throws IOException {

        String json = "{}";
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCouponConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testInquireCouponMemberByIdServiceService() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("couponMemberId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCouponMemberById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireCouponMemberListService() throws IOException {

        String json = "{}";
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireCouponMemberList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireMemberCardByIdAndToken() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("memberCardId", "1");
        map.put("token", "c2FsZXMtMQ==");
        String json = JsonUtils.mapToJson(map);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoSessionAndCryptJson(json, "1");
        parameters.put("request", requestJson);
        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
        String mdtjsonJson = md5.encrypt(requestJson, null);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardByIdAndToken&sign=").concat(mdtjsonJson), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireMemberCardById() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("memberCardId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    // @Test
    public void testinquireMemberCardConfigById() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("memberCardConfigId", "1");

        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardConfigById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test

    public void testinquireMemberCardLevelById() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"
        map.put("memberCardLevelId", "1");
        String json = JsonUtils.mapToJson(map);
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardLevelById"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }
    //@Test

    public void testinquireMemberCardList() throws IOException {

        String json = "{}";
        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildCryptJson(session, dataJson, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageCouponConfigList() throws IOException {

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
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageCouponConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageCouponList() throws IOException {

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
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageCouponList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageCouponMemberList() throws IOException {

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
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageCouponMemberList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquireMemberCardConfigList() throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        //"couponId", "token"


        String json = JsonUtils.mapToJson(map);
//        ICrypto aes = cryptoManager.getCrypto(CryptEnum.AES);
//        String dataJson = aes.encrypt(json, key);
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        String requestJson = buildNoCryptJson(session, json, "1");
        parameters.put("request", requestJson);
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquireMemberCardConfigList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageMemberCardLevelList() throws IOException {

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
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageMemberCardLevelList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageMemberCardList() throws IOException {

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
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageMemberCardList"), parameters, cookie);
        String content = response.getResult();
        cookie = response.getCookie();
        System.out.println("content=" + content);
    }

    //@Test
    public void testinquirePageMemberList() throws IOException {

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
//        ICrypto md5 = cryptoManager.getCrypto(CryptEnum.MD5);
//        String mdtjsonJson = md5.encrypt(requestJson, null);
        HttpResponseEntity response = httpClient.executePost(url.concat("act=inquirePageMemberList"), parameters, cookie);
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