// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AtomicReaderContext.java

package org.apache.lucene.index;

import java.util.Collections;
import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			IndexReaderContext, CompositeReaderContext, AtomicReader, IndexReader

public final class AtomicReaderContext extends IndexReaderContext
{

	public final int ord;
	public final int docBase;
	private final AtomicReader reader;
	private final List leaves;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/AtomicReaderContext.desiredAssertionStatus();

	AtomicReaderContext(CompositeReaderContext parent, AtomicReader reader, int ord, int docBase, int leafOrd, int leafDocBase)
	{
		super(parent, ord, docBase);
		this.ord = leafOrd;
		this.docBase = leafDocBase;
		this.reader = reader;
		leaves = isTopLevel ? Collections.singletonList(this) : null;
	}

	AtomicReaderContext(AtomicReader atomicReader)
	{
		this(null, atomicReader, 0, 0, 0, 0);
	}

	public List leaves()
	{
		if (!isTopLevel)
			throw new UnsupportedOperationException("This is not a top-level context.");
		if (!$assertionsDisabled && leaves == null)
			throw new AssertionError();
		else
			return leaves;
	}

	public List children()
	{
		return null;
	}

	public AtomicReader reader()
	{
		return reader;
	}

	public volatile IndexReader reader()
	{
		return reader();
	}

}
