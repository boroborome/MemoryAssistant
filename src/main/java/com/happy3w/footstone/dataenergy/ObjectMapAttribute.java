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

import com.happy3w.footstone.util.AttributeAccesser;
import com.happy3w.footstone.xml.XmlEnergyUtil;

import java.lang.reflect.Method;

/**
 * <DT><B>Title:</B></DT>
 * <DD>属性信息</DD>
 * <DT><B>Description:</B></DT>
 * <DD>记录一个属性对应的设置和获取方法</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-3-9
 */
public class ObjectMapAttribute extends AttributeAccesser {
    /**
     * 属性为简单类型的标记
     */
    public final boolean isSimpleType;

    /**
     * 构造函数
     *
     * @param name
     * @param getMethod
     * @param setMethod
     */
    public ObjectMapAttribute(String name, Method getMethod, Method setMethod) {
        super(name, getMethod, setMethod);

        isSimpleType = XmlEnergyUtil.isSimpleType(getMethod.getReturnType());
    }
}
