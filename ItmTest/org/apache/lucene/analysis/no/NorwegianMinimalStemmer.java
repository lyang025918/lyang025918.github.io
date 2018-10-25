// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NorwegianMinimalStemmer.java

package org.apache.lucene.analysis.no;

import org.apache.lucene.analysis.util.StemmerUtil;

public class NorwegianMinimalStemmer
{

	public NorwegianMinimalStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len > 4 && s[len - 1] == 's')
			len--;
		if (len > 5 && StemmerUtil.endsWith(s, len, "ene"))
			return len - 3;
		if (len > 4 && (StemmerUtil.endsWith(s, len, "er") || StemmerUtil.endsWith(s, len, "en") || StemmerUtil.endsWith(s, len, "et")))
			return len - 2;
		if (len > 3)
			switch (s[len - 1])
			{
			case 97: // 'a'
			case 101: // 'e'
				return len - 1;
			}
		return len;
	}
}
