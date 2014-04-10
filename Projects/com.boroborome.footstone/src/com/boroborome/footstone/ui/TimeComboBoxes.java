/*
 * <P>Title: 基石模块</P> <P>Description:[描述功能、作用、用法和注意事项]</P> <P>Copyright: Copyright (c) 2008</P> <P>Company: BoRoBoRoMe Co. Ltd.</P>
 * 
 * @author BoRoBoRoMe
 * 
 * @version 1.0 2011-10-2
 */
package com.boroborome.footstone.ui;

/**
 * <DT><B>Title:</B></DT> <DD>基石</DD> <DT><B>Description:</B></DT> <DD>[描述功能、作用、用法和注意事项]</DD>
 * <P>
 * Copyright: Copyright (c) 2008
 * </P>
 * <P>
 * Company: BoRoBoRoMe Co. Ltd.
 * </P>
 * 
 * @author BoRoBoRoMe
 * @version 1.0 2011-10-2
 */
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeComboBoxes
{

    private JPanel jContentPane = null;
    private JComboBox yearComboBox = null;
    private JComboBox monthComboBox = null;
    private JComboBox dayComboBox = null;
    private JLabel jLabel = null;
    private JLabel jLabel1 = null;
    private JLabel jLabel2 = null;

    // 定义时间框的开始的位置
    private int startX, startY;
    // 定义时间框的长度和宽度
    private int width, height;
    // 定义各段占的长度
    private float roomForYearField = 5;
    private float roomForMonthField = 3;
    private float roomForDayField = 3;
    private float roomForLetter = 2;
    private float roomForSeparator = 1; // 分隔作用

    public TimeComboBoxes(int startX, int startY, int width, int height)
    {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    float getwholeSpaceLength()
    {
        float result;
        result = roomForYearField + roomForLetter + roomForSeparator;
        result += roomForMonthField + roomForLetter + roomForSeparator;
        result += roomForDayField + roomForLetter;
        return result;
    }

    float getYearSpacePercent()
    {
        return roomForYearField / getwholeSpaceLength();
    }

    float getMonthSpacePercent()
    {
        return roomForMonthField / getwholeSpaceLength();
    }

    float getDaySpacePercent()
    {
        return roomForDayField / getwholeSpaceLength();
    }

    float getLetterSpacePercent()
    {
        return roomForLetter / getwholeSpaceLength();
    }

    float getSeparatorSpacePercent()
    {
        return roomForSeparator / getwholeSpaceLength();
    }

    Rectangle getYearRectangle()
    {
        int width, height;
        float temp = this.width * getYearSpacePercent();
        width = (int) temp;
        height = this.height;
        Rectangle yearRec = new Rectangle(startX, startY, width, height);
        return yearRec;
    }

    Rectangle getMonthRectangle()
    {
        int x, width, height;

        // 计算x
        float temp = getYearSpacePercent() + getLetterSpacePercent() + getSeparatorSpacePercent();
        temp = temp * this.width;
        temp += this.startX;
        x = (int) temp;
        // 计算width
        temp = this.width * getMonthSpacePercent();
        width = (int) temp;

        height = this.height;
        Rectangle monthRec = new Rectangle(x, startY, width, height);
        return monthRec;
    }

    Rectangle getDayRectangle()
    {
        int x, width, height;
        // 计算x
        float temp = 1.0f - (getDaySpacePercent() + getLetterSpacePercent());
        temp = temp * this.width;
        temp += this.startX;
        x = (int) temp;
        // 计算width
        temp = this.width * getDaySpacePercent();
        width = (int) temp;

        height = this.height;
        Rectangle dayRec = new Rectangle(x, startY, width, height);
        return dayRec;
    }

    Rectangle getYearLabelRectangle()
    {
        int x, width, height;
        // 计算x
        float temp = getYearSpacePercent();
        temp = temp * this.width;
        temp += this.startX;
        x = (int) temp;
        // 计算width
        temp = this.width * getLetterSpacePercent();
        width = (int) temp;

        height = this.height;
        Rectangle yearLabelRec = new Rectangle(x, startY, width, height);
        return yearLabelRec;
    }

    Rectangle getMonthLabelRectangle()
    {
        int x, width, height;
        // 计算x
        float temp = getYearSpacePercent() + getLetterSpacePercent() + getSeparatorSpacePercent();
        temp += getMonthSpacePercent();
        temp = temp * this.width;
        temp += this.startX;
        x = (int) temp;
        // 计算width
        temp = this.width * getLetterSpacePercent();
        width = (int) temp;

        height = this.height;
        Rectangle yearLabelRec = new Rectangle(x, startY, width, height);
        return yearLabelRec;
    }

    Rectangle getDayLabelRectangle()
    {
        int x, width, height;
        // 计算x
        float temp = 1.0f - getLetterSpacePercent();
        temp = temp * this.width;
        temp += this.startX;
        x = (int) temp;
        // 计算width
        temp = this.width * getLetterSpacePercent();
        width = (int) temp;

        height = this.height;
        Rectangle yearLabelRec = new Rectangle(x, startY, width, height);
        return yearLabelRec;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    public JPanel getBoxes()
    {
        if (jContentPane == null)
        {
            jLabel2 = new JLabel();
            jLabel2.setBounds(getYearLabelRectangle());
            jLabel2.setText("日");
            jLabel1 = new JLabel();
            jLabel1.setBounds(getMonthLabelRectangle());
            jLabel1.setText("月");
            jLabel = new JLabel();
            jLabel.setBounds(getDayLabelRectangle());
            jLabel.setText("年");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getYearComboBox(), null);
            jContentPane.add(getMonthComboBox(), null);
            jContentPane.add(getDayComboBox(), null);
            jContentPane.add(jLabel, null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(jLabel2, null);
        }
        return jContentPane;
    }

    /**
     * This method initializes yearComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getYearComboBox()
    {
        if (yearComboBox == null)
        {
            yearComboBox = new JComboBox(Library.getComboBoxYearFiled());
            yearComboBox.setBounds(getYearRectangle());
            yearComboBox.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    dayComboBox.removeAllItems();
                    Library.flushComboBox(yearComboBox, monthComboBox, dayComboBox, new java.awt.Rectangle(421, 375,
                            45, 31));
                }
            });
        }
        return yearComboBox;
    }

    public void setYearComboBox()
    {

    }

    /**
     * This method initializes monthComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getMonthComboBox()
    {
        if (monthComboBox == null)
        {
            monthComboBox = new JComboBox(Library.getComboBoxMonthFiled());
            monthComboBox.setBounds(getMonthRectangle());
            monthComboBox.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    dayComboBox.removeAllItems();
                    Library.flushComboBox(yearComboBox, monthComboBox, dayComboBox, new java.awt.Rectangle(421, 375,
                            45, 31));
                }
            });
        }
        return monthComboBox;
    }

    /**
     * This method initializes dayComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getDayComboBox()
    {
        if (dayComboBox == null)
        {
            String year = yearComboBox.getSelectedItem().toString();
            String month = monthComboBox.getSelectedItem().toString();
            dayComboBox = new JComboBox(Library.getComboBoxDayFiled(year, month));
            dayComboBox.setBounds(getDayRectangle());
        }
        return dayComboBox;
    }

    public String getTimeString()
    {
        String result;
        result = yearComboBox.getSelectedItem().toString();
        result += "-";
        result += monthComboBox.getSelectedItem().toString();
        result += "-";
        result += dayComboBox.getSelectedItem().toString();
        return result;
    }
}

class Constants
{

    public static final int END_YEAR = 3000;
    public static final int BEGIN_YEAR = 1980;

}

class Library
{

    /**
     * 用于填充jComnboBox的year框
     */
    public static String[] getComboBoxYearFiled()
    {
        String[] result = new String[Constants.END_YEAR - Constants.BEGIN_YEAR];
        String temp;
        for (int i = Constants.BEGIN_YEAR, j = 0; i < Constants.END_YEAR; i++, j++)
        {
            temp = "" + i + "";
            result[j] = new String(temp);
        }
        return result;
    }

    /**
     * 用于填充jComnboBox的month框
     */
    public static String[] getComboBoxMonthFiled()
    {
        String[] result = new String[12];
        String temp;
        for (int i = 0, j = 1; i < 12; i++, j++)
        {
            temp = "" + j + "";
            result[i] = new String(temp);
        }
        return result;
    }

    /**
     * 用于填充jComnboBox的day框,用于用户选择月份后对日的下拉式框做修改
     */
    public static String[] getComboBoxDayFiled(String yearField, String monthField)
    {
        int year, month, days;
        year = (new Integer(yearField)).intValue();
        month = (new Integer(monthField)).intValue();
        days = new Date(year, month, 0).getDay();// getDays(year,month);
        String[] result = new String[days];
        String temp;
        for (int i = 0, j = 1; i < days; i++, j++)
        {
            temp = "" + j + "";
            result[i] = new String(temp);
        }
        return result;
    }

    public static void flushComboBox(JComboBox yearComboBox, JComboBox monthComboBox, JComboBox dayComboBox,
            java.awt.Rectangle rec)
    {
        String year = yearComboBox.getSelectedItem().toString();
        String month = monthComboBox.getSelectedItem().toString();
        dayComboBox.removeAllItems();
        for (int i = 0; i < Library.getComboBoxDayFiled(year, month).length; i++)
            dayComboBox.addItem(Library.getComboBoxDayFiled(year, month)[i]);
    }
}
