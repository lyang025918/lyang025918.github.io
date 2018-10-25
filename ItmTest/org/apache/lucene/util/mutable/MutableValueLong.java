// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueLong.java

package org.apache.lucene.util.mutable;


// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValue

public class MutableValueLong extends MutableValue
{

	public long value;

	public MutableValueLong()
	{
	}

	public Object toObject()
	{
		return exists ? Long.valueOf(value) : null;
	}

	public void copy(MutableValue source)
	{
		MutableValueLong s = (MutableValueLong)source;
		exists = s.exists;
		value = s.value;
	}

	public MutableValue duplicate()
	{
		MutableValueLong v = new MutableValueLong();
		v.value = value;
		v.exists = exists;
		return v;
	}

	public boolean equalsSameType(Object other)
	{
		MutableValueLong b = (MutableValueLong)other;
		return value == b.value && exists == b.exists;
	}

	public int compareSameType(Object other)
	{
		MutableValueLong b = (MutableValueLong)other;
		long bv = b.value;
		if (value < bv)
			return -1;
		if (value > bv)
			return 1;
		if (exists == b.exists)
			return 0;
		else
			return exists ? 1 : -1;
	}

	public int hashCode()
	{
		return (int)value + (int)(value >> 32);
	}
}
