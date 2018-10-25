// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DistanceRewriteQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			RewriteQuery, DistanceQuery, BasicQueryFactory

class DistanceRewriteQuery extends RewriteQuery
{

	DistanceRewriteQuery(DistanceQuery srndQuery, String fieldName, BasicQueryFactory qf)
	{
		super(srndQuery, fieldName, qf);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		return ((DistanceQuery)srndQuery).getSpanNearQuery(reader, fieldName, getBoost(), qf);
	}
}
