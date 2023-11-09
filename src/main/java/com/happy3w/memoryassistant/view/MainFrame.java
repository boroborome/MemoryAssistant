package com.happy3w.memoryassistant.view;

import com.happy3w.footstone.FootstoneSvcAccess;
import com.happy3w.footstone.resource.IResourceMgrSvc;
import com.happy3w.footstone.resource.ISpaceName;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;
import com.happy3w.footstone.svc.ISystemInstallSvc;
import com.happy3w.footstone.ui.ExecuteSqlPanel;
import com.happy3w.memoryassistant.service.MemoryService;
import com.happy3w.memoryassistant.view.res.ResConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Component
@Slf4j
public class MainFrame extends JFrame implements ISpaceName {
    public static final String DefualtSpaceName = "MainFrame";

    private final ISystemInstallSvc systemInstallSvc;
    private final Optional<IDatabaseMgrSvc> iDatabaseMgrSvc;
    private final IResourceMgrSvc resourceMgrSvc;
    private final ApplicationContext applicationContext;
    private final MemoryService memoryService;

    private JLabel lblStatus = new JLabel();
    private JDesktopPane desktopPane = new JDesktopPane();

    private String spaceName = DefualtSpaceName;

    private Map<Class<? extends JComponent>, JInternalFrame> mapFrame = new HashMap<>();

    public MainFrame(ISystemInstallSvc systemInstallSvc,
                     Optional<IDatabaseMgrSvc> iDatabaseMgrSvc,
                     IResourceMgrSvc resourceMgrSvc,
                     ApplicationContext applicationContext, MemoryService memoryService) {
        super();
        this.systemInstallSvc = systemInstallSvc;
        this.iDatabaseMgrSvc = iDatabaseMgrSvc;
        this.resourceMgrSvc = resourceMgrSvc;
        this.applicationContext = applicationContext;
        this.memoryService = memoryService;
        URL iconUrl = MainFrame.class.getResource("/brain.png");
        ImageIcon icon = new ImageIcon(iconUrl);
        this.setIconImage(icon.getImage());
    }

    @PostConstruct
    public void initUI() {
        initialize();
        showFrame(InfoManagePanel.class, "Manage Info");
    }

    @Override
    public String getSpaceName() {
        return spaceName;
    }

    @Override
    public void setSpaceName(final String spaceName) {
        this.spaceName = spaceName;
    }

    private void rebuildDB() {
        int result = JOptionPane.showConfirmDialog(this,
                "The operation will rebuild the database, and all data will be clean.\nAre you sure you want to do?",
                "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }

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
        this.setTitle("Memory Assistant");
    }

    private JPanel createPnlMain() {
        lblStatus.setMinimumSize(new Dimension(10, 20));
        lblStatus.setText("");
        lblStatus.setPreferredSize(new Dimension(10, 20));
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        JToolBar toolBar = createToolBar();
        pnlMain.add(toolBar, BorderLayout.NORTH);
        pnlMain.add(lblStatus, BorderLayout.SOUTH);
        pnlMain.add(desktopPane, BorderLayout.CENTER);
        return pnlMain;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true);
//        toolBar.add(createBtnShowWindow("Manage Task", TaskItemFrame.class));
//        toolBar.add(createBtnShowWindow("Manage Event", TaskEventFrame.class));
//        toolBar.add(createBtnShowWindow("Test", CheckCloseInternalFrame.class));
        toolBar.add(createBtnShowWindow("Manage Info", InformationPanel.class));
//        toolBar.add(createUpgradeAction());
        if (iDatabaseMgrSvc.isPresent() && log.isDebugEnabled()) {
            toolBar.add(createBtnShowWindow("Execute Sql", ExecuteSqlPanel.class));
        }
        return toolBar;
    }

    private JButton createBtnShowWindow(String title, Class<? extends JComponent> panelType) {
        JButton btn = new JButton(title);
        btn.setPreferredSize(new Dimension(80, 20));
        btn.addActionListener(new ShowWindowAction(panelType, title));
        return btn;
    }

    private JInternalFrame createInternalFrame(Class<? extends JComponent> panelType, String title) throws InstantiationException, IllegalAccessException {
        JComponent panel = applicationContext.getBean(panelType);
        if (panel == null) {
            panel = panelType.newInstance();
        }

        JInternalFrame frame = new JInternalFrame();
        frame.setSize(900, 700);
        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setIconifiable(true);
        frame.setClosable(true);
        frame.setContentPane(panel);
        frame.setTitle(title);
        return frame;
    }

    private void showFrame(Class<? extends JComponent> panelType, String title) {
        JInternalFrame frame = this.mapFrame.get(panelType);
        if (frame == null) {
            try {
                frame = createInternalFrame(panelType, title);
            } catch (InstantiationException e) {
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                return;
            } catch (IllegalAccessException e) {
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                return;
            }
            mapFrame.put(panelType, frame);
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
        mainMenuBar.add(createMenuManage());
//            mainMenuBar.add(getMenuQuery());
        mainMenuBar.add(createMenuSetting());
        mainMenuBar.add(createMenuAbout());
        return mainMenuBar;
    }

    private Component createMenuManage() {
        JMenu menuFile = new JMenu();
        menuFile.setText("Manage");
        menuFile.add(createMItmExport());
        menuFile.add(createMItmImport());
        return menuFile;
    }

    private JMenuItem createMItmImport() {
        JMenuItem menuItem = new JMenuItem("Import");
        menuItem.addActionListener(e -> doImport());
        return menuItem;
    }

    private JMenuItem createMItmExport() {
        JMenuItem menuItem = new JMenuItem("Export");
        menuItem.addActionListener(e -> doExport());
        return menuItem;
    }

    private JMenu createMenuFile() {
        JMenu menuFile = new JMenu();
        menuFile.setText("File");
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
        a.putValue(Action.NAME, "Exit");
        mItmExit.setAction(a);
        return mItmExit;
    }

    private JMenu createMenuSetting() {
        JMenu menuSetting = new JMenu();
        menuSetting.setText("Setting");
//            menuSetting.add(getMItmQuerySetting());
//            menuSetting.add(getMItmSetting());
        menuSetting.add(createMItmRebuildDB());
        return menuSetting;
    }

    private JMenu createMenuAbout() {
        JMenu menuAbout = new JMenu();
        menuAbout.setText("Help");
        menuAbout.add(createMItmAbout());
        return menuAbout;
    }

    private JMenuItem createMItmAbout() {
        JMenuItem mItmAbout = new JMenuItem();
        mItmAbout.addActionListener(e -> showAbout());
        mItmAbout.setText("About");
        return mItmAbout;
    }

    protected void showAbout() {
        JOptionPane.showMessageDialog(this, resourceMgrSvc.getRes(ResConst.ResKey, ResConst.About));
    }

    private JMenuItem createMItmRebuildDB() {
        JMenuItem mItmRebuildDB = new JMenuItem();
        mItmRebuildDB.setText("RebuildDB");
        mItmRebuildDB.addActionListener(e -> rebuildDB());
        return mItmRebuildDB;
    }

    private void doExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("请选择保存文件目录");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File fileToSave = fileChooser.getSelectedFile();
        try {
            memoryService.exportData(fileToSave);
        } catch (IOException e) {
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
    }

    private void doImport() {
        int result = JOptionPane.showConfirmDialog(this, "所有现有数据会被覆盖，确认导入吗");
        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("请选择导入文件目录");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File fileToSave = fileChooser.getSelectedFile();
        try {
            memoryService.importData(fileToSave);
        } catch (IOException e) {
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
    }

    private class ShowWindowAction implements ActionListener {
        private final Class<? extends JComponent> panelType;
        private final String title;

        public ShowWindowAction(Class<? extends JComponent> panelType, String title) {
            super();
            this.panelType = panelType;
            this.title = title;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showFrame(panelType, title);
        }
    }
}
