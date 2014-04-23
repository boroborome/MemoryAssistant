package com.boroborome.ma.logic.bundle;

import java.sql.Connection;
import java.sql.DriverManager;

import org.osgi.framework.BundleContext;

import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.sql.DefaultDatabaseMgrSvc;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;
import com.boroborome.footstone.svc.DatabaseSystemInstallSvc;
import com.boroborome.footstone.svc.IIDGeneratorSvc;
import com.boroborome.footstone.svc.ISystemInstallSvc;
import com.boroborome.ma.logic.impl.MAInformationSvcImpl;
import com.boroborome.ma.logic.impl.MAKeywordSvcImpl;
import com.boroborome.ma.logic.res.ResConst;
import com.boroborome.ma.model.MAKeyword;
import com.boroborome.ma.model.svc.IMAInformationSvc;
import com.boroborome.ma.model.svc.IMAKeywordSvc;

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
        MAKeywordSvcImpl keywordSvc = new MAKeywordSvcImpl(databaseMgrSvc);
        registerService(IMAKeywordSvc.class, keywordSvc, null);
        registerService(IMAInformationSvc.class, new MAInformationSvcImpl(databaseMgrSvc, keywordSvc), null);
        
        IIDGeneratorSvc idGenerator = getService(IIDGeneratorSvc.class);
        idGenerator.init(MAKeyword.class, keywordSvc);
	}

	@Override
	public void bundleStop(BundleContext context)
	{
		
	}

}
