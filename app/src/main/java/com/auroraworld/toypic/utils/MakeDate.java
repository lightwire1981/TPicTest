package com.auroraworld.toypic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MakeDate {

    public enum DATE_TYPE {
        BIRTH,
        ID
    }
    public static String makeDateString(DATE_TYPE type) {
        String form;
        switch (type) {
            case BIRTH:
                form = "yyyy-MM-dd";
                break;
            case ID:
                form = "yyMMddHHmmss";
                break;
            default:
                form = "yyyy-MM-dd HH:mm:ss";
                break;
        }
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(form, Locale.KOREA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(now);
    }
}
