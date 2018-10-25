// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TextField.java

package org.apache.lucene.document;

import java.io.Reader;
import org.apache.lucene.analysis.TokenStream;

// Referenced classes of package org.apache.lucene.document:
//			Field, FieldType

public final class TextField extends Field
{

	public static final FieldType TYPE_NOT_STORED;
	public static final FieldType TYPE_STORED;

	public TextField(String name, Reader reader)
	{
		super(name, reader, TYPE_NOT_STORED);
	}

	public TextField(String name, String value, Field.Store store)
	{
		super(name, value, store != Field.Store.YES ? TYPE_NOT_STORED : TYPE_STORED);
	}

	public TextField(String name, TokenStream stream)
	{
		super(name, stream, TYPE_NOT_STORED);
	}

	static 
	{
		TYPE_NOT_STORED = new FieldType();
		TYPE_STORED = new FieldType();
		TYPE_NOT_STORED.setIndexed(true);
		TYPE_NOT_STORED.setTokenized(true);
		TYPE_NOT_STORED.freeze();
		TYPE_STORED.setIndexed(true);
		TYPE_STORED.setTokenized(true);
		TYPE_STORED.setStored(true);
		TYPE_STORED.freeze();
	}
}
