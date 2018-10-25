// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ApplicationObserverDel.java

package IceGrid;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.List;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ApplicationInfo, ApplicationUpdateInfo

public interface _ApplicationObserverDel
	extends _ObjectDel
{

	public abstract void applicationInit(int i, List list, Map map)
		throws LocalExceptionWrapper;

	public abstract void applicationAdded(int i, ApplicationInfo applicationinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void applicationRemoved(int i, String s, Map map)
		throws LocalExceptionWrapper;

	public abstract void applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Map map)
		throws LocalExceptionWrapper;
}
