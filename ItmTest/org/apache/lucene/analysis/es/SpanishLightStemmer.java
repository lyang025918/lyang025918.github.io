// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanishLightStemmer.java

package org.apache.lucene.analysis.es;


public class SpanishLightStemmer
{

	public SpanishLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 5)
			return len;
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 224: 
			case 225: 
			case 226: 
			case 228: 
				s[i] = 'a';
				break;

			case 242: 
			case 243: 
			case 244: 
			case 246: 
				s[i] = 'o';
				break;

			case 232: 
			case 233: 
			case 234: 
			case 235: 
				s[i] = 'e';
				break;

			case 249: 
			case 250: 
			case 251: 
			case 252: 
				s[i] = 'u';
				break;

			case 236: 
			case 237: 
			case 238: 
			case 239: 
				s[i] = 'i';
				break;
			}

		switch (s[len - 1])
		{
		case 97: // 'a'
		case 101: // 'e'
		case 111: // 'o'
			return len - 1;

		case 115: // 's'
			if (s[len - 2] == 'e' && s[len - 3] == 's' && s[len - 4] == 'e')
				return len - 2;
			if (s[len - 2] == 'e' && s[len - 3] == 'c')
			{
				s[len - 3] = 'z';
				return len - 2;
			}
			if (s[len - 2] == 'o' || s[len - 2] == 'a' || s[len - 2] == 'e')
				return len - 2;
			break;
		}
		return len;
	}
}
