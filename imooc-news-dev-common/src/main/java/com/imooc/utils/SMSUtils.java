package com.imooc.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2021/12/20 9:10 下午
 * @Version 1.0
 */
@Component
@Slf4j
public class SMSUtils {

//     @Resource
//     public TencentResource tencentResource;

    public Map<String, String> sendSMS(String phone, String code) {
        try {
            // 在这里面，我使用 System.getenv() 方法来获取腾讯云的 SECRET_ID 与 SECRET_KEY
            // 因为本项目是一个开源项目，为了避免我的信息上传到 Github 上，所以我将密钥配置到环境变量中，然后使用了获取本地环境变量中的方式
            // 我们也可以使用在配置文件中配置密钥，然后读取配置文件中的值的方式
            String secretId = System.getenv("TENCENT_SECRET_ID");
            String secretKey = System.getenv("TENCENT_SECRET_KEY");
            Credential credential = new Credential(secretId, secretKey);

            // 实例化一个 http 选项，可选，无特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST"); // 默认使用 POST 请求
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            // 实例化一个 Client 选项
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            clientProfile.setDebug(true);
            // 实例化要请求产品(以cvm为例)的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(credential, "ap-guangzhou", clientProfile);

            // 实例化一个请求对象，每个接口都会对应一个 request 对象
            SendSmsRequest request = new SendSmsRequest();
            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            String sdkAppId = "1400612346";
            request.setSmsSdkAppId(sdkAppId);
            String signName = "憨憨二师兄个人公众号";
            request.setSignName(signName);
            String templateId = "1250896";
            request.setTemplateId(templateId);
            String[] phoneNumberSet = {"+86" + phone};
            request.setPhoneNumberSet(phoneNumberSet);
            // 模版内容：验证码为：{1}，您正在登录，若非本人操作，请勿泄露。
            String[] templateParam = {code};
            request.setTemplateParamSet(templateParam);


            // 返回的 response 是一个 SendSmsResponse 实例，与请求对象对应
            SendSmsResponse response = client.SendSms(request);
            SendStatus[] sendStatusSet = response.getSendStatusSet();
            String statusCode = sendStatusSet[0].getCode();
            String msg = sendStatusSet[0].getMessage();
            Map<String, String> result = new HashMap<>();
            result.put("code", statusCode);
            result.put("msg", msg);
            return result;

        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 生成随机的六位验证码，只能是六位数字
     *
     * @return
     */
    public String generateSMSCode() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sb.append((int) (Math.random() * 9 + 1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SMSUtils smsUtils = new SMSUtils();
        smsUtils.sendSMS("15526787357",smsUtils.generateSMSCode());
    }
}
