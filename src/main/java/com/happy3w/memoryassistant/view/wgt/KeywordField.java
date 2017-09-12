/**
 * 
 */
package com.happy3w.memoryassistant.view.wgt;

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

import com.happy3w.footstone.model.EventContainer;
import com.happy3w.footstone.ui.BaseReadonlyTableModel;
import com.happy3w.footstone.ui.ExtTable;
import com.happy3w.memoryassistant.logic.MAKeywordSvc;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.model.MAKeywordCondition;
import com.happy3w.memoryassistant.model.MAKeywordFilterIterator;
import com.happy3w.memoryassistant.utils.ContextHolder;
import com.happy3w.memoryassistant.view.query.IQueryLogic;
import com.happy3w.memoryassistant.view.query.QueryAssistant;

/**
 * @author boroborome
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
	
	private EventContainer<IKeywordFieldListener> eventContainer = new EventContainer<IKeywordFieldListener>(IKeywordFieldListener.class);
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
					doSelectKeywordInPopWin();
				}
			}
		});
		
		//Init actions
		initPopupWindowKeyActions();
	}

	/**
	 * @return the eventContainer
	 */
	public EventContainer<IKeywordFieldListener> getEventContainer()
	{
		return eventContainer;
	}

	public boolean isPopupWindowVisible()
	{
		return (this.popupWindow != null && popupWindow.isVisible());
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
		//find out the word prefix to query
		Point preLocation = findCurKeywordPos();
		
		String strKeywords = this.getText();
		//if the prefix is empty then clear the popup window
		String prefix =  (preLocation.x == preLocation.y) ? "" : strKeywords.substring(preLocation.x, preLocation.y);
		
		setExistKeyword.clear();
		setExistKeyword.addAll(Arrays.asList(strKeywords.split(MAKeyword.KeywordSplitStr)));
		
		if (prefix == null || prefix.isEmpty())
		{
			this.popupWindow.setVisible(false);
		}
		else
		{
			queryAssistant.setCondtion(prefix);
		}
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

	private void doSelectKeywordInPopWin()
	{
		int selectRow = tblKey.getSelectedRow();
		if (selectRow >= 0)
		{
			MAKeyword keyword = tblModelKey.getItem(selectRow);
			
			//Find out the start and end of current keyword where cursor on
			int curCare = this.getCaretPosition();
			String strKeywords = this.getText();
			int keywordLen = strKeywords.length();
			
			//find out the keyword's start position
			int startIndex = findStartIndex(curCare, strKeywords);
			
			//find out the keyword's end position
			int endIndex = findEndIndex(curCare, strKeywords);
			
			//Make sure the startIndex and the endIndex is valid.
			if (startIndex < 0)
			{
				startIndex = 0;
			}
			
			if (endIndex >= keywordLen)
			{
				endIndex = keywordLen;
			}
			
			//show selected keyword at the right pos
			StringBuilder buf = new StringBuilder(strKeywords);
			if (startIndex > endIndex)
			{
				System.out.println(startIndex);
			}
			buf.replace(startIndex, endIndex, keyword.getKeyword());
			
			//Set the text and set the care to the new keyword's tail
			String newKeywords = buf.toString();
			setText(newKeywords);
			if (curCare > buf.length())
			{
				curCare = buf.length();
			}
			else
			{
				curCare = findEndIndex(curCare, newKeywords);
			}
			this.setCaretPosition(curCare);
			
			setExistKeyword.add(keyword.getKeyword());
			
			popupWindow.setVisible(false);
			fireKeywordChange();
		}
	}


	private static int findStartIndex(int startIndex, String strKeywords)
	{
		int keywordLen = strKeywords.length();
		for (--startIndex; startIndex > 0; --startIndex)
		{
			if (startIndex >= keywordLen)
			{
				continue;
			}
			if (strKeywords.charAt(startIndex) == MAKeyword.KeywordSplitChar)
			{
				++startIndex;
				break;
			}
		}
		return startIndex;
	}


	private static int findEndIndex(int endIndex, String strKeywords)
	{
		for (int keywordLen = strKeywords.length(); endIndex < keywordLen && strKeywords.charAt(endIndex) != MAKeyword.KeywordSplitChar; ++endIndex)
		{
			if (strKeywords.charAt(endIndex) == MAKeyword.KeywordSplitChar)
			{
				--endIndex;
			}
		}
		return endIndex;
	}

	private class KeywordQueryLogic implements IQueryLogic<String, MAKeyword>
	{
		private MAKeywordSvc maKeywordSvc;
		private MAKeywordCondition cond;
		
		public KeywordQueryLogic()
		{
			maKeywordSvc = ContextHolder.getBean(MAKeywordSvc.class);
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
				if (tblModelKey.getRowCount() <= 0)
				{
					popupWindow.setVisible(false);
				}
				else
				{
					// find out the right posistion to show the popup window
					Point newPos = getLocation();
					SwingUtilities.convertPointToScreen(newPos, getParent());
					newPos.y += getHeight();
					newPos.x += getFontMetrics(getFont()).stringWidth(getText().substring(0, getCaretPosition()));
					popupWindow.setLocation(newPos);
					popupWindow.setVisible(true);
				}
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
			if (popupWindow.isVisible())
			{
				action.doAction(e);
			}
		}
		else
		{
			super.processKeyEvent(e);
			updatePopupInfo();
			if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
					&& !isPopupWindowVisible())
			{
				fireKeywordChange();
			}
		}
	}
	
	private void fireKeywordChange()
	{
		KeywordFieldEvent event = new KeywordFieldEvent(this, this.getLstKeyword());
		this.eventContainer.fireEvents(IKeywordFieldListener.EVENT_KEYWORD_CHANGE, event);
	}

	private void initPopupWindowKeyActions()
	{
		mapKeyAction.put(Integer.valueOf(KeyEvent.VK_ENTER), new IPopupWindowKeyAction()
		{
			@Override
			public void doAction(KeyEvent e)
			{
				doSelectKeywordInPopWin();				
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
