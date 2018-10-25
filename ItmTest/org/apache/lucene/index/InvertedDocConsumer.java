// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvertedDocConsumer.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			SegmentWriteState, DocInverterPerField, FieldInfo, InvertedDocConsumerPerField

abstract class InvertedDocConsumer
{

	InvertedDocConsumer()
	{
	}

	abstract void abort();

	abstract void flush(Map map, SegmentWriteState segmentwritestate)
		throws IOException;

	abstract InvertedDocConsumerPerField addField(DocInverterPerField docinverterperfield, FieldInfo fieldinfo);

	abstract void startDocument()
		throws IOException;

	abstract void finishDocument()
		throws IOException;

	abstract boolean freeRAM();
}
