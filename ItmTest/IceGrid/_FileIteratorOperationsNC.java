// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileIteratorOperationsNC.java

package IceGrid;

import Ice.StringSeqHolder;

// Referenced classes of package IceGrid:
//			FileNotAvailableException

public interface _FileIteratorOperationsNC
{

	public abstract boolean read(int i, StringSeqHolder stringseqholder)
		throws FileNotAvailableException;

	public abstract void destroy();
}
