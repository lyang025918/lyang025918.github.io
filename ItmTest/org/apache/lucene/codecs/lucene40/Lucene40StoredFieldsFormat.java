// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40StoredFieldsFormat.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40StoredFieldsReader, Lucene40StoredFieldsWriter

public class Lucene40StoredFieldsFormat extends StoredFieldsFormat
{

	public Lucene40StoredFieldsFormat()
	{
	}

	public StoredFieldsReader fieldsReader(Directory directory, SegmentInfo si, FieldInfos fn, IOContext context)
		throws IOException
	{
		return new Lucene40StoredFieldsReader(directory, si, fn, context);
	}

	public StoredFieldsWriter fieldsWriter(Directory directory, SegmentInfo si, IOContext context)
		throws IOException
	{
		return new Lucene40StoredFieldsWriter(directory, si.name, context);
	}
}
