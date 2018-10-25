// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GalicianStemFilter.java

package org.apache.lucene.analysis.gl;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.gl:
//			GalicianStemmer

public final class GalicianStemFilter extends TokenFilter
{

	private final GalicianStemmer stemmer = new GalicianStemmer();
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public GalicianStemFilter(TokenStream input)
	{
		super(input);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (!keywordAttr.isKeyword())
			{
				int len = termAtt.length();
				int newlen = stemmer.stem(termAtt.resizeBuffer(len + 1), len);
				termAtt.setLength(newlen);
			}
			return true;
		} else
		{
			return false;
		}
	}
}
