// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiValueSource.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.ValueSource;

public abstract class MultiValueSource extends ValueSource
{

	public MultiValueSource()
	{
	}

	public abstract int dimension();
}
