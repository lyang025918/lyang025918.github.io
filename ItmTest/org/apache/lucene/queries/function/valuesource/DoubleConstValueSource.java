// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DoubleConstValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			ConstNumberSource

public class DoubleConstValueSource extends ConstNumberSource
{

	final double constant;
	private final float fv;
	private final long lv;

	public DoubleConstValueSource(double constant)
	{
		this.constant = constant;
		fv = (float)constant;
		lv = (long)constant;
	}

	public String description()
	{
		return (new StringBuilder()).append("const(").append(constant).append(")").toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return new DoubleDocValues(this) {

			final DoubleConstValueSource this$0;

			public float floatVal(int doc)
			{
				return fv;
			}

			public int intVal(int doc)
			{
				return (int)lv;
			}

			public long longVal(int doc)
			{
				return lv;
			}

			public double doubleVal(int doc)
			{
				return constant;
			}

			public String strVal(int doc)
			{
				return Double.toString(constant);
			}

			public Object objectVal(int doc)
			{
				return Double.valueOf(constant);
			}

			public String toString(int doc)
			{
				return description();
			}

			
			{
				this$0 = DoubleConstValueSource.this;
				super(x0);
			}
		};
	}

	public int hashCode()
	{
		long bits = Double.doubleToRawLongBits(constant);
		return (int)(bits ^ bits >>> 32);
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof DoubleConstValueSource))
		{
			return false;
		} else
		{
			DoubleConstValueSource other = (DoubleConstValueSource)o;
			return constant == other.constant;
		}
	}

	public int getInt()
	{
		return (int)lv;
	}

	public long getLong()
	{
		return lv;
	}

	public float getFloat()
	{
		return fv;
	}

	public double getDouble()
	{
		return constant;
	}

	public Number getNumber()
	{
		return Double.valueOf(constant);
	}

	public boolean getBool()
	{
		return constant != 0.0D;
	}


}
