/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:具有扩展功能的标签</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-12
 */
package com.happy3w.footstone.ui;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:具有扩展功能的标签
 * 扩展功能标签主要提供两项功能
 * 1、让辅助工具自动更新界面
 * 2、对外提供字符资源
 * 比如使用extLabel.getValue来获取本标签标识的后面录入控件内容说明</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-3-12
 */
public class ExtLabel extends JLabel {
    /*
     * 格式，具有形如{0}占位符的格式字符串
     */
    private String format;

    /*
     * 占位符对应的键值列表
     */
    private String[] keys;

    /*
     * 结果值列表
     */
    private String[] values;

    /**
     * 构造函数
     */
    public ExtLabel() {
        super();
    }

    /**
     * 获取格式，具有形如{0}占位符的格式字符串
     *
     * @return 格式，具有形如{0}占位符的格式字符串
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置，具有形如{0}占位符的格式字符串
     *
     * @param format ，具有形如{0}占位符的格式字符串
     */
    public void setFormat(final String format) {
        this.format = format;
    }

    /**
     * 获取第一个键值
     *
     * @return 第一个键值
     */
    public String getKey() {
        String key = null;
        if (keys != null && keys.length > 0) {
            key = keys[0];
        }
        return key;
    }

    /**
     * 获取占位符对应的键值列表
     *
     * @return 占位符对应的键值列表
     */
    public String[] getKeys() {
        return keys;
    }

    /**
     * 设置占位符对应的键值列表
     *
     * @param keys 占位符对应的键值列表
     */
    public void setKeys(final String[] keys) {
        this.keys = keys;
    }

    /**
     * 获取第一个键值对应的值
     *
     * @return 第一个键值对应的值
     */
    public String getValue() {
        String v = null;
        if (values != null && values.length > 0) {
            v = values[0];
        }
        return v;
    }

    /**
     * 获取结果值列表
     *
     * @return 结果值列表
     */
    public String[] getValues() {
        return values;
    }

    /**
     * 设置结果值列表
     *
     * @param values 结果值列表
     */
    public void setValues(final String[] values) {
        this.values = values;
        super.setText(MessageFormat.format(format, (Object[]) values));
    }

    /**
     * 设置标签的字符串，如果字符串中含有{}括起来的字符串，则会根据字符串设置格式和对应键值列表
     */
    @Override
    public void setText(final String text) {
        Pattern p = Pattern.compile("\\{(\\w[\\d\\w]*)\\}"); //$NON-NLS-1$
        Matcher mather = p.matcher(text);
        List<String> lstKey = new ArrayList<String>();
        while (mather.find()) {
            //获得找到的字符串,如果没有出现过,则保存
            String key = mather.group();
            key = key.substring(1, key.length() - 1); //去掉首尾的大括号
            if (!lstKey.contains(key)) {
                lstKey.add(key);
            }
        }
        format = text;
        int len = lstKey.size();
        keys = new String[len];
        values = new String[len];
        for (int i = 0; i < len; i++) {
            String key = lstKey.get(i);
            format = format.replaceAll("\\{" + key + "\\}",  //$NON-NLS-1$ //$NON-NLS-2$
                    "{" + i + "}"); //$NON-NLS-1$ //$NON-NLS-2$
            keys[i] = key;
        }

        super.setText(text);
    }

}
