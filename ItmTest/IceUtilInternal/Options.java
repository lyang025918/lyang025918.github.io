// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Options.java

package IceUtilInternal;

import java.util.ArrayList;
import java.util.List;

public final class Options
{
	public static final class BadQuote extends Exception
	{

		BadQuote(String message)
		{
			super(message);
		}
	}


	static final boolean $assertionsDisabled = !IceUtilInternal/Options.desiredAssertionStatus();

	public Options()
	{
	}

	public static String[] split(String line)
		throws BadQuote
	{
		String IFS = " \t\n";
		int NormalState = 1;
		int DoubleQuoteState = 2;
		int SingleQuoteState = 3;
		int ANSIQuoteState = 4;
		line = line.trim();
		if (line.length() == 0)
			return new String[0];
		int state = 1;
		StringBuilder arg = new StringBuilder(128);
		List vec = new ArrayList();
		for (int i = 0; i < line.length(); i++)
		{
			char c = line.charAt(i);
label0:
			switch (state)
			{
			case 1: // '\001'
				switch (c)
				{
				case 92: // '\\'
					if (i >= line.length() - 1 || line.charAt(++i) == '\n')
						break label0;
					switch (line.charAt(i))
					{
					case 32: // ' '
					case 34: // '"'
					case 36: // '$'
					case 92: // '\\'
						arg.append(line.charAt(i));
						break;

					default:
						arg.append('\\');
						arg.append(line.charAt(i));
						break;
					}
					break label0;

				case 39: // '\''
					state = 3;
					break label0;

				case 34: // '"'
					state = 2;
					break label0;

				case 36: // '$'
					if (i < line.length() - 1 && line.charAt(i + 1) == '\'')
					{
						state = 4;
						i++;
					} else
					{
						arg.append('$');
					}
					break label0;
				}
				if (" \t\n".indexOf(line.charAt(i)) != -1)
				{
					vec.add(arg.toString());
					arg = new StringBuilder(128);
					while (++i < line.length() && " \t\n".indexOf(line.charAt(i)) != -1) ;
					i--;
				} else
				{
					arg.append(line.charAt(i));
				}
				break;

			case 2: // '\002'
				if (c == '\\' && i < line.length() - 1)
				{
					switch (c = line.charAt(++i))
					{
					case 10: // '\n'
					case 34: // '"'
					case 92: // '\\'
						arg.append(c);
						break;

					default:
						arg.append('\\');
						arg.append(c);
						break;
					}
					break;
				}
				if (c == '"')
					state = 1;
				else
					arg.append(c);
				break;

			case 3: // '\003'
				if (c == '\'')
					state = 1;
				else
					arg.append(c);
				break;

			case 4: // '\004'
				switch (c)
				{
				case 92: // '\\'
					if (i == line.length() - 1)
						break label0;
					switch (c = line.charAt(++i))
					{
					case 97: // 'a'
					{
						arg.append('\007');
						break label0;
					}

					case 98: // 'b'
					{
						arg.append('\b');
						break label0;
					}

					case 102: // 'f'
					{
						arg.append('\f');
						break label0;
					}

					case 110: // 'n'
					{
						arg.append('\n');
						break label0;
					}

					case 114: // 'r'
					{
						arg.append('\r');
						break label0;
					}

					case 116: // 't'
					{
						arg.append('\t');
						break label0;
					}

					case 118: // 'v'
					{
						arg.append('\013');
						break label0;
					}

					case 92: // '\\'
					{
						arg.append('\\');
						break label0;
					}

					case 39: // '\''
					{
						arg.append('\'');
						break label0;
					}

					case 101: // 'e'
					{
						arg.append('\033');
						break label0;
					}

					case 48: // '0'
					case 49: // '1'
					case 50: // '2'
					case 51: // '3'
					case 52: // '4'
					case 53: // '5'
					case 54: // '6'
					case 55: // '7'
					{
						String octalDigits = "01234567";
						short us = 0;
						int j;
						for (j = i; j < i + 3 && j < line.length() && "01234567".indexOf(c = line.charAt(j)) != -1; j++)
							us = (short)((us * 8 + c) - 48);

						i = j - 1;
						arg.append((char)us);
						break label0;
					}

					case 120: // 'x'
					{
						String hexDigits = "0123456789abcdefABCDEF";
						if (i < line.length() - 1 && "0123456789abcdefABCDEF".indexOf(line.charAt(i + 1)) == -1)
						{
							arg.append('\\');
							arg.append('x');
							break label0;
						}
						short s = 0;
						int j;
						for (j = i + 1; j < i + 3 && j < line.length() && "0123456789abcdefABCDEF".indexOf(c = line.charAt(j)) != -1; j++)
						{
							s *= 16;
							if (Character.isDigit(c))
							{
								s += (short)(c - 48);
								continue;
							}
							if (Character.isLowerCase(c))
								s += (short)((c - 97) + 10);
							else
								s += (short)((c - 65) + 10);
						}

						i = j - 1;
						arg.append((char)s);
						break label0;
					}

					case 99: // 'c'
					{
						c = line.charAt(++i);
						if (Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'Z' || c == '@' || c >= '[' && c <= '_')
						{
							arg.append((char)(Character.toUpperCase(c) - 64));
						} else
						{
							arg.append('\\');
							arg.append('c');
							arg.append(c);
						}
						break;
					}

					case 40: // '('
					case 41: // ')'
					case 42: // '*'
					case 43: // '+'
					case 44: // ','
					case 45: // '-'
					case 46: // '.'
					case 47: // '/'
					case 56: // '8'
					case 57: // '9'
					case 58: // ':'
					case 59: // ';'
					case 60: // '<'
					case 61: // '='
					case 62: // '>'
					case 63: // '?'
					case 64: // '@'
					case 65: // 'A'
					case 66: // 'B'
					case 67: // 'C'
					case 68: // 'D'
					case 69: // 'E'
					case 70: // 'F'
					case 71: // 'G'
					case 72: // 'H'
					case 73: // 'I'
					case 74: // 'J'
					case 75: // 'K'
					case 76: // 'L'
					case 77: // 'M'
					case 78: // 'N'
					case 79: // 'O'
					case 80: // 'P'
					case 81: // 'Q'
					case 82: // 'R'
					case 83: // 'S'
					case 84: // 'T'
					case 85: // 'U'
					case 86: // 'V'
					case 87: // 'W'
					case 88: // 'X'
					case 89: // 'Y'
					case 90: // 'Z'
					case 91: // '['
					case 93: // ']'
					case 94: // '^'
					case 95: // '_'
					case 96: // '`'
					case 100: // 'd'
					case 103: // 'g'
					case 104: // 'h'
					case 105: // 'i'
					case 106: // 'j'
					case 107: // 'k'
					case 108: // 'l'
					case 109: // 'm'
					case 111: // 'o'
					case 112: // 'p'
					case 113: // 'q'
					case 115: // 's'
					case 117: // 'u'
					case 119: // 'w'
					default:
					{
						arg.append('\\');
						arg.append(c);
						break;
					}
					}
					break;

				case 39: // '\''
					state = 1;
					break;

				default:
					arg.append(c);
					break;
				}
				break;

			default:
				if (!$assertionsDisabled)
					throw new AssertionError();
				break;
			}
		}

		switch (state)
		{
		case 1: // '\001'
			vec.add(arg.toString());
			break;

		case 3: // '\003'
			throw new BadQuote("missing closing single quote");

		case 2: // '\002'
			throw new BadQuote("missing closing double quote");

		case 4: // '\004'
			throw new BadQuote("unterminated $' quote");

		default:
			if (!$assertionsDisabled)
				throw new AssertionError();
			break;
		}
		return (String[])(String[])vec.toArray(new String[0]);
	}

}
