/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-11-1
 */
package com.boroborome.footstone.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-11-1
 */
public class CheckCloseInternalFrame extends JInternalFrame
{
    /* (non-Javadoc)
     * @see javax.swing.JInternalFrame#doDefaultCloseAction()
     */
    @Override
    public void doDefaultCloseAction()
    {
        // TODO Auto-generated method stub
        super.doDefaultCloseAction();
    }

    /**
     * 构造函数
     * @param title
     * @param resizable
     * @param closable
     * @param maximizable
     * @param iconifiable
     */
    public CheckCloseInternalFrame(String title, JPanel pnl, boolean resizable, boolean closable, boolean maximizable,
            boolean iconifiable)
    {
        super(title, resizable, closable, maximizable, iconifiable);
    }

    /**
     * 构造函数
     * @param title
     * @param resizable
     * @param closable
     * @param maximizable
     */
    public CheckCloseInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable)
    {
        super(title, resizable, closable, maximizable);
    }

    /**
     * 构造函数
     * @param title
     * @param resizable
     * @param closable
     */
    public CheckCloseInternalFrame(String title, boolean resizable, boolean closable)
    {
        super(title, resizable, closable);
    }

    /**
     * 构造函数
     * @param title
     * @param resizable
     */
    public CheckCloseInternalFrame(String title, boolean resizable)
    {
        super(title, resizable);
    }

    /**
     * 构造函数
     * @param title
     */
    public CheckCloseInternalFrame(String title)
    {
        super(title);
    }

}
