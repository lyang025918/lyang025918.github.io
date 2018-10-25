// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermVectorsConsumer.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.store.FlushInfo;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermsHashConsumer, ByteSliceReader, TermsHashConsumerPerField, TermVectorsConsumerPerField, 
//			DocumentsWriterPerThread, SegmentWriteState, SegmentInfo, TermsHashPerField, 
//			IndexWriter, TermsHash, FieldInfo

final class TermVectorsConsumer extends TermsHashConsumer
{

	TermVectorsWriter writer;
	final DocumentsWriterPerThread docWriter;
	int freeCount;
	int lastDocID;
	final DocumentsWriterPerThread.DocState docState;
	final BytesRef flushTerm = new BytesRef();
	final ByteSliceReader vectorSliceReaderPos = new ByteSliceReader();
	final ByteSliceReader vectorSliceReaderOff = new ByteSliceReader();
	boolean hasVectors;
	int numVectorFields;
	TermVectorsConsumerPerField perFields[];
	String lastVectorFieldName;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/TermVectorsConsumer.desiredAssertionStatus();

	public TermVectorsConsumer(DocumentsWriterPerThread docWriter)
	{
		this.docWriter = docWriter;
		docState = docWriter.docState;
	}

	void flush(Map fieldsToFlush, SegmentWriteState state)
		throws IOException
	{
		int numDocs;
		if (writer == null)
			break MISSING_BLOCK_LABEL_119;
		numDocs = state.segmentInfo.getDocCount();
		fill(numDocs);
		if (!$assertionsDisabled && state.segmentInfo == null)
			throw new AssertionError();
		writer.finish(state.fieldInfos, numDocs);
		IOUtils.close(new Closeable[] {
			writer
		});
		writer = null;
		lastDocID = 0;
		hasVectors = false;
		break MISSING_BLOCK_LABEL_119;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			writer
		});
		writer = null;
		lastDocID = 0;
		hasVectors = false;
		throw exception;
		TermVectorsConsumerPerField perField;
		for (Iterator i$ = fieldsToFlush.values().iterator(); i$.hasNext(); perField.shrinkHash())
		{
			TermsHashConsumerPerField field = (TermsHashConsumerPerField)i$.next();
			perField = (TermVectorsConsumerPerField)field;
			perField.termsHashPerField.reset();
		}

		return;
	}

	void fill(int docID)
		throws IOException
	{
		for (; lastDocID < docID; lastDocID++)
			writer.startDocument(0);

	}

	private final void initTermVectorsWriter()
		throws IOException
	{
		if (writer == null)
		{
			IOContext context = new IOContext(new FlushInfo(docWriter.getNumDocsInRAM(), docWriter.bytesUsed()));
			writer = docWriter.codec.termVectorsFormat().vectorsWriter(docWriter.directory, docWriter.getSegmentInfo(), context);
			lastDocID = 0;
		}
	}

	void finishDocument(TermsHash termsHash)
		throws IOException
	{
		if (!$assertionsDisabled && !docWriter.writer.testPoint("TermVectorsTermsWriter.finishDocument start"))
			throw new AssertionError();
		if (!hasVectors)
			return;
		initTermVectorsWriter();
		fill(docState.docID);
		writer.startDocument(numVectorFields);
		for (int i = 0; i < numVectorFields; i++)
			perFields[i].finishDocument();

		if (!$assertionsDisabled && lastDocID != docState.docID)
			throw new AssertionError((new StringBuilder()).append("lastDocID=").append(lastDocID).append(" docState.docID=").append(docState.docID).toString());
		lastDocID++;
		termsHash.reset();
		reset();
		if (!$assertionsDisabled && !docWriter.writer.testPoint("TermVectorsTermsWriter.finishDocument end"))
			throw new AssertionError();
		else
			return;
	}

	public void abort()
	{
		hasVectors = false;
		if (writer != null)
		{
			writer.abort();
			writer = null;
		}
		lastDocID = 0;
		reset();
	}

	void reset()
	{
		numVectorFields = 0;
		perFields = new TermVectorsConsumerPerField[1];
	}

	public TermsHashConsumerPerField addField(TermsHashPerField termsHashPerField, FieldInfo fieldInfo)
	{
		return new TermVectorsConsumerPerField(termsHashPerField, this, fieldInfo);
	}

	void addFieldToFlush(TermVectorsConsumerPerField fieldToFlush)
	{
		if (numVectorFields == perFields.length)
		{
			int newSize = ArrayUtil.oversize(numVectorFields + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
			TermVectorsConsumerPerField newArray[] = new TermVectorsConsumerPerField[newSize];
			System.arraycopy(perFields, 0, newArray, 0, numVectorFields);
			perFields = newArray;
		}
		perFields[numVectorFields++] = fieldToFlush;
	}

	void startDocument()
	{
		if (!$assertionsDisabled && !clearLastVectorFieldName())
		{
			throw new AssertionError();
		} else
		{
			reset();
			return;
		}
	}

	final boolean clearLastVectorFieldName()
	{
		lastVectorFieldName = null;
		return true;
	}

	final boolean vectorFieldsInOrder(FieldInfo fi)
	{
		boolean flag;
		if (lastVectorFieldName == null)
			break MISSING_BLOCK_LABEL_37;
		flag = lastVectorFieldName.compareTo(fi.name) < 0;
		lastVectorFieldName = fi.name;
		return flag;
		flag = true;
		lastVectorFieldName = fi.name;
		return flag;
		Exception exception;
		exception;
		lastVectorFieldName = fi.name;
		throw exception;
	}

}
