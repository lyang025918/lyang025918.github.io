// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xStoredFieldsFormat.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xStoredFieldsReader

/**
 * @deprecated Class Lucene3xStoredFieldsFormat is deprecated
 */

class Lucene3xStoredFieldsFormat extends StoredFieldsFormat
{

	Lucene3xStoredFieldsFormat()
	{
	}

	public StoredFieldsReader fieldsReader(Directory directory, SegmentInfo si, FieldInfos fn, IOContext context)
		throws IOException
	{
		return new Lucene3xStoredFieldsReader(directory, si, fn, context);
	}

	public StoredFieldsWriter fieldsWriter(Directory directory, SegmentInfo si, IOContext context)
		throws IOException
	{
		throw new UnsupportedOperationException("this codec can only be used for reading");
	}
}
