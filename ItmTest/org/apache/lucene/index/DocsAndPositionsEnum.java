// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocsAndPositionsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			DocsEnum

public abstract class DocsAndPositionsEnum extends DocsEnum
{

	public static final int FLAG_OFFSETS = 1;
	public static final int FLAG_PAYLOADS = 2;

	protected DocsAndPositionsEnum()
	{
	}

	public abstract int nextPosition()
		throws IOException;

	public abstract int startOffset()
		throws IOException;

	public abstract int endOffset()
		throws IOException;

	public abstract BytesRef getPayload()
		throws IOException;
}
