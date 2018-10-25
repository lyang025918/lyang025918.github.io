// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldsProducer.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.index.Fields;

public abstract class FieldsProducer extends Fields
	implements Closeable
{

	protected FieldsProducer()
	{
	}

	public abstract void close()
		throws IOException;
}
