// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicOperations.java

package org.apache.lucene.util.automaton;

import java.util.*;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.automaton:
//			State, Automaton, StatePair, Transition, 
//			SortedIntSet, BasicAutomata

public final class BasicOperations
{
	private static final class PointTransitionSet
	{

		int count;
		PointTransitions points[];
		private static final int HASHMAP_CUTOVER = 30;
		private final HashMap map;
		private boolean useHash;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/BasicOperations.desiredAssertionStatus();

		private PointTransitions next(int point)
		{
			if (count == points.length)
			{
				PointTransitions newArray[] = new PointTransitions[ArrayUtil.oversize(1 + count, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
				System.arraycopy(points, 0, newArray, 0, count);
				points = newArray;
			}
			PointTransitions points0 = points[count];
			if (points0 == null)
				points0 = points[count] = new PointTransitions();
			points0.reset(point);
			count++;
			return points0;
		}

		private PointTransitions find(int point)
		{
			if (useHash)
			{
				Integer pi = Integer.valueOf(point);
				PointTransitions p = (PointTransitions)map.get(pi);
				if (p == null)
				{
					p = next(point);
					map.put(pi, p);
				}
				return p;
			}
			for (int i = 0; i < count; i++)
				if (points[i].point == point)
					return points[i];

			PointTransitions p = next(point);
			if (count == 30)
			{
				if (!$assertionsDisabled && map.size() != 0)
					throw new AssertionError();
				for (int i = 0; i < count; i++)
					map.put(Integer.valueOf(points[i].point), points[i]);

				useHash = true;
			}
			return p;
		}

		public void reset()
		{
			if (useHash)
			{
				map.clear();
				useHash = false;
			}
			count = 0;
		}

		public void sort()
		{
			if (count > 1)
				ArrayUtil.mergeSort(points, 0, count);
		}

		public void add(Transition t)
		{
			find(t.min).starts.add(t);
			find(1 + t.max).ends.add(t);
		}

		public String toString()
		{
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < count; i++)
			{
				if (i > 0)
					s.append(' ');
				s.append(points[i].point).append(':').append(points[i].starts.count).append(',').append(points[i].ends.count);
			}

			return s.toString();
		}


		private PointTransitionSet()
		{
			points = new PointTransitions[5];
			map = new HashMap();
			useHash = false;
		}

	}

	private static final class PointTransitions
		implements Comparable
	{

		int point;
		final TransitionList ends;
		final TransitionList starts;

		public int compareTo(PointTransitions other)
		{
			return point - other.point;
		}

		public void reset(int point)
		{
			this.point = point;
			ends.count = 0;
			starts.count = 0;
		}

		public boolean equals(Object other)
		{
			return ((PointTransitions)other).point == point;
		}

		public int hashCode()
		{
			return point;
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((PointTransitions)x0);
		}

		private PointTransitions()
		{
			ends = new TransitionList();
			starts = new TransitionList();
		}

	}

	private static final class TransitionList
	{

		Transition transitions[];
		int count;

		public void add(Transition t)
		{
			if (transitions.length == count)
			{
				Transition newArray[] = new Transition[ArrayUtil.oversize(1 + count, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
				System.arraycopy(transitions, 0, newArray, 0, count);
				transitions = newArray;
			}
			transitions[count++] = t;
		}

		private TransitionList()
		{
			transitions = new Transition[2];
		}

	}


	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/BasicOperations.desiredAssertionStatus();

	private BasicOperations()
	{
	}

	public static Automaton concatenate(Automaton a1, Automaton a2)
	{
		if (a1.isSingleton() && a2.isSingleton())
			return BasicAutomata.makeString((new StringBuilder()).append(a1.singleton).append(a2.singleton).toString());
		if (isEmpty(a1) || isEmpty(a2))
			return BasicAutomata.makeEmpty();
		boolean deterministic = a1.isSingleton() && a2.isDeterministic();
		if (a1 == a2)
		{
			a1 = a1.cloneExpanded();
			a2 = a2.cloneExpanded();
		} else
		{
			a1 = a1.cloneExpandedIfRequired();
			a2 = a2.cloneExpandedIfRequired();
		}
		State s;
		for (Iterator i$ = a1.getAcceptStates().iterator(); i$.hasNext(); s.addEpsilon(a2.initial))
		{
			s = (State)i$.next();
			s.accept = false;
		}

		a1.deterministic = deterministic;
		a1.clearNumberedStates();
		a1.checkMinimizeAlways();
		return a1;
	}

	public static Automaton concatenate(List l)
	{
		if (l.isEmpty())
			return BasicAutomata.makeEmptyString();
		boolean all_singleton = true;
		Iterator i$ = l.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Automaton a = (Automaton)i$.next();
			if (a.isSingleton())
				continue;
			all_singleton = false;
			break;
		} while (true);
		if (all_singleton)
		{
			StringBuilder b = new StringBuilder();
			Automaton a;
			for (Iterator i$ = l.iterator(); i$.hasNext(); b.append(a.singleton))
				a = (Automaton)i$.next();

			return BasicAutomata.makeString(b.toString());
		}
		for (b = l.iterator(); b.hasNext();)
		{
			Automaton a = (Automaton)b.next();
			if (isEmpty(a))
				return BasicAutomata.makeEmpty();
		}

		Set ids = new HashSet();
		Automaton a;
		for (Iterator i$ = l.iterator(); i$.hasNext(); ids.add(Integer.valueOf(System.identityHashCode(a))))
			a = (Automaton)i$.next();

		boolean has_aliases = ids.size() != l.size();
		Automaton b = (Automaton)l.get(0);
		if (has_aliases)
			b = b.cloneExpanded();
		else
			b = b.cloneExpandedIfRequired();
		Set ac = b.getAcceptStates();
		boolean first = true;
		Iterator i$ = l.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Automaton a = (Automaton)i$.next();
			if (first)
				first = false;
			else
			if (!a.isEmptyString())
			{
				Automaton aa = a;
				if (has_aliases)
					aa = aa.cloneExpanded();
				else
					aa = aa.cloneExpandedIfRequired();
				Set ns = aa.getAcceptStates();
				Iterator i$ = ac.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					State s = (State)i$.next();
					s.accept = false;
					s.addEpsilon(aa.initial);
					if (s.accept)
						ns.add(s);
				} while (true);
				ac = ns;
			}
		} while (true);
		b.deterministic = false;
		b.clearNumberedStates();
		b.checkMinimizeAlways();
		return b;
	}

	public static Automaton optional(Automaton a)
	{
		a = a.cloneExpandedIfRequired();
		State s = new State();
		s.addEpsilon(a.initial);
		s.accept = true;
		a.initial = s;
		a.deterministic = false;
		a.clearNumberedStates();
		a.checkMinimizeAlways();
		return a;
	}

	public static Automaton repeat(Automaton a)
	{
		a = a.cloneExpanded();
		State s = new State();
		s.accept = true;
		s.addEpsilon(a.initial);
		State p;
		for (Iterator i$ = a.getAcceptStates().iterator(); i$.hasNext(); p.addEpsilon(s))
			p = (State)i$.next();

		a.initial = s;
		a.deterministic = false;
		a.clearNumberedStates();
		a.checkMinimizeAlways();
		return a;
	}

	public static Automaton repeat(Automaton a, int min)
	{
		if (min == 0)
			return repeat(a);
		List as = new ArrayList();
		while (min-- > 0) 
			as.add(a);
		as.add(repeat(a));
		return concatenate(as);
	}

	public static Automaton repeat(Automaton a, int min, int max)
	{
		if (min > max)
			return BasicAutomata.makeEmpty();
		max -= min;
		a.expandSingleton();
		Automaton b;
		if (min == 0)
			b = BasicAutomata.makeEmptyString();
		else
		if (min == 1)
		{
			b = a.clone();
		} else
		{
			List as = new ArrayList();
			while (min-- > 0) 
				as.add(a);
			b = concatenate(as);
		}
		if (max > 0)
		{
			Automaton d;
			Automaton c;
			for (d = a.clone(); --max > 0; d = c)
			{
				c = a.clone();
				State p;
				for (Iterator i$ = c.getAcceptStates().iterator(); i$.hasNext(); p.addEpsilon(d.initial))
					p = (State)i$.next();

			}

			State p;
			for (Iterator i$ = b.getAcceptStates().iterator(); i$.hasNext(); p.addEpsilon(d.initial))
				p = (State)i$.next();

			b.deterministic = false;
			b.clearNumberedStates();
			b.checkMinimizeAlways();
		}
		return b;
	}

	public static Automaton complement(Automaton a)
	{
		a = a.cloneExpandedIfRequired();
		a.determinize();
		a.totalize();
		State arr$[] = a.getNumberedStates();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State p = arr$[i$];
			p.accept = !p.accept;
		}

		a.removeDeadTransitions();
		return a;
	}

	public static Automaton minus(Automaton a1, Automaton a2)
	{
		if (isEmpty(a1) || a1 == a2)
			return BasicAutomata.makeEmpty();
		if (isEmpty(a2))
			return a1.cloneIfRequired();
		if (a1.isSingleton())
		{
			if (run(a2, a1.singleton))
				return BasicAutomata.makeEmpty();
			else
				return a1.cloneIfRequired();
		} else
		{
			return intersection(a1, a2.complement());
		}
	}

	public static Automaton intersection(Automaton a1, Automaton a2)
	{
		if (a1.isSingleton())
			if (run(a2, a1.singleton))
				return a1.cloneIfRequired();
			else
				return BasicAutomata.makeEmpty();
		if (a2.isSingleton())
			if (run(a1, a2.singleton))
				return a2.cloneIfRequired();
			else
				return BasicAutomata.makeEmpty();
		if (a1 == a2)
			return a1.cloneIfRequired();
		Transition transitions1[][] = a1.getSortedTransitions();
		Transition transitions2[][] = a2.getSortedTransitions();
		Automaton c = new Automaton();
		LinkedList worklist = new LinkedList();
		HashMap newstates = new HashMap();
		StatePair p = new StatePair(c.initial, a1.initial, a2.initial);
		worklist.add(p);
		newstates.put(p, p);
		while (worklist.size() > 0) 
		{
			p = (StatePair)worklist.removeFirst();
			p.s.accept = p.s1.accept && p.s2.accept;
			Transition t1[] = transitions1[p.s1.number];
			Transition t2[] = transitions2[p.s2.number];
			int n1 = 0;
			int b2 = 0;
			while (n1 < t1.length) 
			{
				for (; b2 < t2.length && t2[b2].max < t1[n1].min; b2++);
				for (int n2 = b2; n2 < t2.length && t1[n1].max >= t2[n2].min; n2++)
				{
					if (t2[n2].max < t1[n1].min)
						continue;
					StatePair q = new StatePair(t1[n1].to, t2[n2].to);
					StatePair r = (StatePair)newstates.get(q);
					if (r == null)
					{
						q.s = new State();
						worklist.add(q);
						newstates.put(q, q);
						r = q;
					}
					int min = t1[n1].min <= t2[n2].min ? t2[n2].min : t1[n1].min;
					int max = t1[n1].max >= t2[n2].max ? t2[n2].max : t1[n1].max;
					p.s.addTransition(new Transition(min, max, r.s));
				}

				n1++;
			}
		}
		c.deterministic = a1.deterministic && a2.deterministic;
		c.removeDeadTransitions();
		c.checkMinimizeAlways();
		return c;
	}

	public static boolean sameLanguage(Automaton a1, Automaton a2)
	{
		if (a1 == a2)
			return true;
		if (a1.isSingleton() && a2.isSingleton())
			return a1.singleton.equals(a2.singleton);
		if (a1.isSingleton())
			return subsetOf(a1, a2) && subsetOf(a2, a1);
		else
			return subsetOf(a2, a1) && subsetOf(a1, a2);
	}

	public static boolean subsetOf(Automaton a1, Automaton a2)
	{
		if (a1 == a2)
			return true;
		if (a1.isSingleton())
			if (a2.isSingleton())
				return a1.singleton.equals(a2.singleton);
			else
				return run(a2, a1.singleton);
		a2.determinize();
		Transition transitions1[][] = a1.getSortedTransitions();
		Transition transitions2[][] = a2.getSortedTransitions();
		LinkedList worklist = new LinkedList();
		HashSet visited = new HashSet();
		StatePair p = new StatePair(a1.initial, a2.initial);
		worklist.add(p);
		visited.add(p);
		while (worklist.size() > 0) 
		{
			p = (StatePair)worklist.removeFirst();
			if (p.s1.accept && !p.s2.accept)
				return false;
			Transition t1[] = transitions1[p.s1.number];
			Transition t2[] = transitions2[p.s2.number];
			int n1 = 0;
			int b2 = 0;
			while (n1 < t1.length) 
			{
				for (; b2 < t2.length && t2[b2].max < t1[n1].min; b2++);
				int min1 = t1[n1].min;
				int max1 = t1[n1].max;
				for (int n2 = b2; n2 < t2.length && t1[n1].max >= t2[n2].min; n2++)
				{
					if (t2[n2].min > min1)
						return false;
					if (t2[n2].max < 0x10ffff)
					{
						min1 = t2[n2].max + 1;
					} else
					{
						min1 = 0x10ffff;
						max1 = 0;
					}
					StatePair q = new StatePair(t1[n1].to, t2[n2].to);
					if (!visited.contains(q))
					{
						worklist.add(q);
						visited.add(q);
					}
				}

				if (min1 <= max1)
					return false;
				n1++;
			}
		}
		return true;
	}

	public static Automaton union(Automaton a1, Automaton a2)
	{
		if (a1.isSingleton() && a2.isSingleton() && a1.singleton.equals(a2.singleton) || a1 == a2)
			return a1.cloneIfRequired();
		if (a1 == a2)
		{
			a1 = a1.cloneExpanded();
			a2 = a2.cloneExpanded();
		} else
		{
			a1 = a1.cloneExpandedIfRequired();
			a2 = a2.cloneExpandedIfRequired();
		}
		State s = new State();
		s.addEpsilon(a1.initial);
		s.addEpsilon(a2.initial);
		a1.initial = s;
		a1.deterministic = false;
		a1.clearNumberedStates();
		a1.checkMinimizeAlways();
		return a1;
	}

	public static Automaton union(Collection l)
	{
		Set ids = new HashSet();
		Automaton a;
		for (Iterator i$ = l.iterator(); i$.hasNext(); ids.add(Integer.valueOf(System.identityHashCode(a))))
			a = (Automaton)i$.next();

		boolean has_aliases = ids.size() != l.size();
		State s = new State();
		Iterator i$ = l.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Automaton b = (Automaton)i$.next();
			if (!isEmpty(b))
			{
				Automaton bb = b;
				if (has_aliases)
					bb = bb.cloneExpanded();
				else
					bb = bb.cloneExpandedIfRequired();
				s.addEpsilon(bb.initial);
			}
		} while (true);
		Automaton a = new Automaton();
		a.initial = s;
		a.deterministic = false;
		a.clearNumberedStates();
		a.checkMinimizeAlways();
		return a;
	}

	public static void determinize(Automaton a)
	{
		if (a.deterministic || a.isSingleton())
			return;
		State allStates[] = a.getNumberedStates();
		boolean initAccept = a.initial.accept;
		int initNumber = a.initial.number;
		a.initial = new State();
		SortedIntSet.FrozenIntSet initialset = new SortedIntSet.FrozenIntSet(initNumber, a.initial);
		LinkedList worklist = new LinkedList();
		Map newstate = new HashMap();
		worklist.add(initialset);
		a.initial.accept = initAccept;
		newstate.put(initialset, a.initial);
		int newStateUpto = 0;
		State newStatesArray[] = new State[5];
		newStatesArray[newStateUpto] = a.initial;
		a.initial.number = newStateUpto;
		newStateUpto++;
		PointTransitionSet points = new PointTransitionSet();
		SortedIntSet statesSet = new SortedIntSet(5);
		while (worklist.size() > 0) 
		{
			SortedIntSet.FrozenIntSet s = (SortedIntSet.FrozenIntSet)worklist.removeFirst();
			for (int i = 0; i < s.values.length; i++)
			{
				State s0 = allStates[s.values[i]];
				for (int j = 0; j < s0.numTransitions; j++)
					points.add(s0.transitionsArray[j]);

			}

			if (points.count != 0)
			{
				points.sort();
				int lastPoint = -1;
				int accCount = 0;
				State r = s.state;
				for (int i = 0; i < points.count; i++)
				{
					int point = points.points[i].point;
					if (statesSet.upto > 0)
					{
						if (!$assertionsDisabled && lastPoint == -1)
							throw new AssertionError();
						statesSet.computeHash();
						State q = (State)newstate.get(statesSet);
						if (q == null)
						{
							q = new State();
							SortedIntSet.FrozenIntSet p = statesSet.freeze(q);
							worklist.add(p);
							if (newStateUpto == newStatesArray.length)
							{
								State newArray[] = new State[ArrayUtil.oversize(1 + newStateUpto, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
								System.arraycopy(newStatesArray, 0, newArray, 0, newStateUpto);
								newStatesArray = newArray;
							}
							newStatesArray[newStateUpto] = q;
							q.number = newStateUpto;
							newStateUpto++;
							q.accept = accCount > 0;
							newstate.put(p, q);
						} else
						if (!$assertionsDisabled && (accCount > 0) != q.accept)
							throw new AssertionError((new StringBuilder()).append("accCount=").append(accCount).append(" vs existing accept=").append(q.accept).append(" states=").append(statesSet).toString());
						r.addTransition(new Transition(lastPoint, point - 1, q));
					}
					Transition transitions[] = points.points[i].ends.transitions;
					int limit = points.points[i].ends.count;
					for (int j = 0; j < limit; j++)
					{
						Transition t = transitions[j];
						Integer num = Integer.valueOf(t.to.number);
						statesSet.decr(num.intValue());
						accCount -= t.to.accept ? 1 : 0;
					}

					points.points[i].ends.count = 0;
					transitions = points.points[i].starts.transitions;
					limit = points.points[i].starts.count;
					for (int j = 0; j < limit; j++)
					{
						Transition t = transitions[j];
						Integer num = Integer.valueOf(t.to.number);
						statesSet.incr(num.intValue());
						accCount += t.to.accept ? 1 : 0;
					}

					lastPoint = point;
					points.points[i].starts.count = 0;
				}

				points.reset();
				if (!$assertionsDisabled && statesSet.upto != 0)
					throw new AssertionError((new StringBuilder()).append("upto=").append(statesSet.upto).toString());
			}
		}
		a.deterministic = true;
		a.setNumberedStates(newStatesArray, newStateUpto);
	}

	public static void addEpsilons(Automaton a, Collection pairs)
	{
		a.expandSingleton();
		HashMap forward = new HashMap();
		HashMap back = new HashMap();
		StatePair p;
		HashSet from;
		for (Iterator i$ = pairs.iterator(); i$.hasNext(); from.add(p.s1))
		{
			p = (StatePair)i$.next();
			HashSet to = (HashSet)forward.get(p.s1);
			if (to == null)
			{
				to = new HashSet();
				forward.put(p.s1, to);
			}
			to.add(p.s2);
			from = (HashSet)back.get(p.s2);
			if (from == null)
			{
				from = new HashSet();
				back.put(p.s2, from);
			}
		}

		LinkedList worklist = new LinkedList(pairs);
		HashSet workset = new HashSet(pairs);
		while (!worklist.isEmpty()) 
		{
			StatePair p = (StatePair)worklist.removeFirst();
			workset.remove(p);
			HashSet to = (HashSet)forward.get(p.s2);
			HashSet from = (HashSet)back.get(p.s1);
			if (to != null)
			{
				Iterator i$ = to.iterator();
				while (i$.hasNext()) 
				{
					State s = (State)i$.next();
					StatePair pp = new StatePair(p.s1, s);
					if (!pairs.contains(pp))
					{
						pairs.add(pp);
						((HashSet)forward.get(p.s1)).add(s);
						((HashSet)back.get(s)).add(p.s1);
						worklist.add(pp);
						workset.add(pp);
						if (from != null)
						{
							Iterator i$ = from.iterator();
							while (i$.hasNext()) 
							{
								State q = (State)i$.next();
								StatePair qq = new StatePair(q, p.s1);
								if (!workset.contains(qq))
								{
									worklist.add(qq);
									workset.add(qq);
								}
							}
						}
					}
				}
			}
		}
		StatePair p;
		for (Iterator i$ = pairs.iterator(); i$.hasNext(); p.s1.addEpsilon(p.s2))
			p = (StatePair)i$.next();

		a.deterministic = false;
		a.clearNumberedStates();
		a.checkMinimizeAlways();
	}

	public static boolean isEmptyString(Automaton a)
	{
		if (a.isSingleton())
			return a.singleton.length() == 0;
		else
			return a.initial.accept && a.initial.numTransitions() == 0;
	}

	public static boolean isEmpty(Automaton a)
	{
		if (a.isSingleton())
			return false;
		else
			return !a.initial.accept && a.initial.numTransitions() == 0;
	}

	public static boolean isTotal(Automaton a)
	{
		if (a.isSingleton())
			return false;
		if (a.initial.accept && a.initial.numTransitions() == 1)
		{
			Transition t = (Transition)a.initial.getTransitions().iterator().next();
			return t.to == a.initial && t.min == 0 && t.max == 0x10ffff;
		} else
		{
			return false;
		}
	}

	public static boolean run(Automaton a, String s)
	{
		if (a.isSingleton())
			return s.equals(a.singleton);
		if (a.deterministic)
		{
			State p = a.initial;
			int i = 0;
			int cp = 0;
			for (; i < s.length(); i += Character.charCount(cp))
			{
				State q = p.step(cp = s.codePointAt(i));
				if (q == null)
					return false;
				p = q;
			}

			return p.accept;
		}
		State states[] = a.getNumberedStates();
		LinkedList pp = new LinkedList();
		LinkedList pp_other = new LinkedList();
		BitSet bb = new BitSet(states.length);
		BitSet bb_other = new BitSet(states.length);
		pp.add(a.initial);
		ArrayList dest = new ArrayList();
		boolean accept = a.initial.accept;
		int i = 0;
		int c = 0;
		do
		{
			if (i >= s.length())
				break;
			c = s.codePointAt(i);
			accept = false;
			pp_other.clear();
			bb_other.clear();
			for (Iterator i$ = pp.iterator(); i$.hasNext();)
			{
				State p = (State)i$.next();
				dest.clear();
				p.step(c, dest);
				Iterator i$ = dest.iterator();
				while (i$.hasNext()) 
				{
					State q = (State)i$.next();
					if (q.accept)
						accept = true;
					if (!bb_other.get(q.number))
					{
						bb_other.set(q.number);
						pp_other.add(q);
					}
				}
			}

			LinkedList tp = pp;
			pp = pp_other;
			pp_other = tp;
			BitSet tb = bb;
			bb = bb_other;
			bb_other = tb;
			i += Character.charCount(c);
		} while (true);
		return accept;
	}

}
