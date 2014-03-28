/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-16
 */
package com.boroborome.footstone.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.boroborome.footstone.exception.MessageException;

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
     * 执行指定的sql语句，返回结果
     * @param sql select开头的sql语句
     * @return
     * @throws SQLException
     * @throws MessageException 
     */
    ResultSet executeQuery(String sql) throws SQLException, MessageException;

    /**
     * 执行更新语句
     * @param sql 非select开头的sql语句
     * @return
     * @throws MessageException 
     * @throws SQLException 
     */
    int executeUpdate(String sql) throws SQLException, MessageException;
}
