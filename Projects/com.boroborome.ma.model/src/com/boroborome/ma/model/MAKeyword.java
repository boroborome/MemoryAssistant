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
public class MAKeyword
{
	public static final char KeywordSplitChar = ' ';
	public static final String KeywordSplitStr = " ";

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
					buf.append(KeywordSplitChar);
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
		String[] aryKeys = lstKeywrod.split(KeywordSplitStr);
		for (String key : aryKeys)
		{
			if (key == null || key.isEmpty())
			{
				continue;
			}
			
			MAKeyword keyword = new MAKeyword();
			keyword.setKeyword(key);
			keyword.setWordid(-1);
			newLstKeyword.add(keyword);
		}
		return newLstKeyword;
	}
	
	@Override
	public String toString()
	{
		return this.wordid + ":" + this.keyword;
	}
}
