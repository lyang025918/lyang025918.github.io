// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormsConsumer.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.apache.lucene.codecs.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.index:
//			InvertedDocEndConsumer, FieldInfo, NormsConsumerPerField, SegmentWriteState, 
//			DocumentsWriterPerThread, FieldInfos, SegmentInfo, DocValues, 
//			DocInverterPerField, InvertedDocEndConsumerPerField, PerDocWriteState

final class NormsConsumer extends InvertedDocEndConsumer
{

	private final NormsFormat normsFormat;
	private PerDocConsumer consumer;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/NormsConsumer.desiredAssertionStatus();

	public NormsConsumer(DocumentsWriterPerThread dwpt)
	{
		normsFormat = dwpt.codec.normsFormat();
	}

	public void abort()
	{
		if (consumer != null)
			consumer.abort();
	}

	public void flush(Map fieldsToFlush, SegmentWriteState state)
		throws IOException
	{
		boolean success;
		boolean anythingFlushed;
		success = false;
		anythingFlushed = false;
label0:
		{
			if (!state.fieldInfos.hasNorms())
				break label0;
			Iterator i$ = state.fieldInfos.iterator();
			FieldInfo fi;
			do
			{
label1:
				do
				{
					DocValues.Type type;
					do
					{
						NormsConsumerPerField toWrite;
						do
						{
							if (!i$.hasNext())
								break label0;
							fi = (FieldInfo)i$.next();
							toWrite = (NormsConsumerPerField)fieldsToFlush.get(fi.name);
						} while (fi.omitsNorms());
						if (toWrite == null || !toWrite.initialized())
							continue label1;
						anythingFlushed = true;
						type = toWrite.flush(state.segmentInfo.getDocCount());
					} while ($assertionsDisabled || fi.getNormType() == type);
					throw new AssertionError();
				} while (!fi.isIndexed());
				anythingFlushed = true;
			} while ($assertionsDisabled || fi.getNormType() == null);
			throw new AssertionError((new StringBuilder()).append("got ").append(fi.getNormType()).append("; field=").append(fi.name).toString());
		}
		success = true;
		if (!anythingFlushed && consumer != null)
			consumer.abort();
		if (success)
			IOUtils.close(new Closeable[] {
				consumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				consumer
			});
		break MISSING_BLOCK_LABEL_298;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				consumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				consumer
			});
		throw exception;
	}

	void finishDocument()
	{
	}

	void startDocument()
	{
	}

	InvertedDocEndConsumerPerField addField(DocInverterPerField docInverterPerField, FieldInfo fieldInfo)
	{
		return new NormsConsumerPerField(docInverterPerField, fieldInfo, this);
	}

	DocValuesConsumer newConsumer(PerDocWriteState perDocWriteState, FieldInfo fieldInfo, DocValues.Type type)
		throws IOException
	{
		if (consumer == null)
			consumer = normsFormat.docsConsumer(perDocWriteState);
		DocValuesConsumer addValuesField = consumer.addValuesField(type, fieldInfo);
		return addValuesField;
	}

}
