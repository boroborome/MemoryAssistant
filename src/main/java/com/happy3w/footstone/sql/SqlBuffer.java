/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-9-4
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:构造Sql时的缓存，类似StringBuilder</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-9-4
 */
public class SqlBuffer {
    /*
     * 用于查找站位符的正则表达式，占位符形为如{0}的内容
     */
    private static final Pattern holderPattern = Pattern.compile("\\{\\d+\\}"); //$NON-NLS-1$

    /*
     * 带有占位符的Sql字符串
     */
    private StringBuilder sqlBuf = new StringBuilder();

    /*
     * 各种参数的列表
     */
    private List<Object> lstParams = new ArrayList<Object>();

    /**
     * 构造函数
     */
    public SqlBuffer() {
        super();
    }

    /**
     * 构造函数
     *
     * @param sql 初始的sql语句
     */
    public SqlBuffer(final String sql) {
        super();
        append(sql);
    }

    /**
     * 构造函数
     *
     * @param sql    初始的sql语句
     * @param params 参数列表
     */
    public SqlBuffer(final String sql, final Object[] params) {
        super();
        append(sql, params);
    }

    /**
     * 清空缓存
     */
    public void reset() {
        sqlBuf.setLength(0);
        lstParams.clear();
    }

    private void formatSql(final String sql) {
        this.sqlBuf.append(sql);
        Matcher m = holderPattern.matcher(sql);
        int curCount = lstParams.size();

        while (m.find()) {
            int start = m.start() + 1;
            int end = m.end() - 1;
            int v = Integer.parseInt(sql.substring(start, end));
            this.sqlBuf.replace(this.sqlBuf.length() - sql.length() + start,
                    this.sqlBuf.length() - sql.length() + end, String.valueOf(curCount + v));
        }
    }

    public SqlBuffer append(final char ch) {
        sqlBuf.append(ch);
        return this;
    }

    public SqlBuffer append(final String sql) {
        formatSql(sql);
        return this;
    }

    public SqlBuffer append(final String sql, final Object param) {
        formatSql(sql);
        lstParams.add(param);
        return this;
    }

    public SqlBuffer append(final String sql, final Object param1, final Object param2) {
        formatSql(sql);
        lstParams.add(param1);
        lstParams.add(param2);
        return this;
    }

    public SqlBuffer append(final String sql, final Object[] params) {
        formatSql(sql);
        lstParams.addAll(Arrays.asList(params));
        return this;
    }

    /**
     * 获取sql
     *
     * @return sql
     */
    public StringBuilder getSqlBuf() {
        return sqlBuf;
    }

    /**
     * 获取lstParams
     *
     * @return lstParams
     */
    public List<Object> getLstParams() {
        return lstParams;
    }

    /**
     * 删除末尾的几个字符
     *
     * @param length 要删除的长度
     */
    public void removeFromLast(final int length) {
        int len = sqlBuf.length() - length;
        if (len < 0) {
            len = 0;
        }
        sqlBuf.setLength(len);
    }

    /**
     * 将两一个缓存与自己合并
     *
     * @param otherSqlBuf 另一个缓存
     */
    public void merge(final SqlBuffer otherSqlBuf) {
        if (lstParams.size() == 0) {
            sqlBuf.append(otherSqlBuf.sqlBuf.toString());
        } else {
            formatSql(otherSqlBuf.sqlBuf.toString());
        }
        lstParams.addAll(otherSqlBuf.lstParams);
    }

    @Override
    public String toString() {
        return sqlBuf.toString();
    }
}
