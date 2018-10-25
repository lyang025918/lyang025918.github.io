// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocValues.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.packed.PackedInts;

public abstract class DocValues
	implements Closeable
{
	public static abstract class SourceCache
	{

		public abstract Source load(DocValues docvalues)
			throws IOException;

		public abstract void invalidate(DocValues docvalues);

		public synchronized void close(DocValues values)
		{
			invalidate(values);
		}

		protected SourceCache()
		{
		}
	}

	public static final class Type extends Enum
	{

		public static final Type VAR_INTS;
		public static final Type FIXED_INTS_8;
		public static final Type FIXED_INTS_16;
		public static final Type FIXED_INTS_32;
		public static final Type FIXED_INTS_64;
		public static final Type FLOAT_32;
		public static final Type FLOAT_64;
		public static final Type BYTES_FIXED_STRAIGHT;
		public static final Type BYTES_FIXED_DEREF;
		public static final Type BYTES_VAR_STRAIGHT;
		public static final Type BYTES_VAR_DEREF;
		public static final Type BYTES_VAR_SORTED;
		public static final Type BYTES_FIXED_SORTED;
		private static final Type $VALUES[];

		public static Type[] values()
		{
			return (Type[])$VALUES.clone();
		}

		public static Type valueOf(String name)
		{
			return (Type)Enum.valueOf(org/apache/lucene/index/DocValues$Type, name);
		}

		static 
		{
			VAR_INTS = new Type("VAR_INTS", 0);
			FIXED_INTS_8 = new Type("FIXED_INTS_8", 1);
			FIXED_INTS_16 = new Type("FIXED_INTS_16", 2);
			FIXED_INTS_32 = new Type("FIXED_INTS_32", 3);
			FIXED_INTS_64 = new Type("FIXED_INTS_64", 4);
			FLOAT_32 = new Type("FLOAT_32", 5);
			FLOAT_64 = new Type("FLOAT_64", 6);
			BYTES_FIXED_STRAIGHT = new Type("BYTES_FIXED_STRAIGHT", 7);
			BYTES_FIXED_DEREF = new Type("BYTES_FIXED_DEREF", 8);
			BYTES_VAR_STRAIGHT = new Type("BYTES_VAR_STRAIGHT", 9);
			BYTES_VAR_DEREF = new Type("BYTES_VAR_DEREF", 10);
			BYTES_VAR_SORTED = new Type("BYTES_VAR_SORTED", 11);
			BYTES_FIXED_SORTED = new Type("BYTES_FIXED_SORTED", 12);
			$VALUES = (new Type[] {
				VAR_INTS, FIXED_INTS_8, FIXED_INTS_16, FIXED_INTS_32, FIXED_INTS_64, FLOAT_32, FLOAT_64, BYTES_FIXED_STRAIGHT, BYTES_FIXED_DEREF, BYTES_VAR_STRAIGHT, 
				BYTES_VAR_DEREF, BYTES_VAR_SORTED, BYTES_FIXED_SORTED
			});
		}

		private Type(String s, int i)
		{
			super(s, i);
		}
	}

	public static abstract class SortedSource extends Source
	{

		private final Comparator comparator;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocValues.desiredAssertionStatus();

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			int ord = ord(docID);
			if (ord < 0)
				bytesRef.length = 0;
			else
				getByOrd(ord, bytesRef);
			return bytesRef;
		}

		public abstract int ord(int i);

		public abstract BytesRef getByOrd(int i, BytesRef bytesref);

		public boolean hasPackedDocToOrd()
		{
			return false;
		}

		public abstract org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd();

		public Comparator getComparator()
		{
			return comparator;
		}

		public int getOrdByValue(BytesRef value, BytesRef spare)
		{
			return binarySearch(value, spare, 0, getValueCount() - 1);
		}

		private int binarySearch(BytesRef b, BytesRef bytesRef, int low, int high)
		{
			int mid = 0;
			while (low <= high) 
			{
				mid = low + high >>> 1;
				getByOrd(mid, bytesRef);
				int cmp = comparator.compare(bytesRef, b);
				if (cmp < 0)
					low = mid + 1;
				else
				if (cmp > 0)
					high = mid - 1;
				else
					return mid;
			}
			if (!$assertionsDisabled && comparator.compare(bytesRef, b) == 0)
				throw new AssertionError();
			else
				return -(low + 1);
		}

		public SortedSource asSortedSource()
		{
			return this;
		}

		public abstract int getValueCount();


		protected SortedSource(Type type, Comparator comparator)
		{
			super(type);
			this.comparator = comparator;
		}
	}

	public static abstract class Source
	{

		protected final Type type;

		public long getInt(int docID)
		{
			throw new UnsupportedOperationException("ints are not supported");
		}

		public double getFloat(int docID)
		{
			throw new UnsupportedOperationException("floats are not supported");
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			throw new UnsupportedOperationException("bytes are not supported");
		}

		public Type getType()
		{
			return type;
		}

		public boolean hasArray()
		{
			return false;
		}

		public Object getArray()
		{
			throw new UnsupportedOperationException("getArray is not supported");
		}

		public SortedSource asSortedSource()
		{
			throw new UnsupportedOperationException("asSortedSource is not supported");
		}

		protected Source(Type type)
		{
			this.type = type;
		}
	}


	public static final DocValues EMPTY_ARRAY[] = new DocValues[0];
	private volatile SourceCache cache;
	private final Object cacheLock = new Object();

	protected DocValues()
	{
		cache = new SourceCache.DirectSourceCache();
	}

	public abstract Source load()
		throws IOException;

	public Source getSource()
		throws IOException
	{
		return cache.load(this);
	}

	public abstract Source getDirectSource()
		throws IOException;

	public abstract Type getType();

	public void close()
		throws IOException
	{
		cache.close(this);
	}

	public int getValueSize()
	{
		return -1;
	}

	public void setCache(SourceCache cache)
	{
		if (cache == null)
			throw new IllegalArgumentException("cache must not be null");
		synchronized (cacheLock)
		{
			SourceCache toClose = this.cache;
			this.cache = cache;
			toClose.close(this);
		}
	}

	public static Source getDefaultSource(Type type)
	{
		return new Source(type) {

			public long getInt(int docID)
			{
				return 0L;
			}

			public double getFloat(int docID)
			{
				return 0.0D;
			}

			public BytesRef getBytes(int docID, BytesRef ref)
			{
				ref.length = 0;
				return ref;
			}

		};
	}

	public static SortedSource getDefaultSortedSource(Type type, int size)
	{
		org.apache.lucene.util.packed.PackedInts.Reader docToOrd = new org.apache.lucene.util.packed.PackedInts.Reader(size) {

			final int val$size;

			public long get(int index)
			{
				return 0L;
			}

			public int getBitsPerValue()
			{
				return 0;
			}

			public int size()
			{
				return size;
			}

			public boolean hasArray()
			{
				return false;
			}

			public Object getArray()
			{
				return null;
			}

			public int get(int index, long arr[], int off, int len)
			{
				len = Math.min(len, size() - index);
				Arrays.fill(arr, off, off + len, 0L);
				return len;
			}

			public long ramBytesUsed()
			{
				return 0L;
			}

			
			{
				size = i;
				super();
			}
		};
		return new SortedSource(type, BytesRef.getUTF8SortedAsUnicodeComparator(), docToOrd) {

			static final boolean $assertionsDisabled = !org/apache/lucene/index/DocValues.desiredAssertionStatus();
			final org.apache.lucene.util.packed.PackedInts.Reader val$docToOrd;

			public BytesRef getBytes(int docID, BytesRef ref)
			{
				ref.length = 0;
				return ref;
			}

			public int ord(int docID)
			{
				return 0;
			}

			public BytesRef getByOrd(int ord, BytesRef bytesRef)
			{
				if (!$assertionsDisabled && ord != 0)
				{
					throw new AssertionError();
				} else
				{
					bytesRef.length = 0;
					return bytesRef;
				}
			}

			public boolean hasPackedDocToOrd()
			{
				return true;
			}

			public org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd()
			{
				return docToOrd;
			}

			public int getOrdByValue(BytesRef value, BytesRef spare)
			{
				return value.length != 0 ? -1 : 0;
			}

			public int getValueCount()
			{
				return 1;
			}


			
			{
				docToOrd = reader;
				super(x0, x1);
			}
		};
	}

}
