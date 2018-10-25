// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xSegmentInfoReader.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.SegmentInfoReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xSegmentInfoFormat, Lucene3xStoredFieldsReader

/**
 * @deprecated Class Lucene3xSegmentInfoReader is deprecated
 */

public class Lucene3xSegmentInfoReader extends SegmentInfoReader
{

	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xSegmentInfoReader.desiredAssertionStatus();

	public Lucene3xSegmentInfoReader()
	{
	}

	public static void readLegacyInfos(SegmentInfos infos, Directory directory, IndexInput input, int format)
		throws IOException
	{
		Lucene3xSegmentInfoReader reader;
		int i;
		infos.version = input.readLong();
		infos.counter = input.readInt();
		reader = new Lucene3xSegmentInfoReader();
		i = input.readInt();
_L3:
		if (i <= 0) goto _L2; else goto _L1
_L1:
		SegmentInfoPerCommit siPerCommit;
		SegmentInfo si;
		Directory dir;
		siPerCommit = reader.readLegacySegmentInfo(directory, format, input);
		si = siPerCommit.info;
		if (si.getVersion() != null)
			break MISSING_BLOCK_LABEL_196;
		dir = directory;
		if (Lucene3xSegmentInfoFormat.getDocStoreOffset(si) != -1)
		{
			if (Lucene3xSegmentInfoFormat.getDocStoreIsCompoundFile(si))
				dir = new CompoundFileDirectory(dir, IndexFileNames.segmentFileName(Lucene3xSegmentInfoFormat.getDocStoreSegment(si), "", "cfx"), IOContext.READONCE, false);
		} else
		if (si.getUseCompoundFile())
			dir = new CompoundFileDirectory(dir, IndexFileNames.segmentFileName(si.name, "", "cfs"), IOContext.READONCE, false);
		Lucene3xStoredFieldsReader.checkCodeVersion(dir, Lucene3xSegmentInfoFormat.getDocStoreSegment(si));
		if (dir != directory)
			dir.close();
		break MISSING_BLOCK_LABEL_186;
		Exception exception;
		exception;
		if (dir != directory)
			dir.close();
		throw exception;
		si.setVersion("3.0");
		break MISSING_BLOCK_LABEL_254;
		if (si.getVersion().equals("2.x"))
			throw new IndexFormatTooOldException((new StringBuilder()).append("segment ").append(si.name).append(" in resource ").append(input).toString(), si.getVersion());
		infos.add(siPerCommit);
		i--;
		  goto _L3
_L2:
		infos.userData = input.readStringStringMap();
		return;
	}

	public SegmentInfo read(Directory directory, String segmentName, IOContext context)
		throws IOException
	{
		boolean success;
		IndexInput input;
		String fileName = IndexFileNames.segmentFileName(segmentName, "", "si");
		success = false;
		input = directory.openInput(fileName, context);
		SegmentInfo segmentinfo;
		SegmentInfo si = readUpgradedSegmentInfo(segmentName, directory, input);
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

	private static void addIfExists(Directory dir, Set files, String fileName)
		throws IOException
	{
		if (dir.fileExists(fileName))
			files.add(fileName);
	}

	private SegmentInfoPerCommit readLegacySegmentInfo(Directory dir, int format, IndexInput input)
		throws IOException
	{
		String version;
		String name;
		int docCount;
		long delGen;
		Map attributes;
		boolean isCompoundFile;
		int delCount;
		Map diagnostics;
		Set files;
label0:
		{
			if (format > -9)
				throw new IndexFormatTooOldException(input, format, -9, -11);
			if (format < -11)
				throw new IndexFormatTooNewException(input, format, -9, -11);
			if (format <= -11)
				version = input.readString();
			else
				version = null;
			name = input.readString();
			docCount = input.readInt();
			delGen = input.readLong();
			int docStoreOffset = input.readInt();
			attributes = new HashMap();
			String docStoreSegment;
			boolean docStoreIsCompoundFile;
			if (docStoreOffset != -1)
			{
				docStoreSegment = input.readString();
				docStoreIsCompoundFile = input.readByte() == 1;
				attributes.put(Lucene3xSegmentInfoFormat.DS_OFFSET_KEY, Integer.toString(docStoreOffset));
				attributes.put(Lucene3xSegmentInfoFormat.DS_NAME_KEY, docStoreSegment);
				attributes.put(Lucene3xSegmentInfoFormat.DS_COMPOUND_KEY, Boolean.toString(docStoreIsCompoundFile));
			} else
			{
				docStoreSegment = name;
				docStoreIsCompoundFile = false;
			}
			byte b = input.readByte();
			if (!$assertionsDisabled && 1 != b)
				throw new AssertionError((new StringBuilder()).append("expected 1 but was: ").append(b).append(" format: ").append(format).toString());
			int numNormGen = input.readInt();
			Map normGen;
			if (numNormGen == -1)
			{
				normGen = null;
			} else
			{
				normGen = new HashMap();
				for (int j = 0; j < numNormGen; j++)
					normGen.put(Integer.valueOf(j), Long.valueOf(input.readLong()));

			}
			isCompoundFile = input.readByte() == 1;
			delCount = input.readInt();
			if (!$assertionsDisabled && delCount > docCount)
				throw new AssertionError();
			boolean hasProx = input.readByte() == 1;
			diagnostics = input.readStringStringMap();
			int hasVectors;
			if (format <= -10)
				hasVectors = input.readByte();
			files = new HashSet();
			if (isCompoundFile)
			{
				files.add(IndexFileNames.segmentFileName(name, "", "cfs"));
			} else
			{
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "fnm"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "frq"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "prx"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "tis"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "tii"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "nrm"));
			}
			if (docStoreOffset != -1)
			{
				if (docStoreIsCompoundFile)
				{
					files.add(IndexFileNames.segmentFileName(docStoreSegment, "", "cfx"));
				} else
				{
					files.add(IndexFileNames.segmentFileName(docStoreSegment, "", "fdx"));
					files.add(IndexFileNames.segmentFileName(docStoreSegment, "", "fdt"));
					addIfExists(dir, files, IndexFileNames.segmentFileName(docStoreSegment, "", "tvx"));
					addIfExists(dir, files, IndexFileNames.segmentFileName(docStoreSegment, "", "tvf"));
					addIfExists(dir, files, IndexFileNames.segmentFileName(docStoreSegment, "", "tvd"));
				}
			} else
			if (!isCompoundFile)
			{
				files.add(IndexFileNames.segmentFileName(name, "", "fdx"));
				files.add(IndexFileNames.segmentFileName(name, "", "fdt"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "tvx"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "tvf"));
				addIfExists(dir, files, IndexFileNames.segmentFileName(name, "", "tvd"));
			}
			if (normGen == null)
				break label0;
			attributes.put(Lucene3xSegmentInfoFormat.NORMGEN_KEY, Integer.toString(numNormGen));
			long gen;
label1:
			do
			{
				java.util.Map.Entry ent;
				for (Iterator i$ = normGen.entrySet().iterator(); i$.hasNext(); attributes.put((new StringBuilder()).append(Lucene3xSegmentInfoFormat.NORMGEN_PREFIX).append(ent.getKey()).toString(), Long.toString(gen)))
				{
					ent = (java.util.Map.Entry)i$.next();
					gen = ((Long)ent.getValue()).longValue();
					if (gen < 1L)
						continue label1;
					files.add(IndexFileNames.fileNameFromGeneration(name, (new StringBuilder()).append("s").append(ent.getKey()).toString(), gen));
				}

				break label0;
			} while (gen == -1L || $assertionsDisabled);
			throw new AssertionError();
		}
		SegmentInfo info = new SegmentInfo(dir, version, name, docCount, isCompoundFile, null, diagnostics, Collections.unmodifiableMap(attributes));
		info.setFiles(files);
		SegmentInfoPerCommit infoPerCommit = new SegmentInfoPerCommit(info, delCount, delGen);
		return infoPerCommit;
	}

	private SegmentInfo readUpgradedSegmentInfo(String name, Directory dir, IndexInput input)
		throws IOException
	{
		CodecUtil.checkHeader(input, "Lucene3xSegmentInfo", 0, 0);
		String version = input.readString();
		int docCount = input.readInt();
		Map attributes = input.readStringStringMap();
		boolean isCompoundFile = input.readByte() == 1;
		Map diagnostics = input.readStringStringMap();
		Set files = input.readStringSet();
		SegmentInfo info = new SegmentInfo(dir, version, name, docCount, isCompoundFile, null, diagnostics, Collections.unmodifiableMap(attributes));
		info.setFiles(files);
		return info;
	}

}
