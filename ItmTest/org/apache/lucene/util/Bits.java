// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Bits.java

package org.apache.lucene.util;


public interface Bits
{
	public static class MatchNoBits
		implements Bits
	{

		final int len;

		public boolean get(int index)
		{
			return false;
		}

		public int length()
		{
			return len;
		}

		public MatchNoBits(int len)
		{
			this.len = len;
		}
	}

	public static class MatchAllBits
		implements Bits
	{

		final int len;

		public boolean get(int index)
		{
			return true;
		}

		public int length()
		{
			return len;
		}

		public MatchAllBits(int len)
		{
			this.len = len;
		}
	}


	public static final Bits EMPTY_ARRAY[] = new Bits[0];

	public abstract boolean get(int i);

	public abstract int length();

}
