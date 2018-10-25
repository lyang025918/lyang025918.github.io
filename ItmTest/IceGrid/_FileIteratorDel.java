// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileIteratorDel.java

package IceGrid;

import Ice.StringSeqHolder;
import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			FileNotAvailableException

public interface _FileIteratorDel
	extends _ObjectDel
{

	public abstract boolean read(int i, StringSeqHolder stringseqholder, Map map)
		throws LocalExceptionWrapper, FileNotAvailableException;

	public abstract void destroy(Map map)
		throws LocalExceptionWrapper;
}
