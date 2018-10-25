// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocConsumer.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			FieldInfos, SegmentWriteState

abstract class DocConsumer
{

	DocConsumer()
	{
	}

	abstract void processDocument(FieldInfos.Builder builder)
		throws IOException;

	abstract void finishDocument()
		throws IOException;

	abstract void flush(SegmentWriteState segmentwritestate)
		throws IOException;

	abstract void abort();

	abstract boolean freeRAM();

	abstract void doAfterFlush();
}
