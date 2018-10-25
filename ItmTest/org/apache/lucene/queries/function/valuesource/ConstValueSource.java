// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConstValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			ConstNumberSource

public class ConstValueSource extends ConstNumberSource
{

	final float constant;
	private final double dv;

	public ConstValueSource(float constant)
	{
		this.constant = constant;
		dv = constant;
	}

	public String description()
	{
		return (new StringBuilder()).append("const(").append(constant).append(")").toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return new FloatDocValues(this) {

			final ConstValueSource this$0;

			public float floatVal(int doc)
			{
				return constant;
			}

			public int intVal(int doc)
			{
				return (int)constant;
			}

			public long longVal(int doc)
			{
				return (long)constant;
			}

			public double doubleVal(int doc)
			{
				return dv;
			}

			public String toString(int doc)
			{
				return description();
			}

			public Object objectVal(int doc)
			{
				return Float.valueOf(constant);
			}

			public boolean boolVal(int doc)
			{
				return constant != 0.0F;
			}

			
			{
				this$0 = ConstValueSource.this;
				super(x0);
			}
		};
	}

	public int hashCode()
	{
		return Float.floatToIntBits(constant) * 31;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof ConstValueSource))
		{
			return false;
		} else
		{
			ConstValueSource other = (ConstValueSource)o;
			return constant == other.constant;
		}
	}

	public int getInt()
	{
		return (int)constant;
	}

	public long getLong()
	{
		return (long)constant;
	}

	public float getFloat()
	{
		return constant;
	}

	public double getDouble()
	{
		return dv;
	}

	public Number getNumber()
	{
		return Float.valueOf(constant);
	}

	public boolean getBool()
	{
		return constant != 0.0F;
	}

}
