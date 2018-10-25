// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LatvianStemmer.java

package org.apache.lucene.analysis.lv;

import org.apache.lucene.analysis.util.StemmerUtil;

public class LatvianStemmer
{
	static class Affix
	{

		char affix[];
		int vc;
		boolean palatalizes;

		Affix(String affix, int vc, boolean palatalizes)
		{
			this.affix = affix.toCharArray();
			this.vc = vc;
			this.palatalizes = palatalizes;
		}
	}


	static final Affix affixes[] = {
		new Affix("ajiem", 3, false), new Affix("ajai", 3, false), new Affix("ajam", 2, false), new Affix("aj¨¡m", 2, false), new Affix("ajos", 2, false), new Affix("aj¨¡s", 2, false), new Affix("iem", 2, true), new Affix("aj¨¡", 2, false), new Affix("ais", 2, false), new Affix("ai", 2, false), 
		new Affix("ei", 2, false), new Affix("¨¡m", 1, false), new Affix("am", 1, false), new Affix("¨¥m", 1, false), new Affix("¨©m", 1, false), new Affix("im", 1, false), new Affix("um", 1, false), new Affix("us", 1, true), new Affix("as", 1, false), new Affix("¨¡s", 1, false), 
		new Affix("es", 1, false), new Affix("os", 1, true), new Affix("ij", 1, false), new Affix("¨©s", 1, false), new Affix("¨¥s", 1, false), new Affix("is", 1, false), new Affix("ie", 1, false), new Affix("u", 1, true), new Affix("a", 1, true), new Affix("i", 1, true), 
		new Affix("e", 1, false), new Affix("¨¡", 1, false), new Affix("¨¥", 1, false), new Affix("¨©", 1, false), new Affix("¨±", 1, false), new Affix("o", 1, false), new Affix("s", 0, false), new Affix("?", 0, false)
	};

	public LatvianStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		int numVowels = numVowels(s, len);
		for (int i = 0; i < affixes.length; i++)
		{
			Affix affix = affixes[i];
			if (numVowels > affix.vc && len >= affix.affix.length + 3 && StemmerUtil.endsWith(s, len, affix.affix))
			{
				len -= affix.affix.length;
				return affix.palatalizes ? unpalatalize(s, len) : len;
			}
		}

		return len;
	}

	private int unpalatalize(char s[], int len)
	{
		if (s[len] == 'u')
		{
			if (StemmerUtil.endsWith(s, len, "k?"))
			{
				len++;
				s[len - 2] = 's';
				s[len - 1] = 't';
				return len;
			}
			if (StemmerUtil.endsWith(s, len, "??"))
			{
				s[len - 2] = 'n';
				s[len - 1] = 'n';
				return len;
			}
		}
		if (StemmerUtil.endsWith(s, len, "pj") || StemmerUtil.endsWith(s, len, "bj") || StemmerUtil.endsWith(s, len, "mj") || StemmerUtil.endsWith(s, len, "vj"))
			return len - 1;
		if (StemmerUtil.endsWith(s, len, "??"))
		{
			s[len - 2] = 's';
			s[len - 1] = 'n';
			return len;
		}
		if (StemmerUtil.endsWith(s, len, "??"))
		{
			s[len - 2] = 'z';
			s[len - 1] = 'n';
			return len;
		}
		if (StemmerUtil.endsWith(s, len, "??"))
		{
			s[len - 2] = 's';
			s[len - 1] = 'l';
			return len;
		}
		if (StemmerUtil.endsWith(s, len, "??"))
		{
			s[len - 2] = 'z';
			s[len - 1] = 'l';
			return len;
		}
		if (StemmerUtil.endsWith(s, len, "??"))
		{
			s[len - 2] = 'l';
			s[len - 1] = 'n';
			return len;
		}
		if (StemmerUtil.endsWith(s, len, "??"))
		{
			s[len - 2] = 'l';
			s[len - 1] = 'l';
			return len;
		}
		if (s[len - 1] == '\u010D')
		{
			s[len - 1] = 'c';
			return len;
		}
		if (s[len - 1] == '\u013C')
		{
			s[len - 1] = 'l';
			return len;
		}
		if (s[len - 1] == '\u0146')
		{
			s[len - 1] = 'n';
			return len;
		} else
		{
			return len;
		}
	}

	private int numVowels(char s[], int len)
	{
		int n = 0;
		int i = 0;
		do
		{
			if (i >= len)
				break;
			switch (s[i])
			{
			case 97: // 'a'
			case 101: // 'e'
			case 105: // 'i'
			case 111: // 'o'
			case 117: // 'u'
			case 257: 
			case 275: 
			case 299: 
			case 363: 
				n++;
				break;
			}
			i++;
		} while (true);
		return n;
	}

}
