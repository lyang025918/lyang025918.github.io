// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceObserverDel.java

package IceBox;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

public interface _ServiceObserverDel
	extends _ObjectDel
{

	public abstract void servicesStarted(String as[], Map map)
		throws LocalExceptionWrapper;

	public abstract void servicesStopped(String as[], Map map)
		throws LocalExceptionWrapper;
}
