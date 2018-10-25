// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40FieldInfosFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40FieldInfosReader, Lucene40FieldInfosWriter

public class Lucene40FieldInfosFormat extends FieldInfosFormat
{

	private final FieldInfosReader reader = new Lucene40FieldInfosReader();
	private final FieldInfosWriter writer = new Lucene40FieldInfosWriter();

	public Lucene40FieldInfosFormat()
	{
	}

	public FieldInfosReader getFieldInfosReader()
		throws IOException
	{
		return reader;
	}

	public FieldInfosWriter getFieldInfosWriter()
		throws IOException
	{
		return writer;
	}
}
