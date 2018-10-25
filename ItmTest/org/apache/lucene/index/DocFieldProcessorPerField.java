// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocFieldProcessorPerField.java

package org.apache.lucene.index;

import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.index:
//			IndexableField, DocFieldProcessor, DocFieldConsumer, DocFieldConsumerPerField, 
//			FieldInfo

final class DocFieldProcessorPerField
{

	final DocFieldConsumerPerField consumer;
	final FieldInfo fieldInfo;
	DocFieldProcessorPerField next;
	int lastGen;
	int fieldCount;
	IndexableField fields[];

	public DocFieldProcessorPerField(DocFieldProcessor docFieldProcessor, FieldInfo fieldInfo)
	{
		lastGen = -1;
		fields = new IndexableField[1];
		consumer = docFieldProcessor.consumer.addField(fieldInfo);
		this.fieldInfo = fieldInfo;
	}

	public void addField(IndexableField field)
	{
		if (fieldCount == fields.length)
		{
			int newSize = ArrayUtil.oversize(fieldCount + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
			IndexableField newArray[] = new IndexableField[newSize];
			System.arraycopy(fields, 0, newArray, 0, fieldCount);
			fields = newArray;
		}
		fields[fieldCount++] = field;
	}

	public void abort()
	{
		consumer.abort();
	}
}
