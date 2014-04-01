/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Apr 1, 2014
 */
package com.boroborome.footstone.svc;

import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        boroborome
 * @version       1.0 Apr 1, 2014
 */
public class DatabaseSystemInstallSvc implements ISystemInstallSvc
{
	private IDatabaseMgrSvc databaseMgrSvc;
	private Class loader;
	private String installFile;
	private String uninstallFile;
	
	/**
	 * 构造函数
	 * @param databaseMgrSvc
	 * @param loader The class used to load file.any class with the same package of sql file can be here.
	 * @param installFile
	 * @param uninstallFile
	 */
	public DatabaseSystemInstallSvc(IDatabaseMgrSvc databaseMgrSvc, Class loader, String installFile, String uninstallFile)
	{
		super();
		if (databaseMgrSvc == null || loader == null || installFile == null || uninstallFile == null)
		{
			throw new IllegalArgumentException("DatabaseSystemInstallSvc does not accept null parameter.");
		}
		this.databaseMgrSvc = databaseMgrSvc;
		this.loader = loader;
		this.installFile = installFile;
		this.uninstallFile = uninstallFile;
	}

	/* (non-Javadoc)
	 * @see com.boroborome.footstone.svc.ISystemInstallSvc#uninstall()
	 */
	@Override
	public void uninstall() throws MessageException
	{
		databaseMgrSvc.runSqlFile(loader.getResourceAsStream(installFile));
	}

	/* (non-Javadoc)
	 * @see com.boroborome.footstone.svc.ISystemInstallSvc#install()
	 */
	@Override
	public void install() throws MessageException
	{
		databaseMgrSvc.runSqlFile(loader.getResourceAsStream(uninstallFile));
	}

}
