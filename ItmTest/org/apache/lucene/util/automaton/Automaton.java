// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Automaton.java

package org.apache.lucene.util.automaton;

import java.util.*;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.automaton:
//			State, Transition, MinimizationOperations, BasicOperations

public class Automaton
	implements Cloneable
{

	public static final int MINIMIZE_HOPCROFT = 2;
	static int minimization = 2;
	State initial;
	boolean deterministic;
	transient Object info;
	String singleton;
	static boolean minimize_always = false;
	static boolean allow_mutation = false;
	private State numberedStates[];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/Automaton.desiredAssertionStatus();

	public Automaton(State initial)
	{
		this.initial = initial;
		deterministic = true;
		singleton = null;
	}

	public Automaton()
	{
		this(new State());
	}

	public static void setMinimization(int algorithm)
	{
		minimization = algorithm;
	}

	public static void setMinimizeAlways(boolean flag)
	{
		minimize_always = flag;
	}

	public static boolean setAllowMutate(boolean flag)
	{
		boolean b = allow_mutation;
		allow_mutation = flag;
		return b;
	}

	static boolean getAllowMutate()
	{
		return allow_mutation;
	}

	void checkMinimizeAlways()
	{
		if (minimize_always)
			MinimizationOperations.minimize(this);
	}

	boolean isSingleton()
	{
		return singleton != null;
	}

	public String getSingleton()
	{
		return singleton;
	}

	public State getInitialState()
	{
		expandSingleton();
		return initial;
	}

	public boolean isDeterministic()
	{
		return deterministic;
	}

	public void setDeterministic(boolean deterministic)
	{
		this.deterministic = deterministic;
	}

	public void setInfo(Object info)
	{
		this.info = info;
	}

	public Object getInfo()
	{
		return info;
	}

	public State[] getNumberedStates()
	{
		if (numberedStates == null)
		{
			expandSingleton();
			Set visited = new HashSet();
			LinkedList worklist = new LinkedList();
			numberedStates = new State[4];
			int upto = 0;
			worklist.add(initial);
			visited.add(initial);
			initial.number = upto;
			numberedStates[upto] = initial;
			upto++;
			while (worklist.size() > 0) 
			{
				State s = (State)worklist.removeFirst();
				int i = 0;
				while (i < s.numTransitions) 
				{
					Transition t = s.transitionsArray[i];
					if (!visited.contains(t.to))
					{
						visited.add(t.to);
						worklist.add(t.to);
						t.to.number = upto;
						if (upto == numberedStates.length)
						{
							State newArray[] = new State[ArrayUtil.oversize(1 + upto, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
							System.arraycopy(numberedStates, 0, newArray, 0, upto);
							numberedStates = newArray;
						}
						numberedStates[upto] = t.to;
						upto++;
					}
					i++;
				}
			}
			if (numberedStates.length != upto)
			{
				State newArray[] = new State[upto];
				System.arraycopy(numberedStates, 0, newArray, 0, upto);
				numberedStates = newArray;
			}
		}
		return numberedStates;
	}

	public void setNumberedStates(State states[])
	{
		setNumberedStates(states, states.length);
	}

	public void setNumberedStates(State states[], int count)
	{
		if (!$assertionsDisabled && count > states.length)
			throw new AssertionError();
		if (count < states.length)
		{
			State newArray[] = new State[count];
			System.arraycopy(states, 0, newArray, 0, count);
			numberedStates = newArray;
		} else
		{
			numberedStates = states;
		}
	}

	public void clearNumberedStates()
	{
		numberedStates = null;
	}

	public Set getAcceptStates()
	{
		expandSingleton();
		HashSet accepts = new HashSet();
		HashSet visited = new HashSet();
		LinkedList worklist = new LinkedList();
		worklist.add(initial);
		visited.add(initial);
		while (worklist.size() > 0) 
		{
			State s = (State)worklist.removeFirst();
			if (s.accept)
				accepts.add(s);
			Iterator i$ = s.getTransitions().iterator();
			while (i$.hasNext()) 
			{
				Transition t = (Transition)i$.next();
				if (!visited.contains(t.to))
				{
					visited.add(t.to);
					worklist.add(t.to);
				}
			}
		}
		return accepts;
	}

	void totalize()
	{
		State s = new State();
		s.addTransition(new Transition(0, 0x10ffff, s));
		State arr$[] = getNumberedStates();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State p = arr$[i$];
			int maxi = 0;
			p.sortTransitions(Transition.CompareByMinMaxThenDest);
			Iterator i$ = p.getTransitions().iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Transition t = (Transition)i$.next();
				if (t.min > maxi)
					p.addTransition(new Transition(maxi, t.min - 1, s));
				if (t.max + 1 > maxi)
					maxi = t.max + 1;
			} while (true);
			if (maxi <= 0x10ffff)
				p.addTransition(new Transition(maxi, 0x10ffff, s));
		}

		clearNumberedStates();
	}

	public void restoreInvariant()
	{
		removeDeadTransitions();
	}

	public void reduce()
	{
		State states[] = getNumberedStates();
		if (isSingleton())
			return;
		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			s.reduce();
		}

	}

	int[] getStartPoints()
	{
		State states[] = getNumberedStates();
		Set pointset = new HashSet();
		pointset.add(Integer.valueOf(0));
		State arr$[] = states;
		int len$ = arr$.length;
label0:
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			Iterator i$ = s.getTransitions().iterator();
			do
			{
				if (!i$.hasNext())
					continue label0;
				Transition t = (Transition)i$.next();
				pointset.add(Integer.valueOf(t.min));
				if (t.max < 0x10ffff)
					pointset.add(Integer.valueOf(t.max + 1));
			} while (true);
		}

		int points[] = new int[pointset.size()];
		int n = 0;
		for (Iterator i$ = pointset.iterator(); i$.hasNext();)
		{
			Integer m = (Integer)i$.next();
			points[n++] = m.intValue();
		}

		Arrays.sort(points);
		return points;
	}

	private State[] getLiveStates()
	{
		State states[] = getNumberedStates();
		Set live = new HashSet();
		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State q = arr$[i$];
			if (q.isAccept())
				live.add(q);
		}

		Set map[] = new Set[states.length];
		for (int i = 0; i < map.length; i++)
			map[i] = new HashSet();

		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			for (int i = 0; i < s.numTransitions; i++)
				map[s.transitionsArray[i].to.number].add(s);

		}

		for (LinkedList worklist = new LinkedList(live); worklist.size() > 0;)
		{
			State s = (State)worklist.removeFirst();
			Iterator i$ = map[s.number].iterator();
			while (i$.hasNext()) 
			{
				State p = (State)i$.next();
				if (!live.contains(p))
				{
					live.add(p);
					worklist.add(p);
				}
			}
		}

		return (State[])live.toArray(new State[live.size()]);
	}

	public void removeDeadTransitions()
	{
		State states[] = getNumberedStates();
		if (isSingleton())
			return;
		State live[] = getLiveStates();
		BitSet liveSet = new BitSet(states.length);
		State arr$[] = live;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			liveSet.set(s.number);
		}

		arr$ = states;
		len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			int upto = 0;
			for (int i = 0; i < s.numTransitions; i++)
			{
				Transition t = s.transitionsArray[i];
				if (liveSet.get(t.to.number))
					s.transitionsArray[upto++] = s.transitionsArray[i];
			}

			s.numTransitions = upto;
		}

		for (int i = 0; i < live.length; i++)
			live[i].number = i;

		if (live.length > 0)
			setNumberedStates(live);
		else
			clearNumberedStates();
		reduce();
	}

	public Transition[][] getSortedTransitions()
	{
		State states[] = getNumberedStates();
		Transition transitions[][] = new Transition[states.length][];
		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			s.sortTransitions(Transition.CompareByMinMaxThenDest);
			s.trimTransitionsArray();
			transitions[s.number] = s.transitionsArray;
			if (!$assertionsDisabled && s.transitionsArray == null)
				throw new AssertionError();
		}

		return transitions;
	}

	public void expandSingleton()
	{
		if (isSingleton())
		{
			State p = new State();
			initial = p;
			int i = 0;
			int cp = 0;
			for (; i < singleton.length(); i += Character.charCount(cp))
			{
				State q = new State();
				p.addTransition(new Transition(cp = singleton.codePointAt(i), q));
				p = q;
			}

			p.accept = true;
			deterministic = true;
			singleton = null;
		}
	}

	public int getNumberOfStates()
	{
		if (isSingleton())
			return singleton.codePointCount(0, singleton.length()) + 1;
		else
			return getNumberedStates().length;
	}

	public int getNumberOfTransitions()
	{
		if (isSingleton())
			return singleton.codePointCount(0, singleton.length());
		int c = 0;
		State arr$[] = getNumberedStates();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			c += s.numTransitions();
		}

		return c;
	}

	public boolean equals(Object obj)
	{
		throw new UnsupportedOperationException("use BasicOperations.sameLanguage instead");
	}

	public int hashCode()
	{
		throw new UnsupportedOperationException();
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder();
		if (isSingleton())
		{
			b.append("singleton: ");
			int length = singleton.codePointCount(0, singleton.length());
			int codepoints[] = new int[length];
			int i = 0;
			int j = 0;
			int cp = 0;
			for (; i < singleton.length(); i += Character.charCount(cp))
				codepoints[j++] = cp = singleton.codePointAt(i);

			int arr$[] = codepoints;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				int c = arr$[i$];
				Transition.appendCharString(c, b);
			}

			b.append("\n");
		} else
		{
			State states[] = getNumberedStates();
			b.append("initial state: ").append(initial.number).append("\n");
			State arr$[] = states;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				State s = arr$[i$];
				b.append(s.toString());
			}

		}
		return b.toString();
	}

	public String toDot()
	{
		StringBuilder b = new StringBuilder("digraph Automaton {\n");
		b.append("  rankdir = LR;\n");
		State states[] = getNumberedStates();
		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			b.append("  ").append(s.number);
			if (s.accept)
				b.append(" [shape=doublecircle,label=\"\"];\n");
			else
				b.append(" [shape=circle,label=\"\"];\n");
			if (s == initial)
			{
				b.append("  initial [shape=plaintext,label=\"\"];\n");
				b.append("  initial -> ").append(s.number).append("\n");
			}
			Transition t;
			for (Iterator i$ = s.getTransitions().iterator(); i$.hasNext(); t.appendDot(b))
			{
				t = (Transition)i$.next();
				b.append("  ").append(s.number);
			}

		}

		return b.append("}\n").toString();
	}

	Automaton cloneExpanded()
	{
		Automaton a = clone();
		a.expandSingleton();
		return a;
	}

	Automaton cloneExpandedIfRequired()
	{
		if (allow_mutation)
		{
			expandSingleton();
			return this;
		} else
		{
			return cloneExpanded();
		}
	}

	public Automaton clone()
	{
		Automaton a;
		a = (Automaton)super.clone();
		if (!isSingleton())
		{
			HashMap m = new HashMap();
			State states[] = getNumberedStates();
			State arr$[] = states;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				State s = arr$[i$];
				m.put(s, new State());
			}

			arr$ = states;
			len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				State s = arr$[i$];
				State p = (State)m.get(s);
				p.accept = s.accept;
				if (s == initial)
					a.initial = p;
				Transition t;
				for (Iterator i$ = s.getTransitions().iterator(); i$.hasNext(); p.addTransition(new Transition(t.min, t.max, (State)m.get(t.to))))
					t = (Transition)i$.next();

			}

		}
		a.clearNumberedStates();
		return a;
		CloneNotSupportedException e;
		e;
		throw new RuntimeException(e);
	}

	Automaton cloneIfRequired()
	{
		if (allow_mutation)
			return this;
		else
			return clone();
	}

	public Automaton concatenate(Automaton a)
	{
		return BasicOperations.concatenate(this, a);
	}

	public static Automaton concatenate(List l)
	{
		return BasicOperations.concatenate(l);
	}

	public Automaton optional()
	{
		return BasicOperations.optional(this);
	}

	public Automaton repeat()
	{
		return BasicOperations.repeat(this);
	}

	public Automaton repeat(int min)
	{
		return BasicOperations.repeat(this, min);
	}

	public Automaton repeat(int min, int max)
	{
		return BasicOperations.repeat(this, min, max);
	}

	public Automaton complement()
	{
		return BasicOperations.complement(this);
	}

	public Automaton minus(Automaton a)
	{
		return BasicOperations.minus(this, a);
	}

	public Automaton intersection(Automaton a)
	{
		return BasicOperations.intersection(this, a);
	}

	public boolean subsetOf(Automaton a)
	{
		return BasicOperations.subsetOf(this, a);
	}

	public Automaton union(Automaton a)
	{
		return BasicOperations.union(this, a);
	}

	public static Automaton union(Collection l)
	{
		return BasicOperations.union(l);
	}

	public void determinize()
	{
		BasicOperations.determinize(this);
	}

	public boolean isEmptyString()
	{
		return BasicOperations.isEmptyString(this);
	}

	public static Automaton minimize(Automaton a)
	{
		MinimizationOperations.minimize(a);
		return a;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
