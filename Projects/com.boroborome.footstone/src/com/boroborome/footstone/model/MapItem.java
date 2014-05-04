/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-8
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.model;

import com.boroborome.footstone.dataenergy.SerialAttribute;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>Map映射条目</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>在从XML文件读取Map信息时会使用这个结构表示key和value之间的关系。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-8
 */
public class MapItem<K,V>
{
    private K key;
    private V value;
    
    /**
     * 构造函数
     */
    public MapItem()
    {
        super();
    }
    /**
     * 构造函数
     * @param key
     * @param value
     */
    public MapItem(K key, V value)
    {
        super();
        this.key = key;
        this.value = value;
    }
    /**
     * 获取key
     * @return key
     */
    @SerialAttribute()
    public K getKey()
    {
        return key;
    }
    /**
     * 设置key
     * @param key key
     */
    public void setKey(K key)
    {
        this.key = key;
    }
    /**
     * 获取value
     * @return value
     */
    @SerialAttribute()
    public V getValue()
    {
        return value;
    }
    /**
     * 设置value
     * @param value value
     */
    public void setValue(V value)
    {
        this.value = value;
    }
    
    
}
