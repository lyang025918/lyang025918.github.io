// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueFloat.java

package org.apache.lucene.util.mutable;


// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValue

public class MutableValueFloat extends MutableValue
{

	public float value;

	public MutableValueFloat()
	{
	}

	public Object toObject()
	{
		return exists ? Float.valueOf(value) : null;
	}

	public void copy(MutableValue source)
	{
		MutableValueFloat s = (MutableValueFloat)source;
		value = s.value;
		exists = s.exists;
	}

	public MutableValue duplicate()
	{
		MutableValueFloat v = new MutableValueFloat();
		v.value = value;
		v.exists = exists;
		return v;
	}

	public boolean equalsSameType(Object other)
	{
		MutableValueFloat b = (MutableValueFloat)other;
		return value == b.value && exists == b.exists;
	}

	public int compareSameType(Object other)
	{
		MutableValueFloat b = (MutableValueFloat)other;
		int c = Float.compare(value, b.value);
		if (c != 0)
			return c;
		if (exists == b.exists)
			return 0;
		else
			return exists ? 1 : -1;
	}

	public int hashCode()
	{
		return Float.floatToIntBits(value);
	}
}
