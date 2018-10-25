// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentStoredFieldVisitor.java

package org.apache.lucene.document;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.StoredFieldVisitor;

// Referenced classes of package org.apache.lucene.document:
//			Document, StoredField, FieldType, Field, 
//			TextField

public class DocumentStoredFieldVisitor extends StoredFieldVisitor
{

	private final Document doc;
	private final Set fieldsToAdd;

	public DocumentStoredFieldVisitor(Set fieldsToAdd)
	{
		doc = new Document();
		this.fieldsToAdd = fieldsToAdd;
	}

	public transient DocumentStoredFieldVisitor(String fields[])
	{
		doc = new Document();
		fieldsToAdd = new HashSet(fields.length);
		String arr$[] = fields;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String field = arr$[i$];
			fieldsToAdd.add(field);
		}

	}

	public DocumentStoredFieldVisitor()
	{
		doc = new Document();
		fieldsToAdd = null;
	}

	public void binaryField(FieldInfo fieldInfo, byte value[])
		throws IOException
	{
		doc.add(new StoredField(fieldInfo.name, value));
	}

	public void stringField(FieldInfo fieldInfo, String value)
		throws IOException
	{
		FieldType ft = new FieldType(TextField.TYPE_STORED);
		ft.setStoreTermVectors(fieldInfo.hasVectors());
		ft.setIndexed(fieldInfo.isIndexed());
		ft.setOmitNorms(fieldInfo.omitsNorms());
		ft.setIndexOptions(fieldInfo.getIndexOptions());
		doc.add(new Field(fieldInfo.name, value, ft));
	}

	public void intField(FieldInfo fieldInfo, int value)
	{
		doc.add(new StoredField(fieldInfo.name, value));
	}

	public void longField(FieldInfo fieldInfo, long value)
	{
		doc.add(new StoredField(fieldInfo.name, value));
	}

	public void floatField(FieldInfo fieldInfo, float value)
	{
		doc.add(new StoredField(fieldInfo.name, value));
	}

	public void doubleField(FieldInfo fieldInfo, double value)
	{
		doc.add(new StoredField(fieldInfo.name, value));
	}

	public org.apache.lucene.index.StoredFieldVisitor.Status needsField(FieldInfo fieldInfo)
		throws IOException
	{
		return fieldsToAdd != null && !fieldsToAdd.contains(fieldInfo.name) ? org.apache.lucene.index.StoredFieldVisitor.Status.NO : org.apache.lucene.index.StoredFieldVisitor.Status.YES;
	}

	public Document getDocument()
	{
		return doc;
	}
}
