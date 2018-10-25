// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Direct64.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class Direct64 extends PackedInts.MutableImpl
{

	final long values[];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Direct64.desiredAssertionStatus();

	Direct64(int valueCount)
	{
		super(valueCount, 64);
		values = new long[valueCount];
	}

	Direct64(DataInput in, int valueCount)
		throws IOException
	{
		this(valueCount);
		for (int i = 0; i < valueCount; i++)
			values[i] = in.readLong();

	}

	public long get(int index)
	{
		return values[index];
	}

	public void set(int index, long value)
	{
		values[index] = value;
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(values);
	}

	public void clear()
	{
		Arrays.fill(values, 0L);
	}

	public Object getArray()
	{
		return values;
	}

	public boolean hasArray()
	{
		return true;
	}

	public int get(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		if (!$assertionsDisabled && off + len > arr.length)
		{
			throw new AssertionError();
		} else
		{
			int gets = Math.min(valueCount - index, len);
			System.arraycopy(values, index, arr, off, gets);
			return gets;
		}
	}

	public int set(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		if (!$assertionsDisabled && off + len > arr.length)
		{
			throw new AssertionError();
		} else
		{
			int sets = Math.min(valueCount - index, len);
			System.arraycopy(arr, off, values, index, sets);
			return sets;
		}
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		Arrays.fill(values, fromIndex, toIndex, val);
	}

}
