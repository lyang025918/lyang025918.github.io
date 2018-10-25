// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Protocol.java

package IceInternal;


public final class Protocol
{

	public static final int headerSize = 14;
	public static final byte magic[] = {
		73, 99, 101, 80
	};
	public static final byte protocolMajor = 1;
	public static final byte protocolMinor = 0;
	public static final byte encodingMajor = 1;
	public static final byte encodingMinor = 0;
	public static final byte requestMsg = 0;
	public static final byte requestBatchMsg = 1;
	public static final byte replyMsg = 2;
	public static final byte validateConnectionMsg = 3;
	public static final byte closeConnectionMsg = 4;
	public static final byte requestHdr[] = {
		magic[0], magic[1], magic[2], magic[3], 1, 0, 1, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0
	};
	public static final byte requestBatchHdr[] = {
		magic[0], magic[1], magic[2], magic[3], 1, 0, 1, 0, 1, 0, 
		0, 0, 0, 0, 0, 0, 0, 0
	};
	public static final byte replyHdr[] = {
		magic[0], magic[1], magic[2], magic[3], 1, 0, 1, 0, 2, 0, 
		0, 0, 0, 0
	};

	public Protocol()
	{
	}

}
