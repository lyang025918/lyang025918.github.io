// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MyCollector.java

package com.iflytek.itm.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;

public class MyCollector extends Collector
{

	private List docs;
	private int docBase;

	public MyCollector()
	{
		docs = new ArrayList(1024);
		docBase = -1;
	}

	TopDocs getTopDocs(int topN)
	{
		TopDocs topDocs = new TopDocs(0, null, 0.0F);
		topDocs.totalHits = docs.size();
		int len = topN <= topDocs.totalHits ? topN : topDocs.totalHits;
		topDocs.scoreDocs = new ScoreDoc[len];
		for (int i = 0; i < len; i++)
		{
			ScoreDoc scoreDoc = new ScoreDoc(((Integer)docs.get(i)).intValue(), 0.0F);
			topDocs.scoreDocs[i] = scoreDoc;
		}

		return topDocs;
	}

	TopDocs getSampleDocs(int sample)
	{
		if (sample == -1 || sample >= docs.size())
			return getTopDocs(docs.size());
		TopDocs topDocs = new TopDocs(0, null, 0.0F);
		topDocs.totalHits = docs.size();
		topDocs.scoreDocs = new ScoreDoc[sample];
		Random ran = new Random(System.currentTimeMillis());
		Set alreadyDocs = new HashSet();
		int i = 0;
		do
		{
			if (i >= sample)
				break;
			int doc = ran.nextInt(docs.size());
			if (!alreadyDocs.contains(Integer.valueOf(doc)))
			{
				alreadyDocs.add(Integer.valueOf(doc));
				ScoreDoc scoreDoc = new ScoreDoc(((Integer)docs.get(i)).intValue(), 0.0F);
				topDocs.scoreDocs[i] = scoreDoc;
				i++;
			}
		} while (true);
		return topDocs;
	}

	public void setScorer(Scorer scorer1)
		throws IOException
	{
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		org.apache.lucene.index.AtomicReader ar = context.reader();
		docBase = context.docBase;
	}

	public void collect(int doc)
		throws IOException
	{
		int docId = doc + docBase;
		docs.add(Integer.valueOf(docId));
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return true;
	}
}
