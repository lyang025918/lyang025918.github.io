// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PortugueseLightStemmer.java

package org.apache.lucene.analysis.pt;

import org.apache.lucene.analysis.util.StemmerUtil;

public class PortugueseLightStemmer
{

	public PortugueseLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 4)
			return len;
		len = removeSuffix(s, len);
		if (len > 3 && s[len - 1] == 'a')
			len = normFeminine(s, len);
		if (len > 4)
			switch (s[len - 1])
			{
			case 97: // 'a'
			case 101: // 'e'
			case 111: // 'o'
				len--;
				break;
			}
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 224: 
			case 225: 
			case 226: 
			case 227: 
			case 228: 
				s[i] = 'a';
				break;

			case 242: 
			case 243: 
			case 244: 
			case 245: 
			case 246: 
				s[i] = 'o';
				break;

			case 232: 
			case 233: 
			case 234: 
			case 235: 
				s[i] = 'e';
				break;

			case 249: 
			case 250: 
			case 251: 
			case 252: 
				s[i] = 'u';
				break;

			case 236: 
			case 237: 
			case 238: 
			case 239: 
				s[i] = 'i';
				break;

			case 231: 
				s[i] = 'c';
				break;
			}

		return len;
	}

	private int removeSuffix(char s[], int len)
	{
		if (len > 4 && StemmerUtil.endsWith(s, len, "es"))
			switch (s[len - 3])
			{
			case 108: // 'l'
			case 114: // 'r'
			case 115: // 's'
			case 122: // 'z'
				return len - 2;
			}
		if (len > 3 && StemmerUtil.endsWith(s, len, "ns"))
		{
			s[len - 2] = 'm';
			return len - 1;
		}
		if (len > 4 && (StemmerUtil.endsWith(s, len, "eis") || StemmerUtil.endsWith(s, len, "\351is")))
		{
			s[len - 3] = 'e';
			s[len - 2] = 'l';
			return len - 1;
		}
		if (len > 4 && StemmerUtil.endsWith(s, len, "ais"))
		{
			s[len - 2] = 'l';
			return len - 1;
		}
		if (len > 4 && StemmerUtil.endsWith(s, len, "\363is"))
		{
			s[len - 3] = 'o';
			s[len - 2] = 'l';
			return len - 1;
		}
		if (len > 4 && StemmerUtil.endsWith(s, len, "is"))
		{
			s[len - 1] = 'l';
			return len;
		}
		if (len > 3 && (StemmerUtil.endsWith(s, len, "\365es") || StemmerUtil.endsWith(s, len, "\343es")))
		{
			len--;
			s[len - 2] = '\343';
			s[len - 1] = 'o';
			return len;
		}
		if (len > 6 && StemmerUtil.endsWith(s, len, "mente"))
			return len - 5;
		if (len > 3 && s[len - 1] == 's')
			return len - 1;
		else
			return len;
	}

	private int normFeminine(char s[], int len)
	{
		if (len > 7 && (StemmerUtil.endsWith(s, len, "inha") || StemmerUtil.endsWith(s, len, "iaca") || StemmerUtil.endsWith(s, len, "eira")))
		{
			s[len - 1] = 'o';
			return len;
		}
		if (len > 6)
		{
			if (StemmerUtil.endsWith(s, len, "osa") || StemmerUtil.endsWith(s, len, "ica") || StemmerUtil.endsWith(s, len, "ida") || StemmerUtil.endsWith(s, len, "ada") || StemmerUtil.endsWith(s, len, "iva") || StemmerUtil.endsWith(s, len, "ama"))
			{
				s[len - 1] = 'o';
				return len;
			}
			if (StemmerUtil.endsWith(s, len, "ona"))
			{
				s[len - 3] = '\343';
				s[len - 2] = 'o';
				return len - 1;
			}
			if (StemmerUtil.endsWith(s, len, "ora"))
				return len - 1;
			if (StemmerUtil.endsWith(s, len, "esa"))
			{
				s[len - 3] = '\352';
				return len - 1;
			}
			if (StemmerUtil.endsWith(s, len, "na"))
			{
				s[len - 1] = 'o';
				return len;
			}
		}
		return len;
	}
}
