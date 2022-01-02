package com.imooc.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.imooc.resource.aliyun.AliyunResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author Dooby Kim
 * @Date 2022/1/2 4:18 下午
 * @Version 1.0
 * @Description 基于阿里云 OSS 的文件上传工具类
 */
@Component
@Slf4j
public class FileUploadUtils {

    @Resource
    private AliyunResource aliyunResource;

    /**
     * @param file
     * @param userId
     * @param suffix
     * @return 返回文件的完整访问路径
     * @throws IOException
     */
    public String upload(MultipartFile file, String userId, String suffix) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = aliyunResource.getEndpoint();
        // 在这里面，我使用 System.getenv() 方法来获取阿里云的 accessKeyId 与 accessKeySecret
        // 因为本项目是一个开源项目，为了避免我的信息上传到 Github 上，所以我将密钥配置到环境变量中，然后使用了获取本地环境变量中的方式
        // 我们也可以使用在配置文件中配置密钥，然后读取配置文件中的值的方式
        // String accessKeyId = aliyunResource.getAccessKeyId();
        // String accessKeySecret = aliyunResource.getAccessKeySecret();

        String accessKeyId = System.getenv("ALIYUN_SECRET_ID");
        String accessKeySecret = System.getenv("ALIYUN_SECRET_KEY");

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写网络流地址。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("获取文件流失败：{}", e.getMessage());
            e.printStackTrace();
        }
        String objectPath = aliyunResource.getObjectName() + "/" + userId + "/" + generateRandomFileName(suffix);
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(aliyunResource.getBucketName(), objectPath, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        return aliyunResource.getOssHost() + "/" + objectPath;
    }

    /**
     * 生成一个随机的文件名，譬如：1e27ef040efb4f18a354b2371083c327.png
     *
     * @param suffix
     * @return
     */
    private String generateRandomFileName(String suffix) {
        return UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
    }
}
