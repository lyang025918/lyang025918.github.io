// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JoinDocFreqValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class JoinDocFreqValueSource extends FieldCacheSource
{

	public static final String NAME = "joindf";
	protected final String qfield;

	public JoinDocFreqValueSource(String field, String qfield)
	{
		super(field);
		this.qfield = qfield;
	}

	public String description()
	{
		return (new StringBuilder()).append("joindf(").append(field).append(":(").append(qfield).append("))").toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final org.apache.lucene.search.FieldCache.DocTerms terms = cache.getTerms(readerContext.reader(), field, 0.5F);
		IndexReader top = ReaderUtil.getTopLevelContext(readerContext).reader();
		Terms t = MultiFields.getTerms(top, qfield);
		TermsEnum termsEnum = t != null ? t.iterator(null) : TermsEnum.EMPTY;
		return new IntDocValues(termsEnum) {

			final BytesRef ref = new BytesRef();
			final org.apache.lucene.search.FieldCache.DocTerms val$terms;
			final TermsEnum val$termsEnum;
			final JoinDocFreqValueSource this$0;

			public int intVal(int doc)
			{
				terms.getTerm(doc, ref);
				if (termsEnum.seekExact(ref, true))
					return termsEnum.docFreq();
				return 0;
				IOException e;
				e;
				throw new RuntimeException((new StringBuilder()).append("caught exception in function ").append(description()).append(" : doc=").append(doc).toString(), e);
			}

			
			{
				this$0 = JoinDocFreqValueSource.this;
				terms = docterms;
				termsEnum = termsenum;
				super(x0);
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != org/apache/lucene/queries/function/valuesource/JoinDocFreqValueSource)
			return false;
		JoinDocFreqValueSource other = (JoinDocFreqValueSource)o;
		if (!qfield.equals(other.qfield))
			return false;
		else
			return super.equals(other);
	}

	public int hashCode()
	{
		return qfield.hashCode() + super.hashCode();
	}
}
