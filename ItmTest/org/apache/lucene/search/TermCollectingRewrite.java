// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermCollectingRewrite.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQuery, Query

abstract class TermCollectingRewrite extends MultiTermQuery.RewriteMethod
{
	static abstract class TermCollector
	{

		protected AtomicReaderContext readerContext;
		protected IndexReaderContext topReaderContext;
		public final AttributeSource attributes = new AttributeSource();

		public void setReaderContext(IndexReaderContext topReaderContext, AtomicReaderContext readerContext)
		{
			this.readerContext = readerContext;
			this.topReaderContext = topReaderContext;
		}

		public abstract boolean collect(BytesRef bytesref)
			throws IOException;

		public abstract void setNextEnum(TermsEnum termsenum)
			throws IOException;

		TermCollector()
		{
		}
	}


	static final boolean $assertionsDisabled = !org/apache/lucene/search/TermCollectingRewrite.desiredAssertionStatus();

	TermCollectingRewrite()
	{
	}

	protected abstract Query getTopLevelQuery()
		throws IOException;

	protected final void addClause(Query topLevel, Term term, int docCount, float boost)
		throws IOException
	{
		addClause(topLevel, term, docCount, boost, null);
	}

	protected abstract void addClause(Query query, Term term, int i, float f, TermContext termcontext)
		throws IOException;

	final void collectTerms(IndexReader reader, MultiTermQuery query, TermCollector collector)
		throws IOException
	{
		IndexReaderContext topReaderContext;
		Comparator lastTermComp;
		Iterator i$;
		topReaderContext = reader.getContext();
		lastTermComp = null;
		i$ = topReaderContext.leaves().iterator();
_L2:
		TermsEnum termsEnum;
		AtomicReaderContext context;
		do
		{
			Terms terms;
			do
			{
				Fields fields;
				do
				{
					if (!i$.hasNext())
						break MISSING_BLOCK_LABEL_227;
					context = (AtomicReaderContext)i$.next();
					fields = context.reader().fields();
				} while (fields == null);
				terms = fields.terms(query.field);
			} while (terms == null);
			termsEnum = getTermsEnum(query, terms, collector.attributes);
			if (!$assertionsDisabled && termsEnum == null)
				throw new AssertionError();
		} while (termsEnum == TermsEnum.EMPTY);
		Comparator newTermComp = termsEnum.getComparator();
		if (lastTermComp != null && newTermComp != null && newTermComp != lastTermComp)
			throw new RuntimeException((new StringBuilder()).append("term comparator should not change between segments: ").append(lastTermComp).append(" != ").append(newTermComp).toString());
		lastTermComp = newTermComp;
		collector.setReaderContext(topReaderContext, context);
		collector.setNextEnum(termsEnum);
_L4:
		BytesRef bytes;
		if ((bytes = termsEnum.next()) == null) goto _L2; else goto _L1
_L1:
		if (collector.collect(bytes)) goto _L4; else goto _L3
_L3:
		return;
	}

}
