// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldMaskingSpanQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, Spans

public class FieldMaskingSpanQuery extends SpanQuery
{

	private SpanQuery maskedQuery;
	private String field;

	public FieldMaskingSpanQuery(SpanQuery maskedQuery, String maskedField)
	{
		this.maskedQuery = maskedQuery;
		field = maskedField;
	}

	public String getField()
	{
		return field;
	}

	public SpanQuery getMaskedQuery()
	{
		return maskedQuery;
	}

	public Spans getSpans(AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		return maskedQuery.getSpans(context, acceptDocs, termContexts);
	}

	public void extractTerms(Set terms)
	{
		maskedQuery.extractTerms(terms);
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return maskedQuery.createWeight(searcher);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		FieldMaskingSpanQuery clone = null;
		SpanQuery rewritten = (SpanQuery)maskedQuery.rewrite(reader);
		if (rewritten != maskedQuery)
		{
			clone = (FieldMaskingSpanQuery)clone();
			clone.maskedQuery = rewritten;
		}
		if (clone != null)
			return clone;
		else
			return this;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("mask(");
		buffer.append(maskedQuery.toString(field));
		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		buffer.append(" as ");
		buffer.append(this.field);
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof FieldMaskingSpanQuery))
		{
			return false;
		} else
		{
			FieldMaskingSpanQuery other = (FieldMaskingSpanQuery)o;
			return getField().equals(other.getField()) && getBoost() == other.getBoost() && getMaskedQuery().equals(other.getMaskedQuery());
		}
	}

	public int hashCode()
	{
		return getMaskedQuery().hashCode() ^ getField().hashCode() ^ Float.floatToRawIntBits(getBoost());
	}
}
