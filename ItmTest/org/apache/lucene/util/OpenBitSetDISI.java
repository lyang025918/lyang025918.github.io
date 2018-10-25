// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OpenBitSetDISI.java

package org.apache.lucene.util;

import java.io.IOException;
import org.apache.lucene.search.DocIdSetIterator;

// Referenced classes of package org.apache.lucene.util:
//			OpenBitSet

public class OpenBitSetDISI extends OpenBitSet
{

	public OpenBitSetDISI(DocIdSetIterator disi, int maxSize)
		throws IOException
	{
		super(maxSize);
		inPlaceOr(disi);
	}

	public OpenBitSetDISI(int maxSize)
	{
		super(maxSize);
	}

	public void inPlaceOr(DocIdSetIterator disi)
		throws IOException
	{
		int doc;
		for (long size = size(); (long)(doc = disi.nextDoc()) < size;)
			fastSet(doc);

	}

	public void inPlaceAnd(DocIdSetIterator disi)
		throws IOException
	{
		int bitSetDoc;
		int disiDoc;
		for (bitSetDoc = nextSetBit(0); bitSetDoc != -1 && (disiDoc = disi.advance(bitSetDoc)) != 0x7fffffff; bitSetDoc = nextSetBit(disiDoc + 1))
			clear(bitSetDoc, disiDoc);

		if (bitSetDoc != -1)
			clear(bitSetDoc, size());
	}

	public void inPlaceNot(DocIdSetIterator disi)
		throws IOException
	{
		int doc;
		for (long size = size(); (long)(doc = disi.nextDoc()) < size;)
			fastClear(doc);

	}

	public void inPlaceXor(DocIdSetIterator disi)
		throws IOException
	{
		int doc;
		for (long size = size(); (long)(doc = disi.nextDoc()) < size;)
			fastFlip(doc);

	}
}
