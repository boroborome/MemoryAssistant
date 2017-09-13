/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Mar 30, 2014
 */
package com.happy3w.footstone.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.res.ResConst;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        boroborome
 * @version       1.0 Mar 30, 2014
 */
public class SimpleSqlBuilder
{
	private StringBuilder sqlString = new StringBuilder();
    private List<Object> lstParam = new ArrayList<Object>();
    private boolean haveNoCondtion = true;
    
    /**
     * 构造函数
     */
    public SimpleSqlBuilder(String selectSql)
    {
        super();
        sqlString.append(selectSql);
    }
    
    /**
     * This function will auto add keyword 'where' at the first time.and add 'and' at the other time.
     * @param conditionSql a sql like "name=?" or "(age > ? or age < ?)"
     * @param param parameter list
     */
    public void appendCondition(String conditionSql, Object... param)
    {
    	if (haveNoCondtion)
    	{
    		sqlString.append(" where ");
    	}
    	else
    	{
    		sqlString.append(" and ");
    	}
    	sqlString.append(conditionSql);
    	lstParam.addAll(Arrays.asList(param));
    	haveNoCondtion = false;
    }

	public void fillParameters(PreparedStatement ps) {
    	fillParameters(ps, lstParam);
	}

    public static void fillParameters(PreparedStatement statement, List lstParam) throws MessageException
    {
    	try
    	{
	    	for (int indexParam = 0, countParam = lstParam.size(); indexParam < countParam; ++indexParam)
	    	{
	    		Object param = lstParam.get(indexParam);
	    		int index = indexParam + 1;
	    		if (param instanceof String)
	    		{
					statement.setString(index, (String) param);
	    		}
	    		else if (param instanceof Long)
	    		{
	    			statement.setLong(index, ((Long) param).longValue());
	    		}
	    		else if (param instanceof Number)
	    		{
	    			statement.setInt(index, ((Number) param).intValue());
	    		}
	    		else
	    		{
	    			throw new MessageException(ResConst.ResKey, ResConst.UnsupportType, 
	    					new Object[]{param == null ? null : param.getClass()},
	    					null);
	    		}
	    		
	    	}
    	}
    	catch (SQLException e)
    	{
    		throw new MessageException(ResConst.ResKey, ResConst.FailedInSetSqlParam, 
					new Object[]{"Unkown"}, e);
    	}
    }

}
