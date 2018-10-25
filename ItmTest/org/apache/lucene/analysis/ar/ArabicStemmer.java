// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicStemmer.java

package org.apache.lucene.analysis.ar;

import org.apache.lucene.analysis.util.StemmerUtil;

public class ArabicStemmer
{

	public static final char ALEF = 1575;
	public static final char BEH = 1576;
	public static final char TEH_MARBUTA = 1577;
	public static final char TEH = 1578;
	public static final char FEH = 1601;
	public static final char KAF = 1603;
	public static final char LAM = 1604;
	public static final char NOON = 1606;
	public static final char HEH = 1607;
	public static final char WAW = 1608;
	public static final char YEH = 1610;
	public static final char prefixes[][] = {
		"??".toCharArray(), "???".toCharArray(), "???".toCharArray(), "???".toCharArray(), "???".toCharArray(), "??".toCharArray(), "?".toCharArray()
	};
	public static final char suffixes[][] = {
		"??".toCharArray(), "??".toCharArray(), "??".toCharArray(), "??".toCharArray(), "??".toCharArray(), "??".toCharArray(), "??".toCharArray(), "?".toCharArray(), "?".toCharArray(), "?".toCharArray()
	};

	public ArabicStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		len = stemPrefix(s, len);
		len = stemSuffix(s, len);
		return len;
	}

	public int stemPrefix(char s[], int len)
	{
		for (int i = 0; i < prefixes.length; i++)
			if (startsWithCheckLength(s, len, prefixes[i]))
				return StemmerUtil.deleteN(s, 0, len, prefixes[i].length);

		return len;
	}

	public int stemSuffix(char s[], int len)
	{
		for (int i = 0; i < suffixes.length; i++)
			if (endsWithCheckLength(s, len, suffixes[i]))
				len = StemmerUtil.deleteN(s, len - suffixes[i].length, len, suffixes[i].length);

		return len;
	}

	boolean startsWithCheckLength(char s[], int len, char prefix[])
	{
		if (prefix.length == 1 && len < 4)
			return false;
		if (len < prefix.length + 2)
			return false;
		for (int i = 0; i < prefix.length; i++)
			if (s[i] != prefix[i])
				return false;

		return true;
	}

	boolean endsWithCheckLength(char s[], int len, char suffix[])
	{
		if (len < suffix.length + 2)
			return false;
		for (int i = 0; i < suffix.length; i++)
			if (s[(len - suffix.length) + i] != suffix[i])
				return false;

		return true;
	}

}
