// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanNearClauseFactory.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spans.SpanOrQuery;
import org.apache.lucene.search.spans.SpanQuery;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			BasicQueryFactory, SrndQuery

public class SpanNearClauseFactory
{

	private IndexReader reader;
	private String fieldName;
	private HashMap weightBySpanQuery;
	private BasicQueryFactory qf;

	public SpanNearClauseFactory(IndexReader reader, String fieldName, BasicQueryFactory qf)
	{
		this.reader = reader;
		this.fieldName = fieldName;
		weightBySpanQuery = new HashMap();
		this.qf = qf;
	}

	public IndexReader getIndexReader()
	{
		return reader;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public BasicQueryFactory getBasicQueryFactory()
	{
		return qf;
	}

	public int size()
	{
		return weightBySpanQuery.size();
	}

	public void clear()
	{
		weightBySpanQuery.clear();
	}

	protected void addSpanQueryWeighted(SpanQuery sq, float weight)
	{
		Float w = (Float)weightBySpanQuery.get(sq);
		if (w != null)
			w = Float.valueOf(w.floatValue() + weight);
		else
			w = Float.valueOf(weight);
		weightBySpanQuery.put(sq, w);
	}

	public void addTermWeighted(Term t, float weight)
		throws IOException
	{
		org.apache.lucene.search.spans.SpanTermQuery stq = qf.newSpanTermQuery(t);
		addSpanQueryWeighted(stq, weight);
	}

	public void addSpanQuery(Query q)
	{
		if (q == SrndQuery.theEmptyLcnQuery)
			return;
		if (!(q instanceof SpanQuery))
		{
			throw new AssertionError((new StringBuilder()).append("Expected SpanQuery: ").append(q.toString(getFieldName())).toString());
		} else
		{
			addSpanQueryWeighted((SpanQuery)q, q.getBoost());
			return;
		}
	}

	public SpanQuery makeSpanClause()
	{
		SpanQuery spanQueries[] = new SpanQuery[size()];
		Iterator sqi = weightBySpanQuery.keySet().iterator();
		int i = 0;
		while (sqi.hasNext()) 
		{
			SpanQuery sq = (SpanQuery)sqi.next();
			sq.setBoost(((Float)weightBySpanQuery.get(sq)).floatValue());
			spanQueries[i++] = sq;
		}
		if (spanQueries.length == 1)
			return spanQueries[0];
		else
			return new SpanOrQuery(spanQueries);
	}
}
