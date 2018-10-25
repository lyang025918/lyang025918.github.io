// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCacheTermsFilter.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			Filter, FieldCache, DocIdSet, FieldCacheDocIdSet

public class FieldCacheTermsFilter extends Filter
{

	private String field;
	private BytesRef terms[];

	public transient FieldCacheTermsFilter(String field, BytesRef terms[])
	{
		this.field = field;
		this.terms = terms;
	}

	public transient FieldCacheTermsFilter(String field, String terms[])
	{
		this.field = field;
		this.terms = new BytesRef[terms.length];
		for (int i = 0; i < terms.length; i++)
			this.terms[i] = new BytesRef(terms[i]);

	}

	public FieldCache getFieldCache()
	{
		return FieldCache.DEFAULT;
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		FieldCache.DocTermsIndex fcsi = getFieldCache().getTermsIndex(context.reader(), field);
		FixedBitSet bits = new FixedBitSet(fcsi.numOrd());
		BytesRef spare = new BytesRef();
		for (int i = 0; i < terms.length; i++)
		{
			int termNumber = fcsi.binarySearchLookup(terms[i], spare);
			if (termNumber > 0)
				bits.set(termNumber);
		}

		return new FieldCacheDocIdSet(bits, fcsi) {

			final FixedBitSet val$bits;
			final FieldCache.DocTermsIndex val$fcsi;
			final FieldCacheTermsFilter this$0;

			protected final boolean matchDoc(int doc)
			{
				return bits.get(fcsi.getOrd(doc));
			}

			
			{
				this$0 = FieldCacheTermsFilter.this;
				bits = fixedbitset;
				fcsi = doctermsindex;
				super(x0, x1);
			}
		};
	}
}
