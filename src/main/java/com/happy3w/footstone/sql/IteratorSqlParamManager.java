/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-5-23
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.sql;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>通过迭代器提供Sql参数的管理器</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-5-23
 */
public class IteratorSqlParamManager implements ISqlParamManager
{
    private Iterator<?> it;
    private IGetValueMethod getValueMethod;
    private Object curValue;
    
    /**
     * 构造函数
     * @param it
     */
    public IteratorSqlParamManager(Iterator<?> it, IGetValueMethod getValueMethod)
    {
        super();
        this.it = it;
        this.getValueMethod = getValueMethod;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.sql.ISqlParamManager#hasNext()
     */
    @Override
    public boolean hasNext()
    {
        return it.hasNext();
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.sql.ISqlParamManager#next()
     */
    @Override
    public void next()
    {
        curValue = it.next();
    }

    @Override
    public ISqlParam createSqlParam(Object paramFlag)
    {
        return new BatchSqlParam(paramFlag);
    }

    private class BatchSqlParam implements ISqlParam
    {
        private Object paramFlag;

        /**
         * 构造函数
         * @param paramFlag
         */
        public BatchSqlParam(Object paramFlag)
        {
            this.paramFlag = paramFlag;
        }

        @Override
        public void setParam(IParamSetter setter) throws SQLException
        {
            setter.setObject(0, getValueMethod.getValue(curValue, paramFlag));
        }
        
    }
}
