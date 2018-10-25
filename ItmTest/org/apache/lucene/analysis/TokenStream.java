// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenStream.java

package org.apache.lucene.analysis;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.lucene.util.AttributeSource;

public abstract class TokenStream extends AttributeSource
	implements Closeable
{

	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/TokenStream.desiredAssertionStatus();

	protected TokenStream()
	{
		if (!$assertionsDisabled && !assertFinal())
			throw new AssertionError();
		else
			return;
	}

	protected TokenStream(AttributeSource input)
	{
		super(input);
		if (!$assertionsDisabled && !assertFinal())
			throw new AssertionError();
		else
			return;
	}

	protected TokenStream(org.apache.lucene.util.AttributeSource.AttributeFactory factory)
	{
		super(factory);
		if (!$assertionsDisabled && !assertFinal())
			throw new AssertionError();
		else
			return;
	}

	private boolean assertFinal()
	{
		Class clazz = getClass();
		if (!clazz.desiredAssertionStatus())
			return true;
		if (!$assertionsDisabled && !clazz.isAnonymousClass() && (clazz.getModifiers() & 0x12) == 0 && !Modifier.isFinal(clazz.getMethod("incrementToken", new Class[0]).getModifiers()))
			throw new AssertionError("TokenStream implementation classes or at least their incrementToken() implementation must be final");
		return true;
		NoSuchMethodException nsme;
		nsme;
		return false;
	}

	public abstract boolean incrementToken()
		throws IOException;

	public void end()
		throws IOException
	{
	}

	public void reset()
		throws IOException
	{
	}

	public void close()
		throws IOException
	{
	}

}
