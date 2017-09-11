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
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public interface IParamSetter
{

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setArray(int, Array)
     */
    void setArray(int parameterIndex, Array x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setAsciiStream(int, InputStream, int)
     */
    void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setAsciiStream(int, InputStream, long)
     */
    void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setAsciiStream(int, InputStream)
     */
    void setAsciiStream(int parameterIndex, InputStream x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBigDecimal(int, BigDecimal)
     */
    void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBinaryStream(int, InputStream, int)
     */
    void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBinaryStream(int, InputStream, long)
     */
    void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBinaryStream(int, InputStream)
     */
    void setBinaryStream(int parameterIndex, InputStream x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBlob(int, Blob)
     */
    void setBlob(int parameterIndex, Blob x) throws SQLException;

    /**
     * @param parameterIndex
     * @param inputStream
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBlob(int, InputStream, long)
     */
    void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param inputStream
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBlob(int, InputStream)
     */
    void setBlob(int parameterIndex, InputStream inputStream) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBoolean(int, boolean)
     */
    void setBoolean(int parameterIndex, boolean x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setByte(int, byte)
     */
    void setByte(int parameterIndex, byte x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBytes(int, byte[])
     */
    void setBytes(int parameterIndex, byte[] x) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setCharacterStream(int, Reader, int)
     */
    void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setCharacterStream(int, Reader, long)
     */
    void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     * @see java.sql.PreparedStatement#setCharacterStream(int, Reader)
     */
    void setCharacterStream(int parameterIndex, Reader reader) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setClob(int, Clob)
     */
    void setClob(int parameterIndex, Clob x) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setClob(int, Reader, long)
     */
    void setClob(int parameterIndex, Reader reader, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     * @see java.sql.PreparedStatement#setClob(int, Reader)
     */
    void setClob(int parameterIndex, Reader reader) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param cal
     * @throws SQLException
     * @see java.sql.PreparedStatement#setDate(int, Date, Calendar)
     */
    void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setDate(int, Date)
     */
    void setDate(int parameterIndex, Date x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setDouble(int, double)
     */
    void setDouble(int parameterIndex, double x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setFloat(int, float)
     */
    void setFloat(int parameterIndex, float x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setInt(int, int)
     */
    void setInt(int parameterIndex, int x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setLong(int, long)
     */
    void setLong(int parameterIndex, long x) throws SQLException;

    /**
     * @param parameterIndex
     * @param value
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNCharacterStream(int, Reader, long)
     */
    void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param value
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNCharacterStream(int, Reader)
     */
    void setNCharacterStream(int parameterIndex, Reader value) throws SQLException;

    /**
     * @param parameterIndex
     * @param value
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNClob(int, NClob)
     */
    void setNClob(int parameterIndex, NClob value) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNClob(int, Reader, long)
     */
    void setNClob(int parameterIndex, Reader reader, long length) throws SQLException;

    /**
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNClob(int, Reader)
     */
    void setNClob(int parameterIndex, Reader reader) throws SQLException;

    /**
     * @param parameterIndex
     * @param value
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNString(int, String)
     */
    void setNString(int parameterIndex, String value) throws SQLException;

    /**
     * @param parameterIndex
     * @param sqlType
     * @param typeName
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNull(int, int, String)
     */
    void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException;

    /**
     * @param parameterIndex
     * @param sqlType
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNull(int, int)
     */
    void setNull(int parameterIndex, int sqlType) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param targetSqlType
     * @param scaleOrLength
     * @throws SQLException
     * @see java.sql.PreparedStatement#setObject(int, Object, int, int)
     */
    void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
            throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param targetSqlType
     * @throws SQLException
     * @see java.sql.PreparedStatement#setObject(int, Object, int)
     */
    void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setObject(int, Object)
     */
    void setObject(int parameterIndex, Object x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setRowId(int, RowId)
     */
    void setRowId(int parameterIndex, RowId x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setShort(int, short)
     */
    void setShort(int parameterIndex, short x) throws SQLException;

    /**
     * @param parameterIndex
     * @param xmlObject
     * @throws SQLException
     * @see java.sql.PreparedStatement#setSQLXML(int, SQLXML)
     */
    void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setString(int, String)
     */
    void setString(int parameterIndex, String x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param cal
     * @throws SQLException
     * @see java.sql.PreparedStatement#setTime(int, Time, Calendar)
     */
    void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setTime(int, Time)
     */
    void setTime(int parameterIndex, Time x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param cal
     * @throws SQLException
     * @see java.sql.PreparedStatement#setTimestamp(int, Timestamp, Calendar)
     */
    void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setTimestamp(int, Timestamp)
     */
    void setTimestamp(int parameterIndex, Timestamp x) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @deprecated
     * @see java.sql.PreparedStatement#setUnicodeStream(int, InputStream, int)
     */
    @SuppressWarnings("dep-ann")
    void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException;

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setURL(int, URL)
     */
    void setURL(int parameterIndex, URL x) throws SQLException;

}
