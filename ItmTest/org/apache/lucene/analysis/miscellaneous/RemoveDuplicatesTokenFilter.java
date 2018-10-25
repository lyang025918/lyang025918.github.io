// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RemoveDuplicatesTokenFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public final class RemoveDuplicatesTokenFilter extends TokenFilter
{

	private final CharTermAttribute termAttribute = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final PositionIncrementAttribute posIncAttribute = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
	private final CharArraySet previous;

	public RemoveDuplicatesTokenFilter(TokenStream in)
	{
		super(in);
		previous = new CharArraySet(Version.LUCENE_31, 8, false);
	}

	public boolean incrementToken()
		throws IOException
	{
		while (input.incrementToken()) 
		{
			char term[] = termAttribute.buffer();
			int length = termAttribute.length();
			int posIncrement = posIncAttribute.getPositionIncrement();
			if (posIncrement > 0)
				previous.clear();
			boolean duplicate = posIncrement == 0 && previous.contains(term, 0, length);
			char saved[] = new char[length];
			System.arraycopy(term, 0, saved, 0, length);
			previous.add(saved);
			if (!duplicate)
				return true;
		}
		return false;
	}

	public void reset()
		throws IOException
	{
		super.reset();
		previous.clear();
	}
}
