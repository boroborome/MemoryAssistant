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

import com.happy3w.footstone.exception.RuntimeMessageException;
import com.happy3w.footstone.util.AttributeAccesser;
import com.happy3w.footstone.util.AttributeIterator;
import com.happy3w.footstone.util.CommonRes;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <DT><B>Title:</B></DT>
 * <DD>对象映射条目</DD>
 * <DT><B>Description:</B></DT>
 * <DD>保存对象和对象名称，以及对象属性之间的关系</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-3-9
 */
public class ObjectMapItem {
    private String name;
    private Class<?> clazz;

    private Map<String, ObjectMapAttribute> mapAttribute;
    /**
     * 后置方法，所有属性都设置好后进行初始化的方法
     */
    private Method postMethod;

    /**
     * 构造函数
     */
    public ObjectMapItem() {
        super();
    }

    /**
     * 构造函数
     *
     * @param name  名称
     * @param clazz 类型
     */
    public ObjectMapItem(final String name, final Class<?> clazz) {
        super();
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 获取name
     *
     * @return name
     */
    @SerialAttribute
    public String getName() {
        return name;
    }

    /**
     * 设置name
     *
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 获取clazz
     *
     * @return clazz
     */
    @SerialAttribute
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * 设置clazz
     *
     * @param clazz clazz
     */
    public void setClazz(final Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * 创建一个对象实例
     *
     * @return 对象实例
     * @throws DataEnergyException
     */
    public Object newInstance() throws DataEnergyException {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new DataEnergyException(CommonRes.ResFileName,
                    CommonRes.CreateInstanceFailed,
                    new Object[]{name}, e);
        }
    }

    /**
     * 从类型信息中初始化属性<br>
     * 所有带有SerailAttribute注视则为属性
     */
    private void initMapAttribute() {
        mapAttribute = new HashMap<String, ObjectMapAttribute>();

        AttributeIterator ai = new AttributeIterator(clazz);
        while (ai.hasNext()) {
            AttributeAccesser aa = ai.next();
            if (aa.getGetMethod().isAnnotationPresent(SerialAttribute.class)) {
                mapAttribute.put(aa.getName(),
                        new ObjectMapAttribute(aa.getName(), aa.getGetMethod(), aa.getSetMethod()));
            }
        }
    }

    /**
     * 获取特定的属性
     *
     * @param attributeName 属性名字
     * @return 属性信息
     */
    public ObjectMapAttribute getAttribute(final String attributeName) {
        if (mapAttribute == null) {
            initMapAttribute();
        }
        return mapAttribute.get(attributeName);
    }

    /**
     * 设置obj的属性attribute为value
     *
     * @param obj
     * @param attributeName
     * @param value
     */
    public void setAttributeValue(final Object obj, final String attributeName, final Object value) {
        ObjectMapAttribute attributeInfo = getAttribute(attributeName);
        if (obj != null && attributeInfo != null) {
            attributeInfo.setAttribute(obj, value);
        }
    }

    /**
     * 获取obj的属性attribute
     *
     * @param obj
     * @param attributeName
     * @return
     * @since: [产品/模块版本，表示从哪个版本开始有]
     * @!deprecated [表示不建议使用]
     * @modify [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
     */
    public Object getAttributeValue(final Object obj, final String attributeName) {
        Object value = null;
        ObjectMapAttribute attributeInfo = getAttribute(attributeName);
        if (obj != null && attributeInfo != null) {
            value = attributeInfo.getAttribute(obj);
        }
        return value;
    }

    /**
     * 获取属性枚举器
     *
     * @return 属性枚举器
     */
    public Iterator<ObjectMapAttribute> getAttributeIterator() {
        if (mapAttribute == null) {
            initMapAttribute();
        }
        return mapAttribute.values().iterator();
    }

    /**
     * 获取后置方法
     *
     * @return 后置方法
     */
    public Method getPostMethod() {
        if (mapAttribute == null) {
            initMapAttribute();
        }
        return postMethod;
    }

    /**
     * 运行对象的后置方法
     *
     * @param value 需要执行后置方法的对象
     */
    public void runPostMethod(final Object value) {
        if (postMethod != null && value != null) {
            try {
                postMethod.invoke(value);
            } catch (Exception e) {
                throw new RuntimeMessageException(CommonRes.ResFileName,
                        CommonRes.RunMethodFailed, new Object[]{postMethod.getName(), name}, e);
            }
        }
    }
}
