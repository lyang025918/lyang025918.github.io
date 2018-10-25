// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoSuchDirectoryException.java

package org.apache.lucene.store;

import java.io.FileNotFoundException;

public class NoSuchDirectoryException extends FileNotFoundException
{

	public NoSuchDirectoryException(String message)
	{
		super(message);
	}
}
