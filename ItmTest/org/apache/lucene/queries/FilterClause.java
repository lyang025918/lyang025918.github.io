// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilterClause.java

package org.apache.lucene.queries;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Filter;

public final class FilterClause
{

	private final org.apache.lucene.search.BooleanClause.Occur occur;
	private final Filter filter;

	public FilterClause(Filter filter, org.apache.lucene.search.BooleanClause.Occur occur)
	{
		this.occur = occur;
		this.filter = filter;
	}

	public Filter getFilter()
	{
		return filter;
	}

	public org.apache.lucene.search.BooleanClause.Occur getOccur()
	{
		return occur;
	}

	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (o == null || !(o instanceof FilterClause))
		{
			return false;
		} else
		{
			FilterClause other = (FilterClause)o;
			return filter.equals(other.filter) && occur == other.occur;
		}
	}

	public int hashCode()
	{
		return filter.hashCode() ^ occur.hashCode();
	}

	public String toString()
	{
		return (new StringBuilder()).append(occur.toString()).append(filter.toString()).toString();
	}
}
