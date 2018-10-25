// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StoredFieldsFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

// Referenced classes of package org.apache.lucene.codecs:
//			StoredFieldsReader, StoredFieldsWriter

public abstract class StoredFieldsFormat
{

	protected StoredFieldsFormat()
	{
	}

	public abstract StoredFieldsReader fieldsReader(Directory directory, SegmentInfo segmentinfo, FieldInfos fieldinfos, IOContext iocontext)
		throws IOException;

	public abstract StoredFieldsWriter fieldsWriter(Directory directory, SegmentInfo segmentinfo, IOContext iocontext)
		throws IOException;
}
