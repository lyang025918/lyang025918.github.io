// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xNormsProducer.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.PerDocProducer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xSegmentInfoFormat

/**
 * @deprecated Class Lucene3xNormsProducer is deprecated
 */

class Lucene3xNormsProducer extends PerDocProducer
{
	private class NormsDocValues extends DocValues
	{

		private final IndexInput file;
		private final long offset;
		final Lucene3xNormsProducer this$0;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return new NormSource(bytes());
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return getSource();
		}

		public org.apache.lucene.index.DocValues.Type getType()
		{
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_8;
		}

		byte[] bytes()
			throws IOException
		{
			byte bytes[] = new byte[maxdoc];
			synchronized (file)
			{
				file.seek(offset);
				file.readBytes(bytes, 0, bytes.length, false);
			}
			if (file != singleNormStream)
			{
				openFiles.remove(file);
				file.close();
			}
			return bytes;
		}

		public int getValueSize()
		{
			return 1;
		}

		public NormsDocValues(IndexInput normInput, long normSeek)
		{
			this$0 = Lucene3xNormsProducer.this;
			super();
			file = normInput;
			offset = normSeek;
		}
	}

	static final class NormSource extends org.apache.lucene.index.DocValues.Source
	{

		final byte bytes[];

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			ref.bytes = bytes;
			ref.offset = docID;
			ref.length = 1;
			return ref;
		}

		public long getInt(int docID)
		{
			return (long)bytes[docID];
		}

		public boolean hasArray()
		{
			return true;
		}

		public Object getArray()
		{
			return bytes;
		}

		protected NormSource(byte bytes[])
		{
			super(org.apache.lucene.index.DocValues.Type.FIXED_INTS_8);
			this.bytes = bytes;
		}
	}


	static final byte NORMS_HEADER[] = {
		78, 82, 77, -1
	};
	static final String NORMS_EXTENSION = "nrm";
	static final String SEPARATE_NORMS_EXTENSION = "s";
	final Map norms;
	final Set openFiles;
	IndexInput singleNormStream;
	final int maxdoc;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xNormsProducer.desiredAssertionStatus();

	public Lucene3xNormsProducer(Directory dir, SegmentInfo info, FieldInfos fields, IOContext context)
		throws IOException
	{
		Directory separateNormsDir;
		boolean success;
		norms = new HashMap();
		openFiles = Collections.newSetFromMap(new IdentityHashMap());
		separateNormsDir = info.dir;
		maxdoc = info.getDocCount();
		String segmentName = info.name;
		success = false;
		long nextNormSeek = NORMS_HEADER.length;
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FieldInfo fi = (FieldInfo)i$.next();
			if (fi.hasNorms())
			{
				String fileName = getNormFilename(info, fi.number);
				Directory d = hasSeparateNorms(info, fi.number) ? separateNormsDir : dir;
				boolean singleNormFile = IndexFileNames.matchesExtension(fileName, "nrm");
				IndexInput normInput = null;
				long normSeek;
				if (singleNormFile)
				{
					normSeek = nextNormSeek;
					if (singleNormStream == null)
					{
						singleNormStream = d.openInput(fileName, context);
						openFiles.add(singleNormStream);
					}
					normInput = singleNormStream;
				} else
				{
					normInput = d.openInput(fileName, context);
					openFiles.add(normInput);
					String version = info.getVersion();
					boolean isUnversioned = (version == null || StringHelper.getVersionComparator().compare(version, "3.2") < 0) && normInput.length() == (long)maxdoc;
					if (isUnversioned)
						normSeek = 0L;
					else
						normSeek = NORMS_HEADER.length;
				}
				NormsDocValues norm = new NormsDocValues(normInput, normSeek);
				norms.put(fi.name, norm);
				nextNormSeek += maxdoc;
			}
		} while (true);
		if (!$assertionsDisabled && singleNormStream != null && nextNormSeek != singleNormStream.length())
			throw new AssertionError(singleNormStream == null ? "null" : ((Object) ((new StringBuilder()).append("len: ").append(singleNormStream.length()).append(" expected: ").append(nextNormSeek).toString())));
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(openFiles);
		break MISSING_BLOCK_LABEL_438;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(openFiles);
		throw exception;
	}

	public DocValues docValues(String field)
		throws IOException
	{
		return (DocValues)norms.get(field);
	}

	public void close()
		throws IOException
	{
		IOUtils.close(openFiles);
		norms.clear();
		openFiles.clear();
		break MISSING_BLOCK_LABEL_49;
		Exception exception;
		exception;
		norms.clear();
		openFiles.clear();
		throw exception;
	}

	private static String getNormFilename(SegmentInfo info, int number)
	{
		if (hasSeparateNorms(info, number))
		{
			long gen = Long.parseLong(info.getAttribute((new StringBuilder()).append(Lucene3xSegmentInfoFormat.NORMGEN_PREFIX).append(number).toString()));
			return IndexFileNames.fileNameFromGeneration(info.name, (new StringBuilder()).append("s").append(number).toString(), gen);
		} else
		{
			return IndexFileNames.segmentFileName(info.name, "", "nrm");
		}
	}

	private static boolean hasSeparateNorms(SegmentInfo info, int number)
	{
		String v = info.getAttribute((new StringBuilder()).append(Lucene3xSegmentInfoFormat.NORMGEN_PREFIX).append(number).toString());
		if (v == null)
			return false;
		if (!$assertionsDisabled && Long.parseLong(v) == -1L)
			throw new AssertionError();
		else
			return true;
	}

}
