/**
 * 
 */
package com.boroborme.ma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author boroborome
 *
 */
public class MAKeyword
{
	private long wordid;
	private String keyword;
	public MAKeyword()
	{
		super();
	}
	/**
	 * @return the wordid
	 */
	public long getWordid()
	{
		return wordid;
	}
	/**
	 * @param wordid the wordid to set
	 */
	public void setWordid(long wordid)
	{
		this.wordid = wordid;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword()
	{
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}
	
	public static String list2String(List<MAKeyword> lstKeyword)
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
		return buf.toString();
	}
	
	public static List<MAKeyword> string2List(String lstKeywrod)
	{
		//TODO [optimize] should filter repeat keyword
		List<MAKeyword> newLstKeyword = new ArrayList<MAKeyword>();
		String[] aryKeys = lstKeywrod.split(" ");
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
}
