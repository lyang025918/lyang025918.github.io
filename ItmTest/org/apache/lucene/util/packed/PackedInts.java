// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PackedInts.java

package org.apache.lucene.util.packed;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.store.*;
import org.apache.lucene.util.LongsRef;

// Referenced classes of package org.apache.lucene.util.packed:
//			Direct8, Direct16, Direct32, Direct64, 
//			Packed8ThreeBlocks, Packed16ThreeBlocks, Packed64, PackedReaderIterator, 
//			DirectPackedReader, DirectPacked64SingleBlockReader, PackedWriter, BulkOperation, 
//			Packed64SingleBlock

public class PackedInts
{
	public static abstract class Writer
	{

		protected final DataOutput out;
		protected final int valueCount;
		protected final int bitsPerValue;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

		void writeHeader()
			throws IOException
		{
			if (!$assertionsDisabled && valueCount == -1)
			{
				throw new AssertionError();
			} else
			{
				CodecUtil.writeHeader(out, "PackedInts", 0);
				out.writeVInt(bitsPerValue);
				out.writeVInt(valueCount);
				out.writeVInt(getFormat().getId());
				return;
			}
		}

		protected abstract Format getFormat();

		public abstract void add(long l)
			throws IOException;

		public final int bitsPerValue()
		{
			return bitsPerValue;
		}

		public abstract void finish()
			throws IOException;

		public abstract int ord();


		protected Writer(DataOutput out, int valueCount, int bitsPerValue)
		{
			if (!$assertionsDisabled && bitsPerValue > 64)
				throw new AssertionError();
			if (!$assertionsDisabled && valueCount < 0 && valueCount != -1)
			{
				throw new AssertionError();
			} else
			{
				this.out = out;
				this.valueCount = valueCount;
				this.bitsPerValue = bitsPerValue;
				return;
			}
		}
	}

	static abstract class MutableImpl extends ReaderImpl
		implements Mutable
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

		public int set(int index, long arr[], int off, int len)
		{
			if (!$assertionsDisabled && len <= 0)
				throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
			if (!$assertionsDisabled && (index < 0 || index >= valueCount))
				throw new AssertionError();
			len = Math.min(len, valueCount - index);
			if (!$assertionsDisabled && off + len > arr.length)
				throw new AssertionError();
			int i = index;
			int o = off;
			for (int end = index + len; i < end;)
			{
				set(i, arr[o]);
				i++;
				o++;
			}

			return len;
		}

		public void fill(int fromIndex, int toIndex, long val)
		{
			if (!$assertionsDisabled && val > PackedInts.maxValue(bitsPerValue))
				throw new AssertionError();
			if (!$assertionsDisabled && fromIndex > toIndex)
				throw new AssertionError();
			for (int i = fromIndex; i < toIndex; i++)
				set(i, val);

		}

		protected Format getFormat()
		{
			return Format.PACKED;
		}

		public void save(DataOutput out)
			throws IOException
		{
			Writer writer = PackedInts.getWriterNoHeader(out, getFormat(), valueCount, bitsPerValue, 1024);
			writer.writeHeader();
			for (int i = 0; i < valueCount; i++)
				writer.add(get(i));

			writer.finish();
		}


		protected MutableImpl(int valueCount, int bitsPerValue)
		{
			super(valueCount, bitsPerValue);
		}
	}

	static abstract class ReaderImpl
		implements Reader
	{

		protected final int bitsPerValue;
		protected final int valueCount;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

		public int getBitsPerValue()
		{
			return bitsPerValue;
		}

		public int size()
		{
			return valueCount;
		}

		public Object getArray()
		{
			return null;
		}

		public boolean hasArray()
		{
			return false;
		}

		public int get(int index, long arr[], int off, int len)
		{
			if (!$assertionsDisabled && len <= 0)
				throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
			if (!$assertionsDisabled && (index < 0 || index >= valueCount))
				throw new AssertionError();
			if (!$assertionsDisabled && off + len > arr.length)
				throw new AssertionError();
			int gets = Math.min(valueCount - index, len);
			int i = index;
			int o = off;
			for (int end = index + gets; i < end;)
			{
				arr[o] = get(i);
				i++;
				o++;
			}

			return gets;
		}


		protected ReaderImpl(int valueCount, int bitsPerValue)
		{
			this.bitsPerValue = bitsPerValue;
			if (!$assertionsDisabled && (bitsPerValue <= 0 || bitsPerValue > 64))
			{
				throw new AssertionError((new StringBuilder()).append("bitsPerValue=").append(bitsPerValue).toString());
			} else
			{
				this.valueCount = valueCount;
				return;
			}
		}
	}

	public static interface Mutable
		extends Reader
	{

		public abstract void set(int i, long l);

		public abstract int set(int i, long al[], int j, int k);

		public abstract void fill(int i, int j, long l);

		public abstract void clear();

		public abstract void save(DataOutput dataoutput)
			throws IOException;
	}

	static abstract class ReaderIteratorImpl
		implements ReaderIterator
	{

		protected final DataInput in;
		protected final int bitsPerValue;
		protected final int valueCount;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

		public long next()
			throws IOException
		{
			LongsRef nextValues = next(1);
			if (!$assertionsDisabled && nextValues.length <= 0)
			{
				throw new AssertionError();
			} else
			{
				long result = nextValues.longs[nextValues.offset];
				nextValues.offset++;
				nextValues.length--;
				return result;
			}
		}

		public int getBitsPerValue()
		{
			return bitsPerValue;
		}

		public int size()
		{
			return valueCount;
		}

		public void close()
			throws IOException
		{
			if (in instanceof Closeable)
				((Closeable)in).close();
		}


		protected ReaderIteratorImpl(int valueCount, int bitsPerValue, DataInput in)
		{
			this.in = in;
			this.bitsPerValue = bitsPerValue;
			this.valueCount = valueCount;
		}
	}

	public static interface ReaderIterator
		extends Closeable
	{

		public abstract long next()
			throws IOException;

		public abstract LongsRef next(int i)
			throws IOException;

		public abstract int getBitsPerValue();

		public abstract int size();

		public abstract int ord();
	}

	public static interface Reader
	{

		public abstract long get(int i);

		public abstract int get(int i, long al[], int j, int k);

		public abstract int getBitsPerValue();

		public abstract int size();

		public abstract long ramBytesUsed();

		public abstract Object getArray();

		public abstract boolean hasArray();
	}

	public static interface Encoder
	{

		public abstract int blockCount();

		public abstract int valueCount();

		public abstract void encode(long al[], int i, long al1[], int j, int k);

		public abstract void encode(long al[], int i, byte abyte0[], int j, int k);

		public abstract void encode(int ai[], int i, long al[], int j, int k);

		public abstract void encode(int ai[], int i, byte abyte0[], int j, int k);
	}

	public static interface Decoder
	{

		public abstract int blockCount();

		public abstract int valueCount();

		public abstract void decode(long al[], int i, long al1[], int j, int k);

		public abstract void decode(byte abyte0[], int i, long al[], int j, int k);

		public abstract void decode(long al[], int i, int ai[], int j, int k);

		public abstract void decode(byte abyte0[], int i, int ai[], int j, int k);
	}

	public static class FormatAndBits
	{

		public final Format format;
		public final int bitsPerValue;

		public FormatAndBits(Format format, int bitsPerValue)
		{
			this.format = format;
			this.bitsPerValue = bitsPerValue;
		}
	}

	public static abstract class Format extends Enum
	{

		public static final Format PACKED;
		public static final Format PACKED_SINGLE_BLOCK;
		public int id;
		private static final Format $VALUES[];
		static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

		public static Format[] values()
		{
			return (Format[])$VALUES.clone();
		}

		public static Format valueOf(String name)
		{
			return (Format)Enum.valueOf(org/apache/lucene/util/packed/PackedInts$Format, name);
		}

		public static Format byId(int id)
		{
			Format arr$[] = values();
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Format format = arr$[i$];
				if (format.getId() == id)
					return format;
			}

			throw new IllegalArgumentException((new StringBuilder()).append("Unknown format id: ").append(id).toString());
		}

		public int getId()
		{
			return id;
		}

		public abstract int nblocks(int i, int j);

		public boolean isSupported(int bitsPerValue)
		{
			return bitsPerValue >= 1 && bitsPerValue <= 64;
		}

		public float overheadPerValue(int bitsPerValue)
		{
			if (!$assertionsDisabled && !isSupported(bitsPerValue))
				throw new AssertionError();
			else
				return 0.0F;
		}

		public final float overheadRatio(int bitsPerValue)
		{
			if (!$assertionsDisabled && !isSupported(bitsPerValue))
				throw new AssertionError();
			else
				return overheadPerValue(bitsPerValue) / (float)bitsPerValue;
		}

		static 
		{
			PACKED = new Format("PACKED", 0, 0) {

				public int nblocks(int bitsPerValue, int values)
				{
					return (int)Math.ceil(((double)values * (double)bitsPerValue) / 64D);
				}

			};
			PACKED_SINGLE_BLOCK = new Format("PACKED_SINGLE_BLOCK", 1, 1) {

				static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

				public int nblocks(int bitsPerValue, int values)
				{
					int valuesPerBlock = 64 / bitsPerValue;
					return (int)Math.ceil((double)values / (double)valuesPerBlock);
				}

				public boolean isSupported(int bitsPerValue)
				{
					return Packed64SingleBlock.isSupported(bitsPerValue);
				}

				public float overheadPerValue(int bitsPerValue)
				{
					if (!$assertionsDisabled && !isSupported(bitsPerValue))
					{
						throw new AssertionError();
					} else
					{
						int valuesPerBlock = 64 / bitsPerValue;
						int overhead = 64 % bitsPerValue;
						return (float)overhead / (float)valuesPerBlock;
					}
				}


			};
			$VALUES = (new Format[] {
				PACKED, PACKED_SINGLE_BLOCK
			});
		}

		private Format(String s, int i, int id)
		{
			super(s, i);
			this.id = id;
		}

	}


	public static final float FASTEST = 7F;
	public static final float FAST = 0.5F;
	public static final float DEFAULT = 0.2F;
	public static final float COMPACT = 0F;
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	public static final String CODEC_NAME = "PackedInts";
	public static final int VERSION_START = 0;
	public static final int VERSION_CURRENT = 0;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedInts.desiredAssertionStatus();

	public PackedInts()
	{
	}

	private static void checkVersion(int version)
	{
		if (version < 0)
			throw new IllegalArgumentException((new StringBuilder()).append("Version is too old, should be at least 0 (got ").append(version).append(")").toString());
		if (version > 0)
			throw new IllegalArgumentException((new StringBuilder()).append("Version is too new, should be at most 0 (got ").append(version).append(")").toString());
		else
			return;
	}

	public static FormatAndBits fastestFormatAndBits(int valueCount, int bitsPerValue, float acceptableOverheadRatio)
	{
		if (valueCount == -1)
			valueCount = 0x7fffffff;
		acceptableOverheadRatio = Math.max(0.0F, acceptableOverheadRatio);
		acceptableOverheadRatio = Math.min(7F, acceptableOverheadRatio);
		float acceptableOverheadPerValue = acceptableOverheadRatio * (float)bitsPerValue;
		int maxBitsPerValue = bitsPerValue + (int)acceptableOverheadPerValue;
		int actualBitsPerValue = -1;
		Format format = Format.PACKED;
		if (bitsPerValue <= 8 && maxBitsPerValue >= 8)
			actualBitsPerValue = 8;
		else
		if (bitsPerValue <= 16 && maxBitsPerValue >= 16)
			actualBitsPerValue = 16;
		else
		if (bitsPerValue <= 32 && maxBitsPerValue >= 32)
			actualBitsPerValue = 32;
		else
		if (bitsPerValue <= 64 && maxBitsPerValue >= 64)
			actualBitsPerValue = 64;
		else
		if (valueCount <= 0x2aaaaaaa && bitsPerValue <= 24 && maxBitsPerValue >= 24)
			actualBitsPerValue = 24;
		else
		if (valueCount <= 0x2aaaaaaa && bitsPerValue <= 48 && maxBitsPerValue >= 48)
		{
			actualBitsPerValue = 48;
		} else
		{
			for (int bpv = bitsPerValue; bpv <= maxBitsPerValue; bpv++)
			{
				if (!Format.PACKED_SINGLE_BLOCK.isSupported(bpv))
					continue;
				float overhead = Format.PACKED_SINGLE_BLOCK.overheadPerValue(bpv);
				float acceptableOverhead = (acceptableOverheadPerValue + (float)bitsPerValue) - (float)bpv;
				if (overhead > acceptableOverhead)
					continue;
				actualBitsPerValue = bpv;
				format = Format.PACKED_SINGLE_BLOCK;
				break;
			}

			if (actualBitsPerValue < 0)
				actualBitsPerValue = bitsPerValue;
		}
		return new FormatAndBits(format, actualBitsPerValue);
	}

	public static Decoder getDecoder(Format format, int version, int bitsPerValue)
	{
		checkVersion(version);
		return BulkOperation.of(format, bitsPerValue);
	}

	public static Encoder getEncoder(Format format, int version, int bitsPerValue)
	{
		checkVersion(version);
		return BulkOperation.of(format, bitsPerValue);
	}

	public static Reader getReaderNoHeader(DataInput in, Format format, int version, int valueCount, int bitsPerValue)
		throws IOException
	{
		checkVersion(version);
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$util$packed$PackedInts$Format[];

			static 
			{
				$SwitchMap$org$apache$lucene$util$packed$PackedInts$Format = new int[Format.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$util$packed$PackedInts$Format[Format.PACKED_SINGLE_BLOCK.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$packed$PackedInts$Format[Format.PACKED.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.util.packed.PackedInts.Format[format.ordinal()])
		{
		case 1: // '\001'
			return Packed64SingleBlock.create(in, valueCount, bitsPerValue);

		case 2: // '\002'
			switch (bitsPerValue)
			{
			default:
				break;

			case 8: // '\b'
				return new Direct8(in, valueCount);

			case 16: // '\020'
				return new Direct16(in, valueCount);

			case 32: // ' '
				return new Direct32(in, valueCount);

			case 64: // '@'
				return new Direct64(in, valueCount);

			case 24: // '\030'
				if (valueCount <= 0x2aaaaaaa)
					return new Packed8ThreeBlocks(in, valueCount);
				break;

			case 48: // '0'
				if (valueCount <= 0x2aaaaaaa)
					return new Packed16ThreeBlocks(in, valueCount);
				break;
			}
			return new Packed64(in, valueCount, bitsPerValue);
		}
		throw new AssertionError((new StringBuilder()).append("Unknown Writer format: ").append(format).toString());
	}

	public static Reader getReader(DataInput in)
		throws IOException
	{
		int version = CodecUtil.checkHeader(in, "PackedInts", 0, 0);
		int bitsPerValue = in.readVInt();
		if (!$assertionsDisabled && (bitsPerValue <= 0 || bitsPerValue > 64))
		{
			throw new AssertionError((new StringBuilder()).append("bitsPerValue=").append(bitsPerValue).toString());
		} else
		{
			int valueCount = in.readVInt();
			Format format = Format.byId(in.readVInt());
			return getReaderNoHeader(in, format, version, valueCount, bitsPerValue);
		}
	}

	public static ReaderIterator getReaderIteratorNoHeader(DataInput in, Format format, int version, int valueCount, int bitsPerValue, int mem)
	{
		checkVersion(version);
		return new PackedReaderIterator(format, valueCount, bitsPerValue, in, mem);
	}

	public static ReaderIterator getReaderIterator(DataInput in, int mem)
		throws IOException
	{
		int version = CodecUtil.checkHeader(in, "PackedInts", 0, 0);
		int bitsPerValue = in.readVInt();
		if (!$assertionsDisabled && (bitsPerValue <= 0 || bitsPerValue > 64))
		{
			throw new AssertionError((new StringBuilder()).append("bitsPerValue=").append(bitsPerValue).toString());
		} else
		{
			int valueCount = in.readVInt();
			Format format = Format.byId(in.readVInt());
			return getReaderIteratorNoHeader(in, format, version, valueCount, bitsPerValue, mem);
		}
	}

	public static Reader getDirectReaderNoHeader(IndexInput in, Format format, int version, int valueCount, int bitsPerValue)
	{
		checkVersion(version);
		switch (1..SwitchMap.org.apache.lucene.util.packed.PackedInts.Format[format.ordinal()])
		{
		case 2: // '\002'
			return new DirectPackedReader(bitsPerValue, valueCount, in);

		case 1: // '\001'
			return new DirectPacked64SingleBlockReader(bitsPerValue, valueCount, in);
		}
		throw new AssertionError((new StringBuilder()).append("Unknwown format: ").append(format).toString());
	}

	public static Reader getDirectReader(IndexInput in)
		throws IOException
	{
		int version = CodecUtil.checkHeader(in, "PackedInts", 0, 0);
		int bitsPerValue = in.readVInt();
		if (!$assertionsDisabled && (bitsPerValue <= 0 || bitsPerValue > 64))
		{
			throw new AssertionError((new StringBuilder()).append("bitsPerValue=").append(bitsPerValue).toString());
		} else
		{
			int valueCount = in.readVInt();
			Format format = Format.byId(in.readVInt());
			return getDirectReaderNoHeader(in, format, version, valueCount, bitsPerValue);
		}
	}

	public static Mutable getMutable(int valueCount, int bitsPerValue, float acceptableOverheadRatio)
	{
		if (!$assertionsDisabled && valueCount < 0)
			throw new AssertionError();
		FormatAndBits formatAndBits = fastestFormatAndBits(valueCount, bitsPerValue, acceptableOverheadRatio);
		switch (1..SwitchMap.org.apache.lucene.util.packed.PackedInts.Format[formatAndBits.format.ordinal()])
		{
		case 1: // '\001'
			return Packed64SingleBlock.create(valueCount, formatAndBits.bitsPerValue);

		case 2: // '\002'
			switch (formatAndBits.bitsPerValue)
			{
			default:
				break;

			case 8: // '\b'
				return new Direct8(valueCount);

			case 16: // '\020'
				return new Direct16(valueCount);

			case 32: // ' '
				return new Direct32(valueCount);

			case 64: // '@'
				return new Direct64(valueCount);

			case 24: // '\030'
				if (valueCount <= 0x2aaaaaaa)
					return new Packed8ThreeBlocks(valueCount);
				break;

			case 48: // '0'
				if (valueCount <= 0x2aaaaaaa)
					return new Packed16ThreeBlocks(valueCount);
				break;
			}
			return new Packed64(valueCount, formatAndBits.bitsPerValue);
		}
		throw new AssertionError();
	}

	public static Writer getWriterNoHeader(DataOutput out, Format format, int valueCount, int bitsPerValue, int mem)
	{
		return new PackedWriter(format, out, valueCount, bitsPerValue, mem);
	}

	public static Writer getWriter(DataOutput out, int valueCount, int bitsPerValue, float acceptableOverheadRatio)
		throws IOException
	{
		if (!$assertionsDisabled && valueCount < 0)
		{
			throw new AssertionError();
		} else
		{
			FormatAndBits formatAndBits = fastestFormatAndBits(valueCount, bitsPerValue, acceptableOverheadRatio);
			Writer writer = getWriterNoHeader(out, formatAndBits.format, valueCount, formatAndBits.bitsPerValue, 1024);
			writer.writeHeader();
			return writer;
		}
	}

	public static int bitsRequired(long maxValue)
	{
		if (maxValue < 0L)
			throw new IllegalArgumentException((new StringBuilder()).append("maxValue must be non-negative (got: ").append(maxValue).append(")").toString());
		else
			return Math.max(1, 64 - Long.numberOfLeadingZeros(maxValue));
	}

	public static long maxValue(int bitsPerValue)
	{
		return bitsPerValue != 64 ? ~(-1L << bitsPerValue) : 0x7fffffffffffffffL;
	}

	public static void copy(Reader src, int srcPos, Mutable dest, int destPos, int len, int mem)
	{
		if (!$assertionsDisabled && srcPos + len > src.size())
			throw new AssertionError();
		if (!$assertionsDisabled && destPos + len > dest.size())
			throw new AssertionError();
		int capacity = mem >>> 3;
		if (capacity == 0)
		{
			for (int i = 0; i < len; i++)
				dest.set(destPos++, src.get(srcPos++));

		} else
		{
			long buf[] = new long[Math.min(capacity, len)];
			int remaining;
			int written;
			for (remaining = 0; len > 0; remaining -= written)
			{
				int read = src.get(srcPos, buf, remaining, Math.min(len, buf.length - remaining));
				if (!$assertionsDisabled && read <= 0)
					throw new AssertionError();
				srcPos += read;
				len -= read;
				remaining += read;
				written = dest.set(destPos, buf, 0, remaining);
				if (!$assertionsDisabled && written <= 0)
					throw new AssertionError();
				destPos += written;
				if (written < remaining)
					System.arraycopy(buf, written, buf, 0, remaining - written);
			}

			int written;
			for (; remaining > 0; remaining -= written)
				written = dest.set(destPos, buf, 0, remaining);

		}
	}

}
