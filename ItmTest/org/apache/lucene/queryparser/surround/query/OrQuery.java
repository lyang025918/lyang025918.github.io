// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			ComposedQuery, SrndQuery, DistanceSubQuery, SrndBooleanQuery, 
//			BasicQueryFactory, SpanNearClauseFactory

public class OrQuery extends ComposedQuery
	implements DistanceSubQuery
{

	public OrQuery(List queries, boolean infix, String opName)
	{
		super(queries, infix, opName);
	}

	public Query makeLuceneQueryFieldNoBoost(String fieldName, BasicQueryFactory qf)
	{
		return SrndBooleanQuery.makeBooleanQuery(makeLuceneSubQueriesField(fieldName, qf), org.apache.lucene.search.BooleanClause.Occur.SHOULD);
	}

	public String distanceSubQueryNotAllowed()
	{
		for (Iterator sqi = getSubQueriesIterator(); sqi.hasNext();)
		{
			SrndQuery leq = (SrndQuery)sqi.next();
			if (leq instanceof DistanceSubQuery)
			{
				String m = ((DistanceSubQuery)leq).distanceSubQueryNotAllowed();
				if (m != null)
					return m;
			} else
			{
				return (new StringBuilder()).append("subquery not allowed: ").append(leq.toString()).toString();
			}
		}

		return null;
	}

	public void addSpanQueries(SpanNearClauseFactory sncf)
		throws IOException
	{
		for (Iterator sqi = getSubQueriesIterator(); sqi.hasNext(); ((DistanceSubQuery)sqi.next()).addSpanQueries(sncf));
	}
}
