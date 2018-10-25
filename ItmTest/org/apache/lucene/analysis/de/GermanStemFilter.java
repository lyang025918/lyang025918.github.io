// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanStemFilter.java

package org.apache.lucene.analysis.de;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.de:
//			GermanStemmer

public final class GermanStemFilter extends TokenFilter
{

	private GermanStemmer stemmer;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public GermanStemFilter(TokenStream in)
	{
		super(in);
		stemmer = new GermanStemmer();
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			String term = termAtt.toString();
			if (!keywordAttr.isKeyword())
			{
				String s = stemmer.stem(term);
				if (s != null && !s.equals(term))
					termAtt.setEmpty().append(s);
			}
			return true;
		} else
		{
			return false;
		}
	}

	public void setStemmer(GermanStemmer stemmer)
	{
		if (stemmer != null)
			this.stemmer = stemmer;
	}
}
