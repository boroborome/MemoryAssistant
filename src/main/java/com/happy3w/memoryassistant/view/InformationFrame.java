/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据库信息的管理界面</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-5
 */
package com.happy3w.memoryassistant.view;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:执行Sql的界面</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-4-5
 */
@Component
public class InformationFrame extends JInternalFrame {

    private final InfoManagePanel infoManagePanel;

    public InformationFrame(InfoManagePanel infoManagePanel) {
        super();
        this.infoManagePanel = infoManagePanel;
    }

    @PostConstruct
    public void initUI() {
        this.setSize(900, 700);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setIconifiable(true);
        this.setClosable(true);
        this.setContentPane(infoManagePanel);
        this.setTitle("Manage Information");
    }
}
