/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:简单异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
package com.happy3w.footstone.exception;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:简单异常,不建议过多的使用这个异常，最好只是使用来测试或者很少出问题且不想和其他模块有耦合关系时</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
public class SimpleException extends Exception
{
    /**
     * 构造函数
     */
    public SimpleException()
    {
        //
    }

    /**
     * 构造函数
     * @param message 消息
     */
    public SimpleException(final String message)
    {
        super(message);
    }

    /**
     * 构造函数
     * @param cause 错误
     */
    public SimpleException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * 构造函数
     * @param message 消息
     * @param cause 错误
     */
    public SimpleException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

}