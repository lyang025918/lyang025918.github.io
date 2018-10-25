// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Transition.java

package org.apache.lucene.util.automaton;

import java.util.Comparator;

// Referenced classes of package org.apache.lucene.util.automaton:
//			State

public class Transition
	implements Cloneable
{
	private static final class CompareByMinMaxThenDestSingle
		implements Comparator
	{

		public int compare(Transition t1, Transition t2)
		{
			if (t1.min < t2.min)
				return -1;
			if (t1.min > t2.min)
				return 1;
			if (t1.max > t2.max)
				return -1;
			if (t1.max < t2.max)
				return 1;
			if (t1.to != t2.to)
			{
				if (t1.to.number < t2.to.number)
					return -1;
				if (t1.to.number > t2.to.number)
					return 1;
			}
			return 0;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((Transition)x0, (Transition)x1);
		}

		private CompareByMinMaxThenDestSingle()
		{
		}

	}

	private static final class CompareByDestThenMinMaxSingle
		implements Comparator
	{

		public int compare(Transition t1, Transition t2)
		{
			if (t1.to != t2.to)
			{
				if (t1.to.number < t2.to.number)
					return -1;
				if (t1.to.number > t2.to.number)
					return 1;
			}
			if (t1.min < t2.min)
				return -1;
			if (t1.min > t2.min)
				return 1;
			if (t1.max > t2.max)
				return -1;
			return t1.max >= t2.max ? 0 : 1;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((Transition)x0, (Transition)x1);
		}

		private CompareByDestThenMinMaxSingle()
		{
		}

	}


	final int min;
	final int max;
	final State to;
	public static final Comparator CompareByDestThenMinMax = new CompareByDestThenMinMaxSingle();
	public static final Comparator CompareByMinMaxThenDest = new CompareByMinMaxThenDestSingle();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/Transition.desiredAssertionStatus();

	public Transition(int c, State to)
	{
		if (!$assertionsDisabled && c < 0)
		{
			throw new AssertionError();
		} else
		{
			min = max = c;
			this.to = to;
			return;
		}
	}

	public Transition(int min, int max, State to)
	{
		if (!$assertionsDisabled && min < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && max < 0)
			throw new AssertionError();
		if (max < min)
		{
			int t = max;
			max = min;
			min = t;
		}
		this.min = min;
		this.max = max;
		this.to = to;
	}

	public int getMin()
	{
		return min;
	}

	public int getMax()
	{
		return max;
	}

	public State getDest()
	{
		return to;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof Transition)
		{
			Transition t = (Transition)obj;
			return t.min == min && t.max == max && t.to == to;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return min * 2 + max * 3;
	}

	public Transition clone()
	{
		return (Transition)super.clone();
		CloneNotSupportedException e;
		e;
		throw new RuntimeException(e);
	}

	static void appendCharString(int c, StringBuilder b)
	{
		if (c >= 33 && c <= 126 && c != 92 && c != 34)
		{
			b.appendCodePoint(c);
		} else
		{
			b.append("\\\\U");
			String s = Integer.toHexString(c);
			if (c < 16)
				b.append("0000000").append(s);
			else
			if (c < 256)
				b.append("000000").append(s);
			else
			if (c < 4096)
				b.append("00000").append(s);
			else
			if (c < 0x10000)
				b.append("0000").append(s);
			else
			if (c < 0x100000)
				b.append("000").append(s);
			else
			if (c < 0x1000000)
				b.append("00").append(s);
			else
			if (c < 0x10000000)
				b.append("0").append(s);
			else
				b.append(s);
		}
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder();
		appendCharString(min, b);
		if (min != max)
		{
			b.append("-");
			appendCharString(max, b);
		}
		b.append(" -> ").append(to.number);
		return b.toString();
	}

	void appendDot(StringBuilder b)
	{
		b.append(" -> ").append(to.number).append(" [label=\"");
		appendCharString(min, b);
		if (min != max)
		{
			b.append("-");
			appendCharString(max, b);
		}
		b.append("\"]\n");
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
