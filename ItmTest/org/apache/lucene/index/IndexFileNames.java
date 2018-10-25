// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexFileNames.java

package org.apache.lucene.index;

import java.util.regex.Pattern;

public final class IndexFileNames
{

	public static final String SEGMENTS = "segments";
	public static final String GEN_EXTENSION = "gen";
	public static final String SEGMENTS_GEN = "segments.gen";
	public static final String COMPOUND_FILE_EXTENSION = "cfs";
	public static final String COMPOUND_FILE_ENTRIES_EXTENSION = "cfe";
	public static final String INDEX_EXTENSIONS[] = {
		"cfs", "cfe", "gen"
	};
	static final Pattern CODEC_FILE_PATTERN = Pattern.compile("_[a-z0-9]+(_.*)?\\..*");
	static final boolean $assertionsDisabled = !org/apache/lucene/index/IndexFileNames.desiredAssertionStatus();

	private IndexFileNames()
	{
	}

	public static String fileNameFromGeneration(String base, String ext, long gen)
	{
		if (gen == -1L)
			return null;
		if (gen == 0L)
			return segmentFileName(base, "", ext);
		if (!$assertionsDisabled && gen <= 0L)
			throw new AssertionError();
		StringBuilder res = (new StringBuilder(base.length() + 6 + ext.length())).append(base).append('_').append(Long.toString(gen, 36));
		if (ext.length() > 0)
			res.append('.').append(ext);
		return res.toString();
	}

	public static String segmentFileName(String segmentName, String segmentSuffix, String ext)
	{
		if (ext.length() > 0 || segmentSuffix.length() > 0)
		{
			if (!$assertionsDisabled && ext.startsWith("."))
				throw new AssertionError();
			StringBuilder sb = new StringBuilder(segmentName.length() + 2 + segmentSuffix.length() + ext.length());
			sb.append(segmentName);
			if (segmentSuffix.length() > 0)
				sb.append('_').append(segmentSuffix);
			if (ext.length() > 0)
				sb.append('.').append(ext);
			return sb.toString();
		} else
		{
			return segmentName;
		}
	}

	public static boolean matchesExtension(String filename, String ext)
	{
		return filename.endsWith((new StringBuilder()).append(".").append(ext).toString());
	}

	private static int indexOfSegmentName(String filename)
	{
		int idx = filename.indexOf('_', 1);
		if (idx == -1)
			idx = filename.indexOf('.');
		return idx;
	}

	public static String stripSegmentName(String filename)
	{
		int idx = indexOfSegmentName(filename);
		if (idx != -1)
			filename = filename.substring(idx);
		return filename;
	}

	public static String parseSegmentName(String filename)
	{
		int idx = indexOfSegmentName(filename);
		if (idx != -1)
			filename = filename.substring(0, idx);
		return filename;
	}

	public static String stripExtension(String filename)
	{
		int idx = filename.indexOf('.');
		if (idx != -1)
			filename = filename.substring(0, idx);
		return filename;
	}

}
