// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompositeReaderContext.java

package org.apache.lucene.index;

import java.util.*;

// Referenced classes of package org.apache.lucene.index:
//			IndexReaderContext, CompositeReader, IndexReader, AtomicReader, 
//			AtomicReaderContext

public final class CompositeReaderContext extends IndexReaderContext
{
	private static final class Builder
	{

		private final CompositeReader reader;
		private final List leaves = new ArrayList();
		private int leafDocBase;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/CompositeReaderContext.desiredAssertionStatus();

		public CompositeReaderContext build()
		{
			return (CompositeReaderContext)build(null, ((IndexReader) (reader)), 0, 0);
		}

		private IndexReaderContext build(CompositeReaderContext parent, IndexReader reader, int ord, int docBase)
		{
			if (reader instanceof AtomicReader)
			{
				AtomicReader ar = (AtomicReader)reader;
				AtomicReaderContext atomic = new AtomicReaderContext(parent, ar, ord, docBase, leaves.size(), leafDocBase);
				leaves.add(atomic);
				leafDocBase += reader.maxDoc();
				return atomic;
			}
			CompositeReader cr = (CompositeReader)reader;
			List sequentialSubReaders = cr.getSequentialSubReaders();
			List children = Arrays.asList(new IndexReaderContext[sequentialSubReaders.size()]);
			CompositeReaderContext newParent;
			if (parent == null)
				newParent = new CompositeReaderContext(cr, children, leaves);
			else
				newParent = new CompositeReaderContext(parent, cr, ord, docBase, children);
			int newDocBase = 0;
			int i = 0;
			for (int c = sequentialSubReaders.size(); i < c; i++)
			{
				IndexReader r = (IndexReader)sequentialSubReaders.get(i);
				children.set(i, build(newParent, r, i, newDocBase));
				newDocBase += r.maxDoc();
			}

			if (!$assertionsDisabled && newDocBase != cr.maxDoc())
				throw new AssertionError();
			else
				return newParent;
		}


		public Builder(CompositeReader reader)
		{
			leafDocBase = 0;
			this.reader = reader;
		}
	}


	private final List children;
	private final List leaves;
	private final CompositeReader reader;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/CompositeReaderContext.desiredAssertionStatus();

	static CompositeReaderContext create(CompositeReader reader)
	{
		return (new Builder(reader)).build();
	}

	CompositeReaderContext(CompositeReaderContext parent, CompositeReader reader, int ordInParent, int docbaseInParent, List children)
	{
		this(parent, reader, ordInParent, docbaseInParent, children, null);
	}

	CompositeReaderContext(CompositeReader reader, List children, List leaves)
	{
		this(null, reader, 0, 0, children, leaves);
	}

	private CompositeReaderContext(CompositeReaderContext parent, CompositeReader reader, int ordInParent, int docbaseInParent, List children, List leaves)
	{
		super(parent, ordInParent, docbaseInParent);
		this.children = Collections.unmodifiableList(children);
		this.leaves = leaves != null ? Collections.unmodifiableList(leaves) : null;
		this.reader = reader;
	}

	public List leaves()
		throws UnsupportedOperationException
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
		return children;
	}

	public CompositeReader reader()
	{
		return reader;
	}

	public volatile IndexReader reader()
	{
		return reader();
	}

}
