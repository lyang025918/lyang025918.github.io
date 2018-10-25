// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersianNormalizer.java

package org.apache.lucene.analysis.fa;

import org.apache.lucene.analysis.util.StemmerUtil;

public class PersianNormalizer
{

	public static final char YEH = 1610;
	public static final char FARSI_YEH = 1740;
	public static final char YEH_BARREE = 1746;
	public static final char KEHEH = 1705;
	public static final char KAF = 1603;
	public static final char HAMZA_ABOVE = 1620;
	public static final char HEH_YEH = 1728;
	public static final char HEH_GOAL = 1729;
	public static final char HEH = 1607;

	public PersianNormalizer()
	{
	}

	public int normalize(char s[], int len)
	{
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 1740: 
			case 1746: 
				s[i] = '\u064A';
				break;

			case 1705: 
				s[i] = '\u0643';
				break;

			case 1728: 
			case 1729: 
				s[i] = '\u0647';
				break;

			case 1620: 
				len = StemmerUtil.delete(s, i, len);
				i--;
				break;
			}

		return len;
	}
}
