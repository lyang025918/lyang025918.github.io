// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsFilter.java

package org.apache.lucene.queries;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.*;

public class TermsFilter extends Filter
{

	private final Set terms = new TreeSet();
	static final boolean $assertionsDisabled = !org/apache/lucene/queries/TermsFilter.desiredAssertionStatus();

	public TermsFilter()
	{
	}

	public void addTerm(Term term)
	{
		terms.add(term);
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		AtomicReader reader = context.reader();
		FixedBitSet result = new FixedBitSet(reader.maxDoc());
		Fields fields = reader.fields();
		if (fields == null)
			return result;
		BytesRef br = new BytesRef();
		String lastField = null;
		TermsEnum termsEnum = null;
		DocsEnum docs = null;
		Iterator i$ = terms.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Term term = (Term)i$.next();
			if (!term.field().equals(lastField))
			{
				Terms termsC = fields.terms(term.field());
				if (termsC == null)
					return result;
				termsEnum = termsC.iterator(null);
				lastField = term.field();
			}
			if (terms != null)
			{
				br.copyBytes(term.bytes());
				if (!$assertionsDisabled && termsEnum == null)
					throw new AssertionError();
				if (termsEnum.seekCeil(br) == org.apache.lucene.index.TermsEnum.SeekStatus.FOUND)
				{
					docs = termsEnum.docs(acceptDocs, docs, 0);
					while (docs.nextDoc() != 0x7fffffff) 
						result.set(docs.docID());
				}
			}
		} while (true);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
		{
			return false;
		} else
		{
			TermsFilter test = (TermsFilter)obj;
			return terms == test.terms || terms != null && terms.equals(test.terms);
		}
	}

	public int hashCode()
	{
		int hash = 9;
		for (Iterator i$ = terms.iterator(); i$.hasNext();)
		{
			Term term = (Term)i$.next();
			hash = 31 * hash + term.hashCode();
		}

		return hash;
	}

}
