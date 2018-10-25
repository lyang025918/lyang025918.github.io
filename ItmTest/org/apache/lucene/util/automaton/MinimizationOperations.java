// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MinimizationOperations.java

package org.apache.lucene.util.automaton;

import java.util.*;

// Referenced classes of package org.apache.lucene.util.automaton:
//			State, Transition, Automaton

public final class MinimizationOperations
{
	static final class StateListNode
	{

		final State q;
		StateListNode next;
		StateListNode prev;
		final StateList sl;

		void remove()
		{
			sl.size--;
			if (sl.first == this)
				sl.first = next;
			else
				prev.next = next;
			if (sl.last == this)
				sl.last = prev;
			else
				next.prev = prev;
		}

		StateListNode(State q, StateList sl)
		{
			this.q = q;
			this.sl = sl;
			if (sl.size++ == 0)
			{
				sl.first = sl.last = this;
			} else
			{
				sl.last.next = this;
				prev = sl.last;
				sl.last = this;
			}
		}
	}

	static final class StateList
	{

		int size;
		StateListNode first;
		StateListNode last;

		StateListNode add(State q)
		{
			return new StateListNode(q, this);
		}

		StateList()
		{
		}
	}

	static final class IntPair
	{

		final int n1;
		final int n2;

		IntPair(int n1, int n2)
		{
			this.n1 = n1;
			this.n2 = n2;
		}
	}


	private MinimizationOperations()
	{
	}

	public static void minimize(Automaton a)
	{
		if (!a.isSingleton())
			minimizeHopcroft(a);
	}

	public static void minimizeHopcroft(Automaton a)
	{
		a.determinize();
		if (a.initial.numTransitions == 1)
		{
			Transition t = a.initial.transitionsArray[0];
			if (t.to == a.initial && t.min == 0 && t.max == 0x10ffff)
				return;
		}
		a.totalize();
		int sigma[] = a.getStartPoints();
		State states[] = a.getNumberedStates();
		int sigmaLen = sigma.length;
		int statesLen = states.length;
		ArrayList reverse[][] = (ArrayList[][])new ArrayList[statesLen][sigmaLen];
		HashSet partition[] = (HashSet[])new HashSet[statesLen];
		ArrayList splitblock[] = (ArrayList[])new ArrayList[statesLen];
		int block[] = new int[statesLen];
		StateList active[][] = new StateList[statesLen][sigmaLen];
		StateListNode active2[][] = new StateListNode[statesLen][sigmaLen];
		LinkedList pending = new LinkedList();
		BitSet pending2 = new BitSet(sigmaLen * statesLen);
		BitSet split = new BitSet(statesLen);
		BitSet refine = new BitSet(statesLen);
		BitSet refine2 = new BitSet(statesLen);
		for (int q = 0; q < statesLen; q++)
		{
			splitblock[q] = new ArrayList();
			partition[q] = new HashSet();
			for (int x = 0; x < sigmaLen; x++)
				active[q][x] = new StateList();

		}

		for (int q = 0; q < statesLen; q++)
		{
			State qq = states[q];
			int j = qq.accept ? 0 : 1;
			partition[j].add(qq);
			block[q] = j;
			for (int x = 0; x < sigmaLen; x++)
			{
				ArrayList r[] = reverse[qq.step(sigma[x]).number];
				if (r[x] == null)
					r[x] = new ArrayList();
				r[x].add(qq);
			}

		}

		for (int j = 0; j <= 1; j++)
		{
label0:
			for (int x = 0; x < sigmaLen; x++)
			{
				Iterator i$ = partition[j].iterator();
				do
				{
					if (!i$.hasNext())
						continue label0;
					State qq = (State)i$.next();
					if (reverse[qq.number][x] != null)
						active2[qq.number][x] = active[j][x].add(qq);
				} while (true);
			}

		}

		for (int x = 0; x < sigmaLen; x++)
		{
			int j = active[0][x].size > active[1][x].size ? 1 : 0;
			pending.add(new IntPair(j, x));
			pending2.set(x * statesLen + j);
		}

		int k = 2;
		for (; !pending.isEmpty(); refine.clear())
		{
			IntPair ip = (IntPair)pending.removeFirst();
			int p = ip.n1;
			int x = ip.n2;
			pending2.clear(x * statesLen + p);
label1:
			for (StateListNode m = active[p][x].first; m != null; m = m.next)
			{
				ArrayList r = reverse[m.q.number][x];
				if (r == null)
					continue;
				Iterator i$ = r.iterator();
				do
				{
					int j;
					do
					{
						State s;
						int i;
						do
						{
							if (!i$.hasNext())
								continue label1;
							s = (State)i$.next();
							i = s.number;
						} while (split.get(i));
						split.set(i);
						j = block[i];
						splitblock[j].add(s);
					} while (refine2.get(j));
					refine2.set(j);
					refine.set(j);
				} while (true);
			}

			for (int j = refine.nextSetBit(0); j >= 0; j = refine.nextSetBit(j + 1))
			{
				ArrayList sb = splitblock[j];
				if (sb.size() < partition[j].size())
				{
					HashSet b1 = partition[j];
					HashSet b2 = partition[k];
					for (Iterator i$ = sb.iterator(); i$.hasNext();)
					{
						State s = (State)i$.next();
						b1.remove(s);
						b2.add(s);
						block[s.number] = k;
						int c = 0;
						while (c < sigmaLen) 
						{
							StateListNode sn = active2[s.number][c];
							if (sn != null && sn.sl == active[j][c])
							{
								sn.remove();
								active2[s.number][c] = active[k][c].add(s);
							}
							c++;
						}
					}

					for (int c = 0; c < sigmaLen; c++)
					{
						int aj = active[j][c].size;
						int ak = active[k][c].size;
						int ofs = c * statesLen;
						if (!pending2.get(ofs + j) && 0 < aj && aj <= ak)
						{
							pending2.set(ofs + j);
							pending.add(new IntPair(j, c));
						} else
						{
							pending2.set(ofs + k);
							pending.add(new IntPair(k, c));
						}
					}

					k++;
				}
				refine2.clear(j);
				State s;
				for (Iterator i$ = sb.iterator(); i$.hasNext(); split.clear(s.number))
					s = (State)i$.next();

				sb.clear();
			}

		}

		State newstates[] = new State[k];
		for (int n = 0; n < newstates.length; n++)
		{
			State s = new State();
			newstates[n] = s;
			for (Iterator i$ = partition[n].iterator(); i$.hasNext();)
			{
				State q = (State)i$.next();
				if (q == a.initial)
					a.initial = s;
				s.accept = q.accept;
				s.number = q.number;
				q.number = n;
			}

		}

		for (int n = 0; n < newstates.length; n++)
		{
			State s = newstates[n];
			s.accept = states[s.number].accept;
			Transition t;
			for (Iterator i$ = states[s.number].getTransitions().iterator(); i$.hasNext(); s.addTransition(new Transition(t.min, t.max, newstates[t.to.number])))
				t = (Transition)i$.next();

		}

		a.clearNumberedStates();
		a.removeDeadTransitions();
	}
}
