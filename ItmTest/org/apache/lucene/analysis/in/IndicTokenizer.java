// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndicTokenizer.java

package org.apache.lucene.analysis.in;

import java.io.Reader;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

/**
 * @deprecated Class IndicTokenizer is deprecated
 */

public final class IndicTokenizer extends CharTokenizer
{

	public IndicTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input)
	{
		super(matchVersion, factory, input);
	}

	public IndicTokenizer(Version matchVersion, AttributeSource source, Reader input)
	{
		super(matchVersion, source, input);
	}

	public IndicTokenizer(Version matchVersion, Reader input)
	{
		super(matchVersion, input);
	}

	protected boolean isTokenChar(int c)
	{
		return Character.isLetter(c) || Character.getType(c) == 6 || Character.getType(c) == 16 || Character.getType(c) == 8;
	}
}
