// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HindiNormalizer.java

package org.apache.lucene.analysis.hi;

import org.apache.lucene.analysis.util.StemmerUtil;

public class HindiNormalizer
{

	public HindiNormalizer()
	{
	}

	public int normalize(char s[], int len)
	{
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			default:
				break;

			case 2344: 
				if (i + 1 < len && s[i + 1] == '\u094D')
				{
					s[i] = '\u0902';
					len = StemmerUtil.delete(s, i + 1, len);
				}
				break;

			case 2305: 
				s[i] = '\u0902';
				break;

			case 2364: 
				len = StemmerUtil.delete(s, i, len);
				i--;
				break;

			case 2345: 
				s[i] = '\u0928';
				break;

			case 2353: 
				s[i] = '\u0930';
				break;

			case 2356: 
				s[i] = '\u0933';
				break;

			case 2392: 
				s[i] = '\u0915';
				break;

			case 2393: 
				s[i] = '\u0916';
				break;

			case 2394: 
				s[i] = '\u0917';
				break;

			case 2395: 
				s[i] = '\u091C';
				break;

			case 2396: 
				s[i] = '\u0921';
				break;

			case 2397: 
				s[i] = '\u0922';
				break;

			case 2398: 
				s[i] = '\u092B';
				break;

			case 2399: 
				s[i] = '\u092F';
				break;

			case 8204: 
			case 8205: 
				len = StemmerUtil.delete(s, i, len);
				i--;
				break;

			case 2381: 
				len = StemmerUtil.delete(s, i, len);
				i--;
				break;

			case 2373: 
			case 2374: 
				s[i] = '\u0947';
				break;

			case 2377: 
			case 2378: 
				s[i] = '\u094B';
				break;

			case 2317: 
			case 2318: 
				s[i] = '\u090F';
				break;

			case 2321: 
			case 2322: 
				s[i] = '\u0913';
				break;

			case 2418: 
				s[i] = '\u0905';
				break;

			case 2310: 
				s[i] = '\u0905';
				break;

			case 2312: 
				s[i] = '\u0907';
				break;

			case 2314: 
				s[i] = '\u0909';
				break;

			case 2400: 
				s[i] = '\u090B';
				break;

			case 2401: 
				s[i] = '\u090C';
				break;

			case 2320: 
				s[i] = '\u090F';
				break;

			case 2324: 
				s[i] = '\u0913';
				break;

			case 2368: 
				s[i] = '\u093F';
				break;

			case 2370: 
				s[i] = '\u0941';
				break;

			case 2372: 
				s[i] = '\u0943';
				break;

			case 2403: 
				s[i] = '\u0962';
				break;

			case 2376: 
				s[i] = '\u0947';
				break;

			case 2380: 
				s[i] = '\u094B';
				break;
			}

		return len;
	}
}
