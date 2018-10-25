// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SortedBytesDocValuesField.java

package org.apache.lucene.document;

import org.apache.lucene.index.DocValues;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.document:
//			Field, FieldType

public class SortedBytesDocValuesField extends Field
{

	public static final FieldType TYPE_FIXED_LEN;
	public static final FieldType TYPE_VAR_LEN;

	public SortedBytesDocValuesField(String name, BytesRef bytes)
	{
		this(name, bytes, false);
	}

	public SortedBytesDocValuesField(String name, BytesRef bytes, boolean isFixedLength)
	{
		super(name, isFixedLength ? TYPE_FIXED_LEN : TYPE_VAR_LEN);
		fieldsData = bytes;
	}

	static 
	{
		TYPE_FIXED_LEN = new FieldType();
		TYPE_FIXED_LEN.setDocValueType(org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED);
		TYPE_FIXED_LEN.freeze();
		TYPE_VAR_LEN = new FieldType();
		TYPE_VAR_LEN.setDocValueType(org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED);
		TYPE_VAR_LEN.freeze();
	}
}
