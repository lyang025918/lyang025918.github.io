// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocIdSetIterator.java

package org.apache.lucene.search;

import java.io.IOException;

public abstract class DocIdSetIterator
{

	public static final int NO_MORE_DOCS = 0x7fffffff;

	public DocIdSetIterator()
	{
	}

	public abstract int docID();

	public abstract int nextDoc()
		throws IOException;

	public abstract int advance(int i)
		throws IOException;
}
