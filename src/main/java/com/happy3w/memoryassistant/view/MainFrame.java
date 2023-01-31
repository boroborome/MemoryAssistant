package com.happy3w.memoryassistant.view;

import com.happy3w.footstone.FootstoneSvcAccess;
import com.happy3w.footstone.resource.IResourceMgrSvc;
import com.happy3w.footstone.resource.ISpaceName;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;
import com.happy3w.footstone.svc.ISystemInstallSvc;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.service.MAInformationSvc;
import com.happy3w.memoryassistant.utils.ContextHolder;
import com.happy3w.memoryassistant.view.res.ResConst;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MainFrame extends JFrame implements ISpaceName {
    /**
     * 默认的资源名字空间
     */
    public static final String DefualtSpaceName = "MainFrame";  //  @jve:decl-index=0: //$NON-NLS-1$

    private JLabel lblStatus = new JLabel();
    private JDesktopPane desktopPane = null;

    private String spaceName = DefualtSpaceName;  //  @jve:decl-index=0:

    private Map<Class<? extends JInternalFrame>, JInternalFrame> mapFrame = new HashMap<Class<? extends JInternalFrame>, JInternalFrame>();

    public MainFrame() {
        super();

        initialize();
        showFrame(InformationFrame.class);
    }

    @Override
    public String getSpaceName() {
        return spaceName;
    }

    @Override
    public void setSpaceName(final String spaceName) {
        this.spaceName = spaceName;
    }

    /**
     * 构建数据库
     */
    private void rebuildDB() {
        int result = JOptionPane.showConfirmDialog(this,
                "The operation will rebuild the database, and all data will be clean.\nAre you sure you want to do?",
                "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        ISystemInstallSvc systemInstallSvc = ContextHolder.getBean(ISystemInstallSvc.class);
        try {
            //数据库删除停止事件分发
            systemInstallSvc.uninstall();
            systemInstallSvc.install();
            JOptionPane.showMessageDialog(this, "Rebuild success.You shuld restart this program.");
        } catch (Exception e) {
            log.error("start distributeEventSvc failed.", e);
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
    }

    private void initialize() {
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setJMenuBar(createMainMenuBar());
        this.setContentPane(createPnlMain());
        this.setTitle("Memory Assistant"); //$NON-NLS-1$
    }

    private JPanel createPnlMain() {
        lblStatus.setMinimumSize(new Dimension(10, 20));
        lblStatus.setText(""); //$NON-NLS-1$
        lblStatus.setPreferredSize(new Dimension(10, 20));
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        JToolBar toolBar = createToolBar();
        pnlMain.add(toolBar, BorderLayout.NORTH);
        pnlMain.add(lblStatus, BorderLayout.SOUTH);
        pnlMain.add(createDesktopPane(), BorderLayout.CENTER);
        return pnlMain;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true);
//        toolBar.add(createBtnShowWindow("Manage Task", TaskItemFrame.class));
//        toolBar.add(createBtnShowWindow("Manage Event", TaskEventFrame.class));
//        toolBar.add(createBtnShowWindow("Test", CheckCloseInternalFrame.class));
        toolBar.add(createBtnShowWindow("Manage Info", InformationFrame.class));
//        toolBar.add(createUpgradeAction());
        if (ContextHolder.getBean(IDatabaseMgrSvc.class) != null && log.isDebugEnabled()) {
            toolBar.add(createBtnShowWindow("Execute Sql", ExecuteSqlFrame.class));
        }
        return toolBar;
    }

    private void doUpgrade() throws ClassNotFoundException, SQLException {
        MAInformationSvc maInformationSvc = ContextHolder.getBean(MAInformationSvc.class);

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String url = "jdbc:derby:database/madb;create=true";

        this.getClass().getClassLoader().loadClass(driver);
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet orgDataSet = statement.executeQuery("select * from tblInformation");
        while (orgDataSet.next()) {
            MAInformation maInformation = new MAInformation();
            maInformation.setContent(orgDataSet.getString("content"));
            maInformation.setLstKeyword(MAKeyword.string2List(orgDataSet.getString("keywords")));

            maInformationSvc.create(Arrays.asList(maInformation).iterator());
        }
    }

    private JButton createUpgradeAction() {
        JButton btn = new JButton("Upgrade");
        btn.setPreferredSize(new Dimension(80, 20));
        btn.addActionListener(e -> {
            try {
                doUpgrade();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        return btn;
    }

    private JButton createBtnShowWindow(String title, Class<? extends JInternalFrame> frameClass) {
        JButton btn = new JButton(title);
        btn.setPreferredSize(new Dimension(80, 20));
        btn.addActionListener(new ShowWindowAction(frameClass));
        return btn;
    }

    private void showFrame(Class<? extends JInternalFrame> frameClass) {
        JInternalFrame frame = this.mapFrame.get(frameClass);
        if (frame == null) {
            try {
                frame = frameClass.newInstance();
            } catch (InstantiationException e) {
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                return;
            } catch (IllegalAccessException e) {
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                return;
            }
            mapFrame.put(frameClass, frame);
        }
        if (frame.isVisible()) {
            frame.toFront();
        } else {
            this.desktopPane.add(frame);
//            frame.setMaximumSize(desktopPane.getSize());
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            frame.setVisible(true);
        }
    }

    private JMenuBar createMainMenuBar() {
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.add(createMenuFile());
//            mainMenuBar.add(getMenuManage());
//            mainMenuBar.add(getMenuQuery());
        mainMenuBar.add(createMenuSetting());
        mainMenuBar.add(createMenuAbout());
        return mainMenuBar;
    }

    private JDesktopPane createDesktopPane() {
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.add(new JButton("Hello"));
        return desktopPane;
    }

    private JMenu createMenuFile() {
        JMenu menuFile = new JMenu();
        menuFile.setText("File"); //$NON-NLS-1$
        menuFile.add(createMItmExit());
        return menuFile;
    }

    private JMenuItem createMItmExit() {
        JMenuItem mItmExit = new JMenuItem();
        AbstractAction a = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        };
        a.putValue(Action.NAME, "Exit"); //$NON-NLS-1$
        mItmExit.setAction(a);
        return mItmExit;
    }

    private JMenu createMenuSetting() {
        JMenu menuSetting = new JMenu();
        menuSetting.setText("Setting"); //$NON-NLS-1$
//            menuSetting.add(getMItmQuerySetting());
//            menuSetting.add(getMItmSetting());
        menuSetting.add(createMItmRebuildDB());
        return menuSetting;
    }

    private JMenu createMenuAbout() {
        JMenu menuAbout = new JMenu();
        menuAbout.setText("Help"); //$NON-NLS-1$
        menuAbout.add(createMItmAbout());
        return menuAbout;
    }

    private JMenuItem createMItmAbout() {
        JMenuItem mItmAbout = new JMenuItem();
        mItmAbout.addActionListener(e -> showAbout());
        mItmAbout.setText("About"); //$NON-NLS-1$
        return mItmAbout;
    }

    protected void showAbout() {
        JOptionPane.showMessageDialog(this, ContextHolder.getBean(IResourceMgrSvc.class).getRes(ResConst.ResKey, ResConst.About));
    }

    private JMenuItem createMItmRebuildDB() {
        JMenuItem mItmRebuildDB = new JMenuItem();
        mItmRebuildDB.setText("RebuildDB"); //$NON-NLS-1$
        mItmRebuildDB.addActionListener(e -> rebuildDB());
        return mItmRebuildDB;
    }

    private class ShowWindowAction implements ActionListener {
        private Class<? extends JInternalFrame> frameClass;

        /**
         * @param frameClass
         */
        public ShowWindowAction(Class<? extends JInternalFrame> frameClass) {
            super();
            this.frameClass = frameClass;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showFrame(frameClass);
        }
    }
}
