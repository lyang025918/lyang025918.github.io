// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationDescriptorSeqHelper.java

package IceGrid;

import IceInternal.BasicStream;
import java.util.*;

// Referenced classes of package IceGrid:
//			ApplicationDescriptor

public final class ApplicationDescriptorSeqHelper
{

	public ApplicationDescriptorSeqHelper()
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
			ApplicationDescriptor __elem;
			for (Iterator i$ = __v.iterator(); i$.hasNext(); __elem.__write(__os))
				__elem = (ApplicationDescriptor)i$.next();

		}
	}

	public static List read(BasicStream __is)
	{
		List __v = new LinkedList();
		int __len0 = __is.readAndCheckSeqSize(10);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			ApplicationDescriptor __elem = new ApplicationDescriptor();
			__elem.__read(__is);
			__v.add(__elem);
		}

		return __v;
	}
}
