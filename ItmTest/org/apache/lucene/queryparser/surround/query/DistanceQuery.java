// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DistanceQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			ComposedQuery, DistanceSubQuery, SpanNearClauseFactory, DistanceRewriteQuery, 
//			SrndQuery, BasicQueryFactory

public class DistanceQuery extends ComposedQuery
	implements DistanceSubQuery
{

	private int opDistance;
	private boolean ordered;

	public DistanceQuery(List queries, boolean infix, int opDistance, String opName, boolean ordered)
	{
		super(queries, infix, opName);
		this.opDistance = opDistance;
		this.ordered = ordered;
	}

	public int getOpDistance()
	{
		return opDistance;
	}

	public boolean subQueriesOrdered()
	{
		return ordered;
	}

	public String distanceSubQueryNotAllowed()
	{
		for (Iterator sqi = getSubQueriesIterator(); sqi.hasNext();)
		{
			Object leq = sqi.next();
			if (leq instanceof DistanceSubQuery)
			{
				DistanceSubQuery dsq = (DistanceSubQuery)leq;
				String m = dsq.distanceSubQueryNotAllowed();
				if (m != null)
					return m;
			} else
			{
				return (new StringBuilder()).append("Operator ").append(getOperatorName()).append(" does not allow subquery ").append(leq.toString()).toString();
			}
		}

		return null;
	}

	public void addSpanQueries(SpanNearClauseFactory sncf)
		throws IOException
	{
		Query snq = getSpanNearQuery(sncf.getIndexReader(), sncf.getFieldName(), getWeight(), sncf.getBasicQueryFactory());
		sncf.addSpanQuery(snq);
	}

	public Query getSpanNearQuery(IndexReader reader, String fieldName, float boost, BasicQueryFactory qf)
		throws IOException
	{
		SpanQuery spanClauses[] = new SpanQuery[getNrSubQueries()];
		Iterator sqi = getSubQueriesIterator();
		for (int qi = 0; sqi.hasNext(); qi++)
		{
			SpanNearClauseFactory sncf = new SpanNearClauseFactory(reader, fieldName, qf);
			((DistanceSubQuery)sqi.next()).addSpanQueries(sncf);
			if (sncf.size() == 0)
			{
				for (; sqi.hasNext(); sncf.clear())
					((DistanceSubQuery)sqi.next()).addSpanQueries(sncf);

				return SrndQuery.theEmptyLcnQuery;
			}
			spanClauses[qi] = sncf.makeSpanClause();
		}

		SpanNearQuery r = new SpanNearQuery(spanClauses, getOpDistance() - 1, subQueriesOrdered());
		r.setBoost(boost);
		return r;
	}

	public Query makeLuceneQueryFieldNoBoost(String fieldName, BasicQueryFactory qf)
	{
		return new DistanceRewriteQuery(this, fieldName, qf);
	}
}
