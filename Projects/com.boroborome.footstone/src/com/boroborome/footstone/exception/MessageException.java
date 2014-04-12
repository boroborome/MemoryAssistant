/*
 * <P>Title:      Common V1.0</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-10-9
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.exception;

import java.text.MessageFormat;

import com.boroborome.footstone.FootstoneSvcAccess;
import com.boroborome.footstone.resource.IResourceMgrSvc;

/**
 * <P>Title:      Common V1.0</P>
 * <P>Description:带有一个消息的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-10-9
 */
public class MessageException extends RuntimeException implements IMessageException
{
    
    /*
     * 处理异常的资源路径
     */
    private String resPath;
    
    /*
     * 与错误有关的参数列表，可能用于拼接提示字符串
     */
    private Object[] params;

    /**
     * 构造函数
     * @param resPath 资源路径
     * @param formatKey 资源文件中消息格式串的键值
     * @param cause 导致错误的原因，可以为Null
     */
    public MessageException(final String resPath, final String formatKey, 
            final Throwable cause)
    {
        super(formatKey, cause);
        this.resPath = resPath;
    }
    
    /**
     * 构造函数
     * @param resPath 资源路径
     * @param formatKey 资源文件中消息格式串的键值
     */
    public MessageException(final String resPath, final String formatKey)
    {
        super(formatKey);
        this.resPath = resPath;
    }
    
    /**
     * 构造函数
     * @param resPath 资源路径
     * @param formatKey 资源文件中消息格式串的键值
     * @param params 参数列表
     * @param cause 导致错误的原因，可以为Null
     */
    public MessageException(final String resPath, final String formatKey, 
            final Object[] params, final Throwable cause)
    {
        super(formatKey, cause);
        this.resPath = resPath;
        this.params = params;
    }
    
    /**
     * 构造函数
     * @param resPath 资源路径
     * @param formatKey 资源文件中消息格式串的键值
     * @param params 参数列表
     */
    public MessageException(final String resPath, final String formatKey, 
            final Object[] params)
    {
        super(formatKey);
        this.resPath = resPath;
        this.params = params;
    }

    
    
    /**
     * 获取消息<br>
     * 从资源文件中获取相关内容
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage()
    {
        IResourceMgrSvc svc = FootstoneSvcAccess.getResourceMgrSvc();
        String format = svc != null ? svc.getRes(resPath, super.getMessage()) : super.getMessage();
        if (format == null)
        {
            format = super.getMessage();
        }
        return MessageFormat.format(format, params);
    }

    /**
     * 获取资源文件中消息格式串的键值
     * @return
     */
    @Override
    public String getFormatKey()
    {
        return super.getMessage();
    }
    
    /**
     * 获取资源路径
     * @return 资源路径
     */
    @Override
    public String getResPath()
    {
        return resPath;
    }

    /**
     * 获取错误相关参数
     * @return 错误相关参数
     */
    @Override
    public Object[] getParams()
    {
        return params;
    }
}
