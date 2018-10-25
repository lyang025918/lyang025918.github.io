// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FloatSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class FloatSeqHelper
{

	public FloatSeqHelper()
	{
	}

	public static void write(BasicStream __os, float __v[])
	{
		__os.writeFloatSeq(__v);
	}

	public static float[] read(BasicStream __is)
	{
		float __v[] = __is.readFloatSeq();
		return __v;
	}

	public static void write(OutputStream __outS, float __v[])
	{
		__outS.writeFloatSeq(__v);
	}

	public static float[] read(InputStream __inS)
	{
		float __v[] = __inS.readFloatSeq();
		return __v;
	}
}
