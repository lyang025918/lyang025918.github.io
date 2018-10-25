// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypeAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			TypeAttribute

public class TypeAttributeImpl extends AttributeImpl
	implements TypeAttribute, Cloneable
{

	private String type;

	public TypeAttributeImpl()
	{
		this("word");
	}

	public TypeAttributeImpl(String type)
	{
		this.type = type;
	}

	public String type()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void clear()
	{
		type = "word";
	}

	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (other instanceof TypeAttributeImpl)
		{
			TypeAttributeImpl o = (TypeAttributeImpl)other;
			return type != null ? type.equals(o.type) : o.type == null;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return type != null ? type.hashCode() : 0;
	}

	public void copyTo(AttributeImpl target)
	{
		TypeAttribute t = (TypeAttribute)target;
		t.setType(type);
	}
}
