// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermVectorsFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

// Referenced classes of package org.apache.lucene.codecs:
//			TermVectorsReader, TermVectorsWriter

public abstract class TermVectorsFormat
{

	protected TermVectorsFormat()
	{
	}

	public abstract TermVectorsReader vectorsReader(Directory directory, SegmentInfo segmentinfo, FieldInfos fieldinfos, IOContext iocontext)
		throws IOException;

	public abstract TermVectorsWriter vectorsWriter(Directory directory, SegmentInfo segmentinfo, IOContext iocontext)
		throws IOException;
}
