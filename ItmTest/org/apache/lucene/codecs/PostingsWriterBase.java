// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PostingsWriterBase.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.store.IndexOutput;

// Referenced classes of package org.apache.lucene.codecs:
//			PostingsConsumer, TermStats

public abstract class PostingsWriterBase extends PostingsConsumer
	implements Closeable
{

	protected PostingsWriterBase()
	{
	}

	public abstract void start(IndexOutput indexoutput)
		throws IOException;

	public abstract void startTerm()
		throws IOException;

	public abstract void flushTermsBlock(int i, int j)
		throws IOException;

	public abstract void finishTerm(TermStats termstats)
		throws IOException;

	public abstract void setField(FieldInfo fieldinfo);

	public abstract void close()
		throws IOException;
}
