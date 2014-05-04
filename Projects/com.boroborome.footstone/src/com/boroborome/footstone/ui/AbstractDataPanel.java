/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:IDataPanel的助理</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-9
 */
package com.boroborome.footstone.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.ItemSelectable;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.exception.InputException;
import com.boroborome.footstone.model.EventContainer;
import com.boroborome.footstone.xml.IXmlObject;
import com.boroborome.footstone.xml.XmlHelper;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:用于辅助处理编辑数据界面的基类
 * 问题：系统中总有很多数据需要提供一个界面编辑，但是每个界面总是有很多相似的逻辑，重复编写浪费劳动力而且还引入很多问题，
 *      这个基类负责将各种共同的逻辑抽象出来，并提供各种辅助功能
 * 功能：
 *      1、为后续开发的各种数据编辑界面规范统一的显示数据、读取数据的接口；
 *      2、实现了部分简单接口，如是否编辑模式(EditMode)、设置/读取编辑的数据(setData/getData)、获取原有值方法；
 *      3、提供了事件管理接口。需要监听界面数据改变动作的部分，可以实现接口IDataPanelListener,添加到事件管理器(eventContainer)中，实现对界面数据改变的监听。
 *      4、类似应用功能的保存界面更改到原始数据(oldValue)的功能。
 *      5、自动监听添加到界面的相关控件内容改变事件，如果需要去掉，需要在添加到界面后删除相关监听动作。
 *      6、提供定义"如何监听各种控件"的接口。方法是在全局列表lstTypeListener中添加ComItem信息。
 *      </P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-9
 * @modify        2008-03-24 BoRoBoRoMe 增加编辑模式接口
 */
public abstract class AbstractDataPanel<T> extends JPanel
{
    /*
     * AbstractDataPanel对界面控件监听逻辑列表
     * ComItem列表
     */
    private static List<AbstractDataPnlListenerMap<?>> lstTypeListener;
    
    /**
     * 获取全局唯一类型处理注册器
     * @return 全局唯一类型处理注册器
     */
    public static List<AbstractDataPnlListenerMap<?>> getLstTypeListener()
    {
        if (lstTypeListener == null)
        {
            lstTypeListener = new ArrayList<AbstractDataPnlListenerMap<?>>();
            // 各种文本录入框
            lstTypeListener.add(new AbstractDataPnlListenerMap<JTextComponent>(JTextComponent.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, JTextComponent com)
                {
                    com.getDocument().addDocumentListener(pnl.eventContainer);
                }
                @Override
                public void unbind(AbstractDataPanel<?> pnl, JTextComponent com)
                {
                    com.getDocument().removeDocumentListener(pnl.eventContainer);
                }
            });

            // 下拉框和勾选框（按钮也包含在内，但没有意义）
            lstTypeListener.add(new AbstractDataPnlListenerMap<ItemSelectable>(ItemSelectable.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, ItemSelectable com)
                {
                    com.addItemListener(pnl.eventContainer);
                }
                @Override
                public void unbind(AbstractDataPanel<?> pnl, ItemSelectable com)
                {
                    com.removeItemListener(pnl.eventContainer);
                }
            });

            // 表格的处理
            lstTypeListener.add(new AbstractDataPnlListenerMap<JTable>(JTable.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, JTable tbl)
                {
                    tbl.addPropertyChangeListener("model", pnl.tablePropertyListener); //$NON-NLS-1$
                    tbl.getModel().addTableModelListener(pnl.eventContainer);
                }
                @Override
                public void unbind(AbstractDataPanel<?> pnl, JTable tbl)
                {
                    tbl.removePropertyChangeListener("model", pnl.tablePropertyListener); //$NON-NLS-1$
                    tbl.getModel().removeTableModelListener(pnl.eventContainer);
                }
            });
            // 数据面板的处理方式
            lstTypeListener.add(new AbstractDataPnlListenerMap<AbstractDataPanel>(AbstractDataPanel.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, AbstractDataPanel com)
                {
                    com.getEventContainer().addEventListener(pnl.eventContainer);
                }

                @Override
                public void unbind(AbstractDataPanel<?> pnl, AbstractDataPanel com)
                {
                    com.getEventContainer().removeEventListener(pnl.eventContainer);
                }
            });

            // ExtTable类型表格
            lstTypeListener.add(new AbstractDataPnlListenerMap<ExtTable>(ExtTable.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, ExtTable com)
                {
                    pnl.dataPanelContainerListener.add(com.getInnerTable());
                }
                @Override
                public void unbind(AbstractDataPanel<?> pnl, ExtTable com)
                {
                    pnl.dataPanelContainerListener.remove(com.getInnerTable());
                }
            });

            // JScrollPane类型表格
            lstTypeListener.add(new AbstractDataPnlListenerMap<JScrollPane>(JScrollPane.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, JScrollPane com)
                {
                    pnl.dataPanelContainerListener.add(com.getViewport().getView());
                }
                @Override
                public void unbind(AbstractDataPanel<?> pnl, JScrollPane com)
                {
                    pnl.dataPanelContainerListener.remove(com.getViewport().getView());
                }
            });

            // 遍历容器
            lstTypeListener.add(new AbstractDataPnlListenerMap<Container>(Container.class)
            {
                @Override
                public void bind(AbstractDataPanel<?> pnl, Container com)
                {
                    for (int i = 0, l = com.getComponentCount(); i < l; i++)
                    {
                        pnl.dataPanelContainerListener.add(com.getComponent(i));
                    }
                }
                @Override
                public void unbind(AbstractDataPanel<?> pnl, Container com)
                {
                    for (int i = 0, l = com.getComponentCount(); i < l; i++)
                    {
                        pnl.dataPanelContainerListener.remove(com.getComponent(i));
                    }
                }
            });
        }
        return lstTypeListener;
    }
    /**
     * DataPanel事件管理器
     */
    protected DataPanelEventContainer eventContainer = new DataPanelEventContainer(this);
    
    protected DataPanelContainerListener dataPanelContainerListener = new DataPanelContainerListener();
   
    //表格属性监听器（只能处理模型改变事件，处理其他事件会出问题）
    protected TablePropertyChangeListener tablePropertyListener = new TablePropertyChangeListener();
    
//    /**
//     * 数据中心
//     */
//    protected IDataKit dataKit;

    /**
     * 原始值
     */
    protected T oldValue;
    
    /**
     * 当前是否编辑模式，true表示是编辑模式，false表示为新建模式
     */
    protected boolean editMode;

    /**
     * 构造函数
     */
    public AbstractDataPanel()
    {
        super();
        this.addContainerListener(dataPanelContainerListener);
//        dataKit = DataKitBuilder.getDataKit();
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.ui.IDataPanel#setEditMode(boolean)
     */
    public void setEditMode(final boolean model)
    {
        editMode = model;
    }
    
    /* (non-Javadoc)
     * @see com.boroborome.common.ui.IDataPanel#isEditMode()
     */
    public boolean isEditMode()
    {
        return editMode;
    }

    
    /**
     * 将需要编辑的信息显示在界面
     * @param value 需要编辑的信息
     */
    public abstract void showData(final T value);
    
    /**
     * 将界面信息保存到结构中
     * @param value 用于保存界面信息的结构
     */
    public abstract void collectData(final T value);
    
    
    /**
     * 检测用户录入是否有效
     * 如果检测到非法录入，则抛出异常，不允许显示对话框
     * @throws InputException 用户录入异常
     */
    public abstract void verifyInput() throws InputException;

    
    /* (non-Javadoc)
     * @see com.boroborome.extui.IDataPanel#showData(java.lang.Object)
     */
    public void setData(T value)
    {
        oldValue = value;
        
        //显示信息之前先设置事件监听模式为忽略，避免不必要的监听事件触发
        eventContainer.setMode(EventContainer.Mode.Ignore);
        showData(value);
        eventContainer.setMode(EventContainer.Mode.Normal);
        eventContainer.setModified(false);
    }

    /**
     * 谨慎使用，会创建新对象，并复制原有对象
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @see com.boroborome.common.ui.IDataPanel#getValue()
     */
    @SuppressWarnings("unchecked")
    public T getData()
    {
        T v = null;
        
        //如果实现了Cloneable接口则Clone这个对象
        if (oldValue instanceof Cloneable)
        {
            Method method;
            try
            {
                method = oldValue.getClass().getMethod("clone", new Class[0]); //$NON-NLS-1$
                if (method != null )
                {
                    v = (T) method.invoke(oldValue, new Object[0]);
                }
            }
            catch (Exception e)
            {
                //将这个异常变为一个不同异常，如果需要制作日志
                //这里不能记录显示界面或者其他，只能记录日志，所以就重新创建的异常
                FootstoneSvcAccess.getExceptionGrave().bury(new Exception(e));
            }
        }
        else if (oldValue instanceof IXmlObject)
        {
            IXmlObject xmlObj = (IXmlObject) oldValue;
            v = (T) XmlHelper.clone(xmlObj);
        }
        else if (oldValue != null)
        {
            //如果没有Cloneable接口,则使用序列化方法构建
            ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(outBuf);
            encoder.writeObject(oldValue);
            encoder.close();
            
            ByteArrayInputStream inBuf = new ByteArrayInputStream(outBuf.toByteArray());
            XMLDecoder decoder = new XMLDecoder(inBuf);
            v = (T) decoder.readObject();
            decoder.close();
        }
        
        if (v != null)
        {
            collectData(v);
        }
        
        return v;
    }    

    /**
     * 将界面上的数据重新保存到原有数据模型中
     * @see com.boroborome.common.ui.IDataPanel#doApply()
     */
    public void saveData()
    {
        collectData(oldValue);
        eventContainer.setModified(false);
    }

    /* (non-Javadoc)
     * @see com.boroborome.common.ui.IDataPanel#getOldValue()
     */
    public Object getOldValue()
    {
        return oldValue;
    }
    
    /**
     * 获取eventContainer
     * @return eventContainer
     */
    public DataPanelEventContainer getEventContainer()
    {
        return eventContainer;
    }

    /**
     * 出发数据改变事件
     */
    public void fireDataChange()
    {
        eventContainer.fireEvents("dataChanged", this); //$NON-NLS-1$
    }

    /**
     * <P>Title:      [产品名称和版本号]</P>
     * <P>Description:增加删除控件时检测是否需要对控件进行监听</P>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2008-10-18
     * @see           [相关类，可选，也可多条]
     * @since         [产品/模块版本，表示从哪个版本开始有]
     * @!deprocated   [表示不建议使用]
     * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
     */
    private class DataPanelContainerListener implements ContainerListener
    {
        public void add(Component c)
        {
            for (AbstractDataPnlListenerMap item : getLstTypeListener())
            {
                if (item.comType.isInstance(c))
                {
                    item.bind(AbstractDataPanel.this, c);
                }
            }
        }
        
        public void remove(Component c)
        {
            for (AbstractDataPnlListenerMap item : lstTypeListener)
            {
                if (item.comType.isInstance(c))
                {
                    item.unbind(AbstractDataPanel.this, c);
                }
            }
        }
        @Override
        public void componentAdded(ContainerEvent e)
        {
            add(e.getChild());
        }
        @Override
        public void componentRemoved(ContainerEvent e)
        {
            remove(e.getChild());
        }
        
    }
    
    private class TablePropertyChangeListener implements PropertyChangeListener
    {
    	@Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            ((TableModel)evt.getOldValue()).removeTableModelListener(AbstractDataPanel.this.eventContainer);
            ((TableModel)evt.getNewValue()).addTableModelListener(AbstractDataPanel.this.eventContainer);
        }
    }
}
