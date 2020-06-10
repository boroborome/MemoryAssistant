/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-16
 */
package com.happy3w.footstone.ui;

import com.happy3w.footstone.FootstoneSvcAccess;
import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Vector;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>执行Sql脚本的面板</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2011-10-16
 */
public class ExecuteSqlPanel extends JSplitPane {

    private static final int TAB_TEXT_RESULT = 0;
    private static final int TAB_TABLE_RESULT = 1;
    private JTextArea txtSql;
    private JTabbedPane tabPnl;
    private JTextArea txtResult;
    private JLabel lblTableResult;
    private JTable tblResult;
    private DefaultTableModel tblModel;

    /**
     * 构造函数
     */
    public ExecuteSqlPanel() {
        super();
        initUI();
    }

    private void initUI() {
        this.setPreferredSize(new Dimension(800, 700));
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setTopComponent(createSqlInputPnl());
        this.setBottomComponent(createSqlResultPnl());
        this.setDividerLocation(300);
    }

    private JTabbedPane createSqlResultPnl() {
        tabPnl = new JTabbedPane();
        tabPnl.addTab("Result", createTextResult());
        tabPnl.addTab("Table Result", createTableResult());
        return tabPnl;
    }

    private JPanel createTableResult() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new GridBagLayout());
        int yIndex = 0;
        pnl.add(createTablePanel(), new GridBagConstraints(0, yIndex++, 1, 1, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(8, 8, 0, 8), 0, 0));
        lblTableResult = new JLabel();
        pnl.add(lblTableResult, new GridBagConstraints(0, yIndex++, 1, 1, 1, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,
                new Insets(8, 8, 8, 8), 0, 0));
        return pnl;
    }

    private JScrollPane createTablePanel() {
        JScrollPane sp = new JScrollPane();
        tblResult = new JTable();
        tblModel = (DefaultTableModel) tblResult.getModel();
        tblResult.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                doRefreshTableInfo();
            }
        });
        sp.setViewportView(tblResult);
        return sp;
    }

    protected void doRefreshTableInfo() {
        int sRow = tblResult.getSelectedRow();
        int aRow = tblResult.getRowCount();
        String info = MessageFormat.format("No.:{0}/Total:{1}", (sRow < 0 ? " " : Integer.toString(sRow + 1)),
                Integer.toString(aRow));
        this.lblTableResult.setText(info);
    }

    private JScrollPane createTextResult() {
        JScrollPane sp = new JScrollPane();
        txtResult = new JTextArea();
        sp.setViewportView(txtResult);
        return sp;
    }

    private JPanel createSqlInputPnl() {
        JPanel pnlInput = new JPanel();
        pnlInput.setLayout(new GridBagLayout());
        int yIndex = 0;
        pnlInput.add(createInputSqlPnl(), new GridBagConstraints(0, yIndex++, 1, 1, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(8, 8, 0, 8), 0, 0));
        JButton btnExecute = new JButton("Execute");
        btnExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doExecuteSql();
            }
        });
        btnExecute.setPreferredSize(new Dimension(80, 20));
        pnlInput.add(btnExecute, new GridBagConstraints(0, yIndex++, 1, 1, 0, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(8, 8, 8, 8), 0, 0));
        return pnlInput;
    }

    /**
     * 获取用户录入sql信息的界面
     *
     * @return
     */
    public JTextArea getSqlArea() {
        return txtSql;
    }

    private void doExecuteSql() {
        IDatabaseMgrSvc svc = FootstoneSvcAccess.getDatabaseSvc();
        if (svc == null) {
            JOptionPane.showMessageDialog(this, "No database connection.");
            return;
        }
        try {
            String sql = txtSql.getSelectedText();
            if (sql == null || sql.isEmpty()) {
                sql = txtSql.getText();
            }
            sql = sql.trim();
            if ("select ".equalsIgnoreCase(sql.substring(0, 7))) {
                doExecuteQuery(svc, sql);
            } else {
                doExecuteUpdate(svc, sql);
            }

        } catch (Exception e) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(stream));
            txtResult.setText(new String(stream.toByteArray()));
            this.tabPnl.setSelectedIndex(TAB_TEXT_RESULT);
        }
    }

    private void doExecuteUpdate(IDatabaseMgrSvc svc, String sql) throws SQLException, MessageException {
        int count = svc.executeUpdate(sql);
        txtResult.setText(MessageFormat.format("{0} rows effected.", Integer.valueOf(count)));
        tabPnl.setSelectedIndex(TAB_TEXT_RESULT);
    }

    private void doExecuteQuery(IDatabaseMgrSvc svc, String sql) throws SQLException, MessageException {
        ResultSet rs = svc.executeQuery(sql);
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        //创建表头
        Vector<String> columnVec = new Vector<String>();
        for (int i = 1; i <= columnCount; i++) {
            columnVec.add(md.getColumnName(i));
        }

        //收集表内容
        Vector<Vector<Object>> dataVec = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> rowVec = new Vector<Object>();
            for (int i = 1; i <= columnCount; i++) {
                rowVec.add(rs.getObject(i));
            }
            dataVec.add(rowVec);
        }

        this.tblModel.setDataVector(dataVec, columnVec);
        this.tabPnl.setSelectedIndex(TAB_TABLE_RESULT);
    }

    private JScrollPane createInputSqlPnl() {
        JScrollPane sp = new JScrollPane();
        txtSql = new JTextArea();
        sp.setViewportView(txtSql);
        return sp;
    }

}
