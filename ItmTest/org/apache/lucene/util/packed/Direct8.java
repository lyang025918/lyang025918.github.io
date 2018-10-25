// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Direct8.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class Direct8 extends PackedInts.MutableImpl
{

	final byte values[];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Direct8.desiredAssertionStatus();

	Direct8(int valueCount)
	{
		super(valueCount, 8);
		values = new byte[valueCount];
	}

	Direct8(DataInput in, int valueCount)
		throws IOException
	{
		this(valueCount);
		for (int i = 0; i < valueCount; i++)
			values[i] = in.readByte();

		int mod = valueCount % 8;
		if (mod != 0)
		{
			for (int i = mod; i < 8; i++)
				in.readByte();

		}
	}

	public long get(int index)
	{
		return (long)values[index] & 255L;
	}

	public void set(int index, long value)
	{
		values[index] = (byte)(int)value;
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(values);
	}

	public void clear()
	{
		Arrays.fill(values, (byte)0);
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
			arr[o] = (long)values[i] & 255L;
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
			values[i] = (byte)(int)arr[o];
			i++;
			o++;
		}

		return sets;
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		if (!$assertionsDisabled && val != (val & 255L))
		{
			throw new AssertionError();
		} else
		{
			Arrays.fill(values, fromIndex, toIndex, (byte)(int)val);
			return;
		}
	}

}
