/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:可以在Xml文件和对象之间进行转换的接口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-18
 */
package com.boroborome.footstone.xml;

import org.dom4j.Element;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:可以在Xml文件和对象之间进行转换的接口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-18
 */
public interface IXmlObject
{
    /**
     * 将信息保存到Xml对象中
     * @param element 准备保存信息的Xml节点
     */
    void saveInfo(final Element element);
    
    /**
     * 从Xml节点中读出对象信息
     * @param element 带有信息的Xml节点
     * @throws XmlException Xml异常
     */
    void loadInfo(final Element element) throws XmlException;
}
