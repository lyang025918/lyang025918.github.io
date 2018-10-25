// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Direct16.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class Direct16 extends PackedInts.MutableImpl
{

	final short values[];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Direct16.desiredAssertionStatus();

	Direct16(int valueCount)
	{
		super(valueCount, 16);
		values = new short[valueCount];
	}

	Direct16(DataInput in, int valueCount)
		throws IOException
	{
		this(valueCount);
		for (int i = 0; i < valueCount; i++)
			values[i] = in.readShort();

		int mod = valueCount % 4;
		if (mod != 0)
		{
			for (int i = mod; i < 4; i++)
				in.readShort();

		}
	}

	public long get(int index)
	{
		return (long)values[index] & 65535L;
	}

	public void set(int index, long value)
	{
		values[index] = (short)(int)value;
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(values);
	}

	public void clear()
	{
		Arrays.fill(values, (short)0);
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
			arr[o] = (long)values[i] & 65535L;
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
			values[i] = (short)(int)arr[o];
			i++;
			o++;
		}

		return sets;
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		if (!$assertionsDisabled && val != (val & 65535L))
		{
			throw new AssertionError();
		} else
		{
			Arrays.fill(values, fromIndex, toIndex, (short)(int)val);
			return;
		}
	}

}
