// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCacheDocIdSet.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			DocIdSet, DocIdSetIterator, FilteredDocIdSetIterator

public abstract class FieldCacheDocIdSet extends DocIdSet
{

	protected final int maxDoc;
	protected final Bits acceptDocs;

	public FieldCacheDocIdSet(int maxDoc, Bits acceptDocs)
	{
		this.maxDoc = maxDoc;
		this.acceptDocs = acceptDocs;
	}

	protected abstract boolean matchDoc(int i);

	public final boolean isCacheable()
	{
		return true;
	}

	public final Bits bits()
	{
		return acceptDocs != null ? new Bits() {

			final FieldCacheDocIdSet this$0;

			public boolean get(int docid)
			{
				return matchDoc(docid) && acceptDocs.get(docid);
			}

			public int length()
			{
				return maxDoc;
			}

			
			{
				this$0 = FieldCacheDocIdSet.this;
				super();
			}
		} : new Bits() {

			final FieldCacheDocIdSet this$0;

			public boolean get(int docid)
			{
				return matchDoc(docid);
			}

			public int length()
			{
				return maxDoc;
			}

			
			{
				this$0 = FieldCacheDocIdSet.this;
				super();
			}
		};
	}

	public final DocIdSetIterator iterator()
		throws IOException
	{
		if (acceptDocs == null)
			return new DocIdSetIterator() {

				private int doc;
				final FieldCacheDocIdSet this$0;

				public int docID()
				{
					return doc;
				}

				public int nextDoc()
				{
					do
					{
						doc++;
						if (doc >= maxDoc)
							return doc = 0x7fffffff;
					} while (!matchDoc(doc));
					return doc;
				}

				public int advance(int target)
				{
					for (doc = target; doc < maxDoc; doc++)
						if (matchDoc(doc))
							return doc;

					return doc = 0x7fffffff;
				}

			
			{
				this$0 = FieldCacheDocIdSet.this;
				super();
				doc = -1;
			}
			};
		if ((acceptDocs instanceof FixedBitSet) || (acceptDocs instanceof OpenBitSet))
			return new FilteredDocIdSetIterator(((DocIdSet)acceptDocs).iterator()) {

				final FieldCacheDocIdSet this$0;

				protected boolean match(int doc)
				{
					return matchDoc(doc);
				}

			
			{
				this$0 = FieldCacheDocIdSet.this;
				super(x0);
			}
			};
		else
			return new DocIdSetIterator() {

				private int doc;
				final FieldCacheDocIdSet this$0;

				public int docID()
				{
					return doc;
				}

				public int nextDoc()
				{
					do
					{
						doc++;
						if (doc >= maxDoc)
							return doc = 0x7fffffff;
					} while (!matchDoc(doc) || !acceptDocs.get(doc));
					return doc;
				}

				public int advance(int target)
				{
					for (doc = target; doc < maxDoc; doc++)
						if (matchDoc(doc) && acceptDocs.get(doc))
							return doc;

					return doc = 0x7fffffff;
				}

			
			{
				this$0 = FieldCacheDocIdSet.this;
				super();
				doc = -1;
			}
			};
	}
}
