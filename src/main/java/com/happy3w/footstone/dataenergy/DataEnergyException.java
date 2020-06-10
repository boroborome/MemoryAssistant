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

import com.happy3w.footstone.exception.MessageException;

/**
 * <P>Title:      数据引擎相关的异常</P>
 * <P>Description:传递错误信息</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2010-2-24
 */
public class DataEnergyException extends MessageException {

    /**
     * 构造函数
     *
     * @param resPath 资源路径
     * @param message 消息资源Key值，Key值对应的字符串值可以带有若干个站位符，与params中个数一致
     * @param params  参数列表
     * @param cause   导致错误的原因，可以为Null
     */
    public DataEnergyException(final String resPath, final String message,
                               final Object[] params, final Throwable cause) {
        super(resPath, message, params, cause);
    }

    /**
     * 构造函数
     *
     * @param resPath 资源路径
     * @param message 消息资源Key值，Key值对应的字符串值可以带有若干个站位符，与params中个数一致
     * @param cause   导致错误的原因，可以为Null
     */
    public DataEnergyException(final String resPath, final String message, final Throwable cause) {
        super(resPath, message, cause);
    }

    /**
     * 构造函数
     *
     * @param resPath 资源路径
     * @param message 消息资源Key值，Key值对应的字符串值可以带有若干个站位符，与params中个数一致
     * @param params  参数列表
     */

    public DataEnergyException(final String resPath, final String message, final Object[] params) {
        super(resPath, message, params);
    }

    /**
     * 构造函数
     *
     * @param resPath 资源路径
     * @param message 消息资源Key值，Key值对应的字符串值可以带有若干个站位符，与params中个数一致
     */
    public DataEnergyException(final String resPath, final String message) {
        super(resPath, message);
    }

}
