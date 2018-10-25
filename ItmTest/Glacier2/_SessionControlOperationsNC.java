// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionControlOperationsNC.java

package Glacier2;


// Referenced classes of package Glacier2:
//			StringSetPrx, IdentitySetPrx

public interface _SessionControlOperationsNC
{

	public abstract StringSetPrx categories();

	public abstract StringSetPrx adapterIds();

	public abstract IdentitySetPrx identities();

	public abstract int getSessionTimeout();

	public abstract void destroy();
}
