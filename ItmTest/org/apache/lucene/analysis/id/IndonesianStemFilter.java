// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndonesianStemFilter.java

package org.apache.lucene.analysis.id;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.id:
//			IndonesianStemmer

public final class IndonesianStemFilter extends TokenFilter
{

	private final CharTermAttribute termAtt;
	private final KeywordAttribute keywordAtt;
	private final IndonesianStemmer stemmer;
	private final boolean stemDerivational;

	public IndonesianStemFilter(TokenStream input)
	{
		this(input, true);
	}

	public IndonesianStemFilter(TokenStream input, boolean stemDerivational)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		keywordAtt = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);
		stemmer = new IndonesianStemmer();
		this.stemDerivational = stemDerivational;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (!keywordAtt.isKeyword())
			{
				int newlen = stemmer.stem(termAtt.buffer(), termAtt.length(), stemDerivational);
				termAtt.setLength(newlen);
			}
			return true;
		} else
		{
			return false;
		}
	}
}
