// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PropertiesAdminDel.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDel

public interface _PropertiesAdminDel
	extends _ObjectDel
{

	public abstract String getProperty(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract Map getPropertiesForPrefix(String s, Map map)
		throws LocalExceptionWrapper;
}
