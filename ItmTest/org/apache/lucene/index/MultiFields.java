// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiFields.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			Fields, AtomicReaderContext, ReaderSlice, MultiBits, 
//			MergedIterator, Terms, MultiTerms, FieldInfo, 
//			IndexReader, AtomicReader, TermsEnum, FieldInfos, 
//			DocsEnum, DocsAndPositionsEnum

public final class MultiFields extends Fields
{

	private final Fields subs[];
	private final ReaderSlice subSlices[];
	private final Map terms = new ConcurrentHashMap();
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiFields.desiredAssertionStatus();

	public static Fields getFields(IndexReader reader)
		throws IOException
	{
		List leaves = reader.leaves();
		switch (leaves.size())
		{
		case 0: // '\0'
			return null;

		case 1: // '\001'
			return ((AtomicReaderContext)leaves.get(0)).reader().fields();
		}
		List fields = new ArrayList();
		List slices = new ArrayList();
		Iterator i$ = leaves.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			AtomicReaderContext ctx = (AtomicReaderContext)i$.next();
			AtomicReader r = ctx.reader();
			Fields f = r.fields();
			if (f != null)
			{
				fields.add(f);
				slices.add(new ReaderSlice(ctx.docBase, r.maxDoc(), fields.size() - 1));
			}
		} while (true);
		if (fields.isEmpty())
			return null;
		if (fields.size() == 1)
			return (Fields)fields.get(0);
		else
			return new MultiFields((Fields[])fields.toArray(Fields.EMPTY_ARRAY), (ReaderSlice[])slices.toArray(ReaderSlice.EMPTY_ARRAY));
	}

	public static Bits getLiveDocs(IndexReader reader)
	{
		if (reader.hasDeletions())
		{
			List leaves = reader.leaves();
			int size = leaves.size();
			if (!$assertionsDisabled && size <= 0)
				throw new AssertionError("A reader with deletions must have at least one leave");
			if (size == 1)
				return ((AtomicReaderContext)leaves.get(0)).reader().getLiveDocs();
			Bits liveDocs[] = new Bits[size];
			int starts[] = new int[size + 1];
			for (int i = 0; i < size; i++)
			{
				AtomicReaderContext ctx = (AtomicReaderContext)leaves.get(i);
				liveDocs[i] = ctx.reader().getLiveDocs();
				starts[i] = ctx.docBase;
			}

			starts[size] = reader.maxDoc();
			return new MultiBits(liveDocs, starts, true);
		} else
		{
			return null;
		}
	}

	public static Terms getTerms(IndexReader r, String field)
		throws IOException
	{
		Fields fields = getFields(r);
		if (fields == null)
			return null;
		else
			return fields.terms(field);
	}

	public static DocsEnum getTermDocsEnum(IndexReader r, Bits liveDocs, String field, BytesRef term)
		throws IOException
	{
		return getTermDocsEnum(r, liveDocs, field, term, 1);
	}

	public static DocsEnum getTermDocsEnum(IndexReader r, Bits liveDocs, String field, BytesRef term, int flags)
		throws IOException
	{
		if (!$assertionsDisabled && field == null)
			throw new AssertionError();
		if (!$assertionsDisabled && term == null)
			throw new AssertionError();
		Terms terms = getTerms(r, field);
		if (terms != null)
		{
			TermsEnum termsEnum = terms.iterator(null);
			if (termsEnum.seekExact(term, true))
				return termsEnum.docs(liveDocs, null, flags);
		}
		return null;
	}

	public static DocsAndPositionsEnum getTermPositionsEnum(IndexReader r, Bits liveDocs, String field, BytesRef term)
		throws IOException
	{
		return getTermPositionsEnum(r, liveDocs, field, term, 3);
	}

	public static DocsAndPositionsEnum getTermPositionsEnum(IndexReader r, Bits liveDocs, String field, BytesRef term, int flags)
		throws IOException
	{
		if (!$assertionsDisabled && field == null)
			throw new AssertionError();
		if (!$assertionsDisabled && term == null)
			throw new AssertionError();
		Terms terms = getTerms(r, field);
		if (terms != null)
		{
			TermsEnum termsEnum = terms.iterator(null);
			if (termsEnum.seekExact(term, true))
				return termsEnum.docsAndPositions(liveDocs, null, flags);
		}
		return null;
	}

	public MultiFields(Fields subs[], ReaderSlice subSlices[])
	{
		this.subs = subs;
		this.subSlices = subSlices;
	}

	public Iterator iterator()
	{
		Iterator subIterators[] = new Iterator[subs.length];
		for (int i = 0; i < subs.length; i++)
			subIterators[i] = subs[i].iterator();

		return new MergedIterator(subIterators);
	}

	public Terms terms(String field)
		throws IOException
	{
		Terms result = (Terms)this.terms.get(field);
		if (result != null)
			return result;
		List subs2 = new ArrayList();
		List slices2 = new ArrayList();
		for (int i = 0; i < subs.length; i++)
		{
			Terms terms = subs[i].terms(field);
			if (terms != null)
			{
				subs2.add(terms);
				slices2.add(subSlices[i]);
			}
		}

		if (subs2.size() == 0)
		{
			result = null;
		} else
		{
			result = new MultiTerms((Terms[])subs2.toArray(Terms.EMPTY_ARRAY), (ReaderSlice[])slices2.toArray(ReaderSlice.EMPTY_ARRAY));
			this.terms.put(field, result);
		}
		return result;
	}

	public int size()
	{
		return -1;
	}

	public static long totalTermFreq(IndexReader r, String field, BytesRef text)
		throws IOException
	{
		Terms terms = getTerms(r, field);
		if (terms != null)
		{
			TermsEnum termsEnum = terms.iterator(null);
			if (termsEnum.seekExact(text, true))
				return termsEnum.totalTermFreq();
		}
		return 0L;
	}

	public static FieldInfos getMergedFieldInfos(IndexReader reader)
	{
		FieldInfos.Builder builder = new FieldInfos.Builder();
		AtomicReaderContext ctx;
		for (Iterator i$ = reader.leaves().iterator(); i$.hasNext(); builder.add(ctx.reader().getFieldInfos()))
			ctx = (AtomicReaderContext)i$.next();

		return builder.finish();
	}

	public static Collection getIndexedFields(IndexReader reader)
	{
		Collection fields = new HashSet();
		Iterator i$ = getMergedFieldInfos(reader).iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FieldInfo fieldInfo = (FieldInfo)i$.next();
			if (fieldInfo.isIndexed())
				fields.add(fieldInfo.name);
		} while (true);
		return fields;
	}

}
