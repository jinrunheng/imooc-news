package com.imooc.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @Author Dooby Kim
 * @Date 2021/12/20 9:21 下午
 * @Version 1.0
 */
class SMSUtilsTest {

    @Test
    void sendSMS() {
        SMSUtils smsUtils = new SMSUtils();
        smsUtils.sendSMS("15526787357", "1234");
    }

    @Test
    void generateSMSCode() {
        SMSUtils smsUtils = new SMSUtils();
        String code = smsUtils.generateSMSCode();
        System.out.println(code);
        Assertions.assertEquals(code.length(), 4);
    }
}