// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SentinelIntSet.java

package org.apache.lucene.util;

import java.util.Arrays;

// Referenced classes of package org.apache.lucene.util:
//			BitUtil

public class SentinelIntSet
{

	public int keys[];
	public int count;
	public final int emptyVal;
	public int rehashCount;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/SentinelIntSet.desiredAssertionStatus();

	public SentinelIntSet(int size, int emptyVal)
	{
		this.emptyVal = emptyVal;
		int tsize = Math.max(BitUtil.nextHighestPowerOfTwo(size), 1);
		rehashCount = tsize - (tsize >> 2);
		if (size >= rehashCount)
		{
			tsize <<= 1;
			rehashCount = tsize - (tsize >> 2);
		}
		keys = new int[tsize];
		if (emptyVal != 0)
			clear();
	}

	public void clear()
	{
		Arrays.fill(keys, emptyVal);
		count = 0;
	}

	public int hash(int key)
	{
		return key;
	}

	public int size()
	{
		return count;
	}

	public int getSlot(int key)
	{
		if (!$assertionsDisabled && key == emptyVal)
			throw new AssertionError();
		int h = hash(key);
		int s = h & keys.length - 1;
		if (keys[s] == key || keys[s] == emptyVal)
			return s;
		int increment = h >> 7 | 1;
		do
			s = s + increment & keys.length - 1;
		while (keys[s] != key && keys[s] != emptyVal);
		return s;
	}

	public int find(int key)
	{
		if (!$assertionsDisabled && key == emptyVal)
			throw new AssertionError();
		int h = hash(key);
		int s = h & keys.length - 1;
		if (keys[s] == key)
			return s;
		if (keys[s] == emptyVal)
			return -s - 1;
		int increment = h >> 7 | 1;
		do
		{
			s = s + increment & keys.length - 1;
			if (keys[s] == key)
				return s;
		} while (keys[s] != emptyVal);
		return -s - 1;
	}

	public boolean exists(int key)
	{
		return find(key) >= 0;
	}

	public int put(int key)
	{
		int s = find(key);
		if (s < 0)
		{
			count++;
			if (count >= rehashCount)
			{
				rehash();
				s = getSlot(key);
			} else
			{
				s = -s - 1;
			}
			keys[s] = key;
		}
		return s;
	}

	public void rehash()
	{
		int newSize = keys.length << 1;
		int oldKeys[] = keys;
		keys = new int[newSize];
		if (emptyVal != 0)
			Arrays.fill(keys, emptyVal);
		for (int i = 0; i < oldKeys.length; i++)
		{
			int key = oldKeys[i];
			if (key != emptyVal)
			{
				int newSlot = getSlot(key);
				keys[newSlot] = key;
			}
		}

		rehashCount = newSize - (newSize >> 2);
	}

}
