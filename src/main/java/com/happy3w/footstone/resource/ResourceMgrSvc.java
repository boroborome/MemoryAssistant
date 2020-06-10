/*
 * <P>Title:      基石模块</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-7-2
 */
package com.happy3w.footstone.resource;

import com.happy3w.footstone.util.ReadOnlyMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author BoRoBoRoMe
 */
public class ResourceMgrSvc implements IResourceMgrSvc {
    /**
     * 资源影射表
     */
    private Map<String, Map<String, String>> mapRes = new HashMap<String, Map<String, String>>();


    /* (non-Javadoc)
     * @see com.boroborome.footstone.resource.IResourceMgrSvc#getRes(java.lang.String)
     */
    @Override
    public Map<String, String> getResMap(String key) {
        return mapRes.get(key);
    }

    /* (non-Javadoc)
     * @see com.boroborome.footstone.resource.IResourceMgrSvc#regRes(java.lang.String, java.util.ResourceBundle)
     */
    @Override
    public void regRes(String key, ResourceBundle res) {
        if (res == null) {
            unregRes(key);
        } else {
            Map<String, String> map = new HashMap<String, String>();
            for (String k : res.keySet()) {
                map.put(k, res.getString(k));
            }
            mapRes.put(key, new ReadOnlyMap<String, String>(map));
        }
    }

    /* (non-Javadoc)
     * @see com.boroborome.footstone.resource.IResourceMgrSvc#unregRes(java.lang.String)
     */
    @Override
    public void unregRes(String key) {
        mapRes.put(key, null);
    }

    @Override
    public void regRes(String key, InputStream resStream) throws IOException {
        PropertyResourceBundleExt prb = new PropertyResourceBundleExt(resStream);
        regRes(key, prb);
    }

    @Override
    public String getRes(String mapKey, String resKey) {
        Map<String, String> map = mapRes.get(mapKey);
        return map == null ? null : map.get(resKey);
    }

}
