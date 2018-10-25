// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanPositionCheckQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, Spans

public abstract class SpanPositionCheckQuery extends SpanQuery
	implements Cloneable
{
	protected class PositionCheckSpan extends Spans
	{

		private Spans spans;
		final SpanPositionCheckQuery this$0;

		public boolean next()
			throws IOException
		{
			if (!spans.next())
				return false;
			else
				return doNext();
		}

		public boolean skipTo(int target)
			throws IOException
		{
			if (!spans.skipTo(target))
				return false;
			else
				return doNext();
		}

		protected boolean doNext()
			throws IOException
		{
			static class 1
			{

				static final int $SwitchMap$org$apache$lucene$search$spans$SpanPositionCheckQuery$AcceptStatus[];

				static 
				{
					$SwitchMap$org$apache$lucene$search$spans$SpanPositionCheckQuery$AcceptStatus = new int[AcceptStatus.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$search$spans$SpanPositionCheckQuery$AcceptStatus[AcceptStatus.YES.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$search$spans$SpanPositionCheckQuery$AcceptStatus[AcceptStatus.NO.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$search$spans$SpanPositionCheckQuery$AcceptStatus[AcceptStatus.NO_AND_ADVANCE.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			do
				switch (1..SwitchMap.org.apache.lucene.search.spans.SpanPositionCheckQuery.AcceptStatus[acceptPosition(this).ordinal()])
				{
				case 1: // '\001'
					return true;

				case 2: // '\002'
					if (!spans.next())
						return false;
					break;

				case 3: // '\003'
					if (!spans.skipTo(spans.doc() + 1))
						return false;
					break;
				}
			while (true);
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
			ArrayList result = null;
			if (spans.isPayloadAvailable())
				result = new ArrayList(spans.getPayload());
			return result;
		}

		public boolean isPayloadAvailable()
			throws IOException
		{
			return spans.isPayloadAvailable();
		}

		public String toString()
		{
			return (new StringBuilder()).append("spans(").append(SpanPositionCheckQuery.this.toString()).append(")").toString();
		}

		public PositionCheckSpan(AtomicReaderContext context, Bits acceptDocs, Map termContexts)
			throws IOException
		{
			this$0 = SpanPositionCheckQuery.this;
			super();
			spans = match.getSpans(context, acceptDocs, termContexts);
		}
	}

	protected static final class AcceptStatus extends Enum
	{

		public static final AcceptStatus YES;
		public static final AcceptStatus NO;
		public static final AcceptStatus NO_AND_ADVANCE;
		private static final AcceptStatus $VALUES[];

		public static AcceptStatus[] values()
		{
			return (AcceptStatus[])$VALUES.clone();
		}

		public static AcceptStatus valueOf(String name)
		{
			return (AcceptStatus)Enum.valueOf(org/apache/lucene/search/spans/SpanPositionCheckQuery$AcceptStatus, name);
		}

		static 
		{
			YES = new AcceptStatus("YES", 0);
			NO = new AcceptStatus("NO", 1);
			NO_AND_ADVANCE = new AcceptStatus("NO_AND_ADVANCE", 2);
			$VALUES = (new AcceptStatus[] {
				YES, NO, NO_AND_ADVANCE
			});
		}

		private AcceptStatus(String s, int i)
		{
			super(s, i);
		}
	}


	protected SpanQuery match;

	public SpanPositionCheckQuery(SpanQuery match)
	{
		this.match = match;
	}

	public SpanQuery getMatch()
	{
		return match;
	}

	public String getField()
	{
		return match.getField();
	}

	public void extractTerms(Set terms)
	{
		match.extractTerms(terms);
	}

	protected abstract AcceptStatus acceptPosition(Spans spans)
		throws IOException;

	public Spans getSpans(AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		return new PositionCheckSpan(context, acceptDocs, termContexts);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		SpanPositionCheckQuery clone = null;
		SpanQuery rewritten = (SpanQuery)match.rewrite(reader);
		if (rewritten != match)
		{
			clone = (SpanPositionCheckQuery)clone();
			clone.match = rewritten;
		}
		if (clone != null)
			return clone;
		else
			return this;
	}
}
