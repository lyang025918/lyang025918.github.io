// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.search.FieldCache;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class ByteFieldSource extends FieldCacheSource
{

	private final org.apache.lucene.search.FieldCache.ByteParser parser;

	public ByteFieldSource(String field)
	{
		this(field, null);
	}

	public ByteFieldSource(String field, org.apache.lucene.search.FieldCache.ByteParser parser)
	{
		super(field);
		this.parser = parser;
	}

	public String description()
	{
		return (new StringBuilder()).append("byte(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final byte arr[] = cache.getBytes(readerContext.reader(), field, parser, false);
		return new FunctionValues() {

			final byte val$arr[];
			final ByteFieldSource this$0;

			public byte byteVal(int doc)
			{
				return arr[doc];
			}

			public short shortVal(int doc)
			{
				return (short)arr[doc];
			}

			public float floatVal(int doc)
			{
				return (float)arr[doc];
			}

			public int intVal(int doc)
			{
				return arr[doc];
			}

			public long longVal(int doc)
			{
				return (long)arr[doc];
			}

			public double doubleVal(int doc)
			{
				return (double)arr[doc];
			}

			public String strVal(int doc)
			{
				return Byte.toString(arr[doc]);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(description()).append('=').append(byteVal(doc)).toString();
			}

			public Object objectVal(int doc)
			{
				return Byte.valueOf(arr[doc]);
			}

			
			{
				this$0 = ByteFieldSource.this;
				arr = abyte0;
				super();
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != org/apache/lucene/queries/function/valuesource/ByteFieldSource)
		{
			return false;
		} else
		{
			ByteFieldSource other = (ByteFieldSource)o;
			return super.equals(other) && (parser != null ? parser.getClass() == other.parser.getClass() : other.parser == null);
		}
	}

	public int hashCode()
	{
		int h = parser != null ? parser.getClass().hashCode() : java/lang/Byte.hashCode();
		h += super.hashCode();
		return h;
	}
}
