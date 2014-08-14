package com.sales.statics;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.DateTimeUtils;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 13 site 14 da 15 shui 16 gu
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireCurrentDayStatics,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"fansIncreaseCount", "eventTakeCount", "visitWeisiteCount", "registerMemberCount"},
        description = "查询当天统计数据",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireCurrentDayStaticsService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        String currentDay = DateTimeUtils.currentYYYYMMDDDay();
        EntityDao<Member> eventDao = applicationContext.getEntityDAO(EntityNames.member);
        String companyIdStr = String.valueOf(companyId);
        Map<String, String> dataMap = new HashMap<String, String>(2, 1);
        dataMap.put("companyId", companyIdStr);
        StringBuilder sqlBuilder = new StringBuilder(50);
        sqlBuilder.append("select type, count(*) totalCount from weixinMessage   where companyId = ?").append(" and date_format( createTime,'%Y-%m-%d') ='").append(currentDay).append("'").append(" and type in (").append("10,13,14,15,16").append(") group by type");
        StringBuilder memberSqlBuilder = new StringBuilder(100);
        memberSqlBuilder.append("select count(*) totalCount from member   where companyId =? ").append(" and date_format( createTime,'%Y-%m-%d') ='").append(currentDay).append("'");
        List<Map<String, String>> resultMapList = eventDao.executeQueryBySql(new String[]{"weixinMessage"}, sqlBuilder.toString(), new String[]{companyIdStr}, dataMap);
        List<Map<String, String>> memberMapList = eventDao.executeQueryBySql(new String[]{"member"}, memberSqlBuilder.toString(), new String[]{companyIdStr}, dataMap);
        Map<String, String> resultMap = new HashMap<String, String>(4, 1);
        resultMap.put("fansIncreaseCount", "0");
        resultMap.put("eventTakeCount", "0");
        resultMap.put("visitWeisiteCount", "0");
        resultMap.put("registerMemberCount", "0");
        String type;
        long eventCount = 0;

        for (Map<String, String> tempResultMap : resultMapList) {
            type = tempResultMap.get("type");
            if (type != null) {
                if ("10".equals(type)) {
                    resultMap.put("fansIncreaseCount", tempResultMap.get("fansIncreaseCount"));
                } else if ("13".equals(type)) {
                    resultMap.put("visitWeisiteCount", tempResultMap.get("visitWeisiteCount"));
                } else {
                    eventCount = eventCount + Long.parseLong(tempResultMap.get("totalCount"));
                }
            }
        }
        resultMap.put("eventTakeCount", String.valueOf(eventCount));
        if (memberMapList != null && !memberMapList.isEmpty()) {
            resultMap.put("registerMemberCount", memberMapList.get(0).get("registerMemberCount"));
        }
        applicationContext.setMapData(resultMap);
        applicationContext.success();
    }
}
