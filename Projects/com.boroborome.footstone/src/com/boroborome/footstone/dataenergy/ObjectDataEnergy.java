/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-7
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.dataenergy;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.boroborome.footstone.model.MapItem;
import com.boroborome.footstone.util.CommonRes;
import com.boroborome.footstone.xml.XmlEnergyUtil;


/**
 * <DT><B>Title:</B></DT>s
 *    <DD>对象数据引擎</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>将对象数据引擎驱动的元数据，来驱动数据机器。</DD>
 * 
 * 对象数据引擎在将对象转换为数据驱动信息时，需要如下信息:<br>
 * <ol>
 * <li><b>对象对应的字符串名称。</b><br>
 * 这个信息通过类型名称映射表完成。<br>
 * 对象数据引擎需要一个节点和类型之间的映射关系，如果没有加载类型映射关系表，则对象数据引擎只能处理下面类型<br>
 * <B>List->ArrayList</B><br>
 * List节点中所有属性无效，所有子节点都会添加到列表中。
 * <B>Map->HashMap</B><br>
 * Map节点中只接受ValueMap节点<br>
 * <B>ValueMap->ValueMap</B><br>
 * 复杂节点值无效，只有name、desc、code有效。
 * </li>
 * <li><B>对象需要提供的属性列表</B><br>
 * 这个信息通过带有SerialAttribute标记的get/is方法来标示需要保存的信息。
 * </li>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-7
 */
public class ObjectDataEnergy implements IDataEnergy
{
    /**
     * 数据引擎驱动的机器
     */
    private IDataMachine machine;

    /**
     * 将要放到数据引擎中用于驱动数据机器的元数据
     */
    private Object source;
    
    /**
     * 引擎使用的对象映射表
     */
    private IObjectMapTable mapTable;
    
    /**
     * 构造函数
     */
    public ObjectDataEnergy()
    {
        super();
        this.mapTable = DefaultObjectMapTable.getInstance();
    }

    /**
     * 构造函数
     * @param mapTable 对象映射表
     */
    public ObjectDataEnergy(final IObjectMapTable mapTable)
    {
        super();
        this.mapTable = mapTable;
    }


    /**
     * 获取mapTable
     * @return mapTable
     */
    public IObjectMapTable getMapTable()
    {
        return mapTable;
    }

    /**
     * 设置mapTable
     * @param mapTable mapTable
     */
    public void setMapTable(IObjectMapTable mapTable)
    {
        this.mapTable = mapTable;
    }

    public Object getSource()
    {
        return source;
    }

    public void setSource(Object source)
    {
        this.source = source;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataEnergy#getMachine()
     */
    @Override
    public IDataMachine getMachine()
    {
        return machine;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataEnergy#setMachine(com.boroborome.common.dataenergy.IDataMachine)
     */
    @Override
    public void setMachine(final IDataMachine machine)
    {
        this.machine = machine;
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.dataenergy.IDataEnergy#translateData()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void translateData() throws DataEnergyException
    {
        machine.startWork();
        
        Stack<DataItem> stack = new Stack<DataItem>();
        stack.push(new DataItem(source, null));
        
        while (!stack.isEmpty())
        {
            DataItem curItem = stack.lastElement();
            
            //如果第一次处理这个对象
            if (curItem.itAttribute == null)
            {
                Class<?> clazz = curItem.obj.getClass();
                curItem.objInfo = mapTable.findInfo(clazz);
                if (curItem.objInfo == null)
                {
                    throw new DataEnergyException(CommonRes.ResFileName,
                            CommonRes.DataEnergyTranslateFailed,
                            new Object[]{clazz});
                }
                
                machine.startData(curItem.objInfo.getName(), curItem.attributeName);
                curItem.itAttribute = curItem.objInfo.getAttributeIterator();
                if (curItem.obj instanceof List)
                {
                    curItem.itList = ((List) curItem.obj).iterator();
                }
                else if (curItem.obj instanceof Map)
                {
                    curItem.itList = new MapIterator((Map) curItem.obj);
                }
            }
            else if (curItem.itAttribute.hasNext())
            {
                ObjectMapAttribute attr = curItem.itAttribute.next();
                Object value = attr.getAttribute(curItem.obj);
                if (attr.isSimpleType || value != null && XmlEnergyUtil.isSimpleType(value.getClass()))
                {
                    machine.setAttribute(attr.name, value);
                }
                else
                {
                    stack.push(new DataItem(value, attr.name));
                }
            }
            else if (curItem.itList != null && curItem.itList.hasNext())
            {
                stack.push(new DataItem(curItem.itList.next(), null));
            }
            else
            {
                machine.endData(stack.pop().objInfo.getName());
            }
        }
        
        
        machine.endWork();
    }

    private static class DataItem
    {
        /**
         * 对象信息，用于转换。
         */
        public ObjectMapItem objInfo;
        
        /**
         * 需要转换的对象
         */
        public Object obj;
        /**
         * 对象在上一级对象应该的属性
         */
        public String attributeName;
        /**
         * 属性碟带器
         */
        public Iterator<ObjectMapAttribute> itAttribute;
        
        /**
         * 如果obj是一个列表，那么它的成员会通过这个枚举器处理.<br>
         * 如果obj是Map，则这个列表的成员是Iterator<MapItem>
         */
        public Iterator<Object> itList;
        
        /**
         * 构造函数
         * @param obj 需要转换的对象
         * @param attributeName 对象在上一级对象应该的属性
         */
        public DataItem(final Object obj, final String attributeName)
        {
            super();
            this.obj = obj;
            this.attributeName = attributeName;
        }
        
    }
    
    @SuppressWarnings("unchecked")
    private static class MapIterator implements Iterator
    {
        private Map map;
        private Iterator itKey;
        
        public MapIterator(Map map)
        {
            this.map = map;
            itKey = map.values().iterator();
        }
        
        @Override
        public boolean hasNext()
        {
            return itKey.hasNext();
        }

        @Override
        public Object next()
        {
            Object key = itKey.next();
            return new MapItem(key, map.get(key));
        }

        @Override
        public void remove()
        {
            //Nothing to do
        }
        
    }
}
