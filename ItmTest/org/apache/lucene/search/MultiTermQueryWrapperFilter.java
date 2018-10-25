// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiTermQueryWrapperFilter.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.FixedBitSet;

// Referenced classes of package org.apache.lucene.search:
//			Filter, MultiTermQuery, DocIdSet

public class MultiTermQueryWrapperFilter extends Filter
{

	protected final MultiTermQuery query;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/MultiTermQueryWrapperFilter.desiredAssertionStatus();

	protected MultiTermQueryWrapperFilter(MultiTermQuery query)
	{
		this.query = query;
	}

	public String toString()
	{
		return query.toString();
	}

	public final boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (o == null)
			return false;
		if (getClass().equals(o.getClass()))
			return query.equals(((MultiTermQueryWrapperFilter)o).query);
		else
			return false;
	}

	public final int hashCode()
	{
		return query.hashCode();
	}

	public final String getField()
	{
		return query.getField();
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		AtomicReader reader = context.reader();
		Fields fields = reader.fields();
		if (fields == null)
			return DocIdSet.EMPTY_DOCIDSET;
		Terms terms = fields.terms(query.field);
		if (terms == null)
			return DocIdSet.EMPTY_DOCIDSET;
		TermsEnum termsEnum = query.getTermsEnum(terms);
		if (!$assertionsDisabled && termsEnum == null)
			throw new AssertionError();
		if (termsEnum.next() != null)
		{
			FixedBitSet bitSet = new FixedBitSet(context.reader().maxDoc());
			DocsEnum docsEnum = null;
			do
			{
				docsEnum = termsEnum.docs(acceptDocs, docsEnum, 0);
				int docid;
				while ((docid = docsEnum.nextDoc()) != 0x7fffffff) 
					bitSet.set(docid);
			} while (termsEnum.next() != null);
			return bitSet;
		} else
		{
			return DocIdSet.EMPTY_DOCIDSET;
		}
	}

}
