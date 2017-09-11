/*
 * <P>Title:      Common V1.0</P>
 * <P>Description:提供系统使用的公共功能</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-2
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>只读列表</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>经初始化后就不能改变的列表，用于对外提供结果，或者提供系统全局常量列表。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @param <T>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-2
 */
public class ReadOnlyList<T> extends ArrayList<T>
{
    
    /**
     * 构造函数
     * @param values 这个只读列表将要保存的值
     */
    public ReadOnlyList(final List<T> values)
    {
        super.addAll(values);
    }

    @Override
    @Deprecated
    public void add(final int index, final T element)
    {
        throw new UnsupportedOperationException("ReadOnlyList.add"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public boolean add(final T e)
    {
        throw new UnsupportedOperationException("ReadOnlyList.add"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public boolean addAll(final Collection<? extends T> c)
    {
        throw new UnsupportedOperationException("ReadOnlyList.addAll"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public boolean addAll(final int index, final Collection<? extends T> c)
    {
        throw new UnsupportedOperationException("ReadOnlyList.addAll"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public void clear()
    {
        throw new UnsupportedOperationException("ReadOnlyList.clear"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public T set(final int index, final T element)
    {
        throw new UnsupportedOperationException("ReadOnlyList.set"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public boolean removeAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException("ReadOnlyList.removeAll"); //$NON-NLS-1$
    }

    @Override
    @Deprecated
    public boolean retainAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException("ReadOnlyList.retainAll"); //$NON-NLS-1$
    }
    
    
}
