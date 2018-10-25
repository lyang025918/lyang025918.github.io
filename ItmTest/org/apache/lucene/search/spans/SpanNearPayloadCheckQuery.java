// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanNearPayloadCheckQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanPositionCheckQuery, SpanNearQuery, Spans, SpanQuery

public class SpanNearPayloadCheckQuery extends SpanPositionCheckQuery
{

	protected final Collection payloadToMatch;

	public SpanNearPayloadCheckQuery(SpanNearQuery match, Collection payloadToMatch)
	{
		super(match);
		this.payloadToMatch = payloadToMatch;
	}

	protected SpanPositionCheckQuery.AcceptStatus acceptPosition(Spans spans)
		throws IOException
	{
		boolean result = spans.isPayloadAvailable();
		if (result)
		{
			Collection candidate = spans.getPayload();
			if (candidate.size() == payloadToMatch.size())
			{
				int matches = 0;
				Iterator i$ = candidate.iterator();
label0:
				do
				{
					if (!i$.hasNext())
						break;
					byte candBytes[] = (byte[])i$.next();
					Iterator i$ = payloadToMatch.iterator();
					byte payBytes[];
					do
					{
						if (!i$.hasNext())
							continue label0;
						payBytes = (byte[])i$.next();
					} while (!Arrays.equals(candBytes, payBytes));
					matches++;
				} while (true);
				if (matches == payloadToMatch.size())
					return SpanPositionCheckQuery.AcceptStatus.YES;
				else
					return SpanPositionCheckQuery.AcceptStatus.NO;
			} else
			{
				return SpanPositionCheckQuery.AcceptStatus.NO;
			}
		} else
		{
			return SpanPositionCheckQuery.AcceptStatus.NO;
		}
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("spanPayCheck(");
		buffer.append(match.toString(field));
		buffer.append(", payloadRef: ");
		for (Iterator i$ = payloadToMatch.iterator(); i$.hasNext(); buffer.append(';'))
		{
			byte bytes[] = (byte[])i$.next();
			ToStringUtils.byteArray(buffer, bytes);
		}

		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public SpanNearPayloadCheckQuery clone()
	{
		SpanNearPayloadCheckQuery result = new SpanNearPayloadCheckQuery((SpanNearQuery)match.clone(), payloadToMatch);
		result.setBoost(getBoost());
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SpanNearPayloadCheckQuery))
		{
			return false;
		} else
		{
			SpanNearPayloadCheckQuery other = (SpanNearPayloadCheckQuery)o;
			return payloadToMatch.equals(other.payloadToMatch) && match.equals(other.match) && getBoost() == other.getBoost();
		}
	}

	public int hashCode()
	{
		int h = match.hashCode();
		h ^= h << 8 | h >>> 25;
		h ^= payloadToMatch.hashCode();
		h ^= Float.floatToRawIntBits(getBoost());
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
