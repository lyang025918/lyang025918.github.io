// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HunspellWord.java

package org.apache.lucene.analysis.hunspell;

import java.util.Arrays;

public class HunspellWord
{

	private final char flags[];

	public HunspellWord()
	{
		flags = null;
	}

	public HunspellWord(char flags[])
	{
		this.flags = flags;
	}

	public boolean hasFlag(char flag)
	{
		return flags != null && Arrays.binarySearch(flags, flag) >= 0;
	}

	public char[] getFlags()
	{
		return flags;
	}
}
