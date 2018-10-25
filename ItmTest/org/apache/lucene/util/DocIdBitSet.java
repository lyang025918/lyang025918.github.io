// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocIdBitSet.java

package org.apache.lucene.util;

import java.util.BitSet;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;

// Referenced classes of package org.apache.lucene.util:
//			Bits

public class DocIdBitSet extends DocIdSet
	implements Bits
{
	private static class DocIdBitSetIterator extends DocIdSetIterator
	{

		private int docId;
		private BitSet bitSet;

		public int docID()
		{
			return docId;
		}

		public int nextDoc()
		{
			int d = bitSet.nextSetBit(docId + 1);
			docId = d != -1 ? d : 0x7fffffff;
			return docId;
		}

		public int advance(int target)
		{
			int d = bitSet.nextSetBit(target);
			docId = d != -1 ? d : 0x7fffffff;
			return docId;
		}

		DocIdBitSetIterator(BitSet bitSet)
		{
			this.bitSet = bitSet;
			docId = -1;
		}
	}


	private final BitSet bitSet;

	public DocIdBitSet(BitSet bitSet)
	{
		this.bitSet = bitSet;
	}

	public DocIdSetIterator iterator()
	{
		return new DocIdBitSetIterator(bitSet);
	}

	public Bits bits()
	{
		return this;
	}

	public boolean isCacheable()
	{
		return true;
	}

	public BitSet getBitSet()
	{
		return bitSet;
	}

	public boolean get(int index)
	{
		return bitSet.get(index);
	}

	public int length()
	{
		return bitSet.size();
	}
}
