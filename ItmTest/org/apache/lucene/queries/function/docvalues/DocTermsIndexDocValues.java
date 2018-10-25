// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocTermsIndexDocValues.java

package org.apache.lucene.queries.function.docvalues;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.*;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.*;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueStr;

public abstract class DocTermsIndexDocValues extends FunctionValues
{
	public static final class DocTermsIndexException extends RuntimeException
	{

		public DocTermsIndexException(String fieldName, RuntimeException cause)
		{
			super((new StringBuilder()).append("Can't initialize DocTermsIndex to generate (function) FunctionValues for field: ").append(fieldName).toString(), cause);
		}
	}


	protected final org.apache.lucene.search.FieldCache.DocTermsIndex termsIndex;
	protected final ValueSource vs;
	protected final MutableValueStr val = new MutableValueStr();
	protected final BytesRef spare = new BytesRef();
	protected final CharsRef spareChars = new CharsRef();

	public DocTermsIndexDocValues(ValueSource vs, AtomicReaderContext context, String field)
		throws IOException
	{
		try
		{
			termsIndex = FieldCache.DEFAULT.getTermsIndex(context.reader(), field);
		}
		catch (RuntimeException e)
		{
			throw new DocTermsIndexException(field, e);
		}
		this.vs = vs;
	}

	public org.apache.lucene.search.FieldCache.DocTermsIndex getDocTermsIndex()
	{
		return termsIndex;
	}

	protected abstract String toTerm(String s);

	public boolean exists(int doc)
	{
		return termsIndex.getOrd(doc) != 0;
	}

	public boolean bytesVal(int doc, BytesRef target)
	{
		int ord = termsIndex.getOrd(doc);
		if (ord == 0)
		{
			target.length = 0;
			return false;
		} else
		{
			termsIndex.lookup(ord, target);
			return true;
		}
	}

	public String strVal(int doc)
	{
		int ord = termsIndex.getOrd(doc);
		if (ord == 0)
		{
			return null;
		} else
		{
			termsIndex.lookup(ord, spare);
			UnicodeUtil.UTF8toUTF16(spare, spareChars);
			return spareChars.toString();
		}
	}

	public boolean boolVal(int doc)
	{
		return exists(doc);
	}

	public abstract Object objectVal(int i);

	public ValueSourceScorer getRangeScorer(final IndexReader reader, String lowerVal, String upperVal, boolean includeLower, boolean includeUpper)
	{
		lowerVal = lowerVal != null ? toTerm(lowerVal) : null;
		upperVal = upperVal != null ? toTerm(upperVal) : null;
		BytesRef spare = new BytesRef();
		int lower = 0x80000000;
		if (lowerVal != null)
		{
			lower = termsIndex.binarySearchLookup(new BytesRef(lowerVal), spare);
			if (lower < 0)
				lower = -lower - 1;
			else
			if (!includeLower)
				lower++;
		}
		int upper = 0x7fffffff;
		if (upperVal != null)
		{
			upper = termsIndex.binarySearchLookup(new BytesRef(upperVal), spare);
			if (upper < 0)
				upper = -upper - 2;
			else
			if (!includeUpper)
				upper--;
		}
		int ll = lower;
		int uu = upper;
		return new ValueSourceScorer(ll, uu) {

			final int val$ll;
			final int val$uu;
			final DocTermsIndexDocValues this$0;

			public boolean matchesValue(int doc)
			{
				int ord = termsIndex.getOrd(doc);
				return ord >= ll && ord <= uu;
			}

			
			{
				this$0 = DocTermsIndexDocValues.this;
				ll = i;
				uu = j;
				super(x0, x1);
			}
		};
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append('=').append(strVal(doc)).toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueStr mval = new MutableValueStr();
			final DocTermsIndexDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				int ord = termsIndex.getOrd(doc);
				mval.exists = ord != 0;
				mval.value = termsIndex.lookup(ord, mval.value);
			}

			
			{
				this$0 = DocTermsIndexDocValues.this;
				super();
			}
		};
	}
}
