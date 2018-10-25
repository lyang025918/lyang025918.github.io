// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DoubleSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class DoubleSeqHelper
{

	public DoubleSeqHelper()
	{
	}

	public static void write(BasicStream __os, double __v[])
	{
		__os.writeDoubleSeq(__v);
	}

	public static double[] read(BasicStream __is)
	{
		double __v[] = __is.readDoubleSeq();
		return __v;
	}

	public static void write(OutputStream __outS, double __v[])
	{
		__outS.writeDoubleSeq(__v);
	}

	public static double[] read(InputStream __inS)
	{
		double __v[] = __inS.readDoubleSeq();
		return __v;
	}
}
