package com.boroborome.footstone.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.boroborome.footstone.model.MapItem;

/**
 * Description
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        boroborome
 * @version       1.0 May 4, 2014
 */
public class MapIterator<K,V> implements Iterator<MapItem<K,V>>
{
	private Map<K,V> map;
    private Iterator<Entry<K, V>> itEntry;
    
    public MapIterator(Map<K,V> map)
    {
        this.map = map;
        itEntry = map.entrySet().iterator();
    }
    
    @Override
    public boolean hasNext()
    {
        return itEntry.hasNext();
    }

    @Override
    public MapItem<K, V> next()
    {
        Entry<K, V> entry = itEntry.next();
        return new MapItem<K, V>(entry.getKey(), entry.getValue());
    }

    @Override
    public void remove()
    {
        //Nothing to do
    }
}
