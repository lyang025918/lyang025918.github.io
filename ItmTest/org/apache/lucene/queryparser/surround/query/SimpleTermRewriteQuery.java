// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleTermRewriteQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			RewriteQuery, SimpleTerm, SrndQuery, SrndBooleanQuery, 
//			BasicQueryFactory

class SimpleTermRewriteQuery extends RewriteQuery
{

	SimpleTermRewriteQuery(SimpleTerm srndQuery, String fieldName, BasicQueryFactory qf)
	{
		super(srndQuery, fieldName, qf);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		final List luceneSubQueries = new ArrayList();
		((SimpleTerm)srndQuery).visitMatchingTerms(reader, fieldName, new SimpleTerm.MatchingTermVisitor() {

			final List val$luceneSubQueries;
			final SimpleTermRewriteQuery this$0;

			public void visitMatchingTerm(Term term)
				throws IOException
			{
				luceneSubQueries.add(qf.newTermQuery(term));
			}

			
			{
				this$0 = SimpleTermRewriteQuery.this;
				luceneSubQueries = list;
				super();
			}
		});
		return luceneSubQueries.size() != 0 ? luceneSubQueries.size() != 1 ? SrndBooleanQuery.makeBooleanQuery(luceneSubQueries, org.apache.lucene.search.BooleanClause.Occur.SHOULD) : (Query)luceneSubQueries.get(0) : SrndQuery.theEmptyLcnQuery;
	}
}
