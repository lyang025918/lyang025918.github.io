// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanFirstQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanPositionRangeQuery, SpanQuery, Spans, SpanPositionCheckQuery

public class SpanFirstQuery extends SpanPositionRangeQuery
{

	static final boolean $assertionsDisabled = !org/apache/lucene/search/spans/SpanFirstQuery.desiredAssertionStatus();

	public SpanFirstQuery(SpanQuery match, int end)
	{
		super(match, 0, end);
	}

	protected SpanPositionCheckQuery.AcceptStatus acceptPosition(Spans spans)
		throws IOException
	{
		if (!$assertionsDisabled && spans.start() == spans.end())
			throw new AssertionError((new StringBuilder()).append("start equals end: ").append(spans.start()).toString());
		if (spans.start() >= end)
			return SpanPositionCheckQuery.AcceptStatus.NO_AND_ADVANCE;
		if (spans.end() <= end)
			return SpanPositionCheckQuery.AcceptStatus.YES;
		else
			return SpanPositionCheckQuery.AcceptStatus.NO;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("spanFirst(");
		buffer.append(match.toString(field));
		buffer.append(", ");
		buffer.append(end);
		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public SpanFirstQuery clone()
	{
		SpanFirstQuery spanFirstQuery = new SpanFirstQuery((SpanQuery)match.clone(), end);
		spanFirstQuery.setBoost(getBoost());
		return spanFirstQuery;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SpanFirstQuery))
		{
			return false;
		} else
		{
			SpanFirstQuery other = (SpanFirstQuery)o;
			return end == other.end && match.equals(other.match) && getBoost() == other.getBoost();
		}
	}

	public int hashCode()
	{
		int h = match.hashCode();
		h ^= h << 8 | h >>> 25;
		h ^= Float.floatToRawIntBits(getBoost()) ^ end;
		return h;
	}

	public volatile SpanPositionRangeQuery clone()
	{
		return clone();
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
