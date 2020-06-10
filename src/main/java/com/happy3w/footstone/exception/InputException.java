/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:用户录入异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
package com.happy3w.footstone.exception;

import java.awt.*;


/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:用户录入异常,主要用于界面信息的校验，出错时提示给用户；也可以用于操作后提示给用户错误信息的其他场合</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-10
 * @modify 2008-03-30 BoRoBoRoMe 增加带有Params的构造函数
 */
public class InputException extends MessageException {
    /*
     * 需要聚焦的控件
     */
    private Component com;

    /**
     * 构造函数
     *
     * @param resPath
     * @param message
     * @param params
     * @param com
     * @param cause
     */
    public InputException(String resPath, String message, Object[] params, Component com,
                          Throwable cause) {
        super(resPath, message, params, cause);
        this.com = com;
    }

    /**
     * 构造函数
     *
     * @param resPath
     * @param message
     * @param com
     * @param cause
     */
    public InputException(String resPath, String message, Component com,
                          Throwable cause) {
        super(resPath, message, cause);
        this.com = com;
    }

    /**
     * 获取需要聚焦的控件
     *
     * @return 需要聚焦的控件
     */
    public Component getRequestFocusCom() {
        return com;
    }
}
