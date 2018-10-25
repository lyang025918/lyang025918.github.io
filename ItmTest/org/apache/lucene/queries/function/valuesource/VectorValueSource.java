// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VectorValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			MultiValueSource

public class VectorValueSource extends MultiValueSource
{

	protected final List sources;

	public VectorValueSource(List sources)
	{
		this.sources = sources;
	}

	public List getSources()
	{
		return sources;
	}

	public int dimension()
	{
		return sources.size();
	}

	public String name()
	{
		return "vector";
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		int size = sources.size();
		if (size == 2)
		{
			final FunctionValues x = ((ValueSource)sources.get(0)).getValues(context, readerContext);
			final FunctionValues y = ((ValueSource)sources.get(1)).getValues(context, readerContext);
			return new FunctionValues() {

				final FunctionValues val$x;
				final FunctionValues val$y;
				final VectorValueSource this$0;

				public void byteVal(int doc, byte vals[])
				{
					vals[0] = x.byteVal(doc);
					vals[1] = y.byteVal(doc);
				}

				public void shortVal(int doc, short vals[])
				{
					vals[0] = x.shortVal(doc);
					vals[1] = y.shortVal(doc);
				}

				public void intVal(int doc, int vals[])
				{
					vals[0] = x.intVal(doc);
					vals[1] = y.intVal(doc);
				}

				public void longVal(int doc, long vals[])
				{
					vals[0] = x.longVal(doc);
					vals[1] = y.longVal(doc);
				}

				public void floatVal(int doc, float vals[])
				{
					vals[0] = x.floatVal(doc);
					vals[1] = y.floatVal(doc);
				}

				public void doubleVal(int doc, double vals[])
				{
					vals[0] = x.doubleVal(doc);
					vals[1] = y.doubleVal(doc);
				}

				public void strVal(int doc, String vals[])
				{
					vals[0] = x.strVal(doc);
					vals[1] = y.strVal(doc);
				}

				public String toString(int doc)
				{
					return (new StringBuilder()).append(name()).append("(").append(x.toString(doc)).append(",").append(y.toString(doc)).append(")").toString();
				}

			
			{
				this$0 = VectorValueSource.this;
				x = functionvalues;
				y = functionvalues1;
				super();
			}
			};
		}
		final FunctionValues valsArr[] = new FunctionValues[size];
		for (int i = 0; i < size; i++)
			valsArr[i] = ((ValueSource)sources.get(i)).getValues(context, readerContext);

		return new FunctionValues() {

			final FunctionValues val$valsArr[];
			final VectorValueSource this$0;

			public void byteVal(int doc, byte vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].byteVal(doc);

			}

			public void shortVal(int doc, short vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].shortVal(doc);

			}

			public void floatVal(int doc, float vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].floatVal(doc);

			}

			public void intVal(int doc, int vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].intVal(doc);

			}

			public void longVal(int doc, long vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].longVal(doc);

			}

			public void doubleVal(int doc, double vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].doubleVal(doc);

			}

			public void strVal(int doc, String vals[])
			{
				for (int i = 0; i < valsArr.length; i++)
					vals[i] = valsArr[i].strVal(doc);

			}

			public String toString(int doc)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(name()).append('(');
				boolean firstTime = true;
				FunctionValues arr$[] = valsArr;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					FunctionValues vals = arr$[i$];
					if (firstTime)
						firstTime = false;
					else
						sb.append(',');
					sb.append(vals.toString(doc));
				}

				sb.append(')');
				return sb.toString();
			}

			
			{
				this$0 = VectorValueSource.this;
				valsArr = afunctionvalues;
				super();
			}
		};
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		ValueSource source;
		for (Iterator i$ = sources.iterator(); i$.hasNext(); source.createWeight(context, searcher))
			source = (ValueSource)i$.next();

	}

	public String description()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name()).append('(');
		boolean firstTime = true;
		ValueSource source;
		for (Iterator i$ = sources.iterator(); i$.hasNext(); sb.append(source))
		{
			source = (ValueSource)i$.next();
			if (firstTime)
				firstTime = false;
			else
				sb.append(',');
		}

		sb.append(")");
		return sb.toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof VectorValueSource))
		{
			return false;
		} else
		{
			VectorValueSource that = (VectorValueSource)o;
			return sources.equals(that.sources);
		}
	}

	public int hashCode()
	{
		return sources.hashCode();
	}
}
