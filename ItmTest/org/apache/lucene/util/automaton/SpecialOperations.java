// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpecialOperations.java

package org.apache.lucene.util.automaton;

import java.util.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Transition, State, Automaton

public final class SpecialOperations
{

	private SpecialOperations()
	{
	}

	static int findIndex(int c, int points[])
	{
		int a = 0;
		for (int b = points.length; b - a > 1;)
		{
			int d = a + b >>> 1;
			if (points[d] > c)
				b = d;
			else
			if (points[d] < c)
				a = d;
			else
				return d;
		}

		return a;
	}

	public static boolean isFinite(Automaton a)
	{
		if (a.isSingleton())
			return true;
		else
			return isFinite(a.initial, new BitSet(a.getNumberOfStates()), new BitSet(a.getNumberOfStates()));
	}

	private static boolean isFinite(State s, BitSet path, BitSet visited)
	{
		path.set(s.number);
		for (Iterator i$ = s.getTransitions().iterator(); i$.hasNext();)
		{
			Transition t = (Transition)i$.next();
			if (path.get(t.to.number) || !visited.get(t.to.number) && !isFinite(t.to, path, visited))
				return false;
		}

		path.clear(s.number);
		visited.set(s.number);
		return true;
	}

	public static String getCommonPrefix(Automaton a)
	{
		if (a.isSingleton())
			return a.singleton;
		StringBuilder b = new StringBuilder();
		HashSet visited = new HashSet();
		State s = a.initial;
		boolean done;
		do
		{
			done = true;
			visited.add(s);
			if (!s.accept && s.numTransitions() == 1)
			{
				Transition t = (Transition)s.getTransitions().iterator().next();
				if (t.min == t.max && !visited.contains(t.to))
				{
					b.appendCodePoint(t.min);
					s = t.to;
					done = false;
				}
			}
		} while (!done);
		return b.toString();
	}

	public static BytesRef getCommonPrefixBytesRef(Automaton a)
	{
		if (a.isSingleton())
			return new BytesRef(a.singleton);
		BytesRef ref = new BytesRef(10);
		HashSet visited = new HashSet();
		State s = a.initial;
		boolean done;
		do
		{
			done = true;
			visited.add(s);
			if (!s.accept && s.numTransitions() == 1)
			{
				Transition t = (Transition)s.getTransitions().iterator().next();
				if (t.min == t.max && !visited.contains(t.to))
				{
					ref.grow(++ref.length);
					ref.bytes[ref.length - 1] = (byte)t.min;
					s = t.to;
					done = false;
				}
			}
		} while (!done);
		return ref;
	}

	public static String getCommonSuffix(Automaton a)
	{
		if (a.isSingleton())
		{
			return a.singleton;
		} else
		{
			Automaton r = a.clone();
			reverse(r);
			r.determinize();
			return (new StringBuilder(getCommonPrefix(r))).reverse().toString();
		}
	}

	public static BytesRef getCommonSuffixBytesRef(Automaton a)
	{
		if (a.isSingleton())
		{
			return new BytesRef(a.singleton);
		} else
		{
			Automaton r = a.clone();
			reverse(r);
			r.determinize();
			BytesRef ref = getCommonPrefixBytesRef(r);
			reverseBytes(ref);
			return ref;
		}
	}

	private static void reverseBytes(BytesRef ref)
	{
		if (ref.length <= 1)
			return;
		int num = ref.length >> 1;
		for (int i = ref.offset; i < ref.offset + num; i++)
		{
			byte b = ref.bytes[i];
			ref.bytes[i] = ref.bytes[(ref.offset * 2 + ref.length) - i - 1];
			ref.bytes[(ref.offset * 2 + ref.length) - i - 1] = b;
		}

	}

	public static Set reverse(Automaton a)
	{
		a.expandSingleton();
		HashMap m = new HashMap();
		State states[] = a.getNumberedStates();
		Set accept = new HashSet();
		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			if (s.isAccept())
				accept.add(s);
		}

		arr$ = states;
		len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State r = arr$[i$];
			m.put(r, new HashSet());
			r.accept = false;
		}

		arr$ = states;
		len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State r = arr$[i$];
			Transition t;
			for (Iterator i$ = r.getTransitions().iterator(); i$.hasNext(); ((HashSet)m.get(t.to)).add(new Transition(t.min, t.max, r)))
				t = (Transition)i$.next();

		}

		arr$ = states;
		len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State r = arr$[i$];
			Set tr = (Set)m.get(r);
			r.setTransitions((Transition[])tr.toArray(new Transition[tr.size()]));
		}

		a.initial.accept = true;
		a.initial = new State();
		State r;
		for (Iterator i$ = accept.iterator(); i$.hasNext(); a.initial.addEpsilon(r))
			r = (State)i$.next();

		a.deterministic = false;
		a.clearNumberedStates();
		return accept;
	}
}
