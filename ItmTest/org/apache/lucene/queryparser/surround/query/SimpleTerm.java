// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleTerm.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SrndQuery, SimpleTermRewriteQuery, DistanceSubQuery, SpanNearClauseFactory, 
//			BasicQueryFactory

public abstract class SimpleTerm extends SrndQuery
	implements DistanceSubQuery, Comparable
{
	public static interface MatchingTermVisitor
	{

		public abstract void visitMatchingTerm(Term term)
			throws IOException;
	}


	private boolean quoted;

	public SimpleTerm(boolean q)
	{
		quoted = q;
	}

	boolean isQuoted()
	{
		return quoted;
	}

	public String getQuote()
	{
		return "\"";
	}

	public String getFieldOperator()
	{
		return "/";
	}

	public abstract String toStringUnquoted();

	/**
	 * @deprecated Method compareTo is deprecated
	 */

	public int compareTo(SimpleTerm ost)
	{
		return toStringUnquoted().compareTo(ost.toStringUnquoted());
	}

	protected void suffixToString(StringBuilder stringbuilder)
	{
	}

	public String toString()
	{
		StringBuilder r = new StringBuilder();
		if (isQuoted())
			r.append(getQuote());
		r.append(toStringUnquoted());
		if (isQuoted())
			r.append(getQuote());
		suffixToString(r);
		weightToString(r);
		return r.toString();
	}

	public abstract void visitMatchingTerms(IndexReader indexreader, String s, MatchingTermVisitor matchingtermvisitor)
		throws IOException;

	public String distanceSubQueryNotAllowed()
	{
		return null;
	}

	public void addSpanQueries(final SpanNearClauseFactory sncf)
		throws IOException
	{
		visitMatchingTerms(sncf.getIndexReader(), sncf.getFieldName(), new MatchingTermVisitor() {

			final SpanNearClauseFactory val$sncf;
			final SimpleTerm this$0;

			public void visitMatchingTerm(Term term)
				throws IOException
			{
				sncf.addTermWeighted(term, getWeight());
			}

			
			{
				this$0 = SimpleTerm.this;
				sncf = spannearclausefactory;
				super();
			}
		});
	}

	public Query makeLuceneQueryFieldNoBoost(String fieldName, BasicQueryFactory qf)
	{
		return new SimpleTermRewriteQuery(this, fieldName, qf);
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((SimpleTerm)x0);
	}
}
