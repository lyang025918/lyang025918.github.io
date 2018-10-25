// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RunAutomaton.java

package org.apache.lucene.util.automaton;


// Referenced classes of package org.apache.lucene.util.automaton:
//			Automaton, State, Transition, SpecialOperations

public abstract class RunAutomaton
{

	final int maxInterval;
	final int size;
	final boolean accept[];
	final int initial;
	final int transitions[];
	final int points[];
	final int classmap[];

	public String toString()
	{
		StringBuilder b = new StringBuilder();
		b.append("initial state: ").append(initial).append("\n");
		for (int i = 0; i < size; i++)
		{
			b.append((new StringBuilder()).append("state ").append(i).toString());
			if (accept[i])
				b.append(" [accept]:\n");
			else
				b.append(" [reject]:\n");
			for (int j = 0; j < points.length; j++)
			{
				int k = transitions[i * points.length + j];
				if (k == -1)
					continue;
				int min = points[j];
				int max;
				if (j + 1 < points.length)
					max = points[j + 1] - 1;
				else
					max = maxInterval;
				b.append(" ");
				Transition.appendCharString(min, b);
				if (min != max)
				{
					b.append("-");
					Transition.appendCharString(max, b);
				}
				b.append(" -> ").append(k).append("\n");
			}

		}

		return b.toString();
	}

	public final int getSize()
	{
		return size;
	}

	public final boolean isAccept(int state)
	{
		return accept[state];
	}

	public final int getInitialState()
	{
		return initial;
	}

	public final int[] getCharIntervals()
	{
		return (int[])points.clone();
	}

	final int getCharClass(int c)
	{
		return SpecialOperations.findIndex(c, points);
	}

	public RunAutomaton(Automaton a, int maxInterval, boolean tableize)
	{
		this.maxInterval = maxInterval;
		a.determinize();
		points = a.getStartPoints();
		State states[] = a.getNumberedStates();
		initial = a.initial.number;
		size = states.length;
		accept = new boolean[size];
		transitions = new int[size * points.length];
		for (int n = 0; n < size * points.length; n++)
			transitions[n] = -1;

		State arr$[] = states;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			State s = arr$[i$];
			int n = s.number;
			accept[n] = s.accept;
			for (int c = 0; c < points.length; c++)
			{
				State q = s.step(points[c]);
				if (q != null)
					transitions[n * points.length + c] = q.number;
			}

		}

		if (tableize)
		{
			classmap = new int[maxInterval + 1];
			int i = 0;
			for (int j = 0; j <= maxInterval; j++)
			{
				if (i + 1 < points.length && j == points[i + 1])
					i++;
				classmap[j] = i;
			}

		} else
		{
			classmap = null;
		}
	}

	public final int step(int state, int c)
	{
		if (classmap == null)
			return transitions[state * points.length + getCharClass(c)];
		else
			return transitions[state * points.length + classmap[c]];
	}
}
