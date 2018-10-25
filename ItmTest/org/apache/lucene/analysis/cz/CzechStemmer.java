// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CzechStemmer.java

package org.apache.lucene.analysis.cz;

import org.apache.lucene.analysis.util.StemmerUtil;

public class CzechStemmer
{

	public CzechStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		len = removeCase(s, len);
		len = removePossessives(s, len);
		if (len > 0)
			len = normalize(s, len);
		return len;
	}

	private int removeCase(char s[], int len)
	{
		if (len > 7 && StemmerUtil.endsWith(s, len, "atech"))
			return len - 5;
		if (len > 6 && (StemmerUtil.endsWith(s, len, "¨§tem") || StemmerUtil.endsWith(s, len, "etem") || StemmerUtil.endsWith(s, len, "at?m")))
			return len - 4;
		if (len > 5 && (StemmerUtil.endsWith(s, len, "ech") || StemmerUtil.endsWith(s, len, "ich") || StemmerUtil.endsWith(s, len, "\355ch") || StemmerUtil.endsWith(s, len, "\351ho") || StemmerUtil.endsWith(s, len, "¨§mi") || StemmerUtil.endsWith(s, len, "emi") || StemmerUtil.endsWith(s, len, "\351mu") || StemmerUtil.endsWith(s, len, "¨§te") || StemmerUtil.endsWith(s, len, "ete") || StemmerUtil.endsWith(s, len, "¨§ti") || StemmerUtil.endsWith(s, len, "eti") || StemmerUtil.endsWith(s, len, "\355ho") || StemmerUtil.endsWith(s, len, "iho") || StemmerUtil.endsWith(s, len, "\355mi") || StemmerUtil.endsWith(s, len, "\355mu") || StemmerUtil.endsWith(s, len, "imu") || StemmerUtil.endsWith(s, len, "\341ch") || StemmerUtil.endsWith(s, len, "ata") || StemmerUtil.endsWith(s, len, "aty") || StemmerUtil.endsWith(s, len, "\375ch") || StemmerUtil.endsWith(s, len, "ama") || StemmerUtil.endsWith(s, len, "ami") || StemmerUtil.endsWith(s, len, "ov\351") || StemmerUtil.endsWith(s, len, "ovi") || StemmerUtil.endsWith(s, len, "\375mi")))
			return len - 3;
		if (len > 4 && (StemmerUtil.endsWith(s, len, "em") || StemmerUtil.endsWith(s, len, "es") || StemmerUtil.endsWith(s, len, "\351m") || StemmerUtil.endsWith(s, len, "\355m") || StemmerUtil.endsWith(s, len, "?m") || StemmerUtil.endsWith(s, len, "at") || StemmerUtil.endsWith(s, len, "\341m") || StemmerUtil.endsWith(s, len, "os") || StemmerUtil.endsWith(s, len, "us") || StemmerUtil.endsWith(s, len, "\375m") || StemmerUtil.endsWith(s, len, "mi") || StemmerUtil.endsWith(s, len, "ou")))
			return len - 2;
		if (len > 3)
			switch (s[len - 1])
			{
			case 97: // 'a'
			case 101: // 'e'
			case 105: // 'i'
			case 111: // 'o'
			case 117: // 'u'
			case 121: // 'y'
			case 225: 
			case 233: 
			case 237: 
			case 253: 
			case 283: 
			case 367: 
				return len - 1;
			}
		return len;
	}

	private int removePossessives(char s[], int len)
	{
		if (len > 5 && (StemmerUtil.endsWith(s, len, "ov") || StemmerUtil.endsWith(s, len, "in") || StemmerUtil.endsWith(s, len, "?v")))
			return len - 2;
		else
			return len;
	}

	private int normalize(char s[], int len)
	{
		if (StemmerUtil.endsWith(s, len, "?t"))
		{
			s[len - 2] = 'c';
			s[len - 1] = 'k';
			return len;
		}
		if (StemmerUtil.endsWith(s, len, "?t"))
		{
			s[len - 2] = 's';
			s[len - 1] = 'k';
			return len;
		}
		switch (s[len - 1])
		{
		case 99: // 'c'
		case 269: 
			s[len - 1] = 'k';
			return len;

		case 122: // 'z'
		case 382: 
			s[len - 1] = 'h';
			return len;
		}
		if (len > 1 && s[len - 2] == 'e')
		{
			s[len - 2] = s[len - 1];
			return len - 1;
		}
		if (len > 2 && s[len - 2] == '\u016F')
		{
			s[len - 2] = 'o';
			return len;
		} else
		{
			return len;
		}
	}
}
