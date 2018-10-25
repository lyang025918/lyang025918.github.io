// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			QueryDocValues

public class QueryValueSource extends ValueSource
{

	final Query q;
	final float defVal;

	public QueryValueSource(Query q, float defVal)
	{
		this.q = q;
		this.defVal = defVal;
	}

	public Query getQuery()
	{
		return q;
	}

	public float getDefaultValue()
	{
		return defVal;
	}

	public String description()
	{
		return (new StringBuilder()).append("query(").append(q).append(",def=").append(defVal).append(")").toString();
	}

	public FunctionValues getValues(Map fcontext, AtomicReaderContext readerContext)
		throws IOException
	{
		return new QueryDocValues(this, readerContext, fcontext);
	}

	public int hashCode()
	{
		return q.hashCode() * 29;
	}

	public boolean equals(Object o)
	{
		if (org/apache/lucene/queries/function/valuesource/QueryValueSource != o.getClass())
		{
			return false;
		} else
		{
			QueryValueSource other = (QueryValueSource)o;
			return q.equals(other.q) && defVal == other.defVal;
		}
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		org.apache.lucene.search.Weight w = searcher.createNormalizedWeight(q);
		context.put(this, w);
	}
}
