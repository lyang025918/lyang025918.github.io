// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositionLengthAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			PositionLengthAttribute

public class PositionLengthAttributeImpl extends AttributeImpl
	implements PositionLengthAttribute, Cloneable
{

	private int positionLength;

	public PositionLengthAttributeImpl()
	{
		positionLength = 1;
	}

	public void setPositionLength(int positionLength)
	{
		if (positionLength < 1)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("Position length must be 1 or greater: got ").append(positionLength).toString());
		} else
		{
			this.positionLength = positionLength;
			return;
		}
	}

	public int getPositionLength()
	{
		return positionLength;
	}

	public void clear()
	{
		positionLength = 1;
	}

	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (other instanceof PositionLengthAttributeImpl)
		{
			PositionLengthAttributeImpl _other = (PositionLengthAttributeImpl)other;
			return positionLength == _other.positionLength;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return positionLength;
	}

	public void copyTo(AttributeImpl target)
	{
		PositionLengthAttribute t = (PositionLengthAttribute)target;
		t.setPositionLength(positionLength);
	}
}
