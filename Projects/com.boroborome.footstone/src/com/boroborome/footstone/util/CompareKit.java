/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-20
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.boroborome.footstone.xml.XmlEnergyUtil;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>比较工具</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>负责完成深度比较工作</DD>
 *    
 * 内部所有方法，如果返回值是Boolean，那么，如果返回内容是true表示已经可以确定当前检测记录是相等的；false表示已经可以确定当前记录检测结果不同，当让整个结果也一定不同；null：检测结果不确定，还需要进行进一步的检查。
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-20
 */
public class CompareKit
{
    private Stack<CompareRecord> stack = new Stack<CompareRecord>();
    private List<CompareRecord> lstUsed = new ArrayList<CompareRecord>();
    
    /**
     * 将一组比较对象添加到比较栈中，添加前会对数据进行基本的相等检测
     * @param value1 值1
     * @param value2 值2
     * @return 基本检测结果
     */
    private Boolean addRecord(final Object value1, final Object value2)
    {
        Boolean result = doEasyCompare(value1, value2);
        
        //如果简单比较没有结果
        if (result == null)
        {
            CompareRecord compareRecord = new CompareRecord(value1, value2);
            if (!lstUsed.contains(compareRecord))
            {
                result = initCompareRecord(compareRecord);
                if (result == null)
                {
                    stack.push(compareRecord);
                    lstUsed.add(compareRecord);
                }
            }
        }
        
        //如果相等，那么忽略这个属性，继续比较其他的内容，所以不允许返回true
        return result == Boolean.TRUE ? null : result;
    }
    
    /**
     * 深度比较
     * @param value1 比较对象
     * @param value2 被比较对象
     * @return 比较结果
     */
    public boolean deepCompare(final Object value1, final Object value2)
    {
        stack.clear();
        lstUsed.clear();
        
        //如果简单比较他们就是相等的，那么不会向栈中存数据，结果直接返回true。
        Boolean firstResult = addRecord(value1, value2);
        if (firstResult != null)
        {
            return firstResult.booleanValue();
        }
        
        while (!stack.isEmpty())
        {
            CompareRecord compareRecord = stack.lastElement();
            
            Boolean result = doCompareRecord(compareRecord);
            
            if (result == Boolean.TRUE)
            {
                stack.pop();
            }
            else if (result == Boolean.FALSE)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 简单比较。仅仅比较是否同一个对象；类型是否相同；简单类型才比较是否相等。
     * @param value1 比较值
     * @param value2 被比较值
     * @return 比较结果
     */
    private Boolean doEasyCompare(final Object value1, final Object value2)
    {
        //简单判断，保证后续比较不是同一个对象，也不为null
        if (value1 == value2)
        {
            return Boolean.TRUE;
        }
        if (value1 == null || value2 == null
                || (!value1.getClass().isArray() && value1.getClass() != value2.getClass()))
        {
            return Boolean.FALSE;
        }
        
        //如果为简单对象，则直接使用equal方法比较结果
        if (XmlEnergyUtil.isSimpleType(value1.getClass()))
        {
            return Boolean.valueOf(value1.equals(value2));
        }
        return null;
    }
    
    /**
     * 初始化比较记录
     * @param compareRecord 比较记录
     * @return 比较结果
     */
    @SuppressWarnings("unchecked")
    private Boolean initCompareRecord(final CompareRecord compareRecord)
    {
        //初始化属性比较数据
        List<Method> lstAttrMethod = new ArrayList<Method>();
        collectAttributeMethods(compareRecord.record1.value, lstAttrMethod);
        compareRecord.itAttribute = lstAttrMethod.iterator();
        
        //如果比较的对象是Array，则它的属性列表无用
        if (compareRecord.record1.value.getClass().isArray())
        {
            Object[] arr1 = (Object[]) compareRecord.record1.value;
            Object[] arr2 = (Object[]) compareRecord.record2.value;
            
            if (arr1.length != arr2.length)
            {
                return Boolean.FALSE;
            }
            compareRecord.record1.itItem = Arrays.asList(arr1).iterator();
            compareRecord.record2.itItem = Arrays.asList(arr2).iterator();
        }
        else if (compareRecord.record1.value instanceof List)
        {
            List lst1 = (List) compareRecord.record1.value;
            List lst2 = (List) compareRecord.record2.value;
            
            if (lst1.size() != lst2.size())
            {
                return Boolean.FALSE;
            }
            
            compareRecord.record1.itItem = lst1.iterator();
            compareRecord.record2.itItem = lst2.iterator();
        }
        else if (compareRecord.record1.value instanceof Map)
        {
            Map map1 = (Map) compareRecord.record1.value;
            Map map2 = (Map) compareRecord.record2.value;
            
            if (map1.size() != map2.size())
            {
                return Boolean.FALSE;
            }
            
            compareRecord.record1.itItem = new MapIterator(map1);
            compareRecord.record2.itItem = new MapIterator(map2);
        }
        return null;
    }
    
    /**
     * 比较当前记录中的属性
     * @param compareRecord 比较记录
     * @return 比较结果
     */
    private Boolean doCompareAttribute(final CompareRecord compareRecord)
    {
        Boolean result = null;
        Method getMethod = compareRecord.itAttribute.next();
        try
        {
            result = addRecord(getMethod.invoke(compareRecord.record1.value),
                    getMethod.invoke(compareRecord.record2.value));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 比较列表信息
     * @param compareRecord 比较记录
     * @return 比较结果
     */
    private Boolean doCompareListItem(final CompareRecord compareRecord)
    {
        return addRecord(compareRecord.record1.itItem.next(),
                compareRecord.record2.itItem.next());
    }
    
    /**
     * 对栈中记录进行一次简单的比较
     * @param compareRecord 比较记录
     * @return 比较结果
     */
    private Boolean doCompareRecord(final CompareRecord compareRecord)
    {
        Boolean result = null;
        if (compareRecord.itAttribute == null)
        {
            result = initCompareRecord(compareRecord);
        }
        else if (compareRecord.itAttribute.hasNext())
        {
            result = doCompareAttribute(compareRecord);
        }
        else if (compareRecord.record1.itItem != null && compareRecord.record1.itItem.hasNext())
        {
            result = doCompareListItem(compareRecord);
        }
        else
        {
            //如果以上条件都不满足，则说明这个对象比较是相等的，需要看下一个了
            result = Boolean.TRUE;
        }
        return result;
    }
    
    /**
     * 将对象中所有读属性的方法保存到lst中
     * @param v 对象
     * @param lst 属性列表
     */
    @SuppressWarnings("nls")
    private static void collectAttributeMethods(final Object v, final List<Method> lst)
    {
        lst.clear();
        //开始比较
        Method[] methods = v.getClass().getMethods();
        
        for (int i = 0; i < methods.length; i++)
        {
            Method m = methods[i];
            //不是获取属性的方法，则继续
            if (m.getParameterTypes().length == 0 &&
                    (m.getName().startsWith("get") || m.getName().startsWith("is"))
                    && !m.getName().equals("getClass"))
            {
                lst.add(m);
            }
        }
    }

    /**
     * <DT><B>Title:</B></DT>
     *    <DD>比较对象的状态记录</DD>
     * <DT><B>Description:</B></DT>
     *    <DD>在一个对象进行比较时用于保存这个对象的临时信息</DD>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2010-3-22
     */
    private static class ValueRecord
    {
        public Object value;
        public Iterator<?> itItem;
        /**
         * 构造函数
         * @param value
         */
        public ValueRecord(final Object value)
        {
            super();
            this.value = value;
        }
        
    }
    /**
     * <DT><B>Title:</B></DT>
     *    <DD>信息比较记录</DD>
     * <DT><B>Description:</B></DT>
     *    <DD>比较过程中保存一对比较信息的临时数据</DD>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2010-3-22
     */
    private static class CompareRecord
    {
        public ValueRecord record1;
        public ValueRecord record2;
        public Iterator<Method> itAttribute;
        /**
         * 构造函数
         */
        public CompareRecord(final Object v1, final Object v2)
        {
            super();
            record1 = new ValueRecord(v1);
            record2 = new ValueRecord(v2);
        }
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj)
        {
            boolean equal = false;
            if (obj instanceof CompareRecord)
            {
                CompareRecord p = (CompareRecord) obj;
                equal = (record1.value == p.record1.value
                        && record2.value == p.record2.value);
            }
            return equal;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            int v = 0;
            if (record1.value != null)
            {
                v = record1.value.hashCode();
            }
            return v;
        }
    }
}
