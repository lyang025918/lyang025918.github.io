// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GreekStemmer.java

package org.apache.lucene.analysis.el;

import java.util.Arrays;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class GreekStemmer
{

	private static final CharArraySet exc4;
	private static final CharArraySet exc6;
	private static final CharArraySet exc7;
	private static final CharArraySet exc8a;
	private static final CharArraySet exc8b;
	private static final CharArraySet exc9;
	private static final CharArraySet exc12a;
	private static final CharArraySet exc12b;
	private static final CharArraySet exc13;
	private static final CharArraySet exc14;
	private static final CharArraySet exc15a;
	private static final CharArraySet exc15b;
	private static final CharArraySet exc16;
	private static final CharArraySet exc17;
	private static final CharArraySet exc18;
	private static final CharArraySet exc19;

	public GreekStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 4)
			return len;
		int origLen = len;
		len = rule0(s, len);
		len = rule1(s, len);
		len = rule2(s, len);
		len = rule3(s, len);
		len = rule4(s, len);
		len = rule5(s, len);
		len = rule6(s, len);
		len = rule7(s, len);
		len = rule8(s, len);
		len = rule9(s, len);
		len = rule10(s, len);
		len = rule11(s, len);
		len = rule12(s, len);
		len = rule13(s, len);
		len = rule14(s, len);
		len = rule15(s, len);
		len = rule16(s, len);
		len = rule17(s, len);
		len = rule18(s, len);
		len = rule19(s, len);
		len = rule20(s, len);
		if (len == origLen)
			len = rule21(s, len);
		return rule22(s, len);
	}

	private int rule0(char s[], int len)
	{
		if (len > 9 && (endsWith(s, len, "¦Ê¦Á¦È¦Å¦Ò¦Ó¦Ø¦Ó¦Ï¦Ò") || endsWith(s, len, "¦Ê¦Á¦È¦Å¦Ò¦Ó¦Ø¦Ó¦Ø¦Í")))
			return len - 4;
		if (len > 8 && (endsWith(s, len, "¦Ã¦Å¦Ã¦Ï¦Í¦Ï¦Ó¦Ï¦Ò") || endsWith(s, len, "¦Ã¦Å¦Ã¦Ï¦Í¦Ï¦Ó¦Ø¦Í")))
			return len - 4;
		if (len > 8 && endsWith(s, len, "¦Ê¦Á¦È¦Å¦Ò¦Ó¦Ø¦Ó¦Á"))
			return len - 3;
		if (len > 7 && (endsWith(s, len, "¦Ó¦Á¦Ó¦Ï¦Ã¦É¦Ï¦Ô") || endsWith(s, len, "¦Ó¦Á¦Ó¦Ï¦Ã¦É¦Ø¦Í")))
			return len - 4;
		if (len > 7 && endsWith(s, len, "¦Ã¦Å¦Ã¦Ï¦Í¦Ï¦Ó¦Á"))
			return len - 3;
		if (len > 7 && endsWith(s, len, "¦Ê¦Á¦È¦Å¦Ò¦Ó¦Ø¦Ò"))
			return len - 2;
		if (len > 6 && endsWith(s, len, "¦Ò¦Ê¦Á¦Ã¦É¦Ï¦Ô") || endsWith(s, len, "¦Ò¦Ê¦Á¦Ã¦É¦Ø¦Í") || endsWith(s, len, "¦Ï¦Ë¦Ï¦Ã¦É¦Ï¦Ô") || endsWith(s, len, "¦Ï¦Ë¦Ï¦Ã¦É¦Ø¦Í") || endsWith(s, len, "¦Ê¦Ñ¦Å¦Á¦Ó¦Ï¦Ò") || endsWith(s, len, "¦Ê¦Ñ¦Å¦Á¦Ó¦Ø¦Í") || endsWith(s, len, "¦Ð¦Å¦Ñ¦Á¦Ó¦Ï¦Ò") || endsWith(s, len, "¦Ð¦Å¦Ñ¦Á¦Ó¦Ø¦Í") || endsWith(s, len, "¦Ó¦Å¦Ñ¦Á¦Ó¦Ï¦Ò") || endsWith(s, len, "¦Ó¦Å¦Ñ¦Á¦Ó¦Ø¦Í"))
			return len - 4;
		if (len > 6 && endsWith(s, len, "¦Ó¦Á¦Ó¦Ï¦Ã¦É¦Á"))
			return len - 3;
		if (len > 6 && endsWith(s, len, "¦Ã¦Å¦Ã¦Ï¦Í¦Ï¦Ò"))
			return len - 2;
		if (len > 5 && (endsWith(s, len, "¦Õ¦Á¦Ã¦É¦Ï¦Ô") || endsWith(s, len, "¦Õ¦Á¦Ã¦É¦Ø¦Í") || endsWith(s, len, "¦Ò¦Ï¦Ã¦É¦Ï¦Ô") || endsWith(s, len, "¦Ò¦Ï¦Ã¦É¦Ø¦Í")))
			return len - 4;
		if (len > 5 && (endsWith(s, len, "¦Ò¦Ê¦Á¦Ã¦É¦Á") || endsWith(s, len, "¦Ï¦Ë¦Ï¦Ã¦É¦Á") || endsWith(s, len, "¦Ê¦Ñ¦Å¦Á¦Ó¦Á") || endsWith(s, len, "¦Ð¦Å¦Ñ¦Á¦Ó¦Á") || endsWith(s, len, "¦Ó¦Å¦Ñ¦Á¦Ó¦Á")))
			return len - 3;
		if (len > 4 && (endsWith(s, len, "¦Õ¦Á¦Ã¦É¦Á") || endsWith(s, len, "¦Ò¦Ï¦Ã¦É¦Á") || endsWith(s, len, "¦Õ¦Ø¦Ó¦Ï¦Ò") || endsWith(s, len, "¦Õ¦Ø¦Ó¦Ø¦Í")))
			return len - 3;
		if (len > 4 && (endsWith(s, len, "¦Ê¦Ñ¦Å¦Á¦Ò") || endsWith(s, len, "¦Ð¦Å¦Ñ¦Á¦Ò") || endsWith(s, len, "¦Ó¦Å¦Ñ¦Á¦Ò")))
			return len - 2;
		if (len > 3 && endsWith(s, len, "¦Õ¦Ø¦Ó¦Á"))
			return len - 2;
		if (len > 2 && endsWith(s, len, "¦Õ¦Ø¦Ò"))
			return len - 1;
		else
			return len;
	}

	private int rule1(char s[], int len)
	{
		if (len > 4 && (endsWith(s, len, "¦Á¦Ä¦Å¦Ò") || endsWith(s, len, "¦Á¦Ä¦Ø¦Í")))
		{
			len -= 4;
			if (!endsWith(s, len, "¦Ï¦Ê") && !endsWith(s, len, "¦Ì¦Á¦Ì") && !endsWith(s, len, "¦Ì¦Á¦Í") && !endsWith(s, len, "¦Ì¦Ð¦Á¦Ì¦Ð") && !endsWith(s, len, "¦Ð¦Á¦Ó¦Å¦Ñ") && !endsWith(s, len, "¦Ã¦É¦Á¦Ã¦É") && !endsWith(s, len, "¦Í¦Ó¦Á¦Í¦Ó") && !endsWith(s, len, "¦Ê¦Ô¦Ñ") && !endsWith(s, len, "¦È¦Å¦É") && !endsWith(s, len, "¦Ð¦Å¦È¦Å¦Ñ"))
				len += 2;
		}
		return len;
	}

	private int rule2(char s[], int len)
	{
		if (len > 4 && (endsWith(s, len, "¦Å¦Ä¦Å¦Ò") || endsWith(s, len, "¦Å¦Ä¦Ø¦Í")))
		{
			len -= 4;
			if (endsWith(s, len, "¦Ï¦Ð") || endsWith(s, len, "¦É¦Ð") || endsWith(s, len, "¦Å¦Ì¦Ð") || endsWith(s, len, "¦Ô¦Ð") || endsWith(s, len, "¦Ã¦Ç¦Ð") || endsWith(s, len, "¦Ä¦Á¦Ð") || endsWith(s, len, "¦Ê¦Ñ¦Á¦Ò¦Ð") || endsWith(s, len, "¦Ì¦É¦Ë"))
				len += 2;
		}
		return len;
	}

	private int rule3(char s[], int len)
	{
		if (len > 5 && (endsWith(s, len, "¦Ï¦Ô¦Ä¦Å¦Ò") || endsWith(s, len, "¦Ï¦Ô¦Ä¦Ø¦Í")))
		{
			len -= 5;
			if (endsWith(s, len, "¦Á¦Ñ¦Ê") || endsWith(s, len, "¦Ê¦Á¦Ë¦É¦Á¦Ê") || endsWith(s, len, "¦Ð¦Å¦Ó¦Á¦Ë") || endsWith(s, len, "¦Ë¦É¦Ö") || endsWith(s, len, "¦Ð¦Ë¦Å¦Î") || endsWith(s, len, "¦Ò¦Ê") || endsWith(s, len, "¦Ò") || endsWith(s, len, "¦Õ¦Ë") || endsWith(s, len, "¦Õ¦Ñ") || endsWith(s, len, "¦Â¦Å¦Ë") || endsWith(s, len, "¦Ë¦Ï¦Ô¦Ë") || endsWith(s, len, "¦Ö¦Í") || endsWith(s, len, "¦Ò¦Ð") || endsWith(s, len, "¦Ó¦Ñ¦Á¦Ã") || endsWith(s, len, "¦Õ¦Å"))
				len += 3;
		}
		return len;
	}

	private int rule4(char s[], int len)
	{
		if (len > 3 && (endsWith(s, len, "¦Å¦Ø¦Ò") || endsWith(s, len, "¦Å¦Ø¦Í")))
		{
			len -= 3;
			if (exc4.contains(s, 0, len))
				len++;
		}
		return len;
	}

	private int rule5(char s[], int len)
	{
		if (len > 2 && endsWith(s, len, "¦É¦Á"))
		{
			len -= 2;
			if (endsWithVowel(s, len))
				len++;
		} else
		if (len > 3 && (endsWith(s, len, "¦É¦Ï¦Ô") || endsWith(s, len, "¦É¦Ø¦Í")))
		{
			len -= 3;
			if (endsWithVowel(s, len))
				len++;
		}
		return len;
	}

	private int rule6(char s[], int len)
	{
		boolean removed = false;
		if (len > 3 && (endsWith(s, len, "¦É¦Ê¦Á") || endsWith(s, len, "¦É¦Ê¦Ï")))
		{
			len -= 3;
			removed = true;
		} else
		if (len > 4 && (endsWith(s, len, "¦É¦Ê¦Ï¦Ô") || endsWith(s, len, "¦É¦Ê¦Ø¦Í")))
		{
			len -= 4;
			removed = true;
		}
		if (removed && (endsWithVowel(s, len) || exc6.contains(s, 0, len)))
			len += 2;
		return len;
	}

	private int rule7(char s[], int len)
	{
		if (len == 5 && endsWith(s, len, "¦Á¦Ã¦Á¦Ì¦Å"))
			return len - 1;
		if (len > 7 && endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Á¦Ì¦Å"))
			len -= 7;
		else
		if (len > 6 && endsWith(s, len, "¦Ï¦Ô¦Ò¦Á¦Ì¦Å"))
			len -= 6;
		else
		if (len > 5 && (endsWith(s, len, "¦Á¦Ã¦Á¦Ì¦Å") || endsWith(s, len, "¦Ç¦Ò¦Á¦Ì¦Å") || endsWith(s, len, "¦Ç¦Ê¦Á¦Ì¦Å")))
			len -= 5;
		if (len > 3 && endsWith(s, len, "¦Á¦Ì¦Å"))
		{
			len -= 3;
			if (exc7.contains(s, 0, len))
				len += 2;
		}
		return len;
	}

	private int rule8(char s[], int len)
	{
		boolean removed = false;
		if (len > 8 && endsWith(s, len, "¦É¦Ï¦Ô¦Í¦Ó¦Á¦Í¦Å"))
		{
			len -= 8;
			removed = true;
		} else
		if (len > 7 && endsWith(s, len, "¦É¦Ï¦Í¦Ó¦Á¦Í¦Å") || endsWith(s, len, "¦Ï¦Ô¦Í¦Ó¦Á¦Í¦Å") || endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Á¦Í¦Å"))
		{
			len -= 7;
			removed = true;
		} else
		if (len > 6 && endsWith(s, len, "¦É¦Ï¦Ó¦Á¦Í¦Å") || endsWith(s, len, "¦Ï¦Í¦Ó¦Á¦Í¦Å") || endsWith(s, len, "¦Ï¦Ô¦Ò¦Á¦Í¦Å"))
		{
			len -= 6;
			removed = true;
		} else
		if (len > 5 && endsWith(s, len, "¦Á¦Ã¦Á¦Í¦Å") || endsWith(s, len, "¦Ç¦Ò¦Á¦Í¦Å") || endsWith(s, len, "¦Ï¦Ó¦Á¦Í¦Å") || endsWith(s, len, "¦Ç¦Ê¦Á¦Í¦Å"))
		{
			len -= 5;
			removed = true;
		}
		if (removed && exc8a.contains(s, 0, len))
		{
			len += 4;
			s[len - 4] = '\u03B1';
			s[len - 3] = '\u03B3';
			s[len - 2] = '\u03B1';
			s[len - 1] = '\u03BD';
		}
		if (len > 3 && endsWith(s, len, "¦Á¦Í¦Å"))
		{
			len -= 3;
			if (endsWithVowelNoY(s, len) || exc8b.contains(s, 0, len))
				len += 2;
		}
		return len;
	}

	private int rule9(char s[], int len)
	{
		if (len > 5 && endsWith(s, len, "¦Ç¦Ò¦Å¦Ó¦Å"))
			len -= 5;
		if (len > 3 && endsWith(s, len, "¦Å¦Ó¦Å"))
		{
			len -= 3;
			if (exc9.contains(s, 0, len) || endsWithVowelNoY(s, len) || endsWith(s, len, "¦Ï¦Ä") || endsWith(s, len, "¦Á¦É¦Ñ") || endsWith(s, len, "¦Õ¦Ï¦Ñ") || endsWith(s, len, "¦Ó¦Á¦È") || endsWith(s, len, "¦Ä¦É¦Á¦È") || endsWith(s, len, "¦Ò¦Ö") || endsWith(s, len, "¦Å¦Í¦Ä") || endsWith(s, len, "¦Å¦Ô¦Ñ") || endsWith(s, len, "¦Ó¦É¦È") || endsWith(s, len, "¦Ô¦Ð¦Å¦Ñ¦È") || endsWith(s, len, "¦Ñ¦Á¦È") || endsWith(s, len, "¦Å¦Í¦È") || endsWith(s, len, "¦Ñ¦Ï¦È") || endsWith(s, len, "¦Ò¦È") || endsWith(s, len, "¦Ð¦Ô¦Ñ") || endsWith(s, len, "¦Á¦É¦Í") || endsWith(s, len, "¦Ò¦Ô¦Í¦Ä") || endsWith(s, len, "¦Ò¦Ô¦Í") || endsWith(s, len, "¦Ò¦Ô¦Í¦È") || endsWith(s, len, "¦Ö¦Ø¦Ñ") || endsWith(s, len, "¦Ð¦Ï¦Í") || endsWith(s, len, "¦Â¦Ñ") || endsWith(s, len, "¦Ê¦Á¦È") || endsWith(s, len, "¦Å¦Ô¦È") || endsWith(s, len, "¦Å¦Ê¦È") || endsWith(s, len, "¦Í¦Å¦Ó") || endsWith(s, len, "¦Ñ¦Ï¦Í") || endsWith(s, len, "¦Á¦Ñ¦Ê") || endsWith(s, len, "¦Â¦Á¦Ñ") || endsWith(s, len, "¦Â¦Ï¦Ë") || endsWith(s, len, "¦Ø¦Õ¦Å¦Ë"))
				len += 2;
		}
		return len;
	}

	private int rule10(char s[], int len)
	{
		if (len > 5 && (endsWith(s, len, "¦Ï¦Í¦Ó¦Á¦Ò") || endsWith(s, len, "¦Ø¦Í¦Ó¦Á¦Ò")))
		{
			if ((len -= 5) == 3 && endsWith(s, len, "¦Á¦Ñ¦Ö"))
			{
				len += 3;
				s[len - 3] = '\u03BF';
			}
			if (endsWith(s, len, "¦Ê¦Ñ¦Å"))
			{
				len += 3;
				s[len - 3] = '\u03C9';
			}
		}
		return len;
	}

	private int rule11(char s[], int len)
	{
		if (len > 6 && endsWith(s, len, "¦Ï¦Ì¦Á¦Ò¦Ó¦Å"))
		{
			if ((len -= 6) == 2 && endsWith(s, len, "¦Ï¦Í"))
				len += 5;
		} else
		if (len > 7 && endsWith(s, len, "¦É¦Ï¦Ì¦Á¦Ò¦Ó¦Å") && (len -= 7) == 2 && endsWith(s, len, "¦Ï¦Í"))
		{
			len += 5;
			s[len - 5] = '\u03BF';
			s[len - 4] = '\u03BC';
			s[len - 3] = '\u03B1';
			s[len - 2] = '\u03C3';
			s[len - 1] = '\u03C4';
		}
		return len;
	}

	private int rule12(char s[], int len)
	{
		if (len > 5 && endsWith(s, len, "¦É¦Å¦Ò¦Ó¦Å"))
		{
			len -= 5;
			if (exc12a.contains(s, 0, len))
				len += 4;
		}
		if (len > 4 && endsWith(s, len, "¦Å¦Ò¦Ó¦Å"))
		{
			len -= 4;
			if (exc12b.contains(s, 0, len))
				len += 3;
		}
		return len;
	}

	private int rule13(char s[], int len)
	{
		if (len > 6 && endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Å¦Ò"))
			len -= 6;
		else
		if (len > 5 && (endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Á") || endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Å")))
			len -= 5;
		boolean removed = false;
		if (len > 4 && endsWith(s, len, "¦Ç¦Ê¦Å¦Ò"))
		{
			len -= 4;
			removed = true;
		} else
		if (len > 3 && (endsWith(s, len, "¦Ç¦Ê¦Á") || endsWith(s, len, "¦Ç¦Ê¦Å")))
		{
			len -= 3;
			removed = true;
		}
		if (removed && (exc13.contains(s, 0, len) || endsWith(s, len, "¦Ò¦Ê¦Ø¦Ë") || endsWith(s, len, "¦Ò¦Ê¦Ï¦Ô¦Ë") || endsWith(s, len, "¦Í¦Á¦Ñ¦È") || endsWith(s, len, "¦Ò¦Õ") || endsWith(s, len, "¦Ï¦È") || endsWith(s, len, "¦Ð¦É¦È")))
			len += 2;
		return len;
	}

	private int rule14(char s[], int len)
	{
		boolean removed = false;
		if (len > 5 && endsWith(s, len, "¦Ï¦Ô¦Ò¦Å¦Ò"))
		{
			len -= 5;
			removed = true;
		} else
		if (len > 4 && (endsWith(s, len, "¦Ï¦Ô¦Ò¦Á") || endsWith(s, len, "¦Ï¦Ô¦Ò¦Å")))
		{
			len -= 4;
			removed = true;
		}
		if (removed && (exc14.contains(s, 0, len) || endsWithVowel(s, len) || endsWith(s, len, "¦Ð¦Ï¦Ä¦Á¦Ñ") || endsWith(s, len, "¦Â¦Ë¦Å¦Ð") || endsWith(s, len, "¦Ð¦Á¦Í¦Ó¦Á¦Ö") || endsWith(s, len, "¦Õ¦Ñ¦Ô¦Ä") || endsWith(s, len, "¦Ì¦Á¦Í¦Ó¦É¦Ë") || endsWith(s, len, "¦Ì¦Á¦Ë¦Ë") || endsWith(s, len, "¦Ê¦Ô¦Ì¦Á¦Ó") || endsWith(s, len, "¦Ë¦Á¦Ö") || endsWith(s, len, "¦Ë¦Ç¦Ã") || endsWith(s, len, "¦Õ¦Á¦Ã") || endsWith(s, len, "¦Ï¦Ì") || endsWith(s, len, "¦Ð¦Ñ¦Ø¦Ó")))
			len += 3;
		return len;
	}

	private int rule15(char s[], int len)
	{
		boolean removed = false;
		if (len > 4 && endsWith(s, len, "¦Á¦Ã¦Å¦Ò"))
		{
			len -= 4;
			removed = true;
		} else
		if (len > 3 && (endsWith(s, len, "¦Á¦Ã¦Á") || endsWith(s, len, "¦Á¦Ã¦Å")))
		{
			len -= 3;
			removed = true;
		}
		if (removed)
		{
			boolean cond1 = exc15a.contains(s, 0, len) || endsWith(s, len, "¦Ï¦Õ") || endsWith(s, len, "¦Ð¦Å¦Ë") || endsWith(s, len, "¦Ö¦Ï¦Ñ¦Ó") || endsWith(s, len, "¦Ë¦Ë") || endsWith(s, len, "¦Ò¦Õ") || endsWith(s, len, "¦Ñ¦Ð") || endsWith(s, len, "¦Õ¦Ñ") || endsWith(s, len, "¦Ð¦Ñ") || endsWith(s, len, "¦Ë¦Ï¦Ö") || endsWith(s, len, "¦Ò¦Ì¦Ç¦Í");
			boolean cond2 = exc15b.contains(s, 0, len) || endsWith(s, len, "¦Ê¦Ï¦Ë¦Ë");
			if (cond1 && !cond2)
				len += 2;
		}
		return len;
	}

	private int rule16(char s[], int len)
	{
		boolean removed = false;
		if (len > 4 && endsWith(s, len, "¦Ç¦Ò¦Ï¦Ô"))
		{
			len -= 4;
			removed = true;
		} else
		if (len > 3 && (endsWith(s, len, "¦Ç¦Ò¦Å") || endsWith(s, len, "¦Ç¦Ò¦Á")))
		{
			len -= 3;
			removed = true;
		}
		if (removed && exc16.contains(s, 0, len))
			len += 2;
		return len;
	}

	private int rule17(char s[], int len)
	{
		if (len > 4 && endsWith(s, len, "¦Ç¦Ò¦Ó¦Å"))
		{
			len -= 4;
			if (exc17.contains(s, 0, len))
				len += 3;
		}
		return len;
	}

	private int rule18(char s[], int len)
	{
		boolean removed = false;
		if (len > 6 && (endsWith(s, len, "¦Ç¦Ò¦Ï¦Ô¦Í¦Å") || endsWith(s, len, "¦Ç¦È¦Ï¦Ô¦Í¦Å")))
		{
			len -= 6;
			removed = true;
		} else
		if (len > 4 && endsWith(s, len, "¦Ï¦Ô¦Í¦Å"))
		{
			len -= 4;
			removed = true;
		}
		if (removed && exc18.contains(s, 0, len))
		{
			len += 3;
			s[len - 3] = '\u03BF';
			s[len - 2] = '\u03C5';
			s[len - 1] = '\u03BD';
		}
		return len;
	}

	private int rule19(char s[], int len)
	{
		boolean removed = false;
		if (len > 6 && (endsWith(s, len, "¦Ç¦Ò¦Ï¦Ô¦Ì¦Å") || endsWith(s, len, "¦Ç¦È¦Ï¦Ô¦Ì¦Å")))
		{
			len -= 6;
			removed = true;
		} else
		if (len > 4 && endsWith(s, len, "¦Ï¦Ô¦Ì¦Å"))
		{
			len -= 4;
			removed = true;
		}
		if (removed && exc19.contains(s, 0, len))
		{
			len += 3;
			s[len - 3] = '\u03BF';
			s[len - 2] = '\u03C5';
			s[len - 1] = '\u03BC';
		}
		return len;
	}

	private int rule20(char s[], int len)
	{
		if (len > 5 && (endsWith(s, len, "¦Ì¦Á¦Ó¦Ø¦Í") || endsWith(s, len, "¦Ì¦Á¦Ó¦Ï¦Ò")))
			len -= 3;
		else
		if (len > 4 && endsWith(s, len, "¦Ì¦Á¦Ó¦Á"))
			len -= 2;
		return len;
	}

	private int rule21(char s[], int len)
	{
		if (len > 9 && endsWith(s, len, "¦É¦Ï¦Í¦Ó¦Ï¦Ô¦Ò¦Á¦Í"))
			return len - 9;
		if (len > 8 && (endsWith(s, len, "¦É¦Ï¦Ì¦Á¦Ò¦Ó¦Á¦Í") || endsWith(s, len, "¦É¦Ï¦Ò¦Á¦Ò¦Ó¦Á¦Í") || endsWith(s, len, "¦É¦Ï¦Ô¦Ì¦Á¦Ò¦Ó¦Å") || endsWith(s, len, "¦Ï¦Í¦Ó¦Ï¦Ô¦Ò¦Á¦Í")))
			return len - 8;
		if (len > 7 && (endsWith(s, len, "¦É¦Å¦Ì¦Á¦Ò¦Ó¦Å") || endsWith(s, len, "¦É¦Å¦Ò¦Á¦Ò¦Ó¦Å") || endsWith(s, len, "¦É¦Ï¦Ì¦Ï¦Ô¦Í¦Á") || endsWith(s, len, "¦É¦Ï¦Ò¦Á¦Ò¦Ó¦Å") || endsWith(s, len, "¦É¦Ï¦Ò¦Ï¦Ô¦Í¦Á") || endsWith(s, len, "¦É¦Ï¦Ô¦Í¦Ó¦Á¦É") || endsWith(s, len, "¦É¦Ï¦Ô¦Í¦Ó¦Á¦Í") || endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Á¦Ó¦Å") || endsWith(s, len, "¦Ï¦Ì¦Á¦Ò¦Ó¦Á¦Í") || endsWith(s, len, "¦Ï¦Ò¦Á¦Ò¦Ó¦Á¦Í") || endsWith(s, len, "¦Ï¦Ô¦Ì¦Á¦Ò¦Ó¦Å")))
			return len - 7;
		if (len > 6 && (endsWith(s, len, "¦É¦Ï¦Ì¦Ï¦Ô¦Í") || endsWith(s, len, "¦É¦Ï¦Í¦Ó¦Á¦Í") || endsWith(s, len, "¦É¦Ï¦Ò¦Ï¦Ô¦Í") || endsWith(s, len, "¦Ç¦È¦Å¦É¦Ó¦Å") || endsWith(s, len, "¦Ç¦È¦Ç¦Ê¦Á¦Í") || endsWith(s, len, "¦Ï¦Ì¦Ï¦Ô¦Í¦Á") || endsWith(s, len, "¦Ï¦Ò¦Á¦Ò¦Ó¦Å") || endsWith(s, len, "¦Ï¦Ò¦Ï¦Ô¦Í¦Á") || endsWith(s, len, "¦Ï¦Ô¦Í¦Ó¦Á¦É") || endsWith(s, len, "¦Ï¦Ô¦Í¦Ó¦Á¦Í") || endsWith(s, len, "¦Ï¦Ô¦Ò¦Á¦Ó¦Å")))
			return len - 6;
		if (len > 5 && (endsWith(s, len, "¦Á¦Ã¦Á¦Ó¦Å") || endsWith(s, len, "¦É¦Å¦Ì¦Á¦É") || endsWith(s, len, "¦É¦Å¦Ó¦Á¦É") || endsWith(s, len, "¦É¦Å¦Ò¦Á¦É") || endsWith(s, len, "¦É¦Ï¦Ó¦Á¦Í") || endsWith(s, len, "¦É¦Ï¦Ô¦Ì¦Á") || endsWith(s, len, "¦Ç¦È¦Å¦É¦Ò") || endsWith(s, len, "¦Ç¦È¦Ï¦Ô¦Í") || endsWith(s, len, "¦Ç¦Ê¦Á¦Ó¦Å") || endsWith(s, len, "¦Ç¦Ò¦Á¦Ó¦Å") || endsWith(s, len, "¦Ç¦Ò¦Ï¦Ô¦Í") || endsWith(s, len, "¦Ï¦Ì¦Ï¦Ô¦Í") || endsWith(s, len, "¦Ï¦Í¦Ó¦Á¦É") || endsWith(s, len, "¦Ï¦Í¦Ó¦Á¦Í") || endsWith(s, len, "¦Ï¦Ò¦Ï¦Ô¦Í") || endsWith(s, len, "¦Ï¦Ô¦Ì¦Á¦É") || endsWith(s, len, "¦Ï¦Ô¦Ò¦Á¦Í")))
			return len - 5;
		if (len > 4 && (endsWith(s, len, "¦Á¦Ã¦Á¦Í") || endsWith(s, len, "¦Á¦Ì¦Á¦É") || endsWith(s, len, "¦Á¦Ò¦Á¦É") || endsWith(s, len, "¦Á¦Ó¦Á¦É") || endsWith(s, len, "¦Å¦É¦Ó¦Å") || endsWith(s, len, "¦Å¦Ò¦Á¦É") || endsWith(s, len, "¦Å¦Ó¦Á¦É") || endsWith(s, len, "¦Ç¦Ä¦Å¦Ò") || endsWith(s, len, "¦Ç¦Ä¦Ø¦Í") || endsWith(s, len, "¦Ç¦È¦Å¦É") || endsWith(s, len, "¦Ç¦Ê¦Á¦Í") || endsWith(s, len, "¦Ç¦Ò¦Á¦Í") || endsWith(s, len, "¦Ç¦Ò¦Å¦É") || endsWith(s, len, "¦Ç¦Ò¦Å¦Ò") || endsWith(s, len, "¦Ï¦Ì¦Á¦É") || endsWith(s, len, "¦Ï¦Ó¦Á¦Í")))
			return len - 4;
		if (len > 3 && (endsWith(s, len, "¦Á¦Å¦É") || endsWith(s, len, "¦Å¦É¦Ò") || endsWith(s, len, "¦Ç¦È¦Ø") || endsWith(s, len, "¦Ç¦Ò¦Ø") || endsWith(s, len, "¦Ï¦Ô¦Í") || endsWith(s, len, "¦Ï¦Ô¦Ò")))
			return len - 3;
		if (len > 2 && (endsWith(s, len, "¦Á¦Í") || endsWith(s, len, "¦Á¦Ò") || endsWith(s, len, "¦Á¦Ø") || endsWith(s, len, "¦Å¦É") || endsWith(s, len, "¦Å¦Ò") || endsWith(s, len, "¦Ç¦Ò") || endsWith(s, len, "¦Ï¦É") || endsWith(s, len, "¦Ï¦Ò") || endsWith(s, len, "¦Ï¦Ô") || endsWith(s, len, "¦Ô¦Ò") || endsWith(s, len, "¦Ø¦Í")))
			return len - 2;
		if (len > 1 && endsWithVowel(s, len))
			return len - 1;
		else
			return len;
	}

	private int rule22(char s[], int len)
	{
		if (endsWith(s, len, "¦Å¦Ò¦Ó¦Å¦Ñ") || endsWith(s, len, "¦Å¦Ò¦Ó¦Á¦Ó"))
			return len - 5;
		if (endsWith(s, len, "¦Ï¦Ó¦Å¦Ñ") || endsWith(s, len, "¦Ï¦Ó¦Á¦Ó") || endsWith(s, len, "¦Ô¦Ó¦Å¦Ñ") || endsWith(s, len, "¦Ô¦Ó¦Á¦Ó") || endsWith(s, len, "¦Ø¦Ó¦Å¦Ñ") || endsWith(s, len, "¦Ø¦Ó¦Á¦Ó"))
			return len - 4;
		else
			return len;
	}

	private boolean endsWith(char s[], int len, String suffix)
	{
		int suffixLen = suffix.length();
		if (suffixLen > len)
			return false;
		for (int i = suffixLen - 1; i >= 0; i--)
			if (s[len - (suffixLen - i)] != suffix.charAt(i))
				return false;

		return true;
	}

	private boolean endsWithVowel(char s[], int len)
	{
		if (len == 0)
			return false;
		switch (s[len - 1])
		{
		case 945: 
		case 949: 
		case 951: 
		case 953: 
		case 959: 
		case 965: 
		case 969: 
			return true;

		case 946: 
		case 947: 
		case 948: 
		case 950: 
		case 952: 
		case 954: 
		case 955: 
		case 956: 
		case 957: 
		case 958: 
		case 960: 
		case 961: 
		case 962: 
		case 963: 
		case 964: 
		case 966: 
		case 967: 
		case 968: 
		default:
			return false;
		}
	}

	private boolean endsWithVowelNoY(char s[], int len)
	{
		if (len == 0)
			return false;
		switch (s[len - 1])
		{
		case 945: 
		case 949: 
		case 951: 
		case 953: 
		case 959: 
		case 969: 
			return true;
		}
		return false;
	}

	static 
	{
		exc4 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦È", "¦Ä", "¦Å¦Ë", "¦Ã¦Á¦Ë", "¦Í", "¦Ð", "¦É¦Ä", "¦Ð¦Á¦Ñ"
		}), false);
		exc6 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Á¦Ë", "¦Á¦Ä", "¦Å¦Í¦Ä", "¦Á¦Ì¦Á¦Í", "¦Á¦Ì¦Ì¦Ï¦Ö¦Á¦Ë", "¦Ç¦È", "¦Á¦Í¦Ç¦È", "¦Á¦Í¦Ó¦É¦Ä", "¦Õ¦Ô¦Ò", "¦Â¦Ñ¦Ø¦Ì", 
			"¦Ã¦Å¦Ñ", "¦Å¦Î¦Ø¦Ä", "¦Ê¦Á¦Ë¦Ð", "¦Ê¦Á¦Ë¦Ë¦É¦Í", "¦Ê¦Á¦Ó¦Á¦Ä", "¦Ì¦Ï¦Ô¦Ë", "¦Ì¦Ð¦Á¦Í", "¦Ì¦Ð¦Á¦Ã¦É¦Á¦Ó", "¦Ì¦Ð¦Ï¦Ë", "¦Ì¦Ð¦Ï¦Ò", 
			"¦Í¦É¦Ó", "¦Î¦É¦Ê", "¦Ò¦Ô¦Í¦Ï¦Ì¦Ç¦Ë", "¦Ð¦Å¦Ó¦Ò", "¦Ð¦É¦Ó¦Ò", "¦Ð¦É¦Ê¦Á¦Í¦Ó", "¦Ð¦Ë¦É¦Á¦Ó¦Ò", "¦Ð¦Ï¦Ò¦Ó¦Å¦Ë¦Í", "¦Ð¦Ñ¦Ø¦Ó¦Ï¦Ä", "¦Ò¦Å¦Ñ¦Ó", 
			"¦Ò¦Ô¦Í¦Á¦Ä", "¦Ó¦Ò¦Á¦Ì", "¦Ô¦Ð¦Ï¦Ä", "¦Õ¦É¦Ë¦Ï¦Í", "¦Õ¦Ô¦Ë¦Ï¦Ä", "¦Ö¦Á¦Ò"
		}), false);
		exc7 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Á¦Í¦Á¦Ð", "¦Á¦Ð¦Ï¦È", "¦Á¦Ð¦Ï¦Ê", "¦Á¦Ð¦Ï¦Ò¦Ó", "¦Â¦Ï¦Ô¦Â", "¦Î¦Å¦È", "¦Ï¦Ô¦Ë", "¦Ð¦Å¦È", "¦Ð¦É¦Ê¦Ñ", "¦Ð¦Ï¦Ó", 
			"¦Ò¦É¦Ö", "¦Ö"
		}), false);
		exc8a = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Ó¦Ñ", "¦Ó¦Ò"
		}), false);
		exc8b = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Â¦Å¦Ó¦Å¦Ñ", "¦Â¦Ï¦Ô¦Ë¦Ê", "¦Â¦Ñ¦Á¦Ö¦Ì", "¦Ã", "¦Ä¦Ñ¦Á¦Ä¦Ï¦Ô¦Ì", "¦È", "¦Ê¦Á¦Ë¦Ð¦Ï¦Ô¦Æ", "¦Ê¦Á¦Ò¦Ó¦Å¦Ë", "¦Ê¦Ï¦Ñ¦Ì¦Ï¦Ñ", "¦Ë¦Á¦Ï¦Ð¦Ë", 
			"¦Ì¦Ø¦Á¦Ì¦Å¦È", "¦Ì", "¦Ì¦Ï¦Ô¦Ò¦Ï¦Ô¦Ë¦Ì", "¦Í", "¦Ï¦Ô¦Ë", "¦Ð", "¦Ð¦Å¦Ë¦Å¦Ê", "¦Ð¦Ë", "¦Ð¦Ï¦Ë¦É¦Ò", "¦Ð¦Ï¦Ñ¦Ó¦Ï¦Ë", 
			"¦Ò¦Á¦Ñ¦Á¦Ê¦Á¦Ó¦Ò", "¦Ò¦Ï¦Ô¦Ë¦Ó", "¦Ó¦Ò¦Á¦Ñ¦Ë¦Á¦Ó", "¦Ï¦Ñ¦Õ", "¦Ó¦Ò¦É¦Ã¦Ã", "¦Ó¦Ò¦Ï¦Ð", "¦Õ¦Ø¦Ó¦Ï¦Ò¦Ó¦Å¦Õ", "¦Ö", "¦×¦Ô¦Ö¦Ï¦Ð¦Ë", "¦Á¦Ã", 
			"¦Ï¦Ñ¦Õ", "¦Ã¦Á¦Ë", "¦Ã¦Å¦Ñ", "¦Ä¦Å¦Ê", "¦Ä¦É¦Ð¦Ë", "¦Á¦Ì¦Å¦Ñ¦É¦Ê¦Á¦Í", "¦Ï¦Ô¦Ñ", "¦Ð¦É¦È", "¦Ð¦Ï¦Ô¦Ñ¦É¦Ó", "¦Ò", 
			"¦Æ¦Ø¦Í¦Ó", "¦É¦Ê", "¦Ê¦Á¦Ò¦Ó", "¦Ê¦Ï¦Ð", "¦Ë¦É¦Ö", "¦Ë¦Ï¦Ô¦È¦Ç¦Ñ", "¦Ì¦Á¦É¦Í¦Ó", "¦Ì¦Å¦Ë", "¦Ò¦É¦Ã", "¦Ò¦Ð", 
			"¦Ò¦Ó¦Å¦Ã", "¦Ó¦Ñ¦Á¦Ã", "¦Ó¦Ò¦Á¦Ã", "¦Õ", "¦Å¦Ñ", "¦Á¦Ä¦Á¦Ð", "¦Á¦È¦É¦Ã¦Ã", "¦Á¦Ì¦Ç¦Ö", "¦Á¦Í¦É¦Ê", "¦Á¦Í¦Ï¦Ñ¦Ã", 
			"¦Á¦Ð¦Ç¦Ã", "¦Á¦Ð¦É¦È", "¦Á¦Ó¦Ò¦É¦Ã¦Ã", "¦Â¦Á¦Ò", "¦Â¦Á¦Ò¦Ê", "¦Â¦Á¦È¦Ô¦Ã¦Á¦Ë", "¦Â¦É¦Ï¦Ì¦Ç¦Ö", "¦Â¦Ñ¦Á¦Ö¦Ô¦Ê", "¦Ä¦É¦Á¦Ó", "¦Ä¦É¦Á¦Õ", 
			"¦Å¦Í¦Ï¦Ñ¦Ã", "¦È¦Ô¦Ò", "¦Ê¦Á¦Ð¦Í¦Ï¦Â¦É¦Ï¦Ì¦Ç¦Ö", "¦Ê¦Á¦Ó¦Á¦Ã¦Á¦Ë", "¦Ê¦Ë¦É¦Â", "¦Ê¦Ï¦É¦Ë¦Á¦Ñ¦Õ", "¦Ë¦É¦Â", "¦Ì¦Å¦Ã¦Ë¦Ï¦Â¦É¦Ï¦Ì¦Ç¦Ö", "¦Ì¦É¦Ê¦Ñ¦Ï¦Â¦É¦Ï¦Ì¦Ç¦Ö", "¦Í¦Ó¦Á¦Â", 
			"¦Î¦Ç¦Ñ¦Ï¦Ê¦Ë¦É¦Â", "¦Ï¦Ë¦É¦Ã¦Ï¦Ä¦Á¦Ì", "¦Ï¦Ë¦Ï¦Ã¦Á¦Ë", "¦Ð¦Å¦Í¦Ó¦Á¦Ñ¦Õ", "¦Ð¦Å¦Ñ¦Ç¦Õ", "¦Ð¦Å¦Ñ¦É¦Ó¦Ñ", "¦Ð¦Ë¦Á¦Ó", "¦Ð¦Ï¦Ë¦Ô¦Ä¦Á¦Ð", "¦Ð¦Ï¦Ë¦Ô¦Ì¦Ç¦Ö", "¦Ò¦Ó¦Å¦Õ", 
			"¦Ó¦Á¦Â", "¦Ó¦Å¦Ó", "¦Ô¦Ð¦Å¦Ñ¦Ç¦Õ", "¦Ô¦Ð¦Ï¦Ê¦Ï¦Ð", "¦Ö¦Á¦Ì¦Ç¦Ë¦Ï¦Ä¦Á¦Ð", "¦×¦Ç¦Ë¦Ï¦Ó¦Á¦Â"
		}), false);
		exc9 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Á¦Â¦Á¦Ñ", "¦Â¦Å¦Í", "¦Å¦Í¦Á¦Ñ", "¦Á¦Â¦Ñ", "¦Á¦Ä", "¦Á¦È", "¦Á¦Í", "¦Á¦Ð¦Ë", "¦Â¦Á¦Ñ¦Ï¦Í", "¦Í¦Ó¦Ñ", 
			"¦Ò¦Ê", "¦Ê¦Ï¦Ð", "¦Ì¦Ð¦Ï¦Ñ", "¦Í¦É¦Õ", "¦Ð¦Á¦Ã", "¦Ð¦Á¦Ñ¦Á¦Ê¦Á¦Ë", "¦Ò¦Å¦Ñ¦Ð", "¦Ò¦Ê¦Å¦Ë", "¦Ò¦Ô¦Ñ¦Õ", "¦Ó¦Ï¦Ê", 
			"¦Ô", "¦Ä", "¦Å¦Ì", "¦È¦Á¦Ñ¦Ñ", "¦È"
		}), false);
		exc12a = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Ð", "¦Á¦Ð", "¦Ò¦Ô¦Ì¦Ð", "¦Á¦Ò¦Ô¦Ì¦Ð", "¦Á¦Ê¦Á¦Ó¦Á¦Ð", "¦Á¦Ì¦Å¦Ó¦Á¦Ì¦Õ"
		}), false);
		exc12b = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Á¦Ë", "¦Á¦Ñ", "¦Å¦Ê¦Ó¦Å¦Ë", "¦Æ", "¦Ì", "¦Î", "¦Ð¦Á¦Ñ¦Á¦Ê¦Á¦Ë", "¦Á¦Ñ", "¦Ð¦Ñ¦Ï", "¦Í¦É¦Ò"
		}), false);
		exc13 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Ä¦É¦Á¦È", "¦È", "¦Ð¦Á¦Ñ¦Á¦Ê¦Á¦Ó¦Á¦È", "¦Ð¦Ñ¦Ï¦Ò¦È", "¦Ò¦Ô¦Í¦È"
		}), false);
		exc14 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Õ¦Á¦Ñ¦Ì¦Á¦Ê", "¦Ö¦Á¦Ä", "¦Á¦Ã¦Ê", "¦Á¦Í¦Á¦Ñ¦Ñ", "¦Â¦Ñ¦Ï¦Ì", "¦Å¦Ê¦Ë¦É¦Ð", "¦Ë¦Á¦Ì¦Ð¦É¦Ä", "¦Ë¦Å¦Ö", "¦Ì", "¦Ð¦Á¦Ó", 
			"¦Ñ", "¦Ë", "¦Ì¦Å¦Ä", "¦Ì¦Å¦Ò¦Á¦Æ", "¦Ô¦Ð¦Ï¦Ó¦Å¦É¦Í", "¦Á¦Ì", "¦Á¦É¦È", "¦Á¦Í¦Ç¦Ê", "¦Ä¦Å¦Ò¦Ð¦Ï¦Æ", "¦Å¦Í¦Ä¦É¦Á¦Õ¦Å¦Ñ", 
			"¦Ä¦Å", "¦Ä¦Å¦Ô¦Ó¦Å¦Ñ¦Å¦Ô", "¦Ê¦Á¦È¦Á¦Ñ¦Å¦Ô", "¦Ð¦Ë¦Å", "¦Ó¦Ò¦Á"
		}), false);
		exc15a = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Á¦Â¦Á¦Ò¦Ó", "¦Ð¦Ï¦Ë¦Ô¦Õ", "¦Á¦Ä¦Ç¦Õ", "¦Ð¦Á¦Ì¦Õ", "¦Ñ", "¦Á¦Ò¦Ð", "¦Á¦Õ", "¦Á¦Ì¦Á¦Ë", "¦Á¦Ì¦Á¦Ë¦Ë¦É", "¦Á¦Í¦Ô¦Ò¦Ó", 
			"¦Á¦Ð¦Å¦Ñ", "¦Á¦Ò¦Ð¦Á¦Ñ", "¦Á¦Ö¦Á¦Ñ", "¦Ä¦Å¦Ñ¦Â¦Å¦Í", "¦Ä¦Ñ¦Ï¦Ò¦Ï¦Ð", "¦Î¦Å¦Õ", "¦Í¦Å¦Ï¦Ð", "¦Í¦Ï¦Ì¦Ï¦Ó", "¦Ï¦Ë¦Ï¦Ð", "¦Ï¦Ì¦Ï¦Ó", 
			"¦Ð¦Ñ¦Ï¦Ò¦Ó", "¦Ð¦Ñ¦Ï¦Ò¦Ø¦Ð¦Ï¦Ð", "¦Ò¦Ô¦Ì¦Ð", "¦Ò¦Ô¦Í¦Ó", "¦Ó", "¦Ô¦Ð¦Ï¦Ó", "¦Ö¦Á¦Ñ", "¦Á¦Å¦É¦Ð", "¦Á¦É¦Ì¦Ï¦Ò¦Ó", "¦Á¦Í¦Ô¦Ð", 
			"¦Á¦Ð¦Ï¦Ó", "¦Á¦Ñ¦Ó¦É¦Ð", "¦Ä¦É¦Á¦Ó", "¦Å¦Í", "¦Å¦Ð¦É¦Ó", "¦Ê¦Ñ¦Ï¦Ê¦Á¦Ë¦Ï¦Ð", "¦Ò¦É¦Ä¦Ç¦Ñ¦Ï¦Ð", "¦Ë", "¦Í¦Á¦Ô", "¦Ï¦Ô¦Ë¦Á¦Ì", 
			"¦Ï¦Ô¦Ñ", "¦Ð", "¦Ó¦Ñ", "¦Ì"
		}), false);
		exc15b = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦×¦Ï¦Õ", "¦Í¦Á¦Ô¦Ë¦Ï¦Ö"
		}), false);
		exc16 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Í", "¦Ö¦Å¦Ñ¦Ò¦Ï¦Í", "¦Ä¦Ø¦Ä¦Å¦Ê¦Á¦Í", "¦Å¦Ñ¦Ç¦Ì¦Ï¦Í", "¦Ì¦Å¦Ã¦Á¦Ë¦Ï¦Í", "¦Å¦Ð¦Ó¦Á¦Í"
		}), false);
		exc17 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Á¦Ò¦Â", "¦Ò¦Â", "¦Á¦Ö¦Ñ", "¦Ö¦Ñ", "¦Á¦Ð¦Ë", "¦Á¦Å¦É¦Ì¦Í", "¦Ä¦Ô¦Ò¦Ö¦Ñ", "¦Å¦Ô¦Ö¦Ñ", "¦Ê¦Ï¦É¦Í¦Ï¦Ö¦Ñ", "¦Ð¦Á¦Ë¦É¦Ì¦×"
		}), false);
		exc18 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Í", "¦Ñ", "¦Ò¦Ð¦É", "¦Ò¦Ó¦Ñ¦Á¦Â¦Ï¦Ì¦Ï¦Ô¦Ó¦Ò", "¦Ê¦Á¦Ê¦Ï¦Ì¦Ï¦Ô¦Ó¦Ò", "¦Å¦Î¦Ø¦Í"
		}), false);
		exc19 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"¦Ð¦Á¦Ñ¦Á¦Ò¦Ï¦Ô¦Ò", "¦Õ", "¦Ö", "¦Ø¦Ñ¦É¦Ï¦Ð¦Ë", "¦Á¦Æ", "¦Á¦Ë¦Ë¦Ï¦Ò¦Ï¦Ô¦Ò", "¦Á¦Ò¦Ï¦Ô¦Ò"
		}), false);
	}
}
