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
package com.happy3w.footstone.resource;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * <P>Title:      Common V1.0</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-10-9
 * @!deprocated [表示不建议使用]
 * @modify [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 * @see [相关类，可选，也可多条]
 * @since [产品/模块版本，表示从哪个版本开始有]
 */
public class ResourceCache extends ResourceBundle {
    private ResourceBundle innerResource;

    public ResourceCache(String outerFileName, Class<?> resourceClass, String innerFileName) {
        innerResource = ResourceManager.getInstance().loadResource(outerFileName, resourceClass, innerFileName);
        innerResource = new SafeResourceBundle(innerResource);
//        if (innerResource == null)
//        {
//            try
//            {
//                innerResource = ResourceManager.getInstance().loadResource(path);
//            }
//            catch (ResourceException e)
//            {
//                ExceptionGrave.getInstance().bury(e);
//                
//                try
//                {
//                    innerResource = new SafeResourceBundle(new PropertyResourceBundle(resourceClass.getResourceAsStream(res)));
//                }
//                catch (IOException e1)
//                {
//                    ExceptionGrave.getInstance().bury(e);
//                }
//            }
//        }
    }

    @Override
    public Enumeration<String> getKeys() {
        Enumeration<String> e = null;
        if (innerResource != null) {
            e = innerResource.getKeys();
        }
        return e;
    }

    @Override
    protected Object handleGetObject(String key) {
        Object result = key;
        if (innerResource != null) {
            result = innerResource.getObject(key);
        }
        return result;
    }

}
