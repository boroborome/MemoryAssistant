package com.boroborome.footstone.bundle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.BundleContext;

import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.exception.IExceptionGrave;
import com.boroborome.footstone.res.ResConst;
import com.boroborome.footstone.resource.IResourceMgrSvc;
import com.boroborome.footstone.ui.action.IObjectActionSvc;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-6
 */
public class Activator extends AbstractFootstoneActivator
{
    @Override
    public void bundleStart(BundleContext context)
    {
//        DOMConfigurator.configure("conf/log4j.xml");
//        JoranConfigurator.configure("conf/log4j.xml");
        PropertyConfigurator.configure("conf/log4j.properties");
        registerService(IResourceMgrSvc.class.getName(), new ResourceMgrSvcImpl(), null);
        registerService(IExceptionGrave.class.getName(), new ExceptionGraveImpl(), null);
        registerService(IObjectActionSvc.class.getName(), new ObjectActionSvcImpl(), null);
        try
        {
            FootstoneSvcAccess.getResourceMgrSvc().regRes(ResConst.ResKey, ResConst.class.getResourceAsStream("resource.properties"));
        }
        catch (Exception e)
        {
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
    }

    @Override
    public void bundleStop(BundleContext context)
    {
        // TODO Auto-generated method stub
        
    }

}
