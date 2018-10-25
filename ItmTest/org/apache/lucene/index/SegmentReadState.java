// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentReadState.java

package org.apache.lucene.index;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfo, FieldInfos

public class SegmentReadState
{

	public final Directory dir;
	public final SegmentInfo segmentInfo;
	public final FieldInfos fieldInfos;
	public final IOContext context;
	public int termsIndexDivisor;
	public final String segmentSuffix;

	public SegmentReadState(Directory dir, SegmentInfo info, FieldInfos fieldInfos, IOContext context, int termsIndexDivisor)
	{
		this(dir, info, fieldInfos, context, termsIndexDivisor, "");
	}

	public SegmentReadState(Directory dir, SegmentInfo info, FieldInfos fieldInfos, IOContext context, int termsIndexDivisor, String segmentSuffix)
	{
		this.dir = dir;
		segmentInfo = info;
		this.fieldInfos = fieldInfos;
		this.context = context;
		this.termsIndexDivisor = termsIndexDivisor;
		this.segmentSuffix = segmentSuffix;
	}

	public SegmentReadState(SegmentReadState other, String newSegmentSuffix)
	{
		dir = other.dir;
		segmentInfo = other.segmentInfo;
		fieldInfos = other.fieldInfos;
		context = other.context;
		termsIndexDivisor = other.termsIndexDivisor;
		segmentSuffix = newSegmentSuffix;
	}
}
