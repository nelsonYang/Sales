package com.sales.weixinservice.utils;

import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class ResponseMessageCfgUtils {

    public static List<ResponseMessageCfg> findKeyWord(String keyWord, List<ResponseMessageCfg> responseMessageCfgList, byte matchType) {
        List<ResponseMessageCfg> resultResponseMessageCfgList = new ArrayList<ResponseMessageCfg>(0);
        for (ResponseMessageCfg ResponseMessageCfg : responseMessageCfgList) {
            if (matchType == 1) {
                if (keyWord.equals(ResponseMessageCfg.getKeyword())) {
                    resultResponseMessageCfgList.add(ResponseMessageCfg);
                }
            } else {
                if (ResponseMessageCfg.getKeyword().contains(keyWord)) {
                    resultResponseMessageCfgList.add(ResponseMessageCfg);
                }
            }
        }
        return resultResponseMessageCfgList;
    }
}
