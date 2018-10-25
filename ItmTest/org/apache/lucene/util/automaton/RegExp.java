// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegExp.java

package org.apache.lucene.util.automaton;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Automaton, BasicOperations, MinimizationOperations, BasicAutomata, 
//			AutomatonProvider

public class RegExp
{
	static final class Kind extends Enum
	{

		public static final Kind REGEXP_UNION;
		public static final Kind REGEXP_CONCATENATION;
		public static final Kind REGEXP_INTERSECTION;
		public static final Kind REGEXP_OPTIONAL;
		public static final Kind REGEXP_REPEAT;
		public static final Kind REGEXP_REPEAT_MIN;
		public static final Kind REGEXP_REPEAT_MINMAX;
		public static final Kind REGEXP_COMPLEMENT;
		public static final Kind REGEXP_CHAR;
		public static final Kind REGEXP_CHAR_RANGE;
		public static final Kind REGEXP_ANYCHAR;
		public static final Kind REGEXP_EMPTY;
		public static final Kind REGEXP_STRING;
		public static final Kind REGEXP_ANYSTRING;
		public static final Kind REGEXP_AUTOMATON;
		public static final Kind REGEXP_INTERVAL;
		private static final Kind $VALUES[];

		public static Kind[] values()
		{
			return (Kind[])$VALUES.clone();
		}

		public static Kind valueOf(String name)
		{
			return (Kind)Enum.valueOf(org/apache/lucene/util/automaton/RegExp$Kind, name);
		}

		static 
		{
			REGEXP_UNION = new Kind("REGEXP_UNION", 0);
			REGEXP_CONCATENATION = new Kind("REGEXP_CONCATENATION", 1);
			REGEXP_INTERSECTION = new Kind("REGEXP_INTERSECTION", 2);
			REGEXP_OPTIONAL = new Kind("REGEXP_OPTIONAL", 3);
			REGEXP_REPEAT = new Kind("REGEXP_REPEAT", 4);
			REGEXP_REPEAT_MIN = new Kind("REGEXP_REPEAT_MIN", 5);
			REGEXP_REPEAT_MINMAX = new Kind("REGEXP_REPEAT_MINMAX", 6);
			REGEXP_COMPLEMENT = new Kind("REGEXP_COMPLEMENT", 7);
			REGEXP_CHAR = new Kind("REGEXP_CHAR", 8);
			REGEXP_CHAR_RANGE = new Kind("REGEXP_CHAR_RANGE", 9);
			REGEXP_ANYCHAR = new Kind("REGEXP_ANYCHAR", 10);
			REGEXP_EMPTY = new Kind("REGEXP_EMPTY", 11);
			REGEXP_STRING = new Kind("REGEXP_STRING", 12);
			REGEXP_ANYSTRING = new Kind("REGEXP_ANYSTRING", 13);
			REGEXP_AUTOMATON = new Kind("REGEXP_AUTOMATON", 14);
			REGEXP_INTERVAL = new Kind("REGEXP_INTERVAL", 15);
			$VALUES = (new Kind[] {
				REGEXP_UNION, REGEXP_CONCATENATION, REGEXP_INTERSECTION, REGEXP_OPTIONAL, REGEXP_REPEAT, REGEXP_REPEAT_MIN, REGEXP_REPEAT_MINMAX, REGEXP_COMPLEMENT, REGEXP_CHAR, REGEXP_CHAR_RANGE, 
				REGEXP_ANYCHAR, REGEXP_EMPTY, REGEXP_STRING, REGEXP_ANYSTRING, REGEXP_AUTOMATON, REGEXP_INTERVAL
			});
		}

		private Kind(String s1, int i)
		{
			super(s1, i);
		}
	}


	public static final int INTERSECTION = 1;
	public static final int COMPLEMENT = 2;
	public static final int EMPTY = 4;
	public static final int ANYSTRING = 8;
	public static final int AUTOMATON = 16;
	public static final int INTERVAL = 32;
	public static final int ALL = 65535;
	public static final int NONE = 0;
	private static boolean allow_mutation = false;
	Kind kind;
	RegExp exp1;
	RegExp exp2;
	String s;
	int c;
	int min;
	int max;
	int digits;
	int from;
	int to;
	String b;
	int flags;
	int pos;

	RegExp()
	{
	}

	public RegExp(String s)
		throws IllegalArgumentException
	{
		this(s, 65535);
	}

	public RegExp(String s, int syntax_flags)
		throws IllegalArgumentException
	{
		b = s;
		flags = syntax_flags;
		RegExp e;
		if (s.length() == 0)
		{
			e = makeString("");
		} else
		{
			e = parseUnionExp();
			if (pos < b.length())
				throw new IllegalArgumentException((new StringBuilder()).append("end-of-string expected at position ").append(pos).toString());
		}
		kind = e.kind;
		exp1 = e.exp1;
		exp2 = e.exp2;
		this.s = e.s;
		c = e.c;
		min = e.min;
		max = e.max;
		digits = e.digits;
		from = e.from;
		to = e.to;
		b = null;
	}

	public Automaton toAutomaton()
	{
		return toAutomatonAllowMutate(null, null);
	}

	public Automaton toAutomaton(AutomatonProvider automaton_provider)
		throws IllegalArgumentException
	{
		return toAutomatonAllowMutate(null, automaton_provider);
	}

	public Automaton toAutomaton(Map automata)
		throws IllegalArgumentException
	{
		return toAutomatonAllowMutate(automata, null);
	}

	public boolean setAllowMutate(boolean flag)
	{
		boolean b = allow_mutation;
		allow_mutation = flag;
		return b;
	}

	private Automaton toAutomatonAllowMutate(Map automata, AutomatonProvider automaton_provider)
		throws IllegalArgumentException
	{
		boolean b = false;
		if (allow_mutation)
			b = Automaton.setAllowMutate(true);
		Automaton a = toAutomaton(automata, automaton_provider);
		if (allow_mutation)
			Automaton.setAllowMutate(b);
		return a;
	}

	private Automaton toAutomaton(Map automata, AutomatonProvider automaton_provider)
		throws IllegalArgumentException
	{
		Automaton a = null;
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[];

			static 
			{
				$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind = new int[Kind.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_UNION.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_CONCATENATION.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_INTERSECTION.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_OPTIONAL.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_REPEAT.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_REPEAT_MIN.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_REPEAT_MINMAX.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_COMPLEMENT.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_CHAR.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_CHAR_RANGE.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_ANYCHAR.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_EMPTY.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_STRING.ordinal()] = 13;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_ANYSTRING.ordinal()] = 14;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_AUTOMATON.ordinal()] = 15;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$RegExp$Kind[Kind.REGEXP_INTERVAL.ordinal()] = 16;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.util.automaton.RegExp.Kind[kind.ordinal()])
		{
		default:
			break;

		case 1: // '\001'
		{
			List list = new ArrayList();
			findLeaves(exp1, Kind.REGEXP_UNION, list, automata, automaton_provider);
			findLeaves(exp2, Kind.REGEXP_UNION, list, automata, automaton_provider);
			a = BasicOperations.union(list);
			MinimizationOperations.minimize(a);
			break;
		}

		case 2: // '\002'
		{
			List list = new ArrayList();
			findLeaves(exp1, Kind.REGEXP_CONCATENATION, list, automata, automaton_provider);
			findLeaves(exp2, Kind.REGEXP_CONCATENATION, list, automata, automaton_provider);
			a = BasicOperations.concatenate(list);
			MinimizationOperations.minimize(a);
			break;
		}

		case 3: // '\003'
		{
			a = exp1.toAutomaton(automata, automaton_provider).intersection(exp2.toAutomaton(automata, automaton_provider));
			MinimizationOperations.minimize(a);
			break;
		}

		case 4: // '\004'
		{
			a = exp1.toAutomaton(automata, automaton_provider).optional();
			MinimizationOperations.minimize(a);
			break;
		}

		case 5: // '\005'
		{
			a = exp1.toAutomaton(automata, automaton_provider).repeat();
			MinimizationOperations.minimize(a);
			break;
		}

		case 6: // '\006'
		{
			a = exp1.toAutomaton(automata, automaton_provider).repeat(min);
			MinimizationOperations.minimize(a);
			break;
		}

		case 7: // '\007'
		{
			a = exp1.toAutomaton(automata, automaton_provider).repeat(min, max);
			MinimizationOperations.minimize(a);
			break;
		}

		case 8: // '\b'
		{
			a = exp1.toAutomaton(automata, automaton_provider).complement();
			MinimizationOperations.minimize(a);
			break;
		}

		case 9: // '\t'
		{
			a = BasicAutomata.makeChar(c);
			break;
		}

		case 10: // '\n'
		{
			a = BasicAutomata.makeCharRange(from, to);
			break;
		}

		case 11: // '\013'
		{
			a = BasicAutomata.makeAnyChar();
			break;
		}

		case 12: // '\f'
		{
			a = BasicAutomata.makeEmpty();
			break;
		}

		case 13: // '\r'
		{
			a = BasicAutomata.makeString(s);
			break;
		}

		case 14: // '\016'
		{
			a = BasicAutomata.makeAnyString();
			break;
		}

		case 15: // '\017'
		{
			Automaton aa = null;
			if (automata != null)
				aa = (Automaton)automata.get(s);
			if (aa == null && automaton_provider != null)
				try
				{
					aa = automaton_provider.getAutomaton(s);
				}
				catch (IOException e)
				{
					throw new IllegalArgumentException(e);
				}
			if (aa == null)
				throw new IllegalArgumentException((new StringBuilder()).append("'").append(s).append("' not found").toString());
			a = aa.clone();
			break;
		}

		case 16: // '\020'
		{
			a = BasicAutomata.makeInterval(min, max, digits);
			break;
		}
		}
		return a;
	}

	private void findLeaves(RegExp exp, Kind kind, List list, Map automata, AutomatonProvider automaton_provider)
	{
		if (exp.kind == kind)
		{
			findLeaves(exp.exp1, kind, list, automata, automaton_provider);
			findLeaves(exp.exp2, kind, list, automata, automaton_provider);
		} else
		{
			list.add(exp.toAutomaton(automata, automaton_provider));
		}
	}

	public String toString()
	{
		return toStringBuilder(new StringBuilder()).toString();
	}

	StringBuilder toStringBuilder(StringBuilder b)
	{
		switch (1..SwitchMap.org.apache.lucene.util.automaton.RegExp.Kind[kind.ordinal()])
		{
		default:
			break;

		case 1: // '\001'
			b.append("(");
			exp1.toStringBuilder(b);
			b.append("|");
			exp2.toStringBuilder(b);
			b.append(")");
			break;

		case 2: // '\002'
			exp1.toStringBuilder(b);
			exp2.toStringBuilder(b);
			break;

		case 3: // '\003'
			b.append("(");
			exp1.toStringBuilder(b);
			b.append("&");
			exp2.toStringBuilder(b);
			b.append(")");
			break;

		case 4: // '\004'
			b.append("(");
			exp1.toStringBuilder(b);
			b.append(")?");
			break;

		case 5: // '\005'
			b.append("(");
			exp1.toStringBuilder(b);
			b.append(")*");
			break;

		case 6: // '\006'
			b.append("(");
			exp1.toStringBuilder(b);
			b.append("){").append(min).append(",}");
			break;

		case 7: // '\007'
			b.append("(");
			exp1.toStringBuilder(b);
			b.append("){").append(min).append(",").append(max).append("}");
			break;

		case 8: // '\b'
			b.append("~(");
			exp1.toStringBuilder(b);
			b.append(")");
			break;

		case 9: // '\t'
			b.append("\\").appendCodePoint(c);
			break;

		case 10: // '\n'
			b.append("[\\").appendCodePoint(from).append("-\\").appendCodePoint(to).append("]");
			break;

		case 11: // '\013'
			b.append(".");
			break;

		case 12: // '\f'
			b.append("#");
			break;

		case 13: // '\r'
			b.append("\"").append(s).append("\"");
			break;

		case 14: // '\016'
			b.append("@");
			break;

		case 15: // '\017'
			b.append("<").append(s).append(">");
			break;

		case 16: // '\020'
			String s1 = Integer.toString(min);
			String s2 = Integer.toString(max);
			b.append("<");
			if (digits > 0)
			{
				for (int i = s1.length(); i < digits; i++)
					b.append('0');

			}
			b.append(s1).append("-");
			if (digits > 0)
			{
				for (int i = s2.length(); i < digits; i++)
					b.append('0');

			}
			b.append(s2).append(">");
			break;
		}
		return b;
	}

	public Set getIdentifiers()
	{
		HashSet set = new HashSet();
		getIdentifiers(((Set) (set)));
		return set;
	}

	void getIdentifiers(Set set)
	{
		switch (1..SwitchMap.org.apache.lucene.util.automaton.RegExp.Kind[kind.ordinal()])
		{
		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
			exp1.getIdentifiers(set);
			exp2.getIdentifiers(set);
			break;

		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
			exp1.getIdentifiers(set);
			break;

		case 15: // '\017'
			set.add(s);
			break;
		}
	}

	static RegExp makeUnion(RegExp exp1, RegExp exp2)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_UNION;
		r.exp1 = exp1;
		r.exp2 = exp2;
		return r;
	}

	static RegExp makeConcatenation(RegExp exp1, RegExp exp2)
	{
		if ((exp1.kind == Kind.REGEXP_CHAR || exp1.kind == Kind.REGEXP_STRING) && (exp2.kind == Kind.REGEXP_CHAR || exp2.kind == Kind.REGEXP_STRING))
			return makeString(exp1, exp2);
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_CONCATENATION;
		if (exp1.kind == Kind.REGEXP_CONCATENATION && (exp1.exp2.kind == Kind.REGEXP_CHAR || exp1.exp2.kind == Kind.REGEXP_STRING) && (exp2.kind == Kind.REGEXP_CHAR || exp2.kind == Kind.REGEXP_STRING))
		{
			r.exp1 = exp1.exp1;
			r.exp2 = makeString(exp1.exp2, exp2);
		} else
		if ((exp1.kind == Kind.REGEXP_CHAR || exp1.kind == Kind.REGEXP_STRING) && exp2.kind == Kind.REGEXP_CONCATENATION && (exp2.exp1.kind == Kind.REGEXP_CHAR || exp2.exp1.kind == Kind.REGEXP_STRING))
		{
			r.exp1 = makeString(exp1, exp2.exp1);
			r.exp2 = exp2.exp2;
		} else
		{
			r.exp1 = exp1;
			r.exp2 = exp2;
		}
		return r;
	}

	private static RegExp makeString(RegExp exp1, RegExp exp2)
	{
		StringBuilder b = new StringBuilder();
		if (exp1.kind == Kind.REGEXP_STRING)
			b.append(exp1.s);
		else
			b.appendCodePoint(exp1.c);
		if (exp2.kind == Kind.REGEXP_STRING)
			b.append(exp2.s);
		else
			b.appendCodePoint(exp2.c);
		return makeString(b.toString());
	}

	static RegExp makeIntersection(RegExp exp1, RegExp exp2)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_INTERSECTION;
		r.exp1 = exp1;
		r.exp2 = exp2;
		return r;
	}

	static RegExp makeOptional(RegExp exp)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_OPTIONAL;
		r.exp1 = exp;
		return r;
	}

	static RegExp makeRepeat(RegExp exp)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_REPEAT;
		r.exp1 = exp;
		return r;
	}

	static RegExp makeRepeat(RegExp exp, int min)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_REPEAT_MIN;
		r.exp1 = exp;
		r.min = min;
		return r;
	}

	static RegExp makeRepeat(RegExp exp, int min, int max)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_REPEAT_MINMAX;
		r.exp1 = exp;
		r.min = min;
		r.max = max;
		return r;
	}

	static RegExp makeComplement(RegExp exp)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_COMPLEMENT;
		r.exp1 = exp;
		return r;
	}

	static RegExp makeChar(int c)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_CHAR;
		r.c = c;
		return r;
	}

	static RegExp makeCharRange(int from, int to)
	{
		if (from > to)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("invalid range: from (").append(from).append(") cannot be > to (").append(to).append(")").toString());
		} else
		{
			RegExp r = new RegExp();
			r.kind = Kind.REGEXP_CHAR_RANGE;
			r.from = from;
			r.to = to;
			return r;
		}
	}

	static RegExp makeAnyChar()
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_ANYCHAR;
		return r;
	}

	static RegExp makeEmpty()
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_EMPTY;
		return r;
	}

	static RegExp makeString(String s)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_STRING;
		r.s = s;
		return r;
	}

	static RegExp makeAnyString()
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_ANYSTRING;
		return r;
	}

	static RegExp makeAutomaton(String s)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_AUTOMATON;
		r.s = s;
		return r;
	}

	static RegExp makeInterval(int min, int max, int digits)
	{
		RegExp r = new RegExp();
		r.kind = Kind.REGEXP_INTERVAL;
		r.min = min;
		r.max = max;
		r.digits = digits;
		return r;
	}

	private boolean peek(String s)
	{
		return more() && s.indexOf(b.codePointAt(pos)) != -1;
	}

	private boolean match(int c)
	{
		if (pos >= b.length())
			return false;
		if (b.codePointAt(pos) == c)
		{
			pos += Character.charCount(c);
			return true;
		} else
		{
			return false;
		}
	}

	private boolean more()
	{
		return pos < b.length();
	}

	private int next()
		throws IllegalArgumentException
	{
		if (!more())
		{
			throw new IllegalArgumentException("unexpected end-of-string");
		} else
		{
			int ch = b.codePointAt(pos);
			pos += Character.charCount(ch);
			return ch;
		}
	}

	private boolean check(int flag)
	{
		return (flags & flag) != 0;
	}

	final RegExp parseUnionExp()
		throws IllegalArgumentException
	{
		RegExp e = parseInterExp();
		if (match(124))
			e = makeUnion(e, parseUnionExp());
		return e;
	}

	final RegExp parseInterExp()
		throws IllegalArgumentException
	{
		RegExp e = parseConcatExp();
		if (check(1) && match(38))
			e = makeIntersection(e, parseInterExp());
		return e;
	}

	final RegExp parseConcatExp()
		throws IllegalArgumentException
	{
		RegExp e = parseRepeatExp();
		if (more() && !peek(")|") && (!check(1) || !peek("&")))
			e = makeConcatenation(e, parseConcatExp());
		return e;
	}

	final RegExp parseRepeatExp()
		throws IllegalArgumentException
	{
		RegExp e = parseComplExp();
		do
		{
			if (!peek("?*+{"))
				break;
			if (match(63))
				e = makeOptional(e);
			else
			if (match(42))
				e = makeRepeat(e);
			else
			if (match(43))
				e = makeRepeat(e, 1);
			else
			if (match(123))
			{
				int start = pos;
				for (; peek("0123456789"); next());
				if (start == pos)
					throw new IllegalArgumentException((new StringBuilder()).append("integer expected at position ").append(pos).toString());
				int n = Integer.parseInt(b.substring(start, pos));
				int m = -1;
				if (match(44))
				{
					start = pos;
					for (; peek("0123456789"); next());
					if (start != pos)
						m = Integer.parseInt(b.substring(start, pos));
				} else
				{
					m = n;
				}
				if (!match(125))
					throw new IllegalArgumentException((new StringBuilder()).append("expected '}' at position ").append(pos).toString());
				if (m == -1)
					e = makeRepeat(e, n);
				else
					e = makeRepeat(e, n, m);
			}
		} while (true);
		return e;
	}

	final RegExp parseComplExp()
		throws IllegalArgumentException
	{
		if (check(2) && match(126))
			return makeComplement(parseComplExp());
		else
			return parseCharClassExp();
	}

	final RegExp parseCharClassExp()
		throws IllegalArgumentException
	{
		if (match(91))
		{
			boolean negate = false;
			if (match(94))
				negate = true;
			RegExp e = parseCharClasses();
			if (negate)
				e = makeIntersection(makeAnyChar(), makeComplement(e));
			if (!match(93))
				throw new IllegalArgumentException((new StringBuilder()).append("expected ']' at position ").append(pos).toString());
			else
				return e;
		} else
		{
			return parseSimpleExp();
		}
	}

	final RegExp parseCharClasses()
		throws IllegalArgumentException
	{
		RegExp e;
		for (e = parseCharClass(); more() && !peek("]"); e = makeUnion(e, parseCharClass()));
		return e;
	}

	final RegExp parseCharClass()
		throws IllegalArgumentException
	{
		int c = parseCharExp();
		if (match(45))
			return makeCharRange(c, parseCharExp());
		else
			return makeChar(c);
	}

	final RegExp parseSimpleExp()
		throws IllegalArgumentException
	{
		String s;
		int i;
		if (match(46))
			return makeAnyChar();
		if (check(4) && match(35))
			return makeEmpty();
		if (check(8) && match(64))
			return makeAnyString();
		int start;
		if (match(34))
		{
			start = pos;
			for (; more() && !peek("\""); next());
			if (!match(34))
				throw new IllegalArgumentException((new StringBuilder()).append("expected '\"' at position ").append(pos).toString());
			else
				return makeString(b.substring(start, pos - 1));
		}
		if (match(40))
		{
			if (match(41))
				return makeString("");
			RegExp e = parseUnionExp();
			if (!match(41))
				throw new IllegalArgumentException((new StringBuilder()).append("expected ')' at position ").append(pos).toString());
			else
				return e;
		}
		if (!check(16) && !check(32) || !match(60))
			break MISSING_BLOCK_LABEL_586;
		e = pos;
		for (; more() && !peek(">"); next());
		if (!match(62))
			throw new IllegalArgumentException((new StringBuilder()).append("expected '>' at position ").append(pos).toString());
		s = b.substring(e, pos - 1);
		i = s.indexOf('-');
		if (i == -1)
			if (!check(16))
				throw new IllegalArgumentException((new StringBuilder()).append("interval syntax error at position ").append(pos - 1).toString());
			else
				return makeAutomaton(s);
		if (!check(32))
			throw new IllegalArgumentException((new StringBuilder()).append("illegal identifier at position ").append(pos - 1).toString());
		int imin;
		int imax;
		int digits;
		if (i == 0 || i == s.length() - 1 || i != s.lastIndexOf('-'))
			throw new NumberFormatException();
		String smin = s.substring(0, i);
		String smax = s.substring(i + 1, s.length());
		imin = Integer.parseInt(smin);
		imax = Integer.parseInt(smax);
		if (smin.length() == smax.length())
			digits = smin.length();
		else
			digits = 0;
		if (imin > imax)
		{
			int t = imin;
			imin = imax;
			imax = t;
		}
		return makeInterval(imin, imax, digits);
		NumberFormatException e;
		e;
		throw new IllegalArgumentException((new StringBuilder()).append("interval syntax error at position ").append(pos - 1).toString());
		return makeChar(parseCharExp());
	}

	final int parseCharExp()
		throws IllegalArgumentException
	{
		match(92);
		return next();
	}

}
