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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.happy3w.footstone.util.CompareUtil;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>Sql构造器</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>可以接收任何参数，全部是用ISqlParam接收参数</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-4-25
 */
public class SqlBuilder
{
    private StringBuilder sqlString = new StringBuilder();
    private List<ISqlParam> lstParam = new ArrayList<ISqlParam>();
    
    
    /**
     * 构造函数
     */
    public SqlBuilder()
    {
        super();
    }

    /**
     * 构造函数
     * @param sql
     * @param params
     */
    public SqlBuilder(final String sql, final ISqlParam... params)
    {
        append(sql, params);
    }

    /**
     * 清空缓存
     */
    public void reset()
    {
        sqlString.setLength(0);
        lstParam.clear();
    }
    

    public SqlBuilder append(final char ch)
    {
        sqlString.append(ch);
        return this;
    }
    
//    public SqlBuilder append(final String sql)
//    {
//        sqlString.append(sql);
//        return this;
//    }
    
//    public SqlBuilder append(final String sql, final ISqlParam...param params)
//    {
//        sqlString.append(sql);
//        lstParam.add(param);
//        return this;
//    }
//    public SqlBuilder append(final String sql, final ISqlParam param1, final ISqlParam param2)
//    {
//        sqlString.append(sql);
//        lstParam.add(param1);
//        lstParam.add(param2);
//        return this;
//    }
    
    public SqlBuilder append(final String sql, final ISqlParam... params)
    {
        sqlString.append(sql);
        if (params != null)
        {
            lstParam.addAll(Arrays.asList(params));
        }
        return this;
    }


    /**
     * 获取sqlString
     * @return sqlString
     */
    public StringBuilder getSqlString()
    {
        return sqlString;
    }

    /**
     * 获取lstParam
     * @return lstParam
     */
    public List<ISqlParam> getLstParam()
    {
        return lstParam;
    }

    /**
     * 删除末尾的几个字符
     * @param length 要删除的长度
     */
    public void removeFromLast(final int length)
    {
        int len = sqlString.length() - length;
        if (len < 0)
        {
            len = 0;
        }
        sqlString.setLength(len);
    }
    

    /**
     * 将两一个缓存与自己合并
     * @param otherSqlBuf 另一个缓存
     */
    public void merge(final SqlBuilder otherSqlBuf)
    {
        sqlString.append(otherSqlBuf.sqlString.toString());
        lstParam.addAll(otherSqlBuf.lstParam);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return sqlString.toString();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object thatObj)
    {
        boolean eq = (thatObj instanceof SqlBuilder);
        if (eq)
        {
            SqlBuilder that = (SqlBuilder) thatObj;
            eq = CompareUtil.isEqual(sqlString.toString(), that.sqlString.toString())
                && CompareUtil.isDeepEqual(lstParam, that.lstParam);
        }
        return eq;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return sqlString.toString().hashCode() * 17 + lstParam.hashCode();
    }
}
