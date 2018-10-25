// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceManagerOperationsNC.java

package IceBox;

import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, ServiceObserverPrx

public interface _ServiceManagerOperationsNC
{

	public abstract Map getSliceChecksums();

	public abstract void startService(String s)
		throws AlreadyStartedException, NoSuchServiceException;

	public abstract void stopService(String s)
		throws AlreadyStoppedException, NoSuchServiceException;

	public abstract void addObserver(ServiceObserverPrx serviceobserverprx);

	public abstract void shutdown();
}
