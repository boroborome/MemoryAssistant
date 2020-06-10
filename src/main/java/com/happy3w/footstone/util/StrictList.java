/*
 * <P>Title:      Common V1.0</P>
 * <P>Description:提供系统使用的公共功能</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2009-6-10
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.util;

import com.happy3w.footstone.exception.RuntimeMessageException;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * <P>Title:      带有上下级关系的准确列表列表</P>
 * <P>Description:这是一个列表，具有下面特征：<br>
 *  <ol>
 *  <li>列表中内容有严格限制，类型确定。</li>
 *  <li>列表中内容会具有上一级的引用，通过列表中的单条信息可以查找到他所属的列表。</li>
 *  </ol></P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2009-6-10
 */
public class StrictList<OwnerType, E extends AbstractChildItem<OwnerType>> extends AbstractList<E> {
    private List<E> lst = new ArrayList<E>();

    private OwnerType owner;
    private Class<E> childType;

    public StrictList(OwnerType owner, Class<E> childType) {
        this.owner = owner;
        this.childType = childType;
    }

    public E createChild() {
        E child = null;
        try {
            child = childType.newInstance();
            child.setOwner(owner);
        } catch (Exception e) {
            throw new RuntimeMessageException(CommonRes.ResFileName,
                    CommonRes.StrictList_CreateChildFailed, e);
        }
        return child;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#add(java.lang.Object)
     */
    @Override
    public void add(int index, E e) {
        checkData(e);

        if (!contains(e)) {
            lst.add(index, e);
        }
    }

    protected void checkData(E e) {
        //if (e.getOwner() != owner)
        //{
        // throw new RuntimeMessageException(CommonRes.ResFileName,
        // CommonRes.StrictList_AddFailed);
        // }
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#set(int, java.lang.Object)
     */
    @Override
    public E set(int index, E element) {
        checkData(element);
        return lst.set(index, element);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#remove(int)
     */
    @Override
    public E remove(int index) {
        return lst.remove(index);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public E get(int index) {
        return lst.get(index);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        return lst.size();
    }

}
