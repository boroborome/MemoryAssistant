/*
 * <P>Title:      基石模块</P>
 * <P>Description:界面数据支持。辅助ObjectAction完成界面更新，从界面获取数据的动作。由界面实现，提供给动作</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-19
 */
package com.boroborome.footstone.ui.action;

import com.boroborome.footstone.model.IBufferIterator;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>界面数据支持接口<br>当一个动作正在执行时可能从界面获取信息，或者将操作的结果显示在界面，或者要求更新，需要通过这个接口实现。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-19
 */
public interface IUIDataSupport<T>
{
    ObjectBasket<T> getObjectBasket();
    
    void add(IBufferIterator<T> it);
    void modify(IBufferIterator<T> it);
    void delete(IBufferIterator<T> it);
}
