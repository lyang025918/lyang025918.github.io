// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConstNumberSource.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.ValueSource;

public abstract class ConstNumberSource extends ValueSource
{

	public ConstNumberSource()
	{
	}

	public abstract int getInt();

	public abstract long getLong();

	public abstract float getFloat();

	public abstract double getDouble();

	public abstract Number getNumber();

	public abstract boolean getBool();
}
