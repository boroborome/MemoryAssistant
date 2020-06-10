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
package com.happy3w.footstone.dataenergy;

import com.happy3w.footstone.model.MapItem;
import com.happy3w.footstone.util.CommonRes;
import com.happy3w.footstone.xml.XmlDataEnergy;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <DT><B>Title:</B></DT>
 * <DD>对象映射表</DD>
 * <DT><B>Description:</B></DT>
 * <DD>用于提供对象和对象名称之间的映射关系</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-3-9
 */
public class DefaultObjectMapTable implements IObjectMapTable {
    private static DefaultObjectMapTable instance;

    /**
     * 获取对象映射表的默认实例<br>
     * 这个是从默认XML文件加载的实例。
     *
     * @return 对象映射表的默认实例
     */
    public static DefaultObjectMapTable getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    /**
     * 创建默认实例<br>
     * 这个方法中代码比较少，但不放在getInstance中，目的是为了正确的使用同步<br>
     * 当前认为这样的同步结构是正确的。
     *
     * @return 创建的实例
     */
    private static synchronized DefaultObjectMapTable createInstance() {
        if (instance != null) {
            return instance;
        }
        DefaultObjectMapTable newInstance = null;
        try {
            newInstance = loadMapTable(ClassLoader.getSystemResourceAsStream(
                    CommonRes.ObjectMapTableConfig));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return newInstance;
    }

    /**
     * 在提供的数据流上读取一个对象映射表
     *
     * @param inputStream 数据流
     * @return 对象映射表
     * @throws DataEnergyException 如果数据流不合法，则会出现异常
     */
    public static DefaultObjectMapTable loadMapTable(InputStream inputStream) throws DataEnergyException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Paramerter inputStream can't be null"); //$NON-NLS-1$
        }

        DefaultObjectMapTable newInstance = new DefaultObjectMapTable();
        newInstance.appendMapTable(inputStream);
        return newInstance;
    }

    /**
     * 通过对象名称查找对象信息
     */
    private Map<String, ObjectMapItem> mapNameType = new HashMap<String, ObjectMapItem>();
    /**
     * 通过对象类型查找对象信息
     */
    private Map<Class<?>, ObjectMapItem> mapTypeName = new HashMap<Class<?>, ObjectMapItem>();

    /**
     * 是否自学。如果开启了自学，则对象映射表在遇到一个没有处理过的类时会自己为这个对象分配一个名字。
     */
    private boolean isSelfStudy = false;

    /**
     * 构造函数
     */
    public DefaultObjectMapTable() {
        super();
    }

    /**
     * 是否自学。如果开启了自学，则对象映射表在遇到一个没有处理过的类时会自己为这个对象分配一个名字。
     *
     * @return isSelfStudy
     */
    public boolean isSelfStudy() {
        return isSelfStudy;
    }

    /**
     * 设置isSelfStudy
     *
     * @param isSelfStudy isSelfStudy
     */
    public void setSelfStudy(boolean isSelfStudy) {
        this.isSelfStudy = isSelfStudy;
    }

    /**
     * 在提供的数据流上读取一个对象映射表，并将读取的信息添加到对象映射表
     *
     * @param inputStream 数据流
     * @return 对象映射表
     * @throws DataEnergyException 如果数据流不合法，则会出现异常
     */
    @SuppressWarnings({
            "unchecked", "nls"
    })
    public void appendMapTable(InputStream inputStream) throws DataEnergyException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Paramerter inputStream can't be null"); //$NON-NLS-1$
        }

        XmlDataEnergy energy = new XmlDataEnergy(inputStream);

        //初始化一个默认的对象表
        DefaultObjectMapTable mapTable = new DefaultObjectMapTable();
        mapTable.reg("List", ArrayList.class);
        mapTable.reg("Map", HashMap.class);
        mapTable.reg(ObjectMapItem.class.getSimpleName(), ObjectMapItem.class);
        mapTable.reg(MapItem.class.getSimpleName(), MapItem.class);

        ObjectDataMachine machine = new ObjectDataMachine(mapTable);
        energy.setMachine(machine);
        energy.translateData();

        for (ObjectMapItem item : (List<ObjectMapItem>) machine.getResult()) {
            this.reg(item.getName(), item.getClazz());
        }
    }

    /**
     * 注册一组名字和类型
     *
     * @param name  类型名称
     * @param clazz 类型
     */
    public void reg(final String name, final Class<?> clazz) {
        ObjectMapItem info = mapNameType.get(name);
        if (info == null || info.getClazz() != clazz) {
            ObjectMapItem mapItem = new ObjectMapItem(name, clazz);
            mapNameType.put(name, mapItem);
            mapTypeName.put(clazz, mapItem);
        }
    }

    @Override
    public ObjectMapItem findInfo(final String name) {
        return mapNameType.get(name);
    }

    @Override
    public ObjectMapItem findInfo(final Class<?> clazz) {
        ObjectMapItem item = mapTypeName.get(clazz);
        if (item == null && isSelfStudy) {
            String simpleName = clazz.getSimpleName();
            String name = simpleName;
            for (int i = 0; mapNameType.get(name) != null; i++) {
                name = simpleName + i;
            }
            reg(name, clazz);
            item = mapTypeName.get(clazz);
        }
        return item;
    }
}
