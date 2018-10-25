// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Spans.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.Collection;

public abstract class Spans
{

	public Spans()
	{
	}

	public abstract boolean next()
		throws IOException;

	public abstract boolean skipTo(int i)
		throws IOException;

	public abstract int doc();

	public abstract int start();

	public abstract int end();

	public abstract Collection getPayload()
		throws IOException;

	public abstract boolean isPayloadAvailable()
		throws IOException;
}
