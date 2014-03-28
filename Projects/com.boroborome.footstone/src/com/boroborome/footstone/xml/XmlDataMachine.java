/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-7
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Stack;

import com.boroborome.footstone.dataenergy.IDataMachine;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>XML数据机器</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>将数据引擎提供的数据保存到XML文件</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-7
 */
public class XmlDataMachine implements IDataMachine
{
    /**
     * 数据机器会将数据引擎提供的数据信息写入这个输出流
     */
    private PrintStream out;
    /**
     * 数据栈，保存每一个节点名称，用于输出节点的结束符
     */
    private Stack<String> dataStack = new Stack<String>();
    
    /**
     * 标示当前输出是否输出在节点的头位置
     * 如果是在节点头位置，则属性可以直接输出，否则只能输出一个属性节点
     */
    private boolean inElement = false;
    
    /**
     * Xml机器处理完后自动关闭数据流
     */
    private boolean autoCloseStream;
    /**
     * 构造函数
     * @param file
     * @throws FileNotFoundException
     */
    public XmlDataMachine(final File file) throws FileNotFoundException
    {
        autoCloseStream = true;
        out = new PrintStream(file); 
    }
    
    /**
     * 构造函数
     * @param output
     */
    public XmlDataMachine(final OutputStream output)
    {
        out = new PrintStream(output);
    }

    /**
     * 获取autoCloseStream
     * @return autoCloseStream
     */
    public boolean isAutoCloseStream()
    {
        return autoCloseStream;
    }

    /**
     * 设置autoCloseStream
     * @param autoCloseStream autoCloseStream
     */
    public void setAutoCloseStream(boolean autoCloseStream)
    {
        this.autoCloseStream = autoCloseStream;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#endData()
     */
    @SuppressWarnings("nls")
    @Override
    public void endData(String name)
    {
        if (this.inElement)
        {
            out.append("/>");
            dataStack.pop();
        }
        else
        {
            out.print("</");
            out.print(dataStack.pop());
            out.print(">");
        }
        
        this.inElement = false;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#endWork()
     */
    @SuppressWarnings("nls")
    @Override
    public void endWork()
    {
        if (this.inElement)
        {
            out.append("/>");
        }
        out.flush();
        if (autoCloseStream)
        {
            out.close();
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#setAttribute(java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("nls")
    @Override
    public void setAttribute(final String name, final Object value)
    {
        if (inElement)
        {
            out.append(" ");
            out.append(name);
            out.append("=\"");
            out.append(XmlEnergyUtil.valueToTypeText(value));
            out.append("\"");
        }
        else
        {
            String typeStr = XmlEnergyUtil.valueType(value);
            out.append("<").append(typeStr).append(" ")
                .append(XmlEnergyUtil.AttributeName).append("=\"")
                .append(name).append("\">").append(XmlEnergyUtil.valueToText(value))
                .append("</").append(typeStr).append(">");
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#startData(java.lang.String)
     */
    @SuppressWarnings("nls")
    @Override
    public void startData(String name, String attributeName)
    {
        this.dataStack.push(name);
        
        if (this.inElement)
        {
            out.print(">");
        }
        
        out.append("<").append(name);
        
        if (attributeName != null)
        {
            out.append(" ").append(XmlEnergyUtil.AttributeName).append("=\"").append(attributeName).append("\"");
        }
        this.inElement = true;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#startWork()
     */
    @SuppressWarnings("nls")
    @Override
    public void startWork()
    {
        out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    }

}
