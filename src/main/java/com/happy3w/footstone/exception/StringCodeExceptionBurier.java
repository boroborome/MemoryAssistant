/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:字符串编码异常处理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-16
 */
package com.happy3w.footstone.exception;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:字符串编码异常处理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-3-16
 */
public class StringCodeExceptionBurier implements IExceptionBurier {
    /*
     * 设置资源时默认获得的标题
     */
    private static final String DefaultTitleKey = "InputExceptionBurier.Title";//$NON-NLS-1$

    /*
     * 默认标题
     */
    private static final String DefaultTitle = "Warning"; //$NON-NLS-1$

    /*
     * 资源
     */
    private ResourceBundle resource;

    /*
     * 主窗口
     */
    private Component mainFrame;

    /*
     * 错误标题
     */
    private String title = DefaultTitle;

    /**
     * 构造函数
     */
    public StringCodeExceptionBurier() {
        super();
    }

    /**
     * 获取主窗口
     *
     * @return 主窗口
     */
    public Component getMainFrame() {
        return mainFrame;
    }

    /**
     * 设置主窗口
     *
     * @param mainFrame 主窗口
     */
    public void setMainFrame(final Component mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * 获取资源
     *
     * @return 资源
     */
    public ResourceBundle getResource() {
        return resource;
    }

    /**
     * 设置资源
     *
     * @param resource 资源
     */
    public void setResource(final ResourceBundle resource) {
        this.resource = resource;
        if (resource != null && DefaultTitle.equals(title)) {
            title = resource.getString(DefaultTitleKey);
        }
    }

    /**
     * 获取标题
     *
     * @return 标题
     */
    public String getTitle() {
        return title;
    }


    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.exception.IExceptionBurier#bury(java.lang.Exception, boolean)
     */
    @Override
    public void bury(final Exception exception, final boolean buried) {
        if (!buried && exception instanceof StringCodeException) {
            exception.printStackTrace();
            StringCodeException ie = (StringCodeException) exception;
            String format = resource.getString(ie.getCode());
            String message = MessageFormat.format(format, ie.getParams());
            JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.WARNING_MESSAGE);
        }
    }
}
