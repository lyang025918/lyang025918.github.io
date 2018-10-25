// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Weight.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			Explanation, Query, Scorer

public abstract class Weight
{

	public Weight()
	{
	}

	public abstract Explanation explain(AtomicReaderContext atomicreadercontext, int i)
		throws IOException;

	public abstract Query getQuery();

	public abstract float getValueForNormalization()
		throws IOException;

	public abstract void normalize(float f, float f1);

	public abstract Scorer scorer(AtomicReaderContext atomicreadercontext, boolean flag, boolean flag1, Bits bits)
		throws IOException;

	public boolean scoresDocsOutOfOrder()
	{
		return false;
	}
}
