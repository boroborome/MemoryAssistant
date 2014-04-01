/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据库信息的管理界面</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
package com.boroborome.ma.view;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import com.boroborome.footstone.ui.ExecuteSqlPanel;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:执行Sql的界面</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
public class ExecuteSqlFrame extends JInternalFrame
{

    private JPanel pnlMain = null;
    
    /**
     * This is the ManageFrame default constructor
     */
    public ExecuteSqlFrame()
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
        this.setTitle("Execute Sql");
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
            ExecuteSqlPanel sqlPanel = new ExecuteSqlPanel();
            sqlPanel.getSqlArea().setText("select * from tbl_taskevent where lasttime < 0\n" +
            		"\n" +
            		"update tbl_taskevent set lasttime=0 where lasttime<0");
            pnlMain.add(sqlPanel, BorderLayout.CENTER);
        }
        return pnlMain;
    }
}
