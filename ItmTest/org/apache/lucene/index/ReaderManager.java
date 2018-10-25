// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReaderManager.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.store.Directory;

// Referenced classes of package org.apache.lucene.index:
//			DirectoryReader, IndexWriter

public final class ReaderManager extends ReferenceManager
{

	public ReaderManager(IndexWriter writer, boolean applyAllDeletes)
		throws IOException
	{
		current = DirectoryReader.open(writer, applyAllDeletes);
	}

	public ReaderManager(Directory dir)
		throws IOException
	{
		current = DirectoryReader.open(dir);
	}

	protected void decRef(DirectoryReader reference)
		throws IOException
	{
		reference.decRef();
	}

	protected DirectoryReader refreshIfNeeded(DirectoryReader referenceToRefresh)
		throws IOException
	{
		return DirectoryReader.openIfChanged(referenceToRefresh);
	}

	protected boolean tryIncRef(DirectoryReader reference)
	{
		return reference.tryIncRef();
	}

	protected volatile boolean tryIncRef(Object x0)
	{
		return tryIncRef((DirectoryReader)x0);
	}

	protected volatile Object refreshIfNeeded(Object x0)
		throws IOException
	{
		return refreshIfNeeded((DirectoryReader)x0);
	}

	protected volatile void decRef(Object x0)
		throws IOException
	{
		decRef((DirectoryReader)x0);
	}
}
