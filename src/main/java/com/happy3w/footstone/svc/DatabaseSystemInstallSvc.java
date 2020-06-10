/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 Apr 1, 2014
 */
package com.happy3w.footstone.svc;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author boroborome
 * @version 1.0 Apr 1, 2014
 */
@Service
public class DatabaseSystemInstallSvc implements ISystemInstallSvc {
    @Autowired
    private Environment env;

    @Autowired
    private IDatabaseMgrSvc databaseMgrSvc;

    /* (non-Javadoc)
     * @see com.boroborome.footstone.svc.ISystemInstallSvc#uninstall()
     */
    @Override
    public void uninstall() throws MessageException {
        String uninstallFile = env.getProperty("happy3w.footstone.installsvc.uninstall");
        databaseMgrSvc.runSqlFile(this.getClass().getClassLoader().getResourceAsStream(uninstallFile));
    }

    /* (non-Javadoc)
     * @see com.boroborome.footstone.svc.ISystemInstallSvc#install()
     */
    @Override
    public void install() throws MessageException {
        String installFile = env.getProperty("happy3w.footstone.installsvc.install");
        databaseMgrSvc.runSqlFile(this.getClass().getClassLoader().getResourceAsStream(installFile));
    }

}
