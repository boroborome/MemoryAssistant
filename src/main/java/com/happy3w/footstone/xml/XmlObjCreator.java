/*
 * <P>Title:      Common V1.0</P>
 * <P>Description:Xml对象构造器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-19
 */
package com.happy3w.footstone.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
 * <P>Title:      Common V1.0</P>
 * <P>Description:Xml对象构造器
 *      在这里注册的对象，都可以通过它进行序列化和反序列化</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-19
 */
public class XmlObjCreator<XmlObjType extends IXmlObject>
{
    /**
     * 默认保存类型的属性名称
     */
    public static final String DefaltClassAttribute = "_cn_"; //$NON-NLS-1$
    
    /*
     * 保存类型的属性名称
     */
    private String classAttribute = DefaltClassAttribute;
    
    /*
     * 类型注册表
     * (Entry)
     */
    private List<Entry> lstTypeMap;

    /**
     * 构造函数
     */
    public XmlObjCreator()
    {
        super();
        lstTypeMap = new ArrayList<Entry>();
    }

    /**
     * 获取类型属性名称
     * @return 类型属性名称
     */
    public String getClassAttribute()
    {
        return classAttribute;
    }

    /**
     * 设置类型属性名称
     * @param classAttribute 类型属性名称
     */
    public void setClassAttribute(final String classAttribute)
    {
        this.classAttribute = classAttribute;
    }
    
    private int indexOf(String name)
    {
        int index = lstTypeMap.size() - 1;
        for (; index >= 0; index--)
        {
            Entry e = lstTypeMap.get(index);
            if (e.name.equals(name))
            {
                break;
            }
        }
        return index;
    }
    
    private int indexOf(final Class<? extends IXmlObject> type)
    {
        int index = lstTypeMap.size() - 1;
        for (; index >= 0; index--)
        {
            Entry e = lstTypeMap.get(index);
            if (e.type == type)
            {
                break;
            }
        }
        return index;
    }
    
    /**
     * 注册类型,如果type为null,则取消注册
     * @param name 类型名称
     * @param type 类型
     */
    public void reg(final String name, final Class<? extends XmlObjType> type)
    {
        //查找列表是否已经存在，如果存在则替换，如果不存在则添加
        int index = indexOf(name);
        
        if (index >= 0)
        {
            if (type == null)
            {
                lstTypeMap.remove(index);
            }
            else
            {
                lstTypeMap.get(index).type = type;
            }
        }
        else if (type != null && name != null)
        {
            Entry e = new Entry();
            e.name = name;
            e.type = type;
            lstTypeMap.add(e);
        }
    }
    
    /**
     * 获取已经注册的类型
     * @param name 类型名称
     * @return 类型
     */
    public Class<? extends XmlObjType> findType(final String name)
    {
        Class<? extends XmlObjType> c = null;
        int index = indexOf(name);
        if (index >= 0)
        {
            c = lstTypeMap.get(index).type;
        }
        
        return c;
    }
    
    /**
     * 按照类型找名称
     * @param type 类型
     * @return 对应名称，没有找到返回null
     */
    public String findName(final Class<? extends IXmlObject> type)
    {
        String n = null;
        int index = indexOf(type);
        if (index >= 0)
        {
            n = lstTypeMap.get(index).name;
        }
        return n;
    }

    /**
     * 注册的名称列表
     * @return 注册的名称列表
     */
    public String[] keyArray()
    {
        List<String> lst = new ArrayList<String>();
        for (int i = 0, l = lstTypeMap.size(); i < l; i++)
        {
            lst.add(lstTypeMap.get(i).name);
        }
        return lst.toArray(new String[lst.size()]);
    }
    
    @SuppressWarnings("unchecked")
	public void loadList(final Element lstElement, List<XmlObjType> lst)
    {
        lst.clear();
        if (lstElement != null)
        {
            Iterator it = lstElement.elements().iterator();
            while (it.hasNext())
            {
                Element e = (Element) it.next();
                XmlObjType obj = load(e);
                lst.add(obj);
            }
        }
    }
    
    public void saveList(final Element lstElement, List<XmlObjType> lst)
    {
        XmlHelper.clearElement(lstElement, null);
        if (lst != null)
        {
            for (XmlObjType obj : lst)
            {
                Element e = XmlHelper.createElement(lstElement, this.findName(obj.getClass()));
                save(e, obj);
            }
        }
    }
    
    /**
     * 通过Xml节点，得到一个类型对象
     * @param element 节点
     * @param nodeName 节点名称
     * @return 类型对象
     * @throws XmlException 可能的Xml异常
     */
    @SuppressWarnings("unchecked")
	public XmlObjType load(final Element element, final String nodeName) throws XmlException
    {
        XmlObjType type = null;
        
        List list = element.elements(nodeName);
        if (list != null && list.size() > 0)
        {
            Element e = (Element) list.get(0);
            type = load(e);
        }
        
        return type;
    }
    
    /**
     * 通过Xml节点，得到一个类型对象
     * @param element 节点
     * @return 类型对象
     * @throws XmlException 可能的Xml异常
     */
    public XmlObjType load(final Element element) throws XmlException
    {
        XmlObjType type = null;
        String clazzName = null;
        if (classAttribute != null && classAttribute.length() > 0)
        {
            clazzName = XmlHelper.loadStrAttribute(element, classAttribute);
        }
        else
        {
            clazzName = XmlHelper.getElementTagName(element);
        }
        
        Class<? extends XmlObjType> c = findType(clazzName);
        try
        {
            type = c.newInstance();
            type.loadInfo(element);
        }
        catch (InstantiationException e1)
        {
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1)
        {
            e1.printStackTrace();
        }
        return type;
    }
    
    /**
     * 将信息保存到Xml节点
     * @param element 节点
     * @param type 类型信息
     */
    public void save(final Element element, final XmlObjType type)
    {
        type.saveInfo(element);
        if (classAttribute != null && classAttribute.length() > 0)
        {
            XmlHelper.saveStrAttribute(element, classAttribute,
                    findName(type.getClass()));
        }
    }
    
    /**
     * <P>Title:      Common V1.0</P>
     * <P>Description:表示类和字符串名称之间的对应关系</P>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2008-4-19
     */
    private class Entry
    {
        private String name;
        private Class<? extends XmlObjType> type;
    }
}
