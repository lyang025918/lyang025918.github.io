// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanMultiTermQueryWrapper.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, Spans, SpanOrQuery, SpanTermQuery

public class SpanMultiTermQueryWrapper extends SpanQuery
{
	public static final class TopTermsSpanBooleanQueryRewrite extends SpanRewriteMethod
	{

		private final TopTermsRewrite delegate;

		public int getSize()
		{
			return delegate.getSize();
		}

		public SpanQuery rewrite(IndexReader reader, MultiTermQuery query)
			throws IOException
		{
			return (SpanQuery)delegate.rewrite(reader, query);
		}

		public int hashCode()
		{
			return 31 * delegate.hashCode();
		}

		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
			{
				return false;
			} else
			{
				TopTermsSpanBooleanQueryRewrite other = (TopTermsSpanBooleanQueryRewrite)obj;
				return delegate.equals(other.delegate);
			}
		}

		public volatile Query rewrite(IndexReader x0, MultiTermQuery x1)
			throws IOException
		{
			return rewrite(x0, x1);
		}

		public TopTermsSpanBooleanQueryRewrite(int size)
		{
			delegate = new TopTermsRewrite(size) {

				final TopTermsSpanBooleanQueryRewrite this$0;

				protected int getMaxSize()
				{
					return 0x7fffffff;
				}

				protected SpanOrQuery getTopLevelQuery()
				{
					return new SpanOrQuery(new SpanQuery[0]);
				}

				protected void addClause(SpanOrQuery topLevel, Term term, int docFreq, float boost, TermContext states)
				{
					SpanTermQuery q = new SpanTermQuery(term);
					q.setBoost(boost);
					topLevel.addClause(q);
				}

				protected volatile void addClause(Query x0, Term x1, int x2, float x3, TermContext x4)
					throws IOException
				{
					addClause((SpanOrQuery)x0, x1, x2, x3, x4);
				}

				protected volatile Query getTopLevelQuery()
					throws IOException
				{
					return getTopLevelQuery();
				}

				
				{
					this$0 = TopTermsSpanBooleanQueryRewrite.this;
					super(x0);
				}
			};
		}
	}

	public static abstract class SpanRewriteMethod extends org.apache.lucene.search.MultiTermQuery.RewriteMethod
	{

		public abstract SpanQuery rewrite(IndexReader indexreader, MultiTermQuery multitermquery)
			throws IOException;

		public volatile Query rewrite(IndexReader x0, MultiTermQuery x1)
			throws IOException
		{
			return rewrite(x0, x1);
		}

		public SpanRewriteMethod()
		{
		}
	}


	protected final MultiTermQuery query;
	public static final SpanRewriteMethod SCORING_SPAN_QUERY_REWRITE = new SpanRewriteMethod() {

		private final ScoringRewrite delegate = new ScoringRewrite() {

			final 1 this$0;

			protected SpanOrQuery getTopLevelQuery()
			{
				return new SpanOrQuery(new SpanQuery[0]);
			}

			protected void checkMaxClauseCount(int i)
			{
			}

			protected void addClause(SpanOrQuery topLevel, Term term, int docCount, float boost, TermContext states)
			{
				SpanTermQuery q = new SpanTermQuery(term);
				q.setBoost(boost);
				topLevel.addClause(q);
			}

			protected volatile void addClause(Query x0, Term x1, int x2, float x3, TermContext x4)
				throws IOException
			{
				addClause((SpanOrQuery)x0, x1, x2, x3, x4);
			}

			protected volatile Query getTopLevelQuery()
				throws IOException
			{
				return getTopLevelQuery();
			}

					
					{
						this$0 = 1.this;
						super();
					}
		};

		public SpanQuery rewrite(IndexReader reader, MultiTermQuery query)
			throws IOException
		{
			return (SpanQuery)delegate.rewrite(reader, query);
		}

		public volatile Query rewrite(IndexReader x0, MultiTermQuery x1)
			throws IOException
		{
			return rewrite(x0, x1);
		}

	};

	public SpanMultiTermQueryWrapper(MultiTermQuery query)
	{
		this.query = query;
		org.apache.lucene.search.MultiTermQuery.RewriteMethod method = query.getRewriteMethod();
		if (method instanceof TopTermsRewrite)
		{
			int pqsize = ((TopTermsRewrite)method).getSize();
			setRewriteMethod(new TopTermsSpanBooleanQueryRewrite(pqsize));
		} else
		{
			setRewriteMethod(SCORING_SPAN_QUERY_REWRITE);
		}
	}

	public final SpanRewriteMethod getRewriteMethod()
	{
		org.apache.lucene.search.MultiTermQuery.RewriteMethod m = query.getRewriteMethod();
		if (!(m instanceof SpanRewriteMethod))
			throw new UnsupportedOperationException("You can only use SpanMultiTermQueryWrapper with a suitable SpanRewriteMethod.");
		else
			return (SpanRewriteMethod)m;
	}

	public final void setRewriteMethod(SpanRewriteMethod rewriteMethod)
	{
		query.setRewriteMethod(rewriteMethod);
	}

	public Spans getSpans(AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		throw new UnsupportedOperationException("Query should have been rewritten");
	}

	public String getField()
	{
		return query.getField();
	}

	public String toString(String field)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SpanMultiTermQueryWrapper(");
		builder.append(query.toString(field));
		builder.append(")");
		return builder.toString();
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		Query q = query.rewrite(reader);
		if (!(q instanceof SpanQuery))
			throw new UnsupportedOperationException("You can only use SpanMultiTermQueryWrapper with a suitable SpanRewriteMethod.");
		else
			return q;
	}

	public int hashCode()
	{
		return 31 * query.hashCode();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
		{
			return false;
		} else
		{
			SpanMultiTermQueryWrapper other = (SpanMultiTermQueryWrapper)obj;
			return query.equals(other.query);
		}
	}

}
