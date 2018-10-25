// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollatedTermAttributeImpl.java

package org.apache.lucene.collation.tokenattributes;

import java.text.CollationKey;
import java.text.Collator;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.apache.lucene.util.BytesRef;

public class CollatedTermAttributeImpl extends CharTermAttributeImpl
{

	private final Collator collator;

	public CollatedTermAttributeImpl(Collator collator)
	{
		this.collator = (Collator)collator.clone();
	}

	public int fillBytesRef()
	{
		BytesRef bytes = getBytesRef();
		bytes.bytes = collator.getCollationKey(toString()).toByteArray();
		bytes.offset = 0;
		bytes.length = bytes.bytes.length;
		return bytes.hashCode();
	}
}
