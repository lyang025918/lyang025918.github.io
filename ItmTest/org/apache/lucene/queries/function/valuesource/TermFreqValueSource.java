// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermFreqValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			DocFreqValueSource

public class TermFreqValueSource extends DocFreqValueSource
{

	public TermFreqValueSource(String field, String val, String indexedField, BytesRef indexedBytes)
	{
		super(field, val, indexedField, indexedBytes);
	}

	public String name()
	{
		return "termfreq";
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		Fields fields = readerContext.reader().fields();
		Terms terms = fields.terms(indexedField);
		return new IntDocValues(terms) {

			DocsEnum docs;
			int atDoc;
			int lastDocRequested;
			final Terms val$terms;
			final TermFreqValueSource this$0;

			public void reset()
				throws IOException
			{
				if (terms != null)
				{
					TermsEnum termsEnum = terms.iterator(null);
					if (termsEnum.seekExact(indexedBytes, false))
						docs = termsEnum.docs(null, null);
					else
						docs = null;
				} else
				{
					docs = null;
				}
				if (docs == null)
					docs = new DocsEnum() {

						final 1 this$1;

						public int freq()
						{
							return 0;
						}

						public int docID()
						{
							return 0x7fffffff;
						}

						public int nextDoc()
						{
							return 0x7fffffff;
						}

						public int advance(int target)
						{
							return 0x7fffffff;
						}

					
					{
						this$1 = 1.this;
						super();
					}
					};
				atDoc = -1;
			}

			public int intVal(int doc)
			{
				if (doc < lastDocRequested)
					reset();
				lastDocRequested = doc;
				if (atDoc < doc)
					atDoc = docs.advance(doc);
				if (atDoc > doc)
					return 0;
				return docs.freq();
				IOException e;
				e;
				throw new RuntimeException((new StringBuilder()).append("caught exception in function ").append(description()).append(" : doc=").append(doc).toString(), e);
			}

			
				throws IOException
			{
				this$0 = TermFreqValueSource.this;
				terms = terms1;
				super(x0);
				lastDocRequested = -1;
				reset();
			}
		};
	}
}
