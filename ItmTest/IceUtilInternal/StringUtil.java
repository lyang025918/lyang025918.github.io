// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringUtil.java

package IceUtilInternal;

import Ice.IntHolder;
import java.io.UnsupportedEncodingException;
import java.util.*;

public final class StringUtil
{

	static final boolean $assertionsDisabled = !IceUtilInternal/StringUtil.desiredAssertionStatus();

	public StringUtil()
	{
	}

	public static int findFirstOf(String str, String match)
	{
		return findFirstOf(str, match, 0);
	}

	public static int findFirstOf(String str, String match, int start)
	{
		int len = str.length();
		for (int i = start; i < len; i++)
		{
			char ch = str.charAt(i);
			if (match.indexOf(ch) != -1)
				return i;
		}

		return -1;
	}

	public static int findFirstNotOf(String str, String match)
	{
		return findFirstNotOf(str, match, 0);
	}

	public static int findFirstNotOf(String str, String match, int start)
	{
		int len = str.length();
		for (int i = start; i < len; i++)
		{
			char ch = str.charAt(i);
			if (match.indexOf(ch) == -1)
				return i;
		}

		return -1;
	}

	private static void encodeChar(byte b, StringBuilder sb, String special)
	{
		switch (b)
		{
		case 92: // '\\'
			sb.append("\\\\");
			break;

		case 39: // '\''
			sb.append("\\'");
			break;

		case 34: // '"'
			sb.append("\\\"");
			break;

		case 8: // '\b'
			sb.append("\\b");
			break;

		case 12: // '\f'
			sb.append("\\f");
			break;

		case 10: // '\n'
			sb.append("\\n");
			break;

		case 13: // '\r'
			sb.append("\\r");
			break;

		case 9: // '\t'
			sb.append("\\t");
			break;

		default:
			if (b < 32 || b > 126)
			{
				sb.append('\\');
				String octal = Integer.toOctalString(b >= 0 ? ((int) (b)) : b + 256);
				for (int j = octal.length(); j < 3; j++)
					sb.append('0');

				sb.append(octal);
				break;
			}
			if (special != null && special.indexOf((char)b) != -1)
			{
				sb.append('\\');
				sb.append((char)b);
			} else
			{
				sb.append((char)b);
			}
			break;
		}
	}

	public static String escapeString(String s, String special)
	{
		if (special != null)
		{
			for (int i = 0; i < special.length(); i++)
				if (special.charAt(i) < ' ' || special.charAt(i) > '~')
					throw new IllegalArgumentException("special characters must be in ASCII range 32-126");

		}
		byte bytes[] = null;
		try
		{
			bytes = s.getBytes("UTF8");
		}
		catch (UnsupportedEncodingException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
			else
				return null;
		}
		StringBuilder result = new StringBuilder(bytes.length);
		for (int i = 0; i < bytes.length; i++)
			encodeChar(bytes[i], result, special);

		return result.toString();
	}

	private static char checkChar(String s, int pos)
	{
		char c = s.charAt(pos);
		if (c < ' ' || c > '~')
		{
			String msg;
			if (pos > 0)
				msg = (new StringBuilder()).append("character after `").append(s.substring(0, pos)).append("'").toString();
			else
				msg = "first character";
			msg = (new StringBuilder()).append(msg).append(" is not a printable ASCII character (ordinal ").append(c).append(")").toString();
			throw new IllegalArgumentException(msg);
		} else
		{
			return c;
		}
	}

	private static char decodeChar(String s, int start, int end, IntHolder nextStart)
	{
		if (!$assertionsDisabled && start < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && start >= end)
			throw new AssertionError();
		if (!$assertionsDisabled && end > s.length())
			throw new AssertionError();
		char c;
		if (s.charAt(start) != '\\')
		{
			c = checkChar(s, start++);
		} else
		{
			if (start + 1 == end)
				throw new IllegalArgumentException("trailing backslash");
			switch (s.charAt(++start))
			{
			case 34: // '"'
			case 39: // '\''
			case 92: // '\\'
				c = s.charAt(start++);
				break;

			case 98: // 'b'
				start++;
				c = '\b';
				break;

			case 102: // 'f'
				start++;
				c = '\f';
				break;

			case 110: // 'n'
				start++;
				c = '\n';
				break;

			case 114: // 'r'
				start++;
				c = '\r';
				break;

			case 116: // 't'
				start++;
				c = '\t';
				break;

			case 48: // '0'
			case 49: // '1'
			case 50: // '2'
			case 51: // '3'
			case 52: // '4'
			case 53: // '5'
			case 54: // '6'
			case 55: // '7'
				int val = 0;
				int j = 0;
				do
				{
					if (j >= 3 || start >= end)
						break;
					int charVal = s.charAt(start++) - 48;
					if (charVal < 0 || charVal > 7)
					{
						start--;
						break;
					}
					val = val * 8 + charVal;
					j++;
				} while (true);
				if (val > 255)
				{
					String msg = (new StringBuilder()).append("octal value \\").append(Integer.toOctalString(val)).append(" (").append(val).append(") is out of range").toString();
					throw new IllegalArgumentException(msg);
				}
				c = (char)val;
				break;

			default:
				c = checkChar(s, start++);
				break;
			}
		}
		nextStart.value = start;
		return c;
	}

	private static void decodeString(String s, int start, int end, StringBuilder sb)
	{
		IntHolder nextStart = new IntHolder();
		for (; start < end; start = nextStart.value)
			sb.append(decodeChar(s, start, end, nextStart));

	}

	public static String unescapeString(String s, int start, int end)
	{
		byte arr[];
		if (!$assertionsDisabled && (start < 0 || start > end || end > s.length()))
			throw new AssertionError();
		StringBuilder sb = new StringBuilder(end - start);
		decodeString(s, start, end, sb);
		String decodedString = sb.toString();
		arr = new byte[decodedString.length()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = (byte)decodedString.charAt(i);

		return new String(arr, 0, arr.length, "UTF8");
		UnsupportedEncodingException ex;
		ex;
		throw new IllegalArgumentException("unsupported encoding", ex);
	}

	public static String joinString(List values, String delimiter)
	{
		StringBuffer s = new StringBuffer();
		boolean first = true;
		for (Iterator i$ = values.iterator(); i$.hasNext();)
		{
			String v = (String)i$.next();
			if (!first)
				s.append(delimiter);
			s.append(v);
			first = false;
		}

		return s.toString();
	}

	public static String[] splitString(String str, String delim)
	{
		List l = new ArrayList();
		char arr[] = new char[str.length()];
		int pos = 0;
		int n = 0;
		char quoteChar = '\0';
		do
		{
			if (pos >= str.length())
				break;
			if (quoteChar == 0 && (str.charAt(pos) == '"' || str.charAt(pos) == '\''))
			{
				quoteChar = str.charAt(pos++);
				continue;
			}
			if (quoteChar == 0 && str.charAt(pos) == '\\' && pos + 1 < str.length() && (str.charAt(pos + 1) == '"' || str.charAt(pos + 1) == '\''))
				pos++;
			else
			if (quoteChar != 0 && str.charAt(pos) == '\\' && pos + 1 < str.length() && str.charAt(pos + 1) == quoteChar)
			{
				pos++;
			} else
			{
				if (quoteChar != 0 && str.charAt(pos) == quoteChar)
				{
					pos++;
					quoteChar = '\0';
					continue;
				}
				if (delim.indexOf(str.charAt(pos)) != -1 && quoteChar == 0)
				{
					pos++;
					if (n > 0)
					{
						l.add(new String(arr, 0, n));
						n = 0;
					}
					continue;
				}
			}
			if (pos < str.length())
				arr[n++] = str.charAt(pos++);
		} while (true);
		if (n > 0)
			l.add(new String(arr, 0, n));
		if (quoteChar != 0)
			return null;
		else
			return (String[])(String[])l.toArray(new String[0]);
	}

	public static int checkQuote(String s)
	{
		return checkQuote(s, 0);
	}

	public static int checkQuote(String s, int start)
	{
		char quoteChar = s.charAt(start);
		if (quoteChar == '"' || quoteChar == '\'')
		{
			start++;
			int pos;
			for (int len = s.length(); start < len && (pos = s.indexOf(quoteChar, start)) != -1; start = pos + 1)
				if (s.charAt(pos - 1) != '\\')
					return pos;

			return -1;
		} else
		{
			return 0;
		}
	}

}
