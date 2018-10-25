// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceManagerDel.java

package IceBox;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, ServiceObserverPrx

public interface _ServiceManagerDel
	extends _ObjectDel
{

	public abstract Map getSliceChecksums(Map map)
		throws LocalExceptionWrapper;

	public abstract void startService(String s, Map map)
		throws LocalExceptionWrapper, AlreadyStartedException, NoSuchServiceException;

	public abstract void stopService(String s, Map map)
		throws LocalExceptionWrapper, AlreadyStoppedException, NoSuchServiceException;

	public abstract void addObserver(ServiceObserverPrx serviceobserverprx, Map map)
		throws LocalExceptionWrapper;

	public abstract void shutdown(Map map)
		throws LocalExceptionWrapper;
}
