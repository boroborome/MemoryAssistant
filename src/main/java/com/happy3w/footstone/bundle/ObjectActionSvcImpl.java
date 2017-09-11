/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-23
 */
package com.happy3w.footstone.bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.happy3w.footstone.model.AbstractBufferIterator;
import com.happy3w.footstone.model.IBufferIterator;
import com.happy3w.footstone.ui.action.AbstractObjectAction;
import com.happy3w.footstone.ui.action.IObjectActionSvc;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-23
 */
public class ObjectActionSvcImpl implements IObjectActionSvc
{
    //Class为对象类型。列表中为这个对象上可以执行的操作
    private Map<Class, List<AbstractObjectAction>> mapAction = new HashMap<Class, List<AbstractObjectAction>>();
    
    /* (non-Javadoc)
     * @see com.boroborome.footstone.ui.action.IObjectActionSvc#getActions(java.lang.Class)
     */
    @Override
    public IBufferIterator<AbstractObjectAction> getActions(Class type)
    {
        List<AbstractObjectAction> lst = mapAction.get(type);
        return lst == null ? null : new CloneIteratorAdapter(lst.iterator());
    }

    /* (non-Javadoc)
     * @see com.boroborome.footstone.ui.action.IObjectActionSvc#regAction(com.boroborome.footstone.ui.action.AbstractObjectAction, java.lang.Class)
     */
    @Override
    public void regAction(AbstractObjectAction action, Class type)
    {
        List<AbstractObjectAction> lst = mapAction.get(type);
        if (lst == null)
        {
            lst = new ArrayList<AbstractObjectAction>();
            mapAction.put(type, lst); 
        }
        
        int index = findAction(lst, action.getID());
        if (index >= 0)
        {
            lst.remove(index);
        }
        
        for (int i = 0; i < lst.size(); i++)
        {
            if (lst.get(i).getIndex() > action.getIndex())
            {
                lst.add(i, action);
                return;
            }
        }
        lst.add(action);
    }

    private int findAction(List<AbstractObjectAction> lst, String id)
    {
        for (int i = lst.size() - 1; i >= 0; i--)
        {
            if (lst.get(i).getID().equals(id))
            {
                return i;
            }
        }
        return -1;
    }
    
    /* (non-Javadoc)
     * @see com.boroborome.footstone.ui.action.IObjectActionSvc#unregAction(java.lang.String, java.lang.Class)
     */
    @Override
    public void unregAction(String id, Class type)
    {
        List<AbstractObjectAction> lst = mapAction.get(type);
        if (lst == null)
        {
            return;
        }
        
        int index = findAction(lst, id);
        if (index >= 0)
        {
            lst.remove(index);
        }
    }

    
    private static class CloneIteratorAdapter extends AbstractBufferIterator<AbstractObjectAction>
    {
        private Iterator<AbstractObjectAction> it;
        /**
         * @param it
         */
        public CloneIteratorAdapter(Iterator<AbstractObjectAction> it)
        {
            this.it = it;
        }
        @Override
        public boolean hasNext()
        {
            return it.hasNext();
        }

        @Override
        public AbstractObjectAction next()
        {
            curItem = it.next().clone();
            return curItem;
        }

        @Override
        public void remove()
        {
            it.remove();
        }
    }
}
