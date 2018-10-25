// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FlagsAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			FlagsAttribute

public class FlagsAttributeImpl extends AttributeImpl
	implements FlagsAttribute, Cloneable
{

	private int flags;

	public FlagsAttributeImpl()
	{
		flags = 0;
	}

	public int getFlags()
	{
		return flags;
	}

	public void setFlags(int flags)
	{
		this.flags = flags;
	}

	public void clear()
	{
		flags = 0;
	}

	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (other instanceof FlagsAttributeImpl)
			return ((FlagsAttributeImpl)other).flags == flags;
		else
			return false;
	}

	public int hashCode()
	{
		return flags;
	}

	public void copyTo(AttributeImpl target)
	{
		FlagsAttribute t = (FlagsAttribute)target;
		t.setFlags(flags);
	}
}
