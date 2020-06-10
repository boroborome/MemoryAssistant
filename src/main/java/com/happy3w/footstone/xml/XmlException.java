package com.happy3w.footstone.xml;

import com.happy3w.footstone.exception.RuntimeMessageException;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:操作Xml时发生的异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-18
 */
public class XmlException extends RuntimeMessageException {

    public XmlException(String resPath, String message, Object[] params, Throwable cause) {
        super(resPath, message, params, cause);
    }

    public XmlException(String resPath, String message, Throwable cause) {
        super(resPath, message, cause);
    }


}
