// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopFieldDocs.java

package org.apache.lucene.search;


// Referenced classes of package org.apache.lucene.search:
//			TopDocs, SortField, ScoreDoc

public class TopFieldDocs extends TopDocs
{

	public SortField fields[];

	public TopFieldDocs(int totalHits, ScoreDoc scoreDocs[], SortField fields[], float maxScore)
	{
		super(totalHits, scoreDocs, maxScore);
		this.fields = fields;
	}
}
