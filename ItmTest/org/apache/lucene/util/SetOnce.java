// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SetOnce.java

package org.apache.lucene.util;

import java.util.concurrent.atomic.AtomicBoolean;

public final class SetOnce
{
	public static final class AlreadySetException extends IllegalStateException
	{

		public AlreadySetException()
		{
			super("The object cannot be set twice!");
		}
	}


	private volatile Object obj;
	private final AtomicBoolean set;

	public SetOnce()
	{
		obj = null;
		set = new AtomicBoolean(false);
	}

	public SetOnce(Object obj)
	{
		this.obj = null;
		this.obj = obj;
		set = new AtomicBoolean(true);
	}

	public final void set(Object obj)
	{
		if (set.compareAndSet(false, true))
			this.obj = obj;
		else
			throw new AlreadySetException();
	}

	public final Object get()
	{
		return obj;
	}
}
