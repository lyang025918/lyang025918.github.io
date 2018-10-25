// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnglishMinimalStemmer.java

package org.apache.lucene.analysis.en;


public class EnglishMinimalStemmer
{

	public EnglishMinimalStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 3 || s[len - 1] != 's')
			return len;
		switch (s[len - 2])
		{
		case 115: // 's'
		case 117: // 'u'
			return len;

		case 101: // 'e'
			if (len > 3 && s[len - 3] == 'i' && s[len - 4] != 'a' && s[len - 4] != 'e')
			{
				s[len - 3] = 'y';
				return len - 2;
			}
			if (s[len - 3] == 'i' || s[len - 3] == 'a' || s[len - 3] == 'o' || s[len - 3] == 'e')
				return len;
			break;
		}
		return len - 1;
	}
}
