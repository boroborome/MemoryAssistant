package com.happy3w.footstone.model;

import java.util.Iterator;


/**
 * This is the base class of filter iterator<br>
 * when we want to filter something from a iterator.we can extends from this class.
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @param <T>
 * @author        boroborome
 * @version       1.0 Apr 25, 2014
 */
public abstract class AbstractFilterIterator<T> implements Iterator<T>
{
	protected Iterator<T> innerIt;
	protected T curItem;

	public AbstractFilterIterator(Iterator<T> innerIt)
	{
		this.innerIt = innerIt;
	}
	
	public abstract boolean isMatch(T value);
	
	private void readNext()
	{
		curItem = null;
		while (innerIt != null && innerIt.hasNext())
		{
			T value = innerIt.next();
			if (!isMatch(value))
			{
				continue;
			}
			curItem = value;
			break;
		}
		
	}
	
	@Override
	public boolean hasNext()
	{
		//do the first initialize
		if (curItem == null && innerIt.hasNext())
		{
			readNext();
		}
		return curItem != null;
	}

	@Override
	public T next()
	{
		//do the first initialize
		if (curItem == null && innerIt.hasNext())
		{
			readNext();
		}
		
		T result = curItem;
		readNext();
		return result;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}