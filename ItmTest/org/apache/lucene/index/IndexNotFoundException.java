// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexNotFoundException.java

package org.apache.lucene.index;

import java.io.FileNotFoundException;

public final class IndexNotFoundException extends FileNotFoundException
{

	public IndexNotFoundException(String msg)
	{
		super(msg);
	}
}
