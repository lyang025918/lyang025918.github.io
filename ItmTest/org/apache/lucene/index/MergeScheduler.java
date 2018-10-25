// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MergeScheduler.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			IndexWriter

public abstract class MergeScheduler
	implements Closeable
{

	protected MergeScheduler()
	{
	}

	public abstract void merge(IndexWriter indexwriter)
		throws IOException;

	public abstract void close()
		throws IOException;
}
