// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryInfoSeqHelper.java

package IceGrid;

import IceInternal.BasicStream;

// Referenced classes of package IceGrid:
//			RegistryInfo

public final class RegistryInfoSeqHelper
{

	public RegistryInfoSeqHelper()
	{
	}

	public static void write(BasicStream __os, RegistryInfo __v[])
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

	public static RegistryInfo[] read(BasicStream __is)
	{
		int __len0 = __is.readAndCheckSeqSize(2);
		RegistryInfo __v[] = new RegistryInfo[__len0];
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			__v[__i0] = new RegistryInfo();
			__v[__i0].__read(__is);
		}

		return __v;
	}
}
