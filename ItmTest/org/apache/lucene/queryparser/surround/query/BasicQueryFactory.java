// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicQueryFactory.java

package org.apache.lucene.queryparser.surround.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.spans.SpanTermQuery;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			TooManyBasicQueries

public class BasicQueryFactory
{

	private int maxBasicQueries;
	private int queriesMade;

	public BasicQueryFactory(int maxBasicQueries)
	{
		this.maxBasicQueries = maxBasicQueries;
		queriesMade = 0;
	}

	public BasicQueryFactory()
	{
		this(1024);
	}

	public int getNrQueriesMade()
	{
		return queriesMade;
	}

	public int getMaxBasicQueries()
	{
		return maxBasicQueries;
	}

	public String toString()
	{
		return (new StringBuilder()).append(getClass().getName()).append("(maxBasicQueries: ").append(maxBasicQueries).append(", queriesMade: ").append(queriesMade).append(")").toString();
	}

	private boolean atMax()
	{
		return queriesMade >= maxBasicQueries;
	}

	protected synchronized void checkMax()
		throws TooManyBasicQueries
	{
		if (atMax())
		{
			throw new TooManyBasicQueries(getMaxBasicQueries());
		} else
		{
			queriesMade++;
			return;
		}
	}

	public TermQuery newTermQuery(Term term)
		throws TooManyBasicQueries
	{
		checkMax();
		return new TermQuery(term);
	}

	public SpanTermQuery newSpanTermQuery(Term term)
		throws TooManyBasicQueries
	{
		checkMax();
		return new SpanTermQuery(term);
	}

	public int hashCode()
	{
		return getClass().hashCode() ^ (atMax() ? '\007' : 0x3e0);
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof BasicQueryFactory))
		{
			return false;
		} else
		{
			BasicQueryFactory other = (BasicQueryFactory)obj;
			return atMax() == other.atMax();
		}
	}
}
