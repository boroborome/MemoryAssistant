/**
 * 
 */
package com.boroborome.appframe.svc;

import java.util.List;

import javax.swing.AbstractAction;

/**
 * @author boroborome
 *
 */
public abstract class AbstractAppAction<T> extends AbstractAction
{
	private List<T> lstData;
	
	public void onSelectDataChanged(List<T> lstData)
	{
		this.lstData = lstData;
	}
}
