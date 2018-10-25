// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntField.java

package org.apache.lucene.document;

import org.apache.lucene.index.FieldInfo;

// Referenced classes of package org.apache.lucene.document:
//			Field, FieldType

public final class IntField extends Field
{

	public static final FieldType TYPE_NOT_STORED;
	public static final FieldType TYPE_STORED;

	public IntField(String name, int value, Field.Store stored)
	{
		super(name, stored != Field.Store.YES ? TYPE_NOT_STORED : TYPE_STORED);
		fieldsData = Integer.valueOf(value);
	}

	public IntField(String name, int value, FieldType type)
	{
		super(name, type);
		if (type.numericType() != FieldType.NumericType.INT)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("type.numericType() must be INT but got ").append(type.numericType()).toString());
		} else
		{
			fieldsData = Integer.valueOf(value);
			return;
		}
	}

	static 
	{
		TYPE_NOT_STORED = new FieldType();
		TYPE_NOT_STORED.setIndexed(true);
		TYPE_NOT_STORED.setTokenized(true);
		TYPE_NOT_STORED.setOmitNorms(true);
		TYPE_NOT_STORED.setIndexOptions(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY);
		TYPE_NOT_STORED.setNumericType(FieldType.NumericType.INT);
		TYPE_NOT_STORED.freeze();
		TYPE_STORED = new FieldType();
		TYPE_STORED.setIndexed(true);
		TYPE_STORED.setTokenized(true);
		TYPE_STORED.setOmitNorms(true);
		TYPE_STORED.setIndexOptions(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY);
		TYPE_STORED.setNumericType(FieldType.NumericType.INT);
		TYPE_STORED.setStored(true);
		TYPE_STORED.freeze();
	}
}
