// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceManagerOperations.java

package IceBox;

import Ice.Current;
import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, ServiceObserverPrx

public interface _ServiceManagerOperations
{

	public abstract Map getSliceChecksums(Current current);

	public abstract void startService(String s, Current current)
		throws AlreadyStartedException, NoSuchServiceException;

	public abstract void stopService(String s, Current current)
		throws AlreadyStoppedException, NoSuchServiceException;

	public abstract void addObserver(ServiceObserverPrx serviceobserverprx, Current current);

	public abstract void shutdown(Current current);
}
