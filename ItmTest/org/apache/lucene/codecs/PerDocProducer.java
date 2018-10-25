// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerDocProducer.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.index.DocValues;

public abstract class PerDocProducer
	implements Closeable
{

	protected PerDocProducer()
	{
	}

	public abstract DocValues docValues(String s)
		throws IOException;

	public abstract void close()
		throws IOException;
}
