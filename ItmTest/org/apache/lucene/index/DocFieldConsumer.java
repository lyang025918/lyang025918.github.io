// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocFieldConsumer.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			SegmentWriteState, FieldInfo, DocFieldConsumerPerField

abstract class DocFieldConsumer
{

	DocFieldConsumer()
	{
	}

	abstract void flush(Map map, SegmentWriteState segmentwritestate)
		throws IOException;

	abstract void abort();

	abstract boolean freeRAM();

	abstract void startDocument()
		throws IOException;

	abstract DocFieldConsumerPerField addField(FieldInfo fieldinfo);

	abstract void finishDocument()
		throws IOException;
}
