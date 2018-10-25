// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsHash.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.ByteBlockPool;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			InvertedDocConsumer, IntBlockPool, TermsHashPerField, DocumentsWriterPerThread, 
//			TermsHashConsumer, SegmentWriteState, DocInverterPerField, FieldInfo, 
//			InvertedDocConsumerPerField

final class TermsHash extends InvertedDocConsumer
{

	final TermsHashConsumer consumer;
	final TermsHash nextTermsHash;
	final DocumentsWriterPerThread docWriter;
	final IntBlockPool intPool;
	final ByteBlockPool bytePool;
	ByteBlockPool termBytePool;
	final boolean primary;
	final DocumentsWriterPerThread.DocState docState;
	final BytesRef tr1 = new BytesRef();
	final BytesRef tr2 = new BytesRef();
	final BytesRef termBytesRef = new BytesRef(10);
	final boolean trackAllocations;

	public TermsHash(DocumentsWriterPerThread docWriter, TermsHashConsumer consumer, boolean trackAllocations, TermsHash nextTermsHash)
	{
		docState = docWriter.docState;
		this.docWriter = docWriter;
		this.consumer = consumer;
		this.trackAllocations = trackAllocations;
		this.nextTermsHash = nextTermsHash;
		intPool = new IntBlockPool(docWriter);
		bytePool = new ByteBlockPool(docWriter.byteBlockAllocator);
		if (nextTermsHash != null)
		{
			primary = true;
			termBytePool = bytePool;
			nextTermsHash.termBytePool = bytePool;
		} else
		{
			primary = false;
		}
	}

	public void abort()
	{
		reset();
		consumer.abort();
		if (nextTermsHash != null)
			nextTermsHash.abort();
		break MISSING_BLOCK_LABEL_45;
		Exception exception;
		exception;
		if (nextTermsHash != null)
			nextTermsHash.abort();
		throw exception;
	}

	void reset()
	{
		intPool.reset();
		bytePool.reset();
		if (primary)
			bytePool.reset();
	}

	void flush(Map fieldsToFlush, SegmentWriteState state)
		throws IOException
	{
		Map childFields = new HashMap();
		Map nextChildFields;
		if (nextTermsHash != null)
			nextChildFields = new HashMap();
		else
			nextChildFields = null;
		Iterator i$ = fieldsToFlush.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			TermsHashPerField perField = (TermsHashPerField)entry.getValue();
			childFields.put(entry.getKey(), perField.consumer);
			if (nextTermsHash != null)
				nextChildFields.put(entry.getKey(), perField.nextPerField);
		} while (true);
		consumer.flush(childFields, state);
		if (nextTermsHash != null)
			nextTermsHash.flush(nextChildFields, state);
	}

	InvertedDocConsumerPerField addField(DocInverterPerField docInverterPerField, FieldInfo fieldInfo)
	{
		return new TermsHashPerField(docInverterPerField, this, nextTermsHash, fieldInfo);
	}

	public boolean freeRAM()
	{
		return false;
	}

	void finishDocument()
		throws IOException
	{
		consumer.finishDocument(this);
		if (nextTermsHash != null)
			nextTermsHash.consumer.finishDocument(nextTermsHash);
	}

	void startDocument()
		throws IOException
	{
		consumer.startDocument();
		if (nextTermsHash != null)
			nextTermsHash.consumer.startDocument();
	}
}
