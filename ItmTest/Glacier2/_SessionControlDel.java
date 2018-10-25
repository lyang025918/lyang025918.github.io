// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionControlDel.java

package Glacier2;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			StringSetPrx, IdentitySetPrx

public interface _SessionControlDel
	extends _ObjectDel
{

	public abstract StringSetPrx categories(Map map)
		throws LocalExceptionWrapper;

	public abstract StringSetPrx adapterIds(Map map)
		throws LocalExceptionWrapper;

	public abstract IdentitySetPrx identities(Map map)
		throws LocalExceptionWrapper;

	public abstract int getSessionTimeout(Map map)
		throws LocalExceptionWrapper;

	public abstract void destroy(Map map)
		throws LocalExceptionWrapper;
}
