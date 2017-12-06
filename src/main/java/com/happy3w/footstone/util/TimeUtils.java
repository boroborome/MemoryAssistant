package com.happy3w.footstone.util;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
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

    public static TimeZone calcTimezoneByHours(double timeZoneOffsetHours) {
        if (timeZoneOffsetHours > 13 || timeZoneOffsetHours < -12) {
            return null;
        }

        String[] ids = TimeZone.getAvailableIDs((int) (timeZoneOffsetHours * 60 * 60 * 1000));
        if (ids.length == 0) {
            // if no ids were returned, something is wrong.
            return null;
        } else {
            return new SimpleTimeZone((int) (timeZoneOffsetHours * 60 * 60 * 1000), ids[0]);
        }
    }

    public static DateTime parse(String stringDate) {
        String adjustDate = stringDate.contains(".0") ? stringDate : stringDate.replace("Z", ".0Z");
        return ISODateTimeFormat.dateTime().parseDateTime(adjustDate);
    }

    public static String formatIso(Date time) {
        return String.valueOf(new DateTime(time));
    }
}
