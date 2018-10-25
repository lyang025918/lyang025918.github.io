// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocFieldProcessor.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			DocConsumer, DocFieldProcessorPerField, StoredFieldsConsumer, DocFieldConsumerPerField, 
//			IndexableField, DocumentsWriterPerThread, FieldInfo, DocFieldConsumer, 
//			SegmentWriteState, SegmentInfo, IndexableFieldType, TypePromoter, 
//			FieldInfos, DocValues

final class DocFieldProcessor extends DocConsumer
{
	private static class DocValuesConsumerHolder
	{

		int docID;
		final DocValuesConsumer docValuesConsumer;
		TypePromoter.TypeCompatibility compatibility;

		public DocValuesConsumerHolder(DocValuesConsumer docValuesConsumer)
		{
			this.docValuesConsumer = docValuesConsumer;
		}
	}


	final DocFieldConsumer consumer;
	final StoredFieldsConsumer fieldsWriter;
	final Codec codec;
	DocFieldProcessorPerField fields[];
	int fieldCount;
	DocFieldProcessorPerField fieldHash[];
	int hashMask;
	int totalFieldCount;
	float docBoost;
	int fieldGen;
	final DocumentsWriterPerThread.DocState docState;
	private static final Comparator fieldsComp = new Comparator() {

		public int compare(DocFieldProcessorPerField o1, DocFieldProcessorPerField o2)
		{
			return o1.fieldInfo.name.compareTo(o2.fieldInfo.name);
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((DocFieldProcessorPerField)x0, (DocFieldProcessorPerField)x1);
		}

	};
	private final Map docValues = new HashMap();
	private PerDocConsumer perDocConsumer;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocFieldProcessor.desiredAssertionStatus();

	public DocFieldProcessor(DocumentsWriterPerThread docWriter, DocFieldConsumer consumer)
	{
		fields = new DocFieldProcessorPerField[1];
		fieldHash = new DocFieldProcessorPerField[2];
		hashMask = 1;
		docState = docWriter.docState;
		codec = docWriter.codec;
		this.consumer = consumer;
		fieldsWriter = new StoredFieldsConsumer(docWriter);
	}

	public void flush(SegmentWriteState state)
		throws IOException
	{
		Map childFields = new HashMap();
		Collection fields = fields();
		DocFieldConsumerPerField f;
		for (Iterator i$ = fields.iterator(); i$.hasNext(); childFields.put(f.getFieldInfo().name, f))
			f = (DocFieldConsumerPerField)i$.next();

		fieldsWriter.flush(state);
		this.consumer.flush(childFields, state);
		DocValuesConsumerHolder consumer;
		for (Iterator i$ = docValues.values().iterator(); i$.hasNext(); consumer.docValuesConsumer.finish(state.segmentInfo.getDocCount()))
			consumer = (DocValuesConsumerHolder)i$.next();

		IOUtils.close(new Closeable[] {
			perDocConsumer
		});
		FieldInfosWriter infosWriter = codec.fieldInfosFormat().getFieldInfosWriter();
		infosWriter.write(state.directory, state.segmentInfo.name, state.fieldInfos, IOContext.DEFAULT);
	}

	public void abort()
	{
		Throwable th = null;
		DocFieldProcessorPerField arr$[] = fieldHash;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			DocFieldProcessorPerField next;
			for (DocFieldProcessorPerField field = arr$[i$]; field != null; field = next)
			{
				next = field.next;
				try
				{
					field.abort();
					continue;
				}
				catch (Throwable t)
				{
					if (th == null)
						th = t;
				}
			}

		}

		IOUtils.closeWhileHandlingException(new Closeable[] {
			perDocConsumer
		});
		try
		{
			fieldsWriter.abort();
		}
		catch (Throwable t)
		{
			if (th == null)
				th = t;
		}
		try
		{
			consumer.abort();
		}
		catch (Throwable t)
		{
			if (th == null)
				th = t;
		}
		try
		{
			if (perDocConsumer != null)
				perDocConsumer.abort();
		}
		catch (Throwable t)
		{
			if (th == null)
				th = t;
		}
		if (th != null)
		{
			if (th instanceof RuntimeException)
				throw (RuntimeException)th;
			if (th instanceof Error)
				throw (Error)th;
			else
				throw new RuntimeException(th);
		} else
		{
			return;
		}
	}

	public boolean freeRAM()
	{
		return consumer.freeRAM();
	}

	public Collection fields()
	{
		Collection fields = new HashSet();
		for (int i = 0; i < fieldHash.length; i++)
		{
			for (DocFieldProcessorPerField field = fieldHash[i]; field != null; field = field.next)
				fields.add(field.consumer);

		}

		if (!$assertionsDisabled && fields.size() != totalFieldCount)
			throw new AssertionError();
		else
			return fields;
	}

	void doAfterFlush()
	{
		fieldHash = new DocFieldProcessorPerField[2];
		hashMask = 1;
		totalFieldCount = 0;
		perDocConsumer = null;
		docValues.clear();
	}

	private void rehash()
	{
		int newHashSize = fieldHash.length * 2;
		if (!$assertionsDisabled && newHashSize <= fieldHash.length)
			throw new AssertionError();
		DocFieldProcessorPerField newHashArray[] = new DocFieldProcessorPerField[newHashSize];
		int newHashMask = newHashSize - 1;
		for (int j = 0; j < fieldHash.length; j++)
		{
			DocFieldProcessorPerField nextFP0;
			for (DocFieldProcessorPerField fp0 = fieldHash[j]; fp0 != null; fp0 = nextFP0)
			{
				int hashPos2 = fp0.fieldInfo.name.hashCode() & newHashMask;
				nextFP0 = fp0.next;
				fp0.next = newHashArray[hashPos2];
				newHashArray[hashPos2] = fp0;
			}

		}

		fieldHash = newHashArray;
		hashMask = newHashMask;
	}

	public void processDocument(FieldInfos.Builder fieldInfos)
		throws IOException
	{
		this.consumer.startDocument();
		fieldsWriter.startDocument();
		fieldCount = 0;
		int thisFieldGen = fieldGen++;
		Iterator i$ = docState.doc.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexableField field = (IndexableField)i$.next();
			String fieldName = field.name();
			int hashPos = fieldName.hashCode() & hashMask;
			DocFieldProcessorPerField fp;
			for (fp = fieldHash[hashPos]; fp != null && !fp.fieldInfo.name.equals(fieldName); fp = fp.next);
			if (fp == null)
			{
				FieldInfo fi = fieldInfos.addOrUpdate(fieldName, field.fieldType());
				fp = new DocFieldProcessorPerField(this, fi);
				fp.next = fieldHash[hashPos];
				fieldHash[hashPos] = fp;
				totalFieldCount++;
				if (totalFieldCount >= fieldHash.length / 2)
					rehash();
			} else
			{
				fieldInfos.addOrUpdate(fp.fieldInfo.name, field.fieldType());
			}
			if (thisFieldGen != fp.lastGen)
			{
				fp.fieldCount = 0;
				if (fieldCount == fields.length)
				{
					int newSize = fields.length * 2;
					DocFieldProcessorPerField newArray[] = new DocFieldProcessorPerField[newSize];
					System.arraycopy(fields, 0, newArray, 0, fieldCount);
					fields = newArray;
				}
				fields[fieldCount++] = fp;
				fp.lastGen = thisFieldGen;
			}
			fp.addField(field);
			if (field.fieldType().stored())
				fieldsWriter.addField(field, fp.fieldInfo);
			DocValues.Type dvType = field.fieldType().docValueType();
			if (dvType != null)
			{
				DocValuesConsumerHolder docValuesConsumer = docValuesConsumer(dvType, docState, fp.fieldInfo);
				DocValuesConsumer consumer = docValuesConsumer.docValuesConsumer;
				if (docValuesConsumer.compatibility == null)
				{
					consumer.add(docState.docID, field);
					docValuesConsumer.compatibility = new TypePromoter.TypeCompatibility(dvType, consumer.getValueSize());
				} else
				if (docValuesConsumer.compatibility.isCompatible(dvType, TypePromoter.getValueSize(dvType, field.binaryValue())))
				{
					consumer.add(docState.docID, field);
				} else
				{
					docValuesConsumer.compatibility.isCompatible(dvType, TypePromoter.getValueSize(dvType, field.binaryValue()));
					TypePromoter.TypeCompatibility compatibility = docValuesConsumer.compatibility;
					throw new IllegalArgumentException((new StringBuilder()).append("Incompatible DocValues type: ").append(dvType.name()).append(" size: ").append(TypePromoter.getValueSize(dvType, field.binaryValue())).append(" expected: ").append(" type: ").append(compatibility.getBaseType()).append(" size: ").append(compatibility.getBaseSize()).toString());
				}
			}
		} while (true);
		ArrayUtil.quickSort(fields, 0, fieldCount, fieldsComp);
		for (int i = 0; i < fieldCount; i++)
		{
			DocFieldProcessorPerField perField = fields[i];
			perField.consumer.processFields(perField.fields, perField.fieldCount);
		}

		if (docState.maxTermPrefix != null && docState.infoStream.isEnabled("IW"))
		{
			docState.infoStream.message("IW", (new StringBuilder()).append("WARNING: document contains at least one immense term (whose UTF8 encoding is longer than the max length 32766), all of which were skipped.  Please correct the analyzer to not produce such terms.  The prefix of the first immense term is: '").append(docState.maxTermPrefix).append("...'").toString());
			docState.maxTermPrefix = null;
		}
	}

	void finishDocument()
		throws IOException
	{
		fieldsWriter.finishDocument();
		consumer.finishDocument();
		break MISSING_BLOCK_LABEL_27;
		Exception exception;
		exception;
		consumer.finishDocument();
		throw exception;
	}

	DocValuesConsumerHolder docValuesConsumer(DocValues.Type valueType, DocumentsWriterPerThread.DocState docState, FieldInfo fieldInfo)
		throws IOException
	{
		DocValuesConsumerHolder docValuesConsumerAndDocID = (DocValuesConsumerHolder)docValues.get(fieldInfo.name);
		if (docValuesConsumerAndDocID != null)
		{
			if (docState.docID == docValuesConsumerAndDocID.docID)
				throw new IllegalArgumentException((new StringBuilder()).append("DocValuesField \"").append(fieldInfo.name).append("\" appears more than once in this document (only one value is allowed, per field)").toString());
			if (!$assertionsDisabled && docValuesConsumerAndDocID.docID >= docState.docID)
			{
				throw new AssertionError();
			} else
			{
				docValuesConsumerAndDocID.docID = docState.docID;
				return docValuesConsumerAndDocID;
			}
		}
		if (perDocConsumer == null)
		{
			PerDocWriteState perDocWriteState = docState.docWriter.newPerDocWriteState("");
			perDocConsumer = docState.docWriter.codec.docValuesFormat().docsConsumer(perDocWriteState);
			if (perDocConsumer == null)
				throw new IllegalStateException((new StringBuilder()).append("codec=").append(docState.docWriter.codec).append(" does not support docValues: from docValuesFormat().docsConsumer(...) returned null; field=").append(fieldInfo.name).toString());
		}
		DocValuesConsumer docValuesConsumer = perDocConsumer.addValuesField(valueType, fieldInfo);
		if (!$assertionsDisabled && fieldInfo.getDocValuesType() != null && fieldInfo.getDocValuesType() != valueType)
		{
			throw new AssertionError();
		} else
		{
			fieldInfo.setDocValuesType(valueType);
			docValuesConsumerAndDocID = new DocValuesConsumerHolder(docValuesConsumer);
			docValuesConsumerAndDocID.docID = docState.docID;
			docValues.put(fieldInfo.name, docValuesConsumerAndDocID);
			return docValuesConsumerAndDocID;
		}
	}

}
