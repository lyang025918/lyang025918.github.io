// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KStemFilter.java

package org.apache.lucene.analysis.en;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.en:
//			KStemmer

public final class KStemFilter extends TokenFilter
{

	private final KStemmer stemmer = new KStemmer();
	private final CharTermAttribute termAttribute = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAtt = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public KStemFilter(TokenStream in)
	{
		super(in);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		char term[] = termAttribute.buffer();
		int len = termAttribute.length();
		if (!keywordAtt.isKeyword() && stemmer.stem(term, len))
			termAttribute.setEmpty().append(stemmer.asCharSequence());
		return true;
	}
}
