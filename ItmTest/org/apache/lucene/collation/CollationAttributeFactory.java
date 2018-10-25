// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollationAttributeFactory.java

package org.apache.lucene.collation;

import java.text.Collator;
import org.apache.lucene.collation.tokenattributes.CollatedTermAttributeImpl;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeSource;

public class CollationAttributeFactory extends org.apache.lucene.util.AttributeSource.AttributeFactory
{

	private final Collator collator;
	private final org.apache.lucene.util.AttributeSource.AttributeFactory delegate;

	public CollationAttributeFactory(Collator collator)
	{
		this(org.apache.lucene.util.AttributeSource.AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY, collator);
	}

	public CollationAttributeFactory(org.apache.lucene.util.AttributeSource.AttributeFactory delegate, Collator collator)
	{
		this.delegate = delegate;
		this.collator = collator;
	}

	public AttributeImpl createAttributeInstance(Class attClass)
	{
		return ((AttributeImpl) (attClass.isAssignableFrom(org/apache/lucene/collation/tokenattributes/CollatedTermAttributeImpl) ? new CollatedTermAttributeImpl(collator) : delegate.createAttributeInstance(attClass)));
	}
}
