// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LinkInfoSeqHelper.java

package IceStorm;

import IceInternal.BasicStream;

// Referenced classes of package IceStorm:
//			LinkInfo

public final class LinkInfoSeqHelper
{

	public LinkInfoSeqHelper()
	{
	}

	public static void write(BasicStream __os, LinkInfo __v[])
	{
		if (__v == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(__v.length);
			for (int __i0 = 0; __i0 < __v.length; __i0++)
				__v[__i0].__write(__os);

		}
	}

	public static LinkInfo[] read(BasicStream __is)
	{
		int __len0 = __is.readAndCheckSeqSize(7);
		LinkInfo __v[] = new LinkInfo[__len0];
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			__v[__i0] = new LinkInfo();
			__v[__i0].__read(__is);
		}

		return __v;
	}
}
