package com.sales.upload;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.FileUtils;
import com.sales.config.ActionNames;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author nelson
 */
@ServiceConfig(
        act =ActionNames.uploadRequiredPhoto,
        importantParameters = {"session", "filePath", "width", "height", "dx1", "dy1", "dx2", "dy2", "degrees", "rate", "type", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.BATCH_MAP,
        returnParameters = {"filePath", "type"},
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        description = "上传截取图片的接口",
        terminalType = TerminalTypeEnum.WEB,
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "filePath", fieldType = FieldTypeEnum.CHAR1024, description = "图片的路径"),
    @Field(fieldName = "width", fieldType = FieldTypeEnum.INT, description = "图片的宽度"),
    @Field(fieldName = "height", fieldType = FieldTypeEnum.INT, description = "图片的高度"),
    @Field(fieldName = "dx1", fieldType = FieldTypeEnum.INT, description = "图片的坐标x1"),
    @Field(fieldName = "dy1", fieldType = FieldTypeEnum.INT, description = "图片的坐标y1"),
    @Field(fieldName = "dx2", fieldType = FieldTypeEnum.INT, description = "图片的坐标x2"),
    @Field(fieldName = "dy2", fieldType = FieldTypeEnum.INT, description = "图片的坐标y2"),
    @Field(fieldName = "degrees", fieldType = FieldTypeEnum.INT, description = "图片旋转的度数"),
    @Field(fieldName = "rate", fieldType = FieldTypeEnum.DOUBLE, description = "图片是否缩放"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.INT, description = "图片类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UploadRequiredPhotoService implements Service {

    private final Logger logger = LoggerFactory.getLogger(UploadRequiredPhotoService.class);

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String[]> parameterMap = applicationContext.getBatchMapParameters();
        long userId = applicationContext.getUserId();
        String uploadDir = applicationContext.getUploadDir();
        String uploadURL = applicationContext.getUploadURL();
        String[] filePaths = parameterMap.get("filePath");
        String[] widths = parameterMap.get("width");
        String[] heights = parameterMap.get("height");
        String[] dx1s = parameterMap.get("dx1");
        String[] dy1s = parameterMap.get("dy1");
        String[] dx2s = parameterMap.get("dx2");
        String[] dy2s = parameterMap.get("dy2");
        String[] degreeses = parameterMap.get("degrees");
        String[] rates = parameterMap.get("rate");
        String[] types = parameterMap.get("type");
        //获取上传图片
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(3);
        Map<String, String> resultMap;
        File sourceFile;
        int width;
        int height;
        int dx1;
        int dy1;
        int dx2;
        int dy2;
        int degrees;
        double rate;
        int type;
        try {
            for (int index = 0; index < filePaths.length; index++) {
                resultMap = new HashMap<String, String>(2, 1);
                sourceFile = FileUtils.getFile(uploadDir, filePaths[index]);
                width = Integer.parseInt(widths[index]);
                height = Integer.parseInt(heights[index]);
                dx1 = Integer.parseInt(dx1s[index]);
                dy1 = Integer.parseInt(dy1s[index]);
                dx2 = Integer.parseInt(dx2s[index]);
                dy2 = Integer.parseInt(dy2s[index]);
                type = Integer.parseInt(types[index]);
                degrees = Integer.parseInt(degreeses[index]);
                rate = Double.parseDouble(rates[index]);
                if (sourceFile != null) {

                    BufferedImage sourceImage = ImageIO.read(sourceFile);
                    BufferedImage tempImage;
                    Graphics2D g2d;
                    //是否缩放
                    if (rate != 1) {
                        //最大放大2倍,最小0.1
                        rate = rate > 5 ? 5 : rate;
                        rate = rate < 0.2 ? 0.2 : rate;
                        double srcW = sourceImage.getWidth() * rate;
                        double srcH = sourceImage.getHeight() * rate;
                        tempImage = new BufferedImage((int) srcW, (int) srcH, BufferedImage.TYPE_INT_RGB);
                        g2d = tempImage.createGraphics();
                        g2d.drawImage(sourceImage, 0, 0, (int) srcW, (int) srcH, null);
                        sourceImage = tempImage;
                    }
                    //是否旋转
                    double theta = 0;
                    //长,宽是否调换
                    boolean flag = true;
                    if (degrees > 0) {
                        degrees = degrees % 360;
                    } else {
                        while (degrees < 0) {
                            degrees += 360;
                        }
                    }
                    if (degrees > 0 && degrees < 90) {
                        degrees = 0;
                    } else if (degrees > 90 && degrees < 180) {
                        degrees = 90;
                    } else if (degrees > 180 && degrees < 270) {
                        degrees = 180;
                    } else if (degrees > 270) {
                        degrees = 270;
                    }
                    if (degrees == 0 || degrees == 180) {
                        flag = false;
                    }
                    theta = Math.toRadians(degrees);
                    int dx = 0;
                    int dy = 0;
                    if (theta != 0) {
                        double rw = sourceImage.getWidth() / 2;
                        double rh = sourceImage.getHeight() / 2;
                        double rr = rw * rw + rh * rh;
                        double r = Math.sqrt(rr);
                        int intR = (int) Math.ceil(r);
                        int int2R = intR * 2;
                        int initX = (int) (r - sourceImage.getWidth() / 2);
                        int initY = (int) (r - sourceImage.getHeight() / 2);
                        if (flag) {
                            dx = initY;
                            dy = initX;
                        } else {
                            dx = initX;
                            dy = initY;
                        }
                        tempImage = new BufferedImage(int2R, int2R, BufferedImage.TYPE_INT_RGB);
                        g2d = tempImage.createGraphics();
                        g2d.rotate(theta, intR, intR);
                        g2d.drawImage(sourceImage, initX, initY, int2R - initX, int2R - initY, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), null);
                        sourceImage = tempImage;
                    }
                    //截图
                    tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    g2d = tempImage.createGraphics();
                    g2d.drawImage(sourceImage, 0, 0, width, height, dx1 + dx, dy1 + dy, dx2 + dx, dy2 + dy, null);
                    //保存文件
                    String path;
                    String fileName;
                    String fileURL;
                    fileName = userId + "_" + UUID.randomUUID().toString() + ".jpg";
                    path = uploadDir + File.separator + fileName;
                    fileURL = uploadURL + File.separator + fileName;
                    File targetFile = new File(path);
                    boolean isSuccess = targetFile.createNewFile();
                    if (isSuccess) {
                        ImageIO.write(tempImage, "jpg", targetFile);
                        resultMap.put("filePath", fileURL);
                        resultMap.put("type", String.valueOf(type));
                        resultMapList.add(resultMap);
                    } else {
                        logger.error("创建文件出错");
                    }
                } else {
                    logger.error("文件不存在");
                }
            }
            if (!resultMapList.isEmpty()) {
                Map<String, List<Map<String, String>>> dataMap = new HashMap<String, List<Map<String, String>>>();
                dataMap.put("filePaths", resultMapList);
                applicationContext.setListMapData(dataMap);
                applicationContext.success();
            } else {
                applicationContext.fail();
            }
        } catch (IOException ex) {
            logger.error("上传文件出错", ex);
        }
    }
}
