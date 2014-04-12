/*
 * <P>Title:      任务事件管理器 V1.0</P>
 * <P>Description:任务事件管理器接口</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-6-26
 */
package com.boroborme.ma.model.svc;

import java.util.List;

import com.boroborme.ma.model.MAKeyword;
import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.svc.IAutoIDDataSvc;


/**
 * 任务管理器接口
 * @author BoRoBoRoMe
 * @param <MAKeyword>
 */
public interface IMAKeywordSvc extends IAutoIDDataSvc<MAKeyword>
{
	public void saveAndUpdate(List<MAKeyword> lstKeyword) throws MessageException;

	public void updateID(List<MAKeyword> lstKeyword);
}
