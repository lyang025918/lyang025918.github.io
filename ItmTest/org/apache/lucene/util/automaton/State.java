// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   State.java

package org.apache.lucene.util.automaton;

import java.util.*;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Transition

public class State
	implements Comparable
{
	private class TransitionsIterable
		implements Iterable
	{

		final State this$0;

		public Iterator iterator()
		{
			return new Iterator() {

				int upto;
				final TransitionsIterable this$1;

				public boolean hasNext()
				{
					return upto < numTransitions;
				}

				public Transition next()
				{
					return transitionsArray[upto++];
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				public volatile Object next()
				{
					return next();
				}

				
				{
					this$1 = TransitionsIterable.this;
					super();
				}
			};
		}

		private TransitionsIterable()
		{
			this$0 = State.this;
			super();
		}

	}


	boolean accept;
	public Transition transitionsArray[];
	public int numTransitions;
	int number;
	int id;
	static int next_id;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/State.desiredAssertionStatus();

	public State()
	{
		resetTransitions();
		id = next_id++;
	}

	final void resetTransitions()
	{
		transitionsArray = new Transition[0];
		numTransitions = 0;
	}

	public Iterable getTransitions()
	{
		return new TransitionsIterable();
	}

	public int numTransitions()
	{
		return numTransitions;
	}

	public void setTransitions(Transition transitions[])
	{
		numTransitions = transitions.length;
		transitionsArray = transitions;
	}

	public void addTransition(Transition t)
	{
		if (numTransitions == transitionsArray.length)
		{
			Transition newArray[] = new Transition[ArrayUtil.oversize(1 + numTransitions, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			System.arraycopy(transitionsArray, 0, newArray, 0, numTransitions);
			transitionsArray = newArray;
		}
		transitionsArray[numTransitions++] = t;
	}

	public void setAccept(boolean accept)
	{
		this.accept = accept;
	}

	public boolean isAccept()
	{
		return accept;
	}

	public State step(int c)
	{
		if (!$assertionsDisabled && c < 0)
			throw new AssertionError();
		for (int i = 0; i < numTransitions; i++)
		{
			Transition t = transitionsArray[i];
			if (t.min <= c && c <= t.max)
				return t.to;
		}

		return null;
	}

	public void step(int c, Collection dest)
	{
		for (int i = 0; i < numTransitions; i++)
		{
			Transition t = transitionsArray[i];
			if (t.min <= c && c <= t.max)
				dest.add(t.to);
		}

	}

	void addEpsilon(State to)
	{
		if (to.accept)
			accept = true;
		Transition t;
		for (Iterator i$ = to.getTransitions().iterator(); i$.hasNext(); addTransition(t))
			t = (Transition)i$.next();

	}

	public void trimTransitionsArray()
	{
		if (numTransitions < transitionsArray.length)
		{
			Transition newArray[] = new Transition[numTransitions];
			System.arraycopy(transitionsArray, 0, newArray, 0, numTransitions);
			transitionsArray = newArray;
		}
	}

	public void reduce()
	{
		if (numTransitions <= 1)
			return;
		sortTransitions(Transition.CompareByDestThenMinMax);
		State p = null;
		int min = -1;
		int max = -1;
		int upto = 0;
		for (int i = 0; i < numTransitions; i++)
		{
			Transition t = transitionsArray[i];
			if (p == t.to)
			{
				if (t.min <= max + 1)
				{
					if (t.max > max)
						max = t.max;
					continue;
				}
				if (p != null)
					transitionsArray[upto++] = new Transition(min, max, p);
				min = t.min;
				max = t.max;
				continue;
			}
			if (p != null)
				transitionsArray[upto++] = new Transition(min, max, p);
			p = t.to;
			min = t.min;
			max = t.max;
		}

		if (p != null)
			transitionsArray[upto++] = new Transition(min, max, p);
		numTransitions = upto;
	}

	public void sortTransitions(Comparator comparator)
	{
		if (numTransitions > 1)
			ArrayUtil.mergeSort(transitionsArray, 0, numTransitions, comparator);
	}

	public int getNumber()
	{
		return number;
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder();
		b.append("state ").append(number);
		if (accept)
			b.append(" [accept]");
		else
			b.append(" [reject]");
		b.append(":\n");
		Transition t;
		for (Iterator i$ = getTransitions().iterator(); i$.hasNext(); b.append("  ").append(t.toString()).append("\n"))
			t = (Transition)i$.next();

		return b.toString();
	}

	public int compareTo(State s)
	{
		return s.id - id;
	}

	public int hashCode()
	{
		return id;
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((State)x0);
	}

}
