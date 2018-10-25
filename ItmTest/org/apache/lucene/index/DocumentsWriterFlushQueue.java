// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriterFlushQueue.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriter, DocumentsWriterDeleteQueue, DocumentsWriterPerThread, FrozenBufferedDeletes

class DocumentsWriterFlushQueue
{
	static final class SegmentFlushTicket extends FlushTicket
	{

		private DocumentsWriterPerThread.FlushedSegment segment;
		private boolean failed;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterFlushQueue.desiredAssertionStatus();

		protected void publish(DocumentsWriter writer)
			throws IOException
		{
			if (!$assertionsDisabled && published)
			{
				throw new AssertionError("ticket was already publised - can not publish twice");
			} else
			{
				published = true;
				writer.finishFlush(segment, frozenDeletes);
				return;
			}
		}

		protected void setSegment(DocumentsWriterPerThread.FlushedSegment segment)
		{
			if (!$assertionsDisabled && failed)
			{
				throw new AssertionError();
			} else
			{
				this.segment = segment;
				return;
			}
		}

		protected void setFailed()
		{
			if (!$assertionsDisabled && segment != null)
			{
				throw new AssertionError();
			} else
			{
				failed = true;
				return;
			}
		}

		protected boolean canPublish()
		{
			return segment != null || failed;
		}


		protected SegmentFlushTicket(FrozenBufferedDeletes frozenDeletes)
		{
			super(frozenDeletes);
			failed = false;
		}
	}

	static final class GlobalDeletesTicket extends FlushTicket
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterFlushQueue.desiredAssertionStatus();

		protected void publish(DocumentsWriter writer)
			throws IOException
		{
			if (!$assertionsDisabled && published)
			{
				throw new AssertionError("ticket was already publised - can not publish twice");
			} else
			{
				published = true;
				writer.finishFlush(null, frozenDeletes);
				return;
			}
		}

		protected boolean canPublish()
		{
			return true;
		}


		protected GlobalDeletesTicket(FrozenBufferedDeletes frozenDeletes)
		{
			super(frozenDeletes);
		}
	}

	static abstract class FlushTicket
	{

		protected FrozenBufferedDeletes frozenDeletes;
		protected boolean published;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterFlushQueue.desiredAssertionStatus();

		protected abstract void publish(DocumentsWriter documentswriter)
			throws IOException;

		protected abstract boolean canPublish();


		protected FlushTicket(FrozenBufferedDeletes frozenDeletes)
		{
			published = false;
			if (!$assertionsDisabled && frozenDeletes == null)
			{
				throw new AssertionError();
			} else
			{
				this.frozenDeletes = frozenDeletes;
				return;
			}
		}
	}


	private final Queue queue = new LinkedList();
	private final AtomicInteger ticketCount = new AtomicInteger();
	private final ReentrantLock purgeLock = new ReentrantLock();
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterFlushQueue.desiredAssertionStatus();

	DocumentsWriterFlushQueue()
	{
	}

	void addDeletesAndPurge(DocumentsWriter writer, DocumentsWriterDeleteQueue deleteQueue)
		throws IOException
	{
		DocumentsWriterFlushQueue documentswriterflushqueue = this;
		JVM INSTR monitorenter ;
		boolean success;
		incTickets();
		success = false;
		queue.add(new GlobalDeletesTicket(deleteQueue.freezeGlobalBuffer(null)));
		success = true;
		if (!success)
			decTickets();
		break MISSING_BLOCK_LABEL_62;
		Exception exception;
		exception;
		if (!success)
			decTickets();
		throw exception;
		Exception exception1;
		exception1;
		throw exception1;
		forcePurge(writer);
		return;
	}

	private void incTickets()
	{
		int numTickets = ticketCount.incrementAndGet();
		if (!$assertionsDisabled && numTickets <= 0)
			throw new AssertionError();
		else
			return;
	}

	private void decTickets()
	{
		int numTickets = ticketCount.decrementAndGet();
		if (!$assertionsDisabled && numTickets < 0)
			throw new AssertionError();
		else
			return;
	}

	synchronized SegmentFlushTicket addFlushTicket(DocumentsWriterPerThread dwpt)
	{
		boolean success;
		incTickets();
		success = false;
		SegmentFlushTicket segmentflushticket;
		SegmentFlushTicket ticket = new SegmentFlushTicket(dwpt.prepareFlush());
		queue.add(ticket);
		success = true;
		segmentflushticket = ticket;
		if (!success)
			decTickets();
		return segmentflushticket;
		Exception exception;
		exception;
		if (!success)
			decTickets();
		throw exception;
	}

	synchronized void addSegment(SegmentFlushTicket ticket, DocumentsWriterPerThread.FlushedSegment segment)
	{
		ticket.setSegment(segment);
	}

	synchronized void markTicketFailed(SegmentFlushTicket ticket)
	{
		ticket.setFailed();
	}

	boolean hasTickets()
	{
		if (!$assertionsDisabled && ticketCount.get() < 0)
			throw new AssertionError((new StringBuilder()).append("ticketCount should be >= 0 but was: ").append(ticketCount.get()).toString());
		else
			return ticketCount.get() != 0;
	}

	private void innerPurge(DocumentsWriter writer)
		throws IOException
	{
		if (!$assertionsDisabled && !purgeLock.isHeldByCurrentThread())
			throw new AssertionError();
_L2:
		FlushTicket head;
		boolean canPublish;
		synchronized (this)
		{
			head = (FlushTicket)queue.peek();
			canPublish = head != null && head.canPublish();
		}
		if (!canPublish)
			break; /* Loop/switch isn't completed */
		head.publish(writer);
		synchronized (this)
		{
			FlushTicket poll = (FlushTicket)queue.poll();
			ticketCount.decrementAndGet();
			if (!$assertionsDisabled && poll != head)
				throw new AssertionError();
		}
		if (true) goto _L2; else goto _L1
		Exception exception2;
		exception2;
		synchronized (this)
		{
			FlushTicket poll = (FlushTicket)queue.poll();
			ticketCount.decrementAndGet();
			if (!$assertionsDisabled && poll != head)
				throw new AssertionError();
		}
		throw exception2;
_L1:
	}

	void forcePurge(DocumentsWriter writer)
		throws IOException
	{
		if (!$assertionsDisabled && Thread.holdsLock(this))
			throw new AssertionError();
		purgeLock.lock();
		innerPurge(writer);
		purgeLock.unlock();
		break MISSING_BLOCK_LABEL_53;
		Exception exception;
		exception;
		purgeLock.unlock();
		throw exception;
	}

	void tryPurge(DocumentsWriter writer)
		throws IOException
	{
		if (!$assertionsDisabled && Thread.holdsLock(this))
			throw new AssertionError();
		if (!purgeLock.tryLock())
			break MISSING_BLOCK_LABEL_56;
		innerPurge(writer);
		purgeLock.unlock();
		break MISSING_BLOCK_LABEL_56;
		Exception exception;
		exception;
		purgeLock.unlock();
		throw exception;
	}

	public int getTicketCount()
	{
		return ticketCount.get();
	}

	synchronized void clear()
	{
		queue.clear();
		ticketCount.set(0);
	}

}
