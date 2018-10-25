// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ElisionFilter.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharArraySet

public final class ElisionFilter extends TokenFilter
{

	private final CharArraySet articles;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public ElisionFilter(TokenStream input, CharArraySet articles)
	{
		super(input);
		this.articles = articles;
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char termBuffer[] = termAtt.buffer();
			int termLength = termAtt.length();
			int index = -1;
			int i = 0;
			do
			{
				if (i >= termLength)
					break;
				char ch = termBuffer[i];
				if (ch == '\'' || ch == '\u2019')
				{
					index = i;
					break;
				}
				i++;
			} while (true);
			if (index >= 0 && articles.contains(termBuffer, 0, index))
				termAtt.copyBuffer(termBuffer, index + 1, termLength - (index + 1));
			return true;
		} else
		{
			return false;
		}
	}
}
