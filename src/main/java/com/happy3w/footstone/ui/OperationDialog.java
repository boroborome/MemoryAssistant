/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:操作对话框</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-20
 */
package com.happy3w.footstone.ui;

import com.happy3w.footstone.FootstoneSvcAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:操作对话框</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-20
 */
public class OperationDialog extends JDialog implements IWindowResumable {
    private static final String CANCEL_ACTION = "Cancel"; //$NON-NLS-1$

    private static final String OK_ACTION = "OK"; //$NON-NLS-1$

    /*
     * 负责处理数据的面板
     */
    private Component dataPanel;  //  @jve:decl-index=0:

    /*
     * 负责处理确定/应用按钮的操作
     */
    private IOperation operation;

    private JPanel pnlMain = null;
    private JPanel pnlButtons = null;
    private JPanel pnlInfo = null;
    private JButton btnOK = null;
    private JButton btnCancel = null;
    private JButton btnApply = null;
//    private IDataKit dataKit = DataKitBuilder.getDataKit();  //  @jve:decl-index=0:

    /*
     * 用户关闭对话框使用的动作
     */
    private int actionResult = JOptionPane.CANCEL_OPTION;
    private JButton btnClose = null;

    /**
     * 根据提供信息创建一个对话框
     *
     * @param parent          控件
     * @param dataPanel       数据面板
     * @param title           对话框标题
     * @param showApplyButton 显示应用按钮
     * @return 构建的对话框
     */
    public static OperationDialog createOperationDlg(final Component parent, final Component dataPanel,
                                                     final String title, final boolean showApplyButton) {
        IOperation operation = null;
        if (dataPanel instanceof IOperation) {
            operation = (IOperation) dataPanel;
        }
        return createOperationDlg(parent, dataPanel, operation, title, showApplyButton);
    }

    /**
     * 根据提供信息创建一个对话框
     *
     * @param parent          控件
     * @param dataPanel       数据面板
     * @param operation       确定/应用按钮需要做的操作
     * @param title           对话框标题
     * @param showApplyButton 显示应用按钮
     * @return 构建的对话框
     */
    public static OperationDialog createOperationDlg(final Component parent, final Component dataPanel,
                                                     final IOperation operation, final String title, final boolean showApplyButton) {
        //查找顶级面板
        Container c = null;
        if (parent instanceof Container) {
            c = (Container) parent;
        } else if (parent != null) {
            c = parent.getParent();
        }

        while (c != null) {
            if (c instanceof Frame || c instanceof Dialog) {
                break;
            }
            c = c.getParent();
        }
        if (c == null) {
            c = JOptionPane.getRootFrame();
        }

        //构造面板
        OperationDialog dlg = null;
        if (c instanceof Dialog) {
            dlg = new OperationDialog((Dialog) c, dataPanel);
        } else if (c instanceof Frame) {
            dlg = new OperationDialog((Frame) c, dataPanel);
        } else {
            dlg = new OperationDialog(dataPanel);
        }
        dlg.setApplyButtonVisible(showApplyButton);
        dlg.operation = operation;
        dlg.setTitle(title);

        return dlg;
    }

    /**
     * 通过给定控件找到顶级窗口
     *
     * @param parent          控件
     * @param dataPanel       数据面板
     * @param title           对话框标题
     * @param showApplyButton 显示应用按钮
     * @return 用户操作结果
     */
    public static int show(final Component parent, final Component dataPanel,
                           final String title, final boolean showApplyButton) {
        //构造面板
        OperationDialog dlg = createOperationDlg(parent, dataPanel, title, showApplyButton);
        dlg.setVisible(true);

        return dlg.getActionResult();
    }

    /**
     * 通过给定控件找到顶级窗口
     *
     * @param parent          控件
     * @param dataPanel       数据面板
     * @param operation       确定/应用按钮需要做的操作
     * @param title           对话框标题
     * @param showApplyButton 显示应用按钮
     * @return 用户操作结果
     */
    public static int show(final Component parent, final Component dataPanel,
                           final IOperation operation, final String title, final boolean showApplyButton) {
        //构造面板
        OperationDialog dlg = createOperationDlg(parent, dataPanel, operation, title, showApplyButton);
        dlg.setVisible(true);

        return dlg.getActionResult();
    }

    /**
     * 构造函数
     *
     * @param dataPanel 编辑数据的面板
     */
    public OperationDialog(final Component dataPanel) {
        commonInit(dataPanel);
    }

    /**
     * 构造函数
     *
     * @param owner     所有者
     * @param dataPanel 编辑数据的面板
     */
    public OperationDialog(final Dialog owner, final Component dataPanel) {
        super(owner);
        commonInit(dataPanel);
    }

    /**
     * 构造函数
     *
     * @param owner     所有者
     * @param dataPanel 编辑数据的面板
     */
    public OperationDialog(final Frame owner, final Component dataPanel) {
        super(owner);
        commonInit(dataPanel);
    }

    /**
     * 初始化界面
     *
     * @param newDataPnl 编辑数据的面板
     */
    private void commonInit(final Component newDataPnl) {
//        if (dataKit == null || newDataPnl == null)
//        {
//            throw new IllegalArgumentException("Constructor not accept null param"); //$NON-NLS-1$
//        }

        this.dataPanel = newDataPnl;

        initialize();

        pnlInfo.add(newDataPnl, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
//        DisplayUtil.updateText(this, ExtUIRes.getRes(), "OperationDialog"); //$NON-NLS-1$

        //设置确认按钮和取消按钮的加速键
        ActionMap actionMap = this.getRootPane().getActionMap();
        InputMap inputMap = this.getRootPane().getInputMap();

        //设置确定按钮快捷键为回车
        inputMap.put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), OK_ACTION);
        actionMap.put(OK_ACTION, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doOK();
            }
        });

        //设置取消按钮的快捷键为ESC
        inputMap.put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), CANCEL_ACTION);
        actionMap.put(CANCEL_ACTION, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });

        pack();
        DisplayUtil.center(this);
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        this.setSize(new Dimension(468, 267));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setContentPane(getPnlMain());
    }

    /**
     * This method initializes pnlMain
     *
     * @return javax.swing.JPanel
     */
    private JPanel getPnlMain() {
        if (pnlMain == null) {
            GridBagConstraints gdbcPnlInfo = new GridBagConstraints();
            gdbcPnlInfo.gridx = 0;
            gdbcPnlInfo.fill = GridBagConstraints.BOTH;
            gdbcPnlInfo.weightx = 1.0D;
            gdbcPnlInfo.weighty = 1.0D;
            gdbcPnlInfo.insets = new Insets(12, 12, 0, 12);
            gdbcPnlInfo.gridy = 0;
            GridBagConstraints gdbcPnlButtons = new GridBagConstraints();
            gdbcPnlButtons.gridx = 0;
            gdbcPnlButtons.weightx = 1.0D;
            gdbcPnlButtons.fill = GridBagConstraints.HORIZONTAL;
            gdbcPnlButtons.insets = new Insets(12, 12, 12, 12);
            gdbcPnlButtons.gridy = 1;
            pnlMain = new JPanel();
            pnlMain.setLayout(new GridBagLayout());
            pnlMain.add(getPnlButtons(), gdbcPnlButtons);
            pnlMain.add(getPnlInfo(), gdbcPnlInfo);
        }
        return pnlMain;
    }

    /**
     * This method initializes pnlButtons
     *
     * @return javax.swing.JPanel
     */
    private JPanel getPnlButtons() {
        if (pnlButtons == null) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.insets = new Insets(0, 4, 0, 0);
            gridBagConstraints.gridy = 0;
            GridBagConstraints gdbcBtnApply = new GridBagConstraints();
            gdbcBtnApply.gridx = 3;
            gdbcBtnApply.insets = new Insets(0, 4, 0, 0);
            gdbcBtnApply.gridy = 0;
            GridBagConstraints gdbcBtnCancel = new GridBagConstraints();
            gdbcBtnCancel.gridx = 1;
            gdbcBtnCancel.insets = new Insets(0, 4, 0, 0);
            gdbcBtnCancel.gridy = 0;
            GridBagConstraints gdbcBtnOK = new GridBagConstraints();
            gdbcBtnOK.gridx = 0;
            gdbcBtnOK.weightx = 1.0D;
            gdbcBtnOK.anchor = GridBagConstraints.EAST;
            gdbcBtnOK.gridy = 0;
            pnlButtons = new JPanel();
            pnlButtons.setLayout(new GridBagLayout());
            pnlButtons.add(getBtnOK(), gdbcBtnOK);
            pnlButtons.add(getBtnCancel(), gdbcBtnCancel);
            pnlButtons.add(getBtnApply(), gdbcBtnApply);
            pnlButtons.add(getBtnClose(), gridBagConstraints);
        }
        return pnlButtons;
    }

    /**
     * This method initializes pnlInfo
     *
     * @return javax.swing.JPanel
     */
    private JPanel getPnlInfo() {
        if (pnlInfo == null) {
            pnlInfo = new JPanel();
            pnlInfo.setLayout(new GridBagLayout());
        }
        return pnlInfo;
    }

    /**
     * This method initializes btnOK
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnOK() {
        if (btnOK == null) {
            btnOK = new JButton();
            btnOK.setText("OK"); //$NON-NLS-1$
            btnOK.setMnemonic(KeyEvent.VK_ENTER);
            btnOK.setPreferredSize(new Dimension(80, 20));
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doOK();
                }

            });
        }
        return btnOK;
    }

    /**
     * This method initializes btnCancel
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setPreferredSize(new Dimension(80, 20));
            btnCancel.setText("Cancel"); //$NON-NLS-1$
            btnCancel.setMnemonic(KeyEvent.VK_ESCAPE);
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    doCancel();
                }

            });
        }
        return btnCancel;
    }

    /**
     * This method initializes btnApply
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnApply() {
        if (btnApply == null) {
            btnApply = new JButton();
            btnApply.setText("Apply"); //$NON-NLS-1$
            btnApply.setMnemonic(KeyEvent.VK_A);
            btnApply.setPreferredSize(new Dimension(80, 20));
            btnApply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doApply();
                }

            });
        }
        return btnApply;
    }

    /**
     * 确定事件
     */
    private void doOK() {
        if (doOperation()) {
            actionResult = JOptionPane.OK_OPTION;
            dispose();
        }
    }

    /**
     * 取消动作
     */
    private void doCancel() {
        actionResult = JOptionPane.CANCEL_OPTION;
        dispose();
    }

    /**
     * 应用事件
     */
    private void doApply() {
        if (doOperation()) {
            btnApply.setEnabled(false);
        }
    }

    /**
     * 处理业务
     *
     * @return 操作成功标记
     */
    @SuppressWarnings("unchecked")
    private boolean doOperation() {
        boolean result = true;
        if (dataPanel instanceof AbstractDataPanel) {
            try {
                AbstractDataPanel dp = (AbstractDataPanel) dataPanel;
                dp.verifyInput();

                result = true;
                if (operation != null) {
                    result = operation.doOperation();
                }
            } catch (Exception e) {
                //虽然这里明显的抛出异常只有verifyInput的InputException，但是doOperation可能会抛出任意异常
                FootstoneSvcAccess.getExceptionGrave().bury(e);
                result = false;
            }
        }
        return result;
    }

    /**
     * 获取应用按钮的可见性
     *
     * @return 应用按钮的可见性
     */
    public boolean isApplyButtonVisible() {
        return btnApply.isVisible();
    }

    /**
     * 设置应用按钮的可见性
     *
     * @param visible 应用按钮的可见性
     */
    public void setApplyButtonVisible(final boolean visible) {
        btnApply.setVisible(visible);
    }

    /**
     * 获取确定按钮的可见性
     *
     * @return 确定按钮的可见性
     */
    public boolean isOKButtonVisible() {
        return btnOK.isVisible();
    }

    /**
     * 设置确定按钮的可见性
     *
     * @param visible 确定按钮的可见性
     */
    public void setOKButtonVisible(final boolean visible) {
        btnOK.setVisible(visible);
    }

    /**
     * 获取取消按钮的可见性
     *
     * @return 取消按钮的可见性
     */
    public boolean isCancelButtonVisible() {
        return btnCancel.isVisible();
    }

    /**
     * 设置取消按钮的可见性
     *
     * @param visible 取消按钮的可见性
     */
    public void setCancelButtonVisible(final boolean visible) {
        btnCancel.setVisible(visible);
    }

    /**
     * 获取关闭按钮的可见性
     *
     * @return 关闭按钮的可见性
     */
    public boolean isCloseButtonVisible() {
        return btnClose.isVisible();
    }

    /**
     * 设置关闭按钮的可见性
     *
     * @param visible 关闭按钮的可见性
     */
    public void setCloseButtonVisible(final boolean visible) {
        btnClose.setVisible(visible);
    }

    /**
     * 显示对话框，并返回操作结果
     *
     * @return 操作结果
     */
    public int showDialog() {
        this.setVisible(true);
        return getActionResult();
    }

    /**
     * 获取关闭对话框操作，Cancel/OK
     *
     * @return 关闭对话框操作
     */
    public int getActionResult() {
        return actionResult;
    }

    /**
     * 获取编辑数据面板
     *
     * @return 编辑数据面板
     */
    public Component getDataPanel() {
        return dataPanel;
    }

    /* (non-Javadoc)
     * @see com.boroborome.extui.uitl.IWindowResumable#loadInfo(com.boroborome.extui.uitl.WindowInfo)
     */
    public void loadInfo(final WindowInfo info) {
        if (dataPanel instanceof IWindowResumable) {
            IWindowResumable r = (IWindowResumable) dataPanel;
            r.loadInfo(info);
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.extui.uitl.IWindowResumable#saveInfo(com.boroborome.extui.uitl.WindowInfo)
     */
    public void saveInfo(final WindowInfo info) {
        if (dataPanel instanceof IWindowResumable) {
            IWindowResumable r = (IWindowResumable) dataPanel;
            r.saveInfo(info);
        }
    }

    /**
     * This method initializes btnClose
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnClose() {
        if (btnClose == null) {
            btnClose = new JButton();
            btnClose.setPreferredSize(new Dimension(80, 20));
            btnClose.setText("Close"); //$NON-NLS-1$
            btnClose.setMnemonic(KeyEvent.VK_ESCAPE);
            btnClose.setVisible(false);
            btnClose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doCancel();
                }
            });
        }
        return btnClose;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
