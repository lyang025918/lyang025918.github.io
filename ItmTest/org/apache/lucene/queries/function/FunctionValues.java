// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FunctionValues.java

package org.apache.lucene.queries.function;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueFloat;

// Referenced classes of package org.apache.lucene.queries.function:
//			ValueSourceScorer

public abstract class FunctionValues
{
	public static abstract class ValueFiller
	{

		public abstract MutableValue getValue();

		public abstract void fillValue(int i);

		public ValueFiller()
		{
		}
	}


	public FunctionValues()
	{
	}

	public byte byteVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public short shortVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public float floatVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public int intVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public long longVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public double doubleVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public String strVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public boolean boolVal(int doc)
	{
		return intVal(doc) != 0;
	}

	public boolean bytesVal(int doc, BytesRef target)
	{
		String s = strVal(doc);
		if (s == null)
		{
			target.length = 0;
			return false;
		} else
		{
			target.copyChars(s);
			return true;
		}
	}

	public Object objectVal(int doc)
	{
		return Float.valueOf(floatVal(doc));
	}

	public boolean exists(int doc)
	{
		return true;
	}

	public int ordVal(int doc)
	{
		throw new UnsupportedOperationException();
	}

	public int numOrd()
	{
		throw new UnsupportedOperationException();
	}

	public abstract String toString(int i);

	public ValueFiller getValueFiller()
	{
		return new ValueFiller() {

			private final MutableValueFloat mval = new MutableValueFloat();
			final FunctionValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.value = floatVal(doc);
			}

			
			{
				this$0 = FunctionValues.this;
				super();
			}
		};
	}

	public void byteVal(int doc, byte vals[])
	{
		throw new UnsupportedOperationException();
	}

	public void shortVal(int doc, short vals[])
	{
		throw new UnsupportedOperationException();
	}

	public void floatVal(int doc, float vals[])
	{
		throw new UnsupportedOperationException();
	}

	public void intVal(int doc, int vals[])
	{
		throw new UnsupportedOperationException();
	}

	public void longVal(int doc, long vals[])
	{
		throw new UnsupportedOperationException();
	}

	public void doubleVal(int doc, double vals[])
	{
		throw new UnsupportedOperationException();
	}

	public void strVal(int doc, String vals[])
	{
		throw new UnsupportedOperationException();
	}

	public Explanation explain(int doc)
	{
		return new Explanation(floatVal(doc), toString(doc));
	}

	public ValueSourceScorer getScorer(IndexReader reader)
	{
		return new ValueSourceScorer(reader, this);
	}

	public ValueSourceScorer getRangeScorer(final IndexReader reader, String lowerVal, String upperVal, boolean includeLower, boolean includeUpper)
	{
		float lower;
		if (lowerVal == null)
			lower = (-1.0F / 0.0F);
		else
			lower = Float.parseFloat(lowerVal);
		float upper;
		if (upperVal == null)
			upper = (1.0F / 0.0F);
		else
			upper = Float.parseFloat(upperVal);
		float l = lower;
		float u = upper;
		if (includeLower && includeUpper)
			return new ValueSourceScorer(l, u) {

				final float val$l;
				final float val$u;
				final FunctionValues this$0;

				public boolean matchesValue(int doc)
				{
					float docVal = floatVal(doc);
					return docVal >= l && docVal <= u;
				}

			
			{
				this$0 = FunctionValues.this;
				l = f;
				u = f1;
				super(x0, x1);
			}
			};
		if (includeLower && !includeUpper)
			return new ValueSourceScorer(l, u) {

				final float val$l;
				final float val$u;
				final FunctionValues this$0;

				public boolean matchesValue(int doc)
				{
					float docVal = floatVal(doc);
					return docVal >= l && docVal < u;
				}

			
			{
				this$0 = FunctionValues.this;
				l = f;
				u = f1;
				super(x0, x1);
			}
			};
		if (!includeLower && includeUpper)
			return new ValueSourceScorer(l, u) {

				final float val$l;
				final float val$u;
				final FunctionValues this$0;

				public boolean matchesValue(int doc)
				{
					float docVal = floatVal(doc);
					return docVal > l && docVal <= u;
				}

			
			{
				this$0 = FunctionValues.this;
				l = f;
				u = f1;
				super(x0, x1);
			}
			};
		else
			return new ValueSourceScorer(l, u) {

				final float val$l;
				final float val$u;
				final FunctionValues this$0;

				public boolean matchesValue(int doc)
				{
					float docVal = floatVal(doc);
					return docVal > l && docVal < u;
				}

			
			{
				this$0 = FunctionValues.this;
				l = f;
				u = f1;
				super(x0, x1);
			}
			};
	}
}
