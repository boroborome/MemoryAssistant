/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-4-25
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.sql;

import java.sql.SQLException;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>简单Sql参数设置器</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>能够处理一般普通参数的设置。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-4-25
 */
public class SimpleSqlParam implements ISqlParam
{
    /**
     * 空的Sql参数
     */
    public static final ISqlParam EmptySqlParam = new SimpleSqlParam(null);
    
    /**
     * 参数值
     */
    private Object paramValue;
    
    /**
     * 内部sql参数提供器，如果使用了内部参数提供器则paramValue无效
     */
    private ISqlParam inner;
    
    /**
     * 构造函数
     */
    public SimpleSqlParam()
    {
        super();
    }

    /**
     * 构造函数
     * @param paramValue
     */
    public SimpleSqlParam(Object paramValue)
    {
        super();
        setParamValue(paramValue);
    }

    /**
     * 获取paramValue
     * @return paramValue
     */
    public Object getParamValue()
    {
        return paramValue;
    }

    /**
     * 设置paramValue
     * @param paramValue paramValue
     */
    public void setParamValue(Object paramValue)
    {
        this.paramValue = paramValue;
        if (paramValue instanceof ISqlParam)
        {
            inner = (ISqlParam) paramValue;
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.sql.ISqlParam#setParam(com.boroborome.common.sql.IParamSetter)
     */
    @Override
    public void setParam(IParamSetter setter) throws SQLException
    {
        if (inner != null)
        {
            inner.setParam(setter);
        }
        else
        {
            setter.setObject(0, paramValue);
        }
    }

}
