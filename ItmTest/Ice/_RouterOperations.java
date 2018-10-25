// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterOperations.java

package Ice;


// Referenced classes of package Ice:
//			Current, ObjectPrx

public interface _RouterOperations
{

	public abstract ObjectPrx getClientProxy(Current current);

	public abstract ObjectPrx getServerProxy(Current current);

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public abstract void addProxy(ObjectPrx objectprx, Current current);

	public abstract ObjectPrx[] addProxies(ObjectPrx aobjectprx[], Current current);
}
