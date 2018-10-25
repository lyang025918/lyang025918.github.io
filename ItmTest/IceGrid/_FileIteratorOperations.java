// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileIteratorOperations.java

package IceGrid;

import Ice.Current;
import Ice.StringSeqHolder;

// Referenced classes of package IceGrid:
//			FileNotAvailableException

public interface _FileIteratorOperations
{

	public abstract boolean read(int i, StringSeqHolder stringseqholder, Current current)
		throws FileNotAvailableException;

	public abstract void destroy(Current current);
}
