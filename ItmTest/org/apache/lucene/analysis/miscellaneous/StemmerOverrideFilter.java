// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StemmerOverrideFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.Version;

public final class StemmerOverrideFilter extends TokenFilter
{

	private final CharArrayMap dictionary;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final KeywordAttribute keywordAtt = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);

	public StemmerOverrideFilter(Version matchVersion, TokenStream input, CharArrayMap dictionary)
	{
		super(input);
		this.dictionary = CharArrayMap.copy(matchVersion, dictionary);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (!keywordAtt.isKeyword())
			{
				String stem = (String)dictionary.get(termAtt.buffer(), 0, termAtt.length());
				if (stem != null)
				{
					termAtt.setEmpty().append(stem);
					keywordAtt.setKeyword(true);
				}
			}
			return true;
		} else
		{
			return false;
		}
	}
}
