// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKWidthFilter.java

package org.apache.lucene.analysis.cjk;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.StemmerUtil;

public final class CJKWidthFilter extends TokenFilter
{

	private CharTermAttribute termAtt;
	private static final char KANA_NORM[] = {
		'\u30FB', '\u30F2', '\u30A1', '\u30A3', '\u30A5', '\u30A7', '\u30A9', '\u30E3', '\u30E5', '\u30E7', 
		'\u30C3', '\u30FC', '\u30A2', '\u30A4', '\u30A6', '\u30A8', '\u30AA', '\u30AB', '\u30AD', '\u30AF', 
		'\u30B1', '\u30B3', '\u30B5', '\u30B7', '\u30B9', '\u30BB', '\u30BD', '\u30BF', '\u30C1', '\u30C4', 
		'\u30C6', '\u30C8', '\u30CA', '\u30CB', '\u30CC', '\u30CD', '\u30CE', '\u30CF', '\u30D2', '\u30D5', 
		'\u30D8', '\u30DB', '\u30DE', '\u30DF', '\u30E0', '\u30E1', '\u30E2', '\u30E4', '\u30E6', '\u30E8', 
		'\u30E9', '\u30EA', '\u30EB', '\u30EC', '\u30ED', '\u30EF', '\u30F3', '\u3099', '\u309A'
	};
	private static final byte KANA_COMBINE_VOICED[] = {
		78, 0, 0, 0, 0, 1, 0, 1, 0, 1, 
		0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 
		0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 
		1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 
		0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 
		1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 8, 8, 8, 8, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 1
	};
	private static final byte KANA_COMBINE_HALF_VOICED[] = {
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 
		2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0
	};

	public CJKWidthFilter(TokenStream input)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char text[] = termAtt.buffer();
			int length = termAtt.length();
			for (int i = 0; i < length; i++)
			{
				char ch = text[i];
				if (ch >= '\uFF01' && ch <= '\uFF5E')
				{
					text[i] -= '\uFEE0';
					continue;
				}
				if (ch < '\uFF65' || ch > '\uFF9F')
					continue;
				if ((ch == '\uFF9E' || ch == '\uFF9F') && i > 0 && combine(text, i, ch))
					length = StemmerUtil.delete(text, i--, length);
				else
					text[i] = KANA_NORM[ch - 65381];
			}

			termAtt.setLength(length);
			return true;
		} else
		{
			return false;
		}
	}

	private static boolean combine(char text[], int pos, char ch)
	{
		char prev = text[pos - 1];
		if (prev >= '\u30A6' && prev <= '\u30FD')
		{
			text[pos - 1] += ch != '\uFF9F' ? ((char) (KANA_COMBINE_VOICED[prev - 12454])) : ((char) (KANA_COMBINE_HALF_VOICED[prev - 12454]));
			return text[pos - 1] != prev;
		} else
		{
			return false;
		}
	}

}
