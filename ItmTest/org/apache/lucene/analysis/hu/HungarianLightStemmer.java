// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HungarianLightStemmer.java

package org.apache.lucene.analysis.hu;

import org.apache.lucene.analysis.util.StemmerUtil;

public class HungarianLightStemmer
{

	public HungarianLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 225: 
				s[i] = 'a';
				break;

			case 233: 
			case 235: 
				s[i] = 'e';
				break;

			case 237: 
				s[i] = 'i';
				break;

			case 243: 
			case 245: 
			case 246: 
			case 337: 
				s[i] = 'o';
				break;

			case 250: 
			case 251: 
			case 252: 
			case 361: 
			case 369: 
				s[i] = 'u';
				break;
			}

		len = removeCase(s, len);
		len = removePossessive(s, len);
		len = removePlural(s, len);
		return normalize(s, len);
	}

	private int removeCase(char s[], int len)
	{
		if (len > 6 && StemmerUtil.endsWith(s, len, "kent"))
			return len - 4;
		if (len > 5)
		{
			if (StemmerUtil.endsWith(s, len, "nak") || StemmerUtil.endsWith(s, len, "nek") || StemmerUtil.endsWith(s, len, "val") || StemmerUtil.endsWith(s, len, "vel") || StemmerUtil.endsWith(s, len, "ert") || StemmerUtil.endsWith(s, len, "rol") || StemmerUtil.endsWith(s, len, "ban") || StemmerUtil.endsWith(s, len, "ben") || StemmerUtil.endsWith(s, len, "bol") || StemmerUtil.endsWith(s, len, "nal") || StemmerUtil.endsWith(s, len, "nel") || StemmerUtil.endsWith(s, len, "hoz") || StemmerUtil.endsWith(s, len, "hez") || StemmerUtil.endsWith(s, len, "tol"))
				return len - 3;
			if ((StemmerUtil.endsWith(s, len, "al") || StemmerUtil.endsWith(s, len, "el")) && !isVowel(s[len - 3]) && s[len - 3] == s[len - 4])
				return len - 3;
		}
		if (len > 4)
		{
			if (StemmerUtil.endsWith(s, len, "at") || StemmerUtil.endsWith(s, len, "et") || StemmerUtil.endsWith(s, len, "ot") || StemmerUtil.endsWith(s, len, "va") || StemmerUtil.endsWith(s, len, "ve") || StemmerUtil.endsWith(s, len, "ra") || StemmerUtil.endsWith(s, len, "re") || StemmerUtil.endsWith(s, len, "ba") || StemmerUtil.endsWith(s, len, "be") || StemmerUtil.endsWith(s, len, "ul") || StemmerUtil.endsWith(s, len, "ig"))
				return len - 2;
			if ((StemmerUtil.endsWith(s, len, "on") || StemmerUtil.endsWith(s, len, "en")) && !isVowel(s[len - 3]))
				return len - 2;
			switch (s[len - 1])
			{
			case 110: // 'n'
			case 116: // 't'
				return len - 1;

			case 97: // 'a'
			case 101: // 'e'
				if (s[len - 2] == s[len - 3] && !isVowel(s[len - 2]))
					return len - 2;
				break;
			}
		}
		return len;
	}

	private int removePossessive(char s[], int len)
	{
		if (len > 6)
		{
			if (!isVowel(s[len - 5]) && (StemmerUtil.endsWith(s, len, "atok") || StemmerUtil.endsWith(s, len, "otok") || StemmerUtil.endsWith(s, len, "etek")))
				return len - 4;
			if (StemmerUtil.endsWith(s, len, "itek") || StemmerUtil.endsWith(s, len, "itok"))
				return len - 4;
		}
		if (len > 5)
		{
			if (!isVowel(s[len - 4]) && (StemmerUtil.endsWith(s, len, "unk") || StemmerUtil.endsWith(s, len, "tok") || StemmerUtil.endsWith(s, len, "tek")))
				return len - 3;
			if (isVowel(s[len - 4]) && StemmerUtil.endsWith(s, len, "juk"))
				return len - 3;
			if (StemmerUtil.endsWith(s, len, "ink"))
				return len - 3;
		}
		if (len > 4)
		{
			if (!isVowel(s[len - 3]) && (StemmerUtil.endsWith(s, len, "am") || StemmerUtil.endsWith(s, len, "em") || StemmerUtil.endsWith(s, len, "om") || StemmerUtil.endsWith(s, len, "ad") || StemmerUtil.endsWith(s, len, "ed") || StemmerUtil.endsWith(s, len, "od") || StemmerUtil.endsWith(s, len, "uk")))
				return len - 2;
			if (isVowel(s[len - 3]) && (StemmerUtil.endsWith(s, len, "nk") || StemmerUtil.endsWith(s, len, "ja") || StemmerUtil.endsWith(s, len, "je")))
				return len - 2;
			if (StemmerUtil.endsWith(s, len, "im") || StemmerUtil.endsWith(s, len, "id") || StemmerUtil.endsWith(s, len, "ik"))
				return len - 2;
		}
		if (len > 3)
			switch (s[len - 1])
			{
			case 98: // 'b'
			case 99: // 'c'
			case 102: // 'f'
			case 103: // 'g'
			case 104: // 'h'
			case 106: // 'j'
			case 107: // 'k'
			case 108: // 'l'
			default:
				break;

			case 97: // 'a'
			case 101: // 'e'
				if (!isVowel(s[len - 2]))
					return len - 1;
				break;

			case 100: // 'd'
			case 109: // 'm'
				if (isVowel(s[len - 2]))
					return len - 1;
				break;

			case 105: // 'i'
				return len - 1;
			}
		return len;
	}

	private int removePlural(char s[], int len)
	{
		if (len > 3 && s[len - 1] == 'k')
		{
			switch (s[len - 2])
			{
			case 97: // 'a'
			case 101: // 'e'
			case 111: // 'o'
				if (len > 4)
					return len - 2;
				break;
			}
			return len - 1;
		} else
		{
			return len;
		}
	}

	private int normalize(char s[], int len)
	{
		if (len > 3)
			switch (s[len - 1])
			{
			case 97: // 'a'
			case 101: // 'e'
			case 105: // 'i'
			case 111: // 'o'
				return len - 1;
			}
		return len;
	}

	private boolean isVowel(char ch)
	{
		switch (ch)
		{
		case 97: // 'a'
		case 101: // 'e'
		case 105: // 'i'
		case 111: // 'o'
		case 117: // 'u'
		case 121: // 'y'
			return true;
		}
		return false;
	}
}
