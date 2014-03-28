package com.boroborome.footstone.util;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class ConvertUtilTest
{

    @Test
    public void testTimeSpanToStr()
    {
        long second = 1000;
        long min = 60 * second;
        long hour = 60 * min;
        long day = 24 * hour;
        long month = 30 * day;
        long year = 12 * month;
        Assert.assertEquals("带有日期和时间", "1-1-1 1:1:1", ConvertUtil.timeSpanToStr(year + month + day + hour + min + second));
    }

}
