// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40NormsFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40DocValuesConsumer, Lucene40DocValuesProducer

public class Lucene40NormsFormat extends NormsFormat
{
	public static class Lucene40NormsDocValuesConsumer extends Lucene40DocValuesConsumer
	{

		protected DocValues getDocValuesForMerge(AtomicReader reader, FieldInfo info)
			throws IOException
		{
			return reader.normValues(info.name);
		}

		protected boolean canMerge(FieldInfo info)
		{
			return info.hasNorms();
		}

		protected org.apache.lucene.index.DocValues.Type getDocValuesType(FieldInfo info)
		{
			return info.getNormType();
		}

		public Lucene40NormsDocValuesConsumer(PerDocWriteState state, String segmentSuffix)
		{
			super(state, segmentSuffix);
		}
	}

	public static class Lucene40NormsDocValuesProducer extends Lucene40DocValuesProducer
	{

		protected boolean canLoad(FieldInfo info)
		{
			return info.hasNorms();
		}

		protected org.apache.lucene.index.DocValues.Type getDocValuesType(FieldInfo info)
		{
			return info.getNormType();
		}

		protected boolean anyDocValuesFields(FieldInfos infos)
		{
			return infos.hasNorms();
		}

		public Lucene40NormsDocValuesProducer(SegmentReadState state, String segmentSuffix)
			throws IOException
		{
			super(state, segmentSuffix);
		}
	}


	private static final String NORMS_SEGMENT_SUFFIX = "nrm";

	public Lucene40NormsFormat()
	{
	}

	public PerDocConsumer docsConsumer(PerDocWriteState state)
		throws IOException
	{
		return new Lucene40NormsDocValuesConsumer(state, "nrm");
	}

	public PerDocProducer docsProducer(SegmentReadState state)
		throws IOException
	{
		return new Lucene40NormsDocValuesProducer(state, "nrm");
	}
}
