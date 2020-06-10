/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-5-16
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.util;

import com.happy3w.footstone.exception.RuntimeMessageException;

import java.lang.reflect.Method;

/**
 * <DT><B>Title:</B></DT>
 * <DD>属性访问器</DD>
 * <DT><B>Description:</B></DT>
 * <DD>访问属性的工具</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-5-16
 */
public class AttributeAccesser {
    /**
     * 属性名称<br>
     * 如果获取方法是getHeight,则属性名称为Height.
     */
    public final String name;

    /**
     * 从对象获取属性的方法
     */
    public final Method getMethod;

    /**
     * 设置属性方法
     */
    public final Method setMethod;

    /**
     * 构造函数
     *
     * @param name
     * @param getMethod
     * @param setMethod
     */
    public AttributeAccesser(String name, Method getMethod, Method setMethod) {
        super();
        this.name = name;
        this.getMethod = getMethod;
        this.setMethod = setMethod;
    }

    /**
     * 获取name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取getMethod
     *
     * @return getMethod
     */
    public Method getGetMethod() {
        return getMethod;
    }

    /**
     * 获取setMethod
     *
     * @return setMethod
     */
    public Method getSetMethod() {
        return setMethod;
    }

    /**
     * 设置属性的值
     *
     * @param obj   对象
     * @param value 属性值
     */
    public void setAttribute(final Object obj, final Object value) {
        try {
            setMethod.invoke(obj, value);
        } catch (Exception e) {
            throw new RuntimeMessageException(CommonRes.ResFileName,
                    CommonRes.RunMethodFailed, new Object[]{"set", name}, e); //$NON-NLS-1$
        }
    }

    /**
     * 获取属性值
     *
     * @param obj 对象
     * @return
     */
    public Object getAttribute(final Object obj) {
        Object value = null;
        try {
            value = getMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeMessageException(CommonRes.ResFileName,
                    CommonRes.RunMethodFailed, new Object[]{"get", name}, e); //$NON-NLS-1$
        }
        return value;
    }
}
