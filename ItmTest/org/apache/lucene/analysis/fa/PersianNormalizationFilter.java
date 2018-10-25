// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersianNormalizationFilter.java

package org.apache.lucene.analysis.fa;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

// Referenced classes of package org.apache.lucene.analysis.fa:
//			PersianNormalizer

public final class PersianNormalizationFilter extends TokenFilter
{

	private final PersianNormalizer normalizer = new PersianNormalizer();
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public PersianNormalizationFilter(TokenStream input)
	{
		super(input);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			int newlen = normalizer.normalize(termAtt.buffer(), termAtt.length());
			termAtt.setLength(newlen);
			return true;
		} else
		{
			return false;
		}
	}
}
