/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:处理界面显示的工具</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-13
 */
package com.boroborome.footstone.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import com.boroborome.footstone.resource.ISpaceName;
import com.boroborome.footstone.resource.SpaceNameResourceBundle;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:处理界面显示的工具</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-13
 */
public class DisplayUtil
{
    //用于检测那些是需要更新的资源，只有一个点开头的字符串才是要替换的内容
//    private static Pattern patternReplaceText;
//    static
//    {
//        patternReplaceText = Pattern.compile("^\\.\\w.*"); //$NON-NLS-1$
//    }
    
    /*
     * 本垒全部为静态方法，不需要构造
     */
    private DisplayUtil()
    {
    	//Nothing to initalize
    }
    
    /**
     * 控件在界面居中
     * @param com 需要居中的控件
     */
    public static void center(final Component com)
    {
        center(com, null);
    }
    
    /**
     * 控件在界面居中
     * @param com 需要居中的控件
     * @param referCom 参考控件,com相对于这个控件居中
     */
    public static void center(final Component com , final Component referCom)
    {
        Point location = null;
        Dimension screenSize = null;
        if (referCom == null)
        {
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
            location = new Point(0,0);
        }
        else
        {
            screenSize = referCom.getSize();
            location = referCom.getLocation();
        }
        
        Dimension   frameSize   =   com.getSize();   
        if (frameSize.height > screenSize.height)   
        {   
            frameSize.height = screenSize.height;   
        }   
        if (frameSize.width > screenSize.width)   
        {   
            frameSize.width = screenSize.width;   
        }

        com.setLocation((screenSize.width - frameSize.width)/2 + location.x,   
                (screenSize.height - frameSize.height)/2 + location.y);   
    }
    
    /**
     * 根据提供的Action列表创建下拉菜单
     * @param actions Action列表
     * @return 下拉菜单
     */
    public static JPopupMenu createPopupMenu(Action[] actions)
    {
        JPopupMenu ppMenu = new JPopupMenu();
        addMenu(ppMenu, actions);
        return ppMenu;
    }
    
    /**
     * 将Action列表构造成菜单，并添加到菜单中
     * @param menu 需要添加菜单的菜单
     * @param actions 用于构造菜单的Action列表
     */
    public static void addMenu(JPopupMenu menu,Action[] actions)
    {
        if (actions != null && actions.length > 0 && menu != null)
        {
            boolean sparator = false;
            for (int i = 0; i < actions.length; i++)
            {
                if (actions[i] != null)
                {
                    menu.add(actions[i]);
                    sparator = false;
                }
                else if (!sparator)
                {
                    menu.addSeparator();
                    sparator = true;
                }
            }
        }
    }
    
    /**
     * 使用Action列表更新菜单，原有菜单被删除
     * @param menu 需要更新的菜单
     * @param actions 用于构造菜单的Action列表
     */
    public static void setMenu(JPopupMenu menu, Action[] actions)
    {
        menu.removeAll();
        addMenu(menu, actions);
    }
    
    /**
     * 将Action列表构造成按钮并添加到工具栏
     * @param toolbar 需要添加按钮的工具栏
     * @param actions 用于构造按钮的Action列表
     */
    public static void addButtons(JToolBar toolbar, Action[] actions)
    {
        if (actions != null && actions.length > 0 && toolbar != null)
        {
            boolean sparator = false;
            for (int i = 0; i < actions.length; i++)
            {
                if (actions[i] != null)
                {
                    toolbar.add(actions[i]);
                    sparator = false;
                }
                else if (!sparator)
                {
                    toolbar.addSeparator();
                    sparator = true;
                }
            }
        }
    }
    
    /**
     * 设置控件组的使能状态
     * @param coms 需要设置使能状态的控件列表
     * @param enable 期望的使能状态
     */
    public static void setComEnable(final Component[] coms, final boolean enable)
    {
        if (coms != null && coms.length > 0)
        {
            for (int i = 0; i < coms.length; i++)
            {
                coms[i].setEnabled(enable);
            }
        }
    }
    
    /**
     * 设置控件树的使能状态
     * @param com 需要设置使能状态的控件
     * @param enable 期望的使能状态
     */
    public static void setComTreeEnable(final Component com, final boolean enable)
    {
        if (com != null)
        {
            Stack<Component> stack = new Stack<Component>();
            stack.push(com);
            while (!stack.isEmpty())
            {
                Component c = stack.pop();
                c.setEnabled(enable);
                if (c instanceof Container)
                {
                    Container container = (Container) c;
                    //如果是容器控件，则处理其中的内容
                    for (int i = container.getComponentCount() -1; i >= 0; i--)
                    {
                        stack.push(container.getComponent(i));
                    }
                }
            }
        }
    }
    
//    /**
//     * 初始化资源链，并更新界面文字
//     * @param com 起点控件
//     * @param dataKit 需要传递的数据中心
//     */
//    public static void updateResChain(final ISpaceName com, final IDataKit dataKit)
//    {
//        updateRes(com, dataKit.getResource(), com.getSpaceName(), dataKit);
//    }
    
//    /**
//     * 初始化资源链，并更新界面文字
//     * @param com 起点控件
//     * @param dataKit 需要传递的数据中心
//     */
//    public static void updateResChain(final Object obj, final IDataKit dataKit)
//    {
//        if (obj instanceof ISpaceName)
//        {
//            updateResChain((ISpaceName) obj, dataKit);
//        }
//        else
//        {
//            updateRes(obj, dataKit.getResource(), "", dataKit);
//        }
//    }
    
//    /**
//     * 初始化资源链，并更新界面文字
//     * @param com 起点控件
//     * @param dataKit 需要传递的数据中心
//     * @param spaceName 更新界面文字使用的名字空间
//     */
//    public static void updateResChain(final Object com, final IDataKit dataKit, final String spaceName)
//    {
//        updateRes(com, dataKit.getResource(), spaceName, dataKit);
//    }
    
    /**
     * 替换界面文字
     * 对于界面上使用点开头的字符串，将到资源文件中替换对应字符
     * @param com 需要更新文字的控件，必须是一个Component
     * @param resource 资源
     * @modify  2008-03-09 BoRoBoRoMe 更改错误的参数校验逻辑
     */
    public static void updateText(final ISpaceName com, final ResourceBundle resource)
    {
        if (!(com instanceof Component) || resource == null)
        {
            return;
        }
        updateRes(com, resource, com.getSpaceName());//, null);
    }
    
    /**
     * 替换界面文字
     * @see void updateText(final ISpaceName com, final ResourceBundle resource)
     * @param com com 需要更新文字的控件
     * @param resource 资源
     * @param spaceName 控件需要的名字空间
     * @modify 2008-03-09 BoRoBoRoMe 调用updateSingleComText时使用了错误的资源参数resource
     */
    public static void updateText(final Component com, final ResourceBundle resource, final String spaceName)
    {
        updateRes(com, resource, spaceName);//, null);
    }
    
    /**
     * 传递资源链，并更新界面文字
     * 是否传递资源，取决于dataKit是否不是null
     * @param obj 需要处理的控件
     * @param resource 更新界面使用的资源
     * @param spaceName 更新界面使用的资源名字空间
     * @param dataKit 需要传递的数据中心，可以为null
     */
    private static void updateRes(final Object obj, final ResourceBundle resource, final String spaceName)//, final IDataKit dataKit)
    {
        if (obj == null || resource == null)
        {
            return;
        }
        
        //待处理控件列表
        Stack<SpaceComInfo> stack = new Stack<SpaceComInfo>();
        stack.push(new SpaceComInfo(obj, new SpaceNameResourceBundle(resource, spaceName)));
        
        //遍历控件树
        while (!stack.isEmpty())
        {
            SpaceComInfo info = stack.pop();
            
            //处理控件上的文字
            if (info.obj instanceof Component)
            {
                updateSingleComText((Component) info.obj, info.resource);
            }
                        
            //将控件的子控件添加到遍历列表
            //检测是否是包含子控件的容器控件
            if (info.obj instanceof JDialog)
            {
                stack.add(createQueueItem(((JDialog) info.obj).getContentPane(), resource, info.resource));
            }
            else if (info.obj instanceof JSplitPane)
            {
                JSplitPane splitPane = (JSplitPane) info.obj;
                stack.add(createQueueItem(splitPane.getLeftComponent(), resource, info.resource));
                stack.add(createQueueItem(splitPane.getRightComponent(), resource, info.resource));
            }
            else if (info.obj instanceof JMenu)
            {
                JMenu menu = (JMenu) info.obj;
                if (menu.getPopupMenu() != null)
                {
                    stack.push(createQueueItem(menu.getPopupMenu(), resource, info.resource));
                }
            }
            else if(info.obj instanceof Container)
            {
                Container container = (Container) info.obj;
                //如果是容器控件，则处理其中的内容
                for (int i = container.getComponentCount() -1; i >= 0; i--)
                {
                    Component c = container.getComponent(i);
                    stack.push(createQueueItem(c, resource, info.resource));
                }
            }
        }
    }
    
    /**
     * 根据com类型构造适合的排队信息结构
     * @param obj 需要处理的控件
     * @param oriResource 原始资源
     * @param parentResoiurce 父结构资源
     * @return 排队结构
     */
    private static SpaceComInfo createQueueItem(final Object obj, final ResourceBundle oriResource, final ResourceBundle parentResoiurce)
    {
        SpaceComInfo item = null;
        if (obj instanceof ISpaceName)
        {
            ISpaceName sn = (ISpaceName) obj;
            item = new SpaceComInfo(obj, new SpaceNameResourceBundle(oriResource, sn.getSpaceName()));
        }
        else
        {
            item = new SpaceComInfo(obj, parentResoiurce);
        }
        return item;
    }
    
    /**
     * 只更新当前一个控件上的文字，不遍历其子控件
     * @param com 控件
     * @param resource 资源
     */
    private static void updateSingleComText(final Component com, final ResourceBundle resource)
    {
        if (com instanceof ExtLabel)
        {
            ExtLabel lb = (ExtLabel) com;
            String[] keys = lb.getKeys();
            String[] values = new String[keys.length];
            for (int i = 0; i < keys.length; i++)
            {
                values[i] = resource.getString(keys[i]);
            }
            lb.setValues(values);
        }
        //如果是标签则设置标签上的文字
        else if (com instanceof JLabel)
        {
            JLabel lb = (JLabel) com;
            String v = getResourceText(resource, lb.getText());
            if (v != null)
            {
                lb.setText(v);
            }
        }
        //如果是按钮
        else if (com instanceof AbstractButton)
        {
            AbstractButton ab = (AbstractButton) com;
            String v = getResourceText(resource, ab.getText());
            if (v != null)
            {
                ab.setText(v);
            }
        }
        //如果是文本录入框，则处理文本录入框中文字
        else if (com instanceof JTextComponent)
        {
            JTextComponent tc = (JTextComponent) com;
            String v = getResourceText(resource, tc.getText());
            if (v != null)
            {
                tc.setText(v);
            }
        }
        //如果是表格，则处理表格的各列标题
        else if (com instanceof JTable)
        {
            JTable tbl = (JTable) com;
            TableColumnModel tblModel = tbl.getColumnModel();
            for (int i = tblModel.getColumnCount() - 1; i >= 0; i--)
            {
                TableColumn column = tblModel.getColumn(i);
                Object head = column.getHeaderValue();
                if (head instanceof String)
                {
                    String headStr = getResourceText(resource, (String) head);
                    if (headStr != null)
                    {
                        column.setHeaderValue(headStr);
                    }
                }
            }
        }
        //如果是对话框，则处理对话框标题
        else if (com instanceof Dialog)
        {
            Dialog dlg = (Dialog) com;
            String v = getResourceText(resource, dlg.getTitle());
            if (v != null)
            {
                dlg.setTitle(v);
            }
        }
        else if (com instanceof Frame)
        {
            Frame f = (Frame) com;
            String v = getResourceText(resource, f.getTitle());
            if (v != null)
            {
                f.setTitle(v);
            }
        }
        else if (com instanceof JInternalFrame)
        {
            JInternalFrame f = (JInternalFrame) com;
            String v = getResourceText(resource, f.getTitle());
            if (v != null)
            {
                f.setTitle(v);
            }
        }
        
        //如果控件有边框，且边框是标题边框，则处理标题
        if (com instanceof JComponent
                && ((JComponent) com).getBorder() instanceof TitledBorder)
        {
            TitledBorder tb = (TitledBorder) ((JComponent) com).getBorder();
            String v = getResourceText(resource, tb.getTitle());
            if (v != null)
            {
                tb.setTitle(v);
            }
        }
    }
    
    /**
     * 根据Key值到资源文件中找一个合适的字符串出来
     * @param resource 资源
     * @param text 文字Key值
     * @return Key值对应的字符串,如果提供的文本不符合规范(如没有用点开头),则返回null
     * @modify 2008-03-09 BoRoBoRoMe 更正错误的参数校验
     */
    private static String getResourceText(final ResourceBundle resource, final String text)
    {
        if (text == null || !text.startsWith(".")) //$NON-NLS-1$
        {
            return null;
        }
        
        return resource.getString(text.substring(1));
    }
    
    
    
    /**
     * <P>Title:      工具包 Util v1.0</P>
     * <P>Description:在处理界面资源时，用于保存控件及其对应的名字空间的数据结构</P>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2008-1-17
     */
    private static class SpaceComInfo
    {
        /*
         * 名字空间
         */
        private final ResourceBundle resource;
        
        /*
         * 需要连接的对象
         */
        private final Object obj;
        
        /**
         * 构造函数
         * @param obj 需要连接的对象
         * @param resource 资源
         */
        public SpaceComInfo(final Object obj, final ResourceBundle resource)
        {
            this.resource = resource;
            this.obj = obj;
        }
    }
    
    /**
     * 填充下拉框列表
     * @param com 需要填充的下拉框
     * @param items 需要填充的内容
     */
    public static void setComItems(JComboBox com, Object[] items)
    {
        com.removeAllItems();
        for (int i = 0; i < items.length; i++)
        {
            com.addItem(items[i]);
        }
    }
    
    /**
     * 填充下拉框列表
     * @param com 需要填充的下拉框
     * @param items 需要填充的内容
     */
    public static void setComItems(JComboBox com, List<? extends Object> lst)
    {
        com.removeAllItems();
        for (Object obj : lst)
        {
            com.addItem(obj);
        }
    }
    
//    /**
//     * 检测控件内容是否为空，如果空则抛出异常
//     * @param textCom 需要检测的控件
//     * @param name 控件对应内容的名称
//     * @throws InputException 录入异常
//     */
//    public static void checkNotEmpty(final JTextComponent textCom, final String name) throws InputException
//    {
//        if (textCom.getText().trim().length() <= 0)
//        {
//            String msg = ExtUIRes.getRes().getString(ExtUIRes.DisplayUtil_NotEmpty);
//            throw new InputException(null, msg, new Object[]{name}, textCom, null);
//        }
//    }
    
    /**
     * 增加跟随控件
     * 发生跟随时，跟随控件的内容和被跟随控件的内容保持一致或者符合某个格式
     * @param source 被跟随控件
     * @param target 跟随控件
     */
    public static void addFollowCom(final JTextField source, final JTextField target)//, final String format)
    {
        source.getDocument().addDocumentListener(new FollowListener(source, target));
    }
    
    /**
     * <P>Title:      工具包 Util v1.0</P>
     * <P>Description:跟随监听器</P>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2008-3-15
     */
    private static class FollowListener implements DocumentListener
    {
        /*
         * 被跟随控件
         */
        private JTextField source;
        
        /*
         * 跟随控件
         */
        private JTextField target;
        
        /**
         * 构造函数
         * @param source 被跟随控件
         * @param target 跟随控件
         */
        public FollowListener(final JTextField source, final JTextField target)
        {
            this.source = source;
            this.target = target;
        }
        
        /**
         * 跟随动作
         */
        private void follow()
        {
            target.setText(source.getText());
        }
        
        /* (non-Javadoc)
         * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
         */
        public void changedUpdate(final DocumentEvent e)
        {
            follow();
        }

        /* (non-Javadoc)
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        public void insertUpdate(final DocumentEvent e)
        {
            follow();
        }

        /* (non-Javadoc)
         * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
         */
        public void removeUpdate(final DocumentEvent e)
        {
            follow();
        }
        
    }
}
