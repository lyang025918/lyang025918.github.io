// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostingQuery.java

package org.apache.lucene.queries;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;

public class BoostingQuery extends Query
{

	private final float boost;
	private final Query match;
	private final Query context;

	public BoostingQuery(Query match, Query context, float boost)
	{
		this.match = match;
		this.context = context.clone();
		this.boost = boost;
		this.context.setBoost(0.0F);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		BooleanQuery result = new BooleanQuery() {

			final BoostingQuery this$0;

			public Weight createWeight(IndexSearcher searcher)
				throws IOException
			{
				return new org.apache.lucene.search.BooleanQuery.BooleanWeight(searcher, false) {

					final 1 this$1;

					public float coord(int overlap, int max)
					{
						switch (overlap)
						{
						case 1: // '\001'
							return 1.0F;

						case 2: // '\002'
							return boost;
						}
						return 0.0F;
					}

					
					{
						this$1 = 1.this;
						super(1.this, x0, x1);
					}
				};
			}

			
			{
				this$0 = BoostingQuery.this;
				super();
			}
		};
		result.add(match, org.apache.lucene.search.BooleanClause.Occur.MUST);
		result.add(context, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		return result;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + Float.floatToIntBits(boost);
		result = 31 * result + (context != null ? context.hashCode() : 0);
		result = 31 * result + (match != null ? match.hashCode() : 0);
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
		BoostingQuery other = (BoostingQuery)obj;
		if (Float.floatToIntBits(boost) != Float.floatToIntBits(other.boost))
			return false;
		if (context == null)
		{
			if (other.context != null)
				return false;
		} else
		if (!context.equals(other.context))
			return false;
		if (match == null)
		{
			if (other.match != null)
				return false;
		} else
		if (!match.equals(other.match))
			return false;
		return true;
	}

	public String toString(String field)
	{
		return (new StringBuilder()).append(match.toString(field)).append("/").append(context.toString(field)).toString();
	}

}
