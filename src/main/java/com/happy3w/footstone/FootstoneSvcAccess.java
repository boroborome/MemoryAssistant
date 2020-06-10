/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-7
 */
package com.happy3w.footstone;

import com.happy3w.footstone.exception.IExceptionGrave;
import com.happy3w.footstone.resource.IResourceMgrSvc;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>基石服务提供器<br>
 * 对外提供方便的获取基石对外提供服务的方法。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2011-7-7
 */
public final class FootstoneSvcAccess {
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * 获取异常处理
     *
     * @return
     */
    public static IExceptionGrave getExceptionGrave() {
        return beanFactory.getBean(IExceptionGrave.class);
    }

    /**
     * 获取资源管理服务
     *
     * @return
     */
    public static IResourceMgrSvc getResourceMgrSvc() {
        return beanFactory.getBean(IResourceMgrSvc.class);
//        return (IResourceMgrSvc) Activator.getService(IResourceMgrSvc.class.getName());
    }

    public static IDatabaseMgrSvc getDatabaseSvc() {
        return null;
    }

    public static void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        FootstoneSvcAccess.beanFactory = beanFactory;
    }
}
