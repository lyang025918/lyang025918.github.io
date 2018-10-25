// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HunspellStemFilter.java

package org.apache.lucene.analysis.hunspell;

import java.io.IOException;
import java.util.List;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.hunspell:
//			HunspellStemmer, HunspellDictionary

public final class HunspellStemFilter extends TokenFilter
{

	private final CharTermAttribute termAtt;
	private final PositionIncrementAttribute posIncAtt;
	private final KeywordAttribute keywordAtt;
	private final HunspellStemmer stemmer;
	private List buffer;
	private org.apache.lucene.util.AttributeSource.State savedState;
	private final boolean dedup;

	public HunspellStemFilter(TokenStream input, HunspellDictionary dictionary)
	{
		this(input, dictionary, true);
	}

	public HunspellStemFilter(TokenStream input, HunspellDictionary dictionary, boolean dedup)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		posIncAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		keywordAtt = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);
		this.dedup = dedup;
		stemmer = new HunspellStemmer(dictionary);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (buffer != null && !buffer.isEmpty())
		{
			HunspellStemmer.Stem nextStem = (HunspellStemmer.Stem)buffer.remove(0);
			restoreState(savedState);
			posIncAtt.setPositionIncrement(0);
			termAtt.copyBuffer(nextStem.getStem(), 0, nextStem.getStemLength());
			termAtt.setLength(nextStem.getStemLength());
			return true;
		}
		if (!input.incrementToken())
			return false;
		if (keywordAtt.isKeyword())
			return true;
		buffer = dedup ? stemmer.uniqueStems(termAtt.buffer(), termAtt.length()) : stemmer.stem(termAtt.buffer(), termAtt.length());
		if (buffer.isEmpty())
			return true;
		HunspellStemmer.Stem stem = (HunspellStemmer.Stem)buffer.remove(0);
		termAtt.copyBuffer(stem.getStem(), 0, stem.getStemLength());
		termAtt.setLength(stem.getStemLength());
		if (!buffer.isEmpty())
			savedState = captureState();
		return true;
	}

	public void reset()
		throws IOException
	{
		super.reset();
		buffer = null;
	}
}
