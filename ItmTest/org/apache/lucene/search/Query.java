// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Query.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Set;
import org.apache.lucene.index.IndexReader;

// Referenced classes of package org.apache.lucene.search:
//			IndexSearcher, Weight

public abstract class Query
	implements Cloneable
{

	private float boost;

	public Query()
	{
		boost = 1.0F;
	}

	public void setBoost(float b)
	{
		boost = b;
	}

	public float getBoost()
	{
		return boost;
	}

	public abstract String toString(String s);

	public String toString()
	{
		return toString("");
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		throw new UnsupportedOperationException((new StringBuilder()).append("Query ").append(this).append(" does not implement createWeight").toString());
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		return this;
	}

	public void extractTerms(Set terms)
	{
		throw new UnsupportedOperationException();
	}

	public Query clone()
	{
		return (Query)super.clone();
		CloneNotSupportedException e;
		e;
		throw new RuntimeException((new StringBuilder()).append("Clone not supported: ").append(e.getMessage()).toString());
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + Float.floatToIntBits(boost);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Query other = (Query)obj;
		return Float.floatToIntBits(boost) == Float.floatToIntBits(other.boost);
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
