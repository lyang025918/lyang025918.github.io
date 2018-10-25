// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RFC2253.java

package IceSSL;

import Ice.LocalException;
import java.util.LinkedList;
import java.util.List;

class RFC2253
{
	private static class ParseState
	{

		String data;
		int pos;

		private ParseState()
		{
		}

	}

	static class RDNEntry
	{

		List rdn;
		boolean negate;

		RDNEntry()
		{
			rdn = new LinkedList();
			negate = false;
		}
	}

	static class RDNPair
	{

		String key;
		String value;

		RDNPair()
		{
		}
	}

	static class ParseException extends LocalException
	{

		public String reason;

		public String ice_name()
		{
			return "RFC2253::ParseException";
		}

		public ParseException()
		{
		}

		public ParseException(String reason)
		{
			this.reason = reason;
		}
	}


	private static final String special = ",=+<>#;";
	private static final String hexvalid = "0123456789abcdefABCDEF";
	static final boolean $assertionsDisabled = !IceSSL/RFC2253.desiredAssertionStatus();

	RFC2253()
	{
	}

	public static List parse(String data)
		throws ParseException
	{
		List results;
		RDNEntry current;
label0:
		{
			results = new LinkedList();
			current = new RDNEntry();
			ParseState state = new ParseState();
			state.data = data;
label1:
			do
			{
				for (state.pos = 0; state.pos < state.data.length();)
				{
					eatWhite(state);
					if (state.pos < state.data.length() && state.data.charAt(state.pos) == '!')
					{
						if (!current.rdn.isEmpty())
							throw new ParseException("negation symbol '!' must appear at start of list");
						state.pos++;
						current.negate = true;
					}
					current.rdn.add(parseNameComponent(state));
					eatWhite(state);
					if (state.pos < state.data.length() && state.data.charAt(state.pos) == ',')
					{
						state.pos++;
					} else
					{
						if (state.pos >= state.data.length() || state.data.charAt(state.pos) != ';')
							continue label1;
						state.pos++;
						results.add(current);
						current = new RDNEntry();
					}
				}

				break label0;
			} while (state.pos >= state.data.length());
			throw new ParseException((new StringBuilder()).append("expected ',' or ';' at `").append(state.data.substring(state.pos)).append("'").toString());
		}
		if (!current.rdn.isEmpty())
			results.add(current);
		return results;
	}

	public static List parseStrict(String data)
		throws ParseException
	{
		List results;
label0:
		{
			results = new LinkedList();
			ParseState state = new ParseState();
			state.data = data;
label1:
			do
			{
				for (state.pos = 0; state.pos < state.data.length(); state.pos++)
				{
					results.add(parseNameComponent(state));
					eatWhite(state);
					if (state.pos >= state.data.length() || state.data.charAt(state.pos) != ',' && state.data.charAt(state.pos) != ';')
						continue label1;
				}

				break label0;
			} while (state.pos >= state.data.length());
			throw new ParseException((new StringBuilder()).append("expected ',' or ';' at `").append(state.data.substring(state.pos)).append("'").toString());
		}
		return results;
	}

	private static RDNPair parseNameComponent(ParseState state)
		throws ParseException
	{
		RDNPair result = parseAttributeTypeAndValue(state);
_L4:
		if (state.pos >= state.data.length()) goto _L2; else goto _L1
_L1:
		eatWhite(state);
		if (state.pos >= state.data.length() || state.data.charAt(state.pos) != '+') goto _L2; else goto _L3
_L3:
		RDNPair p;
		state.pos++;
		p = parseAttributeTypeAndValue(state);
		new StringBuilder();
		result;
		JVM INSTR dup_x1 ;
		value;
		append();
		"+";
		append();
		toString();
		value;
		new StringBuilder();
		result;
		JVM INSTR dup_x1 ;
		value;
		append();
		p.key;
		append();
		toString();
		value;
		new StringBuilder();
		result;
		JVM INSTR dup_x1 ;
		value;
		append();
		'=';
		append();
		toString();
		value;
		new StringBuilder();
		result;
		JVM INSTR dup_x1 ;
		value;
		append();
		p.value;
		append();
		toString();
		value;
		  goto _L4
_L2:
		return result;
	}

	private static RDNPair parseAttributeTypeAndValue(ParseState state)
		throws ParseException
	{
		RDNPair p = new RDNPair();
		p.key = parseAttributeType(state);
		eatWhite(state);
		if (state.pos >= state.data.length())
			throw new ParseException("invalid attribute type/value pair (unexpected end of state.data)");
		if (state.data.charAt(state.pos) != '=')
		{
			throw new ParseException("invalid attribute type/value pair (missing =)");
		} else
		{
			state.pos++;
			p.value = parseAttributeValue(state);
			return p;
		}
	}

	private static String parseAttributeType(ParseState state)
		throws ParseException
	{
		StringBuffer result;
label0:
		{
			eatWhite(state);
			if (state.pos >= state.data.length())
				throw new ParseException("invalid attribute type (expected end of state.data)");
			result = new StringBuffer();
			if (Character.isDigit(state.data.charAt(state.pos)) || state.data.length() - state.pos >= 4 && (state.data.substring(state.pos, state.pos + 4).equals("oid.") || state.data.substring(state.pos, state.pos + 4).equals("OID.")))
			{
				if (!Character.isDigit(state.data.charAt(state.pos)))
				{
					result.append(state.data.substring(state.pos, state.pos + 4));
					state.pos += 4;
				}
				do
				{
					for (; state.pos < state.data.length() && Character.isDigit(state.data.charAt(state.pos)); state.pos++)
						result.append(state.data.charAt(state.pos));

					if (state.pos >= state.data.length() || state.data.charAt(state.pos) != '.')
						break label0;
					result.append(state.data.charAt(state.pos));
					state.pos++;
				} while (state.pos >= state.data.length() || Character.isDigit(state.data.charAt(state.pos)));
				throw new ParseException("invalid attribute type (expected end of state.data)");
			}
			if (Character.isUpperCase(state.data.charAt(state.pos)) || Character.isLowerCase(state.data.charAt(state.pos)))
			{
				result.append(state.data.charAt(state.pos));
				for (state.pos++; state.pos < state.data.length() && (Character.isDigit(state.data.charAt(state.pos)) || Character.isUpperCase(state.data.charAt(state.pos)) || Character.isLowerCase(state.data.charAt(state.pos)) || state.data.charAt(state.pos) == '-'); state.pos++)
					result.append(state.data.charAt(state.pos));

			} else
			{
				throw new ParseException("invalid attribute type");
			}
		}
		return result.toString();
	}

	private static String parseAttributeValue(ParseState state)
		throws ParseException
	{
		eatWhite(state);
		if (state.pos >= state.data.length())
			return "";
		StringBuffer result = new StringBuffer();
		if (state.data.charAt(state.pos) == '#')
		{
			result.append(state.data.charAt(state.pos));
			state.pos++;
			do
			{
				String h = parseHexPair(state, true);
				if (h.length() == 0)
					break;
				result.append(h);
			} while (true);
		} else
		if (state.data.charAt(state.pos) == '"')
		{
			result.append(state.data.charAt(state.pos));
			state.pos++;
			do
			{
				if (state.pos >= state.data.length())
					throw new ParseException("invalid attribute value (unexpected end of state.data)");
				if (state.data.charAt(state.pos) == '"')
				{
					result.append(state.data.charAt(state.pos));
					state.pos++;
					break;
				}
				if (state.data.charAt(state.pos) != '\\')
				{
					result.append(state.data.charAt(state.pos));
					state.pos++;
				} else
				{
					result.append(parsePair(state));
				}
			} while (true);
		} else
		{
			do
			{
				if (state.pos >= state.data.length())
					break;
				if (state.data.charAt(state.pos) == '\\')
				{
					result.append(parsePair(state));
					continue;
				}
				if (",=+<>#;".indexOf(state.data.charAt(state.pos)) != -1 || state.data.charAt(state.pos) == '"')
					break;
				result.append(state.data.charAt(state.pos));
				state.pos++;
			} while (true);
		}
		return result.toString();
	}

	private static String parsePair(ParseState state)
		throws ParseException
	{
		String result = "";
		if (!$assertionsDisabled && state.data.charAt(state.pos) != '\\')
			throw new AssertionError();
		result = (new StringBuilder()).append(result).append(state.data.charAt(state.pos)).toString();
		state.pos++;
		if (state.pos >= state.data.length())
			throw new ParseException("invalid escape format (unexpected end of state.data)");
		if (",=+<>#;".indexOf(state.data.charAt(state.pos)) != -1 || state.data.charAt(state.pos) != '\\' || state.data.charAt(state.pos) != '"')
		{
			result = (new StringBuilder()).append(result).append(state.data.charAt(state.pos)).toString();
			state.pos++;
			return result;
		} else
		{
			return parseHexPair(state, false);
		}
	}

	private static String parseHexPair(ParseState state, boolean allowEmpty)
		throws ParseException
	{
		String result = "";
		if (state.pos < state.data.length() && "0123456789abcdefABCDEF".indexOf(state.data.charAt(state.pos)) != -1)
		{
			result = (new StringBuilder()).append(result).append(state.data.charAt(state.pos)).toString();
			state.pos++;
		}
		if (state.pos < state.data.length() && "0123456789abcdefABCDEF".indexOf(state.data.charAt(state.pos)) != -1)
		{
			result = (new StringBuilder()).append(result).append(state.data.charAt(state.pos)).toString();
			state.pos++;
		}
		if (result.length() != 2)
		{
			if (allowEmpty && result.length() == 0)
				return result;
			else
				throw new ParseException("invalid hex format");
		} else
		{
			return result;
		}
	}

	private static void eatWhite(ParseState state)
	{
		for (; state.pos < state.data.length() && state.data.charAt(state.pos) == ' '; state.pos++);
	}

}
