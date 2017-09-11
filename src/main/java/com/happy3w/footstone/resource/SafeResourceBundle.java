/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:安全的资源管理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-19
 */
package com.happy3w.footstone.resource;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:安全的资源管理器，本资源管理器在读到不存在的键值时并不抛出异常，而是将键值添加标记后返回</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-19
 */
public class SafeResourceBundle extends ResourceBundle
{
    /*
     * 被管理的资源
     */
    private ResourceBundle resource;
    
    /**
     * 构造函数
     */
    public SafeResourceBundle()
    {
    	//Nothing to initalize
    }
    
    /**
     * 构造函数
     * @param resource 需要管理的资源
     */
    public SafeResourceBundle(final ResourceBundle resource)
    {
        this.resource = resource;
    }

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#getKeys()
     */
    @Override
    public Enumeration<String> getKeys()
    {
        return resource.getKeys();
    }

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
     */
    @Override
    protected Object handleGetObject(final String key)
    {
        Object value = null;
        try
        {
            value = resource.getObject(key);
        }
        catch (Exception exp)
        {
            value = "#" + key + '#'; //$NON-NLS-1$
            System.out.println("need resource:" + value); //$NON-NLS-1$
        }
        
        return value;
    }

    /**
     * 获取需要管理的资源
     * @return 需要管理的资源
     */
    public ResourceBundle getResource()
    {
        return resource;
    }

    /**
     * 设置需要管理的资源
     * @param resource 需要管理的资源
     */
    public void setResource(final ResourceBundle resource)
    {
        this.resource = resource;
    }

}
