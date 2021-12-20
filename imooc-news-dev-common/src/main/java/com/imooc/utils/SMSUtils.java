package com.imooc.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.*;
import com.imooc.resource.aliyun.AliyunResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2021/12/20 9:10 下午
 * @Version 1.0
 */
@Component
public class SMSUtils {


    @Resource
    public AliyunResource aliyunResource;

    public void sendSMS() throws Exception {
        Client client = SMSUtils.createClient(aliyunResource);

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers("15526787357");
        client.sendSms(sendSmsRequest);
    }

    private static Client createClient(AliyunResource resource) throws Exception {
        Config config = new Config()
                .setAccessKeyId(resource.getAccessKeyId())
                .setAccessKeySecret(resource.getAccessKeySecret());

        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
}
