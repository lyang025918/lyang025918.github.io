// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerDocProducerBase.java

package org.apache.lucene.codecs;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.codecs:
//			PerDocProducer

public abstract class PerDocProducerBase extends PerDocProducer
{

	protected abstract void closeInternal(Collection collection)
		throws IOException;

	protected abstract Map docValues();

	protected PerDocProducerBase()
	{
	}

	public void close()
		throws IOException
	{
		closeInternal(docValues().values());
	}

	public DocValues docValues(String field)
		throws IOException
	{
		return (DocValues)docValues().get(field);
	}

	public Comparator getComparator()
		throws IOException
	{
		return BytesRef.getUTF8SortedAsUnicodeComparator();
	}

	protected TreeMap load(FieldInfos fieldInfos, String segment, int docCount, Directory dir, IOContext context)
		throws IOException
	{
		TreeMap values;
		boolean success;
		values = new TreeMap();
		success = false;
		Iterator i$ = fieldInfos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FieldInfo fieldInfo = (FieldInfo)i$.next();
			if (canLoad(fieldInfo))
			{
				String field = fieldInfo.name;
				String id = docValuesId(segment, fieldInfo.number);
				values.put(field, loadDocValues(docCount, dir, id, getDocValuesType(fieldInfo), context));
			}
		} while (true);
		success = true;
		if (!success)
			try
			{
				closeInternal(values.values());
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_144;
		Exception exception;
		exception;
		if (!success)
			try
			{
				closeInternal(values.values());
			}
			catch (Throwable t) { }
		throw exception;
		return values;
	}

	protected boolean canLoad(FieldInfo info)
	{
		return info.hasDocValues();
	}

	protected org.apache.lucene.index.DocValues.Type getDocValuesType(FieldInfo info)
	{
		return info.getDocValuesType();
	}

	protected boolean anyDocValuesFields(FieldInfos infos)
	{
		return infos.hasDocValues();
	}

	public static String docValuesId(String segmentsName, int fieldId)
	{
		return (new StringBuilder()).append(segmentsName).append("_").append(fieldId).toString();
	}

	protected abstract DocValues loadDocValues(int i, Directory directory, String s, org.apache.lucene.index.DocValues.Type type, IOContext iocontext)
		throws IOException;
}
