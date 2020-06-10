/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:基本简单的异常处理</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
package com.happy3w.footstone.exception;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:基本简单的异常处理,只是简单的输出</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-10
 */
public class SimpleExceptionBurier implements IExceptionBurier {
    /**
     * 构造函数
     */
    public SimpleExceptionBurier() {
        //Nothing to initalize
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.exception.IExceptionBurier#bury(java.lang.Exception, boolean)
     */
    @Override
    public void bury(Exception exception, boolean buried) {
        if (!buried) {
            exception.printStackTrace();
        }
    }

}
