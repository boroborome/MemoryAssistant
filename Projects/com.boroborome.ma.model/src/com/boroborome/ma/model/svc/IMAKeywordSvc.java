package com.boroborome.ma.model.svc;

import java.util.List;

import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.svc.IAutoIDDataSvc;
import com.boroborome.ma.model.MAKeyword;


/**
 * 任务管理器接口
 * @author BoRoBoRoMe
 * @param <MAKeyword>
 */
public interface IMAKeywordSvc extends IAutoIDDataSvc<MAKeyword>
{
	/**
	 * Refresh the id of keyword and save them into database<br>
	 * this funcation do two things<br>
	 * 1.if the keyword is exist,then query the database and set the id of keyword<br>
	 * 2.if the keyword is not exist,then create new id for this keyword<br>
	 * 3.save the keyword not exist into database.
	 * @param lstKeyword
	 * @throws MessageException
	 */
	public void saveAndUpdate(List<MAKeyword> lstKeyword) throws MessageException;

	/**
	 * find out the id of the keyword from database,if it is not exist,then set -1 to id.
	 * @param lstKeyword
	 * @return
	 */
	public Iterable<MAKeyword> updateID(List<MAKeyword> lstKeyword);
}
