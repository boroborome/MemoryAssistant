/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:资源管理器</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 */
package com.happy3w.footstone.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.happy3w.footstone.FootstoneSvcAccess;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;

import com.happy3w.footstone.exception.IExceptionGrave;
import com.happy3w.footstone.util.CommonRes;
import com.happy3w.footstone.util.FileUtil;
import com.happy3w.footstone.xml.IXmlObject;
import com.happy3w.footstone.xml.XmlException;
import com.happy3w.footstone.xml.XmlHelper;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:资源管理器
 *      资源管理器负责对外提供各种资源，读取和保存，在系统启动时这个模块应该第一个加载<br>
 *      资源管理有如下几点使用要点<br>
 *      <ol>
 *      </ol>
 *      </P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-11
 * @modify        2008-03-08 BoRoBoRoMe 为getInstance增加static属性
 * @modify        2008-03-30 BoRoBoRoMe 增加资源映射地图
 */
public class ResourceManager
{
    /**
     * 资源文件的配置文件名称
     * 资源文件管理器在启动的时候会依据这个配置文件初始化自己
     * 主要配置，资源管理器的基路径
     */
    private static final String ConfigFile = "ResourceManagerConfig.xml"; //$NON-NLS-1$
    
    /**
     * 资源文件基路径属性名称
     */
    private static final String BasePathAttribute = "basePath"; //$NON-NLS-1$
    
    /*
     * 资源文件后缀
     */
    private static final String PROPERTY_FILE_EXT = ".properties"; //$NON-NLS-1$

    /*
     * 静态全局资源管理器
     */
    private static ResourceManager instance;
    
    /**
     * 获取静态全局资源管理器
     * @return 静态全局资源管理器
     */
    public static synchronized ResourceManager getInstance()
    {
        if (instance == null)
        {
            instance = new ResourceManager();
            File file = new File(ConfigFile);
            if (file.exists())
            {
                Document doc = ResourceManager.loadDocument(file);
                Element root = doc.getRootElement();
                instance.setBasePath(XmlHelper.loadStrAttribute(root, BasePathAttribute));
            }
        }
        return instance;
    }
    
    /*
     * 资源地图
     */
    private Map<String, ResourceBundle> mapRes;
    
    /*
     * 基路径
     */
    private String basePath;
    
    /**
     * 构造函数
     */
    public ResourceManager()
    {
        super();
        mapRes = new HashMap<String, ResourceBundle>();
    }

    /**
     * 获得键值对应的资源
     * @param key 键值
     * @return 资源
     */
    public ResourceBundle getResource(final String key)
    {
        return mapRes.get(key);
    }
    
    /**
     * 设置键值对应的资源
     * @param key 键
     * @param resource 资源
     */
    public void setResource(final String key, final ResourceBundle resource)
    {
        mapRes.put(key, resource);
    }
    
    /**
     * 检测文件是否存在
     * 如果输入的文件是相对路径，则以基路径为准
     * @param file 要检测的文件
     * @return 文件是否存在
     */
    public boolean isExist(final String file)
    {
        String filePath = revisePath(file);
        return new File(filePath).exists();
    }
    
    /**
     * 将提供的路径转换成一个文件
     * @param path 路径
     * @return 文件
     */
    private File toFile(String path)
    {
        String filePath = revisePath(path);
        if (!filePath.endsWith(PROPERTY_FILE_EXT))
        {
            filePath = filePath + PROPERTY_FILE_EXT;
        }
        return new File(revisePath(filePath));
    }
    
    /**
     * 获取资源
     * @param path 路径,如果没有以.properties结尾，则自动添加后缀.properties
     * @return 资源
     * @throws ResourceException 资源异常
     */
    public ResourceBundle loadResource(final String path) throws ResourceException
    {
        return loadResourceExt(path, true);
    }
    
    /**
     * 获取资源
     * @param path 路径,如果没有以.properties结尾，则自动添加后缀.properties
     * @param withException 表示出错是否要抛出异常
     * @return 得到的资源
     * @throws ResourceException 资源异常
     */
    private ResourceBundle loadResourceExt(final String path, boolean withException) throws ResourceException
    {
        FileInputStream stream = null;
        ResourceBundle resource = null;
        File file = toFile(path);
        try
        {
            stream = new FileInputStream(file);
            resource = new PropertyResourceBundleExt(stream);
        }
        catch (IOException e)
        {
            if (withException)
            {
                throw new ResourceException(CommonRes.ResFileName,
                        CommonRes.ResourceManager_GetResourceFailed, 
                        new Object[]{file.getPath()}, e);
            }
        }
        finally
        {
            if (stream != null)
            {
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    throw new ResourceException(CommonRes.ResFileName,
                            CommonRes.ResourceManager_GetResourceFailed,
                            new Object[]{file.getPath()}, e);
                }
            }
        }
        return resource;
    }

    /**
     * 选择性的加载资源文件
     * 首先察看缓存，之后察看外部文件，最后使用内嵌文件
     * @param outerFileName 外部资源文件
     * @param resourceClass 加载内部资源使用的类
     * @param innerFileName 内部资源路径
     * @return 资源
     */
    public ResourceBundle loadResource(String outerFileName, Class<?> resourceClass, String innerFileName)
    {
        ResourceBundle resource;
        
        //察看缓存
        resource = getResource(outerFileName);
        if (resource == null)
        {
            //尝试加载外部资源文件
            try
            {
                resource = loadResourceExt(outerFileName, false);
            }
            catch (ResourceException e)
            {
                //不会抛出异常
            }
        }
        
        //没有外部资源文件，则加载内嵌文件
        if (resource == null)
        {
            try
            {
//                ClassLoader.getSystemResource(innerFileName).openStream();
                resource = new PropertyResourceBundleExt(ClassLoader.getSystemResource(innerFileName).openStream());
            }
            catch (IOException exp)
            {
                IExceptionGrave grave = FootstoneSvcAccess.getExceptionGrave();
                if (grave != null)
                {
                    grave.bury(exp);
                }
            }
        }
        return resource;
    }
    
    /**
     * 获取基路径
     * @return 基路径
     */
    public String getBasePath()
    {
        return basePath;
    }

    /**
     * 设置基路径
     * @param basePath 基路径
     */
    private void setBasePath(final String basePath)
    {
        this.basePath = basePath;
    }
    
    //-----------------Xml操作-------------------------

    /**
     * 根据当前路径修改路径
     * 如果path为相对路径，那么这个函数会将相对路径转换成相对base的绝对路径
     * 如果path是绝对路径则，直接返回
     * @param path 需要校正的路径
     * @return 校正后的路径（绝对路径）
     */
    private String revisePath(final String path)
    {
        String newPath = null;
        if (FileUtil.isAbstractPath(path))
        {
            newPath = path;
        }
        else
        {
            newPath = FileUtil.joinPath(basePath , path);
        }
        return newPath;
    }
    
    /**
     * 获取文件输入流
     * @param path 文件路径
     * @return 文件输入流
     * @throws FileNotFoundException
     */
    public InputStream getFileInputStream(final String path) throws FileNotFoundException
    {
        //识别绝对路径和相对路径
        String newPath = revisePath(path);
        return new FileInputStream(newPath);
    }
    
    public OutputStream getFileOutputStream(final String path) throws FileNotFoundException
    {
      //识别绝对路径和相对路径
        String newPath = revisePath(path);
        return new FileOutputStream(newPath);
    }
    
    /**
     * 通过给出的路径读取xml文件
     * @param path 文件路径
     * @return Xml文档模型
     * @throws XmlException 读取Xml异常
     */
    public Document loadDocument(final String path) throws XmlException
    {
        if (path == null || path.length() <= 0)
        {
            throw new IllegalArgumentException("loadDocument not accept null params."); //$NON-NLS-1$
        }
        
        //识别绝对路径和相对路径
        String newPath = revisePath(path);
        return loadDocument(new File(newPath));
    }
    
//    public static Document loadDocument(final InputStream in)
//    {
//        Document document = null;
//        try
//        {
//            SAXReader reader = new SAXReader();
//            reader.setIgnoreComments(true);
////            reader.setIncludeExternalDTDDeclarations(false);
////            reader.setValidation(false);
//            reader.setFeature(Constants.XERCES_FEATURE_PREFIX + Constants.LOAD_EXTERNAL_DTD_FEATURE, false);
////            reader.setFeature(Constants.NAMESPACE_PREFIXES_FEATURE + Constants.ANONYMOUS_TYPE_NAMESPACE, false);
//            document = reader.read(in);
//        }
//        catch (Exception e)
//        {
//            throw new XmlException(CommonRes.ResFileName,
//                    CommonRes.XmlHelper_LoadFaild, new Object[]{"inputstream"} , e); //$NON-NLS-1$
//        }
//        return document;
//    }
    
    public static Document loadDocument(final File file) throws XmlException
    {
        Document document = null;
        try
        {
            SAXReader reader = new SAXReader();
            document = reader.read(file);
        }
        catch (Exception e)
        {
            throw new XmlException(CommonRes.ResFileName,
                    CommonRes.XmlHelper_LoadFaild, new Object[]{file.getAbsolutePath()} , e);
        }
        return document;
    }
    
    /**
     * 将结构保存到指定位置
     * @param document Xml文档
     * @param path 需要保存的相对位置
     * @throws XmlException 写Xml异常
     */
    public void saveDocument(final Document document, final String path) throws XmlException
    {
        saveDocument(document, path, null);
    }
    
    /**
     * 将结构保存到指定位置
     * @param document Xml文档
     * @param path 需要保存的相对位置
     * @param xsl 转换使用的格式，可以为null
     * @throws XmlException 写Xml异常
     */
    public void saveDocument(final Document document, final String path, final Document xsl) throws XmlException
    {
        if (path == null || path.length() <= 0 || document == null)
        {
            throw new IllegalArgumentException("saveDocument not accept null params."); //$NON-NLS-1$
        }
        
        //识别绝对路径和相对路径
        String newPath = revisePath(path);
        
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try
        {
            Transformer transformer = null;
            if (xsl == null)
            {
                transformer = factory.newTransformer();
            }
            else
            {
                transformer = factory.newTransformer(new DocumentSource(xsl));
            }
            transformer.transform(new DocumentSource(document), new StreamResult(new File(newPath)));
        }
        catch (Exception e)
        {
            throw new XmlException(CommonRes.ResFileName,
                    CommonRes.XmlHelper_SaveFailed, 
                    new Object[]{newPath}, e);
        }
    }

    /**
     * 让对象xmlObj从path位置的Xml文件加载信息
     * @param xmlObj xml对象
     * @param path 读取的位置
     * @throws XmlException Xml读取异常
     */
    public void load(final IXmlObject xmlObj, final String path) throws XmlException
    {
        if (xmlObj == null || path == null || path.length() <= 0)
        {
            throw new IllegalArgumentException("load not accept null params."); //$NON-NLS-1$
        }
        
        Document document = loadDocument(path);
        xmlObj.loadInfo(document.getRootElement());
    }
    
    /**
     * 将对象xmlObj保存到path位置
     * @param xmlObj xml对象
     * @param rootName 根节点名称
     * @param path 保存的相对路径
     * @throws XmlException Xml异常
     */
    public void save(final IXmlObject xmlObj, final String rootName, final String path) throws XmlException
    {
        save(xmlObj, rootName, path, null);
    }
    
    /**
     * 将对象xmlObj保存到path位置
     * @param xmlObj xml对象
     * @param rootName 根节点名称
     * @param path 保存的相对路径
     * @param xsl 转换使用的格式，可以为null
     * @throws XmlException Xml异常
     */
    public void save(final IXmlObject xmlObj, final String rootName, final String path, final Document xsl) throws XmlException
    {
        if (xmlObj == null || path == null || path.length() <= 0 
                || rootName == null || rootName.length() <= 0)
        {
            throw new IllegalArgumentException("save not accept null params."); //$NON-NLS-1$
        }
        
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(rootName);
        
        xmlObj.saveInfo(root);
        saveDocument(document, path, xsl);
    }
}
