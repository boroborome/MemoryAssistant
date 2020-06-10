/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-16
 */
package com.happy3w.footstone.exception;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>消息异常接口<br>
 * 只有异常才继承这个接口，继承这个接口的异常可以提供多语言信息支持。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2011-7-16
 */
public interface IMessageException {
    /**
     * 获取消息<br>
     * 从资源文件中获取相关内容
     *
     * @see Throwable#getMessage()
     */
    String getMessage();

    /**
     * 获取资源文件中消息格式串的键值
     *
     * @return
     */
    String getFormatKey();

    /**
     * 获取资源路径
     *
     * @return 资源路径
     */
    String getResPath();

    /**
     * 获取错误相关参数
     *
     * @return 错误相关参数
     */
    Object[] getParams();
}
