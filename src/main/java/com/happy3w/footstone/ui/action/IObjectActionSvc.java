/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-19
 */
package com.happy3w.footstone.ui.action;

import com.happy3w.footstone.model.IBufferIterator;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>对象动作服务<br>
 *    一个类型的对象上可以进行一系列的操作。一般有增加删除修改查询。此服务用于管理对象可以执行操作的列表</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-19
 */
public interface IObjectActionSvc
{
    /**
     * 注册某个类型的对象使用的动作<br>
     * 注册动作的id在type的所有动的中必须唯一，否则会被覆盖
     * @param <T> 对象类型
     * @param action 对象支持的动作
     * @param type 对象类型
     */
    void regAction(AbstractObjectAction action, Class type);
    
    /**
     * 注销对象类型type中名称为name的动作。
     * @param id
     * @param type
     */
    void unregAction(String id, Class type);
    
    /**
     * 根据对象类型获取所有可以操作的动作
     * @param type
     * @return 返回的动作都是原来注册动作的副本
     */
    IBufferIterator<AbstractObjectAction> getActions(Class type);
}
