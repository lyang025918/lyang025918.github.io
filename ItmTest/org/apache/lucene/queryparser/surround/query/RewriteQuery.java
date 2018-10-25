// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RewriteQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SrndQuery, BasicQueryFactory

abstract class RewriteQuery extends Query
{

	protected final SrndQuery srndQuery;
	protected final String fieldName;
	protected final BasicQueryFactory qf;

	RewriteQuery(SrndQuery srndQuery, String fieldName, BasicQueryFactory qf)
	{
		this.srndQuery = srndQuery;
		this.fieldName = fieldName;
		this.qf = qf;
	}

	public abstract Query rewrite(IndexReader indexreader)
		throws IOException;

	public String toString()
	{
		return toString(null);
	}

	public String toString(String field)
	{
		return (new StringBuilder()).append(getClass().getName()).append(field != null ? (new StringBuilder()).append("(unused: ").append(field).append(")").toString() : "").append("(").append(fieldName).append(", ").append(srndQuery.toString()).append(", ").append(qf.toString()).append(")").toString();
	}

	public int hashCode()
	{
		return getClass().hashCode() ^ fieldName.hashCode() ^ qf.hashCode() ^ srndQuery.hashCode();
	}

	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		} else
		{
			RewriteQuery other = (RewriteQuery)obj;
			return fieldName.equals(other.fieldName) && qf.equals(other.qf) && srndQuery.equals(other.srndQuery);
		}
	}

	public RewriteQuery clone()
	{
		throw new UnsupportedOperationException();
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
