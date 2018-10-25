// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryWrapperFilter.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			Filter, IndexSearcher, Query, DocIdSet, 
//			Weight, DocIdSetIterator

public class QueryWrapperFilter extends Filter
{

	private final Query query;

	public QueryWrapperFilter(Query query)
	{
		if (query == null)
		{
			throw new NullPointerException("Query may not be null");
		} else
		{
			this.query = query;
			return;
		}
	}

	public final Query getQuery()
	{
		return query;
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, final Bits acceptDocs)
		throws IOException
	{
		final AtomicReaderContext privateContext = context.reader().getContext();
		final Weight weight = (new IndexSearcher(privateContext)).createNormalizedWeight(query);
		return new DocIdSet() {

			final Weight val$weight;
			final AtomicReaderContext val$privateContext;
			final Bits val$acceptDocs;
			final QueryWrapperFilter this$0;

			public DocIdSetIterator iterator()
				throws IOException
			{
				return weight.scorer(privateContext, true, false, acceptDocs);
			}

			public boolean isCacheable()
			{
				return false;
			}

			
			{
				this$0 = QueryWrapperFilter.this;
				weight = weight1;
				privateContext = atomicreadercontext;
				acceptDocs = bits;
				super();
			}
		};
	}

	public String toString()
	{
		return (new StringBuilder()).append("QueryWrapperFilter(").append(query).append(")").toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof QueryWrapperFilter))
			return false;
		else
			return query.equals(((QueryWrapperFilter)o).query);
	}

	public int hashCode()
	{
		return query.hashCode() ^ 0x923f64b9;
	}
}
