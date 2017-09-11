/*
 * <P>Title:      基石模块</P>
 * <P>Description:异常的坟墓，这个工具负责处理各种异常.各种异常埋葬者（IExceptionBurier）在这里注册后，就可以处理对应的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-2
 */
package com.happy3w.footstone.exception;

/**
 * 异常的坟墓，这个工具负责处理各种异常.各种异常埋葬者（IExceptionBurier）在这里注册后，就可以处理对应的异常
 * @author BoRoBoRoMe
 */
public interface IExceptionGrave
{
    /**
     * 注册一个异常处理器
     * @param exceptionClass 异常类型
     * @param burier 对应的异常处理器
     */
    void regBurier(final Class<? extends Exception> exceptionClass, final IExceptionBurier burier);
    
    /**
     * 注销一个异常处理器
     * @param exceptionClass 异常类型
     * @param burier 对应的异常处理器
     */
    void unregBurier(final Class<? extends Exception> exceptionClass);
    
    /**
     * 检测这个异常类型是否有对应的处理器（继承关系不算）
     * @param exceptionClass 异常类型
     * @return 对应处理器
     */
    boolean isReg(final Class<? extends Exception> exceptionClass);
    
    /**
     * 获得对应异常的处理器（继承关系不算）
     * @param exceptionClass 异常类型
     * @return 对应处理器
     */
    IExceptionBurier getBurier(final Class<? extends Exception> exceptionClass);
    
    /**
     * 处理掉一个异常
     * 按照异常类型的继承关系处理直道Exception类型终止
     * @param exception 需要处理的异常
     */
    void bury(final Exception exception);
}
