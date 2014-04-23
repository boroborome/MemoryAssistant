/**
 * 
 */
package com.boroborome.ma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author boroborome
 *
 */
public class MAInformation
{
	private long createTime;
	private long modifyTime;
	private String content;
	
	private List<MAKeyword> lstKeyword = new ArrayList<MAKeyword>();
	
	public MAInformation()
	{
		super();
	}

	/**
	 * @return the createTime
	 */
	public long getCreateTime()
	{
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime
	 */
	public long getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(long modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the lstKeyword
	 */
	public List<MAKeyword> getLstKeyword()
	{
		return lstKeyword;
	}

	/**
	 * @param lstKeyword the lstKeyword to set
	 */
	public void setLstKeyword(List<MAKeyword> lstKeyword)
	{
		this.lstKeyword = lstKeyword;
	}
	
}
