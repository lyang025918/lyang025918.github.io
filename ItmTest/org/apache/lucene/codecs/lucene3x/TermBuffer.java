// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermBuffer.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.index.*;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.BytesRef;

/**
 * @deprecated Class TermBuffer is deprecated
 */

final class TermBuffer
	implements Cloneable
{

	private String field;
	private Term term;
	private BytesRef bytes;
	private int currentFieldNumber;
	private static final Comparator utf8AsUTF16Comparator = BytesRef.getUTF8SortedAsUTF16Comparator();
	int newSuffixStart;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/TermBuffer.desiredAssertionStatus();

	TermBuffer()
	{
		bytes = new BytesRef(10);
		currentFieldNumber = -2;
	}

	public int compareTo(TermBuffer other)
	{
		if (field == other.field)
			return utf8AsUTF16Comparator.compare(bytes, other.bytes);
		else
			return field.compareTo(other.field);
	}

	public void read(IndexInput input, FieldInfos fieldInfos)
		throws IOException
	{
		term = null;
		newSuffixStart = input.readVInt();
		int length = input.readVInt();
		int totalLength = newSuffixStart + length;
		if (bytes.bytes.length < totalLength)
			bytes.grow(totalLength);
		bytes.length = totalLength;
		input.readBytes(bytes.bytes, newSuffixStart, length);
		int fieldNumber = input.readVInt();
		if (fieldNumber != currentFieldNumber)
		{
			currentFieldNumber = fieldNumber;
			if (currentFieldNumber == -1)
			{
				field = "";
			} else
			{
				if (!$assertionsDisabled && fieldInfos.fieldInfo(currentFieldNumber) == null)
					throw new AssertionError(currentFieldNumber);
				field = fieldInfos.fieldInfo(currentFieldNumber).name.intern();
			}
		} else
		if (!$assertionsDisabled && !field.equals(fieldInfos.fieldInfo(fieldNumber).name))
			throw new AssertionError((new StringBuilder()).append("currentFieldNumber=").append(currentFieldNumber).append(" field=").append(field).append(" vs ").append(fieldInfos.fieldInfo(fieldNumber)).toString() != null ? ((Object) (fieldInfos.fieldInfo(fieldNumber).name)) : "null");
	}

	public void set(Term term)
	{
		if (term == null)
		{
			reset();
			return;
		} else
		{
			bytes.copyBytes(term.bytes());
			field = term.field().intern();
			currentFieldNumber = -1;
			this.term = term;
			return;
		}
	}

	public void set(TermBuffer other)
	{
		field = other.field;
		currentFieldNumber = other.currentFieldNumber;
		term = null;
		bytes.copyBytes(other.bytes);
	}

	public void reset()
	{
		field = null;
		term = null;
		currentFieldNumber = -1;
	}

	public Term toTerm()
	{
		if (field == null)
			return null;
		if (term == null)
			term = new Term(field, BytesRef.deepCopyOf(bytes));
		return term;
	}

	protected TermBuffer clone()
	{
		TermBuffer clone = null;
		try
		{
			clone = (TermBuffer)super.clone();
		}
		catch (CloneNotSupportedException e) { }
		clone.bytes = BytesRef.deepCopyOf(bytes);
		return clone;
	}

	protected volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
