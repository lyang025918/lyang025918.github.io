// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BufferedDeletes.java

package org.apache.lucene.index;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.index:
//			Term

class BufferedDeletes
{

	static final int BYTES_PER_DEL_TERM;
	static final int BYTES_PER_DEL_DOCID;
	static final int BYTES_PER_DEL_QUERY;
	final AtomicInteger numTermDeletes;
	final Map terms;
	final Map queries;
	final List docIDs;
	public static final Integer MAX_INT = Integer.valueOf(0x7fffffff);
	final AtomicLong bytesUsed;
	private static final boolean VERBOSE_DELETES = false;
	long gen;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/BufferedDeletes.desiredAssertionStatus();

	public BufferedDeletes()
	{
		this(new AtomicLong());
	}

	BufferedDeletes(AtomicLong bytesUsed)
	{
		numTermDeletes = new AtomicInteger();
		terms = new HashMap();
		queries = new HashMap();
		docIDs = new ArrayList();
		if (!$assertionsDisabled && bytesUsed == null)
		{
			throw new AssertionError();
		} else
		{
			this.bytesUsed = bytesUsed;
			return;
		}
	}

	public String toString()
	{
		String s = (new StringBuilder()).append("gen=").append(gen).toString();
		if (numTermDeletes.get() != 0)
			s = (new StringBuilder()).append(s).append(" ").append(numTermDeletes.get()).append(" deleted terms (unique count=").append(terms.size()).append(")").toString();
		if (queries.size() != 0)
			s = (new StringBuilder()).append(s).append(" ").append(queries.size()).append(" deleted queries").toString();
		if (docIDs.size() != 0)
			s = (new StringBuilder()).append(s).append(" ").append(docIDs.size()).append(" deleted docIDs").toString();
		if (bytesUsed.get() != 0L)
			s = (new StringBuilder()).append(s).append(" bytesUsed=").append(bytesUsed.get()).toString();
		return s;
	}

	public void addQuery(Query query, int docIDUpto)
	{
		Integer current = (Integer)queries.put(query, Integer.valueOf(docIDUpto));
		if (current == null)
			bytesUsed.addAndGet(BYTES_PER_DEL_QUERY);
	}

	public void addDocID(int docID)
	{
		docIDs.add(Integer.valueOf(docID));
		bytesUsed.addAndGet(BYTES_PER_DEL_DOCID);
	}

	public void addTerm(Term term, int docIDUpto)
	{
		Integer current = (Integer)terms.get(term);
		if (current != null && docIDUpto < current.intValue())
			return;
		terms.put(term, Integer.valueOf(docIDUpto));
		numTermDeletes.incrementAndGet();
		if (current == null)
			bytesUsed.addAndGet(BYTES_PER_DEL_TERM + term.bytes.length + 2 * term.field().length());
	}

	void clear()
	{
		terms.clear();
		queries.clear();
		docIDs.clear();
		numTermDeletes.set(0);
		bytesUsed.set(0L);
	}

	void clearDocIDs()
	{
		bytesUsed.addAndGet(-docIDs.size() * BYTES_PER_DEL_DOCID);
		docIDs.clear();
	}

	boolean any()
	{
		return terms.size() > 0 || docIDs.size() > 0 || queries.size() > 0;
	}

	static 
	{
		BYTES_PER_DEL_TERM = 9 * RamUsageEstimator.NUM_BYTES_OBJECT_REF + 7 * RamUsageEstimator.NUM_BYTES_OBJECT_HEADER + 40;
		BYTES_PER_DEL_DOCID = 2 * RamUsageEstimator.NUM_BYTES_OBJECT_REF + RamUsageEstimator.NUM_BYTES_OBJECT_HEADER + 4;
		BYTES_PER_DEL_QUERY = 5 * RamUsageEstimator.NUM_BYTES_OBJECT_REF + 2 * RamUsageEstimator.NUM_BYTES_OBJECT_HEADER + 8 + 24;
	}
}
