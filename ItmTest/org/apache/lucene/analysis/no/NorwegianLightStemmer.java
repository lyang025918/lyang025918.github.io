// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NorwegianLightStemmer.java

package org.apache.lucene.analysis.no;

import org.apache.lucene.analysis.util.StemmerUtil;

public class NorwegianLightStemmer
{

	public NorwegianLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len > 4 && s[len - 1] == 's')
			len--;
		if (len > 7 && (StemmerUtil.endsWith(s, len, "heter") || StemmerUtil.endsWith(s, len, "heten")))
			return len - 5;
		if (len > 5 && (StemmerUtil.endsWith(s, len, "dom") || StemmerUtil.endsWith(s, len, "het")))
			return len - 3;
		if (len > 7 && (StemmerUtil.endsWith(s, len, "elser") || StemmerUtil.endsWith(s, len, "elsen")))
			return len - 5;
		if (len > 6 && (StemmerUtil.endsWith(s, len, "ende") || StemmerUtil.endsWith(s, len, "else") || StemmerUtil.endsWith(s, len, "este") || StemmerUtil.endsWith(s, len, "eren")))
			return len - 4;
		if (len > 5 && (StemmerUtil.endsWith(s, len, "ere") || StemmerUtil.endsWith(s, len, "est") || StemmerUtil.endsWith(s, len, "ene")))
			return len - 3;
		if (len > 4 && (StemmerUtil.endsWith(s, len, "er") || StemmerUtil.endsWith(s, len, "en") || StemmerUtil.endsWith(s, len, "et") || StemmerUtil.endsWith(s, len, "st") || StemmerUtil.endsWith(s, len, "te")))
			return len - 2;
		if (len > 3)
			switch (s[len - 1])
			{
			case 97: // 'a'
			case 101: // 'e'
			case 110: // 'n'
				return len - 1;
			}
		return len;
	}
}
