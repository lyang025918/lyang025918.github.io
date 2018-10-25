// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldInfosWriter.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

public abstract class FieldInfosWriter
{

	protected FieldInfosWriter()
	{
	}

	public abstract void write(Directory directory, String s, FieldInfos fieldinfos, IOContext iocontext)
		throws IOException;
}
