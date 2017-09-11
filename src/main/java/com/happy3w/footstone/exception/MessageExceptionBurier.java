/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-7
 */
package com.happy3w.footstone.exception;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>消息异常处理器</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-7
 */
public class MessageExceptionBurier implements IExceptionBurier
{
    /* (non-Javadoc)
     * @see com.boroborome.footstone.exception.IExceptionBurier#bury(java.lang.Exception, boolean)
     */
    @Override
    public void bury(Exception exception, boolean buried)
    {
        if (!buried && exception instanceof MessageException)
        {
            MessageException exp = (MessageException) exception;
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), exp.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

}
