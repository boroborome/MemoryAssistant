/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-19
 */
package com.happy3w.footstone.ui.action;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>能够操作某种数据类型的动作</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-19
 */
public abstract class AbstractObjectAction<T> extends AbstractAction implements Cloneable
{
    public static final String ID = "id";
    public static final String Index = "Index";
    
    private IUIDataSupport<T> uiDataSupport;
    
    /**
     * 获取index<br>
     * 这个索引决定菜单或者按钮的显示顺序
     * @return index
     */
    public int getIndex()
    {
        Integer i = (Integer) getValue(Index);
        return i == null ? Integer.MAX_VALUE : i.intValue();
    }
    
    public void setIndex(int index)
    {
        putValue(Index, Integer.valueOf(index));
    }

    /**
     * 获取uiDataSupport
     * @return uiDataSupport
     */
    public IUIDataSupport<T> getUiDataSupport()
    {
        return uiDataSupport;
    }

    /**
     * 设置uiDataSupport
     * @param uiDataSupport uiDataSupport
     */
    public void setUiDataSupport(IUIDataSupport<T> uiDataSupport)
    {
        this.uiDataSupport = uiDataSupport;
        updateStatus();
    }

    /**
     * 根据uiDataSupport提供的物品篮中内容更新界面状态。比如使能与否
     * 篮中内容改变时会调用这个
     */
    public abstract void updateStatus();

    public String getID()
    {
        return (String) getValue(ID);
    }
    public void setID(String id)
    {
        putValue(ID, id);
    }
    
    public String getName()
    {
        return (String) getValue(Action.NAME);
    }
    public void setName(String name)
    {
        putValue(Action.NAME, name);
    }
    
//    public int getMaxItem()
//    {
//        return 1;
//    }
//    
//    public int getCurItem()
//    {
//        return 0;
//    }

    /* (non-Javadoc)
     * @see javax.swing.AbstractAction#clone()
     */
    @Override
    public AbstractObjectAction<T> clone()
    {
        AbstractObjectAction<T> newObj = null;
        try
        {
            newObj = (AbstractObjectAction<T>) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            //此异常不会发生
            e.printStackTrace();
        }
        return newObj;
    }
    
    
}
