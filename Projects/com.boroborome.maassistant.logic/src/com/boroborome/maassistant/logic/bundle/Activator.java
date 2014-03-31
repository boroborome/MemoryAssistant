package com.boroborome.maassistant.logic.bundle;

import java.sql.Connection;
import java.sql.DriverManager;

import org.osgi.framework.BundleContext;

import com.boroborme.maassistant.model.svc.IMAInformationSvc;
import com.boroborme.maassistant.model.svc.IMAKeywordSvc;
import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.sql.DefaultDatabaseMgrSvc;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;
import com.boroborome.maassistant.logic.impl.MAInformationSvcImpl;
import com.boroborome.maassistant.logic.impl.MAKeywordSvcImpl;
import com.boroborome.maassistant.logic.res.ResConst;

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
        }
        catch (Exception e)
        {
            FootstoneSvcAccess.getExceptionGrave().bury(e);
        }
        if (databaseMgrSvc == null)
        {
        	return;
        }
        
        registerService(IMAKeywordSvc.class, new MAKeywordSvcImpl(databaseMgrSvc), null);
        registerService(IMAInformationSvc.class, new MAInformationSvcImpl(databaseMgrSvc), null);
	}

	@Override
	public void bundleStop(BundleContext context)
	{
		// TODO Auto-generated method stub
		
	}

}
