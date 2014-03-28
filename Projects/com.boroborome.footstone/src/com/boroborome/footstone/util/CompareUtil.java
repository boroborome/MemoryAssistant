/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:比较工具</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 */
package com.boroborome.footstone.util;


/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:比较工具,提供各种比较用的常用方法</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 */
public class CompareUtil
{

    /**
     * 构造函数
     */
    private CompareUtil()
    {
    	//Nothing to initalize
    }
    
    /**
     * 比较两个对象是否相等
     * @param value1 对象1
     * @param value2 对象2
     * @return 使用equals方法比较的结果
     */
    public static boolean isEqual(final Object value1, final Object value2)
    {
        boolean equal = value1 == value2;
        if (!equal)
        {
            if (value1 != null)
            {
                equal = value1.equals(value2);
            }
        }
        return equal;
    }
    
    /**
     * 比较两个对象是否相等，此方法直接获取对应属性进行比较，如果属性为列表，则遍历列表
     * 如果获取属性失败则认为不相等
     * @param value1 对象1
     * @param value2 对象2
     * @return 比较结果
     */
    public static boolean isDeepEqual(final Object value1, final Object value2)
    {
        return new CompareKit().deepCompare(value1, value2);
    }
}
