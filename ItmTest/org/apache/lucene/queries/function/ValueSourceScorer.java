// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ValueSourceScorer.java

package org.apache.lucene.queries.function;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.queries.function:
//			FunctionValues

public class ValueSourceScorer extends Scorer
{

	protected final IndexReader reader;
	private int doc;
	protected final int maxDoc;
	protected final FunctionValues values;
	protected boolean checkDeletes;
	private final Bits liveDocs;

	protected ValueSourceScorer(IndexReader reader, FunctionValues values)
	{
		super(null);
		doc = -1;
		this.reader = reader;
		maxDoc = reader.maxDoc();
		this.values = values;
		setCheckDeletes(true);
		liveDocs = MultiFields.getLiveDocs(reader);
	}

	public IndexReader getReader()
	{
		return reader;
	}

	public void setCheckDeletes(boolean checkDeletes)
	{
		this.checkDeletes = checkDeletes && reader.hasDeletions();
	}

	public boolean matches(int doc)
	{
		return (!checkDeletes || liveDocs.get(doc)) && matchesValue(doc);
	}

	public boolean matchesValue(int doc)
	{
		return true;
	}

	public int docID()
	{
		return doc;
	}

	public int nextDoc()
		throws IOException
	{
		do
		{
			doc++;
			if (doc >= maxDoc)
				return doc = 0x7fffffff;
		} while (!matches(doc));
		return doc;
	}

	public int advance(int target)
		throws IOException
	{
		doc = target - 1;
		return nextDoc();
	}

	public float score()
		throws IOException
	{
		return values.floatVal(doc);
	}

	public float freq()
		throws IOException
	{
		return 1.0F;
	}
}
