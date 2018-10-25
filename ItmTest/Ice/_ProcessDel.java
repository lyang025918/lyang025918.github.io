// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ProcessDel.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDel

public interface _ProcessDel
	extends _ObjectDel
{

	public abstract void shutdown(Map map)
		throws LocalExceptionWrapper;

	public abstract void writeMessage(String s, int i, Map map)
		throws LocalExceptionWrapper;
}
