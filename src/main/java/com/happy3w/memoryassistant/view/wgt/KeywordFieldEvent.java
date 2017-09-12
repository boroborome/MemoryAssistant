/**
 * 
 */
package com.happy3w.memoryassistant.view.wgt;

import com.happy3w.memoryassistant.model.MAKeyword;

import java.util.EventObject;
import java.util.List;


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
