package com.imooc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2022/2/21 9:48 下午
 * @Version 1.0
 */
@Component
public class CompareFaceUtils {

    public boolean compare(String imgABase64, String imgBBase64) {
        try {
            String secretId = System.getenv("TENCENT_SECRET_ID");
            String secretKey = System.getenv("TENCENT_SECRET_KEY");

            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CompareFaceRequest req = new CompareFaceRequest();

            req.setImageA(imgABase64);
            req.setImageB(imgBBase64);
            // req.setUrlA(imgAUrl);
            // req.setUrlB(imgBUrl);
            // 返回的resp是一个CompareFaceResponse的实例，与请求对象对应
            CompareFaceResponse resp = client.CompareFace(req);
            // 输出json格式的字符串回包
            // System.out.println(CompareFaceResponse.toJsonString(resp));

            JSONObject jsonObject = JSON.parseObject(CompareFaceResponse.toJsonString(resp));
            String score = jsonObject.get("Score").toString();
            double v = Double.parseDouble(score);
            if (v > 60) {
                return true;
            } else {
                return false;
            }
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            String secretId = System.getenv("TENCENT_SECRET_ID");
            String secretKey = System.getenv("TENCENT_SECRET_KEY");

            String imageAUrl = "https://tva1.sinaimg.cn/large/e6c9d24egy1gztj34493tj20kx0c6dg6.jpg";
            String imageBUrl = "https://tva1.sinaimg.cn/large/e6c9d24egy1gztj2zgvf3j20hs0lemy2.jpg";
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(secretId, secretKey);
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

            // req.setImageA(imageAUrl);
            // req.setImageB(imageBUrl);
            req.setUrlA(imageAUrl);
            req.setUrlB(imageBUrl);
            // 返回的resp是一个CompareFaceResponse的实例，与请求对象对应
            CompareFaceResponse resp = client.CompareFace(req);
            // 输出json格式的字符串回包
            System.out.println(CompareFaceResponse.toJsonString(resp));
            JSONObject jsonObject = JSON.parseObject(CompareFaceResponse.toJsonString(resp));
            String score = jsonObject.get("Score").toString();
            double v = Double.parseDouble(score);
            if (v > 60) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
