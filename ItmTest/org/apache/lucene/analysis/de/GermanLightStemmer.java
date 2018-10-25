// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanLightStemmer.java

package org.apache.lucene.analysis.de;


public class GermanLightStemmer
{

	public GermanLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
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

			case 236: 
			case 237: 
			case 238: 
			case 239: 
				s[i] = 'i';
				break;

			case 249: 
			case 250: 
			case 251: 
			case 252: 
				s[i] = 'u';
				break;
			}

		len = step1(s, len);
		return step2(s, len);
	}

	private boolean stEnding(char ch)
	{
		switch (ch)
		{
		case 98: // 'b'
		case 100: // 'd'
		case 102: // 'f'
		case 103: // 'g'
		case 104: // 'h'
		case 107: // 'k'
		case 108: // 'l'
		case 109: // 'm'
		case 110: // 'n'
		case 116: // 't'
			return true;

		case 99: // 'c'
		case 101: // 'e'
		case 105: // 'i'
		case 106: // 'j'
		case 111: // 'o'
		case 112: // 'p'
		case 113: // 'q'
		case 114: // 'r'
		case 115: // 's'
		default:
			return false;
		}
	}

	private int step1(char s[], int len)
	{
		if (len > 5 && s[len - 3] == 'e' && s[len - 2] == 'r' && s[len - 1] == 'n')
			return len - 3;
		if (len > 4 && s[len - 2] == 'e')
			switch (s[len - 1])
			{
			case 109: // 'm'
			case 110: // 'n'
			case 114: // 'r'
			case 115: // 's'
				return len - 2;
			}
		if (len > 3 && s[len - 1] == 'e')
			return len - 1;
		if (len > 3 && s[len - 1] == 's' && stEnding(s[len - 2]))
			return len - 1;
		else
			return len;
	}

	private int step2(char s[], int len)
	{
		if (len > 5 && s[len - 3] == 'e' && s[len - 2] == 's' && s[len - 1] == 't')
			return len - 3;
		if (len > 4 && s[len - 2] == 'e' && (s[len - 1] == 'r' || s[len - 1] == 'n'))
			return len - 2;
		if (len > 4 && s[len - 2] == 's' && s[len - 1] == 't' && stEnding(s[len - 3]))
			return len - 2;
		else
			return len;
	}
}
