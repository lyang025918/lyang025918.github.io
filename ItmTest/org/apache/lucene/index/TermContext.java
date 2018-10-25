// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermContext.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			TermState, AtomicReaderContext, IndexReaderContext, Term, 
//			AtomicReader, Fields, Terms, TermsEnum

public final class TermContext
{

	public final IndexReaderContext topReaderContext;
	private final TermState states[];
	private int docFreq;
	private long totalTermFreq;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/TermContext.desiredAssertionStatus();

	public TermContext(IndexReaderContext context)
	{
		if (!$assertionsDisabled && (context == null || !context.isTopLevel))
			throw new AssertionError();
		topReaderContext = context;
		docFreq = 0;
		int len;
		if (context.leaves() == null)
			len = 1;
		else
			len = context.leaves().size();
		states = new TermState[len];
	}

	public TermContext(IndexReaderContext context, TermState state, int ord, int docFreq, long totalTermFreq)
	{
		this(context);
		register(state, ord, docFreq, totalTermFreq);
	}

	public static TermContext build(IndexReaderContext context, Term term, boolean cache)
		throws IOException
	{
		if (!$assertionsDisabled && (context == null || !context.isTopLevel))
			throw new AssertionError();
		String field = term.field();
		BytesRef bytes = term.bytes();
		TermContext perReaderTermState = new TermContext(context);
		Iterator i$ = context.leaves().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			AtomicReaderContext ctx = (AtomicReaderContext)i$.next();
			Fields fields = ctx.reader().fields();
			if (fields != null)
			{
				Terms terms = fields.terms(field);
				if (terms != null)
				{
					TermsEnum termsEnum = terms.iterator(null);
					if (termsEnum.seekExact(bytes, cache))
					{
						TermState termState = termsEnum.termState();
						perReaderTermState.register(termState, ctx.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
					}
				}
			}
		} while (true);
		return perReaderTermState;
	}

	public void clear()
	{
		docFreq = 0;
		Arrays.fill(states, null);
	}

	public void register(TermState state, int ord, int docFreq, long totalTermFreq)
	{
		if (!$assertionsDisabled && state == null)
			throw new AssertionError("state must not be null");
		if (!$assertionsDisabled && (ord < 0 || ord >= states.length))
			throw new AssertionError();
		if (!$assertionsDisabled && states[ord] != null)
			throw new AssertionError((new StringBuilder()).append("state for ord: ").append(ord).append(" already registered").toString());
		this.docFreq += docFreq;
		if (this.totalTermFreq >= 0L && totalTermFreq >= 0L)
			this.totalTermFreq += totalTermFreq;
		else
			this.totalTermFreq = -1L;
		states[ord] = state;
	}

	public TermState get(int ord)
	{
		if (!$assertionsDisabled && (ord < 0 || ord >= states.length))
			throw new AssertionError();
		else
			return states[ord];
	}

	public int docFreq()
	{
		return docFreq;
	}

	public long totalTermFreq()
	{
		return totalTermFreq;
	}

	public void setDocFreq(int docFreq)
	{
		this.docFreq = docFreq;
	}

}
