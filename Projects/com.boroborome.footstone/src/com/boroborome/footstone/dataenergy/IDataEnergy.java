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
package com.boroborome.footstone.dataenergy;

/**
 * <P>Title:      数据引擎</P>
 * <P>Description:实现本接口的对象负责向数据机器提供数据</P>
 * 数据引擎处理的数据要求，数据中不能带有名称为_AN_的属性，这个名字被系统占用，用于处理复杂属性值
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-2-24
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
public interface IDataEnergy
{
    /**
     * 设置引擎使用的数据机器
     * @param machine 数据机器
     */
    void setMachine(final IDataMachine machine);
    
    /**
     * 获取数据机器
     * @return 数据机器
     */
    IDataMachine getMachine();
    
    /**
     * 转换数据，转换结束后才终止
     * @throws DataEnergyException 转换数据过程中可能出现的异常。
     */
    void translateData() throws DataEnergyException;
}
