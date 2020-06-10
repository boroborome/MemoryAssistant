/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:使用字符串类型数据作为编码的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
package com.happy3w.footstone.exception;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:使用字符串类型数据作为编码的异常
 * 这个异常的编码，可以从资源文件中找到对应的说明
 * 并且可以通过与params参数一起构造提示信息</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-10
 */
public class StringCodeException extends Exception {
    /*
     * 字符串编码
     */
    private String code;

    /*
     * 与错误有关的参数列表，可能用于拼接提示字符串
     */
    private Object[] params;

    /**
     * 构造函数
     *
     * @param code    字符串错误编码
     * @param message 默认的消息
     */
    public StringCodeException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code    错误编码
     * @param message 默认的消息
     * @param cause   导致这个错误的原因
     */
    public StringCodeException(final String code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code    错误编码
     * @param message 默认的消息
     * @param params  参数列表
     * @param cause   导致这个错误的原因
     */
    public StringCodeException(final String code, final String message,
                               final Object[] params, final Throwable cause) {
        super(message, cause);
        this.code = code;
        this.params = params;
    }

    /**
     * 构造函数
     *
     * @param code    错误编码
     * @param message 默认的消息
     * @param params  参数列表
     */
    public StringCodeException(final String code, final String message,
                               final Object[] params) {
        super(message);
        this.code = code;
        this.params = params;
    }

    /**
     * 获取错误编码
     *
     * @return 错误编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取错误相关参数
     *
     * @return 错误相关参数
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * 设置错误相关参数
     *
     * @param params 错误相关参数
     */
    public void setParams(final Object[] params) {
        this.params = params;
    }

}
