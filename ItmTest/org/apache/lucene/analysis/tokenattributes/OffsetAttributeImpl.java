// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OffsetAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			OffsetAttribute

public class OffsetAttributeImpl extends AttributeImpl
	implements OffsetAttribute, Cloneable
{

	private int startOffset;
	private int endOffset;

	public OffsetAttributeImpl()
	{
	}

	public int startOffset()
	{
		return startOffset;
	}

	public void setOffset(int startOffset, int endOffset)
	{
		if (startOffset < 0 || endOffset < startOffset)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("startOffset must be non-negative, and endOffset must be >= startOffset, startOffset=").append(startOffset).append(",endOffset=").append(endOffset).toString());
		} else
		{
			this.startOffset = startOffset;
			this.endOffset = endOffset;
			return;
		}
	}

	public int endOffset()
	{
		return endOffset;
	}

	public void clear()
	{
		startOffset = 0;
		endOffset = 0;
	}

	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (other instanceof OffsetAttributeImpl)
		{
			OffsetAttributeImpl o = (OffsetAttributeImpl)other;
			return o.startOffset == startOffset && o.endOffset == endOffset;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int code = startOffset;
		code = code * 31 + endOffset;
		return code;
	}

	public void copyTo(AttributeImpl target)
	{
		OffsetAttribute t = (OffsetAttribute)target;
		t.setOffset(startOffset, endOffset);
	}
}
