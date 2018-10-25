// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Filter.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			DocIdSet

public abstract class Filter
{

	public Filter()
	{
	}

	public abstract DocIdSet getDocIdSet(AtomicReaderContext atomicreadercontext, Bits bits)
		throws IOException;
}
