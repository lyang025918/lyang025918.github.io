// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldInfosFormat.java

package org.apache.lucene.codecs;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.codecs:
//			FieldInfosReader, FieldInfosWriter

public abstract class FieldInfosFormat
{

	protected FieldInfosFormat()
	{
	}

	public abstract FieldInfosReader getFieldInfosReader()
		throws IOException;

	public abstract FieldInfosWriter getFieldInfosWriter()
		throws IOException;
}
