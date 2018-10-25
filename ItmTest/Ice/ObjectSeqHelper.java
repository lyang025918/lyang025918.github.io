// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectSeqHelper.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.SequencePatcher;

// Referenced classes of package Ice:
//			Object, ObjectImpl, OutputStream, InputStream

public final class ObjectSeqHelper
{

	public ObjectSeqHelper()
	{
	}

	public static void write(BasicStream __os, Object __v[])
	{
		if (__v == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(__v.length);
			for (int __i0 = 0; __i0 < __v.length; __i0++)
				__os.writeObject(__v[__i0]);

		}
	}

	public static Object[] read(BasicStream __is)
	{
		int __len0 = __is.readAndCheckSeqSize(4);
		String __type0 = ObjectImpl.ice_staticId();
		Object __v[] = new Object[__len0];
		for (int __i0 = 0; __i0 < __len0; __i0++)
			__is.readObject(new SequencePatcher(__v, Ice/Object, __type0, __i0));

		return __v;
	}

	public static void write(OutputStream __outS, Object __v[])
	{
		if (__v == null)
		{
			__outS.writeSize(0);
		} else
		{
			__outS.writeSize(__v.length);
			for (int __i0 = 0; __i0 < __v.length; __i0++)
				__outS.writeObject(__v[__i0]);

		}
	}

	public static Object[] read(InputStream __inS)
	{
		int __len0 = __inS.readAndCheckSeqSize(4);
		String __type0 = ObjectImpl.ice_staticId();
		Object __v[] = new Object[__len0];
		for (int __i0 = 0; __i0 < __len0; __i0++)
			__inS.readObject(new SequencePatcher(__v, Ice/Object, __type0, __i0));

		return __v;
	}
}
