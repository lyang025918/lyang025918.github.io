// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilterCodec.java

package org.apache.lucene.codecs;


// Referenced classes of package org.apache.lucene.codecs:
//			Codec, DocValuesFormat, FieldInfosFormat, LiveDocsFormat, 
//			NormsFormat, PostingsFormat, SegmentInfoFormat, StoredFieldsFormat, 
//			TermVectorsFormat

public abstract class FilterCodec extends Codec
{

	protected final Codec delegate;

	protected FilterCodec(String name, Codec delegate)
	{
		super(name);
		this.delegate = delegate;
	}

	public DocValuesFormat docValuesFormat()
	{
		return delegate.docValuesFormat();
	}

	public FieldInfosFormat fieldInfosFormat()
	{
		return delegate.fieldInfosFormat();
	}

	public LiveDocsFormat liveDocsFormat()
	{
		return delegate.liveDocsFormat();
	}

	public NormsFormat normsFormat()
	{
		return delegate.normsFormat();
	}

	public PostingsFormat postingsFormat()
	{
		return delegate.postingsFormat();
	}

	public SegmentInfoFormat segmentInfoFormat()
	{
		return delegate.segmentInfoFormat();
	}

	public StoredFieldsFormat storedFieldsFormat()
	{
		return delegate.storedFieldsFormat();
	}

	public TermVectorsFormat termVectorsFormat()
	{
		return delegate.termVectorsFormat();
	}
}
