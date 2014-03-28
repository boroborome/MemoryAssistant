/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:窗口信息</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-25
 */
package com.boroborome.footstone.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.boroborome.footstone.xml.IXmlObject;
import com.boroborome.footstone.xml.XmlHelper;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:窗口信息,用于保存窗口大小未知信息</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-25
 * @modify        2008-03-09 BoRoBoRoMe 原来保存和写入的代码反了，纠正了过来
 * @modify        2008-04-11 BoRoBoRoMe 增加了窗口额外信息的读写方法
 */
public class WindowInfo implements IXmlObject
{
    /**
     * 建议的节点名称
     */
    public static final String NodeName = "WindowInfo"; //$NON-NLS-1$
    
    /**
     * 位置属性的X
     */
    public static final String Location_X = "Location.X"; //$NON-NLS-1$
    
    /**
     * 位置属性的Y
     */
    public static final String Location_Y = "Location.Y"; //$NON-NLS-1$
    
    /**
     * 大小属性的宽度
     */
    public static final String Size_Width = "Size.Width"; //$NON-NLS-1$
    
    /**
     * 大小属性的高度
     */
    public static final String Size_Height = "Size.Height"; //$NON-NLS-1$
    
    /**
     * 附加内容列表节点
     */
    public static final String MapValueListNode = "MapValueList"; //$NON-NLS-1$
    
    /**
     * 附加内容节点
     */
    public static final String MapValueNode = "MapValue"; //$NON-NLS-1$
    
    /**
     * 映射使用键的属性名称
     */
    public static final String MapKeyAttribute = "key"; //$NON-NLS-1$
    
    /**
     * 映射对应值的属性名称
     */
    public static final String MapValueAttribute = "value"; //$NON-NLS-1$
    
    /*
     * 窗口位置
     */
    private Point location;
    
    /*
     * 窗口大小
     */
    private Dimension size;
    
    /*
     * 映射各种某个窗口特有的值
     * String -> String
     */
    private Map<String, String> mapValue;
    
    /**
     * 构造函数
     */
    public WindowInfo()
    {
        location = new Point();
        size = new Dimension();
        mapValue = new HashMap<String, String>();
    }

    /**
     * 读取窗口信息，一般为JFrame,Dialog,JDialog或者JInternalFrame
     * @param window 用于读取信息的窗口
     */
    public void readnfo(final Component window)
    {
        location = window.getLocation();
        size = window.getSize();
    }
    
    /**
     * 恢复信息，一般为JFrame,Dialog,JDialog或者JInternalFrame
     * @param window 等待恢复信息的窗口
     */
    public void resumeInfo(final Component window)
    {
        window.setSize(size);
        window.setLocation(location);
        window.validate();
    }
    
    /* (non-Javadoc)
     * @see com.boroborome.common.xml.IXmlObject#loadInfo(org.dom4j.Element)
     */
    @SuppressWarnings("unchecked")
	@Override
    public void loadInfo(final Element element)
    {
        location.x = (int) XmlHelper.loadNumAttribute(element, Location_X);
        location.y = (int) XmlHelper.loadNumAttribute(element, Location_Y);
        size.width = (int) XmlHelper.loadNumAttribute(element, Size_Width);
        size.height = (int) XmlHelper.loadNumAttribute(element, Size_Height);
        
        mapValue.clear();
        Element mapListElement = element.element(MapValueListNode);
        if (mapListElement != null)
        {
            List lstMap = mapListElement.elements();
            for (int i = 0, l = lstMap.size(); i < l; i++)
            {
                Element e = (Element) lstMap.get(i);
                String key = XmlHelper.loadStrAttribute(e, MapKeyAttribute);
                String value = XmlHelper.loadStrAttribute(e, MapValueAttribute);
                mapValue.put(key, value);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.xml.IXmlObject#saveInfo(org.dom4j.Element)
     */
    @Override
    public void saveInfo(final Element element)
    {
        XmlHelper.saveNumAttribute(element, Location_X, location.x);
        XmlHelper.saveNumAttribute(element, Location_Y, location.y);
        XmlHelper.saveNumAttribute(element, Size_Width, size.width);
        XmlHelper.saveNumAttribute(element, Size_Height, size.height);
        
        if (!mapValue.isEmpty())
        {
            Element mapListElement = element.addElement(MapValueListNode);
            for (String key : mapValue.keySet())
            {
            	Element e = mapListElement.addElement(MapValueNode);
                XmlHelper.saveStrAttribute(e, MapKeyAttribute, key);
                XmlHelper.saveStrAttribute(e, MapValueAttribute, mapValue.get(key));
            }
        }
    }

    /**
     * 获取窗口位置
     * @return 窗口位置
     */
    public Point getLocation()
    {
        return location;
    }

    /**
     * 保存一个值
     * @param key 对应的键值
     * @param value 对应的值
     */
    public void setInfo(final String key, final String value)
    {
        if (value == null)
        {
            mapValue.remove(key);
        }
        else
        {
            mapValue.put(key, value);
        }
    }
    
    /**
     * 保存一个值
     * @param key 对应的键值
     * @param value 对应的值
     */
    public void setInfo(final String key, final int value)
    {
        mapValue.put(key, Integer.toString(value));
    }
    
    /**
     * 获取字符串值
     * @param key 对应的键值
     * @param defaultValue 默认值
     * @return 对应的值的字符串
     */
    public String getStr(final String key, final String defaultValue)
    {
        String v = mapValue.get(key);
        if (v == null)
        {
            v = defaultValue;
        }
        return v;
    }
    
    /**
     * 获取数字值
     * @param key 对应的键值
     * @param defaultValue 默认值
     * @return 对应的值的数字值
     */
    public int getInt(final String key, final int defaultValue)
    {
        int i = defaultValue;
        String s = mapValue.get(key);
        if (s != null)
        {
            i = Integer.parseInt(s);
        }
        return i;
    }
    
    /**
     * 设置窗口位置
     * @param location 窗口位置
     */
    public void setLocation(final Point location)
    {
        this.location = location;
    }

    /**
     * 获取窗口大小
     * @return 窗口大小
     */
    public Dimension getSize()
    {
        return size;
    }

    /**
     * 设置窗口大小
     * @param size 窗口大小
     */
    public void setSize(final Dimension size)
    {
        this.size = size;
    }

}
