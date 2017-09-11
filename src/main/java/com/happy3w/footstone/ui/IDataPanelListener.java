/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据面板中内容改变事件</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-9
 */
package com.happy3w.footstone.ui;

import java.util.EventListener;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:数据面板中内容改变事件</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-9
 */
public interface IDataPanelListener extends EventListener
{
    public static final String DATA_CHANGED = "dataChanged"; //$NON-NLS-1$

    /**
     * 内容改变时会调用的入口函数<br>
     * 监听接口不区分面板类型，所以不使用泛型
     * @param dataPanel 改变内容的面板
     */
    @SuppressWarnings("unchecked")
	void dataChanged(final AbstractDataPanel<?> dataPanel);
}
