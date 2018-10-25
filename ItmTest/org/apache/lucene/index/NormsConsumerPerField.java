// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormsConsumerPerField.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.codecs.DocValuesConsumer;
import org.apache.lucene.search.similarities.Similarity;

// Referenced classes of package org.apache.lucene.index:
//			InvertedDocEndConsumerPerField, Norm, DocInverterPerField, FieldInfo, 
//			DocumentsWriterPerThread, NormsConsumer, DocValues, FieldInvertState

final class NormsConsumerPerField extends InvertedDocEndConsumerPerField
	implements Comparable
{

	private final FieldInfo fieldInfo;
	private final DocumentsWriterPerThread.DocState docState;
	private final Similarity similarity;
	private final FieldInvertState fieldState;
	private DocValuesConsumer consumer;
	private final Norm norm = new Norm();
	private final NormsConsumer parent;
	private DocValues.Type initType;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/NormsConsumerPerField.desiredAssertionStatus();

	public NormsConsumerPerField(DocInverterPerField docInverterPerField, FieldInfo fieldInfo, NormsConsumer parent)
	{
		this.fieldInfo = fieldInfo;
		this.parent = parent;
		docState = docInverterPerField.docState;
		fieldState = docInverterPerField.fieldState;
		similarity = docState.similarity;
	}

	public int compareTo(NormsConsumerPerField other)
	{
		return fieldInfo.name.compareTo(other.fieldInfo.name);
	}

	void finish()
		throws IOException
	{
		if (fieldInfo.isIndexed() && !fieldInfo.omitsNorms())
		{
			similarity.computeNorm(fieldState, norm);
			if (norm.type() != null)
			{
				IndexableField field = norm.field();
				DocValuesConsumer consumer = getConsumer(norm.type());
				consumer.add(docState.docID, field);
			}
		}
	}

	DocValues.Type flush(int docCount)
		throws IOException
	{
		if (!initialized())
		{
			return null;
		} else
		{
			consumer.finish(docCount);
			return initType;
		}
	}

	private DocValuesConsumer getConsumer(DocValues.Type type)
		throws IOException
	{
		if (consumer == null)
		{
			if (!$assertionsDisabled && fieldInfo.getNormType() != null && fieldInfo.getNormType() != type)
				throw new AssertionError();
			fieldInfo.setNormValueType(type);
			consumer = parent.newConsumer(docState.docWriter.newPerDocWriteState(""), fieldInfo, type);
			initType = type;
		}
		if (initType != type)
			throw new IllegalArgumentException((new StringBuilder()).append("NormTypes for field: ").append(fieldInfo.name).append(" doesn't match ").append(initType).append(" != ").append(type).toString());
		else
			return consumer;
	}

	boolean initialized()
	{
		return consumer != null;
	}

	void abort()
	{
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((NormsConsumerPerField)x0);
	}

}
