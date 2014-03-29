/**
 * 
 */
package com.boroborome.maassistant.logic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.boroborme.maassistant.model.MAKeyword;
import com.boroborome.footstone.model.AbstractDBIterator;

/**
 * @author boroborome
 *
 */
public class MAKeywordDBIterator extends AbstractDBIterator<MAKeyword>
{

	public MAKeywordDBIterator(ResultSet rs)
	{
		super(rs);
	}

	@Override
	public MAKeyword adapterValue(ResultSet rs) throws SQLException
	{
		MAKeyword item = new MAKeyword();
        item.setWordid(rs.getLong("wordid"));
        item.setKeyword(rs.getString("keyword"));
        return item;
	}

}
