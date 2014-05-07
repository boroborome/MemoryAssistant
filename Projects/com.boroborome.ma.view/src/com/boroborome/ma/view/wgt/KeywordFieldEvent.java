/**
 * 
 */
package com.boroborome.ma.view.wgt;

import java.util.EventObject;
import java.util.List;

import com.boroborome.ma.model.MAKeyword;

/**
 * @author boroborome
 *
 */
public class KeywordFieldEvent extends EventObject
{
	private List<MAKeyword> lstKey;
	public KeywordFieldEvent(Object source, List<MAKeyword> lstKey)
	{
		super(source);
		this.lstKey = lstKey;
	}
	/**
	 * @return the lstKey
	 */
	public List<MAKeyword> getLstKey()
	{
		return lstKey;
	}

	
}
