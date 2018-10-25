// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadHelper.java

package org.apache.lucene.analysis.payloads;


public class PayloadHelper
{

	public PayloadHelper()
	{
	}

	public static byte[] encodeFloat(float payload)
	{
		return encodeFloat(payload, new byte[4], 0);
	}

	public static byte[] encodeFloat(float payload, byte data[], int offset)
	{
		return encodeInt(Float.floatToIntBits(payload), data, offset);
	}

	public static byte[] encodeInt(int payload)
	{
		return encodeInt(payload, new byte[4], 0);
	}

	public static byte[] encodeInt(int payload, byte data[], int offset)
	{
		data[offset] = (byte)(payload >> 24);
		data[offset + 1] = (byte)(payload >> 16);
		data[offset + 2] = (byte)(payload >> 8);
		data[offset + 3] = (byte)payload;
		return data;
	}

	public static float decodeFloat(byte bytes[])
	{
		return decodeFloat(bytes, 0);
	}

	public static final float decodeFloat(byte bytes[], int offset)
	{
		return Float.intBitsToFloat(decodeInt(bytes, offset));
	}

	public static final int decodeInt(byte bytes[], int offset)
	{
		return (bytes[offset] & 0xff) << 24 | (bytes[offset + 1] & 0xff) << 16 | (bytes[offset + 2] & 0xff) << 8 | bytes[offset + 3] & 0xff;
	}
}
