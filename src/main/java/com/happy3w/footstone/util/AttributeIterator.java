/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-5-21
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.util;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * <DT><B>Title:</B></DT>
 * <DD>[产品名称和版本号]</DD>
 * <DT><B>Description:</B></DT>
 * <DD>提供一个类类型，枚举所有的属性</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-5-21
 */
public class AttributeIterator implements Iterator<AttributeAccesser> {
    private Method[] methods;
    private int index;

    /**
     * 构造函数
     *
     * @param clazz 提供属性的类型
     */
    public AttributeIterator(Class<?> clazz) {
        methods = clazz.getMethods();
    }

    /**
     * 将方法当作是一个属性，获取属性名称<br>
     * 如果可以获取到属性名称，则是属性，否则不是属性
     *
     * @param method
     * @return
     */
    private String getAttributeName(Method method) {
        String resultAttributeName = null;
        String newAttrName = method.getName();

        //确定属性名字
        if (newAttrName.startsWith("get")) //$NON-NLS-1$
        {
            resultAttributeName = newAttrName.substring(3);
        } else if (newAttrName.startsWith("is")) //$NON-NLS-1$
        {
            resultAttributeName = newAttrName.substring(2);
        }
        return resultAttributeName;
    }

    /**
     * 获取属性的设置方法
     *
     * @param attributeName 属性名称
     * @return
     */
    private Method getSetMethod(String attributeName) {
        Method setMethod = null;
        String setName = "set" + attributeName; //$NON-NLS-1$
        for (int setMethodIndex = methods.length - 1; setMethodIndex >= 0; setMethodIndex--) {
            if (methods[setMethodIndex].getName().equals(setName)) {
                setMethod = methods[setMethodIndex];
                break;
            }
        }
        return setMethod;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        for (; index < methods.length; index++) {
            String attributeName = getAttributeName(methods[index]);
            if (attributeName != null) {
                break;
            }
        }
        return index < methods.length;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public AttributeAccesser next() {
        AttributeAccesser aa = null;

        if (hasNext()) {
            String attributeName = getAttributeName(methods[index]);
            aa = new AttributeAccesser(attributeName, methods[index], getSetMethod(attributeName));
            index++;
        }

        return aa;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("AttributeIterator Unsupport remove method."); //$NON-NLS-1$
    }

}
