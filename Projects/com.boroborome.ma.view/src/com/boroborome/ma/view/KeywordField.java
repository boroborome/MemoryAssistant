/**
 * 
 */
package com.boroborome.ma.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.boroborme.ma.model.MAInformation;
import com.boroborme.ma.model.MAKeyword;
import com.boroborome.footstone.ui.BaseReadonlyTableModel;
import com.boroborome.footstone.ui.ExtTable;

/**
 * @author boroborome
 *
 */
public class KeywordField extends JTextField
{
	private List<MAKeyword> lstKeyword = new ArrayList<MAKeyword>();

	private JWindow popupWindow;

	private BaseReadonlyTableModel<MAKeyword> keyTableModel;

	private ExtTable keyTable;
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
		
		//TODO monitoring the value change
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
				updatePopupInfo();
			}});
	}

	private void initPopupWindow()
	{
		popupWindow = new JWindow();
		keyTableModel = new BaseReadonlyTableModel<MAKeyword>(new String[]{"Key"})
		{
			@Override
			public Object[] formatItem(MAKeyword data)
			{
				return new Object[]{data.getKeyword()};
			}
		};
		keyTable = new ExtTable();
		keyTable.setModel(keyTableModel);
		popupWindow.setContentPane(keyTable);
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
		// TODO query Keywords and show
		Point newPos = this.getLocation();
		SwingUtilities.convertPointToScreen(newPos, this.getParent());
		newPos.y += this.getHeight();
		newPos.x += this.getFontMetrics(getFont()).stringWidth(this.getText().substring(0, this.getCaretPosition()));
		popupWindow.setLocation(newPos);
		popupWindow.setVisible(true);
	}


	/**
	 * @return the lstKeyword
	 */
	public List<MAKeyword> getLstKeyword()
	{
		List<MAKeyword> newLstKeyword = new ArrayList<MAKeyword>(lstKeyword.size());
		return newLstKeyword;
	}

	/**
	 * @param lstKeyword the lstKeyword to set
	 */
	public void setLstKeyword(List<MAKeyword> lstKeyword)
	{
		this.lstKeyword.clear();
		if (lstKeyword != null)
		{
			this.lstKeyword.addAll(lstKeyword);
		}
	}

	public boolean isEmpty()
	{
		return lstKeyword.isEmpty();
	}
	
	
}
