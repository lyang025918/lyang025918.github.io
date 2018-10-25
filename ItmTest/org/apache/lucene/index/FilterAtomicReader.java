// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilterAtomicReader.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import org.apache.lucene.util.*;
import org.apache.lucene.util.automaton.CompiledAutomaton;

// Referenced classes of package org.apache.lucene.index:
//			AtomicReader, FieldInfos, Fields, StoredFieldVisitor, 
//			DocValues, DocsAndPositionsEnum, DocsEnum, TermsEnum, 
//			TermState, Terms

public class FilterAtomicReader extends AtomicReader
{
	public static class FilterDocsAndPositionsEnum extends DocsAndPositionsEnum
	{

		protected final DocsAndPositionsEnum in;

		public int docID()
		{
			return in.docID();
		}

		public int freq()
			throws IOException
		{
			return in.freq();
		}

		public int nextDoc()
			throws IOException
		{
			return in.nextDoc();
		}

		public int advance(int target)
			throws IOException
		{
			return in.advance(target);
		}

		public int nextPosition()
			throws IOException
		{
			return in.nextPosition();
		}

		public int startOffset()
			throws IOException
		{
			return in.startOffset();
		}

		public int endOffset()
			throws IOException
		{
			return in.endOffset();
		}

		public BytesRef getPayload()
			throws IOException
		{
			return in.getPayload();
		}

		public AttributeSource attributes()
		{
			return in.attributes();
		}

		public FilterDocsAndPositionsEnum(DocsAndPositionsEnum in)
		{
			this.in = in;
		}
	}

	public static class FilterDocsEnum extends DocsEnum
	{

		protected final DocsEnum in;

		public int docID()
		{
			return in.docID();
		}

		public int freq()
			throws IOException
		{
			return in.freq();
		}

		public int nextDoc()
			throws IOException
		{
			return in.nextDoc();
		}

		public int advance(int target)
			throws IOException
		{
			return in.advance(target);
		}

		public AttributeSource attributes()
		{
			return in.attributes();
		}

		public FilterDocsEnum(DocsEnum in)
		{
			this.in = in;
		}
	}

	public static class FilterTermsEnum extends TermsEnum
	{

		protected final TermsEnum in;

		public boolean seekExact(BytesRef text, boolean useCache)
			throws IOException
		{
			return in.seekExact(text, useCache);
		}

		public TermsEnum.SeekStatus seekCeil(BytesRef text, boolean useCache)
			throws IOException
		{
			return in.seekCeil(text, useCache);
		}

		public void seekExact(long ord)
			throws IOException
		{
			in.seekExact(ord);
		}

		public BytesRef next()
			throws IOException
		{
			return in.next();
		}

		public BytesRef term()
			throws IOException
		{
			return in.term();
		}

		public long ord()
			throws IOException
		{
			return in.ord();
		}

		public int docFreq()
			throws IOException
		{
			return in.docFreq();
		}

		public long totalTermFreq()
			throws IOException
		{
			return in.totalTermFreq();
		}

		public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
			throws IOException
		{
			return in.docs(liveDocs, reuse, flags);
		}

		public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
			throws IOException
		{
			return in.docsAndPositions(liveDocs, reuse, flags);
		}

		public Comparator getComparator()
		{
			return in.getComparator();
		}

		public void seekExact(BytesRef term, TermState state)
			throws IOException
		{
			in.seekExact(term, state);
		}

		public TermState termState()
			throws IOException
		{
			return in.termState();
		}

		public AttributeSource attributes()
		{
			return in.attributes();
		}

		public FilterTermsEnum(TermsEnum in)
		{
			this.in = in;
		}
	}

	public static class FilterTerms extends Terms
	{

		protected final Terms in;

		public TermsEnum iterator(TermsEnum reuse)
			throws IOException
		{
			return in.iterator(reuse);
		}

		public Comparator getComparator()
			throws IOException
		{
			return in.getComparator();
		}

		public long size()
			throws IOException
		{
			return in.size();
		}

		public long getSumTotalTermFreq()
			throws IOException
		{
			return in.getSumTotalTermFreq();
		}

		public long getSumDocFreq()
			throws IOException
		{
			return in.getSumDocFreq();
		}

		public int getDocCount()
			throws IOException
		{
			return in.getDocCount();
		}

		public TermsEnum intersect(CompiledAutomaton automaton, BytesRef bytes)
			throws IOException
		{
			return in.intersect(automaton, bytes);
		}

		public boolean hasOffsets()
		{
			return in.hasOffsets();
		}

		public boolean hasPositions()
		{
			return in.hasPositions();
		}

		public boolean hasPayloads()
		{
			return in.hasPayloads();
		}

		public FilterTerms(Terms in)
		{
			this.in = in;
		}
	}

	public static class FilterFields extends Fields
	{

		protected final Fields in;

		public Iterator iterator()
		{
			return in.iterator();
		}

		public Terms terms(String field)
			throws IOException
		{
			return in.terms(field);
		}

		public int size()
		{
			return in.size();
		}

		public long getUniqueTermCount()
			throws IOException
		{
			return in.getUniqueTermCount();
		}

		public FilterFields(Fields in)
		{
			this.in = in;
		}
	}


	protected final AtomicReader in;

	public FilterAtomicReader(AtomicReader in)
	{
		this.in = in;
		in.registerParentReader(this);
	}

	public Bits getLiveDocs()
	{
		ensureOpen();
		return in.getLiveDocs();
	}

	public FieldInfos getFieldInfos()
	{
		return in.getFieldInfos();
	}

	public Fields getTermVectors(int docID)
		throws IOException
	{
		ensureOpen();
		return in.getTermVectors(docID);
	}

	public int numDocs()
	{
		return in.numDocs();
	}

	public int maxDoc()
	{
		return in.maxDoc();
	}

	public void document(int docID, StoredFieldVisitor visitor)
		throws IOException
	{
		ensureOpen();
		in.document(docID, visitor);
	}

	public boolean hasDeletions()
	{
		ensureOpen();
		return in.hasDeletions();
	}

	protected void doClose()
		throws IOException
	{
		in.close();
	}

	public Fields fields()
		throws IOException
	{
		ensureOpen();
		return in.fields();
	}

	public Object getCoreCacheKey()
	{
		return in.getCoreCacheKey();
	}

	public Object getCombinedCoreAndDeletesKey()
	{
		return in.getCombinedCoreAndDeletesKey();
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder("FilterAtomicReader(");
		buffer.append(in);
		buffer.append(')');
		return buffer.toString();
	}

	public DocValues docValues(String field)
		throws IOException
	{
		ensureOpen();
		return in.docValues(field);
	}

	public DocValues normValues(String field)
		throws IOException
	{
		ensureOpen();
		return in.normValues(field);
	}
}
