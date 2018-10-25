// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SmallFloat.java

package org.apache.lucene.util;


public class SmallFloat
{

	private SmallFloat()
	{
	}

	public static byte floatToByte(float f, int numMantissaBits, int zeroExp)
	{
		int fzero = 63 - zeroExp << numMantissaBits;
		int bits = Float.floatToRawIntBits(f);
		int smallfloat = bits >> 24 - numMantissaBits;
		if (smallfloat <= fzero)
			return ((byte)(bits > 0 ? 1 : 0));
		if (smallfloat >= fzero + 256)
			return -1;
		else
			return (byte)(smallfloat - fzero);
	}

	public static float byteToFloat(byte b, int numMantissaBits, int zeroExp)
	{
		if (b == 0)
		{
			return 0.0F;
		} else
		{
			int bits = (b & 0xff) << 24 - numMantissaBits;
			bits += 63 - zeroExp << 24;
			return Float.intBitsToFloat(bits);
		}
	}

	public static byte floatToByte315(float f)
	{
		int bits = Float.floatToRawIntBits(f);
		int smallfloat = bits >> 21;
		if (smallfloat <= 384)
			return ((byte)(bits > 0 ? 1 : 0));
		if (smallfloat >= 640)
			return -1;
		else
			return (byte)(smallfloat - 384);
	}

	public static float byte315ToFloat(byte b)
	{
		if (b == 0)
		{
			return 0.0F;
		} else
		{
			int bits = (b & 0xff) << 21;
			bits += 0x30000000;
			return Float.intBitsToFloat(bits);
		}
	}

	public static byte floatToByte52(float f)
	{
		int bits = Float.floatToRawIntBits(f);
		int smallfloat = bits >> 19;
		if (smallfloat <= 1952)
			return ((byte)(bits > 0 ? 1 : 0));
		if (smallfloat >= 2208)
			return -1;
		else
			return (byte)(smallfloat - 1952);
	}

	public static float byte52ToFloat(byte b)
	{
		if (b == 0)
		{
			return 0.0F;
		} else
		{
			int bits = (b & 0xff) << 19;
			bits += 0x3d000000;
			return Float.intBitsToFloat(bits);
		}
	}
}
