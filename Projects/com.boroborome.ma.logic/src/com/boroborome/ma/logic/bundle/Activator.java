package com.boroborome.ma.logic.bundle;

import java.sql.Connection;
import java.sql.DriverManager;

import org.osgi.framework.BundleContext;

import com.boroborme.ma.model.svc.IMAInformationSvc;
import com.boroborme.ma.model.svc.IMAKeywordSvc;
import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.sql.DefaultDatabaseMgrSvc;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;
import com.boroborome.footstone.svc.DatabaseSystemInstallSvc;
import com.boroborome.footstone.svc.ISystemInstallSvc;
import com.boroborome.ma.logic.impl.MAInformationSvcImpl;
import com.boroborome.ma.logic.impl.MAKeywordSvcImpl;
import com.boroborome.ma.logic.res.ResConst;

public class Activator extends AbstractFootstoneActivator
{
	@Override
	public void bundleStart(BundleContext context)
	{
		IDatabaseMgrSvc databaseMgrSvc = null;
        try
        {
            FootstoneSvcAccess.getResourceMgrSvc().regRes(ResConst.ResKey, ResConst.class.getResourceAsStream("resource.properties"));
            
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            String url = "jdbc:derby:database/madb;create=true";
          
            Activator.class.getClassLoader().loadClass(driver);
            Connection connection = DriverManager.getConnection(url);
            databaseMgrSvc = new DefaultDatabaseMgrSvc(connection);
            registerService(IDatabaseMgrSvc.class, databaseMgrSvc, null);
        }
        catch (Exception e)
        {
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
        if (databaseMgrSvc == null)
        {
        	return;
        }
        
        registerService(ISystemInstallSvc.class,
        		new DatabaseSystemInstallSvc(databaseMgrSvc, ResConst.class,
        				"install.sql", "uninstall.sql"), null);
        registerService(IMAKeywordSvc.class, new MAKeywordSvcImpl(databaseMgrSvc), null);
        registerService(IMAInformationSvc.class, new MAInformationSvcImpl(databaseMgrSvc), null);
	}

	@Override
	public void bundleStop(BundleContext context)
	{
		
	}

}
