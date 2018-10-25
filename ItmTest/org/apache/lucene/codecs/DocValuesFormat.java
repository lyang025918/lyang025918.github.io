// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocValuesFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.PerDocWriteState;
import org.apache.lucene.index.SegmentReadState;

// Referenced classes of package org.apache.lucene.codecs:
//			PerDocConsumer, PerDocProducer

public abstract class DocValuesFormat
{

	protected DocValuesFormat()
	{
	}

	public abstract PerDocConsumer docsConsumer(PerDocWriteState perdocwritestate)
		throws IOException;

	public abstract PerDocProducer docsProducer(SegmentReadState segmentreadstate)
		throws IOException;
}
