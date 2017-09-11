/*
 * <P>Title:      任务管理器 V1.0</P>
 * <P>Description:任务管理系统总接口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-1
 */
package com.happy3w.footstone.svc;

import com.happy3w.footstone.exception.MessageException;

/**
 * @author BoRoBoRoMe
 *
 */
public interface ISystemInstallSvc
{
    /**
     * 卸载
     * @throws MessageException
     */
    void uninstall() throws MessageException;
    
    /**
     * 安装
     * @throws MessageException
     */
    void install() throws MessageException;
}
