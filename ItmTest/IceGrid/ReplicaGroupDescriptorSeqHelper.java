// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReplicaGroupDescriptorSeqHelper.java

package IceGrid;

import IceInternal.BasicStream;
import java.util.*;

// Referenced classes of package IceGrid:
//			ReplicaGroupDescriptor

public final class ReplicaGroupDescriptorSeqHelper
{

	public ReplicaGroupDescriptorSeqHelper()
	{
	}

	public static void write(BasicStream __os, List __v)
	{
		if (__v == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(__v.size());
			ReplicaGroupDescriptor __elem;
			for (Iterator i$ = __v.iterator(); i$.hasNext(); __elem.__write(__os))
				__elem = (ReplicaGroupDescriptor)i$.next();

		}
	}

	public static List read(BasicStream __is)
	{
		List __v = new LinkedList();
		int __len0 = __is.readAndCheckSeqSize(7);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			ReplicaGroupDescriptor __elem = new ReplicaGroupDescriptor();
			__elem.__read(__is);
			__v.add(__elem);
		}

		return __v;
	}
}
