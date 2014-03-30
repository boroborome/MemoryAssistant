/**
 * 
 */
package com.boroborome.maassistant.logic.impl;

import com.boroborme.maassistant.model.MAInformation;
import com.boroborme.maassistant.model.svc.IMAInformationSvc;
import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.model.EventContainer;
import com.boroborome.footstone.model.IBufferIterator;
import com.boroborome.footstone.svc.IDataChangeListener;
import com.boroborome.footstone.svc.IDataCondition;

/**
 * @author boroborome
 *
 */
public class MAInformationSvcImpl implements IMAInformationSvc
{

	@Override
	public void create(IBufferIterator<MAInformation> it) throws MessageException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(IBufferIterator<MAInformation> it) throws MessageException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(IBufferIterator<MAInformation> it) throws MessageException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBufferIterator<MAInformation> query(IDataCondition<MAInformation> condition) throws MessageException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventContainer<IDataChangeListener<MAInformation>> getEventContainer()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
