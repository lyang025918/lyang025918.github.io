// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.AttributeSource;

public abstract class DocsEnum extends DocIdSetIterator
{

	public static final int FLAG_FREQS = 1;
	private AttributeSource atts;

	protected DocsEnum()
	{
		atts = null;
	}

	public abstract int freq()
		throws IOException;

	public AttributeSource attributes()
	{
		if (atts == null)
			atts = new AttributeSource();
		return atts;
	}
}
