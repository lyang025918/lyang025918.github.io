// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericIndexDocValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;

public class NumericIndexDocValueSource extends ValueSource
{

	private final String field;

	public NumericIndexDocValueSource(String field)
	{
		this.field = field;
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final org.apache.lucene.index.DocValues.Source source = readerContext.reader().docValues(field).getSource();
		org.apache.lucene.index.DocValues.Type type = source.getType();
		static class 3
		{

			static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[org.apache.lucene.index.DocValues.Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_32.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_64.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.VAR_INTS.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (3..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
		case 2: // '\002'
			return new FunctionValues() {

				final org.apache.lucene.index.DocValues.Source val$source;
				final NumericIndexDocValueSource this$0;

				public String toString(int doc)
				{
					return (new StringBuilder()).append("float: [").append(floatVal(doc)).append("]").toString();
				}

				public float floatVal(int doc)
				{
					return (float)source.getFloat(doc);
				}

			
			{
				this$0 = NumericIndexDocValueSource.this;
				source = source1;
				super();
			}
			};

		case 3: // '\003'
			return new FunctionValues() {

				final org.apache.lucene.index.DocValues.Source val$source;
				final NumericIndexDocValueSource this$0;

				public String toString(int doc)
				{
					return (new StringBuilder()).append("float: [").append(floatVal(doc)).append("]").toString();
				}

				public float floatVal(int doc)
				{
					return (float)source.getInt(doc);
				}

			
			{
				this$0 = NumericIndexDocValueSource.this;
				source = source1;
				super();
			}
			};
		}
		throw new IOException((new StringBuilder()).append("Type: ").append(type).append("is not numeric").toString());
	}

	public String description()
	{
		return toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (field != null ? field.hashCode() : 0);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumericIndexDocValueSource other = (NumericIndexDocValueSource)obj;
		if (field == null)
		{
			if (other.field != null)
				return false;
		} else
		if (!field.equals(other.field))
			return false;
		return true;
	}

	public String toString()
	{
		return (new StringBuilder()).append("FunctionValues float(").append(field).append(')').toString();
	}
}
