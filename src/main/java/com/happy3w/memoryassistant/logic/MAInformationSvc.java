/**
 * 
 */
package com.happy3w.memoryassistant.logic;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.model.AbstractBufferIterator;
import com.happy3w.footstone.model.EventContainer;
import com.happy3w.footstone.model.IBufferIterator;
import com.happy3w.footstone.svc.IDataChangeListener;
import com.happy3w.footstone.svc.IDataCondition;
import com.happy3w.footstone.svc.IDataSvc;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAInformationCondition;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.repository.MAInformationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author boroborome
 *
 */
@Slf4j
@Service
public class MAInformationSvc implements IDataSvc<MAInformation>
{
	private EventContainer<IDataChangeListener<MAInformation>> eventContainer = new EventContainer<IDataChangeListener<MAInformation>>(IDataChangeListener.class);
	@Autowired
	private MAKeywordSvc keywordSvc;

	@Autowired
	private MAInformationRepository maInformationRepository;

	public MAInformationSvc()
	{
		super();
	}

	@Override
	public void create(Iterator<MAInformation> it) throws MessageException
	{
		it.forEachRemaining(info -> {
			maInformationRepository.save(info);
			eventContainer.fireEvents(IDataChangeListener.EVENT_CREATED, info);
		});
	}

	
	@Override
	public void modify(Iterator<MAInformation> it) throws MessageException
	{
		it.forEachRemaining(info -> {
			maInformationRepository.save(info);
			eventContainer.fireEvents(IDataChangeListener.EVENT_MODIFIED, info);
		});
	}

	@Override
	public void delete(Iterator<MAInformation> it) throws MessageException
	{
		it.forEachRemaining(info -> {
			maInformationRepository.delete(info.getId());
			eventContainer.fireEvents(IDataChangeListener.EVENT_DELETED, info);
		});
	}

	//TODO:[optimize] implement a sql function to summery all keyword of a information.
	//URL:http://db.apache.org/derby/docs/10.9/devguide/index.html
	//Title:Derby server-side programming
	@Override
	public IBufferIterator<MAInformation> query(IDataCondition<MAInformation> condition) throws MessageException
	{
		IBufferIterator<MAInformation> result = null;
		MAInformationCondition c = (MAInformationCondition) condition;
		
		List<MAInformation> queryResult = null;
        if (c.getLstKeyword() == null || c.getLstKeyword().isEmpty())
        {
        	queryResult = maInformationRepository.findAll();
        }
        else
        {
        	Iterable<MAKeyword> itNoID = keywordSvc.filterNoIdKeywords(c.getLstKeyword());
        	if (itNoID != null)
        	{
        		return null;
        	}
        	
        	List<Long> lstID = new ArrayList<Long>();
        	for (MAKeyword key : c.getLstKeyword())
        	{
        		lstID.add(Long.valueOf(key.getId()));
        	}
        	queryResult = maInformationRepository.findAllByLstKeywordFullMatch(lstID);
        }
		
		//This statement and rs will be used by MAKeywordDBIterator.
		//So they can't be closed here
        return AbstractBufferIterator.from(queryResult.iterator());
	}

	@Override
	public EventContainer<IDataChangeListener<MAInformation>> getEventContainer()
	{
		return this.eventContainer;
	}
}
