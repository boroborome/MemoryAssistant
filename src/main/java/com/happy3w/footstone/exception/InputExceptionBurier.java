/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:录入异常处理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-15
 */
package com.happy3w.footstone.exception;

import javax.swing.JOptionPane;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:录入异常处理器
 *  处理界面录入时的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-15
 */
public class InputExceptionBurier extends StringCodeExceptionBurier
{

    /* (non-Javadoc)
     * @see com.boroborome.common.exception.IExceptionBurier#bury(java.lang.Exception, boolean)
     */
	@Override
    public void bury(final Exception exception, final boolean buried)
    {
        super.bury(exception, buried);
        
        if (!buried && exception instanceof InputException)
        {
            InputException ie = (InputException) exception;
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ie.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            if (ie.getRequestFocusCom() != null)
            {
                ie.getRequestFocusCom().requestFocus();
            }
        }
    }

}
