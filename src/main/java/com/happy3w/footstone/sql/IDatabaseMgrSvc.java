/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-16
 */
package com.happy3w.footstone.sql;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.happy3w.footstone.exception.MessageException;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>提供读取当前连接的数据库的信息。如表信息，通过连接执行sql得到结果</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-16
 */
public interface IDatabaseMgrSvc
{
	/**
	 * 针对某个数据类型逐个执行某种sql语句<br>
	 * 一般应用于批量插入，批量修改
	 * @param sql 需要执行的sql
	 * @param it 数据迭代器
	 * @param fileMethod 将数据填充到sql中的逻辑
	 * @throws MessageException
	 * @since:        [产品/模块版本，表示从哪个版本开始有]
	 */
	public<T> void executeSql(String sql, Iterator<T> it, IFillSql<T> fileMethod) throws MessageException;
	
	PreparedStatement createStatement(String sql) throws MessageException;
	
	boolean executeUpdateSql(String sql, Object... param);
	
	/**
	 * Execute the sql in filestream
	 * @param sqlFileStream sql file stream
	 * @throws MessageException
	 * @since:        [产品/模块版本，表示从哪个版本开始有]
	 */
	void runSqlFile(InputStream sqlFileStream) throws MessageException;
	
    /**
     * 执行指定的sql语句，返回结果
     * @param sql select开头的sql语句
     * @return
     * @throws SQLException
     * @throws MessageException 
     */
    ResultSet executeQuery(String sql, Object... param);

    /**
     * 执行更新语句
     * @param sql 非select开头的sql语句
     * @return
     * @throws MessageException 
     * @throws SQLException 
     */
    int executeUpdate(String sql) throws SQLException, MessageException;
}
