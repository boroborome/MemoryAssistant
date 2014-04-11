/**
 * 
 */
package com.boroborome.ma.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		
		//monitoring the value change
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e)
			{
				
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyChar() == ' ')
				{
					saveKeyword();
				}
				else
				{
					updatePopupInfo();
				}
			}
		});
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
		String strKeywords = this.getText();
		int endIndex = this.getCaretPosition() - 1;
		int startIndex = endIndex;
		for (; startIndex >= 0 && strKeywords.charAt(startIndex) != ' '; --startIndex);
		
		//if the prefix is empty then clear the popup window
		if (startIndex == endIndex)
		{
			tblModelKey.clear();
		}
		else
		{
			startIndex++;
			String prefix = strKeywords.substring(startIndex, endIndex + 1);
			this.queryAssistant.setCondtion(prefix);
		}
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
			tblModelKey.clear();			
		}


		@Override
		public Iterator<MAKeyword> query(String condition) throws Exception
		{
			cond.setKeywordLike(condition);
			Iterator<MAKeyword> itKeyword = maKeywordSvc.query(cond);
			return itKeyword;
		}


		@Override
		public void showData(Iterator<MAKeyword> it) throws Exception
		{
			tblModelKey.showData(it);			
		}
		
	}
}
