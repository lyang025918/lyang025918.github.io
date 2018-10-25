// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40SegmentInfoWriter.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.SegmentInfoWriter;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

public class Lucene40SegmentInfoWriter extends SegmentInfoWriter
{

	public Lucene40SegmentInfoWriter()
	{
	}

	public void write(Directory dir, SegmentInfo si, FieldInfos fis, IOContext ioContext)
		throws IOException
	{
		String fileName;
		IndexOutput output;
		boolean success;
		fileName = IndexFileNames.segmentFileName(si.name, "", "si");
		si.addFile(fileName);
		output = dir.createOutput(fileName, ioContext);
		success = false;
		CodecUtil.writeHeader(output, "Lucene40SegmentInfo", 0);
		output.writeString(si.getVersion());
		output.writeInt(si.getDocCount());
		output.writeByte((byte)(si.getUseCompoundFile() ? 1 : -1));
		output.writeStringStringMap(si.getDiagnostics());
		output.writeStringStringMap(si.attributes());
		output.writeStringSet(si.files());
		success = true;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				output
			});
			si.dir.deleteFile(fileName);
		} else
		{
			output.close();
		}
		break MISSING_BLOCK_LABEL_182;
		Exception exception;
		exception;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				output
			});
			si.dir.deleteFile(fileName);
		} else
		{
			output.close();
		}
		throw exception;
	}
}
