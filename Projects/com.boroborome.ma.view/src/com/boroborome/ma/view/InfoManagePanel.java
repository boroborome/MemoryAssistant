/**
 * 
 */
package com.boroborome.ma.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.boroborme.ma.model.MAInformation;
import com.boroborome.footstone.ui.BaseReadonlyTableModel;
import com.boroborome.footstone.ui.ExtTable;

/**
 * @author boroborome
 *
 */
public class InfoManagePanel extends JPanel
{
	private JTextField txtKeys;
	private JTextArea txtInfoDetail;
	private BaseReadonlyTableModel<MAInformation> infoTableModel;
	private ExtTable infoTable;

	public InfoManagePanel()
	{
		initUI();
	}

	private void initUI()
	{
		this.setLayout(new GridBagLayout());
		this.add(new JLabel("Key:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		txtKeys = new JTextField();
		this.add(txtKeys, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 12, 0, 0), 0, 0));
		
		JSplitPane sptPnl = new JSplitPane();
		this.add(sptPnl, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(12, 0, 0, 0), 0, 0));
		sptPnl.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		sptPnl.setLeftComponent(createInfoMgrTable());
		sptPnl.setRightComponent(createInfoDetailPnl());
	}

	private JScrollPane createInfoDetailPnl()
	{
		txtInfoDetail = new JTextArea();
		return new JScrollPane(txtInfoDetail);
	}

	private Component createInfoMgrTable()
	{
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridBagLayout());
		pnl.add(createButtonPnl(), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		pnl.add(createInfoTable(), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(12, 0, 0, 0), 0, 0));
		return pnl;
	}

	private Component createInfoTable()
	{
		infoTableModel = new BaseReadonlyTableModel<MAInformation>(new String[]{"CreateTime", "KeyWord", "Content"})
		{
			@Override
			public Object[] formatItem(MAInformation data)
			{
				return new Object[]{data.getCreateTime(), "Keywords", data.getContent()};
			}
		};
		infoTable = new ExtTable();
		infoTable.setModel(infoTableModel);
		return infoTable;
	}

	private Component createButtonPnl()
	{
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridBagLayout());
		JButton btnAdd = new JButton("Create");
		btnAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				doAddInfo();
			}
		});
		pnl.add(btnAdd, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				doAddInfo();
			}
		});
		pnl.add(btnDelete, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		pnl.add(new JLabel(""), new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		return pnl;
	}
	

	private void doAddInfo()
	{
		// TODO Auto-generated method stub
		
	}
}
