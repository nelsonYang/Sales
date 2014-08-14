package com.sales.member;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PageModel;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.SetUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.Coupon;
import com.sales.entity.EntityNames;
import com.sales.entity.CouponMember;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePageCouponMemberListByToken,
        importantParameters = {"memberId", "pageCount", "pageNo", "encryptType", "token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"couponMemberId", "memberId", "companyId", "createTime", "couponId", "memberNo", "couponName", "effectiveStartTime", "effectiveEndTime"},
        description = "分页查询CouponMember",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageCouponMemberListByToken implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageCouponMemberListByToken.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();

        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        String token = parameters.get("token");
        String memberId = parameters.get("memberId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            EntityDao<CouponMember> entityDAO = applicationContext.getEntityDAO(EntityNames.couponMember);
            List<Condition> conditionList = new ArrayList<Condition>(0);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            Condition memberIdCondition = new Condition("memberId", ConditionTypeEnum.EQUAL, String.valueOf(memberId));
            conditionList.add(memberIdCondition);
            PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
            List<CouponMember> entityList = enityPageMode.getDataList();
            if (entityList != null) {
                if (!entityList.isEmpty()) {
                    Set<Long> couponIdSet = new HashSet<Long>(entityList.size());
                    for (CouponMember couponMember : entityList) {
                        couponIdSet.add(couponMember.getCouponId());
                    }
                    EntityDao<Coupon> couponDAO = applicationContext.getEntityDAO(EntityNames.coupon);
                    conditionList = new ArrayList<Condition>(0);
                    companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
                    conditionList.add(companyIdCondition);
                    Condition couponIdCondition = new Condition("couponId", ConditionTypeEnum.IN, SetUtils.LongSet2Str(couponIdSet));
                    conditionList.add(couponIdCondition);
                    List<Coupon> couponList = couponDAO.inquireByCondition(conditionList);
                    List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(entityList.size());
                    Map<String,String> couponMemberMap;
                    for (CouponMember couponMember : entityList) {
                        couponMemberMap = couponMember.toMap();
                        couponMemberMap.put("couponName", "");
                        couponMemberMap.put("effectiveStartTime", "");
                        couponMemberMap.put("effectiveEndTime", "");
                        for(Coupon coupon : couponList){
                            if(couponMember.getCouponId() == coupon.getCouponId()){
                                couponMemberMap.putAll(coupon.toMap());
                                break;
                            }
                        }
                        resultMapList.add(couponMemberMap);
                        
                    }
                    Map<String, List<Map<String, String>>> listMap = new HashMap<String, List<Map<String, String>>>(2, 1);
                    listMap.put("dataList", resultMapList);
                    applicationContext.setListMapData(listMap);
                    applicationContext.setTotalPage(enityPageMode.getTotalPage());
                    applicationContext.setCount(enityPageMode.getTotalCount());
                    applicationContext.success();
                } else {
                    applicationContext.noData();
                }
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
