// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StoredFieldsConsumer.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.index:
//			IndexableField, FieldInfo, DocumentsWriterPerThread, SegmentWriteState, 
//			SegmentInfo, IndexWriter

final class StoredFieldsConsumer
{

	StoredFieldsWriter fieldsWriter;
	final DocumentsWriterPerThread docWriter;
	int lastDocID;
	int freeCount;
	final DocumentsWriterPerThread.DocState docState;
	final Codec codec;
	private int numStoredFields;
	private IndexableField storedFields[];
	private FieldInfo fieldInfos[];
	int allocCount;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/StoredFieldsConsumer.desiredAssertionStatus();

	public StoredFieldsConsumer(DocumentsWriterPerThread docWriter)
	{
		this.docWriter = docWriter;
		docState = docWriter.docState;
		codec = docWriter.codec;
	}

	public void reset()
	{
		numStoredFields = 0;
		storedFields = new IndexableField[1];
		fieldInfos = new FieldInfo[1];
	}

	public void startDocument()
	{
		reset();
	}

	public void flush(SegmentWriteState state)
		throws IOException
	{
		int numDocs;
		numDocs = state.segmentInfo.getDocCount();
		if (numDocs > 0)
		{
			initFieldsWriter(state.context);
			fill(numDocs);
		}
		if (fieldsWriter == null)
			break MISSING_BLOCK_LABEL_84;
		fieldsWriter.finish(state.fieldInfos, numDocs);
		fieldsWriter.close();
		fieldsWriter = null;
		lastDocID = 0;
		break MISSING_BLOCK_LABEL_84;
		Exception exception;
		exception;
		fieldsWriter.close();
		fieldsWriter = null;
		lastDocID = 0;
		throw exception;
	}

	private synchronized void initFieldsWriter(IOContext context)
		throws IOException
	{
		if (fieldsWriter == null)
		{
			fieldsWriter = codec.storedFieldsFormat().fieldsWriter(docWriter.directory, docWriter.getSegmentInfo(), context);
			lastDocID = 0;
		}
	}

	void abort()
	{
		reset();
		if (fieldsWriter != null)
		{
			fieldsWriter.abort();
			fieldsWriter = null;
			lastDocID = 0;
		}
	}

	void fill(int docID)
		throws IOException
	{
		for (; lastDocID < docID; lastDocID++)
			fieldsWriter.startDocument(0);

	}

	void finishDocument()
		throws IOException
	{
		if (!$assertionsDisabled && !docWriter.writer.testPoint("StoredFieldsWriter.finishDocument start"))
			throw new AssertionError();
		initFieldsWriter(IOContext.DEFAULT);
		fill(docState.docID);
		if (fieldsWriter != null && numStoredFields > 0)
		{
			fieldsWriter.startDocument(numStoredFields);
			for (int i = 0; i < numStoredFields; i++)
				fieldsWriter.writeField(fieldInfos[i], storedFields[i]);

			lastDocID++;
		}
		reset();
		if (!$assertionsDisabled && !docWriter.writer.testPoint("StoredFieldsWriter.finishDocument end"))
			throw new AssertionError();
		else
			return;
	}

	public void addField(IndexableField field, FieldInfo fieldInfo)
	{
		if (numStoredFields == storedFields.length)
		{
			int newSize = ArrayUtil.oversize(numStoredFields + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
			IndexableField newArray[] = new IndexableField[newSize];
			System.arraycopy(storedFields, 0, newArray, 0, numStoredFields);
			storedFields = newArray;
			FieldInfo newInfoArray[] = new FieldInfo[newSize];
			System.arraycopy(fieldInfos, 0, newInfoArray, 0, numStoredFields);
			fieldInfos = newInfoArray;
		}
		storedFields[numStoredFields] = field;
		fieldInfos[numStoredFields] = fieldInfo;
		numStoredFields++;
		if (!$assertionsDisabled && !docState.testPoint("StoredFieldsWriterPerThread.processFields.writeField"))
			throw new AssertionError();
		else
			return;
	}

}
