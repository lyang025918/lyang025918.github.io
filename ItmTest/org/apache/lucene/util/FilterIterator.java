// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilterIterator.java

package org.apache.lucene.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class FilterIterator
	implements Iterator
{

	private final Iterator iterator;
	private Object next;
	private boolean nextIsSet;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/FilterIterator.desiredAssertionStatus();

	protected abstract boolean predicateFunction(Object obj);

	public FilterIterator(Iterator baseIterator)
	{
		next = null;
		nextIsSet = false;
		iterator = baseIterator;
	}

	public final boolean hasNext()
	{
		return nextIsSet || setNext();
	}

	public final Object next()
	{
		if (!hasNext())
			throw new NoSuchElementException();
		if (!$assertionsDisabled && !nextIsSet)
			throw new AssertionError();
		Object obj = next;
		nextIsSet = false;
		next = null;
		return obj;
		Exception exception;
		exception;
		nextIsSet = false;
		next = null;
		throw exception;
	}

	public final void remove()
	{
		throw new UnsupportedOperationException();
	}

	private boolean setNext()
	{
		while (iterator.hasNext()) 
		{
			Object object = iterator.next();
			if (predicateFunction(object))
			{
				next = object;
				nextIsSet = true;
				return true;
			}
		}
		return false;
	}

}
