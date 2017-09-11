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
 *    <DD>Sql参数设置</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>SqlBuilder用于管理参数的接口</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-4-25
 */
public interface ISqlParam
{
    /**
     * 将保存的参数信息通过参数设置接口设置到执行sql的工具中
     * @param setter
     * @throws SQLException
     */
    void setParam(IParamSetter setter) throws SQLException;
}
