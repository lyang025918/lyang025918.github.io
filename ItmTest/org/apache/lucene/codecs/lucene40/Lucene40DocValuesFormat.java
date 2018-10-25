// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40DocValuesFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.PerDocWriteState;
import org.apache.lucene.index.SegmentReadState;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40DocValuesConsumer, Lucene40DocValuesProducer

public class Lucene40DocValuesFormat extends DocValuesFormat
{

	public Lucene40DocValuesFormat()
	{
	}

	public PerDocConsumer docsConsumer(PerDocWriteState state)
		throws IOException
	{
		return new Lucene40DocValuesConsumer(state, "dv");
	}

	public PerDocProducer docsProducer(SegmentReadState state)
		throws IOException
	{
		return new Lucene40DocValuesProducer(state, "dv");
	}
}
