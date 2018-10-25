// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xNormsFormat.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.PerDocWriteState;
import org.apache.lucene.index.SegmentReadState;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xNormsProducer

/**
 * @deprecated Class Lucene3xNormsFormat is deprecated
 */

class Lucene3xNormsFormat extends NormsFormat
{

	Lucene3xNormsFormat()
	{
	}

	public PerDocConsumer docsConsumer(PerDocWriteState state)
		throws IOException
	{
		throw new UnsupportedOperationException("this codec can only be used for reading");
	}

	public PerDocProducer docsProducer(SegmentReadState state)
		throws IOException
	{
		return new Lucene3xNormsProducer(state.dir, state.segmentInfo, state.fieldInfos, state.context);
	}
}
