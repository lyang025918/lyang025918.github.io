// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WhitespaceTokenizer.java

package org.apache.lucene.analysis.core;

import java.io.Reader;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

public final class WhitespaceTokenizer extends CharTokenizer
{

	public WhitespaceTokenizer(Version matchVersion, Reader in)
	{
		super(matchVersion, in);
	}

	public WhitespaceTokenizer(Version matchVersion, AttributeSource source, Reader in)
	{
		super(matchVersion, source, in);
	}

	public WhitespaceTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader in)
	{
		super(matchVersion, factory, in);
	}

	protected boolean isTokenChar(int c)
	{
		return !Character.isWhitespace(c);
	}
}
