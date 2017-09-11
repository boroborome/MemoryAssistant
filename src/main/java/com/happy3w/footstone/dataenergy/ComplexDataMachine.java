/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-2-25
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.dataenergy;

import java.util.ArrayList;

/**
 * <P>Title:      复合的数据处理机</P>
 * <P>Description:这个数据处理机的作用是将多个数据处理机组合在一起，让多个数据处理机共同工作。</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-2-25
 */
public class ComplexDataMachine extends ArrayList<IDataMachine> implements IDataMachine
{
    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#endData()
     */
    @Override
    public void endData(String name) throws DataEnergyException
    {
        for (IDataMachine machine : this)
        {
            machine.endData(name);
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setAttribute(final String name, final Object value) throws DataEnergyException
    {
        for (IDataMachine machine : this)
        {
            machine.setAttribute(name, value);
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#startData(java.lang.String)
     */
    @Override
    public void startData(final String name, final String attributeName) throws DataEnergyException
    {
        for (IDataMachine machine : this)
        {
            machine.startData(name, attributeName);
        }
    }

    @Override
    public void endWork() throws DataEnergyException
    {
        for (IDataMachine machine : this)
        {
            machine.endWork();
        }
    }

    @Override
    public void startWork() throws DataEnergyException
    {
        for (IDataMachine machine : this)
        {
            machine.startWork();
        }
    }

}
