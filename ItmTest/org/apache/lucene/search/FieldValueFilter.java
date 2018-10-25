// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldValueFilter.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			Filter, DocIdSet, FieldCache, BitsFilteredDocIdSet, 
//			FieldCacheDocIdSet

public class FieldValueFilter extends Filter
{

	private final String field;
	private final boolean negate;

	public FieldValueFilter(String field)
	{
		this(field, false);
	}

	public FieldValueFilter(String field, boolean negate)
	{
		this.field = field;
		this.negate = negate;
	}

	public String field()
	{
		return field;
	}

	public boolean negate()
	{
		return negate;
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		Bits docsWithField = FieldCache.DEFAULT.getDocsWithField(context.reader(), field);
		if (negate)
			if (docsWithField instanceof org.apache.lucene.util.Bits.MatchAllBits)
				return null;
			else
				return new FieldCacheDocIdSet(acceptDocs, docsWithField) {

					final Bits val$docsWithField;
					final FieldValueFilter this$0;

					protected final boolean matchDoc(int doc)
					{
						return !docsWithField.get(doc);
					}

			
			{
				this$0 = FieldValueFilter.this;
				docsWithField = bits;
				super(x0, x1);
			}
				};
		if (docsWithField instanceof org.apache.lucene.util.Bits.MatchNoBits)
			return null;
		if (docsWithField instanceof DocIdSet)
			return BitsFilteredDocIdSet.wrap((DocIdSet)docsWithField, acceptDocs);
		else
			return new FieldCacheDocIdSet(acceptDocs, docsWithField) {

				final Bits val$docsWithField;
				final FieldValueFilter this$0;

				protected final boolean matchDoc(int doc)
				{
					return docsWithField.get(doc);
				}

			
			{
				this$0 = FieldValueFilter.this;
				docsWithField = bits;
				super(x0, x1);
			}
			};
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (field != null ? field.hashCode() : 0);
		result = 31 * result + (negate ? 1231 : '\u04D5');
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldValueFilter other = (FieldValueFilter)obj;
		if (field == null)
		{
			if (other.field != null)
				return false;
		} else
		if (!field.equals(other.field))
			return false;
		return negate == other.negate;
	}

	public String toString()
	{
		return (new StringBuilder()).append("FieldValueFilter [field=").append(field).append(", negate=").append(negate).append("]").toString();
	}
}
