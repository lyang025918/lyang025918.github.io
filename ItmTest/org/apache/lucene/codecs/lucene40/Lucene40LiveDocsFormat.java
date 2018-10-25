// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40LiveDocsFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import java.util.Collection;
import org.apache.lucene.codecs.LiveDocsFormat;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.MutableBits;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			BitVector

public class Lucene40LiveDocsFormat extends LiveDocsFormat
{

	static final String DELETES_EXTENSION = "del";
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40LiveDocsFormat.desiredAssertionStatus();

	public Lucene40LiveDocsFormat()
	{
	}

	public MutableBits newLiveDocs(int size)
		throws IOException
	{
		BitVector bitVector = new BitVector(size);
		bitVector.invertAll();
		return bitVector;
	}

	public MutableBits newLiveDocs(Bits existing)
		throws IOException
	{
		BitVector liveDocs = (BitVector)existing;
		return liveDocs.clone();
	}

	public Bits readLiveDocs(Directory dir, SegmentInfoPerCommit info, IOContext context)
		throws IOException
	{
		String filename = IndexFileNames.fileNameFromGeneration(info.info.name, "del", info.getDelGen());
		BitVector liveDocs = new BitVector(dir, filename, context);
		if (!$assertionsDisabled && liveDocs.count() != info.info.getDocCount() - info.getDelCount())
			throw new AssertionError((new StringBuilder()).append("liveDocs.count()=").append(liveDocs.count()).append(" info.docCount=").append(info.info.getDocCount()).append(" info.getDelCount()=").append(info.getDelCount()).toString());
		if (!$assertionsDisabled && liveDocs.length() != info.info.getDocCount())
			throw new AssertionError();
		else
			return liveDocs;
	}

	public void writeLiveDocs(MutableBits bits, Directory dir, SegmentInfoPerCommit info, int newDelCount, IOContext context)
		throws IOException
	{
		String filename = IndexFileNames.fileNameFromGeneration(info.info.name, "del", info.getNextDelGen());
		BitVector liveDocs = (BitVector)bits;
		if (!$assertionsDisabled && liveDocs.count() != info.info.getDocCount() - info.getDelCount() - newDelCount)
			throw new AssertionError();
		if (!$assertionsDisabled && liveDocs.length() != info.info.getDocCount())
		{
			throw new AssertionError();
		} else
		{
			liveDocs.write(dir, filename, context);
			return;
		}
	}

	public void files(SegmentInfoPerCommit info, Collection files)
		throws IOException
	{
		if (info.hasDeletions())
			files.add(IndexFileNames.fileNameFromGeneration(info.info.name, "del", info.getDelGen()));
	}

}
