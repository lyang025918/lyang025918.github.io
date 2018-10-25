// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoolSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class BoolSeqHelper
{

	public BoolSeqHelper()
	{
	}

	public static void write(BasicStream __os, boolean __v[])
	{
		__os.writeBoolSeq(__v);
	}

	public static boolean[] read(BasicStream __is)
	{
		boolean __v[] = __is.readBoolSeq();
		return __v;
	}

	public static void write(OutputStream __outS, boolean __v[])
	{
		__outS.writeBoolSeq(__v);
	}

	public static boolean[] read(InputStream __inS)
	{
		boolean __v[] = __inS.readBoolSeq();
		return __v;
	}
}
