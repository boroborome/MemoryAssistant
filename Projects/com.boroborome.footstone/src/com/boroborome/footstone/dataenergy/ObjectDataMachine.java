/*
 * <P>Title:      Common V1.0</P>
 * <P>Description:提供系统使用的公共功能</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-11
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.dataenergy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.boroborome.footstone.model.MapItem;
import com.boroborome.footstone.util.CommonRes;

/**
 * <P>Title:      对象数据机器</P>
 * <P>Description:从数据引擎转换数据对象</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-11
 */
public class ObjectDataMachine implements IDataMachine
{
    /**
     * 引擎使用的对象映射表
     */
    private IObjectMapTable mapTable;
    
    private Stack<DataItem> stackResult = new Stack<DataItem>();

    private List<Object> lstResult = new ArrayList<Object>();
    
    /**
     * 构造函数
     */
    public ObjectDataMachine()
    {
        super();
        mapTable = DefaultObjectMapTable.getInstance();
    }

    /**
     * 获取转换结果列表
     * @return 转换结果列表
     */
    public List<Object> getLstResult()
    {
        return lstResult;
    }
    
    /**
     * 获取转换结果<br>
     * 如果有多个结果，则将第一个结果返回。
     * @return 转换结果
     */
    public Object getResult()
    {
        Object result = null;
        if (!lstResult.isEmpty())
        {
            result = lstResult.get(0);
        }
        return result;
    }
    
    /**
     * 构造函数
     * @param mapTable
     */
    public ObjectDataMachine(IObjectMapTable mapTable)
    {
        super();
        this.mapTable = mapTable;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#endData()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void endData(String name)
    {
        DataItem item = stackResult.pop();
        ObjectMapItem info = mapTable.findInfo(item.dataItem);
        info.runPostMethod(item.value);
        
        if (stackResult.isEmpty())
        {
            lstResult.add(item.value);
        }
        else
        {
            DataItem parent = stackResult.lastElement();
            if (item.attributeName != null)
            {
                ObjectMapItem parentInfo = mapTable.findInfo(parent.dataItem);
                parentInfo.setAttributeValue(parent.value, item.attributeName, item.value);
            }
            else if (parent.value instanceof List)
            {
                ((List) parent.value).add(item.value);
            }
            else if (parent.value instanceof Map
                    && item.value instanceof MapItem)
            {
                MapItem map = (MapItem) item.value;
                ((Map) parent.value).put(map.getKey(), map.getValue());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#endWork()
     */
    @Override
    public void endWork()
    {
        //Nothing to do
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setAttribute(String name, Object value) throws DataEnergyException
    {
        DataItem item = stackResult.lastElement();
        ObjectMapItem objInfo = mapTable.findInfo(item.dataItem);
        objInfo.setAttributeValue(item.value, name, value);
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#startData(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void startData(String name, String attributeName) throws DataEnergyException
    {
        //这个节点代表的对象
        Object value = null;
            
        //如果属性不为null，则尽量从上一个对象上获取它的属性当做是当前处理节点的对象
        if (attributeName != null)
        {
            DataItem parent = stackResult.lastElement();
            if (parent != null)
            {
                value = mapTable.findInfo(parent.dataItem).getAttributeValue(parent.value, attributeName);
                if (value instanceof List)
                {
                    ((List) value).clear();
                }
                else if (value instanceof Map)
                {
                    ((Map) value).clear();
                }
            }
        }
        
        //如果无法获取已经存在数据实例，则创建一个新的实例
        if (value == null)
        {
            ObjectMapItem objInfo = mapTable.findInfo(name);
            if (objInfo != null)
            {
                value = objInfo.newInstance();
            }
            else
            {
                throw new DataEnergyException(CommonRes.ResFileName,
                        CommonRes.UnknownDataName,
                        new Object[]{name});
            }
        }
        
        DataItem item = new DataItem(name, attributeName, value);
        stackResult.push(item);
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataMachine#startWork()
     */
    @Override
    public void startWork()
    {
        //Nothing to do
    }

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
    private static class DataItem
    {
        public final String dataItem;
        public final String attributeName;
        public final Object value;
        /**
         * 构造函数
         * @param dataItem
         * @param attributeName
         * @param value
         */
        public DataItem(String dataItem, String attributeName, Object value)
        {
            super();
            this.dataItem = dataItem;
            this.attributeName = attributeName;
            this.value = value;
        }
    }
}
