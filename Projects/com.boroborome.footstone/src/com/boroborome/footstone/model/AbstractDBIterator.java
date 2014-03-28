/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-5
 */
package com.boroborome.footstone.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.exception.RuntimeMessageException;
import com.boroborome.footstone.res.ResConst;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-5
 */
public abstract class AbstractDBIterator<T> extends AbstractBufferIterator<T>
{
    private static final Logger log = Logger.getLogger(AbstractDBIterator.class);
    
    private ResultSet dbRS;
    private boolean bHasNext;

    /**
     * 构造方法
     * @param rs 数据库集
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2011-7-16
     */
    public AbstractDBIterator(ResultSet rs)
    {
        if (rs == null)
        {
            throw new IllegalArgumentException("AbstractDBIterator constructor do not accept null parameter.");
        }
        dbRS = rs;
        
        try
        {
            bHasNext = dbRS.next();
        }
        catch (SQLException e)
        {
            log.error("init data failed.", e);
            throw new RuntimeMessageException(ResConst.ResKey, ResConst.UnkownError, new Object[]{e.getMessage()}, e);
        }
    }
    @Override
    public boolean hasNext()
    {
        return bHasNext;
    }

    public abstract T adapterValue(ResultSet rs) throws SQLException;
    
    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     * @throws RuntimeMessageException 读取失败的时候会抛出这个异常，注意捕获这个异常
     */
    @Override
    public T next()
    {
        try
        {
            this.curItem = adapterValue(dbRS);
            bHasNext = dbRS.next();
        }
        catch (SQLException e)
        {
            curItem = null;
            log.error("get data failed.", e);
            throw new RuntimeMessageException(ResConst.ResKey, ResConst.UnkownError, new Object[]{e.getMessage()}, e);
        }
        return curItem;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     * @throws RuntimeMessageException 删除失败的时候会抛出这个异常，注意捕获这个异常
     */
    @Override
    public void remove()
    {
        try
        {
            dbRS.deleteRow();
        }
        catch (SQLException e)
        {
            log.error("remove data failed.", e);
            throw new RuntimeMessageException(ResConst.ResKey, ResConst.RemoveTaskFailed, e);
        }
    }
}
