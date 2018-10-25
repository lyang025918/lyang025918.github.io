// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			PayloadAttribute

public class PayloadAttributeImpl extends AttributeImpl
	implements PayloadAttribute, Cloneable
{

	private BytesRef payload;

	public PayloadAttributeImpl()
	{
	}

	public PayloadAttributeImpl(BytesRef payload)
	{
		this.payload = payload;
	}

	public BytesRef getPayload()
	{
		return payload;
	}

	public void setPayload(BytesRef payload)
	{
		this.payload = payload;
	}

	public void clear()
	{
		payload = null;
	}

	public PayloadAttributeImpl clone()
	{
		PayloadAttributeImpl clone = (PayloadAttributeImpl)super.clone();
		if (payload != null)
			clone.payload = payload.clone();
		return clone;
	}

	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (other instanceof PayloadAttribute)
		{
			PayloadAttributeImpl o = (PayloadAttributeImpl)other;
			if (o.payload == null || payload == null)
				return o.payload == null && payload == null;
			else
				return o.payload.equals(payload);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return payload != null ? payload.hashCode() : 0;
	}

	public void copyTo(AttributeImpl target)
	{
		PayloadAttribute t = (PayloadAttribute)target;
		t.setPayload(payload != null ? payload.clone() : null);
	}

	public volatile AttributeImpl clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
