// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PostingsReaderBase.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.codecs:
//			BlockTermState

public abstract class PostingsReaderBase
	implements Closeable
{

	protected PostingsReaderBase()
	{
	}

	public abstract void init(IndexInput indexinput)
		throws IOException;

	public abstract BlockTermState newTermState()
		throws IOException;

	public abstract void nextTerm(FieldInfo fieldinfo, BlockTermState blocktermstate)
		throws IOException;

	public abstract DocsEnum docs(FieldInfo fieldinfo, BlockTermState blocktermstate, Bits bits, DocsEnum docsenum, int i)
		throws IOException;

	public abstract DocsAndPositionsEnum docsAndPositions(FieldInfo fieldinfo, BlockTermState blocktermstate, Bits bits, DocsAndPositionsEnum docsandpositionsenum, int i)
		throws IOException;

	public abstract void close()
		throws IOException;

	public abstract void readTermsBlock(IndexInput indexinput, FieldInfo fieldinfo, BlockTermState blocktermstate)
		throws IOException;
}
