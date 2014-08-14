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
import com.framework.utils.SetUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.Coupon;
import com.sales.entity.EntityNames;
import com.sales.entity.CouponMember;
import com.sales.entity.Member;
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
        act = ActionNames.inquirePageCouponMemberList,
        importantParameters = {"session", "pageCount", "pageNo", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"couponMemberId", "memberId", "companyId", "createTime", "couponId", "memberNo", "couponName", "effectiveStartTime", "effectiveEndTime", "realName", "mobile"},
        description = "分页查询CouponMember",
        validateParameters = {
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageCouponMemberListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageCouponMemberListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        EntityDao<CouponMember> entityDAO = applicationContext.getEntityDAO(EntityNames.couponMember);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
        List<CouponMember> entityList = enityPageMode.getDataList();
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                Set<Long> couponIdSet = new HashSet<Long>(entityList.size());
                Set<Long> memberIdSet = new HashSet<Long>(entityList.size());
                for (CouponMember couponMember : entityList) {
                    couponIdSet.add(couponMember.getCouponId());
                    memberIdSet.add(couponMember.getMemberId());
                }
                EntityDao<Member> memberDAO = applicationContext.getEntityDAO(EntityNames.member);
                conditionList = new ArrayList<Condition>(2);
                companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition memberIdCondition = new Condition("memberId", ConditionTypeEnum.IN, SetUtils.LongSet2Str(memberIdSet));
                conditionList.add(memberIdCondition);
                List<Member> memberList = memberDAO.inquireByCondition(conditionList);
                EntityDao<Coupon> couponDAO = applicationContext.getEntityDAO(EntityNames.coupon);
                conditionList = new ArrayList<Condition>(0);
                companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition couponIdCondition = new Condition("couponId", ConditionTypeEnum.IN, SetUtils.LongSet2Str(couponIdSet));
                conditionList.add(couponIdCondition);
                List<Coupon> couponList = couponDAO.inquireByCondition(conditionList);
                List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(entityList.size());
                Map<String, String> couponMemberMap;
                for (CouponMember couponMember : entityList) {
                    couponMemberMap = couponMember.toMap();
                    couponMemberMap.put("couponName", "");
                    couponMemberMap.put("effectiveStartTime", "");
                    couponMemberMap.put("effectiveEndTime", "");
                    couponMemberMap.put("realName", "");
                    couponMemberMap.put("mobile", "");
                    for (Coupon coupon : couponList) {
                        if (couponMember.getCouponId() == coupon.getCouponId()) {
                            couponMemberMap.putAll(coupon.toMap());
                            break;
                        }
                    }
                    for (Member member : memberList) {
                        if (member.getMemberId() == couponMember.getMemberId()) {
                            couponMemberMap.putAll(member.toMap());
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

    }
}
