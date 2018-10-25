// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MapOfSets.java

package org.apache.lucene.util;

import java.util.*;

public class MapOfSets
{

	private final Map theMap;

	public MapOfSets(Map m)
	{
		theMap = m;
	}

	public Map getMap()
	{
		return theMap;
	}

	public int put(Object key, Object val)
	{
		Set theSet;
		if (theMap.containsKey(key))
		{
			theSet = (Set)theMap.get(key);
		} else
		{
			theSet = new HashSet(23);
			theMap.put(key, theSet);
		}
		theSet.add(val);
		return theSet.size();
	}

	public int putAll(Object key, Collection vals)
	{
		Set theSet;
		if (theMap.containsKey(key))
		{
			theSet = (Set)theMap.get(key);
		} else
		{
			theSet = new HashSet(23);
			theMap.put(key, theSet);
		}
		theSet.addAll(vals);
		return theSet.size();
	}
}
