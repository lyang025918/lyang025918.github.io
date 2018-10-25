// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteSeqHelper.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public final class ByteSeqHelper
{

	public ByteSeqHelper()
	{
	}

	public static void write(BasicStream __os, byte __v[])
	{
		__os.writeByteSeq(__v);
	}

	public static byte[] read(BasicStream __is)
	{
		byte __v[] = __is.readByteSeq();
		return __v;
	}

	public static void write(OutputStream __outS, byte __v[])
	{
		__outS.writeByteSeq(__v);
	}

	public static byte[] read(InputStream __inS)
	{
		byte __v[] = __inS.readByteSeq();
		return __v;
	}
}
