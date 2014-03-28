/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:Xml辅助工具</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 */
package com.boroborome.footstone.xml;

import java.lang.reflect.Constructor;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.boroborome.footstone.util.CommonRes;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:Xml辅助工具
 * 所有函数都遵守如下规范：<br>
 * 1、所有将信息保存到Xml节点的方法都是用save开头<br>
 * 2、所有从Xml节点读取信息的方法都是用load开头<br>
 * 3、save和load方法的前两个参数一定是Xml节点和字符串，这个Xml节点是需要保存信息将要保存节点的容器节点；字符串是保存信息节点的名字。如下图所示<br>
 * &lt;容器节点名称&gt;
 *      &lt;信息节点名称&gt;
 *          信息内容
 *      &lt;/信息节点名称&gt;
 * &lt;/容器节点名称&gt;
 * 
 * </P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 * @modify        2008-03-16 BoRoBoRoMe 增加函数revisePath，处理相对路径和绝对路径的区分
 * @modify        2008-03-16 BoRoBoRoMe 增加使用Xsl转换Xml的接口
 * @modify        2008-03-18 BoRoBoRoMe 增加序列化和反序列Integer属性的方法
 */
public final class XmlHelper
{
    
    private static final String StrItemNode = "Item"; //$NON-NLS-1$
    
    /**
     * 用于保存字符串数组的节点名称
     */
    public static String StrArrayNode = "StrArray"; //$NON-NLS-1$
 
    /**
     * 清除节点中Tag为elementName的节点
     * @param containerNode 父节点
     * @param infoNodeName 需要清除节点的Tag值
     */
    @SuppressWarnings("unchecked")
	public static void clearElement(final Element containerNode, final String infoNodeName)
    {
        List lstOriElement = null;
        if (infoNodeName == null || infoNodeName.length() <= 0)
        {
            lstOriElement = containerNode.elements();
        }
        else
        {
            lstOriElement = containerNode.elements(infoNodeName);
        }
        for (int i = lstOriElement.size() - 1; i >= 0; i--)
        {
            containerNode.remove((Element) lstOriElement.get(i));
        }
    }
    
    @SuppressWarnings("unchecked")
	public static Element findElement(Element containerNode, String infoNodeName)
    {
        List lst = containerNode.elements();
        Element e = null;
        for (int i = lst.size() - 1; i >= 0; i--)
        {
            Element tempElement = (Element) lst.get(i);
            if (tempElement.getQualifiedName().equals(infoNodeName))
            {
                e = tempElement;
                break;
            }
        }
        return e;
    }
    
    public static String getElementTagName(Element element)
    {
        return element.getQualifiedName();
    }
    
    /**
     * 将字符串信息以节点的形式保存到一个Xml节点中
     * @param containerNode 用于保存信息的节点
     * @param infoNodeName 节点名称
     * @param value 需要保存的值，这个值不区分null和空字符串
     */
    public static void saveStrElement(final Element containerNode, final String infoNodeName, final String value)
    {
        //创建新的节点，并添加
        Element valueElement = containerNode.element(infoNodeName);
        if (valueElement == null)
        {
            if (value != null)
            {
                valueElement = containerNode.addElement(infoNodeName);
                valueElement.setText(value);
            }
        }
        else
        {
            if (value != null)
            {
                valueElement.setText(value);
            }
            else
            {
                containerNode.remove(valueElement);
            }
        }
    }
    
    /**
     * 从节点element中找到一个Tag为elementName的节点，并读出其中的字符串值
     * @param containerNode 包含字符串节点的节点
     * @param infoNodeName 字符串节点的名称
     * @return 字符串值，如果没有找到字符串节点，则返回值为null
     */
    @SuppressWarnings("unchecked")
	public static String loadStrElement(final Element containerNode, final String infoNodeName)
    {
        String value = null;
        List lstElement = containerNode.elements(infoNodeName);
        if (lstElement.size() > 0)
        {
            if (lstElement.get(0) instanceof Element)
            {
                value = ((Element) lstElement.get(0)).getText();
            }
        }
        return value;
    }
    
    /**
     * 保存字符串列表
     * @param containerNode 包含字符串节点的节点
     * @param infoNodeName 字符串节点的名称
     * @param values 需要保存的字符串列表
     */
    public static void saveStrArray(final Element containerNode, final String infoNodeName, final String[] values)
    {
        Element lstElement = containerNode.element(infoNodeName);
        if (lstElement == null)
        {
            lstElement = containerNode.addElement(infoNodeName);
        }
        saveStrArray(lstElement, values);
    }
    
    /**
     * 保存字符串列表
     * @param infoNode 信息会保存在本节点
     * @param values 需要保存的字符串列表
     */
    private static void saveStrArray(final Element infoNode, final String[] values)
    {
        clearElement(infoNode, StrItemNode);
        for (int i = 0; i < values.length; i++)
        {
            Element e = infoNode.addElement(StrItemNode);
            String v = values[i];
            if (v == null)
            {
                v = ""; //$NON-NLS-1$
            }
            e.setText(v);
        }
    }
    
    /**
     * 读取字符串列表
     * @param containerNode 包含字符串节点的节点
     * @param infoNodeName 字符串节点的名称
     * @return 字符串列表
     */
    public static String[] loadStrArray(final Element containerNode, final String infoNodeName)
    {
        Element lstElement = containerNode.element(infoNodeName);
        String[] strs = null;
        if (lstElement != null)
        {
            strs = loadStrArray(lstElement);
        }
        return strs;
    }
    
    /**
     * 读取字符串列表
     * @param infoNode 包含字符串节点的节点,包含信息的节点
     * @return 字符串列表
     */
    @SuppressWarnings("unchecked")
	private static String[] loadStrArray(final Element infoNode)
    {
        List lst = infoNode.elements(StrItemNode);
        String[] strs = new String[lst.size()];
        for (int i = 0; i < strs.length; i++)
        {
            Element  e = (Element) lst.get(i);
            strs[i] = e.getText();
        }
        return strs;
    }
    
    /**
     * 将数字信息以节点的形式保存到一个Xml节点中
     * @param containerNode 用于保存信息的节点
     * @param elementName 节点名称
     * @param value 需要保存的值
     */
    public static void saveNumElement(final Element containerNode, final String infoNodeName, final long value)
    {
        saveStrElement(containerNode, infoNodeName, Long.toString(value));
    }
    
    /**
     * 从节点读出一个数字，如果没有节点，或者节点中数字不符合规范，则返回0
     * @param containerNode 含有信息的节点
     * @param infoNodeName 数字节点名称
     * @return 读出的数字
     */
    public static long loadNumElement(final Element containerNode, final String infoNodeName)
    {
        return loadNumElement(containerNode, infoNodeName, 0);
    }
    
    /**
     * 从节点读出一个数字，如果没有节点，或者节点中数字不符合规范，则返回默认值
     * @param containerNode 含有信息的节点
     * @param infoNodeName 数字节点名称
     * @param defaultValue 默认值
     * @return 从文件读出的数值
     */
    public static long loadNumElement(final Element containerNode, final String infoNodeName, final long defaultValue)
    {
        long value = defaultValue;
        try
        {
            value = Long.parseLong(loadStrElement(containerNode, infoNodeName));
        }
        catch (NumberFormatException exp)
        {
            //不处理这个异常
        }
        return value;
    }
    
    /**
     * 将Boolean值信息保存到节点中
     * @param containerNode 用于保存信息的节点
     * @param infoNodeName 节点名称
     * @param value 需要保存的值
     */
    public static void saveBoolElement(final Element containerNode, final String infoNodeName, final boolean value)
    {
        saveStrElement(containerNode, infoNodeName, Boolean.toString(value));
    }

    /**
     * 从节点读取一个boolean值，如果节点不存在或者非true的值都回返回false,比较不区分大小写
     * @param containerNode 含有信息的节点
     * @param infoNodeName 节点名称
     * @return 读出的值
     */
    public static boolean loadBoolElement(final Element containerNode, final String infoNodeName)
    {
        return "true".equalsIgnoreCase(loadStrElement(containerNode, infoNodeName)); //$NON-NLS-1$
    }
    
    /**
     * 从节点读取一个boolean值，如果节点不存在或者是空字符串，则返回默认值；如果是true则返回true，如果是其他字符串则返回false
     * @param containerNode 含有信息的节点
     * @param infoNodeName 节点名称
     * @param defaultValue 默认值
     * @return 读出的值
     */
    public static boolean loadBoolElement(final Element containerNode, final String infoNodeName, final boolean defaultValue)
    {
        boolean value = defaultValue;
        String valueStr = loadStrElement(containerNode, infoNodeName);
        if (valueStr != null && valueStr.length() > 0)
        {
            value = Boolean.getBoolean(valueStr);
        }
        return value;
    }
    
    /**
     * 将IXml列表保存到Xml文件
     * @param containerNode 用于保存信息的节点
     * @param infoNodeName 列表对应节点名称
     * @param lst 需要保存的列表
     * @param itemName 每一个属性对应节点名称
     */
    public static void saveXmlArrayElement(final Element containerNode, final String infoNodeName, final List<? extends IXmlObject> lst, final String itemName)
    {
        Element infoNode = containerNode.element(infoNodeName);
        if (infoNode == null)
        {
            infoNode = containerNode.addElement(infoNodeName);
        }
        XmlHelper.clearElement(infoNode, itemName);
        for (IXmlObject xmlObj : lst)
        {
            Element itemNode = infoNode.addElement(itemName);
            xmlObj.saveInfo(itemNode);
        }
    }

    /**
     * 将列表信息从Xml节点读出
     * @param containerNode 用于保存信息的节点
     * @param infoNodeName 容器节点
     * @param lst 需要保存的列表
     * @param elementName 列表对应节点名称
     * @param itemName 每一个属性对应节点名称
     * @param xmlObjClass 每一个节点对应的类型
     * @throws XmlException 可能的异常
     */
    @SuppressWarnings("unchecked")
	public static void loadXmlArrayElement(final Element containerNode, final String infoNodeName,
            final List lst, final Class<? extends IXmlObject> xmlObjClass) 
            throws XmlException
    {
        lst.clear();
        Element infoNode = findElement(containerNode, infoNodeName);
        if (infoNode == null)
        {
            return;
        }
        
        List lstElement = infoNode.elements();
        try
        {
            for (int i = 0, count = lstElement.size(); i < count; i++)
            {
                IXmlObject xmlObj = xmlObjClass.newInstance();
                xmlObj.loadInfo((Element) lstElement.get(i));
                lst.add(xmlObj);
            }
        }
        catch (Exception e)
        {
            throw new XmlException(CommonRes.ResFileName,
                    CommonRes.XmlHelper_LoadXmlObjFaild,
                    new Object[]{xmlObjClass.getName()}, e);
        }
    }

    /**
     * 将列表信息从Xml节点读出
     * @param containerNode 用于保存信息的节点
     * @param infoNodeName 容器节点
     * @param lst 需要保存的列表
     * @param elementName 列表对应节点名称
     * @param itemName 每一个属性对应节点名称
     * @param xmlObjClass 每一个节点对应的类型
     * @throws XmlException 可能的异常
     */
    @SuppressWarnings("unchecked")
	public static void loadXmlArrayElement(final Element containerNode, final String infoNodeName,
            final List lst, final Class<? extends IXmlObject> xmlObjClass,
            final Object param) 
            throws XmlException
    {
        lst.clear();
        Element infoNode = findElement(containerNode, infoNodeName);
        if (infoNode == null)
        {
            return;
        }
        
        List lstElement = infoNode.elements();
        try
        {
            Object[] paramValues;
            if (param == null)
            {
                paramValues = new Object[0];
            }
            else
            {
                paramValues = new Object[]{param};
            }
            //找到符合条件的构造函数，如果是null，则找到的第一个带有参数的构造函数就是
            Constructor<?>[] constructors = xmlObjClass.getConstructors();
            Constructor<?> constructor = null;
            for (int i = 0; i < constructors.length; i++)
            {
                Class<?>[] paramsType = constructors[i].getParameterTypes();
                if (param == null || 
                        (paramsType.length == 1 && param.getClass().isAssignableFrom(paramsType[0])))
                {
                    constructor = constructors[i];
                    break;
                }
            }
            
            if (constructor == null)
            {
                throw new XmlException(CommonRes.ResFileName, CommonRes.XmlHelper_NotFindConstructor,
                        new Object[]{xmlObjClass, param == null ? null : param.getClass()}, null);
            }
            
            for (int i = 0, count = lstElement.size(); i < count; i++)
            {
                IXmlObject xmlObj = (IXmlObject) constructor.newInstance(paramValues);
                xmlObj.loadInfo((Element) lstElement.get(i));
                lst.add(xmlObj);
            }
        }
        catch (Exception e)
        {
            throw new XmlException(CommonRes.ResFileName,
                    CommonRes.XmlHelper_LoadXmlObjFaild,
                    new Object[]{xmlObjClass.getName()}, e);
        }
    }
    
    /**
     * 将Xml对象保存到Xml节点
     * @param containerNode 用于存储信息的Xml节点
     * @param infoNodeName Xml节点名称
     * @param xmlObj 需要保存的Xml对象
     */
    public static void saveXmlObjElement(final Element containerNode, final String infoNodeName, final IXmlObject xmlObj)
    {
        Element valueElement = containerNode.element(infoNodeName);
        if (xmlObj == null && valueElement != null)
        {
            containerNode.remove(valueElement);
        }
        else if (xmlObj != null && valueElement == null)
        {
            valueElement = containerNode.addElement(infoNodeName);
        }
        
        if (xmlObj != null)
        {
            xmlObj.saveInfo(valueElement);
        }
    }
    
    /**
     * 从Xml节点读取Xml对象信息
     * @param containerNode 保存有Xml信息的节点
     * @param infoNodeName 保存Xml信息节点的名称
     * @param xmlObj 等待读取信息的Xml对象
     * @return 如果为null说明没有找到节点，否则返回值和xmlObj是同一个对象
     * @throws XmlException Xml异常
     */
    public static IXmlObject loadXmlObjElement(final Element containerNode, final String infoNodeName, final IXmlObject xmlObj) throws XmlException
    {
        IXmlObject value = null;
        Element objElement = XmlHelper.findElement(containerNode, infoNodeName);
        if (objElement != null)
        {
            xmlObj.loadInfo(objElement);
            value = xmlObj;
        }
        return value;
    }
    
    /**
     * 将字符串以属性的方式保存在节点中
     * @param infoNode 用于保存信息的节点
     * @param attributeName 属性名称
     * @param value 需要保存的值
     */
    public static void saveStrAttribute(final Element infoNode, final String attributeName, final String value)
    {
        Attribute attribute = infoNode.attribute(attributeName);
        if (value == null) //remove Attribute
        {
            if (attribute != null)
            {
                infoNode.remove(attribute);//.setAttributeValue(attributeName);
            }
        }
        else if (attribute == null) // new Attribute
        {
            infoNode.addAttribute(attributeName, value);
        }
        else// modify Attribute
        {
            attribute.setValue(value);
//            element.add(attribute);
        }
    }
    
    /**
     * 从节点属性中读取字符串值
     * @param infoNode 用于保存信息的节点
     * @param attributeName 属性名称
     * @return 读出的字符串值,如果属性不存在则返回null
     */
    public static String loadStrAttribute(final Element infoNode, final String attributeName)
    {
        return infoNode.attributeValue(attributeName);
    }
    
    /**
     * 将整数数字保存到属性中
     * @param infoNode 用于保存信息的节点
     * @param attributeName 属性名称
     * @param value 需要保存的数字
     */
    public static void saveNumAttribute(final Element infoNode, final String attributeName, final long value)
    {
        saveStrAttribute(infoNode, attributeName, Long.toString(value));
    }
    
    /**
     * 由节点属性读取一个数字
     * @param infoNode 保存了信息的节点
     * @param attributeName 属性名称
     * @return 得到的数字值，如果没有找到属性或者属性不是数字，则返回0
     */
    public static long loadNumAttribute(final Element infoNode, final String attributeName)
    {
        return loadNumAttribute(infoNode, attributeName, 0);
    }
    
    /**
     * 将数字信息保存成属性
     * @param infoNode 用于保存数字信息的节点
     * @param attributeName 属性名称
     * @param value 属性值
     */
    public static void saveIntAttribute(final Element infoNode, final String attributeName, final Integer value)
    {
        Attribute a = infoNode.attribute(attributeName);
        if (a != null)
        {
            infoNode.remove(a);
        }
        if (value != null)
        {
            infoNode.addAttribute(attributeName, value.toString());
        }
    }
    
    /**
     * 读取数字属性
     * @param infoNode 带有值的节点
     * @param attributeName 属性名称
     * @return 值，可能为null
     */
    public static Integer loadIntAttribute(final Element infoNode, final String attributeName)
    {
        Integer result = null;
        Attribute a = infoNode.attribute(attributeName);
        if (a != null)
        {
            result = new Integer(a.getValue());
        }
        return result;
    }
    
    /**
     * 由节点属性读取一个数字
     * @param infoNode 保存了信息的节点
     * @param attributeName 属性名称
     * @param defaultValue 默认值
     * @return 得到的数字值，没有这个属性或者格式不正确则返回默认值
     */
    public static long loadNumAttribute(final Element infoNode, final String attributeName, final long defaultValue)
    {
        long value = defaultValue;
        try
        {
            value = Long.parseLong(infoNode.attributeValue(attributeName));
        }
        catch (NumberFormatException exp)
        {
            //不处理这个异常
        }
        return value;
    }
    
    /**
     * 将Boolean值信息保存到节点属性中
     * @param infoNode 用于保存信息的节点
     * @param attributeName 属性名称
     * @param value 需要保存的值
     */
    public static void saveBoolAttribute(final Element infoNode, final String attributeName, final boolean value)
    {
        saveStrAttribute(infoNode, attributeName, Boolean.toString(value));
    }

    /**
     * 从节点属性读取一个boolean值，如果节点不存在或者非true的值都回返回false
     * @param infoNode 含有信息的节点
     * @param attributeName 属性名称
     * @return 读出的值
     */
    public static boolean loadBoolAttribute(final Element infoNode, final String attributeName)
    {
        return Boolean.valueOf(loadStrAttribute(infoNode, attributeName)).booleanValue();
    }
    
    /**
     * 从节点属性读取一个boolean值，如果节点不存在或者是空字符串，则返回默认值；如果是true则返回true，如果是其他字符串则返回false
     * @param infoNode 含有信息的节点
     * @param attributeName 属性名称
     * @param defaultValue 默认值
     * @return 读出的值
     */
    public static boolean loadBoolAttribute(final Element infoNode, final String attributeName, final boolean defaultValue)
    {
        boolean value = defaultValue;
        String valueStr = loadStrAttribute(infoNode, attributeName);
        if (valueStr != null && valueStr.length() > 0)
        {
            value = Boolean.getBoolean(valueStr);
        }
        return value;
    }
    
    /**
     * 将信息保存到一个Xml节点
     * @param obj 要保存的信息
     * @param nodeName 节点名称
     * @return 转换后带有信息的节点
     */
    public static Element toElement(final IXmlObject obj, final String nodeName)
    {
        Document doc =  DocumentHelper.createDocument();
        Element e = doc.addElement(nodeName);
        obj.saveInfo(e);
        return e;
    }
   
    /**
     * 将字符串数组列表保存到Xml文件
     * @param containerNode 用于保存列表的Xml节点
     * @param infoNodeName 列表节点名称
     * @param lstInitValue 需要保存的字符串数组列表
     */
    public static void saveStringArrayArray(Element containerNode, String infoNodeName, List<String[]> lstInitValue)
    {
        Element infoNode = containerNode.element(infoNodeName);
        if (infoNode == null)
        {
            infoNode = containerNode.addElement(infoNodeName);
        }
        XmlHelper.clearElement(infoNode, StrArrayNode);
        for (int i = 0; i < lstInitValue.size(); i++)
        {
            String[] vs = lstInitValue.get(i);
            Element e = infoNode.addElement(StrArrayNode);
            XmlHelper.saveStrArray(e, vs);
        }
    }
    
    /**
     * 从节点读取字符串数组列表
     * @param containerNode 读取信息的节点
     * @param infoNode 数组节点名称
     * @param lstInitValue 字符串数组列表
     */
    @SuppressWarnings("unchecked")
	public static void loadStringArrayArray(Element containerNode, String infoNode, List<String[]> lstInitValue)
    {
        lstInitValue.clear();
        Element lstElement = containerNode.element(infoNode);
        if (lstElement != null)
        {
            List lst = lstElement.elements(StrArrayNode);
            for (int i = 0; i < lst.size(); i++)
            {
                String[] vs = XmlHelper.loadStrArray((Element) lst.get(i));
                lstInitValue.add(vs);
            }
        }
    }
    
    public static Element createElement(Element parentElement, String name)
    {
        Element e = parentElement.addElement(name);
        return e;
    }

    @SuppressWarnings("unchecked")
	public static Element getFirstElement(Element element)
    {
        Element first = null;
        List lst = element.elements();
        if (lst.size() > 0)
        {
            first = (Element) lst.get(0);
        }
        return first;
    }

    public static IXmlObject clone(IXmlObject xmlObj)
    {
        IXmlObject newObj = null;
        try
        {
            newObj = xmlObj.getClass().newInstance();
            newObj.loadInfo(toElement(xmlObj, "root")); //$NON-NLS-1$
        }
        catch (Exception e)
        {
            throw new XmlException(CommonRes.ResFileName,
                    CommonRes.XmlHelper_LoadXmlObjFaild,
                    new Object[]{xmlObj.getClass().getName()}, e);
        }
        return newObj;
    }
}
