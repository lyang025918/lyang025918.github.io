// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocInverter.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.index:
//			DocFieldConsumer, DocInverterPerField, InvertedDocConsumer, InvertedDocEndConsumer, 
//			DocumentsWriterPerThread, SegmentWriteState, FieldInfo, DocFieldConsumerPerField

final class DocInverter extends DocFieldConsumer
{

	final InvertedDocConsumer consumer;
	final InvertedDocEndConsumer endConsumer;
	final DocumentsWriterPerThread.DocState docState;

	public DocInverter(DocumentsWriterPerThread.DocState docState, InvertedDocConsumer consumer, InvertedDocEndConsumer endConsumer)
	{
		this.docState = docState;
		this.consumer = consumer;
		this.endConsumer = endConsumer;
	}

	void flush(Map fieldsToFlush, SegmentWriteState state)
		throws IOException
	{
		Map childFieldsToFlush = new HashMap();
		Map endChildFieldsToFlush = new HashMap();
		java.util.Map.Entry fieldToFlush;
		DocInverterPerField perField;
		for (Iterator i$ = fieldsToFlush.entrySet().iterator(); i$.hasNext(); endChildFieldsToFlush.put(fieldToFlush.getKey(), perField.endConsumer))
		{
			fieldToFlush = (java.util.Map.Entry)i$.next();
			perField = (DocInverterPerField)fieldToFlush.getValue();
			childFieldsToFlush.put(fieldToFlush.getKey(), perField.consumer);
		}

		consumer.flush(childFieldsToFlush, state);
		endConsumer.flush(endChildFieldsToFlush, state);
	}

	public void startDocument()
		throws IOException
	{
		consumer.startDocument();
		endConsumer.startDocument();
	}

	public void finishDocument()
		throws IOException
	{
		endConsumer.finishDocument();
		consumer.finishDocument();
	}

	void abort()
	{
		consumer.abort();
		endConsumer.abort();
		break MISSING_BLOCK_LABEL_27;
		Exception exception;
		exception;
		endConsumer.abort();
		throw exception;
	}

	public boolean freeRAM()
	{
		return consumer.freeRAM();
	}

	public DocFieldConsumerPerField addField(FieldInfo fi)
	{
		return new DocInverterPerField(this, fi);
	}
}
