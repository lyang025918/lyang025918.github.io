// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectProxySeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			ObjectPrx, OutputStream, InputStream

public final class ObjectProxySeqHelper
{

	public ObjectProxySeqHelper()
	{
	}

	public static void write(BasicStream __os, ObjectPrx __v[])
	{
		if (__v == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(__v.length);
			for (int __i0 = 0; __i0 < __v.length; __i0++)
				__os.writeProxy(__v[__i0]);

		}
	}

	public static ObjectPrx[] read(BasicStream __is)
	{
		int __len0 = __is.readAndCheckSeqSize(2);
		ObjectPrx __v[] = new ObjectPrx[__len0];
		for (int __i0 = 0; __i0 < __len0; __i0++)
			__v[__i0] = __is.readProxy();

		return __v;
	}

	public static void write(OutputStream __outS, ObjectPrx __v[])
	{
		if (__v == null)
		{
			__outS.writeSize(0);
		} else
		{
			__outS.writeSize(__v.length);
			for (int __i0 = 0; __i0 < __v.length; __i0++)
				__outS.writeProxy(__v[__i0]);

		}
	}

	public static ObjectPrx[] read(InputStream __inS)
	{
		int __len0 = __inS.readAndCheckSeqSize(2);
		ObjectPrx __v[] = new ObjectPrx[__len0];
		for (int __i0 = 0; __i0 < __len0; __i0++)
			__v[__i0] = __inS.readProxy();

		return __v;
	}
}
