// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanMinimalStemmer.java

package org.apache.lucene.analysis.de;


public class GermanMinimalStemmer
{

	public GermanMinimalStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 5)
			return len;
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 228: 
				s[i] = 'a';
				break;

			case 246: 
				s[i] = 'o';
				break;

			case 252: 
				s[i] = 'u';
				break;
			}

		if (len > 6 && s[len - 3] == 'n' && s[len - 2] == 'e' && s[len - 1] == 'n')
			return len - 3;
		if (len > 5)
			switch (s[len - 1])
			{
			case 110: // 'n'
				if (s[len - 2] == 'e')
					return len - 2;
				break;

			case 101: // 'e'
				if (s[len - 2] == 's')
					return len - 2;
				break;

			case 115: // 's'
				if (s[len - 2] == 'e')
					return len - 2;
				break;

			case 114: // 'r'
				if (s[len - 2] == 'e')
					return len - 2;
				break;
			}
		switch (s[len - 1])
		{
		case 101: // 'e'
		case 110: // 'n'
		case 114: // 'r'
		case 115: // 's'
			return len - 1;
		}
		return len;
	}
}
