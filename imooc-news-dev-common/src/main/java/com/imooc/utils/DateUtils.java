package com.imooc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2021/12/26 11:55 下午
 * @Version 1.0
 * @Description 日期工具类，用于字符串与日期进行相互转化
 */

public class DateUtils {

    public static Date string2Date(String dateString, String format, boolean lenient) {
        if (dateString == null) {
            return null;
        }
        DateFormat df = null;

        try {

            if (format == null) {
                df = new SimpleDateFormat();
            } else {
                df = new SimpleDateFormat(format);
            }

            // setLenient avoids allowing dates like 9/32/2001
            // which would otherwise parse to 10/2/2001
            df.setLenient(false);

            return df.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date string2Date(String dateString, String format) {
        return string2Date(dateString, format, false);
    }

    public static Date string2Date(String dateString) {
        return string2Date(dateString, "yyyy-MM-dd");
    }

    public static String date2String(Date date, String pattern) {

        if (date == null) {
            return null;
        }

        try {
            SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
            sfDate.setLenient(false);
            return sfDate.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String date2String(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }
}
