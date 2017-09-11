/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-1
 */
package com.happy3w.footstone.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.res.ResConst;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>负责提供基本常用数据类型的转换</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-1
 */
public class ConvertUtil
{
    /**
     * 将时间戳转换为字符串的时间，包括秒
     * @param time
     * @return
     */
    public static String timeToStr(long time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time)); 
    }
    
    /**
     * 将字符串的时间转换为时间戳<br>
     * 允许仅仅输入日期，或者时间，如果只有日期，则事件为0:0:0;如果只有时间，则默认为今天
     * @param str
     * @return
     * @throws MessageException 时间格式不是yyyy-MM-dd HH:mm:ss，或者输入的为空
     */
    public static long strToTime(String str) throws MessageException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str != null && !str.isEmpty())
        {
            //仅仅有日期或者事件
            if (str.indexOf(' ') < 0)
            {
                //如果仅仅有日期
                if (str.indexOf('-') > 0)
                {
                    str = str + " 00:00:00";
                }
                else //仅仅有时间
                {
                    String curTime = timeToStr(System.currentTimeMillis());
                    str = curTime.substring(0, 10) + ' ' + str;
                }
            }
        }
        long result = 0l;
        try
        {
            Date d = sdf.parse(str);
            result = d.getTime(); 
        }
        catch (ParseException e)
        {
            throw new MessageException(ResConst.ResKey, ResConst.ConvertTimeFailed, new Object[]{str}, e);
        } 
        return result;
    }
    
    /**
     * 将时间戳转换为字符串型时间
     * @param time 形如YYYY-MM-dd hh:mm:ss
     * @return
     */
    public static String timeSpanToStr(long time)
    {
        StringBuilder sb = new StringBuilder();
        long remainTime = writeTime(sb, time, "-", DateSpanValue);
        if ("0-0-0".equals(sb.toString()))
        {
            sb.setLength(0);
        }
        sb.append(' ');
        writeTime(sb, remainTime, ":", TimeSpanValue);
        return sb.toString();
    }
    
    /**
     * 将time信息填写到buffer中
     * @param buffer 保存结果
     * @param time 当前毫秒数
     * @param split 分隔符，日期使用-，时间使用：
     * @param values 权值列表
     * @return 转换后剩余的时间
     */
    private static long writeTime(StringBuilder buffer, long time, String split, long[] values)
    {
        long remainTime = time;
        for (int i = 0; i < values.length; i++)
        {
            long v = remainTime / values[i];
            buffer.append(String.valueOf(v)).append(split);
            
            remainTime = remainTime % values[i];
        }
        buffer.setLength(buffer.length() - split.length());
        return remainTime;
    }

    /**
     * 将时间段转换为毫秒
     * @param str 格式为 YYYY-MM-dd hh:mm:ss.SSS
     * @return null或者空字符串表示0
     * @throws MessageException
     */
    public static long strToTimeSpan(String str) throws MessageException
    {
        if (str == null || str.isEmpty())
        {
            return 0;
        }
        
        long result = 0;
        for (String spanTime : str.split(" "))
        {
            if (spanTime.indexOf('-') > 0)
            {
                result += toTimeSpan(spanTime, "-" , DateSpanValue);
            }
            else
            {
                result += toTimeSpan(spanTime, ":" , TimeSpanValue);
            }
        }
        return result;
    }
    
    /**
     * 在hh:mm:ss中各段信息的权重
     */
    private static final long[] TimeSpanValue = new long[]{60*60*1000, 60*1000, 1000};
    
    /**
     * 在hh:mm:ss中各段信息的权重
     */
    private static final long[] DateSpanValue = new long[]{12l * 30 * 24 * TimeSpanValue[0], 30l * 24 * TimeSpanValue[0], 24l * TimeSpanValue[0]};

    /**
     * 将YYYY-MM-dd或者hh:mm:ss转换为毫秒的时间
     * @param spanTime
     * @param split 信息分割付，当前只有-和:两种，一个出现在日期上，一个出现在时间上
     * @param values 分隔符隔开的各段信息的权重
     * @return
     */
    private static long toTimeSpan(String spanTime, String split, long[] values)
    {
        if (spanTime == null || spanTime.isEmpty())
        {
            return 0;
        }
        long result = 0;
        String[] items = spanTime.split(split);
        for (int i = 0; i < items.length && i < values.length; i++)
        {
            result += Integer.parseInt(items[i]) * values[i];
        }
        return result;
    }
}
