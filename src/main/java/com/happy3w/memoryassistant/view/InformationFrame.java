/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据库信息的管理界面</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
package com.happy3w.memoryassistant.view;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:执行Sql的界面</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
public class InformationFrame extends JInternalFrame
{

    private JPanel pnlMain = null;
    
    /**
     * This is the ManageFrame default constructor
     */
    public InformationFrame()
    {
        super();
        
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(900, 700);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setIconifiable(true);
        this.setClosable(true);
        this.setContentPane(getPnlMain());
        this.setTitle("Manage Information");
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlMain()
    {
        if (pnlMain == null)
        {
            pnlMain = new JPanel();
            pnlMain.setLayout(new BorderLayout());
            InfoManagePanel pnlInfo = new InfoManagePanel();
            pnlMain.add(pnlInfo, BorderLayout.CENTER);
        }
        return pnlMain;
    }
}
