/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Apr 12, 2014
 */
package com.happy3w.footstone.svc;

import com.happy3w.footstone.exception.MessageException;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>The interface of datasvc which is support auto id</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author boroborome
 * @version 1.0 Apr 12, 2014
 */
public interface IAutoIDDataSvc<E> extends IDataSvc<E> {
    /**
     * The method to get MaxID
     *
     * @return
     * @throws MessageException
     */
    long getMaxID() throws MessageException;
}
