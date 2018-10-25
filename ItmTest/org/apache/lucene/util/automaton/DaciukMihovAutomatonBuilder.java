// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DaciukMihovAutomatonBuilder.java

package org.apache.lucene.util.automaton;

import java.util.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.util.automaton:
//			State, Transition, Automaton

final class DaciukMihovAutomatonBuilder
{
	private static final class State
	{

		private static final int NO_LABELS[] = new int[0];
		private static final State NO_STATES[] = new State[0];
		int labels[];
		State states[];
		boolean is_final;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/DaciukMihovAutomatonBuilder.desiredAssertionStatus();

		State getState(int label)
		{
			int index = Arrays.binarySearch(labels, label);
			return index < 0 ? null : states[index];
		}

		public boolean equals(Object obj)
		{
			State other = (State)obj;
			return is_final == other.is_final && Arrays.equals(labels, other.labels) && referenceEquals(states, other.states);
		}

		public int hashCode()
		{
			int hash = is_final ? 1 : 0;
			hash ^= hash * 31 + labels.length;
			int arr$[] = labels;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				int c = arr$[i$];
				hash ^= hash * 31 + c;
			}

			arr$ = states;
			len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				State s = arr$[i$];
				hash ^= System.identityHashCode(s);
			}

			return hash;
		}

		boolean hasChildren()
		{
			return labels.length > 0;
		}

		State newState(int label)
		{
			if (!$assertionsDisabled && Arrays.binarySearch(labels, label) >= 0)
			{
				throw new AssertionError((new StringBuilder()).append("State already has transition labeled: ").append(label).toString());
			} else
			{
				labels = Arrays.copyOf(labels, labels.length + 1);
				states = (State[])Arrays.copyOf(states, states.length + 1);
				labels[labels.length - 1] = label;
				return states[states.length - 1] = new State();
			}
		}

		State lastChild()
		{
			if (!$assertionsDisabled && !hasChildren())
				throw new AssertionError("No outgoing transitions.");
			else
				return states[states.length - 1];
		}

		State lastChild(int label)
		{
			int index = labels.length - 1;
			State s = null;
			if (index >= 0 && labels[index] == label)
				s = states[index];
			if (!$assertionsDisabled && s != getState(label))
				throw new AssertionError();
			else
				return s;
		}

		void replaceLastChild(State state)
		{
			if (!$assertionsDisabled && !hasChildren())
			{
				throw new AssertionError("No outgoing transitions.");
			} else
			{
				states[states.length - 1] = state;
				return;
			}
		}

		private static boolean referenceEquals(Object a1[], Object a2[])
		{
			if (a1.length != a2.length)
				return false;
			for (int i = 0; i < a1.length; i++)
				if (a1[i] != a2[i])
					return false;

			return true;
		}


		private State()
		{
			labels = NO_LABELS;
			states = NO_STATES;
		}

	}


	private HashMap stateRegistry;
	private State root;
	private CharsRef previous;
	private static final Comparator comparator = CharsRef.getUTF16SortedAsUTF8Comparator();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/DaciukMihovAutomatonBuilder.desiredAssertionStatus();

	DaciukMihovAutomatonBuilder()
	{
		stateRegistry = new HashMap();
		root = new State();
	}

	public void add(CharsRef current)
	{
		if (!$assertionsDisabled && stateRegistry == null)
			throw new AssertionError("Automaton already built.");
		if (!$assertionsDisabled && previous != null && comparator.compare(previous, current) > 0)
			throw new AssertionError((new StringBuilder()).append("Input must be in sorted UTF-8 order: ").append(previous).append(" >= ").append(current).toString());
		if (!$assertionsDisabled && !setPrevious(current))
			throw new AssertionError();
		int pos = 0;
		int max = current.length();
		State state = root;
		State next;
		for (; pos < max && (next = state.lastChild(Character.codePointAt(current, pos))) != null; pos += Character.charCount(Character.codePointAt(current, pos)))
			state = next;

		if (state.hasChildren())
			replaceOrRegister(state);
		addSuffix(state, current, pos);
	}

	public State complete()
	{
		if (stateRegistry == null)
			throw new IllegalStateException();
		if (root.hasChildren())
			replaceOrRegister(root);
		stateRegistry = null;
		return root;
	}

	private static org.apache.lucene.util.automaton.State convert(State s, IdentityHashMap visited)
	{
		org.apache.lucene.util.automaton.State converted = (org.apache.lucene.util.automaton.State)visited.get(s);
		if (converted != null)
			return converted;
		converted = new org.apache.lucene.util.automaton.State();
		converted.setAccept(s.is_final);
		visited.put(s, converted);
		int i = 0;
		int labels[] = s.labels;
		State arr$[] = s.states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State target = arr$[i$];
			converted.addTransition(new Transition(labels[i++], convert(target, visited)));
		}

		return converted;
	}

	public static Automaton build(Collection input)
	{
		DaciukMihovAutomatonBuilder builder = new DaciukMihovAutomatonBuilder();
		CharsRef scratch = new CharsRef();
		for (Iterator i$ = input.iterator(); i$.hasNext(); builder.add(scratch))
		{
			BytesRef b = (BytesRef)i$.next();
			UnicodeUtil.UTF8toUTF16(b, scratch);
		}

		Automaton a = new Automaton();
		a.initial = convert(builder.complete(), new IdentityHashMap());
		a.deterministic = true;
		return a;
	}

	private boolean setPrevious(CharsRef current)
	{
		previous = CharsRef.deepCopyOf(current);
		return true;
	}

	private void replaceOrRegister(State state)
	{
		State child = state.lastChild();
		if (child.hasChildren())
			replaceOrRegister(child);
		State registered = (State)stateRegistry.get(child);
		if (registered != null)
			state.replaceLastChild(registered);
		else
			stateRegistry.put(child, child);
	}

	private void addSuffix(State state, CharSequence current, int fromIndex)
	{
		int cp;
		for (int len = current.length(); fromIndex < len; fromIndex += Character.charCount(cp))
		{
			cp = Character.codePointAt(current, fromIndex);
			state = state.newState(cp);
		}

		state.is_final = true;
	}

}
