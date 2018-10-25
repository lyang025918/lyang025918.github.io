// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseCharFilter.java

package org.apache.lucene.analysis.charfilter;

import java.io.Reader;
import java.util.Arrays;
import org.apache.lucene.analysis.CharFilter;
import org.apache.lucene.util.ArrayUtil;

public abstract class BaseCharFilter extends CharFilter
{

	private int offsets[];
	private int diffs[];
	private int size;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/charfilter/BaseCharFilter.desiredAssertionStatus();

	public BaseCharFilter(Reader in)
	{
		super(in);
		size = 0;
	}

	protected int correct(int currentOff)
	{
		if (offsets == null || currentOff < offsets[0])
			return currentOff;
		int hi = size - 1;
		if (currentOff >= offsets[hi])
			return currentOff + diffs[hi];
		int lo = 0;
		int mid = -1;
		while (hi >= lo) 
		{
			mid = lo + hi >>> 1;
			if (currentOff < offsets[mid])
				hi = mid - 1;
			else
			if (currentOff > offsets[mid])
				lo = mid + 1;
			else
				return currentOff + diffs[mid];
		}
		if (currentOff < offsets[mid])
			return mid != 0 ? currentOff + diffs[mid - 1] : currentOff;
		else
			return currentOff + diffs[mid];
	}

	protected int getLastCumulativeDiff()
	{
		return offsets != null ? diffs[size - 1] : 0;
	}

	protected void addOffCorrectMap(int off, int cumulativeDiff)
	{
		if (offsets == null)
		{
			offsets = new int[64];
			diffs = new int[64];
		} else
		if (size == offsets.length)
		{
			offsets = ArrayUtil.grow(offsets);
			diffs = ArrayUtil.grow(diffs);
		}
		if (!$assertionsDisabled && size != 0 && off < offsets[size - 1])
			throw new AssertionError((new StringBuilder()).append("Offset #").append(size).append("(").append(off).append(") is less than the last recorded offset ").append(offsets[size - 1]).append("\n").append(Arrays.toString(offsets)).append("\n").append(Arrays.toString(diffs)).toString());
		if (size == 0 || off != offsets[size - 1])
		{
			offsets[size] = off;
			diffs[size++] = cumulativeDiff;
		} else
		{
			diffs[size - 1] = cumulativeDiff;
		}
	}

}
