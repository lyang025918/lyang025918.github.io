// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanNearQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, SpanOrQuery, NearSpansOrdered, NearSpansUnordered, 
//			Spans

public class SpanNearQuery extends SpanQuery
	implements Cloneable
{

	protected List clauses;
	protected int slop;
	protected boolean inOrder;
	protected String field;
	private boolean collectPayloads;

	public SpanNearQuery(SpanQuery clauses[], int slop, boolean inOrder)
	{
		this(clauses, slop, inOrder, true);
	}

	public SpanNearQuery(SpanQuery clauses[], int slop, boolean inOrder, boolean collectPayloads)
	{
		this.clauses = new ArrayList(clauses.length);
		for (int i = 0; i < clauses.length; i++)
		{
			SpanQuery clause = clauses[i];
			if (i == 0)
				field = clause.getField();
			else
			if (!clause.getField().equals(field))
				throw new IllegalArgumentException("Clauses must have same field.");
			this.clauses.add(clause);
		}

		this.collectPayloads = collectPayloads;
		this.slop = slop;
		this.inOrder = inOrder;
	}

	public SpanQuery[] getClauses()
	{
		return (SpanQuery[])clauses.toArray(new SpanQuery[clauses.size()]);
	}

	public int getSlop()
	{
		return slop;
	}

	public boolean isInOrder()
	{
		return inOrder;
	}

	public String getField()
	{
		return field;
	}

	public void extractTerms(Set terms)
	{
		SpanQuery clause;
		for (Iterator i$ = clauses.iterator(); i$.hasNext(); clause.extractTerms(terms))
			clause = (SpanQuery)i$.next();

	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("spanNear([");
		Iterator i = clauses.iterator();
		do
		{
			if (!i.hasNext())
				break;
			SpanQuery clause = (SpanQuery)i.next();
			buffer.append(clause.toString(field));
			if (i.hasNext())
				buffer.append(", ");
		} while (true);
		buffer.append("], ");
		buffer.append(slop);
		buffer.append(", ");
		buffer.append(inOrder);
		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public Spans getSpans(AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		if (clauses.size() == 0)
			return (new SpanOrQuery(getClauses())).getSpans(context, acceptDocs, termContexts);
		if (clauses.size() == 1)
			return ((SpanQuery)clauses.get(0)).getSpans(context, acceptDocs, termContexts);
		else
			return ((Spans) (inOrder ? new NearSpansOrdered(this, context, acceptDocs, termContexts, collectPayloads) : new NearSpansUnordered(this, context, acceptDocs, termContexts)));
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		SpanNearQuery clone = null;
		for (int i = 0; i < clauses.size(); i++)
		{
			SpanQuery c = (SpanQuery)clauses.get(i);
			SpanQuery query = (SpanQuery)c.rewrite(reader);
			if (query == c)
				continue;
			if (clone == null)
				clone = clone();
			clone.clauses.set(i, query);
		}

		if (clone != null)
			return clone;
		else
			return this;
	}

	public SpanNearQuery clone()
	{
		int sz = clauses.size();
		SpanQuery newClauses[] = new SpanQuery[sz];
		for (int i = 0; i < sz; i++)
			newClauses[i] = (SpanQuery)((SpanQuery)clauses.get(i)).clone();

		SpanNearQuery spanNearQuery = new SpanNearQuery(newClauses, slop, inOrder);
		spanNearQuery.setBoost(getBoost());
		return spanNearQuery;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SpanNearQuery))
			return false;
		SpanNearQuery spanNearQuery = (SpanNearQuery)o;
		if (inOrder != spanNearQuery.inOrder)
			return false;
		if (slop != spanNearQuery.slop)
			return false;
		if (!clauses.equals(spanNearQuery.clauses))
			return false;
		else
			return getBoost() == spanNearQuery.getBoost();
	}

	public int hashCode()
	{
		int result = clauses.hashCode();
		result ^= result << 14 | result >>> 19;
		result += Float.floatToRawIntBits(getBoost());
		result += slop;
		result ^= inOrder ? 0x99afd3bd : 0;
		return result;
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
