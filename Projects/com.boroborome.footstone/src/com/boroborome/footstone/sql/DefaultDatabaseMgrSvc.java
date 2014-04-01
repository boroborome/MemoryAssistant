/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Mar 29, 2014
 */
package com.boroborome.footstone.sql;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.model.IBufferIterator;
import com.boroborome.footstone.res.ResConst;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        boroborome
 * @version       1.0 Mar 29, 2014
 */
public class DefaultDatabaseMgrSvc implements IDatabaseMgrSvc
{
	private Connection connection;
	public DefaultDatabaseMgrSvc(Connection connection)
	{
		super();
		this.connection = connection;
	}

	@Override
	public <T> void executeSql(String sql, IBufferIterator<T> it, IFillSql<T> fileMethod) throws MessageException
	{
		if (it == null || !it.hasNext())
        {
            return;
        }
        PreparedStatement statement = createStatement(sql);
        try
        {
            do
            {
                T value = it.next();
                fileMethod.fill(statement, value);
                statement.execute();
                fileMethod.onSuccess(value);
            }
            while (it.hasNext());
        }
        catch (SQLException e)
        {
            throw new MessageException(ResConst.ResKey, ResConst.FailedExeBatchSql, e);
        }
	}

	@Override
	public PreparedStatement createStatement(String sql) throws MessageException
	{
		try
        {
            return connection.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            throw new MessageException(ResConst.ResKey, ResConst.FailedToCreateState, e);
        }
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException, MessageException
	{
		PreparedStatement statement = createStatement(sql);
		return statement.executeQuery();
	}

	@Override
	public int executeUpdate(String sql) throws SQLException, MessageException
	{
		PreparedStatement statement = createStatement(sql);
		return statement.executeUpdate();
	}

	@Override
	public void runSqlFile(InputStream sqlFileStream) throws MessageException
	{
        ScriptRunner runner = new ScriptRunner(connection);
        runner.runScript(new InputStreamReader(sqlFileStream));
	}

}
