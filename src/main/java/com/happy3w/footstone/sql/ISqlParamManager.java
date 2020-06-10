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

/**
 * <DT><B>Title:</B></DT>
 * <DD>Sql参数管理器</DD>
 * <DT><B>Description:</B></DT>
 * <DD>在批量处理数据时，为了不重复创建Sql，所以使用Sql参数构建器</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-5-23
 */
public interface ISqlParamManager {
    /**
     * 检测是否还有需要处理的数据
     *
     * @return
     */
    boolean hasNext();

    /**
     * 处理下一个参数
     */
    void next();

    /**
     * 构建一个Sql参数
     *
     * @param paramFlag Sql参数在获取值时使用的标记
     * @return
     */
    ISqlParam createSqlParam(Object paramFlag);
}
