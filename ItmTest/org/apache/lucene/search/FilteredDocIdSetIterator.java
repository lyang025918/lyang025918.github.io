// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilteredDocIdSetIterator.java

package org.apache.lucene.search;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.search:
//			DocIdSetIterator

public abstract class FilteredDocIdSetIterator extends DocIdSetIterator
{

	protected DocIdSetIterator _innerIter;
	private int doc;

	public FilteredDocIdSetIterator(DocIdSetIterator innerIter)
	{
		if (innerIter == null)
		{
			throw new IllegalArgumentException("null iterator");
		} else
		{
			_innerIter = innerIter;
			doc = -1;
			return;
		}
	}

	protected abstract boolean match(int i);

	public int docID()
	{
		return doc;
	}

	public int nextDoc()
		throws IOException
	{
		while ((doc = _innerIter.nextDoc()) != 0x7fffffff) 
			if (match(doc))
				return doc;
		return doc;
	}

	public int advance(int target)
		throws IOException
	{
		doc = _innerIter.advance(target);
		if (doc != 0x7fffffff)
		{
			if (match(doc))
				return doc;
			while ((doc = _innerIter.nextDoc()) != 0x7fffffff) 
				if (match(doc))
					return doc;
			return doc;
		} else
		{
			return doc;
		}
	}
}
