// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40PostingsFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.SegmentReadState;
import org.apache.lucene.index.SegmentWriteState;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40PostingsWriter, Lucene40PostingsReader

public final class Lucene40PostingsFormat extends PostingsFormat
{

	private final int minBlockSize;
	private final int maxBlockSize;
	static final String FREQ_EXTENSION = "frq";
	static final String PROX_EXTENSION = "prx";
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsFormat.desiredAssertionStatus();

	public Lucene40PostingsFormat()
	{
		this(25, 48);
	}

	public Lucene40PostingsFormat(int minBlockSize, int maxBlockSize)
	{
		super("Lucene40");
		this.minBlockSize = minBlockSize;
		if (!$assertionsDisabled && minBlockSize <= 1)
		{
			throw new AssertionError();
		} else
		{
			this.maxBlockSize = maxBlockSize;
			return;
		}
	}

	public FieldsConsumer fieldsConsumer(SegmentWriteState state)
		throws IOException
	{
		PostingsWriterBase docs;
		boolean success;
		docs = new Lucene40PostingsWriter(state);
		success = false;
		FieldsConsumer fieldsconsumer;
		FieldsConsumer ret = new BlockTreeTermsWriter(state, docs, minBlockSize, maxBlockSize);
		success = true;
		fieldsconsumer = ret;
		if (!success)
			docs.close();
		return fieldsconsumer;
		Exception exception;
		exception;
		if (!success)
			docs.close();
		throw exception;
	}

	public FieldsProducer fieldsProducer(SegmentReadState state)
		throws IOException
	{
		PostingsReaderBase postings;
		boolean success;
		postings = new Lucene40PostingsReader(state.dir, state.fieldInfos, state.segmentInfo, state.context, state.segmentSuffix);
		success = false;
		FieldsProducer fieldsproducer;
		FieldsProducer ret = new BlockTreeTermsReader(state.dir, state.fieldInfos, state.segmentInfo, postings, state.context, state.segmentSuffix, state.termsIndexDivisor);
		success = true;
		fieldsproducer = ret;
		if (!success)
			postings.close();
		return fieldsproducer;
		Exception exception;
		exception;
		if (!success)
			postings.close();
		throw exception;
	}

	public String toString()
	{
		return (new StringBuilder()).append(getName()).append("(minBlockSize=").append(minBlockSize).append(" maxBlockSize=").append(maxBlockSize).append(")").toString();
	}

}
