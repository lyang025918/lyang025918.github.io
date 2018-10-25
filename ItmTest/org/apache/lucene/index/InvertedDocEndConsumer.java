// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvertedDocEndConsumer.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			SegmentWriteState, DocInverterPerField, FieldInfo, InvertedDocEndConsumerPerField

abstract class InvertedDocEndConsumer
{

	InvertedDocEndConsumer()
	{
	}

	abstract void flush(Map map, SegmentWriteState segmentwritestate)
		throws IOException;

	abstract void abort();

	abstract InvertedDocEndConsumerPerField addField(DocInverterPerField docinverterperfield, FieldInfo fieldinfo);

	abstract void startDocument()
		throws IOException;

	abstract void finishDocument()
		throws IOException;
}
