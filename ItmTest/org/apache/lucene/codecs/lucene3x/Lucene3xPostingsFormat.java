// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xPostingsFormat.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.SegmentReadState;
import org.apache.lucene.index.SegmentWriteState;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xFields

/**
 * @deprecated Class Lucene3xPostingsFormat is deprecated
 */

class Lucene3xPostingsFormat extends PostingsFormat
{

	public static final String TERMS_EXTENSION = "tis";
	public static final String TERMS_INDEX_EXTENSION = "tii";
	public static final String FREQ_EXTENSION = "frq";
	public static final String PROX_EXTENSION = "prx";

	public Lucene3xPostingsFormat()
	{
		super("Lucene3x");
	}

	public FieldsConsumer fieldsConsumer(SegmentWriteState state)
		throws IOException
	{
		throw new UnsupportedOperationException("this codec can only be used for reading");
	}

	public FieldsProducer fieldsProducer(SegmentReadState state)
		throws IOException
	{
		return new Lucene3xFields(state.dir, state.fieldInfos, state.segmentInfo, state.context, state.termsIndexDivisor);
	}
}
