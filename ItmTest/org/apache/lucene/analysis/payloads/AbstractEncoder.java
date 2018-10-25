// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractEncoder.java

package org.apache.lucene.analysis.payloads;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			PayloadEncoder

public abstract class AbstractEncoder
	implements PayloadEncoder
{

	public AbstractEncoder()
	{
	}

	public BytesRef encode(char buffer[])
	{
		return encode(buffer, 0, buffer.length);
	}
}
