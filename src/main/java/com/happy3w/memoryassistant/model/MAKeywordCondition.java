/**
 * 
 */
package com.happy3w.memoryassistant.model;

import com.happy3w.footstone.svc.IDataCondition;
import com.happy3w.memoryassistant.model.MAKeyword;

/**
 * @author boroborome
 *
 */
public class MAKeywordCondition implements IDataCondition<MAKeyword>
{
	/**
	 * A test contains '%'
	 */
	private String keywordLike;

	/**
	 * 
	 */
	public MAKeywordCondition()
	{
		super();
	}

	/**
	 * @return the keywordLike
	 */
	public String getKeywordLike()
	{
		return keywordLike;
	}

	/**
	 * @param keywordLike the keywordLike to set
	 */
	public void setKeywordLike(String keywordLike)
	{
		this.keywordLike = keywordLike;
	}
	
}
