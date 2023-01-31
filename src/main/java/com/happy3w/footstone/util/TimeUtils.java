package com.happy3w.footstone.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    public static String formatToPattern(Date date, TimeZone timeZone, String pattern) {
        if (null == date) {
            date = new Date();
        }

        if (null == timeZone) {
            timeZone = TimeZone.getDefault();
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }
}
