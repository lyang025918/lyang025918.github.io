// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocValuesWriterBase.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Counter;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			Writer

public abstract class DocValuesWriterBase extends PerDocConsumer
{

	protected final String segmentName;
	private final Counter bytesUsed;
	protected final IOContext context;
	private final float acceptableOverheadRatio;
	public static final String INDEX_EXTENSION = "idx";
	public static final String DATA_EXTENSION = "dat";

	protected DocValuesWriterBase(PerDocWriteState state)
	{
		this(state, 0.5F);
	}

	protected DocValuesWriterBase(PerDocWriteState state, float acceptableOverheadRatio)
	{
		segmentName = state.segmentInfo.name;
		bytesUsed = state.bytesUsed;
		context = state.context;
		this.acceptableOverheadRatio = acceptableOverheadRatio;
	}

	protected abstract Directory getDirectory()
		throws IOException;

	public void close()
		throws IOException
	{
	}

	public DocValuesConsumer addValuesField(org.apache.lucene.index.DocValues.Type valueType, FieldInfo field)
		throws IOException
	{
		return Writer.create(valueType, PerDocProducerBase.docValuesId(segmentName, field.number), getDirectory(), getComparator(), bytesUsed, context, acceptableOverheadRatio);
	}

	public Comparator getComparator()
		throws IOException
	{
		return BytesRef.getUTF8SortedAsUnicodeComparator();
	}
}
