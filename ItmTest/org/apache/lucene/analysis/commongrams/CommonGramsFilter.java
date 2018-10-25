// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonGramsFilter.java

package org.apache.lucene.analysis.commongrams;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

public final class CommonGramsFilter extends TokenFilter
{

	public static final String GRAM_TYPE = "gram";
	private static final char SEPARATOR = 95;
	private final CharArraySet commonWords;
	private final StringBuilder buffer = new StringBuilder();
	private final CharTermAttribute termAttribute = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final OffsetAttribute offsetAttribute = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private final TypeAttribute typeAttribute = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	private final PositionIncrementAttribute posIncAttribute = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
	private final PositionLengthAttribute posLenAttribute = (PositionLengthAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionLengthAttribute);
	private int lastStartOffset;
	private boolean lastWasCommon;
	private org.apache.lucene.util.AttributeSource.State savedState;

	public CommonGramsFilter(Version matchVersion, TokenStream input, CharArraySet commonWords)
	{
		super(input);
		this.commonWords = commonWords;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (savedState != null)
		{
			restoreState(savedState);
			savedState = null;
			saveTermBuffer();
			return true;
		}
		if (!input.incrementToken())
			return false;
		if (lastWasCommon || isCommon() && buffer.length() > 0)
		{
			savedState = captureState();
			gramToken();
			return true;
		} else
		{
			saveTermBuffer();
			return true;
		}
	}

	public void reset()
		throws IOException
	{
		super.reset();
		lastWasCommon = false;
		savedState = null;
		buffer.setLength(0);
	}

	private boolean isCommon()
	{
		return commonWords != null && commonWords.contains(termAttribute.buffer(), 0, termAttribute.length());
	}

	private void saveTermBuffer()
	{
		buffer.setLength(0);
		buffer.append(termAttribute.buffer(), 0, termAttribute.length());
		buffer.append('_');
		lastStartOffset = offsetAttribute.startOffset();
		lastWasCommon = isCommon();
	}

	private void gramToken()
	{
		buffer.append(termAttribute.buffer(), 0, termAttribute.length());
		int endOffset = offsetAttribute.endOffset();
		clearAttributes();
		int length = buffer.length();
		char termText[] = termAttribute.buffer();
		if (length > termText.length)
			termText = termAttribute.resizeBuffer(length);
		buffer.getChars(0, length, termText, 0);
		termAttribute.setLength(length);
		posIncAttribute.setPositionIncrement(0);
		posLenAttribute.setPositionLength(2);
		offsetAttribute.setOffset(lastStartOffset, endOffset);
		typeAttribute.setType("gram");
		buffer.setLength(0);
	}
}
