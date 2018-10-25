// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItalianLightStemmer.java

package org.apache.lucene.analysis.it;


public class ItalianLightStemmer
{

	public ItalianLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 6)
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
		case 101: // 'e'
			if (s[len - 2] == 'i' || s[len - 2] == 'h')
				return len - 2;
			else
				return len - 1;

		case 105: // 'i'
			if (s[len - 2] == 'h' || s[len - 2] == 'i')
				return len - 2;
			else
				return len - 1;

		case 97: // 'a'
			if (s[len - 2] == 'i')
				return len - 2;
			else
				return len - 1;

		case 111: // 'o'
			if (s[len - 2] == 'i')
				return len - 2;
			else
				return len - 1;
		}
		return len;
	}
}
