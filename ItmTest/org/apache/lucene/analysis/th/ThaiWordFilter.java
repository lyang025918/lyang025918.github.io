// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThaiWordFilter.java

package org.apache.lucene.analysis.th;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Locale;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.analysis.util.CharArrayIterator;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

public final class ThaiWordFilter extends TokenFilter
{

	public static final boolean DBBI_AVAILABLE;
	private static final BreakIterator proto;
	private final BreakIterator breaker;
	private final CharArrayIterator charIterator = CharArrayIterator.newWordInstance();
	private final boolean handlePosIncr;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private final PositionIncrementAttribute posAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
	private AttributeSource clonedToken;
	private CharTermAttribute clonedTermAtt;
	private OffsetAttribute clonedOffsetAtt;
	private boolean hasMoreTokensInClone;
	private boolean hasIllegalOffsets;

	public ThaiWordFilter(Version matchVersion, TokenStream input)
	{
		super(((TokenStream) (matchVersion.onOrAfter(Version.LUCENE_31) ? input : ((TokenStream) (new LowerCaseFilter(matchVersion, input))))));
		breaker = (BreakIterator)proto.clone();
		clonedToken = null;
		clonedTermAtt = null;
		clonedOffsetAtt = null;
		hasMoreTokensInClone = false;
		hasIllegalOffsets = false;
		if (!DBBI_AVAILABLE)
		{
			throw new UnsupportedOperationException("This JRE does not have support for Thai segmentation");
		} else
		{
			handlePosIncr = matchVersion.onOrAfter(Version.LUCENE_31);
			return;
		}
	}

	public boolean incrementToken()
		throws IOException
	{
		if (hasMoreTokensInClone)
		{
			int start = breaker.current();
			int end = breaker.next();
			if (end != -1)
			{
				clonedToken.copyTo(this);
				termAtt.copyBuffer(clonedTermAtt.buffer(), start, end - start);
				if (hasIllegalOffsets)
					offsetAtt.setOffset(clonedOffsetAtt.startOffset(), clonedOffsetAtt.endOffset());
				else
					offsetAtt.setOffset(clonedOffsetAtt.startOffset() + start, clonedOffsetAtt.startOffset() + end);
				if (handlePosIncr)
					posAtt.setPositionIncrement(1);
				return true;
			}
			hasMoreTokensInClone = false;
		}
		if (!input.incrementToken())
			return false;
		if (termAtt.length() == 0 || Character.UnicodeBlock.of(termAtt.charAt(0)) != Character.UnicodeBlock.THAI)
			return true;
		hasMoreTokensInClone = true;
		hasIllegalOffsets = offsetAtt.endOffset() - offsetAtt.startOffset() != termAtt.length();
		if (clonedToken == null)
		{
			clonedToken = cloneAttributes();
			clonedTermAtt = (CharTermAttribute)clonedToken.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			clonedOffsetAtt = (OffsetAttribute)clonedToken.getAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		} else
		{
			copyTo(clonedToken);
		}
		charIterator.setText(clonedTermAtt.buffer(), 0, clonedTermAtt.length());
		breaker.setText(charIterator);
		int end = breaker.next();
		if (end != -1)
		{
			termAtt.setLength(end);
			if (hasIllegalOffsets)
				offsetAtt.setOffset(clonedOffsetAtt.startOffset(), clonedOffsetAtt.endOffset());
			else
				offsetAtt.setOffset(clonedOffsetAtt.startOffset(), clonedOffsetAtt.startOffset() + end);
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
		hasMoreTokensInClone = false;
		clonedToken = null;
		clonedTermAtt = null;
		clonedOffsetAtt = null;
	}

	static 
	{
		proto = BreakIterator.getWordInstance(new Locale("th"));
		proto.setText("???????");
		DBBI_AVAILABLE = proto.isBoundary(4);
	}
}
