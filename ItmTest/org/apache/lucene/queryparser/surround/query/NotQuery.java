// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NotQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.util.List;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			ComposedQuery, SrndBooleanQuery, BasicQueryFactory

public class NotQuery extends ComposedQuery
{

	public NotQuery(List queries, String opName)
	{
		super(queries, true, opName);
	}

	public Query makeLuceneQueryFieldNoBoost(String fieldName, BasicQueryFactory qf)
	{
		List luceneSubQueries = makeLuceneSubQueriesField(fieldName, qf);
		BooleanQuery bq = new BooleanQuery();
		bq.add((Query)luceneSubQueries.get(0), org.apache.lucene.search.BooleanClause.Occur.MUST);
		SrndBooleanQuery.addQueriesToBoolean(bq, luceneSubQueries.subList(1, luceneSubQueries.size()), org.apache.lucene.search.BooleanClause.Occur.MUST_NOT);
		return bq;
	}
}
