/**
 * 
 */
package com.boroborome.ma.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.boroborme.ma.model.MAKeyword;
import com.boroborme.ma.model.MAKeywordCondition;
import com.boroborme.ma.model.svc.IMAKeywordSvc;
import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.ui.BaseReadonlyTableModel;
import com.boroborome.footstone.ui.ExtTable;
import com.boroborome.ma.view.query.IQueryLogic;
import com.boroborome.ma.view.query.QueryAssistant;

/**
 * @author boroborome
 * TODO this control should return the keyword's id?
 * TODO we should implement the fun:select keyword from popup window
 */
public class KeywordField extends JTextField
{
//	private List<MAKeyword> lstKeyword = new ArrayList<MAKeyword>();

	private JWindow popupWindow;

	private BaseReadonlyTableModel<MAKeyword> tblModelKey;

	private ExtTable tblKey;

	private QueryAssistant<String, MAKeyword> queryAssistant 
		= new QueryAssistant<String, MAKeyword>("Thread Query keyword for KeywordField",
				new KeywordQueryLogic());
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
					int selectRow = tblKey.getSelectedRow();
					if (selectRow >= 0)
					{
						MAKeyword keyword = tblModelKey.getItem(selectRow);
						Point pos = findCurKeywordPos();
						if (pos.x == pos.y)
						{
							setText(getText() + keyword.getKeyword());
						}
						else
						{
							String strKeywordList = getText();
							StringBuilder buf = new StringBuilder(strKeywordList);
							buf.replace(pos.x, pos.y, keyword.getKeyword());
							setText(buf.toString());
						}
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#processKeyEvent(java.awt.event.KeyEvent)
	 */
	@Override
	protected void processKeyEvent(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (e.getID() != KeyEvent.KEY_PRESSED)
			{
				return;
			}
			
			if (popupWindow.isVisible() && tblModelKey.getRowCount() > 0)
			{	System.out.println(e.toString());
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
				return;
			}
		}
		else
		{
			super.processKeyEvent(e);
			updatePopupInfo();
		}
	}

	protected void saveKeyword()
	{
		// TODO implement save keyword.
		
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
		for (; startIndex >= 0 && strKeywords.charAt(startIndex) != ' '; --startIndex);
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
		System.out.println("prefix:" + prefix);
		this.queryAssistant.setCondtion(prefix);
	}


	/**
	 * @return the lstKeyword
	 */
	public List<MAKeyword> getLstKeyword()
	{
		//TODO [optimize] should filter repeat keyword
		List<MAKeyword> newLstKeyword = new ArrayList<MAKeyword>();
		String[] aryKeys = this.getText().split(" ");
		for (String key : aryKeys)
		{
			if (key == null || key.isEmpty())
			{
				continue;
			}
			
			MAKeyword keyword = new MAKeyword();
			keyword.setKeyword(key);
			newLstKeyword.add(keyword);
		}
		return newLstKeyword;
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
					buf.append(' ');
				}
				buf.append(keyword.getKeyword());
			}
		}
		this.setText(buf.toString());	
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
			System.out.println("clear");
			tblModelKey.clear();			
		}


		@Override
		public Iterator<MAKeyword> query(String condition) throws Exception
		{
			System.out.println("query condition:"+ condition);
			cond.setKeywordLike(condition);
			Iterator<MAKeyword> itKeyword = maKeywordSvc.query(cond);
			return itKeyword;
		}


		@Override
		public void showData(Iterator<MAKeyword> it) throws Exception
		{
			System.out.println("show result." + it.hasNext());
			tblModelKey.showData(it);			
		}
		
	}
}
