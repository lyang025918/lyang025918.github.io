// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			MultiFunction

public class DefFunction extends MultiFunction
{

	public DefFunction(List sources)
	{
		super(sources);
	}

	protected String name()
	{
		return "def";
	}

	public FunctionValues getValues(Map fcontext, AtomicReaderContext readerContext)
		throws IOException
	{
		return new MultiFunction.Values(valsArr(sources, fcontext, readerContext)) {

			final int upto;
			final DefFunction this$0;

			private FunctionValues get(int doc)
			{
				for (int i = 0; i < upto; i++)
				{
					FunctionValues vals = valsArr[i];
					if (vals.exists(doc))
						return vals;
				}

				return valsArr[upto];
			}

			public byte byteVal(int doc)
			{
				return get(doc).byteVal(doc);
			}

			public short shortVal(int doc)
			{
				return get(doc).shortVal(doc);
			}

			public float floatVal(int doc)
			{
				return get(doc).floatVal(doc);
			}

			public int intVal(int doc)
			{
				return get(doc).intVal(doc);
			}

			public long longVal(int doc)
			{
				return get(doc).longVal(doc);
			}

			public double doubleVal(int doc)
			{
				return get(doc).doubleVal(doc);
			}

			public String strVal(int doc)
			{
				return get(doc).strVal(doc);
			}

			public boolean boolVal(int doc)
			{
				return get(doc).boolVal(doc);
			}

			public boolean bytesVal(int doc, BytesRef target)
			{
				return get(doc).bytesVal(doc, target);
			}

			public Object objectVal(int doc)
			{
				return get(doc).objectVal(doc);
			}

			public boolean exists(int doc)
			{
				FunctionValues arr$[] = valsArr;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					FunctionValues vals = arr$[i$];
					if (vals.exists(doc))
						return true;
				}

				return false;
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return super.getValueFiller();
			}

			
			{
				this$0 = DefFunction.this;
				super(DefFunction.this, x0);
				upto = valsArr.length - 1;
			}
		};
	}
}
