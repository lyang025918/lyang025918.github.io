// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AtomicReader.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.index:
//			IndexReader, AtomicReaderContext, FieldInfo, Fields, 
//			Terms, TermsEnum, FieldInfos, Term, 
//			DocsEnum, DocsAndPositionsEnum, DocValues, IndexReaderContext

public abstract class AtomicReader extends IndexReader
{

	private final AtomicReaderContext readerContext = new AtomicReaderContext(this);
	static final boolean $assertionsDisabled = !org/apache/lucene/index/AtomicReader.desiredAssertionStatus();

	protected AtomicReader()
	{
	}

	public final AtomicReaderContext getContext()
	{
		ensureOpen();
		return readerContext;
	}

	/**
	 * @deprecated Method hasNorms is deprecated
	 */

	public final boolean hasNorms(String field)
		throws IOException
	{
		ensureOpen();
		FieldInfo fi = getFieldInfos().fieldInfo(field);
		return fi != null && fi.hasNorms();
	}

	public abstract Fields fields()
		throws IOException;

	public final int docFreq(Term term)
		throws IOException
	{
		Fields fields = fields();
		if (fields == null)
			return 0;
		Terms terms = fields.terms(term.field());
		if (terms == null)
			return 0;
		TermsEnum termsEnum = terms.iterator(null);
		if (termsEnum.seekExact(term.bytes(), true))
			return termsEnum.docFreq();
		else
			return 0;
	}

	public final long totalTermFreq(Term term)
		throws IOException
	{
		Fields fields = fields();
		if (fields == null)
			return 0L;
		Terms terms = fields.terms(term.field());
		if (terms == null)
			return 0L;
		TermsEnum termsEnum = terms.iterator(null);
		if (termsEnum.seekExact(term.bytes(), true))
			return termsEnum.totalTermFreq();
		else
			return 0L;
	}

	public final Terms terms(String field)
		throws IOException
	{
		Fields fields = fields();
		if (fields == null)
			return null;
		else
			return fields.terms(field);
	}

	public final DocsEnum termDocsEnum(Term term)
		throws IOException
	{
		if (!$assertionsDisabled && term.field() == null)
			throw new AssertionError();
		if (!$assertionsDisabled && term.bytes() == null)
			throw new AssertionError();
		Fields fields = fields();
		if (fields != null)
		{
			Terms terms = fields.terms(term.field());
			if (terms != null)
			{
				TermsEnum termsEnum = terms.iterator(null);
				if (termsEnum.seekExact(term.bytes(), true))
					return termsEnum.docs(getLiveDocs(), null);
			}
		}
		return null;
	}

	public final DocsAndPositionsEnum termPositionsEnum(Term term)
		throws IOException
	{
		if (!$assertionsDisabled && term.field() == null)
			throw new AssertionError();
		if (!$assertionsDisabled && term.bytes() == null)
			throw new AssertionError();
		Fields fields = fields();
		if (fields != null)
		{
			Terms terms = fields.terms(term.field());
			if (terms != null)
			{
				TermsEnum termsEnum = terms.iterator(null);
				if (termsEnum.seekExact(term.bytes(), true))
					return termsEnum.docsAndPositions(getLiveDocs(), null);
			}
		}
		return null;
	}

	public abstract DocValues docValues(String s)
		throws IOException;

	public abstract DocValues normValues(String s)
		throws IOException;

	public abstract FieldInfos getFieldInfos();

	public abstract Bits getLiveDocs();

	public volatile IndexReaderContext getContext()
	{
		return getContext();
	}

}
