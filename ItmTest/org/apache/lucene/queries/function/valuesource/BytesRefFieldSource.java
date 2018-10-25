// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BytesRefFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DocTermsIndexDocValues;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class BytesRefFieldSource extends FieldCacheSource
{

	public BytesRefFieldSource(String field)
	{
		super(field);
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return new DocTermsIndexDocValues(this, readerContext, field) {

			final BytesRefFieldSource this$0;

			protected String toTerm(String readableValue)
			{
				return readableValue;
			}

			public Object objectVal(int doc)
			{
				return strVal(doc);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(description()).append('=').append(strVal(doc)).toString();
			}

			
			{
				this$0 = BytesRefFieldSource.this;
				super(x0, x1, x2);
			}
		};
	}
}
