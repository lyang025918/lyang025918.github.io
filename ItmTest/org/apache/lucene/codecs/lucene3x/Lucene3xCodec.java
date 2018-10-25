// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xCodec.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.codecs.*;
import org.apache.lucene.codecs.lucene40.Lucene40LiveDocsFormat;
import org.apache.lucene.index.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xPostingsFormat, Lucene3xStoredFieldsFormat, Lucene3xTermVectorsFormat, Lucene3xFieldInfosFormat, 
//			Lucene3xSegmentInfoFormat, Lucene3xNormsFormat

/**
 * @deprecated Class Lucene3xCodec is deprecated
 */

public class Lucene3xCodec extends Codec
{

	private final PostingsFormat postingsFormat = new Lucene3xPostingsFormat();
	private final StoredFieldsFormat fieldsFormat = new Lucene3xStoredFieldsFormat();
	private final TermVectorsFormat vectorsFormat = new Lucene3xTermVectorsFormat();
	private final FieldInfosFormat fieldInfosFormat = new Lucene3xFieldInfosFormat();
	private final SegmentInfoFormat infosFormat = new Lucene3xSegmentInfoFormat();
	private final Lucene3xNormsFormat normsFormat = new Lucene3xNormsFormat();
	static final String COMPOUND_FILE_STORE_EXTENSION = "cfx";
	private final LiveDocsFormat liveDocsFormat = new Lucene40LiveDocsFormat();
	private final DocValuesFormat docValuesFormat = new DocValuesFormat() {

		final Lucene3xCodec this$0;

		public PerDocConsumer docsConsumer(PerDocWriteState state)
			throws IOException
		{
			return null;
		}

		public PerDocProducer docsProducer(SegmentReadState state)
			throws IOException
		{
			return null;
		}

			
			{
				this$0 = Lucene3xCodec.this;
				super();
			}
	};

	public Lucene3xCodec()
	{
		super("Lucene3x");
	}

	public PostingsFormat postingsFormat()
	{
		return postingsFormat;
	}

	public DocValuesFormat docValuesFormat()
	{
		return docValuesFormat;
	}

	public StoredFieldsFormat storedFieldsFormat()
	{
		return fieldsFormat;
	}

	public TermVectorsFormat termVectorsFormat()
	{
		return vectorsFormat;
	}

	public FieldInfosFormat fieldInfosFormat()
	{
		return fieldInfosFormat;
	}

	public SegmentInfoFormat segmentInfoFormat()
	{
		return infosFormat;
	}

	public NormsFormat normsFormat()
	{
		return normsFormat;
	}

	public LiveDocsFormat liveDocsFormat()
	{
		return liveDocsFormat;
	}

	public static Set getDocStoreFiles(SegmentInfo info)
	{
		if (Lucene3xSegmentInfoFormat.getDocStoreOffset(info) != -1)
		{
			String dsName = Lucene3xSegmentInfoFormat.getDocStoreSegment(info);
			Set files = new HashSet();
			if (Lucene3xSegmentInfoFormat.getDocStoreIsCompoundFile(info))
			{
				files.add(IndexFileNames.segmentFileName(dsName, "", "cfx"));
			} else
			{
				files.add(IndexFileNames.segmentFileName(dsName, "", "fdx"));
				files.add(IndexFileNames.segmentFileName(dsName, "", "fdt"));
				files.add(IndexFileNames.segmentFileName(dsName, "", "tvx"));
				files.add(IndexFileNames.segmentFileName(dsName, "", "tvf"));
				files.add(IndexFileNames.segmentFileName(dsName, "", "tvd"));
			}
			return files;
		} else
		{
			return null;
		}
	}
}
