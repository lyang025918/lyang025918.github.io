// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanPositionRangeQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanPositionCheckQuery, SpanQuery, Spans

public class SpanPositionRangeQuery extends SpanPositionCheckQuery
{

	protected int start;
	protected int end;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/spans/SpanPositionRangeQuery.desiredAssertionStatus();

	public SpanPositionRangeQuery(SpanQuery match, int start, int end)
	{
		super(match);
		this.start = 0;
		this.start = start;
		this.end = end;
	}

	protected SpanPositionCheckQuery.AcceptStatus acceptPosition(Spans spans)
		throws IOException
	{
		if (!$assertionsDisabled && spans.start() == spans.end())
			throw new AssertionError();
		if (spans.start() >= end)
			return SpanPositionCheckQuery.AcceptStatus.NO_AND_ADVANCE;
		if (spans.start() >= start && spans.end() <= end)
			return SpanPositionCheckQuery.AcceptStatus.YES;
		else
			return SpanPositionCheckQuery.AcceptStatus.NO;
	}

	public int getStart()
	{
		return start;
	}

	public int getEnd()
	{
		return end;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("spanPosRange(");
		buffer.append(match.toString(field));
		buffer.append(", ").append(start).append(", ");
		buffer.append(end);
		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public SpanPositionRangeQuery clone()
	{
		SpanPositionRangeQuery result = new SpanPositionRangeQuery((SpanQuery)match.clone(), start, end);
		result.setBoost(getBoost());
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SpanPositionRangeQuery))
		{
			return false;
		} else
		{
			SpanPositionRangeQuery other = (SpanPositionRangeQuery)o;
			return end == other.end && start == other.start && match.equals(other.match) && getBoost() == other.getBoost();
		}
	}

	public int hashCode()
	{
		int h = match.hashCode();
		h ^= h << 8 | h >>> 25;
		h ^= Float.floatToRawIntBits(getBoost()) ^ end ^ start;
		return h;
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
