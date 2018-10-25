// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterOperationsNC.java

package Ice;


// Referenced classes of package Ice:
//			ObjectPrx

public interface _RouterOperationsNC
{

	public abstract ObjectPrx getClientProxy();

	public abstract ObjectPrx getServerProxy();

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public abstract void addProxy(ObjectPrx objectprx);

	public abstract ObjectPrx[] addProxies(ObjectPrx aobjectprx[]);
}
