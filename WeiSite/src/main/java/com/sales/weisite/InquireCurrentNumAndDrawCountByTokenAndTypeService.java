package com.sales.weisite;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.JsonUtils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.Award;
import com.sales.entity.EntityNames;
import com.sales.entity.EventFlow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireAwardCurrentNumAndDrawCountByToken,
        importantParameters = {"token", "weixinId", "eventId"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        requireLogin = LoginEnum.NOT_REQUIRE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"drawCount", "currentAwards"},
        description = "查询抽奖的次数和当前已经获奖的次数",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token"),
    @Field(fieldName = "eventId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR1024, description = "访问者微信id")
})
public class InquireCurrentNumAndDrawCountByTokenAndTypeService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String eventId = parameters.get("eventId");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyCondition);
            Condition eventIdCondition = new Condition("eventId", ConditionTypeEnum.EQUAL, eventId);
            conditionList.add(eventIdCondition);
            List<Award> awardList = awardDao.inquireByCondition(conditionList);
            List<Map<String,String>> mapList = new ArrayList<Map<String,String>>(awardList.size());
            Map<String,String> awardMap;
            for(Award award : awardList){
                awardMap = new HashMap<String,String>(4,1);
                awardMap.put("awardId", String.valueOf(award.getAwardId()));
                awardMap.put("awardName", award.getAwardName());
                awardMap.put("awardCurrentNum", String.valueOf(award.getAwardCurrentNum()));
                mapList.add(awardMap);
            }
            String json = JsonUtils.mapListToJsonArray(mapList);
            EntityDao<EventFlow> eventFlowDao = applicationContext.getEntityDAO(EntityNames.eventFlow);
            Condition weixinIdCondition = new Condition("weixinId", ConditionTypeEnum.EQUAL, weixinId);
            conditionList.add(weixinIdCondition);
           int drawCount =  eventFlowDao.countByCondition(conditionList);
           Map<String,String> resultMap = new HashMap<String,String>(2,1);
           resultMap.put("drawCount", String.valueOf(drawCount));
           resultMap.put("currentAwards", json);
           applicationContext.setMapData(resultMap);
           applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
