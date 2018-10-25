// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsHashConsumerPerField.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			IndexableField, ParallelPostingsArray

abstract class TermsHashConsumerPerField
{

	TermsHashConsumerPerField()
	{
	}

	abstract boolean start(IndexableField aindexablefield[], int i)
		throws IOException;

	abstract void finish()
		throws IOException;

	abstract void skippingLongTerm()
		throws IOException;

	abstract void start(IndexableField indexablefield);

	abstract void newTerm(int i)
		throws IOException;

	abstract void addTerm(int i)
		throws IOException;

	abstract int getStreamCount();

	abstract ParallelPostingsArray createPostingsArray(int i);
}
