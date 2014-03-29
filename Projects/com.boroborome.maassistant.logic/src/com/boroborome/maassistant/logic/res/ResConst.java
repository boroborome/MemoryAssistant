/*
 * <P>Title:      任务管理  </P>
 * <P>Description:[描述功能、作用�?用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-2
 * @since         v1.0
 */
package com.boroborome.maassistant.logic.res;

/**
 * @author BoRoBoRoMe
 *
 */
public final class ResConst
{
    /**
     * 任务管理器的资源
     */
    public static final String ResKey = ResConst.class.getPackage().getName();
    public static final String FailedSql = "db.FailedSql";
    public static final String FailedFile = "db.FailedFile";
    public static final String ConnectDBFailed = "db.ConnnectDBFailed";
    
    //Failed to create statement.
    public static final String FailedToCreateState = "db.FailedToCreateState";
	public static final String FailedInExeSql = "db.FailedInExeSql";
    
}
