// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulgarianStemmer.java

package org.apache.lucene.analysis.bg;

import org.apache.lucene.analysis.util.StemmerUtil;

public class BulgarianStemmer
{

	public BulgarianStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 4)
			return len;
		if (len > 5 && StemmerUtil.endsWith(s, len, "我投忘"))
			return len - 3;
		len = removeArticle(s, len);
		len = removePlural(s, len);
		if (len > 3)
		{
			if (StemmerUtil.endsWith(s, len, "攸"))
				len--;
			if (StemmerUtil.endsWith(s, len, "忘") || StemmerUtil.endsWith(s, len, "抉") || StemmerUtil.endsWith(s, len, "快"))
				len--;
		}
		if (len > 4 && StemmerUtil.endsWith(s, len, "快扶"))
		{
			s[len - 2] = '\u043D';
			len--;
		}
		if (len > 5 && s[len - 2] == '\u044A')
		{
			s[len - 2] = s[len - 1];
			len--;
		}
		return len;
	}

	private int removeArticle(char s[], int len)
	{
		if (len > 6 && StemmerUtil.endsWith(s, len, "我攸找"))
			return len - 3;
		if (len > 5 && (StemmerUtil.endsWith(s, len, "抓找") || StemmerUtil.endsWith(s, len, "找抉") || StemmerUtil.endsWith(s, len, "找快") || StemmerUtil.endsWith(s, len, "找忘") || StemmerUtil.endsWith(s, len, "我攸")))
			return len - 2;
		if (len > 4 && StemmerUtil.endsWith(s, len, "攸找"))
			return len - 2;
		else
			return len;
	}

	private int removePlural(char s[], int len)
	{
		if (len > 6)
		{
			if (StemmerUtil.endsWith(s, len, "抉志扯我"))
				return len - 3;
			if (StemmerUtil.endsWith(s, len, "抉志快"))
				return len - 3;
			if (StemmerUtil.endsWith(s, len, "快志快"))
			{
				s[len - 3] = '\u0439';
				return len - 2;
			}
		}
		if (len > 5)
		{
			if (StemmerUtil.endsWith(s, len, "我投忘"))
				return len - 3;
			if (StemmerUtil.endsWith(s, len, "找忘"))
				return len - 2;
			if (StemmerUtil.endsWith(s, len, "扯我"))
			{
				s[len - 2] = '\u043A';
				return len - 1;
			}
			if (StemmerUtil.endsWith(s, len, "戒我"))
			{
				s[len - 2] = '\u0433';
				return len - 1;
			}
			if (s[len - 3] == '\u0435' && s[len - 1] == '\u0438')
			{
				s[len - 3] = '\u044F';
				return len - 1;
			}
		}
		if (len > 4)
		{
			if (StemmerUtil.endsWith(s, len, "扼我"))
			{
				s[len - 2] = '\u0445';
				return len - 1;
			}
			if (StemmerUtil.endsWith(s, len, "我"))
				return len - 1;
		}
		return len;
	}
}
