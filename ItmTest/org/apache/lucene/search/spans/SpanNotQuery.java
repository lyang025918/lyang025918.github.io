// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanNotQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, Spans

public class SpanNotQuery extends SpanQuery
	implements Cloneable
{

	private SpanQuery include;
	private SpanQuery exclude;

	public SpanNotQuery(SpanQuery include, SpanQuery exclude)
	{
		this.include = include;
		this.exclude = exclude;
		if (!include.getField().equals(exclude.getField()))
			throw new IllegalArgumentException("Clauses must have same field.");
		else
			return;
	}

	public SpanQuery getInclude()
	{
		return include;
	}

	public SpanQuery getExclude()
	{
		return exclude;
	}

	public String getField()
	{
		return include.getField();
	}

	public void extractTerms(Set terms)
	{
		include.extractTerms(terms);
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("spanNot(");
		buffer.append(include.toString(field));
		buffer.append(", ");
		buffer.append(exclude.toString(field));
		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public SpanNotQuery clone()
	{
		SpanNotQuery spanNotQuery = new SpanNotQuery((SpanQuery)include.clone(), (SpanQuery)exclude.clone());
		spanNotQuery.setBoost(getBoost());
		return spanNotQuery;
	}

	public Spans getSpans(final AtomicReaderContext context, final Bits acceptDocs, final Map termContexts)
		throws IOException
	{
		return new Spans() {

			private Spans includeSpans;
			private boolean moreInclude;
			private Spans excludeSpans;
			private boolean moreExclude;
			final AtomicReaderContext val$context;
			final Bits val$acceptDocs;
			final Map val$termContexts;
			final SpanNotQuery this$0;

			public boolean next()
				throws IOException
			{
				if (moreInclude)
					moreInclude = includeSpans.next();
				do
				{
					if (!moreInclude || !moreExclude)
						break;
					if (includeSpans.doc() > excludeSpans.doc())
						moreExclude = excludeSpans.skipTo(includeSpans.doc());
					for (; moreExclude && includeSpans.doc() == excludeSpans.doc() && excludeSpans.end() <= includeSpans.start(); moreExclude = excludeSpans.next());
					if (!moreExclude || includeSpans.doc() != excludeSpans.doc() || includeSpans.end() <= excludeSpans.start())
						break;
					moreInclude = includeSpans.next();
				} while (true);
				return moreInclude;
			}

			public boolean skipTo(int target)
				throws IOException
			{
				if (moreInclude)
					moreInclude = includeSpans.skipTo(target);
				if (!moreInclude)
					return false;
				if (moreExclude && includeSpans.doc() > excludeSpans.doc())
					moreExclude = excludeSpans.skipTo(includeSpans.doc());
				for (; moreExclude && includeSpans.doc() == excludeSpans.doc() && excludeSpans.end() <= includeSpans.start(); moreExclude = excludeSpans.next());
				if (!moreExclude || includeSpans.doc() != excludeSpans.doc() || includeSpans.end() <= excludeSpans.start())
					return true;
				else
					return next();
			}

			public int doc()
			{
				return includeSpans.doc();
			}

			public int start()
			{
				return includeSpans.start();
			}

			public int end()
			{
				return includeSpans.end();
			}

			public Collection getPayload()
				throws IOException
			{
				ArrayList result = null;
				if (includeSpans.isPayloadAvailable())
					result = new ArrayList(includeSpans.getPayload());
				return result;
			}

			public boolean isPayloadAvailable()
				throws IOException
			{
				return includeSpans.isPayloadAvailable();
			}

			public String toString()
			{
				return (new StringBuilder()).append("spans(").append(SpanNotQuery.this.toString()).append(")").toString();
			}

			
				throws IOException
			{
				this$0 = SpanNotQuery.this;
				context = atomicreadercontext;
				acceptDocs = bits;
				termContexts = map;
				super();
				includeSpans = include.getSpans(context, acceptDocs, termContexts);
				moreInclude = true;
				excludeSpans = exclude.getSpans(context, acceptDocs, termContexts);
				moreExclude = excludeSpans.next();
			}
		};
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		SpanNotQuery clone = null;
		SpanQuery rewrittenInclude = (SpanQuery)include.rewrite(reader);
		if (rewrittenInclude != include)
		{
			clone = clone();
			clone.include = rewrittenInclude;
		}
		SpanQuery rewrittenExclude = (SpanQuery)exclude.rewrite(reader);
		if (rewrittenExclude != exclude)
		{
			if (clone == null)
				clone = clone();
			clone.exclude = rewrittenExclude;
		}
		if (clone != null)
			return clone;
		else
			return this;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SpanNotQuery))
		{
			return false;
		} else
		{
			SpanNotQuery other = (SpanNotQuery)o;
			return include.equals(other.include) && exclude.equals(other.exclude) && getBoost() == other.getBoost();
		}
	}

	public int hashCode()
	{
		int h = include.hashCode();
		h = h << 1 | h >>> 31;
		h ^= exclude.hashCode();
		h = h << 1 | h >>> 31;
		h ^= Float.floatToRawIntBits(getBoost());
		return h;
	}

	public volatile Query clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}


}
