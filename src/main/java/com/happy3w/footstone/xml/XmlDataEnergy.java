/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-2-25
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.xml;

import com.happy3w.footstone.dataenergy.DataEnergyException;
import com.happy3w.footstone.dataenergy.IDataEnergy;
import com.happy3w.footstone.dataenergy.IDataMachine;
import com.happy3w.footstone.util.CommonRes;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.Stack;

/**
 * <DT><B>Title:</B></DT>
 * <DD>XML引擎</DD>
 * <DT><B>Description:</B></DT>
 * <DD>将Xml文件解析成数据数据引擎使用协议的解析引擎。</DD>
 * <PRE>
 * {@code
 * 解析逻辑如下：
 * //startDocument()
 * <School xmlns:test="http://www.boroborome.com/test" name="s:清华" area="i:10000">    //xmlns开头的属性被忽略，这种属性尽用于保持文档结构的准确
 * //startData("School")
 * //setAttribute("name", "清华")
 * //setAttribute("area", 10000)
 * <People _aN_="manager" name="s:BoRoBoRoMe">                 //startData("People", "manager")
 * //setAttribute("name", "BoRoBoRoMe")
 * <str _aN_="description">                                //setAttribute("description", "清华建校于XXX年....")
 * 清华建校于XXX年....
 * </str>
 * </People>                                                   //endData()
 * </School>                                                        //endData()
 * //endDocument()
 * }
 * <PRE>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-2-25
 */
public class XmlDataEnergy implements IDataEnergy {
    /**
     * 数据引擎驱动的机器
     */
    private IDataMachine machine;

    /**
     * 数据引擎驱动使用的文档流
     */
    private InputStream inputStream;

    /**
     * 构造函数
     */
    public XmlDataEnergy() {
        super();
    }

    /**
     * 构造函数
     *
     * @param inputStream 带有Xml信息的数据流
     */
    public XmlDataEnergy(final InputStream inputStream) {
        super();
        this.inputStream = inputStream;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataEnergy#getMachine()
     */
    @Override
    public IDataMachine getMachine() {
        return machine;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataEnergy#setMachine(com.boroborome.common.dataenergy.IDataMachine)
     */
    @Override
    public void setMachine(final IDataMachine machine) {
        this.machine = machine;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataEnergy#translateData()
     */
    @Override
    public void translateData() throws DataEnergyException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(false);
            SAXParser parser = spf.newSAXParser();
            parser.parse(inputStream, new ReadXmlContentHandler());
//            TransformerFactory factory = SAXTransformerFactory.newInstance();
////            factory.setFeature(XMLConstants.XML_NS_URI, true);
////            factory.setFeature(XMLConstants.XMLNS_ATTRIBUTE, true);
////            factory.setFeature(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, true);
//            Transformer transformer = factory.newTransformer();
//            transformer.transform(new SAXSource(new InputSource(inputStream)),
//                    new SAXResult(new ReadXmlContentHandler()));
        } catch (Exception e) {
            throw new DataEnergyException(CommonRes.ResFileName,
                    CommonRes.XmlEnergy_Failed, e);
        }
    }

    /**
     * 解析文档时保存节点和属性之间关系的数据结构
     */
    private class DataItem {
        /**
         * 数据名称
         */
        public final String dataName;
        /**
         * 属性名称
         */
        public final String attributeName;

        public final boolean isSimpleType;

        /**
         * 如果是简单类型则这里会保存值
         */
        private Object value;

        /**
         * 构造函数
         *
         * @param dataName      数据名称
         * @param attributeName 属性名称
         */
        public DataItem(final String dataName, final String attributeName) {
            super();
            this.dataName = dataName;
            this.attributeName = attributeName;
            this.isSimpleType = XmlEnergyUtil.isSimpleType(dataName);
        }

        public void setValue(final Object newValue) {
            value = newValue;
        }

        public Object getValue() {
            return value;
        }
    }

    /**
     * 负责解析XML文件
     */
    private class ReadXmlContentHandler extends DefaultHandler {

        /**
         * 属性栈，保存当前驱动的属性名称
         */
        private Stack<DataItem> attributeStack = new Stack<DataItem>();

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // 只有当前节点是简单类型时才将文本节点内容当作属性内容
            if (!attributeStack.isEmpty()) {
                DataItem item = attributeStack.lastElement();
                if (item.isSimpleType) {
                    try {
                        item.setValue(XmlEnergyUtil.textToValue(item.dataName, new String(ch, start, length)));
                    } catch (DataEnergyException e) {
                        throw new SAXException("Convert value failed.", e); //$NON-NLS-1$
                    }
                }
            }
        }

        @Override
        public void endDocument() throws SAXException {
            try {
                machine.endWork();
            } catch (DataEnergyException e) {
                throw new SAXException("Run XmlDataEnergy endWork failed.", e); //$NON-NLS-1$
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            DataItem item = attributeStack.pop();
            try {
                if (item.isSimpleType) {
                    machine.setAttribute(item.attributeName, item.getValue());
                } else {
                    machine.endData(qName);
                }
            } catch (DataEnergyException e) {
                throw new SAXException("Run XmlDataEnergy endData failed.", e); //$NON-NLS-1$
            }
        }

        @Override
        public void startDocument() throws SAXException {
            try {
                machine.startWork();
            } catch (DataEnergyException e) {
                throw new SAXException("Run XmlDataEnergy startWork failed.", e); //$NON-NLS-1$
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            DataItem item = new DataItem(qName, atts.getValue(XmlEnergyUtil.AttributeName));
            attributeStack.add(item);


            //2.如果是简单类型则无论是否有属性名称，一定执行setAttribute方法,这个执行过程会在处理常量时执行，如果没有执行则在end时执行
            //否则执行startData，并处理所有属性
            if (!item.isSimpleType) {
                try {
                    machine.startData(item.dataName, item.attributeName);
                    for (int index = 0; index < atts.getLength(); index++) {
                        String attributeName = atts.getQName(index);
                        if (!XmlEnergyUtil.AttributeName.equals(attributeName)
                                && !attributeName.startsWith(XmlEnergyUtil.NameSpaceHead)) {
                            machine.setAttribute(attributeName,
                                    XmlEnergyUtil.textToValue(atts.getValue(index)));
                        }
                    }
                } catch (DataEnergyException e) {
                    throw new SAXException("Run XmlDataEnergy failed.", e); //$NON-NLS-1$
                }
            }
        }
    }
}
