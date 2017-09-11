/*
 * <P>Title:      任务管理器 V1.0</P>
 * <P>Description:数据管理接口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-6-24
 */
package com.happy3w.footstone.svc;

import java.util.Iterator;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.model.EventContainer;

/**
 * @author BoRoBoRoMe
 * TODO [optimize] this interface can be defined with input Iterator and output Iterator.
 */
public interface IDataSvc<E>
{
    /**
     * 创建一系列对象
     * @param it
     * @throws MessageException
     */
    void create(Iterator<E> it) throws MessageException;
    
    /**
     * 修改一系列信息
     * @param it
     * @throws MessageException
     */
    void modify(Iterator<E> it) throws MessageException;
    
    /**
     * 删除一系列东西
     * @param it
     * @throws MessageException
     */
    void delete(Iterator<E> it) throws MessageException;
    
    /**
     * 根据条件查询数据
     * @param condition
     * @return
     * @throws MessageException
     */
    Iterator<E> query(IDataCondition<E> condition) throws MessageException;
    
    /**
     * 获取事件容器。通过这个容易可以监视这个数据管理服务处理数据的增加、删除、修改事件
     * @return
     */
    EventContainer<IDataChangeListener<E>> getEventContainer();
}
