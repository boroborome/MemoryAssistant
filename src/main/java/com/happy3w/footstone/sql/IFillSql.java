/*
 * <P>Title:      任务管理器</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-6
 * @since         v1.0
 */
package com.happy3w.footstone.sql;

import com.happy3w.footstone.exception.MessageException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author BoRoBoRoMe
 */
public abstract class IFillSql<T> {
    /**
     * 根据场景不同，有时将value中值填写到statement中
     * 有时将statement中内容填写到value中
     *
     * @param statement
     * @param value
     */
    public abstract void fill(PreparedStatement statement, T value) throws SQLException, MessageException;

    /**
     * 成功时执行
     *
     * @param value
     * @throws MessageException
     */
    public void onSuccess(T value) throws MessageException {

    }

    /**
     * 失败时执行
     *
     * @param value
     */
    public void onFailed(T value) throws MessageException {

    }
}
