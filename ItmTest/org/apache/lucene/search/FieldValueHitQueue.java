// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldValueHitQueue.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			FieldComparator, FieldDoc, SortField, ScoreDoc

public abstract class FieldValueHitQueue extends PriorityQueue
{
	private static final class MultiComparatorsFieldValueHitQueue extends FieldValueHitQueue
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldValueHitQueue.desiredAssertionStatus();

		protected boolean lessThan(Entry hitA, Entry hitB)
		{
			if (!$assertionsDisabled && hitA == hitB)
				throw new AssertionError();
			if (!$assertionsDisabled && hitA.slot == hitB.slot)
				throw new AssertionError();
			int numComparators = comparators.length;
			for (int i = 0; i < numComparators; i++)
			{
				int c = reverseMul[i] * comparators[i].compare(hitA.slot, hitB.slot);
				if (c != 0)
					return c > 0;
			}

			return hitA.doc > hitB.doc;
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((Entry)x0, (Entry)x1);
		}


		public MultiComparatorsFieldValueHitQueue(SortField fields[], int size)
			throws IOException
		{
			super(fields, size, null);
			int numComparators = comparators.length;
			for (int i = 0; i < numComparators; i++)
			{
				SortField field = fields[i];
				reverseMul[i] = field.reverse ? -1 : 1;
				setComparator(i, field.getComparator(size, i));
			}

		}
	}

	private static final class OneComparatorFieldValueHitQueue extends FieldValueHitQueue
	{

		private final int oneReverseMul;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldValueHitQueue.desiredAssertionStatus();

		protected boolean lessThan(Entry hitA, Entry hitB)
		{
			if (!$assertionsDisabled && hitA == hitB)
				throw new AssertionError();
			if (!$assertionsDisabled && hitA.slot == hitB.slot)
				throw new AssertionError();
			int c = oneReverseMul * firstComparator.compare(hitA.slot, hitB.slot);
			if (c != 0)
				return c > 0;
			else
				return hitA.doc > hitB.doc;
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((Entry)x0, (Entry)x1);
		}


		public OneComparatorFieldValueHitQueue(SortField fields[], int size)
			throws IOException
		{
			super(fields, size, null);
			SortField field = fields[0];
			setComparator(0, field.getComparator(size, 0));
			oneReverseMul = field.reverse ? -1 : 1;
			reverseMul[0] = oneReverseMul;
		}
	}

	public static class Entry extends ScoreDoc
	{

		public int slot;

		public String toString()
		{
			return (new StringBuilder()).append("slot:").append(slot).append(" ").append(super.toString()).toString();
		}

		public Entry(int slot, int doc, float score)
		{
			super(doc, score);
			this.slot = slot;
		}
	}


	protected final SortField fields[];
	protected final FieldComparator comparators[];
	protected FieldComparator firstComparator;
	protected final int reverseMul[];

	private FieldValueHitQueue(SortField fields[], int size)
	{
		super(size);
		this.fields = fields;
		int numComparators = fields.length;
		comparators = new FieldComparator[numComparators];
		reverseMul = new int[numComparators];
	}

	public static FieldValueHitQueue create(SortField fields[], int size)
		throws IOException
	{
		if (fields.length == 0)
			throw new IllegalArgumentException("Sort must contain at least one field");
		if (fields.length == 1)
			return new OneComparatorFieldValueHitQueue(fields, size);
		else
			return new MultiComparatorsFieldValueHitQueue(fields, size);
	}

	public FieldComparator[] getComparators()
	{
		return comparators;
	}

	public int[] getReverseMul()
	{
		return reverseMul;
	}

	public void setComparator(int pos, FieldComparator comparator)
	{
		if (pos == 0)
			firstComparator = comparator;
		comparators[pos] = comparator;
	}

	protected abstract boolean lessThan(Entry entry, Entry entry1);

	FieldDoc fillFields(Entry entry)
	{
		int n = comparators.length;
		Object fields[] = new Object[n];
		for (int i = 0; i < n; i++)
			fields[i] = comparators[i].value(entry.slot);

		return new FieldDoc(entry.doc, entry.score, fields);
	}

	SortField[] getFields()
	{
		return fields;
	}

	protected volatile boolean lessThan(Object x0, Object x1)
	{
		return lessThan((Entry)x0, (Entry)x1);
	}

}
