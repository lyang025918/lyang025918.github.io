// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SortedIntSet.java

package org.apache.lucene.util.automaton;

import java.util.*;
import org.apache.lucene.util.ArrayUtil;

// Referenced classes of package org.apache.lucene.util.automaton:
//			State

final class SortedIntSet
{
	public static final class FrozenIntSet
	{

		final int values[];
		final int hashCode;
		final State state;

		public int hashCode()
		{
			return hashCode;
		}

		public boolean equals(Object _other)
		{
			if (_other == null)
				return false;
			if (_other instanceof FrozenIntSet)
			{
				FrozenIntSet other = (FrozenIntSet)_other;
				if (hashCode != other.hashCode)
					return false;
				if (other.values.length != values.length)
					return false;
				for (int i = 0; i < values.length; i++)
					if (other.values[i] != values[i])
						return false;

				return true;
			}
			if (_other instanceof SortedIntSet)
			{
				SortedIntSet other = (SortedIntSet)_other;
				if (hashCode != other.hashCode)
					return false;
				if (other.values.length != values.length)
					return false;
				for (int i = 0; i < values.length; i++)
					if (other.values[i] != values[i])
						return false;

				return true;
			} else
			{
				return false;
			}
		}

		public String toString()
		{
			StringBuilder sb = (new StringBuilder()).append('[');
			for (int i = 0; i < values.length; i++)
			{
				if (i > 0)
					sb.append(' ');
				sb.append(values[i]);
			}

			sb.append(']');
			return sb.toString();
		}

		public FrozenIntSet(int values[], int hashCode, State state)
		{
			this.values = values;
			this.hashCode = hashCode;
			this.state = state;
		}

		public FrozenIntSet(int num, State state)
		{
			values = (new int[] {
				num
			});
			this.state = state;
			hashCode = 683 + num;
		}
	}


	int values[];
	int counts[];
	int upto;
	private int hashCode;
	private static final int TREE_MAP_CUTOVER = 30;
	private final Map map = new TreeMap();
	private boolean useTreeMap;
	State state;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/SortedIntSet.desiredAssertionStatus();

	public SortedIntSet(int capacity)
	{
		values = new int[capacity];
		counts = new int[capacity];
	}

	public void incr(int num)
	{
		if (useTreeMap)
		{
			Integer key = Integer.valueOf(num);
			Integer val = (Integer)map.get(key);
			if (val == null)
				map.put(key, Integer.valueOf(1));
			else
				map.put(key, Integer.valueOf(1 + val.intValue()));
			return;
		}
		if (upto == values.length)
		{
			values = ArrayUtil.grow(values, 1 + upto);
			counts = ArrayUtil.grow(counts, 1 + upto);
		}
		for (int i = 0; i < upto; i++)
		{
			if (values[i] == num)
			{
				counts[i]++;
				return;
			}
			if (num < values[i])
			{
				for (int j = upto - 1; j >= i; j--)
				{
					values[1 + j] = values[j];
					counts[1 + j] = counts[j];
				}

				values[i] = num;
				counts[i] = 1;
				upto++;
				return;
			}
		}

		values[upto] = num;
		counts[upto] = 1;
		upto++;
		if (upto == 30)
		{
			useTreeMap = true;
			for (int i = 0; i < upto; i++)
				map.put(Integer.valueOf(values[i]), Integer.valueOf(counts[i]));

		}
	}

	public void decr(int num)
	{
		if (useTreeMap)
		{
			int count = ((Integer)map.get(Integer.valueOf(num))).intValue();
			if (count == 1)
				map.remove(Integer.valueOf(num));
			else
				map.put(Integer.valueOf(num), Integer.valueOf(count - 1));
			if (map.size() == 0)
			{
				useTreeMap = false;
				upto = 0;
			}
			return;
		}
		for (int i = 0; i < upto; i++)
			if (values[i] == num)
			{
				counts[i]--;
				if (counts[i] == 0)
				{
					int limit;
					for (limit = upto - 1; i < limit; i++)
					{
						values[i] = values[i + 1];
						counts[i] = counts[i + 1];
					}

					upto = limit;
				}
				return;
			}

		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public void computeHash()
	{
		if (useTreeMap)
		{
			if (map.size() > values.length)
			{
				int size = ArrayUtil.oversize(map.size(), 4);
				values = new int[size];
				counts = new int[size];
			}
			hashCode = map.size();
			upto = 0;
			for (Iterator i$ = map.keySet().iterator(); i$.hasNext();)
			{
				int state = ((Integer)i$.next()).intValue();
				hashCode = 683 * hashCode + state;
				values[upto++] = state;
			}

		} else
		{
			hashCode = upto;
			for (int i = 0; i < upto; i++)
				hashCode = 683 * hashCode + values[i];

		}
	}

	public FrozenIntSet freeze(State state)
	{
		int c[] = new int[upto];
		System.arraycopy(values, 0, c, 0, upto);
		return new FrozenIntSet(c, hashCode, state);
	}

	public int hashCode()
	{
		return hashCode;
	}

	public boolean equals(Object _other)
	{
		if (_other == null)
			return false;
		if (!(_other instanceof FrozenIntSet))
			return false;
		FrozenIntSet other = (FrozenIntSet)_other;
		if (hashCode != other.hashCode)
			return false;
		if (other.values.length != upto)
			return false;
		for (int i = 0; i < upto; i++)
			if (other.values[i] != values[i])
				return false;

		return true;
	}

	public String toString()
	{
		StringBuilder sb = (new StringBuilder()).append('[');
		for (int i = 0; i < upto; i++)
		{
			if (i > 0)
				sb.append(' ');
			sb.append(values[i]).append(':').append(counts[i]);
		}

		sb.append(']');
		return sb.toString();
	}


}
