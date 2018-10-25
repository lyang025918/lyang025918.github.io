// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlowCompositeReaderWrapper.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.index:
//			AtomicReader, CompositeReader, DocValues, MultiFields, 
//			MultiDocValues, Fields, IndexReader, StoredFieldVisitor, 
//			FieldInfos

public final class SlowCompositeReaderWrapper extends AtomicReader
{

	private final CompositeReader in;
	private final Map normsCache = new HashMap();
	private final Fields fields;
	private final Bits liveDocs;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SlowCompositeReaderWrapper.desiredAssertionStatus();

	public static AtomicReader wrap(IndexReader reader)
		throws IOException
	{
		if (reader instanceof CompositeReader)
			return new SlowCompositeReaderWrapper((CompositeReader)reader);
		if (!$assertionsDisabled && !(reader instanceof AtomicReader))
			throw new AssertionError();
		else
			return (AtomicReader)reader;
	}

	public SlowCompositeReaderWrapper(CompositeReader reader)
		throws IOException
	{
		in = reader;
		fields = MultiFields.getFields(in);
		liveDocs = MultiFields.getLiveDocs(in);
		in.registerParentReader(this);
	}

	public String toString()
	{
		return (new StringBuilder()).append("SlowCompositeReaderWrapper(").append(in).append(")").toString();
	}

	public Fields fields()
	{
		ensureOpen();
		return fields;
	}

	public DocValues docValues(String field)
		throws IOException
	{
		ensureOpen();
		return MultiDocValues.getDocValues(in, field);
	}

	public synchronized DocValues normValues(String field)
		throws IOException
	{
		ensureOpen();
		DocValues values = (DocValues)normsCache.get(field);
		if (values == null)
		{
			values = MultiDocValues.getNormDocValues(in, field);
			normsCache.put(field, values);
		}
		return values;
	}

	public Fields getTermVectors(int docID)
		throws IOException
	{
		ensureOpen();
		return in.getTermVectors(docID);
	}

	public int numDocs()
	{
		return in.numDocs();
	}

	public int maxDoc()
	{
		return in.maxDoc();
	}

	public void document(int docID, StoredFieldVisitor visitor)
		throws IOException
	{
		ensureOpen();
		in.document(docID, visitor);
	}

	public Bits getLiveDocs()
	{
		ensureOpen();
		return liveDocs;
	}

	public FieldInfos getFieldInfos()
	{
		ensureOpen();
		return MultiFields.getMergedFieldInfos(in);
	}

	public boolean hasDeletions()
	{
		ensureOpen();
		return liveDocs != null;
	}

	public Object getCoreCacheKey()
	{
		return in.getCoreCacheKey();
	}

	public Object getCombinedCoreAndDeletesKey()
	{
		return in.getCombinedCoreAndDeletesKey();
	}

	protected void doClose()
		throws IOException
	{
		in.close();
	}

}
