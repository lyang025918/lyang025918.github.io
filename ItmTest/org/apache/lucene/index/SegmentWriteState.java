// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentWriteState.java

package org.apache.lucene.index;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.MutableBits;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfo, FieldInfos, BufferedDeletes

public class SegmentWriteState
{

	public final InfoStream infoStream;
	public final Directory directory;
	public final SegmentInfo segmentInfo;
	public final FieldInfos fieldInfos;
	public int delCountOnFlush;
	public final BufferedDeletes segDeletes;
	public MutableBits liveDocs;
	public final String segmentSuffix;
	public int termIndexInterval;
	public final IOContext context;

	public SegmentWriteState(InfoStream infoStream, Directory directory, SegmentInfo segmentInfo, FieldInfos fieldInfos, int termIndexInterval, BufferedDeletes segDeletes, IOContext context)
	{
		this.infoStream = infoStream;
		this.segDeletes = segDeletes;
		this.directory = directory;
		this.segmentInfo = segmentInfo;
		this.fieldInfos = fieldInfos;
		this.termIndexInterval = termIndexInterval;
		segmentSuffix = "";
		this.context = context;
	}

	public SegmentWriteState(SegmentWriteState state, String segmentSuffix)
	{
		infoStream = state.infoStream;
		directory = state.directory;
		segmentInfo = state.segmentInfo;
		fieldInfos = state.fieldInfos;
		termIndexInterval = state.termIndexInterval;
		context = state.context;
		this.segmentSuffix = segmentSuffix;
		segDeletes = state.segDeletes;
		delCountOnFlush = state.delCountOnFlush;
	}
}
