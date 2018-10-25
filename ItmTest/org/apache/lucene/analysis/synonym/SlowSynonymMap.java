// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlowSynonymMap.java

package org.apache.lucene.analysis.synonym;

import java.util.*;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.Version;

/**
 * @deprecated Class SlowSynonymMap is deprecated
 */

class SlowSynonymMap
{

	public CharArrayMap submap;
	public Token synonyms[];
	int flags;
	static final int INCLUDE_ORIG = 1;
	static final int IGNORE_CASE = 2;

	public SlowSynonymMap()
	{
	}

	public SlowSynonymMap(boolean ignoreCase)
	{
		if (ignoreCase)
			flags |= 2;
	}

	public boolean includeOrig()
	{
		return (flags & 1) != 0;
	}

	public boolean ignoreCase()
	{
		return (flags & 2) != 0;
	}

	public void add(List singleMatch, List replacement, boolean includeOrig, boolean mergeExisting)
	{
		SlowSynonymMap currMap = this;
		for (Iterator i$ = singleMatch.iterator(); i$.hasNext();)
		{
			String str = (String)i$.next();
			if (currMap.submap == null)
				currMap.submap = new CharArrayMap(Version.LUCENE_40, 1, ignoreCase());
			SlowSynonymMap map = (SlowSynonymMap)currMap.submap.get(str);
			if (map == null)
			{
				map = new SlowSynonymMap();
				map.flags |= flags & 2;
				currMap.submap.put(str, map);
			}
			currMap = map;
		}

		if (currMap.synonyms != null && !mergeExisting)
			throw new IllegalArgumentException((new StringBuilder()).append("SynonymFilter: there is already a mapping for ").append(singleMatch).toString());
		List superset = currMap.synonyms != null ? mergeTokens(Arrays.asList(currMap.synonyms), replacement) : replacement;
		currMap.synonyms = (Token[])superset.toArray(new Token[superset.size()]);
		if (includeOrig)
			currMap.flags |= 1;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder("<");
		if (synonyms != null)
		{
			sb.append("[");
			for (int i = 0; i < synonyms.length; i++)
			{
				if (i != 0)
					sb.append(',');
				sb.append(synonyms[i]);
			}

			if ((flags & 1) != 0)
				sb.append(",ORIG");
			sb.append("],");
		}
		sb.append(submap);
		sb.append(">");
		return sb.toString();
	}

	public static List makeTokens(List strings)
	{
		List ret = new ArrayList(strings.size());
		Token newTok;
		for (Iterator i$ = strings.iterator(); i$.hasNext(); ret.add(newTok))
		{
			String str = (String)i$.next();
			newTok = new Token(str, 0, 0, "SYNONYM");
		}

		return ret;
	}

	public static List mergeTokens(List lst1, List lst2)
	{
		ArrayList result = new ArrayList();
		if (lst1 == null || lst2 == null)
		{
			if (lst2 != null)
				result.addAll(lst2);
			if (lst1 != null)
				result.addAll(lst1);
			return result;
		}
		int pos = 0;
		Iterator iter1 = lst1.iterator();
		Iterator iter2 = lst2.iterator();
		Token tok1 = iter1.hasNext() ? (Token)iter1.next() : null;
		Token tok2 = iter2.hasNext() ? (Token)iter2.next() : null;
		int pos1 = tok1 == null ? 0 : tok1.getPositionIncrement();
		int pos2 = tok2 == null ? 0 : tok2.getPositionIncrement();
		while (tok1 != null || tok2 != null) 
		{
			for (; tok1 != null && (pos1 <= pos2 || tok2 == null); pos1 += tok1 == null ? 0 : tok1.getPositionIncrement())
			{
				Token tok = new Token(tok1.startOffset(), tok1.endOffset(), tok1.type());
				tok.copyBuffer(tok1.buffer(), 0, tok1.length());
				tok.setPositionIncrement(pos1 - pos);
				result.add(tok);
				pos = pos1;
				tok1 = iter1.hasNext() ? (Token)iter1.next() : null;
			}

			while (tok2 != null && (pos2 <= pos1 || tok1 == null)) 
			{
				Token tok = new Token(tok2.startOffset(), tok2.endOffset(), tok2.type());
				tok.copyBuffer(tok2.buffer(), 0, tok2.length());
				tok.setPositionIncrement(pos2 - pos);
				result.add(tok);
				pos = pos2;
				tok2 = iter2.hasNext() ? (Token)iter2.next() : null;
				pos2 += tok2 == null ? 0 : tok2.getPositionIncrement();
			}
		}
		return result;
	}
}
