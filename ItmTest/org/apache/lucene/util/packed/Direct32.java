// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Direct32.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class Direct32 extends PackedInts.MutableImpl
{

	final int values[];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Direct32.desiredAssertionStatus();

	Direct32(int valueCount)
	{
		super(valueCount, 32);
		values = new int[valueCount];
	}

	Direct32(DataInput in, int valueCount)
		throws IOException
	{
		this(valueCount);
		for (int i = 0; i < valueCount; i++)
			values[i] = in.readInt();

		int mod = valueCount % 2;
		if (mod != 0)
		{
			for (int i = mod; i < 2; i++)
				in.readInt();

		}
	}

	public long get(int index)
	{
		return (long)values[index] & 0xffffffffL;
	}

	public void set(int index, long value)
	{
		values[index] = (int)value;
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(values);
	}

	public void clear()
	{
		Arrays.fill(values, 0);
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
			throw new AssertionError();
		int gets = Math.min(valueCount - index, len);
		int i = index;
		int o = off;
		for (int end = index + gets; i < end;)
		{
			arr[o] = (long)values[i] & 0xffffffffL;
			i++;
			o++;
		}

		return gets;
	}

	public int set(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		if (!$assertionsDisabled && off + len > arr.length)
			throw new AssertionError();
		int sets = Math.min(valueCount - index, len);
		int i = index;
		int o = off;
		for (int end = index + sets; i < end;)
		{
			values[i] = (int)arr[o];
			i++;
			o++;
		}

		return sets;
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		if (!$assertionsDisabled && val != (val & 0xffffffffL))
		{
			throw new AssertionError();
		} else
		{
			Arrays.fill(values, fromIndex, toIndex, (int)val);
			return;
		}
	}

}
