// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SwedishLightStemFilter.java

package org.apache.lucene.analysis.sv;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.sv:
//			SwedishLightStemmer

public final class SwedishLightStemFilter extends TokenFilter
{

	private final SwedishLightStemmer stemmer = new SwedishLightStemmer();
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public SwedishLightStemFilter(TokenStream input)
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
				int newlen = stemmer.stem(termAtt.buffer(), termAtt.length());
				termAtt.setLength(newlen);
			}
			return true;
		} else
		{
			return false;
		}
	}
}
