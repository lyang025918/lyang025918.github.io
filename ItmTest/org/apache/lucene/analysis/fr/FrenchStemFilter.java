// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchStemFilter.java

package org.apache.lucene.analysis.fr;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.fr:
//			FrenchStemmer

/**
 * @deprecated Class FrenchStemFilter is deprecated
 */

public final class FrenchStemFilter extends TokenFilter
{

	private FrenchStemmer stemmer;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public FrenchStemFilter(TokenStream in)
	{
		super(in);
		stemmer = new FrenchStemmer();
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

	public void setStemmer(FrenchStemmer stemmer)
	{
		if (stemmer != null)
			this.stemmer = stemmer;
	}
}
