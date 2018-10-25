// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NearSpansUnordered.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search.spans:
//			Spans, SpanNearQuery, SpanQuery, NearSpansOrdered

public class NearSpansUnordered extends Spans
{
	private class SpansCell extends Spans
	{

		private Spans spans;
		private SpansCell next;
		private int length;
		private int index;
		final NearSpansUnordered this$0;

		public boolean next()
			throws IOException
		{
			return adjust(spans.next());
		}

		public boolean skipTo(int target)
			throws IOException
		{
			return adjust(spans.skipTo(target));
		}

		private boolean adjust(boolean condition)
		{
			if (length != -1)
				totalLength-= = length;
			if (condition)
			{
				length = end() - start();
				totalLength+= = length;
				if (max == null || doc() > max.doc() || doc() == max.doc() && end() > max.end())
					max = this;
			}
			more = condition;
			return condition;
		}

		public int doc()
		{
			return spans.doc();
		}

		public int start()
		{
			return spans.start();
		}

		public int end()
		{
			return spans.end();
		}

		public Collection getPayload()
			throws IOException
		{
			return new ArrayList(spans.getPayload());
		}

		public boolean isPayloadAvailable()
			throws IOException
		{
			return spans.isPayloadAvailable();
		}

		public String toString()
		{
			return (new StringBuilder()).append(spans.toString()).append("#").append(index).toString();
		}




		public SpansCell(Spans spans, int index)
		{
			this$0 = NearSpansUnordered.this;
			super();
			length = -1;
			this.spans = spans;
			this.index = index;
		}
	}

	private class CellQueue extends PriorityQueue
	{

		final NearSpansUnordered this$0;

		protected final boolean lessThan(SpansCell spans1, SpansCell spans2)
		{
			if (spans1.doc() == spans2.doc())
				return NearSpansOrdered.docSpansOrdered(spans1, spans2);
			else
				return spans1.doc() < spans2.doc();
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((SpansCell)x0, (SpansCell)x1);
		}

		public CellQueue(int size)
		{
			this$0 = NearSpansUnordered.this;
			super(size);
		}
	}


	private SpanNearQuery query;
	private List ordered;
	private Spans subSpans[];
	private int slop;
	private SpansCell first;
	private SpansCell last;
	private int totalLength;
	private CellQueue queue;
	private SpansCell max;
	private boolean more;
	private boolean firstTime;

	public NearSpansUnordered(SpanNearQuery query, AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		ordered = new ArrayList();
		more = true;
		firstTime = true;
		this.query = query;
		slop = query.getSlop();
		SpanQuery clauses[] = query.getClauses();
		queue = new CellQueue(clauses.length);
		subSpans = new Spans[clauses.length];
		for (int i = 0; i < clauses.length; i++)
		{
			SpansCell cell = new SpansCell(clauses[i].getSpans(context, acceptDocs, termContexts), i);
			ordered.add(cell);
			subSpans[i] = cell.spans;
		}

	}

	public Spans[] getSubSpans()
	{
		return subSpans;
	}

	public boolean next()
		throws IOException
	{
		if (firstTime)
		{
			initList(true);
			listToQueue();
			firstTime = false;
		} else
		if (more)
			if (min().next())
				queue.updateTop();
			else
				more = false;
		do
		{
			if (!more)
				break;
			boolean queueStale = false;
			if (min().doc() != max.doc())
			{
				queueToList();
				queueStale = true;
			}
			while (more && first.doc() < last.doc()) 
			{
				more = first.skipTo(last.doc());
				firstToLast();
				queueStale = true;
			}
			if (!more)
				return false;
			if (queueStale)
			{
				listToQueue();
				queueStale = false;
			}
			if (atMatch())
				return true;
			more = min().next();
			if (more)
				queue.updateTop();
		} while (true);
		return false;
	}

	public boolean skipTo(int target)
		throws IOException
	{
		if (firstTime)
		{
			initList(false);
			for (SpansCell cell = first; more && cell != null; cell = cell.next)
				more = cell.skipTo(target);

			if (more)
				listToQueue();
			firstTime = false;
		} else
		{
			while (more && min().doc() < target) 
				if (min().skipTo(target))
					queue.updateTop();
				else
					more = false;
		}
		return more && (atMatch() || next());
	}

	private SpansCell min()
	{
		return (SpansCell)queue.top();
	}

	public int doc()
	{
		return min().doc();
	}

	public int start()
	{
		return min().start();
	}

	public int end()
	{
		return max.end();
	}

	public Collection getPayload()
		throws IOException
	{
		Set matchPayload = new HashSet();
		for (SpansCell cell = first; cell != null; cell = cell.next)
			if (cell.isPayloadAvailable())
				matchPayload.addAll(cell.getPayload());

		return matchPayload;
	}

	public boolean isPayloadAvailable()
		throws IOException
	{
		for (SpansCell pointer = min(); pointer != null; pointer = pointer.next)
			if (pointer.isPayloadAvailable())
				return true;

		return false;
	}

	public String toString()
	{
		return (new StringBuilder()).append(getClass().getName()).append("(").append(query.toString()).append(")@").append(firstTime ? "START" : more ? (new StringBuilder()).append(doc()).append(":").append(start()).append("-").append(end()).toString() : "END").toString();
	}

	private void initList(boolean next)
		throws IOException
	{
		for (int i = 0; more && i < ordered.size(); i++)
		{
			SpansCell cell = (SpansCell)ordered.get(i);
			if (next)
				more = cell.next();
			if (more)
				addToList(cell);
		}

	}

	private void addToList(SpansCell cell)
	{
		if (last != null)
			last.next = cell;
		else
			first = cell;
		last = cell;
		cell.next = null;
	}

	private void firstToLast()
	{
		last.next = first;
		last = first;
		first = first.next;
		last.next = null;
	}

	private void queueToList()
	{
		last = first = null;
		for (; queue.top() != null; addToList((SpansCell)queue.pop()));
	}

	private void listToQueue()
	{
		queue.clear();
		for (SpansCell cell = first; cell != null; cell = cell.next)
			queue.add(cell);

	}

	private boolean atMatch()
	{
		return min().doc() == max.doc() && max.end() - min().start() - totalLength <= slop;
	}





}
