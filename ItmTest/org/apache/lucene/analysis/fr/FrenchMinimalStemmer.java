// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchMinimalStemmer.java

package org.apache.lucene.analysis.fr;


public class FrenchMinimalStemmer
{

	public FrenchMinimalStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 6)
			return len;
		if (s[len - 1] == 'x')
		{
			if (s[len - 3] == 'a' && s[len - 2] == 'u')
				s[len - 2] = 'l';
			return len - 1;
		}
		if (s[len - 1] == 's')
			len--;
		if (s[len - 1] == 'r')
			len--;
		if (s[len - 1] == 'e')
			len--;
		if (s[len - 1] == '\351')
			len--;
		if (s[len - 1] == s[len - 2])
			len--;
		return len;
	}
}
