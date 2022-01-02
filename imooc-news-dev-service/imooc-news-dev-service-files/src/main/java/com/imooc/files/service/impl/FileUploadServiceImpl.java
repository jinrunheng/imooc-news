package com.imooc.files.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.imooc.files.service.FileUploadService;
import com.imooc.resource.aliyun.AliyunResource;
import com.imooc.utils.FileUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2022/1/1 8:55 下午
 * @Version 1.0
 * @Description 文件上传服务实现类
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    private FileUploadUtils fileUploadUtils;


    @Override
    public String upload(MultipartFile file, String userId, String suffix) {
        return fileUploadUtils.upload(file, userId, suffix);
    }
}
