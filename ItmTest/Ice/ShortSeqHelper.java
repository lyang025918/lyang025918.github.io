// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ShortSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class ShortSeqHelper
{

	public ShortSeqHelper()
	{
	}

	public static void write(BasicStream __os, short __v[])
	{
		__os.writeShortSeq(__v);
	}

	public static short[] read(BasicStream __is)
	{
		short __v[] = __is.readShortSeq();
		return __v;
	}

	public static void write(OutputStream __outS, short __v[])
	{
		__outS.writeShortSeq(__v);
	}

	public static short[] read(InputStream __inS)
	{
		short __v[] = __inS.readShortSeq();
		return __v;
	}
}
