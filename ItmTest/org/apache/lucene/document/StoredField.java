// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StoredField.java

package org.apache.lucene.document;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.document:
//			Field, FieldType

public final class StoredField extends Field
{

	public static final FieldType TYPE;

	public StoredField(String name, byte value[])
	{
		super(name, value, TYPE);
	}

	public StoredField(String name, byte value[], int offset, int length)
	{
		super(name, value, offset, length, TYPE);
	}

	public StoredField(String name, BytesRef value)
	{
		super(name, value, TYPE);
	}

	public StoredField(String name, String value)
	{
		super(name, value, TYPE);
	}

	public StoredField(String name, int value)
	{
		super(name, TYPE);
		fieldsData = Integer.valueOf(value);
	}

	public StoredField(String name, float value)
	{
		super(name, TYPE);
		fieldsData = Float.valueOf(value);
	}

	public StoredField(String name, long value)
	{
		super(name, TYPE);
		fieldsData = Long.valueOf(value);
	}

	public StoredField(String name, double value)
	{
		super(name, TYPE);
		fieldsData = Double.valueOf(value);
	}

	static 
	{
		TYPE = new FieldType();
		TYPE.setStored(true);
		TYPE.freeze();
	}
}
