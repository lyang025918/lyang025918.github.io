// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanQuery.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanWeight, Spans

public abstract class SpanQuery extends Query
{

	public SpanQuery()
	{
	}

	public abstract Spans getSpans(AtomicReaderContext atomicreadercontext, Bits bits, Map map)
		throws IOException;

	public abstract String getField();

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new SpanWeight(this, searcher);
	}
}
