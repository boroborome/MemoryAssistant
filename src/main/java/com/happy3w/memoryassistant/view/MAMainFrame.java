/**
 *
 */
package com.happy3w.memoryassistant.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author boroborome
 *
 */
public class MAMainFrame extends JFrame {
    public MAMainFrame() {
        initUI();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initUI() {
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(createPnlMain());
        this.setTitle("Task Manager"); //$NON-NLS-1$

        URL urlIcon = MAMainFrame.class.getResource("BiiBallLite.ico");
        Image img = Toolkit.getDefaultToolkit().getImage(urlIcon);
        this.setIconImage(img);
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel createPnlMain() {
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        InfoManagePanel pnlInfo = new InfoManagePanel();
        pnlMain.add(pnlInfo, BorderLayout.CENTER);
        return pnlMain;
    }
}
