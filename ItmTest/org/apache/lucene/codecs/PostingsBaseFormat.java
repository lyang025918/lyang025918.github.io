// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PostingsBaseFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.SegmentReadState;
import org.apache.lucene.index.SegmentWriteState;

// Referenced classes of package org.apache.lucene.codecs:
//			PostingsReaderBase, PostingsWriterBase

public abstract class PostingsBaseFormat
{

	public final String name;

	protected PostingsBaseFormat(String name)
	{
		this.name = name;
	}

	public abstract PostingsReaderBase postingsReaderBase(SegmentReadState segmentreadstate)
		throws IOException;

	public abstract PostingsWriterBase postingsWriterBase(SegmentWriteState segmentwritestate)
		throws IOException;
}
