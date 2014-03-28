/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-1
 */
package com.boroborome.footstone.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>基石</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-1
 */
public class LabelPropertyPanel extends JScrollPane
{
    //保存属性ID与名称和value控件之间的映射关系
    private Map<String, PropertyItem> mapProperty = new HashMap<String, PropertyItem>();
    
    private JPanel infoPnl;//显示属性信息的面板
    private int newRowIndex = 0;//新增加信息的行索引，此索引表示增加过的内容，不能确定当前有多少，如果删除记录，此数据不减少
    
    /**
     * 构造函数
     */
    public LabelPropertyPanel()
    {
        super();
        init();
    }

    private void init()
    {
        JPanel mainPnl = new JPanel();
        mainPnl.setLayout(new GridBagLayout());
        
        infoPnl = new JPanel();
        infoPnl.setLayout(new GridBagLayout());
        
        mainPnl.add(infoPnl, new GridBagConstraints(0, 0, 1, 1, 0, 0, 
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        mainPnl.add(new JLabel(), new GridBagConstraints(100, 100, 1, 1, 1, 1, 
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        this.setViewportView(mainPnl);
    }
    
    /**
     * 添加一个属性，value、description可以为空,name必填，不可重复
     * @param name
     * @param description
     * @param value
     */
    public void addProperty(String name, String description, String value)
    {
        if (name == null || name.isEmpty() || mapProperty.containsKey(name))
        {
            throw new IllegalArgumentException("名称不能为空，不能重复。当前名称为：" + name);
        }
        
        PropertyItem item = new PropertyItem();
        item.name = name;
        mapProperty.put(item.name, item);
        
        infoPnl.add(item.descLbl, new GridBagConstraints(0, newRowIndex, 1, 1, 0, 0, 
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(8, 8, 0, 0), 0, 0));
        infoPnl.add(item.valueLbl, new GridBagConstraints(1, newRowIndex, 1, 1, 0, 1, 
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(8, 8, 0, 8), 0, 0));
        newRowIndex++;
        
        setDesc(name, description);
        setValue(name, value);
    }

    public void setValue(String name, String value)
    {
        //如果名称无效则忽略
        if (name == null || name.isEmpty() || !mapProperty.containsKey(name))
        {
            return;
        }
        
        PropertyItem item = mapProperty.get(name);
        item.valueLbl.setText(value);
    }

    public void setDesc(String name, String description)
    {
        //如果名称无效则忽略
        if (name == null || name.isEmpty() || !mapProperty.containsKey(name))
        {
            return;
        }
        
        PropertyItem item = mapProperty.get(name);
        item.descLbl.setText(description == null || description.isEmpty() ? "" : description + ":");
    }

    private static class PropertyItem
    {
        private String name;
        private JLabel descLbl = new JLabel();
        private JLabel valueLbl = new JLabel();
    }
}
