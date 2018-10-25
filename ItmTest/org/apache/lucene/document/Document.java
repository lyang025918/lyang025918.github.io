// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Document.java

package org.apache.lucene.document;

import java.util.*;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.util.BytesRef;

public final class Document
	implements Iterable
{

	private final List fields = new ArrayList();
	private static final String NO_STRINGS[] = new String[0];

	public Document()
	{
	}

	public Iterator iterator()
	{
		return fields.iterator();
	}

	public final void add(IndexableField field)
	{
		fields.add(field);
	}

	public final void removeField(String name)
	{
		for (Iterator it = fields.iterator(); it.hasNext();)
		{
			IndexableField field = (IndexableField)it.next();
			if (field.name().equals(name))
			{
				it.remove();
				return;
			}
		}

	}

	public final void removeFields(String name)
	{
		Iterator it = fields.iterator();
		do
		{
			if (!it.hasNext())
				break;
			IndexableField field = (IndexableField)it.next();
			if (field.name().equals(name))
				it.remove();
		} while (true);
	}

	public final BytesRef[] getBinaryValues(String name)
	{
		List result = new ArrayList();
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexableField field = (IndexableField)i$.next();
			if (field.name().equals(name))
			{
				BytesRef bytes = field.binaryValue();
				if (bytes != null)
					result.add(bytes);
			}
		} while (true);
		return (BytesRef[])result.toArray(new BytesRef[result.size()]);
	}

	public final BytesRef getBinaryValue(String name)
	{
		for (Iterator i$ = fields.iterator(); i$.hasNext();)
		{
			IndexableField field = (IndexableField)i$.next();
			if (field.name().equals(name))
			{
				BytesRef bytes = field.binaryValue();
				if (bytes != null)
					return bytes;
			}
		}

		return null;
	}

	public final IndexableField getField(String name)
	{
		for (Iterator i$ = fields.iterator(); i$.hasNext();)
		{
			IndexableField field = (IndexableField)i$.next();
			if (field.name().equals(name))
				return field;
		}

		return null;
	}

	public IndexableField[] getFields(String name)
	{
		List result = new ArrayList();
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexableField field = (IndexableField)i$.next();
			if (field.name().equals(name))
				result.add(field);
		} while (true);
		return (IndexableField[])result.toArray(new IndexableField[result.size()]);
	}

	public final List getFields()
	{
		return fields;
	}

	public final String[] getValues(String name)
	{
		List result = new ArrayList();
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexableField field = (IndexableField)i$.next();
			if (field.name().equals(name) && field.stringValue() != null)
				result.add(field.stringValue());
		} while (true);
		if (result.size() == 0)
			return NO_STRINGS;
		else
			return (String[])result.toArray(new String[result.size()]);
	}

	public final String get(String name)
	{
		for (Iterator i$ = fields.iterator(); i$.hasNext();)
		{
			IndexableField field = (IndexableField)i$.next();
			if (field.name().equals(name) && field.stringValue() != null)
				return field.stringValue();
		}

		return null;
	}

	public final String toString()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("Document<");
		for (int i = 0; i < fields.size(); i++)
		{
			IndexableField field = (IndexableField)fields.get(i);
			buffer.append(field.toString());
			if (i != fields.size() - 1)
				buffer.append(" ");
		}

		buffer.append(">");
		return buffer.toString();
	}

}
