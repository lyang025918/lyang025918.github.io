// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TurkishLowerCaseFilter.java

package org.apache.lucene.analysis.tr;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public final class TurkishLowerCaseFilter extends TokenFilter
{

	private static final int LATIN_CAPITAL_LETTER_I = 73;
	private static final int LATIN_SMALL_LETTER_I = 105;
	private static final int LATIN_SMALL_LETTER_DOTLESS_I = 305;
	private static final int COMBINING_DOT_ABOVE = 775;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public TurkishLowerCaseFilter(TokenStream in)
	{
		super(in);
	}

	public final boolean incrementToken()
		throws IOException
	{
		boolean iOrAfter = false;
		if (input.incrementToken())
		{
			char buffer[] = termAtt.buffer();
			int length = termAtt.length();
			int i = 0;
			do
			{
				if (i >= length)
					break;
				int ch = Character.codePointAt(buffer, i);
				iOrAfter = ch == 73 || iOrAfter && Character.getType(ch) == 6;
				if (iOrAfter)
					switch (ch)
					{
					case 775: 
						length = delete(buffer, i, length);
						continue;

					case 73: // 'I'
						if (isBeforeDot(buffer, i + 1, length))
						{
							buffer[i] = 'i';
						} else
						{
							buffer[i] = '\u0131';
							iOrAfter = false;
						}
						i++;
						continue;
					}
				i += Character.toChars(Character.toLowerCase(ch), buffer, i);
			} while (true);
			termAtt.setLength(length);
			return true;
		} else
		{
			return false;
		}
	}

	private boolean isBeforeDot(char s[], int pos, int len)
	{
		int ch;
		for (int i = pos; i < len; i += Character.charCount(ch))
		{
			ch = Character.codePointAt(s, i);
			if (Character.getType(ch) != 6)
				return false;
			if (ch == 775)
				return true;
		}

		return false;
	}

	private int delete(char s[], int pos, int len)
	{
		if (pos < len)
			System.arraycopy(s, pos + 1, s, pos, len - pos - 1);
		return len - 1;
	}
}
