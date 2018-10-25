// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositionIncrementAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			PositionIncrementAttribute

public class PositionIncrementAttributeImpl extends AttributeImpl
	implements PositionIncrementAttribute, Cloneable
{

	private int positionIncrement;

	public PositionIncrementAttributeImpl()
	{
		positionIncrement = 1;
	}

	public void setPositionIncrement(int positionIncrement)
	{
		if (positionIncrement >= 0);
		this.positionIncrement = positionIncrement;
	}

	public int getPositionIncrement()
	{
		return positionIncrement;
	}

	public void clear()
	{
		positionIncrement = 1;
	}

	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (other instanceof PositionIncrementAttributeImpl)
		{
			PositionIncrementAttributeImpl _other = (PositionIncrementAttributeImpl)other;
			return positionIncrement == _other.positionIncrement;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return positionIncrement;
	}

	public void copyTo(AttributeImpl target)
	{
		PositionIncrementAttribute t = (PositionIncrementAttribute)target;
		t.setPositionIncrement(positionIncrement);
	}
}
