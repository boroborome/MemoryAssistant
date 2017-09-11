/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:异常埋葬者，负责处理一种异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.exception;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:异常埋葬者，负责处理一种异常，一个异常埋葬着可以处理一种异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-10
 */
public interface IExceptionBurier
{
    /**
     * 处理提供的异常
     * @param exception 需要处理的异常
     * @param buried 说明这个异常是否已经被处理过
     */
    void bury(final Exception exception, final boolean buried);
}