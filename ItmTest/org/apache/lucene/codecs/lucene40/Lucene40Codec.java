// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40Codec.java

package org.apache.lucene.codecs.lucene40;

import org.apache.lucene.codecs.*;
import org.apache.lucene.codecs.perfield.PerFieldPostingsFormat;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40StoredFieldsFormat, Lucene40TermVectorsFormat, Lucene40FieldInfosFormat, Lucene40DocValuesFormat, 
//			Lucene40SegmentInfoFormat, Lucene40NormsFormat, Lucene40LiveDocsFormat

public class Lucene40Codec extends Codec
{

	private final StoredFieldsFormat fieldsFormat = new Lucene40StoredFieldsFormat();
	private final TermVectorsFormat vectorsFormat = new Lucene40TermVectorsFormat();
	private final FieldInfosFormat fieldInfosFormat = new Lucene40FieldInfosFormat();
	private final DocValuesFormat docValuesFormat = new Lucene40DocValuesFormat();
	private final SegmentInfoFormat infosFormat = new Lucene40SegmentInfoFormat();
	private final NormsFormat normsFormat = new Lucene40NormsFormat();
	private final LiveDocsFormat liveDocsFormat = new Lucene40LiveDocsFormat();
	private final PostingsFormat postingsFormat = new PerFieldPostingsFormat() {

		final Lucene40Codec this$0;

		public PostingsFormat getPostingsFormatForField(String field)
		{
			return Lucene40Codec.this.getPostingsFormatForField(field);
		}

			
			{
				this$0 = Lucene40Codec.this;
				super();
			}
	};
	private final PostingsFormat defaultFormat = PostingsFormat.forName("Lucene40");

	public Lucene40Codec()
	{
		super("Lucene40");
	}

	public final StoredFieldsFormat storedFieldsFormat()
	{
		return fieldsFormat;
	}

	public final TermVectorsFormat termVectorsFormat()
	{
		return vectorsFormat;
	}

	public final DocValuesFormat docValuesFormat()
	{
		return docValuesFormat;
	}

	public final PostingsFormat postingsFormat()
	{
		return postingsFormat;
	}

	public final FieldInfosFormat fieldInfosFormat()
	{
		return fieldInfosFormat;
	}

	public final SegmentInfoFormat segmentInfoFormat()
	{
		return infosFormat;
	}

	public final NormsFormat normsFormat()
	{
		return normsFormat;
	}

	public final LiveDocsFormat liveDocsFormat()
	{
		return liveDocsFormat;
	}

	public PostingsFormat getPostingsFormatForField(String field)
	{
		return defaultFormat;
	}
}
