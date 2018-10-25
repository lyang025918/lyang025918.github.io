// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearcherFactory.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;

// Referenced classes of package org.apache.lucene.search:
//			IndexSearcher

public class SearcherFactory
{

	public SearcherFactory()
	{
	}

	public IndexSearcher newSearcher(IndexReader reader)
		throws IOException
	{
		return new IndexSearcher(reader);
	}
}
