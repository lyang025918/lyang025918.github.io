// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40DocValuesConsumer.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.lucene40.values.DocValuesWriterBase;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.PerDocWriteState;
import org.apache.lucene.store.CompoundFileDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.IOUtils;

public class Lucene40DocValuesConsumer extends DocValuesWriterBase
{

	private final Directory mainDirectory;
	private Directory directory;
	private final String segmentSuffix;
	public static final String DOC_VALUES_SEGMENT_SUFFIX = "dv";

	public Lucene40DocValuesConsumer(PerDocWriteState state, String segmentSuffix)
	{
		super(state);
		this.segmentSuffix = segmentSuffix;
		mainDirectory = state.directory;
	}

	protected Directory getDirectory()
		throws IOException
	{
		if (directory == null)
			directory = new CompoundFileDirectory(mainDirectory, IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfs"), context, true);
		return directory;
	}

	public void close()
		throws IOException
	{
		if (directory != null)
			directory.close();
	}

	public void abort()
	{
label0:
		{
			try
			{
				close();
			}
			catch (Throwable t)
			{
				IOUtils.deleteFilesIgnoringExceptions(mainDirectory, new String[] {
					IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfs"), IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfe")
				});
				break label0;
			}
			finally
			{
				IOUtils.deleteFilesIgnoringExceptions(mainDirectory, new String[] {
					IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfs"), IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfe")
				});
				throw exception;
			}
			IOUtils.deleteFilesIgnoringExceptions(mainDirectory, new String[] {
				IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfs"), IndexFileNames.segmentFileName(segmentName, segmentSuffix, "cfe")
			});
			break label0;
		}
	}
}
