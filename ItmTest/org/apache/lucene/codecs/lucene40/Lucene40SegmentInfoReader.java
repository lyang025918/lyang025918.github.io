// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40SegmentInfoReader.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.SegmentInfoReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

public class Lucene40SegmentInfoReader extends SegmentInfoReader
{

	public Lucene40SegmentInfoReader()
	{
	}

	public SegmentInfo read(Directory dir, String segment, IOContext context)
		throws IOException
	{
		String fileName;
		IndexInput input;
		boolean success;
		fileName = IndexFileNames.segmentFileName(segment, "", "si");
		input = dir.openInput(fileName, context);
		success = false;
		SegmentInfo segmentinfo;
		CodecUtil.checkHeader(input, "Lucene40SegmentInfo", 0, 0);
		String version = input.readString();
		int docCount = input.readInt();
		if (docCount < 0)
			throw new CorruptIndexException((new StringBuilder()).append("invalid docCount: ").append(docCount).append(" (resource=").append(input).append(")").toString());
		boolean isCompoundFile = input.readByte() == 1;
		Map diagnostics = input.readStringStringMap();
		Map attributes = input.readStringStringMap();
		Set files = input.readStringSet();
		if (input.getFilePointer() != input.length())
			throw new CorruptIndexException((new StringBuilder()).append("did not read all bytes from file \"").append(fileName).append("\": read ").append(input.getFilePointer()).append(" vs size ").append(input.length()).append(" (resource: ").append(input).append(")").toString());
		SegmentInfo si = new SegmentInfo(dir, version, segment, docCount, isCompoundFile, null, diagnostics, Collections.unmodifiableMap(attributes));
		si.setFiles(files);
		success = true;
		segmentinfo = si;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		else
			input.close();
		return segmentinfo;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		else
			input.close();
		throw exception;
	}
}
