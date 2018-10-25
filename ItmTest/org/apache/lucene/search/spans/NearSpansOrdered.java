// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NearSpansOrdered.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search.spans:
//			Spans, SpanNearQuery, SpanQuery

public class NearSpansOrdered extends Spans
{

	private final int allowedSlop;
	private boolean firstTime;
	private boolean more;
	private final Spans subSpans[];
	private boolean inSameDoc;
	private int matchDoc;
	private int matchStart;
	private int matchEnd;
	private List matchPayload;
	private final Spans subSpansByDoc[];
	private final Comparator spanDocComparator;
	private SpanNearQuery query;
	private boolean collectPayloads;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/spans/NearSpansOrdered.desiredAssertionStatus();

	public NearSpansOrdered(SpanNearQuery spanNearQuery, AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		this(spanNearQuery, context, acceptDocs, termContexts, true);
	}

	public NearSpansOrdered(SpanNearQuery spanNearQuery, AtomicReaderContext context, Bits acceptDocs, Map termContexts, boolean collectPayloads)
		throws IOException
	{
		firstTime = true;
		more = false;
		inSameDoc = false;
		matchDoc = -1;
		matchStart = -1;
		matchEnd = -1;
		spanDocComparator = new Comparator() {

			final NearSpansOrdered this$0;

			public int compare(Spans o1, Spans o2)
			{
				return o1.doc() - o2.doc();
			}

			public volatile int compare(Object x0, Object x1)
			{
				return compare((Spans)x0, (Spans)x1);
			}

			
			{
				this$0 = NearSpansOrdered.this;
				super();
			}
		};
		this.collectPayloads = true;
		if (spanNearQuery.getClauses().length < 2)
			throw new IllegalArgumentException((new StringBuilder()).append("Less than 2 clauses: ").append(spanNearQuery).toString());
		this.collectPayloads = collectPayloads;
		allowedSlop = spanNearQuery.getSlop();
		SpanQuery clauses[] = spanNearQuery.getClauses();
		subSpans = new Spans[clauses.length];
		matchPayload = new LinkedList();
		subSpansByDoc = new Spans[clauses.length];
		for (int i = 0; i < clauses.length; i++)
		{
			subSpans[i] = clauses[i].getSpans(context, acceptDocs, termContexts);
			subSpansByDoc[i] = subSpans[i];
		}

		query = spanNearQuery;
	}

	public int doc()
	{
		return matchDoc;
	}

	public int start()
	{
		return matchStart;
	}

	public int end()
	{
		return matchEnd;
	}

	public Spans[] getSubSpans()
	{
		return subSpans;
	}

	public Collection getPayload()
		throws IOException
	{
		return matchPayload;
	}

	public boolean isPayloadAvailable()
	{
		return !matchPayload.isEmpty();
	}

	public boolean next()
		throws IOException
	{
		if (firstTime)
		{
			firstTime = false;
			for (int i = 0; i < subSpans.length; i++)
				if (!subSpans[i].next())
				{
					more = false;
					return false;
				}

			more = true;
		}
		if (collectPayloads)
			matchPayload.clear();
		return advanceAfterOrdered();
	}

	public boolean skipTo(int target)
		throws IOException
	{
		if (firstTime)
		{
			firstTime = false;
			for (int i = 0; i < subSpans.length; i++)
				if (!subSpans[i].skipTo(target))
				{
					more = false;
					return false;
				}

			more = true;
		} else
		if (more && subSpans[0].doc() < target)
			if (subSpans[0].skipTo(target))
			{
				inSameDoc = false;
			} else
			{
				more = false;
				return false;
			}
		if (collectPayloads)
			matchPayload.clear();
		return advanceAfterOrdered();
	}

	private boolean advanceAfterOrdered()
		throws IOException
	{
		while (more && (inSameDoc || toSameDoc())) 
			if (stretchToOrder() && shrinkToAfterShortestMatch())
				return true;
		return false;
	}

	private boolean toSameDoc()
		throws IOException
	{
		ArrayUtil.mergeSort(subSpansByDoc, spanDocComparator);
		int firstIndex = 0;
		int maxDoc = subSpansByDoc[subSpansByDoc.length - 1].doc();
		do
		{
			if (subSpansByDoc[firstIndex].doc() == maxDoc)
				break;
			if (!subSpansByDoc[firstIndex].skipTo(maxDoc))
			{
				more = false;
				inSameDoc = false;
				return false;
			}
			maxDoc = subSpansByDoc[firstIndex].doc();
			if (++firstIndex == subSpansByDoc.length)
				firstIndex = 0;
		} while (true);
		for (int i = 0; i < subSpansByDoc.length; i++)
			if (!$assertionsDisabled && subSpansByDoc[i].doc() != maxDoc)
				throw new AssertionError((new StringBuilder()).append(" NearSpansOrdered.toSameDoc() spans ").append(subSpansByDoc[0]).append("\n at doc ").append(subSpansByDoc[i].doc()).append(", but should be at ").append(maxDoc).toString());

		inSameDoc = true;
		return true;
	}

	static final boolean docSpansOrdered(Spans spans1, Spans spans2)
	{
		if (!$assertionsDisabled && spans1.doc() != spans2.doc())
		{
			throw new AssertionError((new StringBuilder()).append("doc1 ").append(spans1.doc()).append(" != doc2 ").append(spans2.doc()).toString());
		} else
		{
			int start1 = spans1.start();
			int start2 = spans2.start();
			return start1 != start2 ? start1 < start2 : spans1.end() < spans2.end();
		}
	}

	private static final boolean docSpansOrdered(int start1, int end1, int start2, int end2)
	{
		return start1 != start2 ? start1 < start2 : end1 < end2;
	}

	private boolean stretchToOrder()
		throws IOException
	{
		matchDoc = subSpans[0].doc();
label0:
		for (int i = 1; inSameDoc && i < subSpans.length; i++)
		{
			do
			{
				if (docSpansOrdered(subSpans[i - 1], subSpans[i]))
					continue label0;
				if (!subSpans[i].next())
				{
					inSameDoc = false;
					more = false;
					continue label0;
				}
			} while (matchDoc == subSpans[i].doc());
			inSameDoc = false;
		}

		return inSameDoc;
	}

	private boolean shrinkToAfterShortestMatch()
		throws IOException
	{
		matchStart = subSpans[subSpans.length - 1].start();
		matchEnd = subSpans[subSpans.length - 1].end();
		Set possibleMatchPayloads = new HashSet();
		if (subSpans[subSpans.length - 1].isPayloadAvailable())
			possibleMatchPayloads.addAll(subSpans[subSpans.length - 1].getPayload());
		Collection possiblePayload = null;
		int matchSlop = 0;
		int lastStart = matchStart;
		int lastEnd = matchEnd;
		for (int i = subSpans.length - 2; i >= 0; i--)
		{
			Spans prevSpans = subSpans[i];
			if (collectPayloads && prevSpans.isPayloadAvailable())
			{
				Collection payload = prevSpans.getPayload();
				possiblePayload = new ArrayList(payload.size());
				possiblePayload.addAll(payload);
			}
			int prevStart = prevSpans.start();
			int prevEnd = prevSpans.end();
			do
			{
				if (!prevSpans.next())
				{
					inSameDoc = false;
					more = false;
					break;
				}
				if (matchDoc != prevSpans.doc())
				{
					inSameDoc = false;
					break;
				}
				int ppStart = prevSpans.start();
				int ppEnd = prevSpans.end();
				if (!docSpansOrdered(ppStart, ppEnd, lastStart, lastEnd))
					break;
				prevStart = ppStart;
				prevEnd = ppEnd;
				if (collectPayloads && prevSpans.isPayloadAvailable())
				{
					Collection payload = prevSpans.getPayload();
					possiblePayload = new ArrayList(payload.size());
					possiblePayload.addAll(payload);
				}
			} while (true);
			if (collectPayloads && possiblePayload != null)
				possibleMatchPayloads.addAll(possiblePayload);
			if (!$assertionsDisabled && prevStart > matchStart)
				throw new AssertionError();
			if (matchStart > prevEnd)
				matchSlop += matchStart - prevEnd;
			matchStart = prevStart;
			lastStart = prevStart;
			lastEnd = prevEnd;
		}

		boolean match = matchSlop <= allowedSlop;
		if (collectPayloads && match && possibleMatchPayloads.size() > 0)
			matchPayload.addAll(possibleMatchPayloads);
		return match;
	}

	public String toString()
	{
		return (new StringBuilder()).append(getClass().getName()).append("(").append(query.toString()).append(")@").append(firstTime ? "START" : more ? (new StringBuilder()).append(doc()).append(":").append(start()).append("-").append(end()).toString() : "END").toString();
	}

}
