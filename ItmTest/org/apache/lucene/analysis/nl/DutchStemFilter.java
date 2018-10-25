// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DutchStemFilter.java

package org.apache.lucene.analysis.nl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.nl:
//			DutchStemmer

/**
 * @deprecated Class DutchStemFilter is deprecated
 */

public final class DutchStemFilter extends TokenFilter
{

	private DutchStemmer stemmer;
	private final CharTermAttribute termAtt;
	private final KeywordAttribute keywordAttr;

	public DutchStemFilter(TokenStream _in)
	{
		super(_in);
		stemmer = new DutchStemmer();
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);
	}

	public DutchStemFilter(TokenStream _in, Map stemdictionary)
	{
		this(_in);
		stemmer.setStemDictionary(stemdictionary);
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

	public void setStemmer(DutchStemmer stemmer)
	{
		if (stemmer != null)
			this.stemmer = stemmer;
	}

	public void setStemDictionary(HashMap dict)
	{
		if (stemmer != null)
			stemmer.setStemDictionary(dict);
	}
}
