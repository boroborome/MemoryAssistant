/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:与文件系统相关的工具</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-23
 */
package com.happy3w.footstone.util;

import java.io.File;
import java.util.Stack;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:与文件系统相关的工具</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-3-23
 */
public final class FileUtil
{
    /**
     * 删除目录/文件（删除子目录和文件）
     * 如果删除失败，则忽略
     * @param dir 需要删除的目录
     */
    public static void deleteDirectory(File dir)
    {
        if (dir != null && dir.exists())
        {
            Stack<FileInfo> stack = new Stack<FileInfo>();
            FileInfo dirInfo = new FileInfo();
            dirInfo.file = dir;
            stack.push(dirInfo);
            
            while (!stack.isEmpty())
            {
                FileInfo fileInfo = stack.pop();
                if (fileInfo.file.isDirectory() && !fileInfo.listOver)
                {
                    boolean hasDir = false;
                    File[] files = fileInfo.file.listFiles();
                    fileInfo.listOver = true;
                    for (int i = 0; i < files.length; i++)
                    {
                        File f = files[i];
                        if (f.isDirectory())
                        {
                            if (!hasDir)
                            {
                                hasDir = true;
                                stack.push(fileInfo);
                            }
                            
                            FileInfo fi = new FileInfo();
                            fi.file = f;
                            stack.push(fi);
                        }
                        else
                        {
                            f.delete();
                        }
                    }
                    if (!hasDir)
                    {
                        fileInfo.file.delete();
                    }
                }
                else
                {
                    fileInfo.file.delete();
                }
            }
        }
    }
    
    private static class FileInfo
    {
        public File file;
        
        /**
         * 是否做过列目录动作，如果做过列目录动作，说明，这个目录应该可以删除
         */
        public boolean listOver;
        
    }
    
    /**
     * 检测提供地址是否为绝对地址
     * @param path 需要检测的地址
     * @return 结果
     */
    public static boolean isAbstractPath(final String path)
    {
        return path != null 
            && (path.startsWith("/")  //$NON-NLS-1$
                    || path.startsWith("\\") //$NON-NLS-1$
                    || path.indexOf(":/") > 0 //$NON-NLS-1$
                    || path.indexOf(":\\") > 0); //$NON-NLS-1$
    }
    
    /**
     * 连接两个路径
     * @param path1 需要连接的路径1
     * @param path2 需要连接的路径2
     * @return 结果路径
     */
    public static String joinPath(final String path1, final String path2)
    {
        //如果path2为绝对地址，则直接返回path2
        if (path1 == null || path1.length() == 0 || isAbstractPath(path2))
        {
            return path2;
        }
        
        StringBuilder buf = new StringBuilder(path1);
        int l = buf.length();
        if (buf.charAt(l - 1) != '/' && buf.charAt(l - 1) != '\\')
        {
            buf.append('/');
        }
        buf.append(path2);
        return buf.toString();
    }
    
    /**
     * 连接一系列路径,忽略中间的Null
     * @param paths 路径列表
     * @return 结果路径
     */
    public static String joinPath(final String[] paths)
    {
//      如果最后为绝对地址，则直接返回最后
        if (isAbstractPath(paths[paths.length - 1]))
        {
            return paths[paths.length - 1];
        }
        
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < paths.length; i++)
        {
            if (isAbstractPath(paths[i]))
            {
                buf.setLength(0);
                buf.append(paths[i]);
            }
            else if (paths[i] != null)
            {
                //忽略null
                int l = buf.length();
                if (l > 0 && buf.charAt(l - 1) != '/' && buf.charAt(l - 1) != '\\')
                {
                    buf.append('/');
                }
                buf.append(paths[i]);
            }
        }
        return buf.toString();
    }
}
