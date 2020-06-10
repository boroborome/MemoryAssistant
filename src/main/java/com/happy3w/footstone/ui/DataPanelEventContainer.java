/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据编辑面板事件管理器，也是各种控件的监听器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-9
 */
package com.happy3w.footstone.ui;

import com.happy3w.footstone.model.EventContainer;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据编辑面板事件管理器，也是各种控件的监听器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-3-9
 */
public class DataPanelEventContainer extends EventContainer<IDataPanelListener> implements DocumentListener,
        ItemListener, ChangeListener, IDataPanelListener, ListSelectionListener, TableModelListener {
    /*
     * 这个事件容器所属的数据面板
     */
    private AbstractDataPanel<?> source;

    /*
     * 是否改变的标记
     */
    private boolean modified = false;

    /**
     * 构造函数
     *
     * @param source 事件容器的所有者
     */
    public DataPanelEventContainer(final AbstractDataPanel<?> source) {
        super(IDataPanelListener.class);
        setSource(source);
    }

    /**
     * 获取修改标记
     *
     * @return 修改标记
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * 设置修改标记
     *
     * @param modified 修改标记
     */
    public void setModified(final boolean modified) {
        this.modified = modified;
    }

    /**
     * 获取事件容器所属的数据面板
     *
     * @return 事件容器所属的数据面板
     */
    public AbstractDataPanel<?> getSource() {
        return source;
    }

    /**
     * 设置事件容器所属的数据面板
     *
     * @param source 事件容器所属的数据面板
     */
    public void setSource(final AbstractDataPanel<?> source) {
        this.source = source;
    }

    /**
     * 通知所有监听，面板内容改变了
     */
    public void notifyChange() {
        modified = true;
        fireEvents(DATA_CHANGED, source);
    }

    /**
     * 文本内容改变
     *
     * @param e 事件参数
     */
    @Override
    public void changedUpdate(final DocumentEvent e) {
        notifyChange();
    }

    /**
     * 文本内容改变
     *
     * @param e 事件参数
     */
    @Override
    public void insertUpdate(final DocumentEvent e) {
        notifyChange();
    }

    /**
     * 文本内容改变
     *
     * @param e 事件参数
     */
    @Override
    public void removeUpdate(final DocumentEvent e) {
        notifyChange();
    }

    /**
     * 下拉框内容改变
     *
     * @param e 事件参数
     */
    @Override
    public void itemStateChanged(final ItemEvent e) {
        notifyChange();
    }

    /**
     * 钩选框改变事件
     *
     * @param e 事件参数
     */
    @Override
    public void stateChanged(final ChangeEvent e) {
        notifyChange();
    }

    /* (non-Javadoc)
     * @see com.boroborome.extui.IDataPanelListener#dataChanged(com.boroborome.extui.IDataPanel)
     */
    @Override
    public void dataChanged(final AbstractDataPanel dataPanel) {
        notifyChange();
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            notifyChange();
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        notifyChange();
    }

}
