/**
 * 
 */
package com.boroborome.ma.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.ui.BaseReadonlyTableModel;
import com.boroborome.footstone.ui.ExtTable;
import com.boroborome.ma.model.MAKeyword;
import com.boroborome.ma.model.MAKeywordCondition;
import com.boroborome.ma.model.MAKeywordFilterIterator;
import com.boroborome.ma.model.svc.IMAKeywordSvc;
import com.boroborome.ma.view.query.IQueryLogic;
import com.boroborome.ma.view.query.QueryAssistant;

/**
 * @author boroborome
 * TODO this control should return the keyword's id?
 * TODO we should implement the fun:select keyword from popup window
 */
public class KeywordField extends JTextField
{
	private Set<String> setExistKeyword = new HashSet<String>();
	private Set<String> setAvailableKeyword = new HashSet<String>();

	private JWindow popupWindow;

	private BaseReadonlyTableModel<MAKeyword> tblModelKey;

	private ExtTable tblKey;

	private QueryAssistant<String, MAKeyword> queryAssistant 
		= new QueryAssistant<String, MAKeyword>("Thread Query keyword for KeywordField",
				new KeywordQueryLogic());
	
	private Map<Integer, IPopupWindowKeyAction> mapKeyAction = new HashMap<Integer, IPopupWindowKeyAction>();
	/**
	 * 
	 */
	public KeywordField()
	{
		super();
		
		initPopupWindow();
		
		//Close the popup window when the focus is out of keyword field and popup window
		this.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				tryClosePopupWindow(e.getOppositeComponent());
			}});
		
		//when the text in keyword field is changed,show the popup window.monitoring the value change
		this.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				
			}
		});
		
		//when double click a keyword in popup window,the show this text in keyword field.
		tblKey.getInnerTable().addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
				{
					selectKeywordInPopWin();
				}
			}
		});
		
		//Init actions
		initPopupWindowKeyActions();
	}


	private void initPopupWindow()
	{
		popupWindow = new JWindow();
		tblModelKey = new BaseReadonlyTableModel<MAKeyword>(new String[]{"Key"})
		{
			@Override
			public Object[] formatItem(MAKeyword data)
			{
				return new Object[]{data.getKeyword()};
			}
		};
		tblKey = new ExtTable();
		tblKey.setModel(tblModelKey);
		popupWindow.setContentPane(tblKey);
		popupWindow.setSize(new Dimension(100, 200));
	}

	protected void tryClosePopupWindow(Component curFocusCom)
	{
		if (popupWindow != null && (curFocusCom != this && curFocusCom != popupWindow))
		{
			popupWindow.dispose();
		}
	}

	/**
	 * x:is the start of index in text
	 * y:is the end of index in text
	 * @return
	 */
	private Point findCurKeywordPos()
	{
		String strKeywords = this.getText();
		Point pos = new Point();
		pos.y = this.getCaretPosition();
		int startIndex = pos.y - 1;
		for (; startIndex >= 0 && strKeywords.charAt(startIndex) != MAKeyword.KeywordSplitChar; --startIndex);
		pos.x = startIndex + 1;
		
		return pos;
	}
	
	private void updatePopupInfo()
	{
		// find out the right posistion to show the popup window
		Point newPos = this.getLocation();
		SwingUtilities.convertPointToScreen(newPos, this.getParent());
		newPos.y += this.getHeight();
		newPos.x += this.getFontMetrics(getFont()).stringWidth(this.getText().substring(0, this.getCaretPosition()));
		popupWindow.setLocation(newPos);
		popupWindow.setVisible(true);
		
		//find out the word prefix to query
		Point preLocation = findCurKeywordPos();
		
		String strKeywords = this.getText();
		//if the prefix is empty then clear the popup window
		String prefix =  (preLocation.x == preLocation.y) ? "" : strKeywords.substring(preLocation.x, preLocation.y);
		
		setExistKeyword.clear();
		setExistKeyword.addAll(Arrays.asList(strKeywords.split(MAKeyword.KeywordSplitStr)));
		this.queryAssistant.setCondtion(prefix);
		
	}


	/**
	 * the keyword list shown in the keyword field<br>
	 * it is readonly.
	 * @return the lstKeyword
	 */
	public List<MAKeyword> getLstKeyword()
	{
		return MAKeyword.string2List(getText());
	}


	/**
	 * Set the available keyword set in popup window<br>
	 * if this set is not empty,only keyword in this set can show in popup window.
	 * @param setKeyword
	 */
	public void setAvailableKeyword(Set<String> setKeyword)
	{
		synchronized (setAvailableKeyword)
		{
			setAvailableKeyword.clear();
			if (setKeyword != null)
			{
				setAvailableKeyword.addAll(setKeyword);
			}
		}
	}
	
	/**
	 * @param lstKeyword the lstKeyword to set
	 */
	public void setLstKeyword(List<MAKeyword> lstKeyword)
	{
		StringBuilder buf = new StringBuilder();
		if (lstKeyword != null && !lstKeyword.isEmpty())
		{
			for (MAKeyword keyword : lstKeyword)
			{
				if (buf.length() > 0)
				{
					buf.append(MAKeyword.KeywordSplitChar);
				}
				buf.append(keyword.getKeyword());
			}
		}
		this.setText(buf.toString());	
	}

	private void selectKeywordInPopWin()
	{
		int selectRow = tblKey.getSelectedRow();
		if (selectRow >= 0)
		{
			MAKeyword keyword = tblModelKey.getItem(selectRow);
			
			//Find out the start and end of current keyword where cursor on
			int startIndex = this.getCaretPosition();
			int endIndex = startIndex;
			
			String strKeywords = this.getText();
			int keywordLen = strKeywords.length();
			if (startIndex >= keywordLen)
			{
				startIndex = keywordLen - 1;
			}
			for (; startIndex >= 0; --startIndex)
			{
				if (strKeywords.charAt(startIndex) == MAKeyword.KeywordSplitChar)
				{
					++startIndex;
					break;
				}
			}
			for (; endIndex < keywordLen && strKeywords.charAt(endIndex) != MAKeyword.KeywordSplitChar; ++endIndex)
			{
				if (strKeywords.charAt(endIndex) == MAKeyword.KeywordSplitChar)
				{
					--endIndex;
				}
			}
			
			//Make sure the startIndex and the endIndex is valid.
			if (startIndex < 0)
			{
				startIndex = 0;
			}
			else if (startIndex >= keywordLen)
			{
				startIndex = keywordLen - 1;
			}
			
			if (endIndex >= keywordLen)
			{
				endIndex = keywordLen;
			}
			
			//show selected keyword at the right pos
			if (startIndex == endIndex)
			{
				setText(getText() + keyword.getKeyword());
			}
			else
			{
				StringBuilder buf = new StringBuilder(strKeywords);
				buf.replace(startIndex, endIndex, keyword.getKeyword());
				setText(buf.toString());
			}
			
			setExistKeyword.add(keyword.getKeyword());
		}
	}

	private class KeywordQueryLogic implements IQueryLogic<String, MAKeyword>
	{
		private IMAKeywordSvc maKeywordSvc;
		private MAKeywordCondition cond;
		
		public KeywordQueryLogic()
		{
			maKeywordSvc = AbstractFootstoneActivator.getService(IMAKeywordSvc.class);
			cond = new MAKeywordCondition();
		}

		@Override
		public void clearView()
		{
			tblModelKey.clear();			
		}


		@Override
		public Iterator<MAKeyword> query(String condition) throws Exception
		{
			cond.setKeywordLike(condition);
			Iterator<MAKeyword> itKeyword = maKeywordSvc.query(cond);
			return new MAKeywordFilterIterator(itKeyword, setExistKeyword, setAvailableKeyword);
		}


		@Override
		public void showData(Iterator<MAKeyword> it) throws Exception
		{
			synchronized(setAvailableKeyword)
			{
				tblModelKey.showData(it);			
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#processKeyEvent(java.awt.event.KeyEvent)
	 */
	@Override
	protected void processKeyEvent(KeyEvent e)
	{
		IPopupWindowKeyAction action = this.mapKeyAction.get(Integer.valueOf(e.getKeyCode()));
		if (action == null)
		{
			action = this.mapKeyAction.get(Integer.valueOf(e.getKeyChar()));
		}
		if (action != null)
		{
			if (e.getID() != KeyEvent.KEY_PRESSED)
			{
				return;
			}
			if (popupWindow.isVisible() && tblModelKey.getRowCount() > 0)
			{
				action.doAction(e);
			}
		}
		else
		{
			super.processKeyEvent(e);
			updatePopupInfo();
		}
	}

	private void initPopupWindowKeyActions()
	{
		mapKeyAction.put(Integer.valueOf(KeyEvent.VK_ENTER), new IPopupWindowKeyAction()
		{
			@Override
			public void doAction(KeyEvent e)
			{
				selectKeywordInPopWin();				
			}
		});
		
		//Move the select row in popup window by array key
		IPopupWindowKeyAction arrowAction = new IPopupWindowKeyAction()
		{
			@Override
			public void doAction(KeyEvent e)
			{
				int selectRow = tblKey.getSelectedRow();
				if (e.getKeyCode() == KeyEvent.VK_UP)
				{
					--selectRow;
					if (selectRow < 0)
					{
						selectRow = tblModelKey.getRowCount() - 1;
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					++selectRow;
					if (selectRow >= tblModelKey.getRowCount())
					{
						selectRow = 0;
					}
				}
				tblKey.getSelectionModel().setSelectionInterval(selectRow, selectRow);
				tblKey.scrollRowToVisible(selectRow);				
			}
			
		};
		mapKeyAction.put(Integer.valueOf(KeyEvent.VK_UP), arrowAction);
		mapKeyAction.put(Integer.valueOf(KeyEvent.VK_DOWN), arrowAction);
		
		//Close the popup window
		mapKeyAction.put(Integer.valueOf(KeyEvent.VK_ESCAPE), new IPopupWindowKeyAction()
		{
			@Override
			public void doAction(KeyEvent e)
			{
				popupWindow.setVisible(false);
			}
		});
	}
	
	private interface IPopupWindowKeyAction
	{
		void doAction(KeyEvent e);
	}

}
