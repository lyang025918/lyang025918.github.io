// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharArraySet.java

package org.apache.lucene.analysis.util;

import java.util.*;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharArrayMap

public class CharArraySet extends AbstractSet
{

	public static final CharArraySet EMPTY_SET = new CharArraySet(CharArrayMap.emptyMap());
	private static final Object PLACEHOLDER = new Object();
	private final CharArrayMap map;

	public CharArraySet(Version matchVersion, int startSize, boolean ignoreCase)
	{
		this(new CharArrayMap(matchVersion, startSize, ignoreCase));
	}

	public CharArraySet(Version matchVersion, Collection c, boolean ignoreCase)
	{
		this(matchVersion, c.size(), ignoreCase);
		addAll(c);
	}

	CharArraySet(CharArrayMap map)
	{
		this.map = map;
	}

	public void clear()
	{
		map.clear();
	}

	public boolean contains(char text[], int off, int len)
	{
		return map.containsKey(text, off, len);
	}

	public boolean contains(CharSequence cs)
	{
		return map.containsKey(cs);
	}

	public boolean contains(Object o)
	{
		return map.containsKey(o);
	}

	public boolean add(Object o)
	{
		return map.put(o, PLACEHOLDER) == null;
	}

	public boolean add(CharSequence text)
	{
		return map.put(text, PLACEHOLDER) == null;
	}

	public boolean add(String text)
	{
		return map.put(text, PLACEHOLDER) == null;
	}

	public boolean add(char text[])
	{
		return map.put(text, PLACEHOLDER) == null;
	}

	public int size()
	{
		return map.size();
	}

	public static CharArraySet unmodifiableSet(CharArraySet set)
	{
		if (set == null)
			throw new NullPointerException("Given set is null");
		if (set == EMPTY_SET)
			return EMPTY_SET;
		if (set.map instanceof CharArrayMap.UnmodifiableCharArrayMap)
			return set;
		else
			return new CharArraySet(CharArrayMap.unmodifiableMap(set.map));
	}

	public static CharArraySet copy(Version matchVersion, Set set)
	{
		if (set == EMPTY_SET)
			return EMPTY_SET;
		if (set instanceof CharArraySet)
		{
			CharArraySet source = (CharArraySet)set;
			return new CharArraySet(CharArrayMap.copy(source.map.matchVersion, source.map));
		} else
		{
			return new CharArraySet(matchVersion, set, false);
		}
	}

	public Iterator iterator()
	{
		return map.originalKeySet().iterator();
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder("[");
		for (Iterator i$ = iterator(); i$.hasNext();)
		{
			Object item = i$.next();
			if (sb.length() > 1)
				sb.append(", ");
			if (item instanceof char[])
				sb.append((char[])(char[])item);
			else
				sb.append(item);
		}

		return sb.append(']').toString();
	}

}
