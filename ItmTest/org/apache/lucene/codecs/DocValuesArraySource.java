// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocValuesArraySource.java

package org.apache.lucene.codecs;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.BytesRef;

public abstract class DocValuesArraySource extends org.apache.lucene.index.DocValues.Source
{
	static final class DoubleValues extends DocValuesArraySource
	{

		private final double values[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesArraySource.desiredAssertionStatus();

		public double[] getArray()
		{
			return values;
		}

		public double getFloat(int docID)
		{
			if (!$assertionsDisabled && (docID < 0 || docID >= values.length))
				throw new AssertionError();
			else
				return values[docID];
		}

		public DocValuesArraySource newFromInput(IndexInput input, int numDocs)
			throws IOException
		{
			return new DoubleValues(input, numDocs);
		}

		public DocValuesArraySource newFromArray(Object array)
		{
			if (!$assertionsDisabled && !(array instanceof double[]))
				throw new AssertionError();
			else
				return new DoubleValues((double[])(double[])array);
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			toBytes(getFloat(docID), ref);
			return ref;
		}

		public volatile Object getArray()
		{
			return getArray();
		}


		DoubleValues()
		{
			super(8, org.apache.lucene.index.DocValues.Type.FLOAT_64);
			values = new double[0];
		}

		private DoubleValues(IndexInput input, int numDocs)
			throws IOException
		{
			super(8, org.apache.lucene.index.DocValues.Type.FLOAT_64);
			values = new double[numDocs];
			for (int i = 0; i < values.length; i++)
				values[i] = Double.longBitsToDouble(input.readLong());

		}

		private DoubleValues(double array[])
		{
			super(8, org.apache.lucene.index.DocValues.Type.FLOAT_64);
			values = array;
		}
	}

	static final class FloatValues extends DocValuesArraySource
	{

		private final float values[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesArraySource.desiredAssertionStatus();

		public float[] getArray()
		{
			return values;
		}

		public double getFloat(int docID)
		{
			if (!$assertionsDisabled && (docID < 0 || docID >= values.length))
				throw new AssertionError();
			else
				return (double)values[docID];
		}

		public void toBytes(double value, BytesRef bytesRef)
		{
			copyInt(bytesRef, Float.floatToRawIntBits((float)value));
		}

		public DocValuesArraySource newFromInput(IndexInput input, int numDocs)
			throws IOException
		{
			return new FloatValues(input, numDocs);
		}

		public DocValuesArraySource newFromArray(Object array)
		{
			if (!$assertionsDisabled && !(array instanceof float[]))
				throw new AssertionError();
			else
				return new FloatValues((float[])(float[])array);
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			toBytes(getFloat(docID), ref);
			return ref;
		}

		public volatile Object getArray()
		{
			return getArray();
		}


		FloatValues()
		{
			super(4, org.apache.lucene.index.DocValues.Type.FLOAT_32);
			values = new float[0];
		}

		private FloatValues(IndexInput input, int numDocs)
			throws IOException
		{
			super(4, org.apache.lucene.index.DocValues.Type.FLOAT_32);
			values = new float[numDocs];
			for (int i = 0; i < values.length; i++)
				values[i] = Float.intBitsToFloat(input.readInt());

		}

		private FloatValues(float array[])
		{
			super(4, org.apache.lucene.index.DocValues.Type.FLOAT_32);
			values = array;
		}
	}

	static final class LongValues extends DocValuesArraySource
	{

		private final long values[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesArraySource.desiredAssertionStatus();

		public long[] getArray()
		{
			return values;
		}

		public long getInt(int docID)
		{
			if (!$assertionsDisabled && (docID < 0 || docID >= values.length))
				throw new AssertionError();
			else
				return values[docID];
		}

		public DocValuesArraySource newFromInput(IndexInput input, int numDocs)
			throws IOException
		{
			return new LongValues(input, numDocs);
		}

		public DocValuesArraySource newFromArray(Object array)
		{
			if (!$assertionsDisabled && !(array instanceof long[]))
				throw new AssertionError();
			else
				return new LongValues((long[])(long[])array);
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			toBytes(getInt(docID), ref);
			return ref;
		}

		public volatile Object getArray()
		{
			return getArray();
		}


		LongValues()
		{
			super(8, org.apache.lucene.index.DocValues.Type.FIXED_INTS_64);
			values = new long[0];
		}

		private LongValues(IndexInput input, int numDocs)
			throws IOException
		{
			super(8, org.apache.lucene.index.DocValues.Type.FIXED_INTS_64);
			values = new long[numDocs];
			for (int i = 0; i < values.length; i++)
				values[i] = input.readLong();

		}

		private LongValues(long array[])
		{
			super(8, org.apache.lucene.index.DocValues.Type.FIXED_INTS_64);
			values = array;
		}
	}

	static final class IntValues extends DocValuesArraySource
	{

		private final int values[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesArraySource.desiredAssertionStatus();

		public int[] getArray()
		{
			return values;
		}

		public double getFloat(int docID)
		{
			return (double)getInt(docID);
		}

		public long getInt(int docID)
		{
			if (!$assertionsDisabled && (docID < 0 || docID >= values.length))
				throw new AssertionError();
			else
				return (long)(-1 & values[docID]);
		}

		public DocValuesArraySource newFromInput(IndexInput input, int numDocs)
			throws IOException
		{
			return new IntValues(input, numDocs);
		}

		public void toBytes(long value, BytesRef bytesRef)
		{
			copyInt(bytesRef, (int)(-1L & value));
		}

		public DocValuesArraySource newFromArray(Object array)
		{
			if (!$assertionsDisabled && !(array instanceof int[]))
				throw new AssertionError();
			else
				return new IntValues((int[])(int[])array);
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			toBytes(getInt(docID), ref);
			return ref;
		}

		public volatile Object getArray()
		{
			return getArray();
		}


		IntValues()
		{
			super(4, org.apache.lucene.index.DocValues.Type.FIXED_INTS_32);
			values = new int[0];
		}

		private IntValues(IndexInput input, int numDocs)
			throws IOException
		{
			super(4, org.apache.lucene.index.DocValues.Type.FIXED_INTS_32);
			values = new int[numDocs];
			for (int i = 0; i < values.length; i++)
				values[i] = input.readInt();

		}

		private IntValues(int array[])
		{
			super(4, org.apache.lucene.index.DocValues.Type.FIXED_INTS_32);
			values = array;
		}
	}

	static final class ShortValues extends DocValuesArraySource
	{

		private final short values[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesArraySource.desiredAssertionStatus();

		public short[] getArray()
		{
			return values;
		}

		public double getFloat(int docID)
		{
			return (double)getInt(docID);
		}

		public long getInt(int docID)
		{
			if (!$assertionsDisabled && (docID < 0 || docID >= values.length))
				throw new AssertionError();
			else
				return (long)values[docID];
		}

		public DocValuesArraySource newFromInput(IndexInput input, int numDocs)
			throws IOException
		{
			return new ShortValues(input, numDocs);
		}

		public void toBytes(long value, BytesRef bytesRef)
		{
			copyShort(bytesRef, (short)(int)(65535L & value));
		}

		public DocValuesArraySource newFromArray(Object array)
		{
			if (!$assertionsDisabled && !(array instanceof short[]))
				throw new AssertionError();
			else
				return new ShortValues((short[])(short[])array);
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			toBytes(getInt(docID), ref);
			return ref;
		}

		public volatile Object getArray()
		{
			return getArray();
		}


		ShortValues()
		{
			super(2, org.apache.lucene.index.DocValues.Type.FIXED_INTS_16);
			values = new short[0];
		}

		private ShortValues(short array[])
		{
			super(2, org.apache.lucene.index.DocValues.Type.FIXED_INTS_16);
			values = array;
		}

		private ShortValues(IndexInput input, int numDocs)
			throws IOException
		{
			super(2, org.apache.lucene.index.DocValues.Type.FIXED_INTS_16);
			values = new short[numDocs];
			for (int i = 0; i < values.length; i++)
				values[i] = input.readShort();

		}
	}

	static final class ByteValues extends DocValuesArraySource
	{

		private final byte values[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesArraySource.desiredAssertionStatus();

		public byte[] getArray()
		{
			return values;
		}

		public double getFloat(int docID)
		{
			return (double)getInt(docID);
		}

		public long getInt(int docID)
		{
			if (!$assertionsDisabled && (docID < 0 || docID >= values.length))
				throw new AssertionError();
			else
				return (long)values[docID];
		}

		public DocValuesArraySource newFromInput(IndexInput input, int numDocs)
			throws IOException
		{
			return new ByteValues(input, numDocs);
		}

		public DocValuesArraySource newFromArray(Object array)
		{
			if (!$assertionsDisabled && !(array instanceof byte[]))
				throw new AssertionError();
			else
				return new ByteValues((byte[])(byte[])array);
		}

		public void toBytes(long value, BytesRef bytesRef)
		{
			if (bytesRef.bytes.length == 0)
				bytesRef.bytes = new byte[1];
			bytesRef.bytes[0] = (byte)(int)(255L & value);
			bytesRef.offset = 0;
			bytesRef.length = 1;
		}

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			toBytes(getInt(docID), ref);
			return ref;
		}

		public volatile Object getArray()
		{
			return getArray();
		}


		ByteValues()
		{
			super(1, org.apache.lucene.index.DocValues.Type.FIXED_INTS_8);
			values = new byte[0];
		}

		private ByteValues(byte array[])
		{
			super(1, org.apache.lucene.index.DocValues.Type.FIXED_INTS_8);
			values = array;
		}

		private ByteValues(IndexInput input, int numDocs)
			throws IOException
		{
			super(1, org.apache.lucene.index.DocValues.Type.FIXED_INTS_8);
			values = new byte[numDocs];
			input.readBytes(values, 0, values.length, false);
		}
	}


	private static final Map TEMPLATES;
	protected final int bytesPerValue;

	public static DocValuesArraySource forType(org.apache.lucene.index.DocValues.Type type)
	{
		return (DocValuesArraySource)TEMPLATES.get(type);
	}

	DocValuesArraySource(int bytesPerValue, org.apache.lucene.index.DocValues.Type type)
	{
		super(type);
		this.bytesPerValue = bytesPerValue;
	}

	public abstract BytesRef getBytes(int i, BytesRef bytesref);

	public abstract DocValuesArraySource newFromInput(IndexInput indexinput, int i)
		throws IOException;

	public abstract DocValuesArraySource newFromArray(Object obj);

	public final boolean hasArray()
	{
		return true;
	}

	public void toBytes(long value, BytesRef bytesRef)
	{
		copyLong(bytesRef, value);
	}

	public void toBytes(double value, BytesRef bytesRef)
	{
		copyLong(bytesRef, Double.doubleToRawLongBits(value));
	}

	public static void copyLong(BytesRef ref, long value)
	{
		if (ref.bytes.length < 8)
			ref.bytes = new byte[8];
		copyInternal(ref, (int)(value >> 32), ref.offset = 0);
		copyInternal(ref, (int)value, 4);
		ref.length = 8;
	}

	public static void copyInt(BytesRef ref, int value)
	{
		if (ref.bytes.length < 4)
			ref.bytes = new byte[4];
		copyInternal(ref, value, ref.offset = 0);
		ref.length = 4;
	}

	public static void copyShort(BytesRef ref, short value)
	{
		if (ref.bytes.length < 2)
			ref.bytes = new byte[2];
		ref.offset = 0;
		ref.bytes[ref.offset] = (byte)(value >> 8);
		ref.bytes[ref.offset + 1] = (byte)value;
		ref.length = 2;
	}

	private static void copyInternal(BytesRef ref, int value, int startOffset)
	{
		ref.bytes[startOffset] = (byte)(value >> 24);
		ref.bytes[startOffset + 1] = (byte)(value >> 16);
		ref.bytes[startOffset + 2] = (byte)(value >> 8);
		ref.bytes[startOffset + 3] = (byte)value;
	}

	public static short asShort(BytesRef b)
	{
		return (short)(0xffff & (b.bytes[b.offset] & 0xff) << 8 | b.bytes[b.offset + 1] & 0xff);
	}

	public static int asInt(BytesRef b)
	{
		return asIntInternal(b, b.offset);
	}

	public static long asLong(BytesRef b)
	{
		return (long)asIntInternal(b, b.offset) << 32 | (long)asIntInternal(b, b.offset + 4) & 0xffffffffL;
	}

	private static int asIntInternal(BytesRef b, int pos)
	{
		return (b.bytes[pos++] & 0xff) << 24 | (b.bytes[pos++] & 0xff) << 16 | (b.bytes[pos++] & 0xff) << 8 | b.bytes[pos] & 0xff;
	}

	static 
	{
		EnumMap templates = new EnumMap(org/apache/lucene/index/DocValues$Type);
		templates.put(org.apache.lucene.index.DocValues.Type.FIXED_INTS_16, new ShortValues());
		templates.put(org.apache.lucene.index.DocValues.Type.FIXED_INTS_32, new IntValues());
		templates.put(org.apache.lucene.index.DocValues.Type.FIXED_INTS_64, new LongValues());
		templates.put(org.apache.lucene.index.DocValues.Type.FIXED_INTS_8, new ByteValues());
		templates.put(org.apache.lucene.index.DocValues.Type.FLOAT_32, new FloatValues());
		templates.put(org.apache.lucene.index.DocValues.Type.FLOAT_64, new DoubleValues());
		TEMPLATES = Collections.unmodifiableMap(templates);
	}
}
