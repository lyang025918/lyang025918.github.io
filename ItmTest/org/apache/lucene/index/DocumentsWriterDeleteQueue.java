// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriterDeleteQueue.java

package org.apache.lucene.index;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.index:
//			BufferedDeletes, FrozenBufferedDeletes, Term

final class DocumentsWriterDeleteQueue
{
	private static final class TermArrayNode extends Node
	{

		void apply(BufferedDeletes bufferedDeletes, int docIDUpto)
		{
			Term arr$[] = (Term[])item;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Term term = arr$[i$];
				bufferedDeletes.addTerm(term, docIDUpto);
			}

		}

		public String toString()
		{
			return (new StringBuilder()).append("dels=").append(Arrays.toString((Object[])item)).toString();
		}

		TermArrayNode(Term term[])
		{
			super(term);
		}
	}

	private static final class QueryArrayNode extends Node
	{

		void apply(BufferedDeletes bufferedDeletes, int docIDUpto)
		{
			Query arr$[] = (Query[])item;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Query query = arr$[i$];
				bufferedDeletes.addQuery(query, docIDUpto);
			}

		}

		QueryArrayNode(Query query[])
		{
			super(query);
		}
	}

	private static final class TermNode extends Node
	{

		void apply(BufferedDeletes bufferedDeletes, int docIDUpto)
		{
			bufferedDeletes.addTerm((Term)item, docIDUpto);
		}

		public String toString()
		{
			return (new StringBuilder()).append("del=").append(item).toString();
		}

		TermNode(Term term)
		{
			super(term);
		}
	}

	private static class Node
	{

		volatile Node next;
		final Object item;
		static final AtomicReferenceFieldUpdater nextUpdater = AtomicReferenceFieldUpdater.newUpdater(org/apache/lucene/index/DocumentsWriterDeleteQueue$Node, org/apache/lucene/index/DocumentsWriterDeleteQueue$Node, "next");

		void apply(BufferedDeletes bufferedDeletes, int docIDUpto)
		{
			throw new IllegalStateException("sentinel item must never be applied");
		}

		boolean casNext(Node cmp, Node val)
		{
			return nextUpdater.compareAndSet(this, cmp, val);
		}


		Node(Object item)
		{
			this.item = item;
		}
	}

	static class DeleteSlice
	{

		Node sliceHead;
		Node sliceTail;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterDeleteQueue.desiredAssertionStatus();

		void apply(BufferedDeletes del, int docIDUpto)
		{
			if (sliceHead == sliceTail)
				return;
			Node current = sliceHead;
			do
			{
				current = current.next;
				if (!$assertionsDisabled && current == null)
					throw new AssertionError("slice property violated between the head on the tail must not be a null node");
				current.apply(del, docIDUpto);
			} while (current != sliceTail);
			reset();
		}

		void reset()
		{
			sliceHead = sliceTail;
		}

		boolean isTailItem(Object item)
		{
			return sliceTail.item == item;
		}

		boolean isEmpty()
		{
			return sliceHead == sliceTail;
		}


		DeleteSlice(Node currentTail)
		{
			if (!$assertionsDisabled && currentTail == null)
			{
				throw new AssertionError();
			} else
			{
				sliceHead = sliceTail = currentTail;
				return;
			}
		}
	}


	private volatile Node tail;
	private static final AtomicReferenceFieldUpdater tailUpdater = AtomicReferenceFieldUpdater.newUpdater(org/apache/lucene/index/DocumentsWriterDeleteQueue, org/apache/lucene/index/DocumentsWriterDeleteQueue$Node, "tail");
	private final DeleteSlice globalSlice;
	private final BufferedDeletes globalBufferedDeletes;
	private final ReentrantLock globalBufferLock;
	final long generation;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterDeleteQueue.desiredAssertionStatus();

	DocumentsWriterDeleteQueue()
	{
		this(0L);
	}

	DocumentsWriterDeleteQueue(long generation)
	{
		this(new BufferedDeletes(), generation);
	}

	DocumentsWriterDeleteQueue(BufferedDeletes globalBufferedDeletes, long generation)
	{
		globalBufferLock = new ReentrantLock();
		this.globalBufferedDeletes = globalBufferedDeletes;
		this.generation = generation;
		tail = new Node(null);
		globalSlice = new DeleteSlice(tail);
	}

	transient void addDelete(Query queries[])
	{
		add(new QueryArrayNode(queries));
		tryApplyGlobalSlice();
	}

	transient void addDelete(Term terms[])
	{
		add(new TermArrayNode(terms));
		tryApplyGlobalSlice();
	}

	void add(Term term, DeleteSlice slice)
	{
		TermNode termNode = new TermNode(term);
		add(((Node) (termNode)));
		slice.sliceTail = termNode;
		if (!$assertionsDisabled && slice.sliceHead == slice.sliceTail)
		{
			throw new AssertionError("slice head and tail must differ after add");
		} else
		{
			tryApplyGlobalSlice();
			return;
		}
	}

	void add(Node item)
	{
		Node currentTail;
		do
			do
			{
				Node tailNext;
				do
				{
					currentTail = tail;
					tailNext = currentTail.next;
				} while (tail != currentTail);
				if (tailNext == null)
					break;
				tailUpdater.compareAndSet(this, currentTail, tailNext);
			} while (true);
		while (!currentTail.casNext(null, item));
		tailUpdater.compareAndSet(this, currentTail, item);
	}

	boolean anyChanges()
	{
		globalBufferLock.lock();
		boolean flag = globalBufferedDeletes.any() || !globalSlice.isEmpty() || globalSlice.sliceTail != tail || tail.next != null;
		globalBufferLock.unlock();
		return flag;
		Exception exception;
		exception;
		globalBufferLock.unlock();
		throw exception;
	}

	void tryApplyGlobalSlice()
	{
		if (!globalBufferLock.tryLock())
			break MISSING_BLOCK_LABEL_58;
		if (updateSlice(globalSlice))
			globalSlice.apply(globalBufferedDeletes, BufferedDeletes.MAX_INT.intValue());
		globalBufferLock.unlock();
		break MISSING_BLOCK_LABEL_58;
		Exception exception;
		exception;
		globalBufferLock.unlock();
		throw exception;
	}

	FrozenBufferedDeletes freezeGlobalBuffer(DeleteSlice callerSlice)
	{
		Node currentTail;
		globalBufferLock.lock();
		currentTail = tail;
		if (callerSlice != null)
			callerSlice.sliceTail = currentTail;
		FrozenBufferedDeletes frozenbuffereddeletes;
		if (globalSlice.sliceTail != currentTail)
		{
			globalSlice.sliceTail = currentTail;
			globalSlice.apply(globalBufferedDeletes, BufferedDeletes.MAX_INT.intValue());
		}
		FrozenBufferedDeletes packet = new FrozenBufferedDeletes(globalBufferedDeletes, false);
		globalBufferedDeletes.clear();
		frozenbuffereddeletes = packet;
		globalBufferLock.unlock();
		return frozenbuffereddeletes;
		Exception exception;
		exception;
		globalBufferLock.unlock();
		throw exception;
	}

	DeleteSlice newSlice()
	{
		return new DeleteSlice(tail);
	}

	boolean updateSlice(DeleteSlice slice)
	{
		if (slice.sliceTail != tail)
		{
			slice.sliceTail = tail;
			return true;
		} else
		{
			return false;
		}
	}

	public int numGlobalTermDeletes()
	{
		return globalBufferedDeletes.numTermDeletes.get();
	}

	void clear()
	{
		globalBufferLock.lock();
		Node currentTail = tail;
		globalSlice.sliceHead = globalSlice.sliceTail = currentTail;
		globalBufferedDeletes.clear();
		globalBufferLock.unlock();
		break MISSING_BLOCK_LABEL_55;
		Exception exception;
		exception;
		globalBufferLock.unlock();
		throw exception;
	}

	private boolean forceApplyGlobalSlice()
	{
		Node currentTail;
		globalBufferLock.lock();
		currentTail = tail;
		boolean flag;
		if (globalSlice.sliceTail != currentTail)
		{
			globalSlice.sliceTail = currentTail;
			globalSlice.apply(globalBufferedDeletes, BufferedDeletes.MAX_INT.intValue());
		}
		flag = globalBufferedDeletes.any();
		globalBufferLock.unlock();
		return flag;
		Exception exception;
		exception;
		globalBufferLock.unlock();
		throw exception;
	}

	public int getBufferedDeleteTermsSize()
	{
		globalBufferLock.lock();
		int i;
		forceApplyGlobalSlice();
		i = globalBufferedDeletes.terms.size();
		globalBufferLock.unlock();
		return i;
		Exception exception;
		exception;
		globalBufferLock.unlock();
		throw exception;
	}

	public long bytesUsed()
	{
		return globalBufferedDeletes.bytesUsed.get();
	}

	public String toString()
	{
		return (new StringBuilder()).append("DWDQ: [ generation: ").append(generation).append(" ]").toString();
	}

}
