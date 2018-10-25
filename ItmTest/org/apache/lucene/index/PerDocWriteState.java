// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerDocWriteState.java

package org.apache.lucene.index;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.Counter;
import org.apache.lucene.util.InfoStream;

// Referenced classes of package org.apache.lucene.index:
//			SegmentWriteState, SegmentInfo

public class PerDocWriteState
{

	public final InfoStream infoStream;
	public final Directory directory;
	public final SegmentInfo segmentInfo;
	public final Counter bytesUsed;
	public final String segmentSuffix;
	public final IOContext context;

	public PerDocWriteState(InfoStream infoStream, Directory directory, SegmentInfo segmentInfo, Counter bytesUsed, String segmentSuffix, IOContext context)
	{
		this.infoStream = infoStream;
		this.directory = directory;
		this.segmentInfo = segmentInfo;
		this.segmentSuffix = segmentSuffix;
		this.bytesUsed = bytesUsed;
		this.context = context;
	}

	public PerDocWriteState(SegmentWriteState state)
	{
		infoStream = state.infoStream;
		directory = state.directory;
		segmentInfo = state.segmentInfo;
		segmentSuffix = state.segmentSuffix;
		bytesUsed = Counter.newCounter();
		context = state.context;
	}

	public PerDocWriteState(PerDocWriteState state, String segmentSuffix)
	{
		infoStream = state.infoStream;
		directory = state.directory;
		segmentInfo = state.segmentInfo;
		this.segmentSuffix = segmentSuffix;
		bytesUsed = state.bytesUsed;
		context = state.context;
	}
}
