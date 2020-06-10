/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:异常的坟墓，这个工具负责处理各种异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
package com.happy3w.footstone.bundle;

import com.happy3w.footstone.exception.IExceptionBurier;
import com.happy3w.footstone.exception.IExceptionGrave;
import com.happy3w.footstone.exception.InputException;
import com.happy3w.footstone.exception.InputExceptionBurier;
import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.exception.MessageExceptionBurier;
import com.happy3w.footstone.exception.SimpleExceptionBurier;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:异常的坟墓，这个工具负责处理各种异常.各种异常埋葬者（IExceptionBurier）在这里注册后，就可以处理对应的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-10
 */
public class ExceptionGraveImpl implements IExceptionGrave {
    private static Logger log = Logger.getLogger(ExceptionGraveImpl.class);


    /**
     * 埋葬者的注册集合
     */
    private Map<Class<? extends Exception>, IExceptionBurier> buriers;

    /**
     * 构造函数
     */
    public ExceptionGraveImpl() {
        buriers = new HashMap<Class<? extends Exception>, IExceptionBurier>();
        regBurier(Exception.class, new SimpleExceptionBurier());
        regBurier(MessageException.class, new MessageExceptionBurier());
        regBurier(InputException.class, new InputExceptionBurier());
    }

    /**
     * 注册一个异常处理器
     *
     * @param exceptionClass 异常类型
     * @param burier         对应的异常处理器
     */
    @Override
    public void regBurier(final Class<? extends Exception> exceptionClass, final IExceptionBurier burier) {
        if (exceptionClass == null || burier == null
                || !Exception.class.isAssignableFrom(exceptionClass)) {
            throw new IllegalArgumentException("ExceptionGrave.regBurier() except no null param."); //$NON-NLS-1$
        }

        buriers.put(exceptionClass, burier);
    }

    /**
     * 注销一个异常处理器
     *
     * @param exceptionClass 异常类型
     */
    @Override
    public void unregBurier(final Class<? extends Exception> exceptionClass) {
        if (exceptionClass == null) {
            throw new IllegalArgumentException("ExceptionGrave.unregBurier() except no null param."); //$NON-NLS-1$
        }

        buriers.remove(exceptionClass);
    }

    /**
     * 检测这个异常类型是否有对应的处理器（继承关系不算）
     *
     * @param exceptionClass 异常类型
     * @return 对应处理器
     */
    @Override
    public boolean isReg(final Class<? extends Exception> exceptionClass) {
        return buriers.get(exceptionClass) != null;
    }

    /**
     * 获得对应异常的处理器（继承关系不算）
     *
     * @param exceptionClass 异常类型
     * @return 对应处理器
     */
    @Override
    public IExceptionBurier getBurier(final Class<? extends Exception> exceptionClass) {
        if (exceptionClass == null) {
            throw new IllegalArgumentException("ExceptionGrave.getBurier() except no null param."); //$NON-NLS-1$
        }

        return buriers.get(exceptionClass);
    }

    /**
     * 处理掉一个异常
     * 按照异常类型的继承关系处理直道Exception类型终止
     *
     * @param exception 需要处理的异常
     */
    @Override
    public void bury(final Exception exception) {
        if (exception == null) {
            throw new IllegalArgumentException("ExceptionGrave.bury() except no null param."); //$NON-NLS-1$
        }

        Class<?> type = exception.getClass();
        boolean buried = false;

        do {
            IExceptionBurier burier = buriers.get(type);
            if (burier != null) {
                try {
                    burier.bury(exception, buried);
                    buried = true;
                } catch (Exception exp) {
                    log.error("bury message failed.", exp);
                }
            }

            type = type.getSuperclass();
        }
        while (Throwable.class != type);
    }
}
