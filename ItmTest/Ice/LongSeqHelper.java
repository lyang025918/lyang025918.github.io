// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LongSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class LongSeqHelper
{

	public LongSeqHelper()
	{
	}

	public static void write(BasicStream __os, long __v[])
	{
		__os.writeLongSeq(__v);
	}

	public static long[] read(BasicStream __is)
	{
		long __v[] = __is.readLongSeq();
		return __v;
	}

	public static void write(OutputStream __outS, long __v[])
	{
		__outS.writeLongSeq(__v);
	}

	public static long[] read(InputStream __inS)
	{
		long __v[] = __inS.readLongSeq();
		return __v;
	}
}
