// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicLetterTokenizer.java

package org.apache.lucene.analysis.ar;

import java.io.Reader;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

/**
 * @deprecated Class ArabicLetterTokenizer is deprecated
 */

public class ArabicLetterTokenizer extends LetterTokenizer
{

	public ArabicLetterTokenizer(Version matchVersion, Reader in)
	{
		super(matchVersion, in);
	}

	public ArabicLetterTokenizer(Version matchVersion, AttributeSource source, Reader in)
	{
		super(matchVersion, source, in);
	}

	public ArabicLetterTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader in)
	{
		super(matchVersion, factory, in);
	}

	protected boolean isTokenChar(int c)
	{
		return super.isTokenChar(c) || Character.getType(c) == 6;
	}
}
