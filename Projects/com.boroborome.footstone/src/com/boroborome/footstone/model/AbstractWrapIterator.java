package com.boroborome.footstone.model;

import java.util.Iterator;

/**
 * Description
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        boroborome
 * @version       1.0 Apr 25, 2014
 */
public abstract class AbstractWrapIterator<T> implements Iterator<T>
{
	protected Iterator<T> innerIt;

	public AbstractWrapIterator(Iterator<T> innerIt)
	{
		this.innerIt = innerIt;
	}
	
	@Override
	public boolean hasNext()
	{
		return innerIt != null && innerIt.hasNext();
	}
	
	@Override
	public void remove()
	{
		innerIt.remove();
	}
	
	@Override
	public T next()
	{
		T result = innerIt.next();
		beforeNext(result);
		return result;
	}
	
	public abstract void beforeNext(T value);
	
}
