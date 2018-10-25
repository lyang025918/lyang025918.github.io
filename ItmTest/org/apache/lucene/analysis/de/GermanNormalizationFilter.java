// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanNormalizationFilter.java

package org.apache.lucene.analysis.de;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.StemmerUtil;

public final class GermanNormalizationFilter extends TokenFilter
{

	private static final int N = 0;
	private static final int V = 1;
	private static final int U = 2;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public GermanNormalizationFilter(TokenStream input)
	{
		super(input);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			int state = 0;
			char buffer[] = termAtt.buffer();
			int length = termAtt.length();
			for (int i = 0; i < length; i++)
			{
				char c = buffer[i];
				switch (c)
				{
				case 97: // 'a'
				case 111: // 'o'
					state = 2;
					break;

				case 117: // 'u'
					state = state != 0 ? 1 : 2;
					break;

				case 101: // 'e'
					if (state == 2)
						length = StemmerUtil.delete(buffer, i--, length);
					state = 1;
					break;

				case 105: // 'i'
				case 113: // 'q'
				case 121: // 'y'
					state = 1;
					break;

				case 228: 
					buffer[i] = 'a';
					state = 1;
					break;

				case 246: 
					buffer[i] = 'o';
					state = 1;
					break;

				case 252: 
					buffer[i] = 'u';
					state = 1;
					break;

				case 223: 
					buffer[i++] = 's';
					buffer = termAtt.resizeBuffer(1 + length);
					if (i < length)
						System.arraycopy(buffer, i, buffer, i + 1, length - i);
					buffer[i] = 's';
					length++;
					state = 0;
					break;

				default:
					state = 0;
					break;
				}
			}

			termAtt.setLength(length);
			return true;
		} else
		{
			return false;
		}
	}
}
