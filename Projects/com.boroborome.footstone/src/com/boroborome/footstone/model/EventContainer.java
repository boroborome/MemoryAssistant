/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:事件容器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-20
 */
package com.boroborome.footstone.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:事件容器，负责保存各种事件，并逐个触发他们
 * 这里的泛型应该和构造函数传入的类型相同，否则无法运行</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-20
 */
public class EventContainer<T extends EventListener>
{
    private static Logger log = Logger.getLogger(EventContainer.class);
    
    /**
     * <DT><B>Title:</B></DT>
     *    <DD>基石</DD>
     * <DT><B>Description:</B></DT>
     *    <DD>处理事件的模式</DD>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2011-10-15
     */
    public static enum Mode
    {
        /**
         * 正常触发事件
         */
         Normal,
        
        /**
         * 忽略事件
         */
        Ignore,
        
        /**
         * 事件延迟，延迟的事件只有手动才能触发
         */
        Delay,
    }

    /**
     * 容器负责的监听器类型,应该是一个接口
     */
    private Class<T> listenerType;
    
    /**
     * 所有支持的操作列表，这里只存放可以被调用的接口，参数只能有一个
     */
    private Map<String,Method> mapMethod = new HashMap<String, Method>();

    /*
     * 事件列表
     */
    private List<EventListener> events;
    
    /*
     * 被延迟的事件列表
     */
    private Map<String, EventItem> delayEvents;
    
    /*
     * 事件处理模式
     */
    private Mode mode;
    
    /**
     * 构造函数
     * @param listenerType 监听类型。这是一个接口，此接口不能有同名的一个参数的函数出现，否则运行结果未知
     */
    public EventContainer(final Class listenerType)
    {
//        Type[] args = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
//        if (args == null || args.length != 1)
//        {
//            throw new IllegalArgumentException("The EventContainer not define a parameterizedType.");
//        }
        this.listenerType = listenerType;//(Class<T>) args[0];
        Method[] methods = listenerType.getMethods();
        for (int i = methods.length - 1; i >= 0; i--)
        {
            Method method = methods[i];
            
            //这里是简单的处理方法，认为不会出现相同名称的方法，都具有一个参数的情况
            if (method.getParameterTypes().length == 1)
            {
                mapMethod.put(method.getName(), method);
            }
        }
        
        mode = Mode.Normal;
        events = new ArrayList<EventListener>();
        delayEvents = new HashMap<String, EventItem>();
    }

    /**
     * 获取处理事件的模式
     * @return 处理事件的模式
     */
    public Mode getMode()
    {
        return mode;
    }

    /**
     * 设置处理事件的模式
     * @param mode 处理事件的模式
     */
    public void setMode(final Mode mode)
    {
        this.mode = mode;
    }
    
    /**
     * 添加事件监听
     * @param listener 需要添加的事件监听
     */
    public void addEventListener(final T listener)
    {
        if (!listenerType.isInstance(listener))
        {
            throw new IllegalArgumentException(listener 
            		+ " is not instanceof " + listenerType); //$NON-NLS-1$
        }
        if (listener != null)
        {
            events.add(listener);
        }
    }
    
    /**
     * 移除一个监听
     * @param listener 需要被移除的监听
     */
    public void removeEventListener(final T listener)
    {
        events.remove(listener);
    }
    
    /**
     * 获取当前所有监听列表
     * @return 监听列表
     */
    @SuppressWarnings("unchecked")
	public T[] getListeners()
    {
        return (T[]) events.toArray(new EventListener[events.size()]);
    }
    
    /**
     * 触发事件
     * @param event 需要出发的事件
     * @param e 事件参数,触发事件时需要传入的参数，要求事件只接受一个参数
     */
    public void fireEvents(final String event, final Object e)
    {
        if (mode == Mode.Normal)
        {
            realFireEvents(event, e);
        }
        else if (mode == Mode.Delay)
        {
            if (!delayEvents.containsKey(event))
            {
                delayEvents.put(event, new EventItem(event, e));
            }
        }
        else
        {
            //如果被忽略则不处理
        }
    }
    
    /**
     * 清除被延迟的事件
     */
    public void clearDelayEvents()
    {
        delayEvents.clear();
    }
    
    /**
     * 触发被延迟的事件
     */
    public void fireDelayEvents()
    {
        for (EventItem event : delayEvents.values())
        {
            try
            {
                realFireEvents(event.event, event.e);
            }
            catch (Throwable exp)
            {
                log.error("realFireEvents failed.", exp);
            }
        }
    }
    
    /**
     * 真正的事件执行
     * @param eventName 需要执行的事件
     * @param e 事件参数
     */
    private void realFireEvents(final String eventName, final Object e)
    {
        if (events.size() > 0)
        {
            //制造参数列表
            Object[] params = new Object[]{e};
            
//            //制造参数类型列表
//            Class[] paramTypes = null;
//            if (e != null)
//            {
//                paramTypes = new Class[]{e.getClass()};
//            }
//            else
//            {
//                paramTypes = new Class[]{null};
//            }
            //获取方法
            Method method = mapMethod.get(eventName);
            if (method == null)
            {
                throw new IllegalArgumentException("Not find the method " + eventName); //$NON-NLS-1$
            }
            
            //运行所有监听器方法
            for (EventListener eventListener : events)
            {
                try
                {
                    method.invoke(eventListener, params);
                }
                catch (Throwable exp)
                {
                    log.error("realFireEvents failed.", exp);
                }
            }
        }
    }
    
    /**
     * <P>Title:      工具包 Util v1.0</P>
     * <P>Description:一个事件信息</P>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2008-1-20
     */
    private static class EventItem
    {
        /*
         * 事件
         */
        public final String event;
        
        /*
         * 事件参数
         */
        public final Object e;
        
        /**
         * 构造函数
         * @param event 事件
         * @param e 事件参数
         */
        public EventItem(final String event, final Object e)
        {
            this.event = event;
            this.e = e;
        }
    }
}
