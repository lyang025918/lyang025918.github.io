// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexableFieldType.java

package org.apache.lucene.index;


// Referenced classes of package org.apache.lucene.index:
//			FieldInfo, DocValues

public interface IndexableFieldType
{

	public abstract boolean indexed();

	public abstract boolean stored();

	public abstract boolean tokenized();

	public abstract boolean storeTermVectors();

	public abstract boolean storeTermVectorOffsets();

	public abstract boolean storeTermVectorPositions();

	public abstract boolean storeTermVectorPayloads();

	public abstract boolean omitNorms();

	public abstract FieldInfo.IndexOptions indexOptions();

	public abstract DocValues.Type docValueType();
}
