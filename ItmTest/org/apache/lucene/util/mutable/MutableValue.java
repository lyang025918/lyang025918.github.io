// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValue.java

package org.apache.lucene.util.mutable;


public abstract class MutableValue
	implements Comparable
{

	public boolean exists;

	public MutableValue()
	{
		exists = true;
	}

	public abstract void copy(MutableValue mutablevalue);

	public abstract MutableValue duplicate();

	public abstract boolean equalsSameType(Object obj);

	public abstract int compareSameType(Object obj);

	public abstract Object toObject();

	public boolean exists()
	{
		return exists;
	}

	public int compareTo(MutableValue other)
	{
		Class c1 = getClass();
		Class c2 = other.getClass();
		if (c1 != c2)
		{
			int c = c1.hashCode() - c2.hashCode();
			if (c == 0)
				c = c1.getCanonicalName().compareTo(c2.getCanonicalName());
			return c;
		} else
		{
			return compareSameType(other);
		}
	}

	public boolean equals(Object other)
	{
		return getClass() == other.getClass() && equalsSameType(other);
	}

	public abstract int hashCode();

	public String toString()
	{
		return exists() ? toObject().toString() : "(null)";
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((MutableValue)x0);
	}
}
