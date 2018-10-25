// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class StringSeqHelper
{

	public StringSeqHelper()
	{
	}

	public static void write(BasicStream __os, String __v[])
	{
		__os.writeStringSeq(__v);
	}

	public static String[] read(BasicStream __is)
	{
		String __v[] = __is.readStringSeq();
		return __v;
	}

	public static void write(OutputStream __outS, String __v[])
	{
		__outS.writeStringSeq(__v);
	}

	public static String[] read(InputStream __inS)
	{
		String __v[] = __inS.readStringSeq();
		return __v;
	}
}
