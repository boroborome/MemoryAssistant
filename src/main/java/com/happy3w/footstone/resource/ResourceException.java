/*
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:资源异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2008-1-16
 */
package com.happy3w.footstone.resource;

import com.happy3w.footstone.exception.MessageException;

/**
 * <P>Title:      工具包 Util v1.0</P>
 * <P>Description:资源异常</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2008-1-16
 */
public class ResourceException extends MessageException {

    public ResourceException(String resPath, String message, Object[] params, Throwable cause) {
        super(resPath, message, params, cause);
    }

    public ResourceException(String resPath, String message, Throwable cause) {
        super(resPath, message, cause);
    }

}
