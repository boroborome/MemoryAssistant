/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-9
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.dataenergy;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>对象映射表接口</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>对外提供对象及对象名称之间关系，并辅助处理对象属性列表</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-9
 */
public interface IObjectMapTable
{
    /**
     * 通过对象类型确定对象的信息
     * @param clazz 对象类型
     * @return 对象信息
     */
    ObjectMapItem findInfo(final Class<?> clazz);
    
    /**
     * 根据对象名称查找对象信息
     * @param name 对象名称
     * @return 对象信息
     */
    ObjectMapItem findInfo(final String name);
}
