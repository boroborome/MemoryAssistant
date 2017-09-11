/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:扩展的属性资源管理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-15
 */
package com.happy3w.footstone.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.PropertyResourceBundle;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:扩展的属性资源管理器
 *      本资源管理器是为了正常处理不同编码的property文件</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-15
 */
public class PropertyResourceBundleExt extends PropertyResourceBundle
{
    /*
     * 正确的编码
     * 如果为null则使用本地默认的编码
     */
    private String charSet;
    
    /**
     * 构造函数
     * @param stream
     * @throws IOException
     */
    public PropertyResourceBundleExt(final InputStream stream) throws IOException
    {
        super(stream);
    }

    /* (non-Javadoc)
     * @see java.util.PropertyResourceBundle#handleGetObject(java.lang.String)
     */
    @Override
    public Object handleGetObject(final String key)
    {
        String value = (String) super.handleGetObject(key);
        try
        {
            byte[] bs = value.getBytes("ISO8859-1"); //$NON-NLS-1$
            if (charSet == null)
            {
                value = new String(bs);
            }
            else
            {
                value = new String(bs, charSet);
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return value;
    }

}
