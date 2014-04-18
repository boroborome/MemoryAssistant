/**
 * 
 */
package com.boroborome.ma.logic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.boroborme.ma.model.MAInformation;
import com.boroborme.ma.model.MAKeyword;
import com.boroborome.footstone.model.AbstractDBIterator;

/**
 * @author boroborome
 *
 */
public class MAInformationDBIterator extends AbstractDBIterator<MAInformation>
{

	public MAInformationDBIterator(ResultSet rs)
	{
		super(rs);
	}

	@Override
	public MAInformation adapterValue(ResultSet rs) throws SQLException
	{
		MAInformation item = new MAInformation();
        item.setCreateTime(rs.getLong("createTime"));
        item.setModifyTime(rs.getLong("modifyTime"));
        item.setContent(rs.getString("content"));
        String keywords = rs.getString("keywords");
        item.getLstKeyword().addAll(MAKeyword.string2List(keywords));
        return item;
	}

}
