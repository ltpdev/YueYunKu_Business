package com.gdcp.yueyunku_business.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yls on 2016/12/29.
 */

public class StringUtils {
    private static final String REGEX_PASSWORD="^[0-9]{3,20}$";
    private static final String REGEX_PHONE_NUMBER="^((13[0-9])|(14[5,9])|(15[^4,\\D])|(17[0,3,5-8])|(18[0,5-9]))\\d{8}$";
    public static boolean isVailedPassword(String password) {
        return password.matches(REGEX_PASSWORD);
    }
    public static boolean isPhoneNumber(String number) {
        Pattern p = Pattern.compile(REGEX_PHONE_NUMBER);
        Matcher m = p.matcher(number);
        return m.matches();
    }

}
