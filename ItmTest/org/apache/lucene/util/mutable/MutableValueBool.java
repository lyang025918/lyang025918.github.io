// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueBool.java

package org.apache.lucene.util.mutable;


// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValue

public class MutableValueBool extends MutableValue
{

	public boolean value;

	public MutableValueBool()
	{
	}

	public Object toObject()
	{
		return exists ? Boolean.valueOf(value) : null;
	}

	public void copy(MutableValue source)
	{
		MutableValueBool s = (MutableValueBool)source;
		value = s.value;
		exists = s.exists;
	}

	public MutableValue duplicate()
	{
		MutableValueBool v = new MutableValueBool();
		v.value = value;
		v.exists = exists;
		return v;
	}

	public boolean equalsSameType(Object other)
	{
		MutableValueBool b = (MutableValueBool)other;
		return value == b.value && exists == b.exists;
	}

	public int compareSameType(Object other)
	{
		MutableValueBool b = (MutableValueBool)other;
		if (value != b.value)
			return value ? 1 : 0;
		if (exists == b.exists)
			return 0;
		else
			return exists ? 1 : -1;
	}

	public int hashCode()
	{
		return value ? 2 : ((int) (exists ? 1 : 0));
	}
}
