package com.imooc.files.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Dooby Kim
 * @Date 2022/1/1 8:54 下午
 * @Version 1.0
 * @Description 文件上传服务接口
 */
public interface FileUploadService {
    String upload(MultipartFile file, String userId, String suffix);
}
