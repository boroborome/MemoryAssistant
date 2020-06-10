/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-10-18
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.ui;

/**
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:数据面板上控件监听的映射结构
 * 数据管理界面上有很多控件，这个接口用于监听这些控件的事件。
 * 当前主要作用是，无论任何的编辑界面，只要录入控件中内容改变，那么应用按钮都要使能</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-10-18
 */
public abstract class AbstractDataPnlListenerMap<ComT> {
    /**
     * 本监听所能处理控件类型
     * 这里不使用泛型JComponent限制类型参数，因为有时控件为接口
     */
    public final Class<ComT> comType;

    /**
     * 构造函数
     *
     * @param type 本监听管理器所支持的数据类型
     */
    public AbstractDataPnlListenerMap(Class<ComT> type) {
        comType = type;
    }

    /**
     * 将本监听添加到pnl上的指定控件<br>
     * 监听信息可以添加到任何的数据编辑面板上，所以这里的数据面板也没有使用泛型进行限制
     *
     * @param pnl
     * @param com
     */
    abstract void bind(final AbstractDataPanel<?> pnl, final ComT com);

    /**
     * 将本监听从pnl的控件上的去掉<br>
     * 监听信息可以添加到任何的数据编辑面板上，所以这里的数据面板也没有使用泛型进行限制
     *
     * @param pnl
     * @param com
     */
    abstract void unbind(final AbstractDataPanel<?> pnl, final ComT com);
}
