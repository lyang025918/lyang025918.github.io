// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompoundWordTokenFilterBase.java

package org.apache.lucene.analysis.compound;

import java.io.IOException;
import java.util.LinkedList;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

public abstract class CompoundWordTokenFilterBase extends TokenFilter
{
	protected class CompoundToken
	{

		public final CharSequence txt;
		public final int startOffset;
		public final int endOffset;
		final CompoundWordTokenFilterBase this$0;

		public CompoundToken(int offset, int length)
		{
			this$0 = CompoundWordTokenFilterBase.this;
			super();
			txt = termAtt.subSequence(offset, offset + length);
			int startOff = offsetAtt.startOffset();
			int endOff = offsetAtt.endOffset();
			if (endOff - startOff != termAtt.length())
			{
				startOffset = startOff;
				endOffset = endOff;
			} else
			{
				int newStart = startOff + offset;
				startOffset = newStart;
				endOffset = newStart + length;
			}
		}
	}


	public static final int DEFAULT_MIN_WORD_SIZE = 5;
	public static final int DEFAULT_MIN_SUBWORD_SIZE = 2;
	public static final int DEFAULT_MAX_SUBWORD_SIZE = 15;
	protected final CharArraySet dictionary;
	protected final LinkedList tokens;
	protected final int minWordSize;
	protected final int minSubwordSize;
	protected final int maxSubwordSize;
	protected final boolean onlyLongestMatch;
	protected final CharTermAttribute termAtt;
	protected final OffsetAttribute offsetAtt;
	private final PositionIncrementAttribute posIncAtt;
	private org.apache.lucene.util.AttributeSource.State current;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/compound/CompoundWordTokenFilterBase.desiredAssertionStatus();

	protected CompoundWordTokenFilterBase(Version matchVersion, TokenStream input, CharArraySet dictionary, boolean onlyLongestMatch)
	{
		this(matchVersion, input, dictionary, 5, 2, 15, onlyLongestMatch);
	}

	protected CompoundWordTokenFilterBase(Version matchVersion, TokenStream input, CharArraySet dictionary)
	{
		this(matchVersion, input, dictionary, 5, 2, 15, false);
	}

	protected CompoundWordTokenFilterBase(Version matchVersion, TokenStream input, CharArraySet dictionary, int minWordSize, int minSubwordSize, int maxSubwordSize, boolean onlyLongestMatch)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		tokens = new LinkedList();
		if (minWordSize < 0)
			throw new IllegalArgumentException("minWordSize cannot be negative");
		this.minWordSize = minWordSize;
		if (minSubwordSize < 0)
			throw new IllegalArgumentException("minSubwordSize cannot be negative");
		this.minSubwordSize = minSubwordSize;
		if (maxSubwordSize < 0)
		{
			throw new IllegalArgumentException("maxSubwordSize cannot be negative");
		} else
		{
			this.maxSubwordSize = maxSubwordSize;
			this.onlyLongestMatch = onlyLongestMatch;
			this.dictionary = dictionary;
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (!tokens.isEmpty())
			if (!$assertionsDisabled && current == null)
			{
				throw new AssertionError();
			} else
			{
				CompoundToken token = (CompoundToken)tokens.removeFirst();
				restoreState(current);
				termAtt.setEmpty().append(token.txt);
				offsetAtt.setOffset(token.startOffset, token.endOffset);
				posIncAtt.setPositionIncrement(0);
				return true;
			}
		current = null;
		if (input.incrementToken())
		{
			if (termAtt.length() >= minWordSize)
			{
				decompose();
				if (!tokens.isEmpty())
					current = captureState();
			}
			return true;
		} else
		{
			return false;
		}
	}

	protected abstract void decompose();

	public void reset()
		throws IOException
	{
		super.reset();
		tokens.clear();
		current = null;
	}

}
