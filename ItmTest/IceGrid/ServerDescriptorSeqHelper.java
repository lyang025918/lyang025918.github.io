// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerDescriptorSeqHelper.java

package IceGrid;

import IceInternal.BasicStream;
import IceInternal.ListPatcher;
import java.util.*;

// Referenced classes of package IceGrid:
//			ServerDescriptor

public final class ServerDescriptorSeqHelper
{

	public ServerDescriptorSeqHelper()
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
			ServerDescriptor __elem;
			for (Iterator i$ = __v.iterator(); i$.hasNext(); __os.writeObject(__elem))
				__elem = (ServerDescriptor)i$.next();

		}
	}

	public static List read(BasicStream __is)
	{
		List __v = new LinkedList();
		int __len0 = __is.readAndCheckSeqSize(4);
		String __type0 = ServerDescriptor.ice_staticId();
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			__v.add(null);
			__is.readObject(new ListPatcher(__v, IceGrid/ServerDescriptor, __type0, __i0));
		}

		return __v;
	}
}
