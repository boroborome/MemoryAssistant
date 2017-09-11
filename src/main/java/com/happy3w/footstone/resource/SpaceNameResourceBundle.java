/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:带有名字空间的资源管理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-16
 */
package com.happy3w.footstone.resource;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:带有名字空间的资源管理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-16
 */
public class SpaceNameResourceBundle extends ResourceBundle
{
    /*
     * 真正的资源
     */
    private ResourceBundle resource;
    
    /*
     * 使用的名字空间
     */
    private String spaceName;
    
    /*
     * 在读取资源时，每一个Key前面需要增加的前缀
     */
    private String prefix;
    
    /**
     * 构造函数
     */
    public SpaceNameResourceBundle()
    {
    	//Nothing to initalize
    }

    /**
     * 构造函数
     * @param resource 资源
     * @param spaceName 名字空间
     */
    public SpaceNameResourceBundle(final ResourceBundle resource, final String spaceName)
    {
        setResource(resource);
        setSpaceName(spaceName);
    }
    
    /* (non-Javadoc)
     * @see java.util.ResourceBundle#getKeys()
     */
    @Override
    public Enumeration<String> getKeys()
    {
        Enumeration<String> e = parent.getKeys();
        if (prefix.length() > 0)
        {
            e = new SpaceNameEnumeration(e, prefix);
        }
        return e;
    }

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
     */
    @Override
    protected Object handleGetObject(String key)
    {
        return resource.getObject(prefix + key);
    }

    /**
     * 获取资源
     * @return 资源
     */
    public ResourceBundle getResource()
    {
        return resource;
    }

    /**
     * 设置资源
     * @param resource 资源
     */
    public void setResource(final ResourceBundle resource)
    {
        this.resource = resource;
    }
    
    /**
     * 获取名字空间
     * @return 名字空间
     */
    public String getSpaceName()
    {
        return spaceName;
    }

    /**
     * 设置名字空间
     * @param spaceName 名字空间
     */
    public void setSpaceName(final String spaceName)
    {
        this.spaceName = spaceName;
        if (spaceName == null || spaceName.length() == 0)
        {
            prefix = ""; //$NON-NLS-1$
        }
        else
        {
            prefix = spaceName + '.';
        }
    }
    
    /**
     * <P>Title:      工具包 Util v1.0</P>
     * <P>Description:名字空间资源的枚举器</P>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2008-1-16
     */
    private static class SpaceNameEnumeration implements Enumeration<String>
    {
        /*
         * 真正的枚举器
         */
        private Enumeration<String> parent;
        
        /*
         * 资源前缀
         */
        private String prefix;
        
        /*
         * 下一个有效键值
         */
        private String nextKey;
        
        /**
         * 构造函数
         * @param parent 真正的枚举器
         * @param prefix 资源前缀
         */
        public SpaceNameEnumeration(final Enumeration<String> parent, final String prefix)
        {
            this.parent = parent;
            this.prefix = prefix;
        }
        
        /* (non-Javadoc)
         * @see java.util.Enumeration#hasMoreElements()
         */
        @Override
        public boolean hasMoreElements()
        {
            nextKey = null;
            while (parent.hasMoreElements())
            {
                String key = parent.nextElement();
                if (key.startsWith(prefix))
                {
                    nextKey = key;
                    break;
                }
            }
            
            return nextKey != null;
        }

        /* (non-Javadoc)
         * @see java.util.Enumeration#nextElement()
         */
        @Override
        public String nextElement()
        {
            return nextKey;
        }
        
    }
}
