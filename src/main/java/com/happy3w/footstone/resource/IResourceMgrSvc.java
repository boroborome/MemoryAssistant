/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2009-6-28
 */
package com.happy3w.footstone.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * <P>Title:      基石模块</P>
 * <P>Description:提供资源管理服务</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2009-6-28
 */
public interface IResourceMgrSvc
{
    /**
     * 注册资源
     * @param key 资源名称
     * @param res 资源文件内容
     */
    void regRes(String key, ResourceBundle res);
    
    /**
     * 注册资源
     * @param key 资源名称
     * @param resStream 资源数据流
     */
    void regRes(String key, InputStream resStream) throws IOException;
    
    /**
     * 注销资源
     * @param key
     */
    void unregRes(String key);
    
    /**
     * 获取资源映射表
     * @param key 资源名称
     * @return 资源
     */
    Map<String, String> getResMap(String key);
    
    /**
     * 获取资源
     * @param mapKey 资源映射表的键值
     * @param resKey  资源键值
     * @return 查询得到的字符串，如果没有则返回null
     */
    String getRes(String mapKey, String resKey);
}
