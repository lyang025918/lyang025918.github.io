// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadAttribute.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.BytesRef;

public interface PayloadAttribute
	extends Attribute
{

	public abstract BytesRef getPayload();

	public abstract void setPayload(BytesRef bytesref);
}
