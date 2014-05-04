/**
 * 
 */
package com.boroborome.appframe.svc;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.Action;

import com.boroborome.footstone.svc.IDataSvc;

/**
 * @author boroborome
 *
 */
public interface IAppFrame
{
	//---------Data Management Area--------------
	/**
	 * regedit a data service of data type
	 * @param type the type of data
	 * @param dataSvc a data service for type
	 */
	<T> void regDataSvc(Class<T> type, IDataSvc<T> dataSvc);
	
	/**
	 * get the data service for type
	 * @param type
	 * @return
	 */
	<T> IDataSvc<T> getDataSvc(Class<T> type);
	
	/**
	 * get all the data service.
	 * @return
	 */
	Iterator<Entry<Class, IDataSvc>> getAllDataSvc();
	
	// -----------------Data Action Management----------------------
	
	<T> void regAction(Class<T> type, Action action);
	
}
