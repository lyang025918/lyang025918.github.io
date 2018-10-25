// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScaleFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;

public class ScaleFloatFunction extends ValueSource
{
	private static class ScaleInfo
	{

		float minVal;
		float maxVal;

		private ScaleInfo()
		{
		}

	}


	protected final ValueSource source;
	protected final float min;
	protected final float max;

	public ScaleFloatFunction(ValueSource source, float min, float max)
	{
		this.source = source;
		this.min = min;
		this.max = max;
	}

	public String description()
	{
		return (new StringBuilder()).append("scale(").append(source.description()).append(",").append(min).append(",").append(max).append(")").toString();
	}

	private ScaleInfo createScaleInfo(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		List leaves = ReaderUtil.getTopLevelContext(readerContext).leaves();
		float minVal = (1.0F / 0.0F);
		float maxVal = (-1.0F / 0.0F);
		for (Iterator i$ = leaves.iterator(); i$.hasNext();)
		{
			AtomicReaderContext leaf = (AtomicReaderContext)i$.next();
			int maxDoc = leaf.reader().maxDoc();
			FunctionValues vals = source.getValues(context, leaf);
			int i = 0;
			while (i < maxDoc) 
			{
				float val = vals.floatVal(i);
				if ((Float.floatToRawIntBits(val) & 0x7f800000) != 0x7f800000)
				{
					if (val < minVal)
						minVal = val;
					if (val > maxVal)
						maxVal = val;
				}
				i++;
			}
		}

		if (minVal == (1.0F / 0.0F))
			minVal = maxVal = 0.0F;
		ScaleInfo scaleInfo = new ScaleInfo();
		scaleInfo.minVal = minVal;
		scaleInfo.maxVal = maxVal;
		context.put(source, scaleInfo);
		return scaleInfo;
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		ScaleInfo scaleInfo = (ScaleInfo)context.get(source);
		if (scaleInfo == null)
			scaleInfo = createScaleInfo(context, readerContext);
		final float scale = scaleInfo.maxVal - scaleInfo.minVal != 0.0F ? (max - min) / (scaleInfo.maxVal - scaleInfo.minVal) : 0.0F;
		final float minSource = scaleInfo.minVal;
		float maxSource = scaleInfo.maxVal;
		final FunctionValues vals = source.getValues(context, readerContext);
		return new FloatDocValues(maxSource) {

			final FunctionValues val$vals;
			final float val$minSource;
			final float val$scale;
			final float val$maxSource;
			final ScaleFloatFunction this$0;

			public float floatVal(int doc)
			{
				return (vals.floatVal(doc) - minSource) * scale + min;
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append("scale(").append(vals.toString(doc)).append(",toMin=").append(min).append(",toMax=").append(max).append(",fromMin=").append(minSource).append(",fromMax=").append(maxSource).append(")").toString();
			}

			
			{
				this$0 = ScaleFloatFunction.this;
				vals = functionvalues;
				minSource = f;
				scale = f1;
				maxSource = f2;
				super(x0);
			}
		};
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		source.createWeight(context, searcher);
	}

	public int hashCode()
	{
		int h = Float.floatToIntBits(min);
		h *= 29;
		h += Float.floatToIntBits(max);
		h *= 29;
		h += source.hashCode();
		return h;
	}

	public boolean equals(Object o)
	{
		if (org/apache/lucene/queries/function/valuesource/ScaleFloatFunction != o.getClass())
		{
			return false;
		} else
		{
			ScaleFloatFunction other = (ScaleFloatFunction)o;
			return min == other.min && max == other.max && source.equals(other.source);
		}
	}
}
