// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsHashConsumer.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			SegmentWriteState, TermsHash, TermsHashPerField, FieldInfo, 
//			TermsHashConsumerPerField

abstract class TermsHashConsumer
{

	TermsHashConsumer()
	{
	}

	abstract void flush(Map map, SegmentWriteState segmentwritestate)
		throws IOException;

	abstract void abort();

	abstract void startDocument()
		throws IOException;

	abstract void finishDocument(TermsHash termshash)
		throws IOException;

	public abstract TermsHashConsumerPerField addField(TermsHashPerField termshashperfield, FieldInfo fieldinfo);
}
