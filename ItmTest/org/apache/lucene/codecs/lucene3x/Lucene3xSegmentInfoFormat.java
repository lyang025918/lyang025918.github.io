// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xSegmentInfoFormat.java

package org.apache.lucene.codecs.lucene3x;

import org.apache.lucene.codecs.*;
import org.apache.lucene.index.SegmentInfo;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xSegmentInfoReader

/**
 * @deprecated Class Lucene3xSegmentInfoFormat is deprecated
 */

public class Lucene3xSegmentInfoFormat extends SegmentInfoFormat
{

	private final SegmentInfoReader reader = new Lucene3xSegmentInfoReader();
	public static final int FORMAT_DIAGNOSTICS = -9;
	public static final int FORMAT_HAS_VECTORS = -10;
	public static final int FORMAT_3_1 = -11;
	public static final String UPGRADED_SI_EXTENSION = "si";
	public static final String UPGRADED_SI_CODEC_NAME = "Lucene3xSegmentInfo";
	public static final int UPGRADED_SI_VERSION_START = 0;
	public static final int UPGRADED_SI_VERSION_CURRENT = 0;
	public static final String DS_OFFSET_KEY = (new StringBuilder()).append(org/apache/lucene/codecs/lucene3x/Lucene3xSegmentInfoFormat.getSimpleName()).append(".dsoffset").toString();
	public static final String DS_NAME_KEY = (new StringBuilder()).append(org/apache/lucene/codecs/lucene3x/Lucene3xSegmentInfoFormat.getSimpleName()).append(".dsname").toString();
	public static final String DS_COMPOUND_KEY = (new StringBuilder()).append(org/apache/lucene/codecs/lucene3x/Lucene3xSegmentInfoFormat.getSimpleName()).append(".dscompound").toString();
	public static final String NORMGEN_KEY = (new StringBuilder()).append(org/apache/lucene/codecs/lucene3x/Lucene3xSegmentInfoFormat.getSimpleName()).append(".normgen").toString();
	public static final String NORMGEN_PREFIX = (new StringBuilder()).append(org/apache/lucene/codecs/lucene3x/Lucene3xSegmentInfoFormat.getSimpleName()).append(".normfield").toString();

	public Lucene3xSegmentInfoFormat()
	{
	}

	public SegmentInfoReader getSegmentInfoReader()
	{
		return reader;
	}

	public SegmentInfoWriter getSegmentInfoWriter()
	{
		throw new UnsupportedOperationException("this codec can only be used for reading");
	}

	public static int getDocStoreOffset(SegmentInfo si)
	{
		String v = si.getAttribute(DS_OFFSET_KEY);
		return v != null ? Integer.parseInt(v) : -1;
	}

	public static String getDocStoreSegment(SegmentInfo si)
	{
		String v = si.getAttribute(DS_NAME_KEY);
		return v != null ? v : si.name;
	}

	public static boolean getDocStoreIsCompoundFile(SegmentInfo si)
	{
		String v = si.getAttribute(DS_COMPOUND_KEY);
		return v != null ? Boolean.parseBoolean(v) : false;
	}

}
