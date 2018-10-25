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
		if (len > 6 && (StemmerUtil.endsWith(s, len, "�ڧ�ާ�") || StemmerUtil.endsWith(s, len, "���ާ�")))
			return len - 4;
		if (len > 5 && (StemmerUtil.endsWith(s, len, "�ڧ��") || StemmerUtil.endsWith(s, len, "�ڧ��") || StemmerUtil.endsWith(s, len, "����") || StemmerUtil.endsWith(s, len, "��ާ�") || StemmerUtil.endsWith(s, len, "����") || StemmerUtil.endsWith(s, len, "����") || StemmerUtil.endsWith(s, len, "�ѧާ�") || StemmerUtil.endsWith(s, len, "�֧ԧ�") || StemmerUtil.endsWith(s, len, "�֧ާ�") || StemmerUtil.endsWith(s, len, "�֧��") || StemmerUtil.endsWith(s, len, "�ڧާ�") || StemmerUtil.endsWith(s, len, "��ԧ�") || StemmerUtil.endsWith(s, len, "��ާ�") || StemmerUtil.endsWith(s, len, "��ާ�") || StemmerUtil.endsWith(s, len, "��֧�")))
			return len - 3;
		if (len > 4 && (StemmerUtil.endsWith(s, len, "�ѧ�") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "�ѧ�") || StemmerUtil.endsWith(s, len, "�֧�") || StemmerUtil.endsWith(s, len, "�ڧ�") || StemmerUtil.endsWith(s, len, "�ڧ�") || StemmerUtil.endsWith(s, len, "�ڧ�") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "�֧�") || StemmerUtil.endsWith(s, len, "�ѧ�") || StemmerUtil.endsWith(s, len, "�֧�") || StemmerUtil.endsWith(s, len, "�֧�") || StemmerUtil.endsWith(s, len, "�ק�") || StemmerUtil.endsWith(s, len, "�֧�") || StemmerUtil.endsWith(s, len, "�ڧ�") || StemmerUtil.endsWith(s, len, "�ڧ�") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "���") || StemmerUtil.endsWith(s, len, "�ާ�")))
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
