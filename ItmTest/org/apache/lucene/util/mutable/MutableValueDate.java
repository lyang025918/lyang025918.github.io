// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutableValueDate.java

package org.apache.lucene.util.mutable;

import java.util.Date;

// Referenced classes of package org.apache.lucene.util.mutable:
//			MutableValueLong, MutableValue

public class MutableValueDate extends MutableValueLong
{

	public MutableValueDate()
	{
	}

	public Object toObject()
	{
		return exists ? new Date(value) : null;
	}

	public MutableValue duplicate()
	{
		MutableValueDate v = new MutableValueDate();
		v.value = value;
		v.exists = exists;
		return v;
	}
}
