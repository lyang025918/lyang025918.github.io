// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueStr.java

package org.apache.lucene.util.mutable;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValue

public class MutableValueStr extends MutableValue
{

	public BytesRef value;

	public MutableValueStr()
	{
		value = new BytesRef();
	}

	public Object toObject()
	{
		return exists ? value.utf8ToString() : null;
	}

	public void copy(MutableValue source)
	{
		MutableValueStr s = (MutableValueStr)source;
		exists = s.exists;
		value.copyBytes(s.value);
	}

	public MutableValue duplicate()
	{
		MutableValueStr v = new MutableValueStr();
		v.value.copyBytes(value);
		v.exists = exists;
		return v;
	}

	public boolean equalsSameType(Object other)
	{
		MutableValueStr b = (MutableValueStr)other;
		return value.equals(b.value) && exists == b.exists;
	}

	public int compareSameType(Object other)
	{
		MutableValueStr b = (MutableValueStr)other;
		int c = value.compareTo(b.value);
		if (c != 0)
			return c;
		if (exists == b.exists)
			return 0;
		else
			return exists ? 1 : -1;
	}

	public int hashCode()
	{
		return value.hashCode();
	}
}
