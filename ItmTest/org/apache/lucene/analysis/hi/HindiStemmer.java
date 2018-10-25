// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HindiStemmer.java

package org.apache.lucene.analysis.hi;

import org.apache.lucene.analysis.util.StemmerUtil;

public class HindiStemmer
{

	public HindiStemmer()
	{
	}

	public int stem(char buffer[], int len)
	{
		if (len > 6 && (StemmerUtil.endsWith(buffer, len, "?????") || StemmerUtil.endsWith(buffer, len, "?????") || StemmerUtil.endsWith(buffer, len, "?????") || StemmerUtil.endsWith(buffer, len, "?????") || StemmerUtil.endsWith(buffer, len, "?????") || StemmerUtil.endsWith(buffer, len, "?????") || StemmerUtil.endsWith(buffer, len, "?????")))
			return len - 5;
		if (len > 5 && (StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????") || StemmerUtil.endsWith(buffer, len, "????")))
			return len - 4;
		if (len > 4 && (StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???") || StemmerUtil.endsWith(buffer, len, "???")))
			return len - 3;
		if (len > 3 && (StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??") || StemmerUtil.endsWith(buffer, len, "??")))
			return len - 2;
		if (len > 2 && (StemmerUtil.endsWith(buffer, len, "?") || StemmerUtil.endsWith(buffer, len, "?") || StemmerUtil.endsWith(buffer, len, "?") || StemmerUtil.endsWith(buffer, len, "?") || StemmerUtil.endsWith(buffer, len, "?") || StemmerUtil.endsWith(buffer, len, "?") || StemmerUtil.endsWith(buffer, len, "?")))
			return len - 1;
		else
			return len;
	}
}
