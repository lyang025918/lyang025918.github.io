// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Collector.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;

// Referenced classes of package org.apache.lucene.search:
//			Scorer

public abstract class Collector
{

	public Collector()
	{
	}

	public abstract void setScorer(Scorer scorer)
		throws IOException;

	public abstract void collect(int i)
		throws IOException;

	public abstract void setNextReader(AtomicReaderContext atomicreadercontext)
		throws IOException;

	public abstract boolean acceptsDocsOutOfOrder();
}
