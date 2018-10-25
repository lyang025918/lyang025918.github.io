// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _QueryOperations.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			LoadSample

public interface _QueryOperations
{

	public abstract ObjectPrx findObjectById(Identity identity, Current current);

	public abstract ObjectPrx findObjectByType(String s, Current current);

	public abstract ObjectPrx findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Current current);

	public abstract ObjectPrx[] findAllObjectsByType(String s, Current current);

	public abstract ObjectPrx[] findAllReplicas(ObjectPrx objectprx, Current current);
}
