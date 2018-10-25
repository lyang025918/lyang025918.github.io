// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDel.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDel, ObjectPrx

public interface _RouterDel
	extends _ObjectDel
{

	public abstract ObjectPrx getClientProxy(Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx getServerProxy(Map map)
		throws LocalExceptionWrapper;

	public abstract void addProxy(ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx[] addProxies(ObjectPrx aobjectprx[], Map map)
		throws LocalExceptionWrapper;
}
