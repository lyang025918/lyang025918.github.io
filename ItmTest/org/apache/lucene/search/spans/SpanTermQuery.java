// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanTermQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanQuery, TermSpans, Spans

public class SpanTermQuery extends SpanQuery
{

	protected Term term;

	public SpanTermQuery(Term term)
	{
		this.term = term;
	}

	public Term getTerm()
	{
		return term;
	}

	public String getField()
	{
		return term.field();
	}

	public void extractTerms(Set terms)
	{
		terms.add(term);
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (term.field().equals(field))
			buffer.append(term.text());
		else
			buffer.append(term.toString());
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (term != null ? term.hashCode() : 0);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpanTermQuery other = (SpanTermQuery)obj;
		if (term == null)
		{
			if (other.term != null)
				return false;
		} else
		if (!term.equals(other.term))
			return false;
		return true;
	}

	public Spans getSpans(AtomicReaderContext context, Bits acceptDocs, Map termContexts)
		throws IOException
	{
		TermContext termContext = (TermContext)termContexts.get(term);
		TermState state;
		if (termContext == null)
		{
			Fields fields = context.reader().fields();
			if (fields != null)
			{
				Terms terms = fields.terms(term.field());
				if (terms != null)
				{
					TermsEnum termsEnum = terms.iterator(null);
					if (termsEnum.seekExact(term.bytes(), true))
						state = termsEnum.termState();
					else
						state = null;
				} else
				{
					state = null;
				}
			} else
			{
				state = null;
			}
		} else
		{
			state = termContext.get(context.ord);
		}
		if (state == null)
			return TermSpans.EMPTY_TERM_SPANS;
		TermsEnum termsEnum = context.reader().terms(term.field()).iterator(null);
		termsEnum.seekExact(term.bytes(), state);
		DocsAndPositionsEnum postings = termsEnum.docsAndPositions(acceptDocs, null, 2);
		if (postings != null)
			return new TermSpans(postings, term);
		else
			throw new IllegalStateException((new StringBuilder()).append("field \"").append(term.field()).append("\" was indexed without position data; cannot run SpanTermQuery (term=").append(term.text()).append(")").toString());
	}
}
