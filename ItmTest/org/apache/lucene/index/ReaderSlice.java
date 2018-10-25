// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReaderSlice.java

package org.apache.lucene.index;


public final class ReaderSlice
{

	public static final ReaderSlice EMPTY_ARRAY[] = new ReaderSlice[0];
	public final int start;
	public final int length;
	public final int readerIndex;

	public ReaderSlice(int start, int length, int readerIndex)
	{
		this.start = start;
		this.length = length;
		this.readerIndex = readerIndex;
	}

	public String toString()
	{
		return (new StringBuilder()).append("slice start=").append(start).append(" length=").append(length).append(" readerIndex=").append(readerIndex).toString();
	}

}
