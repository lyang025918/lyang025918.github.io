// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FloatDocValuesField.java

package org.apache.lucene.document;

import org.apache.lucene.index.DocValues;

// Referenced classes of package org.apache.lucene.document:
//			Field, FieldType

public class FloatDocValuesField extends Field
{

	public static final FieldType TYPE;

	public FloatDocValuesField(String name, float value)
	{
		super(name, TYPE);
		fieldsData = Float.valueOf(value);
	}

	static 
	{
		TYPE = new FieldType();
		TYPE.setDocValueType(org.apache.lucene.index.DocValues.Type.FLOAT_32);
		TYPE.freeze();
	}
}
