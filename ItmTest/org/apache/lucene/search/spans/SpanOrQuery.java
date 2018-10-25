// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanOrQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, Spans

public class SpanOrQuery extends SpanQuery
	implements Cloneable
{
	private class SpanQueue extends PriorityQueue
	{

		final SpanOrQuery this$0;

		protected final boolean lessThan(Spans spans1, Spans spans2)
		{
			if (spans1.doc() == spans2.doc())
			{
				if (spans1.start() == spans2.start())
					return spans1.end() < spans2.end();
				else
					return spans1.start() < spans2.start();
			} else
			{
				return spans1.doc() < spans2.doc();
			}
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((Spans)x0, (Spans)x1);
		}

		public SpanQueue(int size)
		{
			this$0 = SpanOrQuery.this;
			super(size);
		}
	}


	private List clauses;
	private String field;

	public transient SpanOrQuery(SpanQuery clauses[])
	{
		this.clauses = new ArrayList(clauses.length);
		for (int i = 0; i < clauses.length; i++)
			addClause(clauses[i]);

	}

	public final void addClause(SpanQuery clause)
	{
		if (field == null)
			field = clause.getField();
		else
		if (!clause.getField().equals(field))
			throw new IllegalArgumentException("Clauses must have same field.");
		clauses.add(clause);
	}

	public SpanQuery[] getClauses()
	{
		return (SpanQuery[])clauses.toArray(new SpanQuery[clauses.size()]);
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

	public SpanOrQuery clone()
	{
		int sz = clauses.size();
		SpanQuery newClauses[] = new SpanQuery[sz];
		for (int i = 0; i < sz; i++)
			newClauses[i] = (SpanQuery)((SpanQuery)clauses.get(i)).clone();

		SpanOrQuery soq = new SpanOrQuery(newClauses);
		soq.setBoost(getBoost());
		return soq;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		SpanOrQuery clone = null;
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

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("spanOr([");
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
		buffer.append("])");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SpanOrQuery that = (SpanOrQuery)o;
		if (!clauses.equals(that.clauses))
			return false;
		if (!clauses.isEmpty() && !field.equals(that.field))
			return false;
		else
			return getBoost() == that.getBoost();
	}

	public int hashCode()
	{
		int h = clauses.hashCode();
		h ^= h << 10 | h >>> 23;
		h ^= Float.floatToRawIntBits(getBoost());
		return h;
	}

	public Spans getSpans(final AtomicReaderContext context, final Bits acceptDocs, final Map termContexts)
		throws IOException
	{
		if (clauses.size() == 1)
			return ((SpanQuery)clauses.get(0)).getSpans(context, acceptDocs, termContexts);
		else
			return new Spans() {

				private SpanQueue queue;
				final AtomicReaderContext val$context;
				final Bits val$acceptDocs;
				final Map val$termContexts;
				final SpanOrQuery this$0;

				private boolean initSpanQueue(int target)
					throws IOException
				{
					queue = new SpanQueue(clauses.size());
					Iterator i = clauses.iterator();
					do
					{
						if (!i.hasNext())
							break;
						Spans spans = ((SpanQuery)i.next()).getSpans(context, acceptDocs, termContexts);
						if (target == -1 && spans.next() || target != -1 && spans.skipTo(target))
							queue.add(spans);
					} while (true);
					return queue.size() != 0;
				}

				public boolean next()
					throws IOException
				{
					if (queue == null)
						return initSpanQueue(-1);
					if (queue.size() == 0)
						return false;
					if (top().next())
					{
						queue.updateTop();
						return true;
					} else
					{
						queue.pop();
						return queue.size() != 0;
					}
				}

				private Spans top()
				{
					return (Spans)queue.top();
				}

				public boolean skipTo(int target)
					throws IOException
				{
					if (queue == null)
						return initSpanQueue(target);
					boolean skipCalled;
					for (skipCalled = false; queue.size() != 0 && top().doc() < target; skipCalled = true)
						if (top().skipTo(target))
							queue.updateTop();
						else
							queue.pop();

					if (skipCalled)
						return queue.size() != 0;
					else
						return next();
				}

				public int doc()
				{
					return top().doc();
				}

				public int start()
				{
					return top().start();
				}

				public int end()
				{
					return top().end();
				}

				public Collection getPayload()
					throws IOException
				{
					ArrayList result = null;
					Spans theTop = top();
					if (theTop != null && theTop.isPayloadAvailable())
						result = new ArrayList(theTop.getPayload());
					return result;
				}

				public boolean isPayloadAvailable()
					throws IOException
				{
					Spans top = top();
					return top != null && top.isPayloadAvailable();
				}

				public String toString()
				{
					return (new StringBuilder()).append("spans(").append(SpanOrQuery.this).append(")@").append(queue != null ? queue.size() <= 0 ? "END" : (new StringBuilder()).append(doc()).append(":").append(start()).append("-").append(end()).toString() : "START").toString();
				}

			
			{
				this$0 = SpanOrQuery.this;
				context = atomicreadercontext;
				acceptDocs = bits;
				termContexts = map;
				super();
				queue = null;
			}
			};
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
