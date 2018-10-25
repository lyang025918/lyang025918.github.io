// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilteredDocIdSet.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			DocIdSet, DocIdSetIterator, FilteredDocIdSetIterator

public abstract class FilteredDocIdSet extends DocIdSet
{

	private final DocIdSet _innerSet;

	public FilteredDocIdSet(DocIdSet innerSet)
	{
		_innerSet = innerSet;
	}

	public boolean isCacheable()
	{
		return _innerSet.isCacheable();
	}

	public Bits bits()
		throws IOException
	{
		final Bits bits = _innerSet.bits();
		return bits != null ? new Bits() {

			final Bits val$bits;
			final FilteredDocIdSet this$0;

			public boolean get(int docid)
			{
				return bits.get(docid) && match(docid);
			}

			public int length()
			{
				return bits.length();
			}

			
			{
				this$0 = FilteredDocIdSet.this;
				bits = bits1;
				super();
			}
		} : null;
	}

	protected abstract boolean match(int i);

	public DocIdSetIterator iterator()
		throws IOException
	{
		DocIdSetIterator iterator = _innerSet.iterator();
		if (iterator == null)
			return null;
		else
			return new FilteredDocIdSetIterator(iterator) {

				final FilteredDocIdSet this$0;

				protected boolean match(int docid)
				{
					return FilteredDocIdSet.this.match(docid);
				}

			
			{
				this$0 = FilteredDocIdSet.this;
				super(x0);
			}
			};
	}
}
