// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GrowableWriter.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

public class GrowableWriter
	implements PackedInts.Mutable
{

	private long currentMaxValue;
	private PackedInts.Mutable current;
	private final float acceptableOverheadRatio;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/GrowableWriter.desiredAssertionStatus();

	public GrowableWriter(int startBitsPerValue, int valueCount, float acceptableOverheadRatio)
	{
		this.acceptableOverheadRatio = acceptableOverheadRatio;
		current = PackedInts.getMutable(valueCount, startBitsPerValue, this.acceptableOverheadRatio);
		currentMaxValue = PackedInts.maxValue(current.getBitsPerValue());
	}

	public long get(int index)
	{
		return current.get(index);
	}

	public int size()
	{
		return current.size();
	}

	public int getBitsPerValue()
	{
		return current.getBitsPerValue();
	}

	public PackedInts.Mutable getMutable()
	{
		return current;
	}

	public Object getArray()
	{
		return current.getArray();
	}

	public boolean hasArray()
	{
		return current.hasArray();
	}

	private void ensureCapacity(long value)
	{
		if (!$assertionsDisabled && value < 0L)
			throw new AssertionError();
		if (value <= currentMaxValue)
		{
			return;
		} else
		{
			int bitsRequired = PackedInts.bitsRequired(value);
			int valueCount = size();
			PackedInts.Mutable next = PackedInts.getMutable(valueCount, bitsRequired, acceptableOverheadRatio);
			PackedInts.copy(current, 0, next, 0, valueCount, 1024);
			current = next;
			currentMaxValue = PackedInts.maxValue(current.getBitsPerValue());
			return;
		}
	}

	public void set(int index, long value)
	{
		ensureCapacity(value);
		current.set(index, value);
	}

	public void clear()
	{
		current.clear();
	}

	public GrowableWriter resize(int newSize)
	{
		GrowableWriter next = new GrowableWriter(getBitsPerValue(), newSize, acceptableOverheadRatio);
		int limit = Math.min(size(), newSize);
		PackedInts.copy(current, 0, next, 0, limit, 1024);
		return next;
	}

	public int get(int index, long arr[], int off, int len)
	{
		return current.get(index, arr, off, len);
	}

	public int set(int index, long arr[], int off, int len)
	{
		long max = 0L;
		int i = off;
		for (int end = off + len; i < end; i++)
			max |= arr[i];

		ensureCapacity(max);
		return current.set(index, arr, off, len);
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		ensureCapacity(val);
		current.fill(fromIndex, toIndex, val);
	}

	public long ramBytesUsed()
	{
		return current.ramBytesUsed();
	}

	public void save(DataOutput out)
		throws IOException
	{
		current.save(out);
	}

}
