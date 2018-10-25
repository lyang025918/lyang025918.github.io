// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xTermVectorsFormat.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xTermVectorsReader, Lucene3xSegmentInfoFormat

/**
 * @deprecated Class Lucene3xTermVectorsFormat is deprecated
 */

class Lucene3xTermVectorsFormat extends TermVectorsFormat
{

	Lucene3xTermVectorsFormat()
	{
	}

	public TermVectorsReader vectorsReader(Directory directory, SegmentInfo segmentInfo, FieldInfos fieldInfos, IOContext context)
		throws IOException
	{
		String fileName;
		Directory cfsDir;
		fileName = IndexFileNames.segmentFileName(Lucene3xSegmentInfoFormat.getDocStoreSegment(segmentInfo), "", "tvf");
		if (Lucene3xSegmentInfoFormat.getDocStoreOffset(segmentInfo) == -1 || !Lucene3xSegmentInfoFormat.getDocStoreIsCompoundFile(segmentInfo))
			break MISSING_BLOCK_LABEL_107;
		String cfxFileName = IndexFileNames.segmentFileName(Lucene3xSegmentInfoFormat.getDocStoreSegment(segmentInfo), "", "cfx");
		if (!segmentInfo.dir.fileExists(cfxFileName))
			break MISSING_BLOCK_LABEL_101;
		cfsDir = new CompoundFileDirectory(segmentInfo.dir, cfxFileName, context, false);
		boolean exists = cfsDir.fileExists(fileName);
		cfsDir.close();
		break MISSING_BLOCK_LABEL_115;
		Exception exception;
		exception;
		cfsDir.close();
		throw exception;
		exists = false;
		break MISSING_BLOCK_LABEL_115;
		exists = directory.fileExists(fileName);
		if (!exists)
			return null;
		else
			return new Lucene3xTermVectorsReader(directory, segmentInfo, fieldInfos, context);
	}

	public TermVectorsWriter vectorsWriter(Directory directory, SegmentInfo segmentInfo, IOContext context)
		throws IOException
	{
		throw new UnsupportedOperationException("this codec can only be used for reading");
	}
}
