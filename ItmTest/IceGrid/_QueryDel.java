// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _QueryDel.java

package IceGrid;

import Ice.*;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			LoadSample

public interface _QueryDel
	extends _ObjectDel
{

	public abstract ObjectPrx findObjectById(Identity identity, Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx findObjectByType(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx[] findAllObjectsByType(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx[] findAllReplicas(ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper;
}
