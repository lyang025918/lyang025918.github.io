// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompositeReader.java

package org.apache.lucene.index;

import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			IndexReader, CompositeReaderContext, IndexReaderContext

public abstract class CompositeReader extends IndexReader
{

	private volatile CompositeReaderContext readerContext;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/CompositeReader.desiredAssertionStatus();

	protected CompositeReader()
	{
		readerContext = null;
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getSimpleName());
		buffer.append('(');
		List subReaders = getSequentialSubReaders();
		if (!$assertionsDisabled && subReaders == null)
			throw new AssertionError();
		if (!subReaders.isEmpty())
		{
			buffer.append(subReaders.get(0));
			int i = 1;
			for (int c = subReaders.size(); i < c; i++)
				buffer.append(" ").append(subReaders.get(i));

		}
		buffer.append(')');
		return buffer.toString();
	}

	protected abstract List getSequentialSubReaders();

	public final CompositeReaderContext getContext()
	{
		ensureOpen();
		if (readerContext == null)
		{
			if (!$assertionsDisabled && getSequentialSubReaders() == null)
				throw new AssertionError();
			readerContext = CompositeReaderContext.create(this);
		}
		return readerContext;
	}

	public volatile IndexReaderContext getContext()
	{
		return getContext();
	}

}
