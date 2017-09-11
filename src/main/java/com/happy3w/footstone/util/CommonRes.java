/*
 * <P>Title:      Common V1.0</P>
 * <P>Description:资源定义</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-19
 */
package com.happy3w.footstone.util;

/**
 * <P>Title:      Common V1.0</P>
 * <P>Description:资源定义</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-4-19
 */
@SuppressWarnings("nls")
public class CommonRes
{
    /**
     * 资源文件名称
     */
    public static final String ResFileName = "res/com/boroborome/common/util/resource.properties"; //$NON-NLS-1$

    public static final String ObjectMapTableConfig = "res/com/boroborome/common/dataenergy/objectMapTable.xml";

    /**
     * Get Resource({0}) Failed.
     */
    public static final String ResourceManager_GetResourceFailed = "ResourceManager.GetResourceFailed"; //$NON-NLS-1$
    
    /**
     * Load Xml({0}) failed.
     */
    public static final String XmlHelper_LoadFaild = "XmlHelper.LoadFaild"; //$NON-NLS-1$
    
    /**
     * Save Xml({0}) failed.
     */
    public static final String XmlHelper_SaveFailed = "XmlHelper.SaveFailed";

    /**
     * "Load XmlObject({0}) Failed."
     */
    public static final String XmlHelper_LoadXmlObjFaild = "XmlHelper.LoadXmlObjFaild";

    public static final String StrictList_AddFailed = "StrictList.AddFailed";

    public static final String StrictList_CreateChildFailed = "StrictList.CreateChildFailed";

    /**
     * Not find a construtor has one param like ({1}) in type ({0}).
     */
    public static final String XmlHelper_NotFindConstructor = "XmlHelper.NotFindConstructor";

    /**
     * Transelate data failed.
     */
    public static final String XmlEnergy_Failed = "XmlDataEnergy.Failed";

    /**
     * Run Post Method "{0}" failed in Object "{1}".
     */
    public static final String RunPostMethodFailed = "ObjectMapAttribute.RunPostMethodFailed";
    
    /**
     * Run Method "{0}" failed in attribute "{1}".
     */
    public static final String RunMethodFailed = "ObjectMapAttribute.RunMethodFailed";
    
    /**
     * The type \"{0}\" can't be translated.
     */
    public static final String DataEnergyTranslateFailed = "DataEnergy.TranslateFailed";

    /**
     * Can't find class {0}.
     */
    public static final String DataEnergy_NoClass = "DataEnergy.NotFindClass";

    /**
     * Create Instance failed in startData. Data name is {0}
     */
    public static final String CreateInstanceFailed = "DataEnergy.CreateInstanceFailed";

    /**
     * Unknown data name : {0}
     */
    public static final String UnknownDataName = "DataEnergy.UnknownDataName";
    
}
