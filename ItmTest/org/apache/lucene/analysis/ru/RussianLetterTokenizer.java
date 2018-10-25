// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RussianLetterTokenizer.java

package org.apache.lucene.analysis.ru;

import java.io.Reader;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

/**
 * @deprecated Class RussianLetterTokenizer is deprecated
 */

public class RussianLetterTokenizer extends CharTokenizer
{

	private static final int DIGIT_0 = 48;
	private static final int DIGIT_9 = 57;

	public RussianLetterTokenizer(Version matchVersion, Reader in)
	{
		super(matchVersion, in);
	}

	public RussianLetterTokenizer(Version matchVersion, AttributeSource source, Reader in)
	{
		super(matchVersion, source, in);
	}

	public RussianLetterTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader in)
	{
		super(matchVersion, factory, in);
	}

	protected boolean isTokenChar(int c)
	{
		return Character.isLetter(c) || c >= 48 && c <= 57;
	}
}
