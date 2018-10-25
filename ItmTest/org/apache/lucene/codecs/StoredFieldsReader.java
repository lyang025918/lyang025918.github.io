// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StoredFieldsReader.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.index.StoredFieldVisitor;

public abstract class StoredFieldsReader
	implements Cloneable, Closeable
{

	protected StoredFieldsReader()
	{
	}

	public abstract void visitDocument(int i, StoredFieldVisitor storedfieldvisitor)
		throws IOException;

	public abstract StoredFieldsReader clone();

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
