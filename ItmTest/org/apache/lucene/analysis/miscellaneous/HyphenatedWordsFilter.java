// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HyphenatedWordsFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

public final class HyphenatedWordsFilter extends TokenFilter
{

	private final CharTermAttribute termAttribute = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final OffsetAttribute offsetAttribute = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private final StringBuilder hyphenated = new StringBuilder();
	private org.apache.lucene.util.AttributeSource.State savedState;
	private boolean exhausted;
	private int lastEndOffset;

	public HyphenatedWordsFilter(TokenStream in)
	{
		super(in);
		exhausted = false;
		lastEndOffset = 0;
	}

	public boolean incrementToken()
		throws IOException
	{
		while (!exhausted && input.incrementToken()) 
		{
			char term[] = termAttribute.buffer();
			int termLength = termAttribute.length();
			lastEndOffset = offsetAttribute.endOffset();
			if (termLength > 0 && term[termLength - 1] == '-')
			{
				if (savedState == null)
					savedState = captureState();
				hyphenated.append(term, 0, termLength - 1);
			} else
			if (savedState == null)
			{
				return true;
			} else
			{
				hyphenated.append(term, 0, termLength);
				unhyphenate();
				return true;
			}
		}
		exhausted = true;
		if (savedState != null)
		{
			hyphenated.append('-');
			unhyphenate();
			return true;
		} else
		{
			return false;
		}
	}

	public void reset()
		throws IOException
	{
		super.reset();
		hyphenated.setLength(0);
		savedState = null;
		exhausted = false;
		lastEndOffset = 0;
	}

	private void unhyphenate()
	{
		restoreState(savedState);
		savedState = null;
		char term[] = termAttribute.buffer();
		int length = hyphenated.length();
		if (length > termAttribute.length())
			term = termAttribute.resizeBuffer(length);
		hyphenated.getChars(0, length, term, 0);
		termAttribute.setLength(length);
		offsetAttribute.setOffset(offsetAttribute.startOffset(), lastEndOffset);
		hyphenated.setLength(0);
	}
}
