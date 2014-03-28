/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-5
 */
package com.boroborome.footstone;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石Bundle激活器。</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>增加了通过静态方法获取Bundle相关信息的方法</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-5
 */
public abstract class AbstractFootstoneActivator implements BundleActivator
{
    /**
     * bundle信息<br>
     * 虽然是静态对象，但并不是整个系统共用的。因为一个Bundle占用一个Classloader，所以一个Bundle会有一个context对象，也因此这里的静态方法获取内容都只是本Bundle中内容。
     */
    private static BundleContext contextInstance;
    
    /**
     * 保存注册了的服务
     */
    private List<ServiceRegistration> lstService = new ArrayList<ServiceRegistration>();

    
    @Override
    public final void start(BundleContext context) throws Exception
    {
        contextInstance = context;
        
        bundleStart(context);
    }

    @Override
    public final void stop(BundleContext context) throws Exception
    {
        bundleStop(context);
        
        for (ServiceRegistration reg : lstService)
        {
            reg.unregister();
        }
        
        contextInstance = null;
    }
    
    /**
     * 获取给定名称的服务
     * @param serviceName 服务名称
     * @return 服务对象。如果没有初始化，或者没有服务名称则返回null
     */
    public static Object getService(String serviceName)
    {
        if (contextInstance == null || serviceName == null || serviceName.isEmpty())
        {
            return null;
        }
        
        ServiceReference ref = contextInstance.getServiceReference(serviceName);
        return ref == null ? null : contextInstance.getService(ref);
    }
    
    /**
     * 获取给定类型的服务
     * @param <T> 提供服务的接口类型
     * @param type 固定的type
     * @return 指定接口的实例。如果系统中没有会返回null
     */
    public static <T> T getService(Class<T> type)
    {
        return (T) getService(type.getName());
    }
    
    /**
     * 注册服务<br>
     * 注册的服务会被保存，当bundle结束时自动注销
     * @param serviceName 服务名称
     * @param service 服务对象
     * @param dic 字典
     */
    public final void registerService(String serviceName, Object service, Dictionary dic)
    {
        lstService.add(contextInstance.registerService(serviceName, service, dic));
    }
    
    /**
     * 注册服务<br>
     * 注册的服务会被保存，当bundle结束时自动注销
     * @param serviceName 服务名称
     * @param service 服务对象
     * @param dic 字典
     */
    public final <T> void registerService(Class<T> serviceType, T service, Dictionary dic)
    {
        lstService.add(contextInstance.registerService(serviceType.getName(), service, dic));
    }
    
    /**
     * Bundle启动时执行初始化动作
     * @param context
     */
    public abstract void bundleStart(BundleContext context);
    
    /**
     * Bundle停止时执行初始化动作
     * @param context
     */
    public abstract void bundleStop(BundleContext context);
}
