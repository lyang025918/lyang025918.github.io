// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PorterStemFilter.java

package org.apache.lucene.analysis.en;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

// Referenced classes of package org.apache.lucene.analysis.en:
//			PorterStemmer

public final class PorterStemFilter extends TokenFilter
{

	private final PorterStemmer stemmer = new PorterStemmer();
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public PorterStemFilter(TokenStream in)
	{
		super(in);
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		if (!keywordAttr.isKeyword() && stemmer.stem(termAtt.buffer(), 0, termAtt.length()))
			termAtt.copyBuffer(stemmer.getResultBuffer(), 0, stemmer.getResultLength());
		return true;
	}
}
