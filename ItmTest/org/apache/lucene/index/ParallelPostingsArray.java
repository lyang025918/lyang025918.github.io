// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParallelPostingsArray.java

package org.apache.lucene.index;

import org.apache.lucene.util.ArrayUtil;

class ParallelPostingsArray
{

	static final int BYTES_PER_POSTING = 12;
	final int size;
	final int textStarts[];
	final int intStarts[];
	final int byteStarts[];

	ParallelPostingsArray(int size)
	{
		this.size = size;
		textStarts = new int[size];
		intStarts = new int[size];
		byteStarts = new int[size];
	}

	int bytesPerPosting()
	{
		return 12;
	}

	ParallelPostingsArray newInstance(int size)
	{
		return new ParallelPostingsArray(size);
	}

	final ParallelPostingsArray grow()
	{
		int newSize = ArrayUtil.oversize(size + 1, bytesPerPosting());
		ParallelPostingsArray newArray = newInstance(newSize);
		copyTo(newArray, size);
		return newArray;
	}

	void copyTo(ParallelPostingsArray toArray, int numToCopy)
	{
		System.arraycopy(textStarts, 0, toArray.textStarts, 0, numToCopy);
		System.arraycopy(intStarts, 0, toArray.intStarts, 0, numToCopy);
		System.arraycopy(byteStarts, 0, toArray.byteStarts, 0, numToCopy);
	}
}
