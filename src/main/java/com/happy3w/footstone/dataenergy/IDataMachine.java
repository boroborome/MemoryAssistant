/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-2-24
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.dataenergy;

/**
 * <P>Title:      数据机器接口</P>
 * Description:所有处理数据的机器需要实现的接口，他会处理数据引擎提供的各种数据。<br>
 * 三类动作的关系Work=[Data]*;Data=[Attribute]*,Data*;。<br>
 * 时间调用顺序:
 * startWork->
 *      [startData->
 *          setAttribute]*
 *          |
 *          [startData->...->endData]*
 *      ->endData]*
 * ->endWork
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-2-24
 */
public interface IDataMachine
{
    /**
     * 开始工作时调用，仅调用一次
     */
    void startWork() throws DataEnergyException;
    
    /**
     * 工作结束，仅在所有数据都转换完后调用一次
     */
    void endWork() throws DataEnergyException;
    
    /**
     * 开始一个数据<br>
     * 在数据引擎开始处理一个数据时，会启动这个方法。
     * @param name 数据的名字
     * @param attributeName 节点代表属性名称。此值可能为null，如果非空表示这个数据节点是一个对象的属性
     */
    void startData(final String name, final String attributeName) throws DataEnergyException;
    
    /**
     * 结束一个数据
     * @param name 数据的名字
     */
    void endData(final String name) throws DataEnergyException;
    
    /**
     * 设置属性
     * @param name 属性名称
     * @param value 属性值
     */
    void setAttribute(final String name, final Object value) throws DataEnergyException;
}
