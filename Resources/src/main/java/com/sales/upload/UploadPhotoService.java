package com.sales.upload;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.entity.FileEntity;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.enumeration.UploadEnum;
import com.framework.service.api.Service;
import com.framework.utils.FileUtils;
import com.sales.config.ActionNames;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Administrator
 */
@ServiceConfig(
        act = ActionNames.uploadPhoto,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        returnParameters = {"filePath"},
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        description = "上传图片的接口",
        terminalType = TerminalTypeEnum.WEB,
        upload = UploadEnum.FLASH,
         validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UploadPhotoService implements Service {

    private final Logger logger = LoggerFactory.getLogger(UploadPhotoService.class);

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = (InvocationContext) applicationContext.getThreadLocalManager().getValue();
        List<FileEntity> fileList = invocationContext.getFileList();
        String filePath = "";
        if (fileList != null && !fileList.isEmpty()) {
            String uploadDir = applicationContext.getUploadDir();
            String uploadURL = applicationContext.getUploadURL();
            FileEntity fileEntity = fileList.get(0);
            filePath = FileUtils.writeFileToDir(fileEntity.getContents(), fileEntity.getFileName(), uploadDir,uploadURL);
        }
        logger.debug("filePath=".concat(filePath));
        Map<String,String> resultMap = new HashMap<String,String>(2,1);
        resultMap.put("filePath", filePath);
        applicationContext.setMapData(resultMap);
        applicationContext.success();
    }
}
