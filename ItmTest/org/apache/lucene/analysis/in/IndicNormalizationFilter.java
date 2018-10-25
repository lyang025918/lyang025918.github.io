// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndicNormalizationFilter.java

package org.apache.lucene.analysis.in;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

// Referenced classes of package org.apache.lucene.analysis.in:
//			IndicNormalizer

public final class IndicNormalizationFilter extends TokenFilter
{

	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final IndicNormalizer normalizer = new IndicNormalizer();

	public IndicNormalizationFilter(TokenStream input)
	{
		super(input);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			termAtt.setLength(normalizer.normalize(termAtt.buffer(), termAtt.length()));
			return true;
		} else
		{
			return false;
		}
	}
}
