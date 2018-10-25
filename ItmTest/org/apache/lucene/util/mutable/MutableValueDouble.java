// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueDouble.java

package org.apache.lucene.util.mutable;


// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValue

public class MutableValueDouble extends MutableValue
{

	public double value;

	public MutableValueDouble()
	{
	}

	public Object toObject()
	{
		return exists ? Double.valueOf(value) : null;
	}

	public void copy(MutableValue source)
	{
		MutableValueDouble s = (MutableValueDouble)source;
		value = s.value;
		exists = s.exists;
	}

	public MutableValue duplicate()
	{
		MutableValueDouble v = new MutableValueDouble();
		v.value = value;
		v.exists = exists;
		return v;
	}

	public boolean equalsSameType(Object other)
	{
		MutableValueDouble b = (MutableValueDouble)other;
		return value == b.value && exists == b.exists;
	}

	public int compareSameType(Object other)
	{
		MutableValueDouble b = (MutableValueDouble)other;
		int c = Double.compare(value, b.value);
		if (c != 0)
			return c;
		if (!exists)
			return -1;
		return b.exists ? 0 : 1;
	}

	public int hashCode()
	{
		long x = Double.doubleToLongBits(value);
		return (int)x + (int)(x >>> 32);
	}
}
