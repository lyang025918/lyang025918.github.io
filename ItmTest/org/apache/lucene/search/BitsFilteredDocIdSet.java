// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BitsFilteredDocIdSet.java

package org.apache.lucene.search;

import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			FilteredDocIdSet, DocIdSet

public final class BitsFilteredDocIdSet extends FilteredDocIdSet
{

	private final Bits acceptDocs;

	public static DocIdSet wrap(DocIdSet set, Bits acceptDocs)
	{
		return ((DocIdSet) (set != null && acceptDocs != null ? new BitsFilteredDocIdSet(set, acceptDocs) : set));
	}

	public BitsFilteredDocIdSet(DocIdSet innerSet, Bits acceptDocs)
	{
		super(innerSet);
		if (acceptDocs == null)
		{
			throw new NullPointerException("acceptDocs is null");
		} else
		{
			this.acceptDocs = acceptDocs;
			return;
		}
	}

	protected boolean match(int docid)
	{
		return acceptDocs.get(docid);
	}
}
