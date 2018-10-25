// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionControlOperations.java

package Glacier2;

import Ice.Current;

// Referenced classes of package Glacier2:
//			StringSetPrx, IdentitySetPrx

public interface _SessionControlOperations
{

	public abstract StringSetPrx categories(Current current);

	public abstract StringSetPrx adapterIds(Current current);

	public abstract IdentitySetPrx identities(Current current);

	public abstract int getSessionTimeout(Current current);

	public abstract void destroy(Current current);
}
