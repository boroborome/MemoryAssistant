/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:简单数据结构</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 */
package com.happy3w.footstone.model;

import org.dom4j.Element;

import com.happy3w.footstone.util.CompareUtil;
import com.happy3w.footstone.xml.IXmlObject;
import com.happy3w.footstone.xml.XmlException;
import com.happy3w.footstone.xml.XmlHelper;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:简单数据结构
 *         用于保存各种简单的数据之间对应关系，也可以用于当作枚举类型
 * </P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 * @modify        BoRoBoRoMe 2008-03-07 增加IXmlObject接口
 */
public class ValueMap implements IXmlObject, Cloneable
{
    /**
     * 建议的节点名称
     */
    public static final String NodeName = "ValueMap"; //$NON-NLS-1$
    
    /**
     * 名称属性的名称
     */
    public static final String NameAttribute = "name"; //$NON-NLS-1$
    
    /**
     * 描述属性的名称
     */
    public static final String DescAttribute = "desc"; //$NON-NLS-1$
    
    /**
     * 编码属性的名称
     */
    public static final String CodeAttribute = "code"; //$NON-NLS-1$
    
    /**
     * 名称
     */
    protected String name;
    
    /**
     * 描述
     */
    protected String desc;
    
    /**
     * 编码
     */
    protected int code;
    
    /**
     * 构造函数
     */
    public ValueMap()
    {
        //Nothing to initalize
    }

    /**
     * 构造函数
     * @param name 名称
     * @param desc 描述
     * @param code 编码
     */
    public ValueMap(final String name, final String desc, final int code)
    {
        this.name = name;
        this.desc = desc;
        this.code = code;
    }

    /**
     * 构造函数
     * @param name 名称
     * @param code 编码
     */
    public ValueMap(final String name, final int code)
    {
        this.name = name;
        this.code = code;
    }

    /**
     * 构造函数
     * @param name 名称
     * @param desc 描述
     */
    public ValueMap(final String name, final String desc)
    {
        this.name = name;
        this.desc = desc;
    }

    /**
     * 获取编码
     * @return 编码
     */
    public int getCode()
    {
        return code;
    }

    /**
     * 设置编码
     * @param code 编码
     */
    public void setCode(final int code)
    {
        this.code = code;
    }

    /**
     * 获取描述
     * @return 描述
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * 设置描述
     * @param desc 描述
     */
    public void setDesc(final String desc)
    {
        this.desc = desc;
    }

    /**
     * 获取名称
     * @return 名称
     */
    public String getName()
    {
        return name;
    }

    /**
     * 设置名称
     * @param name 名称
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.xml.IXmlObject#loadInfo(org.dom4j.Element)
     */
    public void loadInfo(final Element element) throws XmlException
    {
        this.name = XmlHelper.loadStrAttribute(element, NameAttribute);
        this.desc = XmlHelper.loadStrAttribute(element, DescAttribute);
        this.code = (int) XmlHelper.loadNumAttribute(element, CodeAttribute);
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.xml.IXmlObject#saveInfo(org.dom4j.Element)
     */
    public void saveInfo(final Element element)
    {
        XmlHelper.saveStrAttribute(element, NameAttribute, name);
        XmlHelper.saveStrAttribute(element, DescAttribute, desc);
        XmlHelper.saveNumAttribute(element, CodeAttribute, code);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return desc;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        ValueMap vm = (ValueMap) super.clone();
        vm.code = code;
        vm.name = name;
        vm.desc = desc;
        return vm;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean eq = obj == this;
        if (!eq && obj instanceof ValueMap)
        {
            ValueMap vm = (ValueMap) obj;
            eq = vm.code == code && CompareUtil.isEqual(vm.name, name) && CompareUtil.isEqual(vm.desc, desc);
        }
        return eq;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int c = code;
        if (name != null)
        {
            c = c<<8 + name.hashCode();
        }
        if (desc != null)
        {
            c = c<<8 + desc.hashCode();
        }
        return c;
    }

    /**
     * 在列表中查找具有给定名称的条目
     * @param items 需要查找的列表
     * @param name 名称
     * @return 条目所在的位置，小于0的位置说明没有找到
     */
    public static int searchByName(final ValueMap[] items, final String name)
    {
        int index = -1;
        if (items != null)
        {
            index = items.length - 1;
            for (; index >= 0; index--)
            {
                if (CompareUtil.isEqual(items[index].name, name))
                {
                    break;
                }
            }
        }
        
        return index;
    }
    
    /**
     * 在列表中查找具有给定描述的条目
     * @param items 需要查找的列表
     * @param desc 描述
     * @return 条目所在的位置，小于0的位置说明没有找到
     */
    public static int searchByDesc(final ValueMap[] items, final String desc)
    {
        int index = -1;
        if (items != null)
        {
            index = items.length - 1;
            for (; index >= 0; index--)
            {
                if (CompareUtil.isEqual(items[index].desc, desc))
                {
                    break;
                }
            }
        }
        
        return index;
    }
    
    
    /**
     * 在列表中查找具有给定编码的条目
     * @param items 需要查找的列表
     * @param code 编码
     * @return 条目所在的位置，小于0的位置说明没有找到
     */
    public static int searchByCode(final ValueMap[] items, final int code)
    {
        int index = -1;
        if (items != null)
        {
            index = items.length - 1;
            for (; index >= 0; index--)
            {
                if (items[index].code == code)
                {
                    break;
                }
            }
        }
        
        return index;
    }

}
