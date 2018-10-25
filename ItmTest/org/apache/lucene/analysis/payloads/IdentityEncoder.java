// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IdentityEncoder.java

package org.apache.lucene.analysis.payloads;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			AbstractEncoder, PayloadEncoder

public class IdentityEncoder extends AbstractEncoder
	implements PayloadEncoder
{

	protected Charset charset;

	public IdentityEncoder()
	{
		charset = Charset.forName("UTF-8");
	}

	public IdentityEncoder(Charset charset)
	{
		this.charset = Charset.forName("UTF-8");
		this.charset = charset;
	}

	public BytesRef encode(char buffer[], int offset, int length)
	{
		ByteBuffer bb = charset.encode(CharBuffer.wrap(buffer, offset, length));
		if (bb.hasArray())
		{
			return new BytesRef(bb.array(), bb.arrayOffset() + bb.position(), bb.remaining());
		} else
		{
			byte b[] = new byte[bb.remaining()];
			bb.get(b);
			return new BytesRef(b);
		}
	}
}
