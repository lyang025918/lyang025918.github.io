// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerDocConsumer.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.index.*;

// Referenced classes of package org.apache.lucene.codecs:
//			DocValuesConsumer

public abstract class PerDocConsumer
	implements Closeable
{

	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/PerDocConsumer.desiredAssertionStatus();

	protected PerDocConsumer()
	{
	}

	public abstract DocValuesConsumer addValuesField(org.apache.lucene.index.DocValues.Type type, FieldInfo fieldinfo)
		throws IOException;

	public void merge(MergeState mergeState)
		throws IOException
	{
		DocValues docValues[] = new DocValues[mergeState.readers.size()];
		Iterator i$ = mergeState.fieldInfos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FieldInfo fieldInfo = (FieldInfo)i$.next();
			mergeState.fieldInfo = fieldInfo;
			if (canMerge(fieldInfo))
			{
				for (int i = 0; i < docValues.length; i++)
					docValues[i] = getDocValuesForMerge((AtomicReader)mergeState.readers.get(i), fieldInfo);

				org.apache.lucene.index.DocValues.Type docValuesType = getDocValuesType(fieldInfo);
				if (!$assertionsDisabled && docValuesType == null)
					throw new AssertionError();
				DocValuesConsumer docValuesConsumer = addValuesField(docValuesType, fieldInfo);
				if (!$assertionsDisabled && docValuesConsumer == null)
					throw new AssertionError();
				docValuesConsumer.merge(mergeState, docValues);
			}
		} while (true);
	}

	protected DocValues getDocValuesForMerge(AtomicReader reader, FieldInfo info)
		throws IOException
	{
		return reader.docValues(info.name);
	}

	protected boolean canMerge(FieldInfo info)
	{
		return info.hasDocValues();
	}

	protected org.apache.lucene.index.DocValues.Type getDocValuesType(FieldInfo info)
	{
		return info.getDocValuesType();
	}

	public abstract void abort();

	public abstract void close()
		throws IOException;

}
