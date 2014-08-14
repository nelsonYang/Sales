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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireFansStatics,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"date", "fansIncreaseAmount", "fansDecreaseAmount"},
        description = "查询会员新增列表",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireFansStaticsService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        String currentMonth = DateTimeUtils.currentYYYYMMDay();
        String companyIdStr = String.valueOf(companyId);
        Map<String, String> dataMap = new HashMap<String, String>(2, 1);
        dataMap.put("companyId", companyIdStr);
        StringBuilder sqlBuilder = new StringBuilder(50);
        EntityDao<Member> eventDao = applicationContext.getEntityDAO(EntityNames.member);
        sqlBuilder.append("select date_format( createTime,'%Y-%m-%d')  date,type, count(*) totalCount from weixinMessage   where companyId = ?").append(" and date_format( createTime,'%Y-%m') ='").append(currentMonth).append("'").append(" and type in (").append("10,11").append(") group by date, type");
        List<Map<String, String>> resultMapList = eventDao.executeQueryBySql(new String[]{"weixinMessage"}, sqlBuilder.toString(), new String[]{companyIdStr}, dataMap);
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>(resultMapList.size());
        Map<String, String> resultMap;
        String type;
        // 14 da 15 shui 16 gu
        for (Map<String, String> tempResultMap : resultMapList) {
            resultMap = new HashMap<String, String>(4, 1);
            resultMap.put("date", tempResultMap.get("date"));
            resultMap.put("fansIncreaseAmount", "0");
            resultMap.put("fansDecreaseAmount", "0");
            type = tempResultMap.get("type");
            if (type != null) {
                if ("11".equals(type)) {
                    resultMap.put("fansDecreaseAmount", tempResultMap.get("totalCount"));
                } else if ("10".equals(type)) {
                    resultMap.put("fansIncreaseAmount", tempResultMap.get("totalCount"));
                }
            }
            resultList.add(resultMap);
        }
        Map<String, List<Map<String, String>>> dataListMap = new HashMap<String, List<Map<String, String>>>();
        dataListMap.put("dataList", resultList);
        applicationContext.setListMapData(dataListMap);
        applicationContext.success();
    }
}
