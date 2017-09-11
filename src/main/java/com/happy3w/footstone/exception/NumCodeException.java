/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:使用数字作为编码的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
package com.happy3w.footstone.exception;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:使用数字作为编码的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
public class NumCodeException extends Exception
{
    /*
     * 数字的错误编码
     */
    private int code;
    
    /*
     * 与错误有关的参数列表，可能用于拼接提示字符串
     */
    private Object[] params;
    
    /**
     * 构造函数
     * @param code 错误编码
     * @param message 默认的消息
     */
    public NumCodeException(final int code, final String message)
    {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     * @param code 错误编码
     * @param message 默认的消息
     * @param cause 导致本错误的错误
     */
    public NumCodeException(final int code, final String message, final Throwable cause)
    {
        super(message, cause);
        this.code = code;
    }
    
    /**
     * 获取错误编码
     * @return 错误编码
     */
    public int getCode()
    {
        return code;
    }

    /**
     * 获取错误相关参数
     * @return 错误相关参数
     */
    public Object[] getParams()
    {
        return params;
    }

    /**
     * 设置错误相关参数
     * @param params 错误相关参数
     */
    public void setParams(final Object[] params)
    {
        this.params = params;
    }
}