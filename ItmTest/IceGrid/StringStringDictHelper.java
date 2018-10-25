// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringStringDictHelper.java

package IceGrid;

import IceInternal.BasicStream;
import java.util.*;

public final class StringStringDictHelper
{

	public StringStringDictHelper()
	{
	}

	public static void write(BasicStream __os, Map __v)
	{
		if (__v == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(__v.size());
			java.util.Map.Entry __e;
			for (Iterator i$ = __v.entrySet().iterator(); i$.hasNext(); __os.writeString((String)__e.getValue()))
			{
				__e = (java.util.Map.Entry)i$.next();
				__os.writeString((String)__e.getKey());
			}

		}
	}

	public static Map read(BasicStream __is)
	{
		Map __v = new HashMap();
		int __sz0 = __is.readSize();
		for (int __i0 = 0; __i0 < __sz0; __i0++)
		{
			String __key = __is.readString();
			String __value = __is.readString();
			__v.put(__key, __value);
		}

		return __v;
	}
}
