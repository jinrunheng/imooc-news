package com.imooc.files.controller;

import com.imooc.api.controller.files.FileUploadControllerApi;
import com.imooc.bo.AddNewAdminBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.files.service.FileUploadService;
import com.imooc.result.JsonResult;
import com.imooc.utils.ImageReviewUtils;
import com.mongodb.client.gridfs.GridFSBucket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * @Author Dooby Kim
 * @Date 2022/1/1 10:55 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class FileUploadController implements FileUploadControllerApi {

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private ImageReviewUtils imageReviewUtils;

    // 目前"审核失败"这张图片的 URL 地址是写死的
    // private static final String FAIL_IMAGE_URL = "https://tva1.sinaimg.cn/large/008i3skNgy1gxzqinm2vpj30ku0msgn0.jpg";

    @Resource
    private GridFSBucket gridFSBucket;

    /**
     * 上传头像
     * <p>
     * 逻辑流程：
     * 1. 如果文件为空或文件名为空，则直接返回异常响应
     * 2. 否则：
     * ---- 1. 获取文件名称，判断文件格式是否符合规范（png,jpg,jpeg）,如果不符合规范则直接返回异常响应
     * ---- 否则：
     * -------- 1. 调用阿里云上传服务，上传文件成功后，获得文件访问的完整路径
     * ---------------- 1. 如果获得文件路径为空，则说明文件上传失败，返回异常响应
     * -------- 2. 调用阿里云内容安全服务，对图片进行审核
     * ---------------- 1. 如果图片审核通过，则将图片地址一同响应回前端
     * ---------------- 2. 如果图片审核不通过，则将审核失败的图片地址响应回前端
     * <p>
     * <p>
     * 2021-1-2：
     * 目前，内容安全服务并未开通，图片审核环节取消。
     *
     * @param userId
     * @param file
     * @return
     */
    @Override
    public JsonResult uploadFace(String userId, MultipartFile file) {
        if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
            return new JsonResult(ResponseStatus.FILE_UPLOAD_EMPTY_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        if (!suffix.equalsIgnoreCase("png")
                && !suffix.equalsIgnoreCase("jpg")
                && !suffix.equalsIgnoreCase("jpeg")) {
            return new JsonResult(ResponseStatus.FILE_FORMAT_ERROR);
        }

        String path = fileUploadService.upload(file, userId, suffix);

        if (StringUtils.isBlank(path)) {
            return new JsonResult(ResponseStatus.FILE_UPLOAD_FAIL_ERROR);
        }
        log.info("File Path : {}", path);

//        if (imageReviewUtils.reviewImage(path)) {
//            return new JsonResult(ResponseStatus.SUCCESS, path);
//        } else {
//            return new JsonResult(ResponseStatus.SUCCESS, FAIL_IMAGE_URL);
//        }
        return new JsonResult(ResponseStatus.SUCCESS, path);
    }

    /**
     * 上传人脸数据到 MongoDB 的 GridFS，实现人脸入库
     * 实现流程：
     * 1.
     *
     * @param newAdminBO
     * @return
     */
    @Override
    public JsonResult uploadToGridFS(AddNewAdminBO newAdminBO) {
        // 获取图片的 base64 字符串
        String img64 = newAdminBO.getImg64();
        // 将 base64 字符串转换为 byte 数组
        byte[] bytes = Base64.getDecoder().decode(img64.trim());
        // 将 byte 数组转换为输入流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 上传至 GridFS 中，并获取文件在 GridFS 中的主键 ID
        ObjectId fileId = gridFSBucket.uploadFromStream(newAdminBO.getAdminName() + ".png", byteArrayInputStream);

        return new JsonResult(ResponseStatus.SUCCESS, fileId.toString());
    }
}
