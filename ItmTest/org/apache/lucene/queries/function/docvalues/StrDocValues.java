// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StrDocValues.java

package org.apache.lucene.queries.function.docvalues;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueStr;

public abstract class StrDocValues extends FunctionValues
{

	protected final ValueSource vs;

	public StrDocValues(ValueSource vs)
	{
		this.vs = vs;
	}

	public abstract String strVal(int i);

	public Object objectVal(int doc)
	{
		return exists(doc) ? strVal(doc) : null;
	}

	public boolean boolVal(int doc)
	{
		return exists(doc);
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append("='").append(strVal(doc)).append("'").toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueStr mval = new MutableValueStr();
			final StrDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.exists = bytesVal(doc, mval.value);
			}

			
			{
				this$0 = StrDocValues.this;
				super();
			}
		};
	}
}
