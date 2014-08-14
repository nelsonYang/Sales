package com.sales.event;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesConstant;
import com.sales.config.SalesErrorCode;
import com.sales.entity.Award;
import com.sales.entity.AwardResult;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import com.sales.entity.EventFlow;
import com.sales.entity.WeixinMessage;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.drawLottoryByTokenAndType,
        importantParameters = {"token", "weixinId", "eventId"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        requireLogin = LoginEnum.NOT_REQUIRE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters={"awardName"},
        requireTranscation = true,
        description = "领取奖项",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token"),
    @Field(fieldName = "eventId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR1024, description = "访问者id")
})
public class DrawLottoryByTokenAndTypeService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String eventId = parameters.get("eventId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        long rewardId = new Random().nextLong();
        if (tokens != null && tokens.length == 2) {
            EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
            parameters.put("companyId", tokens[1]);
            parameters.put("awardTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
            parameters.put("awardNum", "1");
            parameters.put("awardSN", UUID.randomUUID().toString());
            
            PrimaryKey primaryKey = new PrimaryKey();
            primaryKey.putKeyField("awardId", String.valueOf(rewardId));
            primaryKey.putKeyField("companyId", tokens[1]);
            Award award = awardDao.inqurieByKey(primaryKey);
            if (award != null) {
                EntityDao<Event> eventDAO = applicationContext.getEntityDAO(EntityNames.event);
                primaryKey = new PrimaryKey();
                primaryKey.putKeyField("eventId", eventId);
                primaryKey.putKeyField("companyId", tokens[1]);
                Event event = eventDAO.inqurieByKey(primaryKey);
                if (award.getAwardCurrentNum() > 0) {
                    String sql = "update award set awardNum = awardNum -1 where awardId= ?";
                    awardDao.executeUpdateBySql(new String[]{"award"}, sql, new String[]{parameters.get("awardId")}, parameters);
                    EntityDao<AwardResult> awardResultDao = applicationContext.getEntityDAO(EntityNames.awardResult);
                    parameters.put("awardStatus", String.valueOf(SalesConstant.UNPROCESS_AWARD_STATUS));
                    AwardResult awardResult = awardResultDao.insert(parameters);
                    EntityDao<EventFlow> eventFlowDao = applicationContext.getEntityDAO(EntityNames.eventFlow);
                    parameters.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
                    parameters.put("companyId", tokens[1]);
                    eventFlowDao.insert(parameters);
                    if (awardResult != null) {
                        EntityDao<WeixinMessage> weixinMesssgeDao = applicationContext.getEntityDAO(EntityNames.weixinMessage);
                        Map<String, String> weixinMessageMap = new HashMap<String, String>();
                        //1-瓜瓜卡 2-水果达人 3-欢乐打转盘
                        weixinMessageMap.put("companyId", tokens[1]);
                        if (event.getType() == 1) {
                            weixinMessageMap.put("type", String.valueOf(SalesConstant.EVENT_GU_GU_KA_TYPE));
                            weixinMessageMap.put("content", "瓜瓜卡");
                        } else if (event.getType() == 2) {
                            weixinMessageMap.put("type", String.valueOf(SalesConstant.EVENT_SHUI_GUO_DA_REN_TYPE));
                            weixinMessageMap.put("content", "水果达人");
                        } else {
                            weixinMessageMap.put("type", String.valueOf(SalesConstant.EVENT_DA_ZHUAN_PAN_TYPE));
                            weixinMessageMap.put("content", "欢乐打转盘");
                        }
                        weixinMessageMap.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
                        weixinMessageMap.put("fromUser", "");
                        weixinMessageMap.put("toUser", "");
                        
                        WeixinMessage weixinMessage = weixinMesssgeDao.insert(weixinMessageMap);
                        if (weixinMessage != null) {
                            Map<String,String> resultMap = new HashMap<String,String>(2,1);
                            resultMap.put("awardName", award.getAwardName());
                            applicationContext.setMapData(resultMap);
                            applicationContext.success();
                        } else {
                            throw new RollBackException("操作失败");
                        }
                        applicationContext.success();
                    } else {
                        applicationContext.fail();
                    }
                } else {
                    applicationContext.fail(SalesErrorCode.AWARD_NUM_EXCCED);
                }
            } else {
                applicationContext.fail(SalesErrorCode.NEXT_TIME);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
        
    }
}
