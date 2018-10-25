// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PostingsFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;
import java.util.Set;
import org.apache.lucene.codecs.lucene40.Lucene40PostingsFormat;
import org.apache.lucene.index.SegmentReadState;
import org.apache.lucene.index.SegmentWriteState;
import org.apache.lucene.util.NamedSPILoader;

// Referenced classes of package org.apache.lucene.codecs:
//			FieldsConsumer, FieldsProducer

public abstract class PostingsFormat
	implements org.apache.lucene.util.NamedSPILoader.NamedSPI
{

	public static final PostingsFormat EMPTY[] = new PostingsFormat[0];
	private final String name;
	private static final Lucene40PostingsFormat _format = new Lucene40PostingsFormat();

	protected PostingsFormat(String name)
	{
		NamedSPILoader.checkServiceName(name);
		this.name = name;
	}

	public final String getName()
	{
		return name;
	}

	public abstract FieldsConsumer fieldsConsumer(SegmentWriteState segmentwritestate)
		throws IOException;

	public abstract FieldsProducer fieldsProducer(SegmentReadState segmentreadstate)
		throws IOException;

	public String toString()
	{
		return (new StringBuilder()).append("PostingsFormat(name=").append(name).append(")").toString();
	}

	public static PostingsFormat forName(String name)
	{
		return _format;
	}

	public static Set availablePostingsFormats()
	{
		return null;
	}

	public static void reloadPostingsFormats(ClassLoader classloader1)
	{
	}

}
