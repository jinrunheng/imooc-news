package com.imooc.utils;

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
        smsUtils.sendSMS("17695780094", "1234");
    }
}