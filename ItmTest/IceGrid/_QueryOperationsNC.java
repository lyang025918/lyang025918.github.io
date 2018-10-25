// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _QueryOperationsNC.java

package IceGrid;

import Ice.Identity;
import Ice.ObjectPrx;

// Referenced classes of package IceGrid:
//			LoadSample

public interface _QueryOperationsNC
{

	public abstract ObjectPrx findObjectById(Identity identity);

	public abstract ObjectPrx findObjectByType(String s);

	public abstract ObjectPrx findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample);

	public abstract ObjectPrx[] findAllObjectsByType(String s);

	public abstract ObjectPrx[] findAllReplicas(ObjectPrx objectprx);
}
