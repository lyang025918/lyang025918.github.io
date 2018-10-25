// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class IntSeqHelper
{

	public IntSeqHelper()
	{
	}

	public static void write(BasicStream __os, int __v[])
	{
		__os.writeIntSeq(__v);
	}

	public static int[] read(BasicStream __is)
	{
		int __v[] = __is.readIntSeq();
		return __v;
	}

	public static void write(OutputStream __outS, int __v[])
	{
		__outS.writeIntSeq(__v);
	}

	public static int[] read(InputStream __inS)
	{
		int __v[] = __inS.readIntSeq();
		return __v;
	}
}
