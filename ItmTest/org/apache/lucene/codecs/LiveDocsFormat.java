// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LiveDocsFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;
import java.util.Collection;
import org.apache.lucene.index.SegmentInfoPerCommit;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.MutableBits;

public abstract class LiveDocsFormat
{

	protected LiveDocsFormat()
	{
	}

	public abstract MutableBits newLiveDocs(int i)
		throws IOException;

	public abstract MutableBits newLiveDocs(Bits bits)
		throws IOException;

	public abstract Bits readLiveDocs(Directory directory, SegmentInfoPerCommit segmentinfopercommit, IOContext iocontext)
		throws IOException;

	public abstract void writeLiveDocs(MutableBits mutablebits, Directory directory, SegmentInfoPerCommit segmentinfopercommit, int i, IOContext iocontext)
		throws IOException;

	public abstract void files(SegmentInfoPerCommit segmentinfopercommit, Collection collection)
		throws IOException;
}
