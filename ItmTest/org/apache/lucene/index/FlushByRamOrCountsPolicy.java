// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FlushByRamOrCountsPolicy.java

package org.apache.lucene.index;

import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.SetOnce;

// Referenced classes of package org.apache.lucene.index:
//			FlushPolicy, DocumentsWriter, LiveIndexWriterConfig, DocumentsWriterFlushControl, 
//			DocumentsWriterPerThread, DocumentsWriterPerThreadPool

class FlushByRamOrCountsPolicy extends FlushPolicy
{

	FlushByRamOrCountsPolicy()
	{
	}

	public void onDelete(DocumentsWriterFlushControl control, DocumentsWriterPerThreadPool.ThreadState state)
	{
		if (flushOnDeleteTerms())
		{
			int maxBufferedDeleteTerms = indexWriterConfig.getMaxBufferedDeleteTerms();
			if (control.getNumGlobalTermDeletes() >= maxBufferedDeleteTerms)
				control.setApplyAllDeletes();
		}
		DocumentsWriter writer = (DocumentsWriter)this.writer.get();
		if (flushOnRAM() && (double)control.getDeleteBytesUsed() > 1048576D * indexWriterConfig.getRAMBufferSizeMB())
		{
			control.setApplyAllDeletes();
			if (writer.infoStream.isEnabled("FP"))
				writer.infoStream.message("FP", (new StringBuilder()).append("force apply deletes bytesUsed=").append(control.getDeleteBytesUsed()).append(" vs ramBuffer=").append(1048576D * indexWriterConfig.getRAMBufferSizeMB()).toString());
		}
	}

	public void onInsert(DocumentsWriterFlushControl control, DocumentsWriterPerThreadPool.ThreadState state)
	{
		if (flushOnDocCount() && state.dwpt.getNumDocsInRAM() >= indexWriterConfig.getMaxBufferedDocs())
			control.setFlushPending(state);
		else
		if (flushOnRAM())
		{
			long limit = (long)(indexWriterConfig.getRAMBufferSizeMB() * 1024D * 1024D);
			long totalRam = control.activeBytes() + control.getDeleteBytesUsed();
			if (totalRam >= limit)
			{
				DocumentsWriter writer = (DocumentsWriter)this.writer.get();
				if (writer.infoStream.isEnabled("FP"))
					writer.infoStream.message("FP", (new StringBuilder()).append("flush: activeBytes=").append(control.activeBytes()).append(" deleteBytes=").append(control.getDeleteBytesUsed()).append(" vs limit=").append(limit).toString());
				markLargestWriterPending(control, state, totalRam);
			}
		}
	}

	protected void markLargestWriterPending(DocumentsWriterFlushControl control, DocumentsWriterPerThreadPool.ThreadState perThreadState, long currentBytesPerThread)
	{
		control.setFlushPending(findLargestNonPendingWriter(control, perThreadState));
	}

	protected boolean flushOnDocCount()
	{
		return indexWriterConfig.getMaxBufferedDocs() != -1;
	}

	protected boolean flushOnDeleteTerms()
	{
		return indexWriterConfig.getMaxBufferedDeleteTerms() != -1;
	}

	protected boolean flushOnRAM()
	{
		return indexWriterConfig.getRAMBufferSizeMB() != -1D;
	}
}
