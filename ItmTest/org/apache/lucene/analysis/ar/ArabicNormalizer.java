// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicNormalizer.java

package org.apache.lucene.analysis.ar;

import org.apache.lucene.analysis.util.StemmerUtil;

public class ArabicNormalizer
{

	public static final char ALEF = 1575;
	public static final char ALEF_MADDA = 1570;
	public static final char ALEF_HAMZA_ABOVE = 1571;
	public static final char ALEF_HAMZA_BELOW = 1573;
	public static final char YEH = 1610;
	public static final char DOTLESS_YEH = 1609;
	public static final char TEH_MARBUTA = 1577;
	public static final char HEH = 1607;
	public static final char TATWEEL = 1600;
	public static final char FATHATAN = 1611;
	public static final char DAMMATAN = 1612;
	public static final char KASRATAN = 1613;
	public static final char FATHA = 1614;
	public static final char DAMMA = 1615;
	public static final char KASRA = 1616;
	public static final char SHADDA = 1617;
	public static final char SUKUN = 1618;

	public ArabicNormalizer()
	{
	}

	public int normalize(char s[], int len)
	{
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 1570: 
			case 1571: 
			case 1573: 
				s[i] = '\u0627';
				break;

			case 1609: 
				s[i] = '\u064A';
				break;

			case 1577: 
				s[i] = '\u0647';
				break;

			case 1600: 
			case 1611: 
			case 1612: 
			case 1613: 
			case 1614: 
			case 1615: 
			case 1616: 
			case 1617: 
			case 1618: 
				len = StemmerUtil.delete(s, i, len);
				i--;
				break;
			}

		return len;
	}
}
