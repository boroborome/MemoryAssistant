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
 * <DT><B>Title:获取值方法</B></DT>
 * <DD>[产品名称和版本号]</DD>
 * <DT><B>Description:</B></DT>
 * <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-5-23
 */
public interface IGetValueMethod {
    /**
     * 获取值
     *
     * @param source 获取数据的源泉
     * @param param  获取数据的参数，参数标记
     * @return 得到的值
     */
    Object getValue(Object source, Object param);
}
