// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexReaderContext.java

package org.apache.lucene.index;

import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			CompositeReaderContext, AtomicReaderContext, IndexReader

public abstract class IndexReaderContext
{

	public final CompositeReaderContext parent;
	public final boolean isTopLevel;
	public final int docBaseInParent;
	public final int ordInParent;

	IndexReaderContext(CompositeReaderContext parent, int ordInParent, int docBaseInParent)
	{
		if (!(this instanceof CompositeReaderContext) && !(this instanceof AtomicReaderContext))
		{
			throw new Error("This class should never be extended by custom code!");
		} else
		{
			this.parent = parent;
			this.docBaseInParent = docBaseInParent;
			this.ordInParent = ordInParent;
			isTopLevel = parent == null;
			return;
		}
	}

	public abstract IndexReader reader();

	public abstract List leaves()
		throws UnsupportedOperationException;

	public abstract List children();
}
