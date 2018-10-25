// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicAutomata.java

package org.apache.lucene.util.automaton;

import java.util.*;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Automaton, State, Transition, StatePair, 
//			BasicOperations, DaciukMihovAutomatonBuilder

public final class BasicAutomata
{

	private BasicAutomata()
	{
	}

	public static Automaton makeEmpty()
	{
		Automaton a = new Automaton();
		State s = new State();
		a.initial = s;
		a.deterministic = true;
		return a;
	}

	public static Automaton makeEmptyString()
	{
		Automaton a = new Automaton();
		a.singleton = "";
		a.deterministic = true;
		return a;
	}

	public static Automaton makeAnyString()
	{
		Automaton a = new Automaton();
		State s = new State();
		a.initial = s;
		s.accept = true;
		s.addTransition(new Transition(0, 0x10ffff, s));
		a.deterministic = true;
		return a;
	}

	public static Automaton makeAnyChar()
	{
		return makeCharRange(0, 0x10ffff);
	}

	public static Automaton makeChar(int c)
	{
		Automaton a = new Automaton();
		a.singleton = new String(Character.toChars(c));
		a.deterministic = true;
		return a;
	}

	public static Automaton makeCharRange(int min, int max)
	{
		if (min == max)
			return makeChar(min);
		Automaton a = new Automaton();
		State s1 = new State();
		State s2 = new State();
		a.initial = s1;
		s2.accept = true;
		if (min <= max)
			s1.addTransition(new Transition(min, max, s2));
		a.deterministic = true;
		return a;
	}

	private static State anyOfRightLength(String x, int n)
	{
		State s = new State();
		if (x.length() == n)
			s.setAccept(true);
		else
			s.addTransition(new Transition(48, 57, anyOfRightLength(x, n + 1)));
		return s;
	}

	private static State atLeast(String x, int n, Collection initials, boolean zeros)
	{
		State s = new State();
		if (x.length() == n)
		{
			s.setAccept(true);
		} else
		{
			if (zeros)
				initials.add(s);
			char c = x.charAt(n);
			s.addTransition(new Transition(c, atLeast(x, n + 1, initials, zeros && c == '0')));
			if (c < '9')
				s.addTransition(new Transition((char)(c + 1), 57, anyOfRightLength(x, n + 1)));
		}
		return s;
	}

	private static State atMost(String x, int n)
	{
		State s = new State();
		if (x.length() == n)
		{
			s.setAccept(true);
		} else
		{
			char c = x.charAt(n);
			s.addTransition(new Transition(c, atMost(x, (char)n + 1)));
			if (c > '0')
				s.addTransition(new Transition(48, (char)(c - 1), anyOfRightLength(x, n + 1)));
		}
		return s;
	}

	private static State between(String x, String y, int n, Collection initials, boolean zeros)
	{
		State s = new State();
		if (x.length() == n)
		{
			s.setAccept(true);
		} else
		{
			if (zeros)
				initials.add(s);
			char cx = x.charAt(n);
			char cy = y.charAt(n);
			if (cx == cy)
			{
				s.addTransition(new Transition(cx, between(x, y, n + 1, initials, zeros && cx == '0')));
			} else
			{
				s.addTransition(new Transition(cx, atLeast(x, n + 1, initials, zeros && cx == '0')));
				s.addTransition(new Transition(cy, atMost(y, n + 1)));
				if (cx + 1 < cy)
					s.addTransition(new Transition((char)(cx + 1), (char)(cy - 1), anyOfRightLength(x, n + 1)));
			}
		}
		return s;
	}

	public static Automaton makeInterval(int min, int max, int digits)
		throws IllegalArgumentException
	{
		Automaton a = new Automaton();
		String x = Integer.toString(min);
		String y = Integer.toString(max);
		if (min > max || digits > 0 && y.length() > digits)
			throw new IllegalArgumentException();
		int d;
		if (digits > 0)
			d = digits;
		else
			d = y.length();
		StringBuilder bx = new StringBuilder();
		for (int i = x.length(); i < d; i++)
			bx.append('0');

		bx.append(x);
		x = bx.toString();
		StringBuilder by = new StringBuilder();
		for (int i = y.length(); i < d; i++)
			by.append('0');

		by.append(y);
		y = by.toString();
		Collection initials = new ArrayList();
		a.initial = between(x, y, 0, initials, digits <= 0);
		if (digits <= 0)
		{
			ArrayList pairs = new ArrayList();
			Iterator i$ = initials.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				State p = (State)i$.next();
				if (a.initial != p)
					pairs.add(new StatePair(a.initial, p));
			} while (true);
			BasicOperations.addEpsilons(a, pairs);
			a.initial.addTransition(new Transition(48, a.initial));
			a.deterministic = false;
		} else
		{
			a.deterministic = true;
		}
		a.checkMinimizeAlways();
		return a;
	}

	public static Automaton makeString(String s)
	{
		Automaton a = new Automaton();
		a.singleton = s;
		a.deterministic = true;
		return a;
	}

	public static Automaton makeStringUnion(Collection utf8Strings)
	{
		if (utf8Strings.isEmpty())
			return makeEmpty();
		else
			return DaciukMihovAutomatonBuilder.build(utf8Strings);
	}
}
