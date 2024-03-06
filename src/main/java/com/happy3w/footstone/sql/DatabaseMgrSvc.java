/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Mar 29, 2014
 */
package com.happy3w.footstone.sql;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.res.ResConst;
import com.ibatis.common.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author boroborome
 * @version 1.0 Mar 29, 2014
 */
public class DatabaseMgrSvc implements IDatabaseMgrSvc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DatabaseMgrSvc() {
        super();
    }

    @Override
    public <T> void executeSql(String sql, Iterator<T> it, IFillSql<T> fileMethod) throws MessageException {
        if (it == null || !it.hasNext()) {
            return;
        }
        jdbcTemplate.execute(sql, new PreparedStatementCallback<String>() {
            @Override
            public String doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                do {
                    T value = it.next();
                    fileMethod.fill(statement, value);
                    statement.execute();
                    fileMethod.onSuccess(value);
                }
                while (it.hasNext());
                return "Success";
            }
        });
    }

//	@Override
//	public PreparedStatement createStatement(String sql) throws MessageException
//	{
//		try
//        {
//            return connection.prepareStatement(sql);
//        }
//        catch (SQLException e)
//        {
//            throw new MessageException(ResConst.ResKey, ResConst.FailedToCreateState, e);
//        }
//	}


    @Override
    public int executeUpdate(String sql) throws SQLException, MessageException {
        return jdbcTemplate.execute(sql, new PreparedStatementCallback<Integer>() {
                    @Override
                    public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        return ps.executeUpdate();
                    }
                }
        );
    }

    @Override
    public void runSqlFile(InputStream sqlFileStream) throws MessageException {
        jdbcTemplate.execute(new ConnectionCallback<String>() {
            @Override
            public String doInConnection(Connection con) throws SQLException, DataAccessException {
                ScriptRunner runner = new ScriptRunner(con, true, true);
                try {
                    runner.runScript(new InputStreamReader(sqlFileStream));
                } catch (Exception e) {
                    throw new MessageException(ResConst.ResKey, ResConst.FailedExeSql, new Object[]{""}, e);
                }
                return null;
            }
        });
    }

    @Override
    public boolean executeUpdateSql(String sql, Object... param) {
        return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                SimpleSqlBuilder.fillParameters(ps, Arrays.asList(param));
                return ps.execute();
            }
        });
    }

    @Override
    public void executeQuery(Consumer<ResultSet> resultConsumer, String sql, Object... param) {
        jdbcTemplate.execute(sql, (PreparedStatementCallback<ResultSet>) ps -> {
            SimpleSqlBuilder.fillParameters(ps, Arrays.asList(param));
            ResultSet rs = ps.executeQuery();
            resultConsumer.accept(rs);
            return rs;
        });
    }

}
