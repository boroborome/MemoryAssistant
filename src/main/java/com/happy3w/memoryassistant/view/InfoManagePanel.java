/**
 * 
 */
package com.happy3w.memoryassistant.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.happy3w.memoryassistant.logic.MAInformationSvc;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAInformationCondition;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.utils.ContextHolder;
import com.happy3w.memoryassistant.view.query.IQueryLogic;
import com.happy3w.memoryassistant.view.query.QueryAssistant;
import com.happy3w.memoryassistant.view.wgt.IKeywordFieldListener;
import com.happy3w.memoryassistant.view.wgt.KeywordField;
import com.happy3w.memoryassistant.view.wgt.KeywordFieldEvent;
import org.apache.log4j.Logger;

import com.happy3w.footstone.FootstoneSvcAccess;
import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.model.AbstractWrapIterator;
import com.happy3w.footstone.ui.BaseReadonlyTableModel;
import com.happy3w.footstone.ui.ExtTable;
import com.happy3w.footstone.util.ConvertUtil;

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
		txtKeys.getEventContainer().addEventListener(new IKeywordFieldListener()
		{
			@Override
			public void onKeywordChange(KeywordFieldEvent e)
			{
				queryAssistant.setCondtion(e.getLstKey());
			}
		});
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
				return new Object[]{ConvertUtil.timeToStr(data.getCreateTime()), 
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
        MAInformationSvc maInformationSvc = ContextHolder.getBean(MAInformationSvc.class);
		try
		{
			currentSelectRow = -1;//cancel current modify information
			tblInfo.getSelectionModel().clearSelection();
			
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
		tblInfo.getSelectionModel().clearSelection();
		
		MAInformation info = new MAInformation();
		info.setLstKeyword(txtKeys.getLstKeyword());
		long curTime = System.currentTimeMillis();
		info.setCreateTime(curTime);
		info.setModifyTime(curTime);
		MAInformationSvc maInformationSvc = ContextHolder.getBean(MAInformationSvc.class);
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
				MAInformationSvc maInformationSvc = ContextHolder.getBean(MAInformationSvc.class);
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
		private MAInformationSvc maInformationSvc;
		private MAInformationCondition cond = new MAInformationCondition();
		
		public QueryInformationLogic()
		{
			maInformationSvc = ContextHolder.getBean(MAInformationSvc.class);
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
			KeywordCollectIterator collectIt = new KeywordCollectIterator(it);
			tblModelInfo.showData(collectIt);
			txtKeys.setAvailableKeyword(collectIt.getSetKeyword());
		}
		
	}
	
	private static class KeywordCollectIterator extends AbstractWrapIterator<MAInformation>
	{
		private Set<String> setKeyword = new HashSet<String>();
		
		public KeywordCollectIterator(Iterator<MAInformation> innerIt)
		{
			super(innerIt);
		}

		@Override
		public void beforeNext(MAInformation value)
		{
			//if the result keyword is more than 100, then ignore the others
			if (setKeyword.size() > 100)
			{
				return;
			}
			
			for (MAKeyword key : value.getLstKeyword())
			{
				setKeyword.add(key.getKeyword());
			}
		}

		/**
		 * @return the setKeyword
		 */
		public Set<String> getSetKeyword()
		{
			return setKeyword;
		}
	}
}
