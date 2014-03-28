/*
 * <P>Title:      任务管理器模型 V1.0</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-15
 */
package com.boroborome.footstone.svc;

import java.util.EventListener;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>任务管理模型</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>数据改变事件接口</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-15
 */
public interface IDataChangeListener<T> extends EventListener
{
    public static final String EVENT_CREATED = "onCreated";
    public static final String EVENT_DELETED = "onDeleted";
    public static final String EVENT_MODIFIED = "onModified";
    
    //数据被创建、删除、修改时的事件
    void onCreated(T value);
    void onDeleted(T value);
    void onModified(T value);
}
