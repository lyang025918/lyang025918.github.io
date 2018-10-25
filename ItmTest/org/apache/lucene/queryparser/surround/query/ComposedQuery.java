// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComposedQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.util.*;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SrndQuery, BasicQueryFactory

public abstract class ComposedQuery extends SrndQuery
{

	protected String opName;
	protected List queries;
	private boolean operatorInfix;

	public ComposedQuery(List qs, boolean operatorInfix, String opName)
	{
		recompose(qs);
		this.operatorInfix = operatorInfix;
		this.opName = opName;
	}

	protected void recompose(List queries)
	{
		if (queries.size() < 2)
		{
			throw new AssertionError("Too few subqueries");
		} else
		{
			this.queries = queries;
			return;
		}
	}

	public String getOperatorName()
	{
		return opName;
	}

	public Iterator getSubQueriesIterator()
	{
		return queries.listIterator();
	}

	public int getNrSubQueries()
	{
		return queries.size();
	}

	public SrndQuery getSubQuery(int qn)
	{
		return (SrndQuery)queries.get(qn);
	}

	public boolean isOperatorInfix()
	{
		return operatorInfix;
	}

	public List makeLuceneSubQueriesField(String fn, BasicQueryFactory qf)
	{
		List luceneSubQueries = new ArrayList();
		for (Iterator sqi = getSubQueriesIterator(); sqi.hasNext(); luceneSubQueries.add(((SrndQuery)sqi.next()).makeLuceneQueryField(fn, qf)));
		return luceneSubQueries;
	}

	public String toString()
	{
		StringBuilder r = new StringBuilder();
		if (isOperatorInfix())
			infixToString(r);
		else
			prefixToString(r);
		weightToString(r);
		return r.toString();
	}

	protected String getPrefixSeparator()
	{
		return ", ";
	}

	protected String getBracketOpen()
	{
		return "(";
	}

	protected String getBracketClose()
	{
		return ")";
	}

	protected void infixToString(StringBuilder r)
	{
		Iterator sqi = getSubQueriesIterator();
		r.append(getBracketOpen());
		if (sqi.hasNext())
		{
			r.append(((SrndQuery)sqi.next()).toString());
			for (; sqi.hasNext(); r.append(((SrndQuery)sqi.next()).toString()))
			{
				r.append(" ");
				r.append(getOperatorName());
				r.append(" ");
			}

		}
		r.append(getBracketClose());
	}

	protected void prefixToString(StringBuilder r)
	{
		Iterator sqi = getSubQueriesIterator();
		r.append(getOperatorName());
		r.append(getBracketOpen());
		if (sqi.hasNext())
		{
			r.append(((SrndQuery)sqi.next()).toString());
			for (; sqi.hasNext(); r.append(((SrndQuery)sqi.next()).toString()))
				r.append(getPrefixSeparator());

		}
		r.append(getBracketClose());
	}

	public boolean isFieldsSubQueryAcceptable()
	{
		for (Iterator sqi = getSubQueriesIterator(); sqi.hasNext();)
			if (((SrndQuery)sqi.next()).isFieldsSubQueryAcceptable())
				return true;

		return false;
	}
}
