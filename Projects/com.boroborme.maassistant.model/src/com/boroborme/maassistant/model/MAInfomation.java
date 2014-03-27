/**
 * 
 */
package com.boroborme.maassistant.model;

/**
 * @author boroborome
 *
 */
public class MAInfomation
{
	private long createTime;
	private long modifyTime;
	private String content;
	
	public MAInfomation()
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
	
	
	
}
