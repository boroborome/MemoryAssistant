/**
 * 
 */
package com.boroborome.ma.view.query;

import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * @author boroborome
 *
 */
public class QueryAssistant<CondtionType, DataType>
{
	private static Logger log = Logger.getLogger(QueryAssistant.class);
	
	private CondtionType condition;
	private boolean conditionChanged = false;
	private IQueryLogic<CondtionType, DataType> logic;
	
	//TODO [optimize] can implements with a thread pool
	private QueryThread queryThread;
	private String threadName;
	
	public QueryAssistant(String threadName, IQueryLogic<CondtionType, DataType> logic)
	{
		if (logic == null)
		{
			throw new IllegalArgumentException("Argument logic can't be null.");
		}
		this.logic = logic;
		this.threadName = threadName;
	}
	
	
	/**
	 * @return the prefix
	 */
	public CondtionType getCondtion()
	{
		return condition;
	}


	/**
	 * @param prefix the prefix to set
	 */
	public void setCondtion(CondtionType condition)
	{
		if (this.condition == condition)
		{
			return;
		}
		if (condition == null || !condition.equals(this.condition))
		{
			this.condition = condition;
			conditionChanged = true;
			if (queryThread == null)
			{
				queryThread = new QueryThread();
				queryThread.setName(threadName);
				queryThread.start();
			}
		}
	}


	private class QueryThread  extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				do
				{
					//TODO [optimize] we should better to add a delay before start
					conditionChanged = false;
					logic.clearView();
					if (conditionChanged)
					{
						continue;
					}
					
					Iterator<DataType> it = logic.query(condition);
					if (conditionChanged)
					{
						continue;
					}
					
					logic.showData(it);
					if (conditionChanged)
					{
						continue;
					}
				}
				while (false);
				
			}
			catch (Exception e)
			{
				log.error("Failed in querying keyword.", e);
			}
			finally
			{
				queryThread = null;
			}
		}
	}
}
