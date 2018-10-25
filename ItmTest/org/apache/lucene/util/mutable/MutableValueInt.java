// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueInt.java

package org.apache.lucene.util.mutable;


// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValue

public class MutableValueInt extends MutableValue
{

	public int value;

	public MutableValueInt()
	{
	}

	public Object toObject()
	{
		return exists ? Integer.valueOf(value) : null;
	}

	public void copy(MutableValue source)
	{
		MutableValueInt s = (MutableValueInt)source;
		value = s.value;
		exists = s.exists;
	}

	public MutableValue duplicate()
	{
		MutableValueInt v = new MutableValueInt();
		v.value = value;
		v.exists = exists;
		return v;
	}

	public boolean equalsSameType(Object other)
	{
		MutableValueInt b = (MutableValueInt)other;
		return value == b.value && exists == b.exists;
	}

	public int compareSameType(Object other)
	{
		MutableValueInt b = (MutableValueInt)other;
		int ai = value;
		int bi = b.value;
		if (ai < bi)
			return -1;
		if (ai > bi)
			return 1;
		if (exists == b.exists)
			return 0;
		else
			return exists ? 1 : -1;
	}

	public int hashCode()
	{
		return (value >> 8) + (value >> 16);
	}
}
