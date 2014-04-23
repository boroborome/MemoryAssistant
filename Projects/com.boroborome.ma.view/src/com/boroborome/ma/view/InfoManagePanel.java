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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.ui.BaseReadonlyTableModel;
import com.boroborome.footstone.ui.ExtTable;
import com.boroborome.ma.model.MAInformation;
import com.boroborome.ma.model.MAInformationCondition;
import com.boroborome.ma.model.MAKeyword;
import com.boroborome.ma.model.svc.IMAInformationSvc;
import com.boroborome.ma.view.query.IQueryLogic;
import com.boroborome.ma.view.query.QueryAssistant;

/**
 * @author boroborome
 *
 */
public class InfoManagePanel extends JPanel
{
	private static Logger log = Logger.getLogger(InfoManagePanel.class);
	
	private KeywordField txtKeys;
	private BaseReadonlyTableModel<MAInformation> tblModelInfo;
	private ExtTable tblInfo;
	private InformationPanel pnlInfoDetail;
	private QueryAssistant<List<MAKeyword>, MAInformation> queryAssistant
		= new QueryAssistant<List<MAKeyword>, MAInformation>("Thread Query Information", new QueryInformationLogic());
	private int currentSelectRow = -1;
	
	public InfoManagePanel()
	{
		initUI();
		
		initActions();
	}

	private void initActions()
	{
		txtKeys.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				updateQueryCondition();				
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				updateQueryCondition();				
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				updateQueryCondition();				
			}
		});
	}
	
	private void updateQueryCondition()
	{
		List<MAKeyword> lstKey = txtKeys.getLstKeyword();
		queryAssistant.setCondtion(lstKey);
	}

	private void initUI()
	{
		//TODO [optimize] the ui of information management should be improve to more usefull
		this.setLayout(new GridBagLayout());
		
		JSplitPane sptPnl = new JSplitPane();
		this.add(sptPnl, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(12, 0, 0, 0), 0, 0));
		sptPnl.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		sptPnl.setLeftComponent(createInfoMgrTable());
		
		pnlInfoDetail = new InformationPanel();
		sptPnl.setRightComponent(pnlInfoDetail);
	}

	private Component createInfoMgrTable()
	{
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridBagLayout());
		
		int row = 0;
		pnl.add(new JLabel("Key:"), new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		txtKeys = new KeywordField();
		pnl.add(txtKeys, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 12, 0, 0), 0, 0));

		
		pnl.add(createButtonPnl(), new GridBagConstraints(0, ++row, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		pnl.add(createInfoTable(), new GridBagConstraints(0, ++row, 2, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(12, 0, 0, 0), 0, 0));
		return pnl;
	}

	private Component createInfoTable()
	{
		tblModelInfo = new BaseReadonlyTableModel<MAInformation>(new String[]{"CreateTime", "KeyWord", "Content"})
		{
			@Override
			public Object[] formatItem(MAInformation data)
			{
				return new Object[]{data.getCreateTime(), 
						MAKeyword.list2String(data.getLstKeyword()), 
						data.getContent()};
			}
		};
		tblInfo = new ExtTable();
		tblInfo.setModel(tblModelInfo);
		tblInfo.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				//if the panel is showing something,we should save it at first
				saveCurSelectInfo();
				
				currentSelectRow = tblInfo.getSelectedRow();
				if (currentSelectRow >= 0)
				{
					MAInformation info = tblModelInfo.getItem(currentSelectRow);
					pnlInfoDetail.setData(info);
				}
				else
				{
					pnlInfoDetail.setData(null);
				}
			}
		});
		return tblInfo;
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
				doDelInfo();
			}
		});
		pnl.add(btnDelete, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		pnl.add(new JLabel(""), new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		return pnl;
	}
	

	private void doDelInfo()
	{
		int[] selectRows = tblInfo.getSelectedRows();
		if (selectRows == null || selectRows.length == 0)
		{
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(this,
                "Are you sure to delete all selected information?", 
                "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result != JOptionPane.YES_OPTION)
        {
            return;
        }
        
        List<MAInformation> lstInfo = new ArrayList<MAInformation>();
        for (int row : selectRows)
        {
        	lstInfo.add(tblModelInfo.getItem(row));
        }
        IMAInformationSvc maInformationSvc = AbstractFootstoneActivator.getService(IMAInformationSvc.class);
		try
		{
			currentSelectRow = -1;//cancel current modify information
			maInformationSvc.delete(lstInfo.iterator());
			// TODO ［optimize] if delete partly failed the result will be wrong.
			for (int rowIndex = selectRows.length - 1; rowIndex >= 0; --rowIndex)
			{
				tblModelInfo.removeRow(selectRows[rowIndex]);
			}
		}
		catch (MessageException e)
		{
			log.error("failed in deleting information.", e);
            FootstoneSvcAccess.getExceptionGrave().bury(e);
		}
	}

	private void doAddInfo()
	{
		MAInformation info = new MAInformation();
		info.setLstKeyword(txtKeys.getLstKeyword());
		long curTime = System.currentTimeMillis();
		info.setCreateTime(curTime);
		info.setModifyTime(curTime);
		IMAInformationSvc maInformationSvc = AbstractFootstoneActivator.getService(IMAInformationSvc.class);
		try
		{
			maInformationSvc.create(Arrays.asList(info).iterator());
			tblModelInfo.insertItem(0, info);
			tblInfo.getSelectionModel().setSelectionInterval(0,  0);
		}
		catch (MessageException e)
		{
			log.error("failed in creating information.", e);
            FootstoneSvcAccess.getExceptionGrave().bury(e);
		}
	}
	
	private void saveCurSelectInfo()
	{
		if (currentSelectRow != -1)
		{
			MAInformation preInfo = new MAInformation();
			pnlInfoDetail.collectData(preInfo);
			preInfo.setModifyTime(System.currentTimeMillis());
			try
			{
				IMAInformationSvc maInformationSvc = AbstractFootstoneActivator.getService(IMAInformationSvc.class);
				maInformationSvc.modify(Arrays.asList(preInfo).iterator());
				tblModelInfo.justSetItem(currentSelectRow, preInfo);
			}
			catch (MessageException exp)
			{
				log.error("failed in modify information.", exp);
		        FootstoneSvcAccess.getExceptionGrave().bury(exp);
			}
		}
	}

	private class QueryInformationLogic implements IQueryLogic<List<MAKeyword>, MAInformation>
	{
		private IMAInformationSvc maInformationSvc;
		private MAInformationCondition cond = new MAInformationCondition();
		
		public QueryInformationLogic()
		{
			maInformationSvc = AbstractFootstoneActivator.getService(IMAInformationSvc.class);
		}
		@Override
		public void clearView()
		{
			saveCurSelectInfo();
			currentSelectRow = -1;
			tblModelInfo.clear();
		}

		@Override
		public Iterator<MAInformation> query(List<MAKeyword> condition) throws Exception
		{
			cond.setLstKeyword(condition);
			return maInformationSvc.query(cond);
		}

		@Override
		public void showData(Iterator<MAInformation> it) throws Exception
		{
			saveCurSelectInfo();
			currentSelectRow = -1;
			tblModelInfo.showData(it);			
		}
		
	}
}
