// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ApplicationObserverOperations.java

package IceGrid;

import Ice.Current;
import java.util.List;

// Referenced classes of package IceGrid:
//			ApplicationInfo, ApplicationUpdateInfo

public interface _ApplicationObserverOperations
{

	public abstract void applicationInit(int i, List list, Current current);

	public abstract void applicationAdded(int i, ApplicationInfo applicationinfo, Current current);

	public abstract void applicationRemoved(int i, String s, Current current);

	public abstract void applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Current current);
}
