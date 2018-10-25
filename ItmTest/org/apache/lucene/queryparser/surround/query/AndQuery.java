// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AndQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.util.List;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			ComposedQuery, SrndBooleanQuery, BasicQueryFactory

public class AndQuery extends ComposedQuery
{

	public AndQuery(List queries, boolean inf, String opName)
	{
		super(queries, inf, opName);
	}

	public Query makeLuceneQueryFieldNoBoost(String fieldName, BasicQueryFactory qf)
	{
		return SrndBooleanQuery.makeBooleanQuery(makeLuceneSubQueriesField(fieldName, qf), org.apache.lucene.search.BooleanClause.Occur.MUST);
	}
}
