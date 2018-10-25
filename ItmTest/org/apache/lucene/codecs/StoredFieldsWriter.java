// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StoredFieldsWriter.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;

public abstract class StoredFieldsWriter
	implements Closeable
{

	protected StoredFieldsWriter()
	{
	}

	public abstract void startDocument(int i)
		throws IOException;

	public abstract void writeField(FieldInfo fieldinfo, IndexableField indexablefield)
		throws IOException;

	public abstract void abort();

	public abstract void finish(FieldInfos fieldinfos, int i)
		throws IOException;

	public int merge(MergeState mergeState)
		throws IOException
	{
		int docCount = 0;
		for (Iterator i$ = mergeState.readers.iterator(); i$.hasNext();)
		{
			AtomicReader reader = (AtomicReader)i$.next();
			int maxDoc = reader.maxDoc();
			Bits liveDocs = reader.getLiveDocs();
			int i = 0;
			while (i < maxDoc) 
			{
				if (liveDocs == null || liveDocs.get(i))
				{
					org.apache.lucene.document.Document doc = reader.document(i);
					addDocument(doc, mergeState.fieldInfos);
					docCount++;
					mergeState.checkAbort.work(300D);
				}
				i++;
			}
		}

		finish(mergeState.fieldInfos, docCount);
		return docCount;
	}

	protected final void addDocument(Iterable doc, FieldInfos fieldInfos)
		throws IOException
	{
		int storedCount = 0;
		Iterator i$ = doc.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexableField field = (IndexableField)i$.next();
			if (field.fieldType().stored())
				storedCount++;
		} while (true);
		startDocument(storedCount);
		i$ = doc.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexableField field = (IndexableField)i$.next();
			if (field.fieldType().stored())
				writeField(fieldInfos.fieldInfo(field.name()), field);
		} while (true);
	}

	public abstract void close()
		throws IOException;
}
