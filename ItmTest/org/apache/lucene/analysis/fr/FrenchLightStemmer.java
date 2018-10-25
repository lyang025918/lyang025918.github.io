// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchLightStemmer.java

package org.apache.lucene.analysis.fr;

import org.apache.lucene.analysis.util.StemmerUtil;

public class FrenchLightStemmer
{

	public FrenchLightStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len > 5 && s[len - 1] == 'x')
		{
			if (s[len - 3] == 'a' && s[len - 2] == 'u' && s[len - 4] != 'e')
				s[len - 2] = 'l';
			len--;
		}
		if (len > 3 && s[len - 1] == 'x')
			len--;
		if (len > 3 && s[len - 1] == 's')
			len--;
		if (len > 9 && StemmerUtil.endsWith(s, len, "issement"))
		{
			len -= 6;
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 8 && StemmerUtil.endsWith(s, len, "issant"))
		{
			len -= 4;
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 6 && StemmerUtil.endsWith(s, len, "ement"))
		{
			if ((len -= 4) > 3 && StemmerUtil.endsWith(s, len, "ive"))
			{
				len--;
				s[len - 1] = 'f';
			}
			return norm(s, len);
		}
		if (len > 11 && StemmerUtil.endsWith(s, len, "ficatrice"))
		{
			len -= 5;
			s[len - 2] = 'e';
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 10 && StemmerUtil.endsWith(s, len, "ficateur"))
		{
			len -= 4;
			s[len - 2] = 'e';
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 9 && StemmerUtil.endsWith(s, len, "catrice"))
		{
			len -= 3;
			s[len - 4] = 'q';
			s[len - 3] = 'u';
			s[len - 2] = 'e';
			return norm(s, len);
		}
		if (len > 8 && StemmerUtil.endsWith(s, len, "cateur"))
		{
			len -= 2;
			s[len - 4] = 'q';
			s[len - 3] = 'u';
			s[len - 2] = 'e';
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 8 && StemmerUtil.endsWith(s, len, "atrice"))
		{
			len -= 4;
			s[len - 2] = 'e';
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 7 && StemmerUtil.endsWith(s, len, "ateur"))
		{
			len -= 3;
			s[len - 2] = 'e';
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 6 && StemmerUtil.endsWith(s, len, "trice"))
		{
			len--;
			s[len - 3] = 'e';
			s[len - 2] = 'u';
			s[len - 1] = 'r';
		}
		if (len > 5 && StemmerUtil.endsWith(s, len, "i\350me"))
			return norm(s, len - 4);
		if (len > 7 && StemmerUtil.endsWith(s, len, "teuse"))
		{
			len -= 2;
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 6 && StemmerUtil.endsWith(s, len, "teur"))
		{
			len--;
			s[len - 1] = 'r';
			return norm(s, len);
		}
		if (len > 5 && StemmerUtil.endsWith(s, len, "euse"))
			return norm(s, len - 2);
		if (len > 8 && StemmerUtil.endsWith(s, len, "\350re"))
		{
			len--;
			s[len - 2] = 'e';
			return norm(s, len);
		}
		if (len > 7 && StemmerUtil.endsWith(s, len, "ive"))
		{
			len--;
			s[len - 1] = 'f';
			return norm(s, len);
		}
		if (len > 4 && (StemmerUtil.endsWith(s, len, "folle") || StemmerUtil.endsWith(s, len, "molle")))
		{
			len -= 2;
			s[len - 1] = 'u';
			return norm(s, len);
		}
		if (len > 9 && StemmerUtil.endsWith(s, len, "nnelle"))
			return norm(s, len - 5);
		if (len > 9 && StemmerUtil.endsWith(s, len, "nnel"))
			return norm(s, len - 3);
		if (len > 4 && StemmerUtil.endsWith(s, len, "\350te"))
		{
			len--;
			s[len - 2] = 'e';
		}
		if (len > 8 && StemmerUtil.endsWith(s, len, "ique"))
			len -= 4;
		if (len > 8 && StemmerUtil.endsWith(s, len, "esse"))
			return norm(s, len - 3);
		if (len > 7 && StemmerUtil.endsWith(s, len, "inage"))
			return norm(s, len - 3);
		if (len > 9 && StemmerUtil.endsWith(s, len, "isation"))
		{
			if ((len -= 7) > 5 && StemmerUtil.endsWith(s, len, "ual"))
				s[len - 2] = 'e';
			return norm(s, len);
		}
		if (len > 9 && StemmerUtil.endsWith(s, len, "isateur"))
			return norm(s, len - 7);
		if (len > 8 && StemmerUtil.endsWith(s, len, "ation"))
			return norm(s, len - 5);
		if (len > 8 && StemmerUtil.endsWith(s, len, "ition"))
			return norm(s, len - 5);
		else
			return norm(s, len);
	}

	private int norm(char s[], int len)
	{
		if (len > 4)
		{
			for (int i = 0; i < len; i++)
				switch (s[i])
				{
				case 224: 
				case 225: 
				case 226: 
					s[i] = 'a';
					break;

				case 244: 
					s[i] = 'o';
					break;

				case 232: 
				case 233: 
				case 234: 
					s[i] = 'e';
					break;

				case 249: 
				case 251: 
					s[i] = 'u';
					break;

				case 238: 
					s[i] = 'i';
					break;

				case 231: 
					s[i] = 'c';
					break;
				}

			char ch = s[0];
			for (int i = 1; i < len; i++)
				if (s[i] == ch && Character.isLetter(ch))
					len = StemmerUtil.delete(s, i--, len);
				else
					ch = s[i];

		}
		if (len > 4 && StemmerUtil.endsWith(s, len, "ie"))
			len -= 2;
		if (len > 4)
		{
			if (s[len - 1] == 'r')
				len--;
			if (s[len - 1] == 'e')
				len--;
			if (s[len - 1] == 'e')
				len--;
			if (s[len - 1] == s[len - 2] && Character.isLetter(s[len - 1]))
				len--;
		}
		return len;
	}
}
