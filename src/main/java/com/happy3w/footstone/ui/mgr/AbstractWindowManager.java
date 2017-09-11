/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Apr 4, 2014
 */
package com.happy3w.footstone.ui.mgr;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>Base class of window manager</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>A programe may be have a lot of window,this class is used for manage this windows.
 *    It support<br>
 *    1. open a windows with a title.
 *    2. find out all the opened windows.
 *    3. find out a window with title.
 *    etc.</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        boroborome
 * @version       1.0 Apr 4, 2014
 */
public abstract class AbstractWindowManager
{
	/**
	 * Map of opened window<br>
	 * key:title<br>
	 * value:The panel which is shown in window.
	 */
	private Map<String, JPanel> mapPanel = new HashMap<String, JPanel>();
	
	public void showWindow(String title, JPanel panel)
	{
		//TODO [optimize] the Window manager framework to be implements
		JPanel pnl = mapPanel.get(title);
		if (pnl == null)
		{
			
		}
	}
}
