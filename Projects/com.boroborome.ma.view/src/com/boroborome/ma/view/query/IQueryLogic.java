/**
 * 
 */
package com.boroborome.ma.view.query;

import java.util.Iterator;

/**
 * @author boroborome
 *
 */
public interface IQueryLogic<CondtionType, DataType>
{

	void clearView();
	Iterator<DataType> query(CondtionType condition) throws Exception;
	void showData(Iterator<DataType> it) throws Exception;

}
