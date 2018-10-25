// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanFilter.java

package org.apache.lucene.queries;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.FixedBitSet;

// Referenced classes of package org.apache.lucene.queries:
//			FilterClause

public class BooleanFilter extends Filter
	implements Iterable
{

	private final List clauses = new ArrayList();
	static final boolean $assertionsDisabled = !org/apache/lucene/queries/BooleanFilter.desiredAssertionStatus();

	public BooleanFilter()
	{
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		FixedBitSet res = null;
		AtomicReader reader = context.reader();
		boolean hasShouldClauses = false;
		Iterator i$ = clauses.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FilterClause fc = (FilterClause)i$.next();
			if (fc.getOccur() == org.apache.lucene.search.BooleanClause.Occur.SHOULD)
			{
				hasShouldClauses = true;
				DocIdSetIterator disi = getDISI(fc.getFilter(), context);
				if (disi != null)
				{
					if (res == null)
						res = new FixedBitSet(reader.maxDoc());
					res.or(disi);
				}
			}
		} while (true);
		if (hasShouldClauses && res == null)
			return DocIdSet.EMPTY_DOCIDSET;
		i$ = clauses.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FilterClause fc = (FilterClause)i$.next();
			if (fc.getOccur() == org.apache.lucene.search.BooleanClause.Occur.MUST_NOT)
			{
				if (res == null)
				{
					if (!$assertionsDisabled && hasShouldClauses)
						throw new AssertionError();
					res = new FixedBitSet(reader.maxDoc());
					res.set(0, reader.maxDoc());
				}
				DocIdSetIterator disi = getDISI(fc.getFilter(), context);
				if (disi != null)
					res.andNot(disi);
			}
		} while (true);
		i$ = clauses.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FilterClause fc = (FilterClause)i$.next();
			if (fc.getOccur() == org.apache.lucene.search.BooleanClause.Occur.MUST)
			{
				DocIdSetIterator disi = getDISI(fc.getFilter(), context);
				if (disi == null)
					return DocIdSet.EMPTY_DOCIDSET;
				if (res == null)
				{
					res = new FixedBitSet(reader.maxDoc());
					res.or(disi);
				} else
				{
					res.and(disi);
				}
			}
		} while (true);
		return res == null ? DocIdSet.EMPTY_DOCIDSET : BitsFilteredDocIdSet.wrap(res, acceptDocs);
	}

	private static DocIdSetIterator getDISI(Filter filter, AtomicReaderContext context)
		throws IOException
	{
		DocIdSet set = filter.getDocIdSet(context, null);
		return set != null && set != DocIdSet.EMPTY_DOCIDSET ? set.iterator() : null;
	}

	public void add(FilterClause filterClause)
	{
		clauses.add(filterClause);
	}

	public final void add(Filter filter, org.apache.lucene.search.BooleanClause.Occur occur)
	{
		add(new FilterClause(filter, occur));
	}

	public List clauses()
	{
		return clauses;
	}

	public final Iterator iterator()
	{
		return clauses().iterator();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
		{
			return false;
		} else
		{
			BooleanFilter other = (BooleanFilter)obj;
			return clauses.equals(other.clauses);
		}
	}

	public int hashCode()
	{
		return 0x272b5eb6 ^ clauses.hashCode();
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder("BooleanFilter(");
		int minLen = buffer.length();
		FilterClause c;
		for (Iterator i$ = clauses.iterator(); i$.hasNext(); buffer.append(c))
		{
			c = (FilterClause)i$.next();
			if (buffer.length() > minLen)
				buffer.append(' ');
		}

		return buffer.append(')').toString();
	}

}
