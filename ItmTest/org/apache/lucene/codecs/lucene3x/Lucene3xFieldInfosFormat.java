// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xFieldInfosFormat.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.codecs.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xFieldInfosReader

/**
 * @deprecated Class Lucene3xFieldInfosFormat is deprecated
 */

class Lucene3xFieldInfosFormat extends FieldInfosFormat
{

	private final FieldInfosReader reader = new Lucene3xFieldInfosReader();

	Lucene3xFieldInfosFormat()
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
		throw new UnsupportedOperationException("this codec can only be used for reading");
	}
}
