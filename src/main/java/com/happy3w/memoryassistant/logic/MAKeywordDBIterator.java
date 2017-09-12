/**
 * 
 */
package com.happy3w.memoryassistant.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.happy3w.footstone.model.AbstractDBIterator;
import com.happy3w.memoryassistant.model.MAKeyword;

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
