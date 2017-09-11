/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:标志这个窗口可以恢复自己的信息</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-11
 */
package com.happy3w.footstone.ui;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:标志这个窗口可以恢复自己的信息</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-11
 */
public interface IWindowResumable
{
    
    /**
     * 将窗口信息保存到info中，应该在窗口关闭的时候执行
     * @param info 可以保存信息的结构
     */
    void saveInfo(final WindowInfo info);
    
    /**
     * 由信息结构恢复窗口信息
     * @param info 信息结构
     */
    void loadInfo(final WindowInfo info);
}
