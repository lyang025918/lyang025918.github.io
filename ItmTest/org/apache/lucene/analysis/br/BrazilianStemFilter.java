// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BrazilianStemFilter.java

package org.apache.lucene.analysis.br;

import java.io.IOException;
import java.util.Set;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.br:
//			BrazilianStemmer

public final class BrazilianStemFilter extends TokenFilter
{

	private BrazilianStemmer stemmer;
	private Set exclusions;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public BrazilianStemFilter(TokenStream in)
	{
		super(in);
		stemmer = new BrazilianStemmer();
		exclusions = null;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			String term = termAtt.toString();
			if (!keywordAttr.isKeyword() && (exclusions == null || !exclusions.contains(term)))
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
}
