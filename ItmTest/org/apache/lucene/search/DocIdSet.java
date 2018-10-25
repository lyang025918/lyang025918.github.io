// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocIdSet.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			DocIdSetIterator

public abstract class DocIdSet
{

	public static final DocIdSet EMPTY_DOCIDSET = new DocIdSet() {

		private final DocIdSetIterator iterator = new DocIdSetIterator() {

			final 1 this$0;

			public int advance(int target)
			{
				return 0x7fffffff;
			}

			public int docID()
			{
				return 0x7fffffff;
			}

			public int nextDoc()
			{
				return 0x7fffffff;
			}

					
					{
						this$0 = 1.this;
						super();
					}
		};

		public DocIdSetIterator iterator()
		{
			return iterator;
		}

		public boolean isCacheable()
		{
			return true;
		}

		public Bits bits()
		{
			return null;
		}

	};

	public DocIdSet()
	{
	}

	public abstract DocIdSetIterator iterator()
		throws IOException;

	public Bits bits()
		throws IOException
	{
		return null;
	}

	public boolean isCacheable()
	{
		return false;
	}

}
