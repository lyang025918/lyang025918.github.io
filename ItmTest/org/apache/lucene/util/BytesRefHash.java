// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BytesRefHash.java

package org.apache.lucene.util;

import java.util.Arrays;
import java.util.Comparator;

// Referenced classes of package org.apache.lucene.util:
//			ByteBlockPool, BytesRef, Counter, ArrayUtil, 
//			SorterTemplate

public final class BytesRefHash
{
	public static class DirectBytesStartArray extends BytesStartArray
	{

		protected final int initSize;
		private int bytesStart[];
		private final Counter bytesUsed = Counter.newCounter();
		static final boolean $assertionsDisabled = !org/apache/lucene/util/BytesRefHash.desiredAssertionStatus();

		public int[] clear()
		{
			return bytesStart = null;
		}

		public int[] grow()
		{
			if (!$assertionsDisabled && bytesStart == null)
				throw new AssertionError();
			else
				return bytesStart = ArrayUtil.grow(bytesStart, bytesStart.length + 1);
		}

		public int[] init()
		{
			return bytesStart = new int[ArrayUtil.oversize(initSize, 4)];
		}

		public Counter bytesUsed()
		{
			return bytesUsed;
		}


		public DirectBytesStartArray(int initSize)
		{
			this.initSize = initSize;
		}
	}

	public static class TrackingDirectBytesStartArray extends BytesStartArray
	{

		protected final int initSize;
		private int bytesStart[];
		protected final Counter bytesUsed;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/BytesRefHash.desiredAssertionStatus();

		public int[] clear()
		{
			if (bytesStart != null)
				bytesUsed.addAndGet(-bytesStart.length * 4);
			return bytesStart = null;
		}

		public int[] grow()
		{
			if (!$assertionsDisabled && bytesStart == null)
			{
				throw new AssertionError();
			} else
			{
				int oldSize = bytesStart.length;
				bytesStart = ArrayUtil.grow(bytesStart, bytesStart.length + 1);
				bytesUsed.addAndGet((bytesStart.length - oldSize) * 4);
				return bytesStart;
			}
		}

		public int[] init()
		{
			bytesStart = new int[ArrayUtil.oversize(initSize, 4)];
			bytesUsed.addAndGet(bytesStart.length * 4);
			return bytesStart;
		}

		public Counter bytesUsed()
		{
			return bytesUsed;
		}


		public TrackingDirectBytesStartArray(int initSize, Counter bytesUsed)
		{
			this.initSize = initSize;
			this.bytesUsed = bytesUsed;
		}
	}

	public static abstract class BytesStartArray
	{

		public abstract int[] init();

		public abstract int[] grow();

		public abstract int[] clear();

		public abstract Counter bytesUsed();

		public BytesStartArray()
		{
		}
	}

	public static class MaxBytesLengthExceededException extends RuntimeException
	{

		MaxBytesLengthExceededException(String message)
		{
			super(message);
		}
	}


	public static final int DEFAULT_CAPACITY = 16;
	final ByteBlockPool pool;
	int bytesStart[];
	private final BytesRef scratch1;
	private int hashSize;
	private int hashHalfSize;
	private int hashMask;
	private int count;
	private int lastCount;
	private int ords[];
	private final BytesStartArray bytesStartArray;
	private Counter bytesUsed;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/BytesRefHash.desiredAssertionStatus();

	public BytesRefHash()
	{
		this(new ByteBlockPool(new ByteBlockPool.DirectAllocator()));
	}

	public BytesRefHash(ByteBlockPool pool)
	{
		this(pool, 16, ((BytesStartArray) (new DirectBytesStartArray(16))));
	}

	public BytesRefHash(ByteBlockPool pool, int capacity, BytesStartArray bytesStartArray)
	{
		scratch1 = new BytesRef();
		lastCount = -1;
		hashSize = capacity;
		hashHalfSize = hashSize >> 1;
		hashMask = hashSize - 1;
		this.pool = pool;
		ords = new int[hashSize];
		Arrays.fill(ords, -1);
		this.bytesStartArray = bytesStartArray;
		bytesStart = bytesStartArray.init();
		bytesUsed = bytesStartArray.bytesUsed() != null ? bytesStartArray.bytesUsed() : Counter.newCounter();
		bytesUsed.addAndGet(hashSize * 4);
	}

	public int size()
	{
		return count;
	}

	public BytesRef get(int ord, BytesRef ref)
	{
		if (!$assertionsDisabled && bytesStart == null)
			throw new AssertionError("bytesStart is null - not initialized");
		if (!$assertionsDisabled && ord >= bytesStart.length)
			throw new AssertionError((new StringBuilder()).append("ord exceeds byteStart len: ").append(bytesStart.length).toString());
		else
			return pool.setBytesRef(ref, bytesStart[ord]);
	}

	public int[] compact()
	{
		if (!$assertionsDisabled && bytesStart == null)
			throw new AssertionError("Bytesstart is null - not initialized");
		int upto = 0;
		for (int i = 0; i < hashSize; i++)
		{
			if (ords[i] == -1)
				continue;
			if (upto < i)
			{
				ords[upto] = ords[i];
				ords[i] = -1;
			}
			upto++;
		}

		if (!$assertionsDisabled && upto != count)
		{
			throw new AssertionError();
		} else
		{
			lastCount = count;
			return ords;
		}
	}

	public int[] sort(final Comparator comp)
	{
		final int compact[] = compact();
		(new SorterTemplate() {

			private final BytesRef pivot = new BytesRef();
			private final BytesRef scratch1 = new BytesRef();
			private final BytesRef scratch2 = new BytesRef();
			static final boolean $assertionsDisabled = !org/apache/lucene/util/BytesRefHash.desiredAssertionStatus();
			final int val$compact[];
			final Comparator val$comp;
			final BytesRefHash this$0;

			protected void swap(int i, int j)
			{
				int o = compact[i];
				compact[i] = compact[j];
				compact[j] = o;
			}

			protected int compare(int i, int j)
			{
				int ord1 = compact[i];
				int ord2 = compact[j];
				if (!$assertionsDisabled && (bytesStart.length <= ord1 || bytesStart.length <= ord2))
					throw new AssertionError();
				else
					return comp.compare(pool.setBytesRef(scratch1, bytesStart[ord1]), pool.setBytesRef(scratch2, bytesStart[ord2]));
			}

			protected void setPivot(int i)
			{
				int ord = compact[i];
				if (!$assertionsDisabled && bytesStart.length <= ord)
				{
					throw new AssertionError();
				} else
				{
					pool.setBytesRef(pivot, bytesStart[ord]);
					return;
				}
			}

			protected int comparePivot(int j)
			{
				int ord = compact[j];
				if (!$assertionsDisabled && bytesStart.length <= ord)
					throw new AssertionError();
				else
					return comp.compare(pivot, pool.setBytesRef(scratch2, bytesStart[ord]));
			}


			
			{
				this$0 = BytesRefHash.this;
				compact = ai;
				comp = comparator;
				super();
			}
		}).quickSort(0, count - 1);
		return compact;
	}

	private boolean equals(int ord, BytesRef b)
	{
		return pool.setBytesRef(scratch1, bytesStart[ord]).bytesEquals(b);
	}

	private boolean shrink(int targetSize)
	{
		int newSize;
		for (newSize = hashSize; newSize >= 8 && newSize / 4 > targetSize; newSize /= 2);
		if (newSize != hashSize)
		{
			bytesUsed.addAndGet(4 * -(hashSize - newSize));
			hashSize = newSize;
			ords = new int[hashSize];
			Arrays.fill(ords, -1);
			hashHalfSize = newSize / 2;
			hashMask = newSize - 1;
			return true;
		} else
		{
			return false;
		}
	}

	public void clear(boolean resetPool)
	{
		lastCount = count;
		count = 0;
		if (resetPool)
			pool.dropBuffersAndReset();
		bytesStart = bytesStartArray.clear();
		if (lastCount != -1 && shrink(lastCount))
		{
			return;
		} else
		{
			Arrays.fill(ords, -1);
			return;
		}
	}

	public void clear()
	{
		clear(true);
	}

	public void close()
	{
		clear(true);
		ords = null;
		bytesUsed.addAndGet(4 * -hashSize);
	}

	public int add(BytesRef bytes)
	{
		return add(bytes, bytes.hashCode());
	}

	public int add(BytesRef bytes, int code)
	{
		if (!$assertionsDisabled && bytesStart == null)
			throw new AssertionError("Bytesstart is null - not initialized");
		int length = bytes.length;
		int hashPos = code & hashMask;
		int e = ords[hashPos];
		if (e != -1 && !equals(e, bytes))
		{
			int inc = (code >> 8) + code | 1;
			do
			{
				code += inc;
				hashPos = code & hashMask;
				e = ords[hashPos];
			} while (e != -1 && !equals(e, bytes));
		}
		if (e == -1)
		{
			int len2 = 2 + bytes.length;
			if (len2 + pool.byteUpto > 32768)
			{
				if (len2 > 32768)
					throw new MaxBytesLengthExceededException((new StringBuilder()).append("bytes can be at most 32766 in length; got ").append(bytes.length).toString());
				pool.nextBuffer();
			}
			byte buffer[] = pool.buffer;
			int bufferUpto = pool.byteUpto;
			if (count >= bytesStart.length)
			{
				bytesStart = bytesStartArray.grow();
				if (!$assertionsDisabled && count >= bytesStart.length + 1)
					throw new AssertionError((new StringBuilder()).append("count: ").append(count).append(" len: ").append(bytesStart.length).toString());
			}
			e = count++;
			bytesStart[e] = bufferUpto + pool.byteOffset;
			if (length < 128)
			{
				buffer[bufferUpto] = (byte)length;
				pool.byteUpto += length + 1;
				if (!$assertionsDisabled && length < 0)
					throw new AssertionError((new StringBuilder()).append("Length must be positive: ").append(length).toString());
				System.arraycopy(bytes.bytes, bytes.offset, buffer, bufferUpto + 1, length);
			} else
			{
				buffer[bufferUpto] = (byte)(0x80 | length & 0x7f);
				buffer[bufferUpto + 1] = (byte)(length >> 7 & 0xff);
				pool.byteUpto += length + 2;
				System.arraycopy(bytes.bytes, bytes.offset, buffer, bufferUpto + 2, length);
			}
			if (!$assertionsDisabled && ords[hashPos] != -1)
				throw new AssertionError();
			ords[hashPos] = e;
			if (count == hashHalfSize)
				rehash(2 * hashSize, true);
			return e;
		} else
		{
			return -(e + 1);
		}
	}

	public int addByPoolOffset(int offset)
	{
		if (!$assertionsDisabled && bytesStart == null)
			throw new AssertionError("Bytesstart is null - not initialized");
		int code = offset;
		int hashPos = offset & hashMask;
		int e = ords[hashPos];
		if (e != -1 && bytesStart[e] != offset)
		{
			int inc = (code >> 8) + code | 1;
			do
			{
				code += inc;
				hashPos = code & hashMask;
				e = ords[hashPos];
			} while (e != -1 && bytesStart[e] != offset);
		}
		if (e == -1)
		{
			if (count >= bytesStart.length)
			{
				bytesStart = bytesStartArray.grow();
				if (!$assertionsDisabled && count >= bytesStart.length + 1)
					throw new AssertionError((new StringBuilder()).append("count: ").append(count).append(" len: ").append(bytesStart.length).toString());
			}
			e = count++;
			bytesStart[e] = offset;
			if (!$assertionsDisabled && ords[hashPos] != -1)
				throw new AssertionError();
			ords[hashPos] = e;
			if (count == hashHalfSize)
				rehash(2 * hashSize, false);
			return e;
		} else
		{
			return -(e + 1);
		}
	}

	private void rehash(int newSize, boolean hashOnData)
	{
		int newMask = newSize - 1;
		bytesUsed.addAndGet(4 * newSize);
		int newHash[] = new int[newSize];
		Arrays.fill(newHash, -1);
		for (int i = 0; i < hashSize; i++)
		{
			int e0 = ords[i];
			if (e0 == -1)
				continue;
			int code;
			if (hashOnData)
			{
				int off = bytesStart[e0];
				int start = off & 0x7fff;
				byte bytes[] = pool.buffers[off >> 15];
				code = 0;
				int len;
				int pos;
				if ((bytes[start] & 0x80) == 0)
				{
					len = bytes[start];
					pos = start + 1;
				} else
				{
					len = (bytes[start] & 0x7f) + ((bytes[start + 1] & 0xff) << 7);
					pos = start + 2;
				}
				for (int endPos = pos + len; pos < endPos;)
					code = 31 * code + bytes[pos++];

			} else
			{
				code = bytesStart[e0];
			}
			int hashPos = code & newMask;
			if (!$assertionsDisabled && hashPos < 0)
				throw new AssertionError();
			if (newHash[hashPos] != -1)
			{
				int inc = (code >> 8) + code | 1;
				do
				{
					code += inc;
					hashPos = code & newMask;
				} while (newHash[hashPos] != -1);
			}
			newHash[hashPos] = e0;
		}

		hashMask = newMask;
		bytesUsed.addAndGet(4 * -ords.length);
		ords = newHash;
		hashSize = newSize;
		hashHalfSize = newSize / 2;
	}

	public void reinit()
	{
		if (bytesStart == null)
			bytesStart = bytesStartArray.init();
		if (ords == null)
		{
			ords = new int[hashSize];
			bytesUsed.addAndGet(4 * hashSize);
		}
	}

	public int byteStart(int ord)
	{
		if (!$assertionsDisabled && bytesStart == null)
			throw new AssertionError("Bytesstart is null - not initialized");
		if (!$assertionsDisabled && (ord < 0 || ord >= count))
			throw new AssertionError(ord);
		else
			return bytesStart[ord];
	}

}
