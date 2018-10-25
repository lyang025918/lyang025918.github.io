// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumDocsValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			ConstIntDocValues

public class NumDocsValueSource extends ValueSource
{

	public NumDocsValueSource()
	{
	}

	public String name()
	{
		return "numdocs";
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append("()").toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return new ConstIntDocValues(ReaderUtil.getTopLevelContext(readerContext).reader().numDocs(), this);
	}

	public boolean equals(Object o)
	{
		return getClass() == o.getClass();
	}

	public int hashCode()
	{
		return getClass().hashCode();
	}
}
