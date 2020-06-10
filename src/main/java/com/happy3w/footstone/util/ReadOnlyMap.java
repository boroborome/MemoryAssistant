/*
 * <P>Title:      基石模块</P>
 * <P>Description:只读的映射表</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-10
 */
package com.happy3w.footstone.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * <DT><B>Title:</B></DT>
 * <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 * <DD>只读的映射表</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2011-7-10
 */
public class ReadOnlyMap<K, V> implements Map<K, V> {
    private Map<K, V> innerMap;

    /**
     * 构造函数
     *
     * @param map
     */
    public ReadOnlyMap(Map<K, V> map) {
        innerMap = map;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return innerMap.entrySet();
    }

    @Override
    public V get(Object key) {
        return innerMap.get(key);
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return innerMap.keySet();
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public Collection<V> values() {
        return innerMap.values();
    }

}
