// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PropertiesAdminOperations.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			Current

public interface _PropertiesAdminOperations
{

	public abstract String getProperty(String s, Current current);

	public abstract Map getPropertiesForPrefix(String s, Current current);
}
