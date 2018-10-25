// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BufferedDeletesStream.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.lucene.search.*;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.InfoStream;

// Referenced classes of package org.apache.lucene.index:
//			FrozenBufferedDeletes, SegmentInfoPerCommit, CoalescedDeletes, Term, 
//			ReadersAndLiveDocs, SegmentReader, Fields, TermsEnum, 
//			DocsEnum, Terms, AtomicReaderContext, SegmentInfo, 
//			SegmentInfos, IndexWriter

class BufferedDeletesStream
{
	public static class QueryAndLimit
	{

		public final Query query;
		public final int limit;

		public QueryAndLimit(Query query, int limit)
		{
			this.query = query;
			this.limit = limit;
		}
	}

	public static class ApplyDeletesResult
	{

		public final boolean anyDeletes;
		public final long gen;
		public final List allDeleted;

		ApplyDeletesResult(boolean anyDeletes, long gen, List allDeleted)
		{
			this.anyDeletes = anyDeletes;
			this.gen = gen;
			this.allDeleted = allDeleted;
		}
	}


	private final List deletes = new ArrayList();
	private long nextGen;
	private Term lastDeleteTerm;
	private final InfoStream infoStream;
	private final AtomicLong bytesUsed = new AtomicLong();
	private final AtomicInteger numTerms = new AtomicInteger();
	private static final Comparator sortSegInfoByDelGen = new Comparator() {

		public int compare(SegmentInfoPerCommit si1, SegmentInfoPerCommit si2)
		{
			long cmp = si1.getBufferedDeletesGen() - si2.getBufferedDeletesGen();
			if (cmp > 0L)
				return 1;
			return cmp >= 0L ? 0 : -1;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((SegmentInfoPerCommit)x0, (SegmentInfoPerCommit)x1);
		}

	};
	static final boolean $assertionsDisabled = !org/apache/lucene/index/BufferedDeletesStream.desiredAssertionStatus();

	public BufferedDeletesStream(InfoStream infoStream)
	{
		nextGen = 1L;
		this.infoStream = infoStream;
	}

	public synchronized long push(FrozenBufferedDeletes packet)
	{
		packet.setDelGen(nextGen++);
		if (!$assertionsDisabled && !packet.any())
			throw new AssertionError();
		if (!$assertionsDisabled && !checkDeleteStats())
			throw new AssertionError();
		if (!$assertionsDisabled && packet.delGen() >= nextGen)
			throw new AssertionError();
		if (!$assertionsDisabled && !deletes.isEmpty() && ((FrozenBufferedDeletes)deletes.get(deletes.size() - 1)).delGen() >= packet.delGen())
			throw new AssertionError("Delete packets must be in order");
		deletes.add(packet);
		numTerms.addAndGet(packet.numTermDeletes);
		bytesUsed.addAndGet(packet.bytesUsed);
		if (infoStream.isEnabled("BD"))
			infoStream.message("BD", (new StringBuilder()).append("push deletes ").append(packet).append(" delGen=").append(packet.delGen()).append(" packetCount=").append(deletes.size()).append(" totBytesUsed=").append(bytesUsed.get()).toString());
		if (!$assertionsDisabled && !checkDeleteStats())
			throw new AssertionError();
		else
			return packet.delGen();
	}

	public synchronized void clear()
	{
		deletes.clear();
		nextGen = 1L;
		numTerms.set(0);
		bytesUsed.set(0L);
	}

	public boolean any()
	{
		return bytesUsed.get() != 0L;
	}

	public int numTerms()
	{
		return numTerms.get();
	}

	public long bytesUsed()
	{
		return bytesUsed.get();
	}

	public synchronized ApplyDeletesResult applyDeletes(IndexWriter.ReaderPool readerPool, List infos)
		throws IOException
	{
		long t0;
		List infos2;
		CoalescedDeletes coalescedDeletes;
		boolean anyNewDeletes;
		int infosIDX;
		int delIDX;
		List allDeleted;
		t0 = System.currentTimeMillis();
		if (infos.size() == 0)
			return new ApplyDeletesResult(false, nextGen++, null);
		if (!$assertionsDisabled && !checkDeleteStats())
			throw new AssertionError();
		if (!any())
		{
			if (infoStream.isEnabled("BD"))
				infoStream.message("BD", "applyDeletes: no deletes; skipping");
			return new ApplyDeletesResult(false, nextGen++, null);
		}
		if (infoStream.isEnabled("BD"))
			infoStream.message("BD", (new StringBuilder()).append("applyDeletes: infos=").append(infos).append(" packetCount=").append(deletes.size()).toString());
		infos2 = new ArrayList();
		infos2.addAll(infos);
		Collections.sort(infos2, sortSegInfoByDelGen);
		coalescedDeletes = null;
		anyNewDeletes = false;
		infosIDX = infos2.size() - 1;
		delIDX = deletes.size() - 1;
		allDeleted = null;
_L2:
		FrozenBufferedDeletes packet;
		SegmentInfoPerCommit info;
		long segGen;
		ReadersAndLiveDocs rld;
		SegmentReader reader;
		int delCount;
		if (infosIDX < 0)
			break; /* Loop/switch isn't completed */
		packet = delIDX < 0 ? null : (FrozenBufferedDeletes)deletes.get(delIDX);
		info = (SegmentInfoPerCommit)infos2.get(infosIDX);
		segGen = info.getBufferedDeletesGen();
		if (packet != null && segGen < packet.delGen())
		{
			if (coalescedDeletes == null)
				coalescedDeletes = new CoalescedDeletes();
			if (!packet.isSegmentPrivate)
				coalescedDeletes.update(packet);
			delIDX--;
			continue; /* Loop/switch isn't completed */
		}
		if (packet == null || segGen != packet.delGen())
			break MISSING_BLOCK_LABEL_775;
		if (!$assertionsDisabled && !packet.isSegmentPrivate)
			throw new AssertionError((new StringBuilder()).append("Packet and Segments deletegen can only match on a segment private del packet gen=").append(segGen).toString());
		if (!$assertionsDisabled && !readerPool.infoIsLive(info))
			throw new AssertionError();
		rld = readerPool.get(info, true);
		reader = rld.getReader(IOContext.READ);
		delCount = 0;
		boolean segAllDeletes;
		if (coalescedDeletes != null)
		{
			delCount = (int)((long)delCount + applyTermDeletes(coalescedDeletes.termsIterable(), rld, reader));
			delCount = (int)((long)delCount + applyQueryDeletes(coalescedDeletes.queriesIterable(), rld, reader));
		}
		delCount = (int)((long)delCount + applyQueryDeletes(packet.queriesIterable(), rld, reader));
		int fullDelCount = rld.info.getDelCount() + rld.getPendingDeleteCount();
		if (!$assertionsDisabled && fullDelCount > rld.info.info.getDocCount())
			throw new AssertionError();
		segAllDeletes = fullDelCount == rld.info.info.getDocCount();
		rld.release(reader);
		readerPool.release(rld);
		break MISSING_BLOCK_LABEL_593;
		Exception exception;
		exception;
		rld.release(reader);
		readerPool.release(rld);
		throw exception;
		anyNewDeletes |= delCount > 0;
		if (segAllDeletes)
		{
			if (allDeleted == null)
				allDeleted = new ArrayList();
			allDeleted.add(info);
		}
		if (infoStream.isEnabled("BD"))
			infoStream.message("BD", (new StringBuilder()).append("seg=").append(info).append(" segGen=").append(segGen).append(" segDeletes=[").append(packet).append("]; coalesced deletes=[").append(coalescedDeletes != null ? ((Object) (coalescedDeletes)) : "null").append("] newDelCount=").append(delCount).append(segAllDeletes ? " 100% deleted" : "").toString());
		if (coalescedDeletes == null)
			coalescedDeletes = new CoalescedDeletes();
		delIDX--;
		infosIDX--;
		info.setBufferedDeletesGen(nextGen);
		continue; /* Loop/switch isn't completed */
		if (coalescedDeletes == null)
			break MISSING_BLOCK_LABEL_1107;
		if (!$assertionsDisabled && !readerPool.infoIsLive(info))
			throw new AssertionError();
		rld = readerPool.get(info, true);
		reader = rld.getReader(IOContext.READ);
		delCount = 0;
		delCount = (int)((long)delCount + applyTermDeletes(coalescedDeletes.termsIterable(), rld, reader));
		delCount = (int)((long)delCount + applyQueryDeletes(coalescedDeletes.queriesIterable(), rld, reader));
		int fullDelCount = rld.info.getDelCount() + rld.getPendingDeleteCount();
		if (!$assertionsDisabled && fullDelCount > rld.info.info.getDocCount())
			throw new AssertionError();
		segAllDeletes = fullDelCount == rld.info.info.getDocCount();
		rld.release(reader);
		readerPool.release(rld);
		break MISSING_BLOCK_LABEL_967;
		Exception exception1;
		exception1;
		rld.release(reader);
		readerPool.release(rld);
		throw exception1;
		anyNewDeletes |= delCount > 0;
		if (segAllDeletes)
		{
			if (allDeleted == null)
				allDeleted = new ArrayList();
			allDeleted.add(info);
		}
		if (infoStream.isEnabled("BD"))
			infoStream.message("BD", (new StringBuilder()).append("seg=").append(info).append(" segGen=").append(segGen).append(" coalesced deletes=[").append(coalescedDeletes != null ? ((Object) (coalescedDeletes)) : "null").append("] newDelCount=").append(delCount).append(segAllDeletes ? " 100% deleted" : "").toString());
		info.setBufferedDeletesGen(nextGen);
		infosIDX--;
		if (true) goto _L2; else goto _L1
_L1:
		if (!$assertionsDisabled && !checkDeleteStats())
			throw new AssertionError();
		if (infoStream.isEnabled("BD"))
			infoStream.message("BD", (new StringBuilder()).append("applyDeletes took ").append(System.currentTimeMillis() - t0).append(" msec").toString());
		return new ApplyDeletesResult(anyNewDeletes, nextGen++, allDeleted);
	}

	synchronized long getNextGen()
	{
		return nextGen++;
	}

	public synchronized void prune(SegmentInfos segmentInfos)
	{
		if (!$assertionsDisabled && !checkDeleteStats())
			throw new AssertionError();
		long minGen = 0x7fffffffffffffffL;
		for (Iterator i$ = segmentInfos.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			minGen = Math.min(info.getBufferedDeletesGen(), minGen);
		}

		if (infoStream.isEnabled("BD"))
			infoStream.message("BD", (new StringBuilder()).append("prune sis=").append(segmentInfos).append(" minGen=").append(minGen).append(" packetCount=").append(deletes.size()).toString());
		int limit = deletes.size();
		for (int delIDX = 0; delIDX < limit; delIDX++)
			if (((FrozenBufferedDeletes)deletes.get(delIDX)).delGen() >= minGen)
			{
				prune(delIDX);
				if (!$assertionsDisabled && !checkDeleteStats())
					throw new AssertionError();
				else
					return;
			}

		prune(limit);
		if (!$assertionsDisabled && any())
			throw new AssertionError();
		if (!$assertionsDisabled && !checkDeleteStats())
			throw new AssertionError();
		else
			return;
	}

	private synchronized void prune(int count)
	{
		if (count > 0)
		{
			if (infoStream.isEnabled("BD"))
				infoStream.message("BD", (new StringBuilder()).append("pruneDeletes: prune ").append(count).append(" packets; ").append(deletes.size() - count).append(" packets remain").toString());
			for (int delIDX = 0; delIDX < count; delIDX++)
			{
				FrozenBufferedDeletes packet = (FrozenBufferedDeletes)deletes.get(delIDX);
				numTerms.addAndGet(-packet.numTermDeletes);
				if (!$assertionsDisabled && numTerms.get() < 0)
					throw new AssertionError();
				bytesUsed.addAndGet(-packet.bytesUsed);
				if (!$assertionsDisabled && bytesUsed.get() < 0L)
					throw new AssertionError();
			}

			deletes.subList(0, count).clear();
		}
	}

	private synchronized long applyTermDeletes(Iterable termsIter, ReadersAndLiveDocs rld, SegmentReader reader)
		throws IOException
	{
		long delCount;
label0:
		{
			delCount = 0L;
			Fields fields = reader.fields();
			if (fields == null)
				return 0L;
			TermsEnum termsEnum = null;
			String currentField = null;
			DocsEnum docs = null;
			if (!$assertionsDisabled && !checkDeleteTerm(null))
				throw new AssertionError();
			boolean any = false;
			Iterator i$ = termsIter.iterator();
			do
			{
				DocsEnum docsEnum;
				do
				{
					Term term;
					do
					{
						do
						{
							if (!i$.hasNext())
								break label0;
							term = (Term)i$.next();
							if (!term.field().equals(currentField))
							{
								if (!$assertionsDisabled && currentField != null && currentField.compareTo(term.field()) >= 0)
									throw new AssertionError();
								currentField = term.field();
								Terms terms = fields.terms(currentField);
								if (terms != null)
									termsEnum = terms.iterator(null);
								else
									termsEnum = null;
							}
						} while (termsEnum == null);
						if (!$assertionsDisabled && !checkDeleteTerm(term))
							throw new AssertionError();
					} while (!termsEnum.seekExact(term.bytes(), false));
					docsEnum = termsEnum.docs(rld.getLiveDocs(), docs, 0);
				} while (docsEnum == null);
				do
				{
					int docID = docsEnum.nextDoc();
					if (docID == 0x7fffffff)
						break;
					if (!any)
					{
						rld.initWritableLiveDocs();
						any = true;
					}
					if (rld.delete(docID))
						delCount++;
				} while (true);
			} while (true);
		}
		return delCount;
	}

	private static long applyQueryDeletes(Iterable queriesIter, ReadersAndLiveDocs rld, SegmentReader reader)
		throws IOException
	{
		long delCount;
label0:
		{
			delCount = 0L;
			AtomicReaderContext readerContext = reader.getContext();
			boolean any = false;
			Iterator i$ = queriesIter.iterator();
			do
			{
				int limit;
				DocIdSetIterator it;
				do
				{
					DocIdSet docs;
					do
					{
						if (!i$.hasNext())
							break label0;
						QueryAndLimit ent = (QueryAndLimit)i$.next();
						Query query = ent.query;
						limit = ent.limit;
						docs = (new QueryWrapperFilter(query)).getDocIdSet(readerContext, reader.getLiveDocs());
					} while (docs == null);
					it = docs.iterator();
				} while (it == null);
				do
				{
					int doc = it.nextDoc();
					if (doc >= limit)
						break;
					if (!any)
					{
						rld.initWritableLiveDocs();
						any = true;
					}
					if (rld.delete(doc))
						delCount++;
				} while (true);
			} while (true);
		}
		return delCount;
	}

	private boolean checkDeleteTerm(Term term)
	{
		if (term != null && !$assertionsDisabled && lastDeleteTerm != null && term.compareTo(lastDeleteTerm) <= 0)
		{
			throw new AssertionError((new StringBuilder()).append("lastTerm=").append(lastDeleteTerm).append(" vs term=").append(term).toString());
		} else
		{
			lastDeleteTerm = term != null ? new Term(term.field(), BytesRef.deepCopyOf(term.bytes)) : null;
			return true;
		}
	}

	private boolean checkDeleteStats()
	{
		int numTerms2 = 0;
		long bytesUsed2 = 0L;
		for (Iterator i$ = deletes.iterator(); i$.hasNext();)
		{
			FrozenBufferedDeletes packet = (FrozenBufferedDeletes)i$.next();
			numTerms2 += packet.numTermDeletes;
			bytesUsed2 += packet.bytesUsed;
		}

		if (!$assertionsDisabled && numTerms2 != numTerms.get())
			throw new AssertionError((new StringBuilder()).append("numTerms2=").append(numTerms2).append(" vs ").append(numTerms.get()).toString());
		if (!$assertionsDisabled && bytesUsed2 != bytesUsed.get())
			throw new AssertionError((new StringBuilder()).append("bytesUsed2=").append(bytesUsed2).append(" vs ").append(bytesUsed).toString());
		else
			return true;
	}

}
