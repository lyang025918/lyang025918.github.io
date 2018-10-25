// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DutchStemmer.java

package org.apache.lucene.analysis.nl;

import java.util.Locale;
import java.util.Map;

/**
 * @deprecated Class DutchStemmer is deprecated
 */

public class DutchStemmer
{

	private static final Locale locale = new Locale("nl", "NL");
	private StringBuilder sb;
	private boolean _removedE;
	private Map _stemDict;
	private int _R1;
	private int _R2;

	public DutchStemmer()
	{
		sb = new StringBuilder();
	}

	public String stem(String term)
	{
		term = term.toLowerCase(locale);
		if (!isStemmable(term))
			return term;
		if (_stemDict != null && _stemDict.containsKey(term))
		{
			if (_stemDict.get(term) instanceof String)
				return (String)_stemDict.get(term);
			else
				return null;
		} else
		{
			sb.delete(0, sb.length());
			sb.insert(0, term);
			substitute(sb);
			storeYandI(sb);
			_R1 = getRIndex(sb, 0);
			_R1 = Math.max(3, _R1);
			step1(sb);
			step2(sb);
			_R2 = getRIndex(sb, _R1);
			step3a(sb);
			step3b(sb);
			step4(sb);
			reStoreYandI(sb);
			return sb.toString();
		}
	}

	private boolean enEnding(StringBuilder sb)
	{
		String enend[] = {
			"ene", "en"
		};
		for (int i = 0; i < enend.length; i++)
		{
			String end = enend[i];
			String s = sb.toString();
			int index = s.length() - end.length();
			if (s.endsWith(end) && index >= _R1 && isValidEnEnding(sb, index - 1))
			{
				sb.delete(index, index + end.length());
				unDouble(sb, index);
				return true;
			}
		}

		return false;
	}

	private void step1(StringBuilder sb)
	{
		if (_R1 >= sb.length())
			return;
		String s = sb.toString();
		int lengthR1 = sb.length() - _R1;
		if (s.endsWith("heden"))
		{
			sb.replace(_R1, lengthR1 + _R1, sb.substring(_R1, lengthR1 + _R1).replaceAll("heden", "heid"));
			return;
		}
		if (enEnding(sb))
			return;
		int index;
		if (s.endsWith("se") && (index = s.length() - 2) >= _R1 && isValidSEnding(sb, index - 1))
		{
			sb.delete(index, index + 2);
			return;
		}
		if (s.endsWith("s") && (index = s.length() - 1) >= _R1 && isValidSEnding(sb, index - 1))
			sb.delete(index, index + 1);
	}

	private void step2(StringBuilder sb)
	{
		_removedE = false;
		if (_R1 >= sb.length())
			return;
		String s = sb.toString();
		int index = s.length() - 1;
		if (index >= _R1 && s.endsWith("e") && !isVowel(sb.charAt(index - 1)))
		{
			sb.delete(index, index + 1);
			unDouble(sb);
			_removedE = true;
		}
	}

	private void step3a(StringBuilder sb)
	{
		if (_R2 >= sb.length())
			return;
		String s = sb.toString();
		int index = s.length() - 4;
		if (s.endsWith("heid") && index >= _R2 && sb.charAt(index - 1) != 'c')
		{
			sb.delete(index, index + 4);
			enEnding(sb);
		}
	}

	private void step3b(StringBuilder sb)
	{
		if (_R2 >= sb.length())
			return;
		String s = sb.toString();
		int index = 0;
		if ((s.endsWith("end") || s.endsWith("ing")) && (index = s.length() - 3) >= _R2)
		{
			sb.delete(index, index + 3);
			if (sb.charAt(index - 2) == 'i' && sb.charAt(index - 1) == 'g')
			{
				if ((sb.charAt(index - 3) != 'e') & (index - 2 >= _R2))
				{
					index -= 2;
					sb.delete(index, index + 2);
				}
			} else
			{
				unDouble(sb, index);
			}
			return;
		}
		if (s.endsWith("ig") && (index = s.length() - 2) >= _R2)
		{
			if (sb.charAt(index - 1) != 'e')
				sb.delete(index, index + 2);
			return;
		}
		if (s.endsWith("lijk") && (index = s.length() - 4) >= _R2)
		{
			sb.delete(index, index + 4);
			step2(sb);
			return;
		}
		if (s.endsWith("baar") && (index = s.length() - 4) >= _R2)
		{
			sb.delete(index, index + 4);
			return;
		}
		if (s.endsWith("bar") && (index = s.length() - 3) >= _R2)
		{
			if (_removedE)
				sb.delete(index, index + 3);
			return;
		} else
		{
			return;
		}
	}

	private void step4(StringBuilder sb)
	{
		if (sb.length() < 4)
			return;
		String end = sb.substring(sb.length() - 4, sb.length());
		char c = end.charAt(0);
		char v1 = end.charAt(1);
		char v2 = end.charAt(2);
		char d = end.charAt(3);
		if (v1 == v2 && d != 'I' && v1 != 'i' && isVowel(v1) && !isVowel(d) && !isVowel(c))
			sb.delete(sb.length() - 2, sb.length() - 1);
	}

	private boolean isStemmable(String term)
	{
		for (int c = 0; c < term.length(); c++)
			if (!Character.isLetter(term.charAt(c)))
				return false;

		return true;
	}

	private void substitute(StringBuilder buffer)
	{
		for (int i = 0; i < buffer.length(); i++)
			switch (buffer.charAt(i))
			{
			case 225: 
			case 228: 
				buffer.setCharAt(i, 'a');
				break;

			case 233: 
			case 235: 
				buffer.setCharAt(i, 'e');
				break;

			case 250: 
			case 252: 
				buffer.setCharAt(i, 'u');
				break;

			case 105: // 'i'
			case 239: 
				buffer.setCharAt(i, 'i');
				break;

			case 243: 
			case 246: 
				buffer.setCharAt(i, 'o');
				break;
			}

	}

	private boolean isValidSEnding(StringBuilder sb, int index)
	{
		char c = sb.charAt(index);
		return !isVowel(c) && c != 'j';
	}

	private boolean isValidEnEnding(StringBuilder sb, int index)
	{
		char c = sb.charAt(index);
		if (isVowel(c))
			return false;
		if (c < '\003')
			return false;
		return c != 'm' || sb.charAt(index - 2) != 'g' || sb.charAt(index - 1) != 'e';
	}

	private void unDouble(StringBuilder sb)
	{
		unDouble(sb, sb.length());
	}

	private void unDouble(StringBuilder sb, int endIndex)
	{
		String s = sb.substring(0, endIndex);
		if (s.endsWith("kk") || s.endsWith("tt") || s.endsWith("dd") || s.endsWith("nn") || s.endsWith("mm") || s.endsWith("ff"))
			sb.delete(endIndex - 1, endIndex);
	}

	private int getRIndex(StringBuilder sb, int start)
	{
		if (start == 0)
			start = 1;
		int i;
		for (i = start; i < sb.length(); i++)
			if (!isVowel(sb.charAt(i)) && isVowel(sb.charAt(i - 1)))
				return i + 1;

		return i + 1;
	}

	private void storeYandI(StringBuilder sb)
	{
		if (sb.charAt(0) == 'y')
			sb.setCharAt(0, 'Y');
		int last = sb.length() - 1;
		for (int i = 1; i < last; i++)
			switch (sb.charAt(i))
			{
			default:
				break;

			case 105: // 'i'
				if (isVowel(sb.charAt(i - 1)) && isVowel(sb.charAt(i + 1)))
					sb.setCharAt(i, 'I');
				break;

			case 121: // 'y'
				if (isVowel(sb.charAt(i - 1)))
					sb.setCharAt(i, 'Y');
				break;
			}

		if (last > 0 && sb.charAt(last) == 'y' && isVowel(sb.charAt(last - 1)))
			sb.setCharAt(last, 'Y');
	}

	private void reStoreYandI(StringBuilder sb)
	{
		String tmp = sb.toString();
		sb.delete(0, sb.length());
		sb.insert(0, tmp.replaceAll("I", "i").replaceAll("Y", "y"));
	}

	private boolean isVowel(char c)
	{
		switch (c)
		{
		case 97: // 'a'
		case 101: // 'e'
		case 105: // 'i'
		case 111: // 'o'
		case 117: // 'u'
		case 121: // 'y'
		case 232: 
			return true;
		}
		return false;
	}

	void setStemDictionary(Map dict)
	{
		_stemDict = dict;
	}

}
