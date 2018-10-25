// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40SegmentInfoFormat.java

package org.apache.lucene.codecs.lucene40;

import org.apache.lucene.codecs.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40SegmentInfoReader, Lucene40SegmentInfoWriter

public class Lucene40SegmentInfoFormat extends SegmentInfoFormat
{

	private final SegmentInfoReader reader = new Lucene40SegmentInfoReader();
	private final SegmentInfoWriter writer = new Lucene40SegmentInfoWriter();
	public static final String SI_EXTENSION = "si";
	static final String CODEC_NAME = "Lucene40SegmentInfo";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	public Lucene40SegmentInfoFormat()
	{
	}

	public SegmentInfoReader getSegmentInfoReader()
	{
		return reader;
	}

	public SegmentInfoWriter getSegmentInfoWriter()
	{
		return writer;
	}
}
