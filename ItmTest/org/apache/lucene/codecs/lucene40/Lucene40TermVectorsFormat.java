// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40TermVectorsFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40TermVectorsReader, Lucene40TermVectorsWriter

public class Lucene40TermVectorsFormat extends TermVectorsFormat
{

	public Lucene40TermVectorsFormat()
	{
	}

	public TermVectorsReader vectorsReader(Directory directory, SegmentInfo segmentInfo, FieldInfos fieldInfos, IOContext context)
		throws IOException
	{
		return new Lucene40TermVectorsReader(directory, segmentInfo, fieldInfos, context);
	}

	public TermVectorsWriter vectorsWriter(Directory directory, SegmentInfo segmentInfo, IOContext context)
		throws IOException
	{
		return new Lucene40TermVectorsWriter(directory, segmentInfo.name, context);
	}
}
