// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IrishLowerCaseFilter.java

package org.apache.lucene.analysis.ga;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public final class IrishLowerCaseFilter extends TokenFilter
{

	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public IrishLowerCaseFilter(TokenStream in)
	{
		super(in);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char chArray[] = termAtt.buffer();
			int chLen = termAtt.length();
			int idx = 0;
			if (chLen > 1 && (chArray[0] == 'n' || chArray[0] == 't') && isUpperVowel(chArray[1]))
			{
				chArray = termAtt.resizeBuffer(chLen + 1);
				for (int i = chLen; i > 1; i--)
					chArray[i] = chArray[i - 1];

				chArray[1] = '-';
				termAtt.setLength(chLen + 1);
				idx = 2;
				chLen++;
			}
			for (int i = idx; i < chLen; i += Character.toChars(Character.toLowerCase(chArray[i]), chArray, i));
			return true;
		} else
		{
			return false;
		}
	}

	private boolean isUpperVowel(int v)
	{
		switch (v)
		{
		case 65: // 'A'
		case 69: // 'E'
		case 73: // 'I'
		case 79: // 'O'
		case 85: // 'U'
		case 193: 
		case 201: 
		case 205: 
		case 211: 
		case 218: 
			return true;
		}
		return false;
	}
}
