// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SrndQuery.java

package org.apache.lucene.queryparser.surround.query;

import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			BasicQueryFactory

public abstract class SrndQuery
	implements Cloneable
{

	private float weight;
	private boolean weighted;
	public static final Query theEmptyLcnQuery = new BooleanQuery() {

		public void setBoost(float boost)
		{
			throw new UnsupportedOperationException();
		}

		public void add(BooleanClause clause)
		{
			throw new UnsupportedOperationException();
		}

		public void add(Query query, org.apache.lucene.search.BooleanClause.Occur occur)
		{
			throw new UnsupportedOperationException();
		}

	};

	public SrndQuery()
	{
		weight = 1.0F;
		weighted = false;
	}

	public void setWeight(float w)
	{
		weight = w;
		weighted = true;
	}

	public boolean isWeighted()
	{
		return weighted;
	}

	public float getWeight()
	{
		return weight;
	}

	public String getWeightString()
	{
		return Float.toString(getWeight());
	}

	public String getWeightOperator()
	{
		return "^";
	}

	protected void weightToString(StringBuilder r)
	{
		if (isWeighted())
		{
			r.append(getWeightOperator());
			r.append(getWeightString());
		}
	}

	public Query makeLuceneQueryField(String fieldName, BasicQueryFactory qf)
	{
		Query q = makeLuceneQueryFieldNoBoost(fieldName, qf);
		if (isWeighted())
			q.setBoost(getWeight() * q.getBoost());
		return q;
	}

	public abstract Query makeLuceneQueryFieldNoBoost(String s, BasicQueryFactory basicqueryfactory);

	public abstract String toString();

	public boolean isFieldsSubQueryAcceptable()
	{
		return true;
	}

	public SrndQuery clone()
	{
		return (SrndQuery)super.clone();
		CloneNotSupportedException cns;
		cns;
		throw new Error(cns);
	}

	public int hashCode()
	{
		return getClass().hashCode() ^ toString().hashCode();
	}

	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!getClass().equals(obj.getClass()))
			return false;
		else
			return toString().equals(obj.toString());
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
