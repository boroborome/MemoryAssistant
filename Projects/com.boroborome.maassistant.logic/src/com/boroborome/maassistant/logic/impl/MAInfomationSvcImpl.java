/**
 * 
 */
package com.boroborome.maassistant.logic.impl;

import com.boroborme.maassistant.model.MAInfomation;
import com.boroborme.maassistant.model.svc.IMAInfomationSvc;
import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.model.EventContainer;
import com.boroborome.footstone.model.IBufferIterator;
import com.boroborome.footstone.svc.IDataChangeListener;
import com.boroborome.footstone.svc.IDataCondition;

/**
 * @author boroborome
 *
 */
public class MAInfomationSvcImpl implements IMAInfomationSvc
{

	@Override
	public void create(IBufferIterator<MAInfomation> it) throws MessageException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(IBufferIterator<MAInfomation> it) throws MessageException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(IBufferIterator<MAInfomation> it) throws MessageException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBufferIterator<MAInfomation> query(IDataCondition<MAInfomation> condition) throws MessageException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventContainer<IDataChangeListener<MAInfomation>> getEventContainer()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
