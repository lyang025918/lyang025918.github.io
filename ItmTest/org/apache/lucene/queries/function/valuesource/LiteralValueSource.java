// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LiteralValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.StrDocValues;
import org.apache.lucene.util.BytesRef;

public class LiteralValueSource extends ValueSource
{

	protected final String string;
	protected final BytesRef bytesRef;
	public static final int hash = org/apache/lucene/queries/function/valuesource/LiteralValueSource.hashCode();

	public LiteralValueSource(String string)
	{
		this.string = string;
		bytesRef = new BytesRef(string);
	}

	public String getValue()
	{
		return string;
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return new StrDocValues(this) {

			final LiteralValueSource this$0;

			public String strVal(int doc)
			{
				return string;
			}

			public boolean bytesVal(int doc, BytesRef target)
			{
				target.copyBytes(bytesRef);
				return true;
			}

			public String toString(int doc)
			{
				return string;
			}

			
			{
				this$0 = LiteralValueSource.this;
				super(x0);
			}
		};
	}

	public String description()
	{
		return (new StringBuilder()).append("literal(").append(string).append(")").toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof LiteralValueSource))
		{
			return false;
		} else
		{
			LiteralValueSource that = (LiteralValueSource)o;
			return string.equals(that.string);
		}
	}

	public int hashCode()
	{
		return hash + string.hashCode();
	}

}
