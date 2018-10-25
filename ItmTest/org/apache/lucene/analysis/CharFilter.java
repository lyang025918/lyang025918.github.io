// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharFilter.java

package org.apache.lucene.analysis;

import java.io.IOException;
import java.io.Reader;

public abstract class CharFilter extends Reader
{

	protected final Reader input;

	public CharFilter(Reader input)
	{
		super(input);
		this.input = input;
	}

	public void close()
		throws IOException
	{
		input.close();
	}

	protected abstract int correct(int i);

	public final int correctOffset(int currentOff)
	{
		int corrected = correct(currentOff);
		return (input instanceof CharFilter) ? ((CharFilter)input).correctOffset(corrected) : corrected;
	}
}
