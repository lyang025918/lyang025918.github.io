// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RussianLightStemmer.java

package org.apache.lucene.analysis.ru;

import org.apache.lucene.analysis.util.StemmerUtil;

public class RussianLightStemmer
{

	public RussianLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		len = removeCase(s, len);
		return normalize(s, len);
	}

	private int normalize(char s[], int len)
	{
		if (len > 3)
			switch (s[len - 1])
			{
			case 1080: 
			case 1100: 
				return len - 1;

			case 1085: 
				if (s[len - 2] == '\u043D')
					return len - 1;
				break;
			}
		return len;
	}

	private int removeCase(char s[], int len)
	{
		if (len > 6 && (StemmerUtil.endsWith(s, len, "我攸技我") || StemmerUtil.endsWith(s, len, "抉攸技我")))
			return len - 4;
		if (len > 5 && (StemmerUtil.endsWith(s, len, "我攸技") || StemmerUtil.endsWith(s, len, "我攸抒") || StemmerUtil.endsWith(s, len, "抉攸抒") || StemmerUtil.endsWith(s, len, "攸技我") || StemmerUtil.endsWith(s, len, "抉攸技") || StemmerUtil.endsWith(s, len, "抉抆志") || StemmerUtil.endsWith(s, len, "忘技我") || StemmerUtil.endsWith(s, len, "快忍抉") || StemmerUtil.endsWith(s, len, "快技批") || StemmerUtil.endsWith(s, len, "快把我") || StemmerUtil.endsWith(s, len, "我技我") || StemmerUtil.endsWith(s, len, "抉忍抉") || StemmerUtil.endsWith(s, len, "抉技批") || StemmerUtil.endsWith(s, len, "抑技我") || StemmerUtil.endsWith(s, len, "抉快志")))
			return len - 3;
		if (len > 4 && (StemmerUtil.endsWith(s, len, "忘攸") || StemmerUtil.endsWith(s, len, "攸攸") || StemmerUtil.endsWith(s, len, "攸抒") || StemmerUtil.endsWith(s, len, "攻攻") || StemmerUtil.endsWith(s, len, "忘抒") || StemmerUtil.endsWith(s, len, "快攻") || StemmerUtil.endsWith(s, len, "我抒") || StemmerUtil.endsWith(s, len, "我攸") || StemmerUtil.endsWith(s, len, "我攻") || StemmerUtil.endsWith(s, len, "抆志") || StemmerUtil.endsWith(s, len, "抉攻") || StemmerUtil.endsWith(s, len, "批攻") || StemmerUtil.endsWith(s, len, "攸技") || StemmerUtil.endsWith(s, len, "抑抒") || StemmerUtil.endsWith(s, len, "快攸") || StemmerUtil.endsWith(s, len, "忘技") || StemmerUtil.endsWith(s, len, "快技") || StemmerUtil.endsWith(s, len, "快抄") || StemmerUtil.endsWith(s, len, "忸技") || StemmerUtil.endsWith(s, len, "快志") || StemmerUtil.endsWith(s, len, "我抄") || StemmerUtil.endsWith(s, len, "我技") || StemmerUtil.endsWith(s, len, "抉快") || StemmerUtil.endsWith(s, len, "抉抄") || StemmerUtil.endsWith(s, len, "抉技") || StemmerUtil.endsWith(s, len, "抉志") || StemmerUtil.endsWith(s, len, "抑快") || StemmerUtil.endsWith(s, len, "抑抄") || StemmerUtil.endsWith(s, len, "抑技") || StemmerUtil.endsWith(s, len, "技我")))
			return len - 2;
		if (len > 3)
			switch (s[len - 1])
			{
			case 1072: 
			case 1077: 
			case 1080: 
			case 1081: 
			case 1086: 
			case 1091: 
			case 1099: 
			case 1100: 
			case 1103: 
				return len - 1;
			}
		return len;
	}
}
