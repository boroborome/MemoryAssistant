/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据库工作者的主窗口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
package com.boroborome.ma.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.resource.IResourceMgrSvc;
import com.boroborome.footstone.resource.ISpaceName;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;
import com.boroborome.footstone.svc.ISystemInstallSvc;
import com.boroborome.ma.view.bundle.Activator;
import com.boroborome.ma.view.res.ResConst;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据库工作者的主窗口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
public class MainFrame extends JFrame implements ISpaceName
{
    private static Logger log = Logger.getLogger(MainFrame.class);
    
    /**
     * 默认的资源名字空间
     */
    public static final String DefualtSpaceName = "MainFrame";  //  @jve:decl-index=0: //$NON-NLS-1$
    
    private static final long serialVersionUID = 1L;
    private JPanel pnlMain = null;
    private JMenuBar mainMenuBar = null;
    private JLabel lblStatus = null;
    private JDesktopPane desktopPane = null;
    private JMenu menuFile = null;
    private JMenuItem mItmExit = null;
    private JMenu menuManage = null;
    private JMenu menuQuery = null;
    private JMenu menuSetting = null;
    private JMenu menuAbout = null;
    private JMenuItem mItmSetting = null;
    private JMenuItem mItmAbout = null;
    private JMenuItem mItmRebuildDB = null;
    
    private String spaceName = DefualtSpaceName;  //  @jve:decl-index=0:

    private JMenuItem mItmQuerySetting = null;

    private JToolBar toolBar;

    private JTabbedPane tabWorkspace;

    private Map<Class<? extends JInternalFrame>, JInternalFrame> mapFrame = new HashMap<Class<? extends JInternalFrame>, JInternalFrame>();
    
    /**
     * This is the default constructor
     */
    public MainFrame()
    {
        super();
        
        initialize();
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.resource.ISpaceName#getSpaceName()
     */
    @Override
    public String getSpaceName()
    {
        return spaceName;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.resource.ISpaceName#setSpaceName(java.lang.String)
     */
    @Override
    public void setSpaceName(final String spaceName)
    {
        this.spaceName = spaceName;
    }

    /**
     * 构建数据库
     */
    private void rebuildDB()
    {
        int result = JOptionPane.showConfirmDialog(this,
                "The operation will rebuild the database, and all data will be clean.\nAre you sure you want to do?", 
                "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result != JOptionPane.YES_OPTION)
        {
            return;
        }
        
        ISystemInstallSvc systemInstallSvc = AbstractFootstoneActivator.getService(ISystemInstallSvc.class);
        try
        {
            //数据库删除停止事件分发
            systemInstallSvc.uninstall();
            systemInstallSvc.install();
            JOptionPane.showMessageDialog(this, "Rebuild success.You shuld restart this program.");
        }
        catch (Exception e)
        {
            log.error("start distributeEventSvc failed.", e);
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
    }

    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setJMenuBar(getMainMenuBar());
        this.setContentPane(getPnlMain());
        this.setTitle("Task Manager"); //$NON-NLS-1$
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
            lblStatus = new JLabel();
            lblStatus.setMinimumSize(new Dimension(10, 20));
            lblStatus.setText(""); //$NON-NLS-1$
            lblStatus.setPreferredSize(new Dimension(10, 20));
            pnlMain = new JPanel();
            pnlMain.setLayout(new BorderLayout());
            toolBar = createToolBar();
            pnlMain.add(toolBar, BorderLayout.NORTH);
            tabWorkspace = getTabWorkspace();
            pnlMain.add(lblStatus, BorderLayout.SOUTH);
            pnlMain.add(getDesktopPane(), BorderLayout.CENTER);
        }
        return pnlMain;
    }

    private JTabbedPane getTabWorkspace()
    {
        if (tabWorkspace == null)
        {
            tabWorkspace = new JTabbedPane();
        }
        return tabWorkspace;
    }

    private JToolBar createToolBar()
    {
        toolBar = new JToolBar();
        toolBar.setFloatable(true);
//        toolBar.add(createBtnShowWindow("Manage Task", TaskItemFrame.class));
//        toolBar.add(createBtnShowWindow("Manage Event", TaskEventFrame.class));
//        toolBar.add(createBtnShowWindow("Test", CheckCloseInternalFrame.class));
        if (Activator.getService(IDatabaseMgrSvc.class) != null && log.isDebugEnabled())
        {
            toolBar.add(createBtnShowWindow("Execute Sql", ExecuteSqlFrame.class));
        }
        return toolBar;
    }
    private JButton createBtnShowWindow(String title, Class<? extends JInternalFrame> frameClass)
    {
        JButton btn = new JButton(title);
        btn.setPreferredSize(new Dimension(80, 20));
        btn.addActionListener(new ShowWindowAction(frameClass));
        return btn;
    }

    private void showFrame(Class<? extends JInternalFrame> frameClass)
    {
        JInternalFrame frame = this.mapFrame.get(frameClass);
        if (frame == null)
        {
            try
            {
                frame = frameClass.newInstance();
            }
            catch (InstantiationException e)
            {
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                return;
            }
            catch (IllegalAccessException e)
            {
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                return;
            }
            mapFrame.put(frameClass, frame);
        }
        if (frame.isVisible())
        {
            frame.toFront();
        }
        else
        {
            this.desktopPane.add(frame);
//            frame.setMaximumSize(desktopPane.getSize());
            try
            {
                frame.setMaximum(true);
            }
            catch (PropertyVetoException e)
            {
                e.printStackTrace();
            }
            frame.setVisible(true);
        }
    }

    /**
     * This method initializes mainMenuBar	
     * 	
     * @return javax.swing.JMenuBar	
     */
    private JMenuBar getMainMenuBar()
    {
        if (mainMenuBar == null)
        {
            mainMenuBar = new JMenuBar();
            mainMenuBar.add(getMenuFile());
//            mainMenuBar.add(getMenuManage());
//            mainMenuBar.add(getMenuQuery());
            mainMenuBar.add(getMenuSetting());
            mainMenuBar.add(getMenuAbout());
        }
        return mainMenuBar;
    }

    /**
     * This method initializes desktopPane	
     * 	
     * @return javax.swing.JDesktopPane	
     */
    private JDesktopPane getDesktopPane()
    {
        if (desktopPane == null)
        {
            desktopPane = new JDesktopPane();
            desktopPane.add(new JButton("Hello"));
        }
        return desktopPane;
    }

    /**
     * This method initializes menuFile	
     * 	
     * @return javax.swing.JMenu	
     */
    private JMenu getMenuFile()
    {
        if (menuFile == null)
        {
            menuFile = new JMenu();
            menuFile.setText("File"); //$NON-NLS-1$
            menuFile.add(getMItmExit());
        }
        return menuFile;
    }

    /**
     * This method initializes mItmExit	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getMItmExit()
    {
        if (mItmExit == null)
        {
            mItmExit = new JMenuItem();
            AbstractAction a = new AbstractAction()
            {
                @Override
                public void actionPerformed(final ActionEvent e)
                {
                    System.exit(0);
                }
            };
            a.putValue(Action.NAME, "Exit"); //$NON-NLS-1$
            mItmExit.setAction(a);
        }
        return mItmExit;
    }


    /**
     * This method initializes menuManage	
     * 	
     * @return javax.swing.JMenu	
     */
    private JMenu getMenuManage()
    {
        if (menuManage == null)
        {
            menuManage = new JMenu();
            menuManage.setText(".Manage"); //$NON-NLS-1$
        }
        return menuManage;
    }

    /**
     * This method initializes menuQuery	
     * 	
     * @return javax.swing.JMenu	
     */
    private JMenu getMenuQuery()
    {
        if (menuQuery == null)
        {
            menuQuery = new JMenu();
            menuQuery.setText(".Query"); //$NON-NLS-1$
        }
        return menuQuery;
    }

    /**
     * This method initializes menuSetting	
     * 	
     * @return javax.swing.JMenu	
     */
    private JMenu getMenuSetting()
    {
        if (menuSetting == null)
        {
            menuSetting = new JMenu();
            menuSetting.setText("Setting"); //$NON-NLS-1$
//            menuSetting.add(getMItmQuerySetting());
//            menuSetting.add(getMItmSetting());
            menuSetting.add(getMItmRebuildDB());
        }
        return menuSetting;
    }

    /**
     * This method initializes menuAbout	
     * 	
     * @return javax.swing.JMenu	
     */
    private JMenu getMenuAbout()
    {
        if (menuAbout == null)
        {
            menuAbout = new JMenu();
            menuAbout.setText("Help"); //$NON-NLS-1$
            menuAbout.add(getMItmAbout());
        }
        return menuAbout;
    }

//    private class QueryAction extends AbstractAction
//    {
//        /*
//         * 需要运行的查询
//         */
//        private CFMQuery query;
//
//        /**
//         * 构造函数
//         * @param query 需要运行的查询
//         */
//        public QueryAction(final CFMQuery query)
//        {
//            this.query = query;
//            this.putValue(Action.NAME, query.getName());
//        }
//
//        /* (non-Javadoc)
//         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//         */
//        @Override
//        public void actionPerformed(final ActionEvent e)
//        {
//            QueryFrame qf = new QueryFrame(getExecutor(), project.getModel(), query);
////            qf.setDataKit(dataKit);
//            qf.setTitle(query.getName());
//            dataKit.getWindowHelper().regWindow(qf, "Query_"+query.getName()); //$NON-NLS-1$
//            desktopPane.add(qf);
//            qf.show();
//        }
//        
//        
//    }
    
//    /**
//     * <P>Title:      工具包 Util v1.0</P>
//     * <P>Description:管理动作</P>
//     * <P>Copyright:  Copyright (c) 2008</P>
//     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
//     * @author        BoRoBoRoMe
//     * @version       1.0 2008-4-5
//     */
//    private class ManageAction extends AbstractAction
//    {
//        /*
//         * 需要管理的数据库信息
//         */
//        private CFMObjectItem dbObjectItem;
//        
//                
//        /**
//         * 构造函数
//         * @param dbObjectItem 需要管理的数据库信息
//         */
//        public ManageAction(final CFMObjectItem dbObjectItem)
//        {
//            this.dbObjectItem = dbObjectItem;
//            this.putValue(Action.NAME, dbObjectItem.getName());
//        }
//
//        /* (non-Javadoc)
//         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//         */
//        @Override
//        public void actionPerformed(final ActionEvent e)
//        {
//            ManageFrame mf = new ManageFrame(dbObjectItem);
//            mf.setTitle(dbObjectItem.getName());
//            dataKit.getWindowHelper().regWindow(mf, dbObjectItem.getName());
//            desktopPane.add(mf);
//            mf.show();
//        }
//        
//    }

    /**
     * This method initializes mItmSetting	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getMItmSetting()
    {
        if (mItmSetting == null)
        {
            mItmSetting = new JMenuItem();
            mItmSetting.setText(".SysSet"); //$NON-NLS-1$
            mItmSetting.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
//                    configSettings();
                }
            });
        }
        return mItmSetting;
    }

    /**
     * This method initializes mItmAbout	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getMItmAbout()
    {
        if (mItmAbout == null)
        {
            mItmAbout = new JMenuItem();
            mItmAbout.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    showAbout();
                }
            });
            mItmAbout.setText("About"); //$NON-NLS-1$
        }
        return mItmAbout;
    }

    protected void showAbout()
    {
        JOptionPane.showMessageDialog(this, Activator.getService(IResourceMgrSvc.class).getRes(ResConst.ResKey, ResConst.About));
    }

    /**
     * This method initializes mItmRebuildDB	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getMItmRebuildDB()
    {
        if (mItmRebuildDB == null)
        {
            mItmRebuildDB = new JMenuItem();
            mItmRebuildDB.setText("RebuildDB"); //$NON-NLS-1$
            mItmRebuildDB.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    rebuildDB();
                }
            });
        }
        return mItmRebuildDB;
    }

    /**
     * This method initializes mItmQuerySet	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getMItmQuerySetting()
    {
        if (mItmQuerySetting == null)
        {
            mItmQuerySetting = new JMenuItem();
            mItmQuerySetting.setText(".QuerySetting"); //$NON-NLS-1$
            mItmQuerySetting.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
//                    querySettings();
                }
            });
        }
        return mItmQuerySetting;
    }
    private class ShowWindowAction implements ActionListener
    {
        private Class<? extends JInternalFrame> frameClass;

        /**
         * @param frameClass
         */
        public ShowWindowAction(Class<? extends JInternalFrame> frameClass)
        {
            super();
            this.frameClass = frameClass;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            showFrame(frameClass);
        }
    }
}
