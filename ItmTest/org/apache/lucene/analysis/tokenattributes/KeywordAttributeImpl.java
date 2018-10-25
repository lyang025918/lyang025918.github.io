// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			KeywordAttribute

public final class KeywordAttributeImpl extends AttributeImpl
	implements KeywordAttribute
{

	private boolean keyword;

	public KeywordAttributeImpl()
	{
	}

	public void clear()
	{
		keyword = false;
	}

	public void copyTo(AttributeImpl target)
	{
		KeywordAttribute attr = (KeywordAttribute)target;
		attr.setKeyword(keyword);
	}

	public int hashCode()
	{
		return keyword ? 31 : 37;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
		{
			return false;
		} else
		{
			KeywordAttributeImpl other = (KeywordAttributeImpl)obj;
			return keyword == other.keyword;
		}
	}

	public boolean isKeyword()
	{
		return keyword;
	}

	public void setKeyword(boolean isKeyword)
	{
		keyword = isKeyword;
	}
}
