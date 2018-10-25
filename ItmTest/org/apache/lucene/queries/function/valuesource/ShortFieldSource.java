// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ShortFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.search.FieldCache;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class ShortFieldSource extends FieldCacheSource
{

	final org.apache.lucene.search.FieldCache.ShortParser parser;

	public ShortFieldSource(String field)
	{
		this(field, null);
	}

	public ShortFieldSource(String field, org.apache.lucene.search.FieldCache.ShortParser parser)
	{
		super(field);
		this.parser = parser;
	}

	public String description()
	{
		return (new StringBuilder()).append("short(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final short arr[] = cache.getShorts(readerContext.reader(), field, parser, false);
		return new FunctionValues() {

			final short val$arr[];
			final ShortFieldSource this$0;

			public byte byteVal(int doc)
			{
				return (byte)arr[doc];
			}

			public short shortVal(int doc)
			{
				return arr[doc];
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
				return Short.toString(arr[doc]);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(description()).append('=').append(shortVal(doc)).toString();
			}

			
			{
				this$0 = ShortFieldSource.this;
				arr = aword0;
				super();
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != org/apache/lucene/queries/function/valuesource/ShortFieldSource)
		{
			return false;
		} else
		{
			ShortFieldSource other = (ShortFieldSource)o;
			return super.equals(other) && (parser != null ? parser.getClass() == other.parser.getClass() : other.parser == null);
		}
	}

	public int hashCode()
	{
		int h = parser != null ? parser.getClass().hashCode() : java/lang/Short.hashCode();
		h += super.hashCode();
		return h;
	}
}
