// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40PostingsBaseFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.SegmentReadState;
import org.apache.lucene.index.SegmentWriteState;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40PostingsReader, Lucene40PostingsWriter

public final class Lucene40PostingsBaseFormat extends PostingsBaseFormat
{

	public Lucene40PostingsBaseFormat()
	{
		super("Lucene40");
	}

	public PostingsReaderBase postingsReaderBase(SegmentReadState state)
		throws IOException
	{
		return new Lucene40PostingsReader(state.dir, state.fieldInfos, state.segmentInfo, state.context, state.segmentSuffix);
	}

	public PostingsWriterBase postingsWriterBase(SegmentWriteState state)
		throws IOException
	{
		return new Lucene40PostingsWriter(state);
	}
}
