// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FlushPolicy.java

package org.apache.lucene.index;

import java.util.Iterator;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.SetOnce;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriter, IndexWriter, DocumentsWriterPerThread, DocumentsWriterFlushControl, 
//			DocumentsWriterPerThreadPool, LiveIndexWriterConfig

abstract class FlushPolicy
	implements Cloneable
{

	protected SetOnce writer;
	protected LiveIndexWriterConfig indexWriterConfig;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FlushPolicy.desiredAssertionStatus();

	FlushPolicy()
	{
		writer = new SetOnce();
	}

	public abstract void onDelete(DocumentsWriterFlushControl documentswriterflushcontrol, DocumentsWriterPerThreadPool.ThreadState threadstate);

	public void onUpdate(DocumentsWriterFlushControl control, DocumentsWriterPerThreadPool.ThreadState state)
	{
		onInsert(control, state);
		onDelete(control, state);
	}

	public abstract void onInsert(DocumentsWriterFlushControl documentswriterflushcontrol, DocumentsWriterPerThreadPool.ThreadState threadstate);

	protected synchronized void init(DocumentsWriter docsWriter)
	{
		writer.set(docsWriter);
		indexWriterConfig = docsWriter.indexWriter.getConfig();
	}

	protected DocumentsWriterPerThreadPool.ThreadState findLargestNonPendingWriter(DocumentsWriterFlushControl control, DocumentsWriterPerThreadPool.ThreadState perThreadState)
	{
		if (!$assertionsDisabled && perThreadState.dwpt.getNumDocsInRAM() <= 0)
			throw new AssertionError();
		long maxRamSoFar = perThreadState.bytesUsed;
		DocumentsWriterPerThreadPool.ThreadState maxRamUsingThreadState = perThreadState;
		if (!$assertionsDisabled && perThreadState.flushPending)
			throw new AssertionError("DWPT should have flushed");
		Iterator activePerThreadsIterator = control.allActiveThreadStates();
		do
		{
			if (!activePerThreadsIterator.hasNext())
				break;
			DocumentsWriterPerThreadPool.ThreadState next = (DocumentsWriterPerThreadPool.ThreadState)activePerThreadsIterator.next();
			if (!next.flushPending)
			{
				long nextRam = next.bytesUsed;
				if (nextRam > maxRamSoFar && next.dwpt.getNumDocsInRAM() > 0)
				{
					maxRamSoFar = nextRam;
					maxRamUsingThreadState = next;
				}
			}
		} while (true);
		if (!$assertionsDisabled && !assertMessage("set largest ram consuming thread pending on lower watermark"))
			throw new AssertionError();
		else
			return maxRamUsingThreadState;
	}

	private boolean assertMessage(String s)
	{
		if (((DocumentsWriter)writer.get()).infoStream.isEnabled("FP"))
			((DocumentsWriter)writer.get()).infoStream.message("FP", s);
		return true;
	}

	public FlushPolicy clone()
	{
		FlushPolicy clone;
		try
		{
			clone = (FlushPolicy)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
		clone.writer = new SetOnce();
		clone.indexWriterConfig = null;
		return clone;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
