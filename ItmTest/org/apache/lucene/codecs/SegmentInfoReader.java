// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentInfoReader.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

public abstract class SegmentInfoReader
{

	protected SegmentInfoReader()
	{
	}

	public abstract SegmentInfo read(Directory directory, String s, IOContext iocontext)
		throws IOException;
}
