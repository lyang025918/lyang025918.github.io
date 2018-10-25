// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DirectSource.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.IOException;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.BytesRef;

abstract class DirectSource extends org.apache.lucene.index.DocValues.Source
{
	private static final class LongToLong extends ToNumeric
	{

		long toLong(IndexInput input)
			throws IOException
		{
			return input.readLong();
		}

		double toDouble(IndexInput input)
		{
			throw new UnsupportedOperationException("doubles are not supported");
		}

		private LongToLong()
		{
		}

	}

	private static final class BytesToDouble extends ToNumeric
	{

		long toLong(IndexInput input)
		{
			throw new UnsupportedOperationException("ints are not supported");
		}

		double toDouble(IndexInput input)
			throws IOException
		{
			return Double.longBitsToDouble(input.readLong());
		}

		private BytesToDouble()
		{
		}

	}

	private static final class BytesToFloat extends ToNumeric
	{

		long toLong(IndexInput input)
		{
			throw new UnsupportedOperationException("ints are not supported");
		}

		double toDouble(IndexInput input)
			throws IOException
		{
			return (double)Float.intBitsToFloat(input.readInt());
		}

		private BytesToFloat()
		{
		}

	}

	private static final class IntToLong extends ToNumeric
	{

		long toLong(IndexInput input)
			throws IOException
		{
			return (long)input.readInt();
		}

		private IntToLong()
		{
		}

	}

	private static final class ShortToLong extends ToNumeric
	{

		long toLong(IndexInput input)
			throws IOException
		{
			return (long)input.readShort();
		}

		private ShortToLong()
		{
		}

	}

	private static final class ByteToLong extends ToNumeric
	{

		long toLong(IndexInput input)
			throws IOException
		{
			return (long)input.readByte();
		}

		private ByteToLong()
		{
		}

	}

	private static abstract class ToNumeric
	{

		abstract long toLong(IndexInput indexinput)
			throws IOException;

		double toDouble(IndexInput input)
			throws IOException
		{
			return (double)toLong(input);
		}

		private ToNumeric()
		{
		}

	}


	protected final IndexInput data;
	private final ToNumeric toNumeric;
	protected final long baseOffset;

	public DirectSource(IndexInput input, org.apache.lucene.index.DocValues.Type type)
	{
		super(type);
		data = input;
		baseOffset = input.getFilePointer();
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[org.apache.lucene.index.DocValues.Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_16.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_32.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_64.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_32.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_8.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
			toNumeric = new ShortToLong(null);
			break;

		case 2: // '\002'
			toNumeric = new BytesToFloat(null);
			break;

		case 3: // '\003'
			toNumeric = new BytesToDouble(null);
			break;

		case 4: // '\004'
			toNumeric = new IntToLong(null);
			break;

		case 5: // '\005'
			toNumeric = new ByteToLong(null);
			break;

		default:
			toNumeric = new LongToLong(null);
			break;
		}
	}

	public BytesRef getBytes(int docID, BytesRef ref)
	{
		int sizeToRead = position(docID);
		ref.offset = 0;
		ref.grow(sizeToRead);
		data.readBytes(ref.bytes, 0, sizeToRead);
		ref.length = sizeToRead;
		return ref;
		IOException ex;
		ex;
		throw new IllegalStateException((new StringBuilder()).append("failed to get value for docID: ").append(docID).toString(), ex);
	}

	public long getInt(int docID)
	{
		position(docID);
		return toNumeric.toLong(data);
		IOException ex;
		ex;
		throw new IllegalStateException((new StringBuilder()).append("failed to get value for docID: ").append(docID).toString(), ex);
	}

	public double getFloat(int docID)
	{
		position(docID);
		return toNumeric.toDouble(data);
		IOException ex;
		ex;
		throw new IllegalStateException((new StringBuilder()).append("failed to get value for docID: ").append(docID).toString(), ex);
	}

	protected abstract int position(int i)
		throws IOException;
}
