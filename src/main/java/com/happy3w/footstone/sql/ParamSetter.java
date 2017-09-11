/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-4-18
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;


/**
 * <DT><B>Title:</B></DT>
 *    <DD>TypeConverter向数据状态链接设置参数的工具</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>每一个需要设置参数的地方都当自己是第一个参数进行设置</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-4-18
 */
public class ParamSetter implements IParamSetter
{
    private PreparedStatement statement;
    private int cursor;
    private int curMaxSize;

    /**
     * 构造函数
     */
    public ParamSetter()
    {
        super();
    }

    /**
     * 构造函数
     * @param statement
     */
    public ParamSetter(PreparedStatement statement)
    {
        super();
        this.statement = statement;
    }

    /**
     * 获取statement
     * @return statement
     */
    public PreparedStatement getStatement()
    {
        return statement;
    }

    /**
     * 设置statement
     * @param statement statement
     */
    public void setStatement(PreparedStatement statement)
    {
        this.statement = statement;
    }

    /**
     * 重置
     */
    public void reset()
    {
        curMaxSize = 0;
        cursor = 0;
    }
    
    /**
     * 接受已经设置的参数，准备接受后面的参数
     */
    public void acceptParam()
    {
        cursor += (curMaxSize + 1);
        curMaxSize = 0;
    }
    
    /**
     * 记录最大索引
     * @param index 当前索引
     */
    private void ensureSize(int index)
    {
        if (index > curMaxSize)
        {
            curMaxSize = index;
        }
    }
    
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setArray(int, java.sql.Array)
     */
    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setArray(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setAsciiStream(int, java.io.InputStream, int)
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setAsciiStream(parameterIndex + cursor, x, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setAsciiStream(int, java.io.InputStream, long)
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setAsciiStream(parameterIndex + cursor, x, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setAsciiStream(int, java.io.InputStream)
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setAsciiStream(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBigDecimal(int, java.math.BigDecimal)
     */
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBigDecimal(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBinaryStream(int, java.io.InputStream, int)
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBinaryStream(parameterIndex + cursor, x, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBinaryStream(int, java.io.InputStream, long)
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBinaryStream(parameterIndex + cursor, x, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBinaryStream(int, java.io.InputStream)
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBinaryStream(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBlob(int, java.sql.Blob)
     */
    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBlob(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBlob(int, java.io.InputStream, long)
     */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBlob(parameterIndex + cursor, inputStream, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBlob(int, java.io.InputStream)
     */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBlob(parameterIndex + cursor, inputStream);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBoolean(int, boolean)
     */
    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBoolean(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setByte(int, byte)
     */
    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setByte(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setBytes(int, byte[])
     */
    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setBytes(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setCharacterStream(int, java.io.Reader, int)
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setCharacterStream(parameterIndex + cursor, reader, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setCharacterStream(int, java.io.Reader, long)
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setCharacterStream(parameterIndex + cursor, reader, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setCharacterStream(int, java.io.Reader)
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setCharacterStream(parameterIndex + cursor, reader);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setClob(int, java.sql.Clob)
     */
    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setClob(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setClob(int, java.io.Reader, long)
     */
    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setClob(parameterIndex + cursor, reader, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setClob(int, java.io.Reader)
     */
    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setClob(parameterIndex + cursor, reader);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setDate(int, java.sql.Date, java.util.Calendar)
     */
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setDate(parameterIndex + cursor, x, cal);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setDate(int, java.sql.Date)
     */
    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setDate(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setDouble(int, double)
     */
    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setDouble(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setFloat(int, float)
     */
    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setFloat(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setInt(int, int)
     */
    @Override
    public void setInt(int parameterIndex, int x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setInt(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setLong(int, long)
     */
    @Override
    public void setLong(int parameterIndex, long x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setLong(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNCharacterStream(int, java.io.Reader, long)
     */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNCharacterStream(parameterIndex + cursor, value, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNCharacterStream(int, java.io.Reader)
     */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNCharacterStream(parameterIndex + cursor, value);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNClob(int, java.sql.NClob)
     */
    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNClob(parameterIndex + cursor, value);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNClob(int, java.io.Reader, long)
     */
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNClob(parameterIndex + cursor, reader, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNClob(int, java.io.Reader)
     */
    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNClob(parameterIndex + cursor, reader);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNString(int, java.lang.String)
     */
    @Override
    public void setNString(int parameterIndex, String value) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNString(parameterIndex + cursor, value);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNull(int, int, java.lang.String)
     */
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNull(parameterIndex + cursor, sqlType, typeName);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setNull(int, int)
     */
    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setNull(parameterIndex + cursor, sqlType);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setObject(int, java.lang.Object, int, int)
     */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setObject(parameterIndex + cursor, x, targetSqlType, scaleOrLength);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setObject(int, java.lang.Object, int)
     */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setObject(parameterIndex + cursor, x, targetSqlType);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setObject(int, java.lang.Object)
     */
    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setObject(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setRowId(int, java.sql.RowId)
     */
    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setRowId(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setShort(int, short)
     */
    @Override
    public void setShort(int parameterIndex, short x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setShort(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setSQLXML(int, java.sql.SQLXML)
     */
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setSQLXML(parameterIndex + cursor, xmlObject);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setString(int, java.lang.String)
     */
    @Override
    public void setString(int parameterIndex, String x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setString(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setTime(int, java.sql.Time, java.util.Calendar)
     */
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setTime(parameterIndex + cursor, x, cal);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setTime(int, java.sql.Time)
     */
    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setTime(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setTimestamp(int, java.sql.Timestamp, java.util.Calendar)
     */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setTimestamp(parameterIndex + cursor, x, cal);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setTimestamp(int, java.sql.Timestamp)
     */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setTimestamp(parameterIndex + cursor, x);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setUnicodeStream(int, java.io.InputStream, int)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setUnicodeStream(parameterIndex + cursor, x, length);
    }
    /* (non-Javadoc)
     * @see com.boroborome.cleverdb.model.IParamSetter#setURL(int, java.net.URL)
     */
    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException
    {
        ensureSize(parameterIndex);
        statement.setURL(parameterIndex + cursor, x);
    }
    
    
}
