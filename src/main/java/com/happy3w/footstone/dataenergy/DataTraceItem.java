/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-11
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.dataenergy;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>数据跟踪记录</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>辅助数据引擎或者数据机器，在遍历数据或者解析数据时需要对数据的中间结果进行跟踪</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-11
 */
public class DataTraceItem
{
    /**
     * 数据名称
     */
    public final String dataName;
    
    /**
     * 数据在上一个节点的属性名称
     */
    public final String attributeName;
    
    /**
     * 构造函数
     * @param dataName
     * @param attributeName
     */
    public DataTraceItem(String dataName, String attributeName)
    {
        super();
        this.dataName = dataName;
        this.attributeName = attributeName;
    }
    
    
}
