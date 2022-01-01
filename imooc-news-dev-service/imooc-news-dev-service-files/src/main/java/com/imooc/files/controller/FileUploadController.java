package com.imooc.files.controller;

import com.imooc.api.controller.files.FileUploadControllerApi;
import com.imooc.enums.ResponseStatus;
import com.imooc.files.service.FileUploadService;
import com.imooc.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

    /**
     * 上传头像
     * <p>
     * 逻辑流程：
     * 1. 如果文件为空或文件名为空，则直接返回
     * 2. 否则
     * ---- 1. 获取文件名称，判断文件格式是否符合规范（png,jpg,jpeg）,如果不符合规范则直接返回
     * ---- 否则
     * -------- 1. 调用阿里云上传服务，上传文件成功后，获得文件的路径
     * -------- 2. 将文件路径同响应信息一同返回给前端
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
        // TODO: 待整合阿里云 OSS 完成头像上传服务
        String path = fileUploadService.upload();
        log.info("File Path : {}", path);
        return new JsonResult(ResponseStatus.SUCCESS, path);
    }
}
