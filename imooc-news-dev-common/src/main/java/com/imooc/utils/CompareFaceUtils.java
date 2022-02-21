package com.imooc.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;

/**
 * @Author Dooby Kim
 * @Date 2022/2/21 9:48 下午
 * @Version 1.0
 */
public class CompareFaceUtils {

    public static void main(String[] args) {
        try {
            String imageAUrl = "";
            String imageBUrl = "";
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("SecretId", "SecretKey");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CompareFaceRequest req = new CompareFaceRequest();

            req.setImageA(imageAUrl);
            req.setImageB(imageBUrl);
            // 返回的resp是一个CompareFaceResponse的实例，与请求对象对应
            CompareFaceResponse resp = client.CompareFace(req);
            // 输出json格式的字符串回包
            System.out.println(CompareFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
