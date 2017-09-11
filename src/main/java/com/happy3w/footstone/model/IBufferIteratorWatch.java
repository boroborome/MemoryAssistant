/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-8
 */
package com.happy3w.footstone.model;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-8
 */
public interface IBufferIteratorWatch<T>
{
    void beforeNext(IBufferIterator<T> it);
    
    void afterNext(IBufferIterator<T> it, T value);
    
    void beforeRemove(IBufferIterator<T> it);
    
    void afterRemove(IBufferIterator<T> it, T value);
}
