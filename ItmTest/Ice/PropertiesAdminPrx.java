// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesAdminPrx.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, AsyncResult, Callback, Callback_PropertiesAdmin_getProperty, 
//			AMI_PropertiesAdmin_getProperty, Callback_PropertiesAdmin_getPropertiesForPrefix, AMI_PropertiesAdmin_getPropertiesForPrefix

public interface PropertiesAdminPrx
	extends ObjectPrx
{

	public abstract String getProperty(String s);

	public abstract String getProperty(String s, Map map);

	public abstract AsyncResult begin_getProperty(String s);

	public abstract AsyncResult begin_getProperty(String s, Map map);

	public abstract AsyncResult begin_getProperty(String s, Callback callback);

	public abstract AsyncResult begin_getProperty(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getProperty(String s, Callback_PropertiesAdmin_getProperty callback_propertiesadmin_getproperty);

	public abstract AsyncResult begin_getProperty(String s, Map map, Callback_PropertiesAdmin_getProperty callback_propertiesadmin_getproperty);

	public abstract String end_getProperty(AsyncResult asyncresult);

	public abstract boolean getProperty_async(AMI_PropertiesAdmin_getProperty ami_propertiesadmin_getproperty, String s);

	public abstract boolean getProperty_async(AMI_PropertiesAdmin_getProperty ami_propertiesadmin_getproperty, String s, Map map);

	public abstract Map getPropertiesForPrefix(String s);

	public abstract Map getPropertiesForPrefix(String s, Map map);

	public abstract AsyncResult begin_getPropertiesForPrefix(String s);

	public abstract AsyncResult begin_getPropertiesForPrefix(String s, Map map);

	public abstract AsyncResult begin_getPropertiesForPrefix(String s, Callback callback);

	public abstract AsyncResult begin_getPropertiesForPrefix(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getPropertiesForPrefix(String s, Callback_PropertiesAdmin_getPropertiesForPrefix callback_propertiesadmin_getpropertiesforprefix);

	public abstract AsyncResult begin_getPropertiesForPrefix(String s, Map map, Callback_PropertiesAdmin_getPropertiesForPrefix callback_propertiesadmin_getpropertiesforprefix);

	public abstract Map end_getPropertiesForPrefix(AsyncResult asyncresult);

	public abstract boolean getPropertiesForPrefix_async(AMI_PropertiesAdmin_getPropertiesForPrefix ami_propertiesadmin_getpropertiesforprefix, String s);

	public abstract boolean getPropertiesForPrefix_async(AMI_PropertiesAdmin_getPropertiesForPrefix ami_propertiesadmin_getpropertiesforprefix, String s, Map map);
}
