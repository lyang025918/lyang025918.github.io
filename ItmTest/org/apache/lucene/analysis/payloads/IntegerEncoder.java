// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntegerEncoder.java

package org.apache.lucene.analysis.payloads;

import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			AbstractEncoder, PayloadEncoder, PayloadHelper

public class IntegerEncoder extends AbstractEncoder
	implements PayloadEncoder
{

	public IntegerEncoder()
	{
	}

	public BytesRef encode(char buffer[], int offset, int length)
	{
		int payload = ArrayUtil.parseInt(buffer, offset, length);
		byte bytes[] = PayloadHelper.encodeInt(payload);
		BytesRef result = new BytesRef(bytes);
		return result;
	}
}
