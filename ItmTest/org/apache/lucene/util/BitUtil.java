// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BitUtil.java

package org.apache.lucene.util;


public final class BitUtil
{

	public static final byte ntzTable[] = {
		8, 0, 1, 0, 2, 0, 1, 0, 3, 0, 
		1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 
		2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 
		1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 
		3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 
		1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 
		2, 0, 1, 0, 6, 0, 1, 0, 2, 0, 
		1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 
		1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 
		2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 
		1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 
		3, 0, 1, 0, 2, 0, 1, 0, 7, 0, 
		1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 
		2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 
		1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 
		5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 
		1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 
		2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 
		1, 0, 6, 0, 1, 0, 2, 0, 1, 0, 
		3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 
		1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 
		2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 
		1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 
		1, 0, 2, 0, 1, 0
	};
	public static final byte nlzTable[] = {
		8, 7, 6, 6, 5, 5, 5, 5, 4, 4, 
		4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
		3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
		2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0
	};

	private BitUtil()
	{
	}

	public static int pop(long x)
	{
		x -= x >>> 1 & 0x5555555555555555L;
		x = (x & 0x3333333333333333L) + (x >>> 2 & 0x3333333333333333L);
		x = x + (x >>> 4) & 0xf0f0f0f0f0f0f0fL;
		x += x >>> 8;
		x += x >>> 16;
		x += x >>> 32;
		return (int)x & 0x7f;
	}

	public static long pop_array(long A[], int wordOffset, int numWords)
	{
		int n = wordOffset + numWords;
		long tot = 0L;
		long tot8 = 0L;
		long ones = 0L;
		long twos = 0L;
		long fours = 0L;
		int i;
		for (i = wordOffset; i <= n - 8; i += 8)
		{
			long b = A[i];
			long c = A[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2];
			c = A[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = A[i + 4];
			c = A[i + 5];
			u = ones ^ u;
			twosA = ones & u | u & c;
			ones = u ^ c;
			u = A[i + 6];
			c = A[i + 7];
			u = ones ^ u;
			twosB = ones & u | u & c;
			ones = u ^ c;
			u = twos ^ twosA;
			long foursB = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = fours ^ foursA;
			long eights = fours & foursA | u & foursB;
			fours = u ^ foursB;
			tot8 += pop(eights);
		}

		if (i <= n - 4)
		{
			long b = A[i];
			long c = A[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2];
			c = A[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 4;
		}
		if (i <= n - 2)
		{
			long b = A[i];
			long c = A[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			long foursA = twos & twosA;
			twos ^= twosA;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 2;
		}
		if (i < n)
			tot += pop(A[i]);
		tot += (long)((pop(fours) << 2) + (pop(twos) << 1) + pop(ones)) + (tot8 << 3);
		return tot;
	}

	public static long pop_intersect(long A[], long B[], int wordOffset, int numWords)
	{
		int n = wordOffset + numWords;
		long tot = 0L;
		long tot8 = 0L;
		long ones = 0L;
		long twos = 0L;
		long fours = 0L;
		int i;
		for (i = wordOffset; i <= n - 8; i += 8)
		{
			long b = A[i] & B[i];
			long c = A[i + 1] & B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] & B[i + 2];
			c = A[i + 3] & B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = A[i + 4] & B[i + 4];
			c = A[i + 5] & B[i + 5];
			u = ones ^ u;
			twosA = ones & u | u & c;
			ones = u ^ c;
			u = A[i + 6] & B[i + 6];
			c = A[i + 7] & B[i + 7];
			u = ones ^ u;
			twosB = ones & u | u & c;
			ones = u ^ c;
			u = twos ^ twosA;
			long foursB = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = fours ^ foursA;
			long eights = fours & foursA | u & foursB;
			fours = u ^ foursB;
			tot8 += pop(eights);
		}

		if (i <= n - 4)
		{
			long b = A[i] & B[i];
			long c = A[i + 1] & B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] & B[i + 2];
			c = A[i + 3] & B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 4;
		}
		if (i <= n - 2)
		{
			long b = A[i] & B[i];
			long c = A[i + 1] & B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			long foursA = twos & twosA;
			twos ^= twosA;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 2;
		}
		if (i < n)
			tot += pop(A[i] & B[i]);
		tot += (long)((pop(fours) << 2) + (pop(twos) << 1) + pop(ones)) + (tot8 << 3);
		return tot;
	}

	public static long pop_union(long A[], long B[], int wordOffset, int numWords)
	{
		int n = wordOffset + numWords;
		long tot = 0L;
		long tot8 = 0L;
		long ones = 0L;
		long twos = 0L;
		long fours = 0L;
		int i;
		for (i = wordOffset; i <= n - 8; i += 8)
		{
			long b = A[i] | B[i];
			long c = A[i + 1] | B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] | B[i + 2];
			c = A[i + 3] | B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = A[i + 4] | B[i + 4];
			c = A[i + 5] | B[i + 5];
			u = ones ^ u;
			twosA = ones & u | u & c;
			ones = u ^ c;
			u = A[i + 6] | B[i + 6];
			c = A[i + 7] | B[i + 7];
			u = ones ^ u;
			twosB = ones & u | u & c;
			ones = u ^ c;
			u = twos ^ twosA;
			long foursB = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = fours ^ foursA;
			long eights = fours & foursA | u & foursB;
			fours = u ^ foursB;
			tot8 += pop(eights);
		}

		if (i <= n - 4)
		{
			long b = A[i] | B[i];
			long c = A[i + 1] | B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] | B[i + 2];
			c = A[i + 3] | B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 4;
		}
		if (i <= n - 2)
		{
			long b = A[i] | B[i];
			long c = A[i + 1] | B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			long foursA = twos & twosA;
			twos ^= twosA;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 2;
		}
		if (i < n)
			tot += pop(A[i] | B[i]);
		tot += (long)((pop(fours) << 2) + (pop(twos) << 1) + pop(ones)) + (tot8 << 3);
		return tot;
	}

	public static long pop_andnot(long A[], long B[], int wordOffset, int numWords)
	{
		int n = wordOffset + numWords;
		long tot = 0L;
		long tot8 = 0L;
		long ones = 0L;
		long twos = 0L;
		long fours = 0L;
		int i;
		for (i = wordOffset; i <= n - 8; i += 8)
		{
			long b = A[i] & ~B[i];
			long c = A[i + 1] & ~B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] & ~B[i + 2];
			c = A[i + 3] & ~B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = A[i + 4] & ~B[i + 4];
			c = A[i + 5] & ~B[i + 5];
			u = ones ^ u;
			twosA = ones & u | u & c;
			ones = u ^ c;
			u = A[i + 6] & ~B[i + 6];
			c = A[i + 7] & ~B[i + 7];
			u = ones ^ u;
			twosB = ones & u | u & c;
			ones = u ^ c;
			u = twos ^ twosA;
			long foursB = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = fours ^ foursA;
			long eights = fours & foursA | u & foursB;
			fours = u ^ foursB;
			tot8 += pop(eights);
		}

		if (i <= n - 4)
		{
			long b = A[i] & ~B[i];
			long c = A[i + 1] & ~B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] & ~B[i + 2];
			c = A[i + 3] & ~B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 4;
		}
		if (i <= n - 2)
		{
			long b = A[i] & ~B[i];
			long c = A[i + 1] & ~B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			long foursA = twos & twosA;
			twos ^= twosA;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 2;
		}
		if (i < n)
			tot += pop(A[i] & ~B[i]);
		tot += (long)((pop(fours) << 2) + (pop(twos) << 1) + pop(ones)) + (tot8 << 3);
		return tot;
	}

	public static long pop_xor(long A[], long B[], int wordOffset, int numWords)
	{
		int n = wordOffset + numWords;
		long tot = 0L;
		long tot8 = 0L;
		long ones = 0L;
		long twos = 0L;
		long fours = 0L;
		int i;
		for (i = wordOffset; i <= n - 8; i += 8)
		{
			long b = A[i] ^ B[i];
			long c = A[i + 1] ^ B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] ^ B[i + 2];
			c = A[i + 3] ^ B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = A[i + 4] ^ B[i + 4];
			c = A[i + 5] ^ B[i + 5];
			u = ones ^ u;
			twosA = ones & u | u & c;
			ones = u ^ c;
			u = A[i + 6] ^ B[i + 6];
			c = A[i + 7] ^ B[i + 7];
			u = ones ^ u;
			twosB = ones & u | u & c;
			ones = u ^ c;
			u = twos ^ twosA;
			long foursB = twos & twosA | u & twosB;
			twos = u ^ twosB;
			u = fours ^ foursA;
			long eights = fours & foursA | u & foursB;
			fours = u ^ foursB;
			tot8 += pop(eights);
		}

		if (i <= n - 4)
		{
			long b = A[i] ^ B[i];
			long c = A[i + 1] ^ B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			b = A[i + 2] ^ B[i + 2];
			c = A[i + 3] ^ B[i + 3];
			u = ones ^ b;
			long twosB = ones & b | u & c;
			ones = u ^ c;
			long u = twos ^ twosA;
			long foursA = twos & twosA | u & twosB;
			twos = u ^ twosB;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 4;
		}
		if (i <= n - 2)
		{
			long b = A[i] ^ B[i];
			long c = A[i + 1] ^ B[i + 1];
			long u = ones ^ b;
			long twosA = ones & b | u & c;
			ones = u ^ c;
			long foursA = twos & twosA;
			twos ^= twosA;
			long eights = fours & foursA;
			fours ^= foursA;
			tot8 += pop(eights);
			i += 2;
		}
		if (i < n)
			tot += pop(A[i] ^ B[i]);
		tot += (long)((pop(fours) << 2) + (pop(twos) << 1) + pop(ones)) + (tot8 << 3);
		return tot;
	}

	public static int ntz(long val)
	{
		int lower = (int)val;
		int lowByte = lower & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte];
		if (lower != 0)
		{
			lowByte = lower >>> 8 & 0xff;
			if (lowByte != 0)
				return ntzTable[lowByte] + 8;
			lowByte = lower >>> 16 & 0xff;
			if (lowByte != 0)
				return ntzTable[lowByte] + 16;
			else
				return ntzTable[lower >>> 24] + 24;
		}
		int upper = (int)(val >> 32);
		lowByte = upper & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte] + 32;
		lowByte = upper >>> 8 & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte] + 40;
		lowByte = upper >>> 16 & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte] + 48;
		else
			return ntzTable[upper >>> 24] + 56;
	}

	public static int ntz(int val)
	{
		int lowByte = val & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte];
		lowByte = val >>> 8 & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte] + 8;
		lowByte = val >>> 16 & 0xff;
		if (lowByte != 0)
			return ntzTable[lowByte] + 16;
		else
			return ntzTable[val >>> 24] + 24;
	}

	public static int ntz2(long x)
	{
		int n = 0;
		int y = (int)x;
		if (y == 0)
		{
			n += 32;
			y = (int)(x >>> 32);
		}
		if ((y & 0xffff) == 0)
		{
			n += 16;
			y >>>= 16;
		}
		if ((y & 0xff) == 0)
		{
			n += 8;
			y >>>= 8;
		}
		return ntzTable[y & 0xff] + n;
	}

	public static int ntz3(long x)
	{
		int n = 1;
		int y = (int)x;
		if (y == 0)
		{
			n += 32;
			y = (int)(x >>> 32);
		}
		if ((y & 0xffff) == 0)
		{
			n += 16;
			y >>>= 16;
		}
		if ((y & 0xff) == 0)
		{
			n += 8;
			y >>>= 8;
		}
		if ((y & 0xf) == 0)
		{
			n += 4;
			y >>>= 4;
		}
		if ((y & 3) == 0)
		{
			n += 2;
			y >>>= 2;
		}
		return n - (y & 1);
	}

	public static int nlz(long x)
	{
		int n = 0;
		int y = (int)(x >>> 32);
		if (y == 0)
		{
			n += 32;
			y = (int)x;
		}
		if ((y & 0xffff0000) == 0)
		{
			n += 16;
			y <<= 16;
		}
		if ((y & 0xff000000) == 0)
		{
			n += 8;
			y <<= 8;
		}
		return n + nlzTable[y >>> 24];
	}

	public static boolean isPowerOfTwo(int v)
	{
		return (v & v - 1) == 0;
	}

	public static boolean isPowerOfTwo(long v)
	{
		return (v & v - 1L) == 0L;
	}

	public static int nextHighestPowerOfTwo(int v)
	{
		v = --v | v >> 1;
		v |= v >> 2;
		v |= v >> 4;
		v |= v >> 8;
		v |= v >> 16;
		return ++v;
	}

	public static long nextHighestPowerOfTwo(long v)
	{
		v--;
		v |= v >> 1;
		v |= v >> 2;
		v |= v >> 4;
		v |= v >> 8;
		v |= v >> 16;
		v |= v >> 32;
		v++;
		return v;
	}

}
