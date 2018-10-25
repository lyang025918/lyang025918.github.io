// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Codec.java

package org.apache.lucene.codecs;

import java.util.Set;
import org.apache.lucene.codecs.lucene40.Lucene40Codec;
import org.apache.lucene.util.NamedSPILoader;

// Referenced classes of package org.apache.lucene.codecs:
//			PostingsFormat, DocValuesFormat, StoredFieldsFormat, TermVectorsFormat, 
//			FieldInfosFormat, SegmentInfoFormat, NormsFormat, LiveDocsFormat

public abstract class Codec
	implements org.apache.lucene.util.NamedSPILoader.NamedSPI
{

	private final String name;
	private static final Lucene40Codec _codec = new Lucene40Codec();
	private static Codec defaultCodec = forName("Lucene40");

	protected Codec(String name)
	{
		NamedSPILoader.checkServiceName(name);
		this.name = name;
	}

	public final String getName()
	{
		return name;
	}

	public abstract PostingsFormat postingsFormat();

	public abstract DocValuesFormat docValuesFormat();

	public abstract StoredFieldsFormat storedFieldsFormat();

	public abstract TermVectorsFormat termVectorsFormat();

	public abstract FieldInfosFormat fieldInfosFormat();

	public abstract SegmentInfoFormat segmentInfoFormat();

	public abstract NormsFormat normsFormat();

	public abstract LiveDocsFormat liveDocsFormat();

	public static Codec forName(String name)
	{
		return _codec;
	}

	public static Set availableCodecs()
	{
		return null;
	}

	public static void reloadCodecs(ClassLoader classloader1)
	{
	}

	public static Codec getDefault()
	{
		return defaultCodec;
	}

	public static void setDefault(Codec codec)
	{
		defaultCodec = codec;
	}

	public String toString()
	{
		return name;
	}

}
