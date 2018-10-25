// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordMarkerFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

public final class KeywordMarkerFilter extends TokenFilter
{

	private final KeywordAttribute keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final CharArraySet keywordSet;

	public KeywordMarkerFilter(TokenStream in, CharArraySet keywordSet)
	{
		super(in);
		this.keywordSet = keywordSet;
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (keywordSet.contains(termAtt.buffer(), 0, termAtt.length()))
				keywordAttr.setKeyword(true);
			return true;
		} else
		{
			return false;
		}
	}
}
