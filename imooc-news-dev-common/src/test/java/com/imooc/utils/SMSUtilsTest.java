package com.imooc.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Dooby Kim
 * @Date 2021/12/20 9:21 下午
 * @Version 1.0
 */
class SMSUtilsTest {

    @Test
    void sendSMS() {
        SMSUtils smsUtils = new SMSUtils();
        String code = smsUtils.generateSMSCode();
        smsUtils.sendSMS("15526787357", code);
    }

    @Test
    void generateSMSCode() {
        SMSUtils smsUtils = new SMSUtils();
        String code = smsUtils.generateSMSCode();
        Assertions.assertEquals(code.length(), 6);
        Assertions.assertTrue(isNumeric(code));
    }

    /**
     * 正则表达式判断生成的四位验证码是否均为 0-9 范围的数字
     *
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) return false;
        return true;
    }
}