// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteBlockPool.java

package org.apache.lucene.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util:
//			BytesRef, RamUsageEstimator, ArrayUtil, Counter

public final class ByteBlockPool
{
	public static class DirectTrackingAllocator extends Allocator
	{

		private final Counter bytesUsed;

		public byte[] getByteBlock()
		{
			bytesUsed.addAndGet(blockSize);
			return new byte[blockSize];
		}

		public void recycleByteBlocks(byte blocks[][], int start, int end)
		{
			bytesUsed.addAndGet(-((end - start) * blockSize));
			for (int i = start; i < end; i++)
				blocks[i] = null;

		}

		public DirectTrackingAllocator(Counter bytesUsed)
		{
			this(32768, bytesUsed);
		}

		public DirectTrackingAllocator(int blockSize, Counter bytesUsed)
		{
			super(blockSize);
			this.bytesUsed = bytesUsed;
		}
	}

	public static final class DirectAllocator extends Allocator
	{

		public void recycleByteBlocks(byte abyte0[][], int i, int j)
		{
		}

		public DirectAllocator()
		{
			this(32768);
		}

		public DirectAllocator(int blockSize)
		{
			super(blockSize);
		}
	}

	public static abstract class Allocator
	{

		protected final int blockSize;

		public abstract void recycleByteBlocks(byte abyte0[][], int i, int j);

		public void recycleByteBlocks(List blocks)
		{
			byte b[][] = (byte[][])blocks.toArray(new byte[blocks.size()][]);
			recycleByteBlocks(b, 0, b.length);
		}

		public byte[] getByteBlock()
		{
			return new byte[blockSize];
		}

		public Allocator(int blockSize)
		{
			this.blockSize = blockSize;
		}
	}


	public static final int BYTE_BLOCK_SHIFT = 15;
	public static final int BYTE_BLOCK_SIZE = 32768;
	public static final int BYTE_BLOCK_MASK = 32767;
	public byte buffers[][];
	int bufferUpto;
	public int byteUpto;
	public byte buffer[];
	public int byteOffset;
	private final Allocator allocator;
	public static final int nextLevelArray[] = {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 9
	};
	public static final int levelSizeArray[] = {
		5, 14, 20, 30, 40, 40, 80, 80, 120, 200
	};
	public static final int FIRST_LEVEL_SIZE = levelSizeArray[0];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/ByteBlockPool.desiredAssertionStatus();

	public ByteBlockPool(Allocator allocator)
	{
		buffers = new byte[10][];
		bufferUpto = -1;
		byteUpto = 32768;
		byteOffset = -32768;
		this.allocator = allocator;
	}

	public void dropBuffersAndReset()
	{
		if (bufferUpto != -1)
		{
			allocator.recycleByteBlocks(buffers, 0, 1 + bufferUpto);
			bufferUpto = -1;
			byteUpto = 32768;
			byteOffset = -32768;
			buffers = new byte[10][];
			buffer = null;
		}
	}

	public void reset()
	{
		if (bufferUpto != -1)
		{
			for (int i = 0; i < bufferUpto; i++)
				Arrays.fill(buffers[i], (byte)0);

			Arrays.fill(buffers[bufferUpto], 0, byteUpto, (byte)0);
			if (bufferUpto > 0)
				allocator.recycleByteBlocks(buffers, 1, 1 + bufferUpto);
			bufferUpto = 0;
			byteUpto = 0;
			byteOffset = 0;
			buffer = buffers[0];
		}
	}

	public void nextBuffer()
	{
		if (1 + bufferUpto == buffers.length)
		{
			byte newBuffers[][] = new byte[ArrayUtil.oversize(buffers.length + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF)][];
			System.arraycopy(buffers, 0, newBuffers, 0, buffers.length);
			buffers = newBuffers;
		}
		buffer = buffers[1 + bufferUpto] = allocator.getByteBlock();
		bufferUpto++;
		byteUpto = 0;
		byteOffset += 32768;
	}

	public int newSlice(int size)
	{
		if (byteUpto > 32768 - size)
			nextBuffer();
		int upto = byteUpto;
		byteUpto += size;
		buffer[byteUpto - 1] = 16;
		return upto;
	}

	public int allocSlice(byte slice[], int upto)
	{
		int level = slice[upto] & 0xf;
		int newLevel = nextLevelArray[level];
		int newSize = levelSizeArray[newLevel];
		if (byteUpto > 32768 - newSize)
			nextBuffer();
		int newUpto = byteUpto;
		int offset = newUpto + byteOffset;
		byteUpto += newSize;
		buffer[newUpto] = slice[upto - 3];
		buffer[newUpto + 1] = slice[upto - 2];
		buffer[newUpto + 2] = slice[upto - 1];
		slice[upto - 3] = (byte)(offset >>> 24);
		slice[upto - 2] = (byte)(offset >>> 16);
		slice[upto - 1] = (byte)(offset >>> 8);
		slice[upto] = (byte)offset;
		buffer[byteUpto - 1] = (byte)(0x10 | newLevel);
		return newUpto + 3;
	}

	public final BytesRef setBytesRef(BytesRef term, int textStart)
	{
		byte bytes[] = term.bytes = buffers[textStart >> 15];
		int pos = textStart & 0x7fff;
		if ((bytes[pos] & 0x80) == 0)
		{
			term.length = bytes[pos];
			term.offset = pos + 1;
		} else
		{
			term.length = (bytes[pos] & 0x7f) + ((bytes[pos + 1] & 0xff) << 7);
			term.offset = pos + 2;
		}
		if (!$assertionsDisabled && term.length < 0)
			throw new AssertionError();
		else
			return term;
	}

	public final BytesRef deref(BytesRef bytes)
	{
		int offset = bytes.offset;
		byte buffer[] = buffers[offset >> 15];
		int pos = offset & 0x7fff;
		bytes.bytes = buffer;
		bytes.offset = pos;
		return bytes;
	}

	public final void copy(BytesRef bytes)
	{
		int length = bytes.length;
		int offset = bytes.offset;
		int overflow = (length + byteUpto) - 32768;
		do
		{
			if (overflow <= 0)
			{
				System.arraycopy(bytes.bytes, offset, buffer, byteUpto, length);
				byteUpto += length;
				break;
			}
			int bytesToCopy = length - overflow;
			System.arraycopy(bytes.bytes, offset, buffer, byteUpto, bytesToCopy);
			offset += bytesToCopy;
			length -= bytesToCopy;
			nextBuffer();
			overflow -= 32768;
		} while (true);
	}

	public final BytesRef copyFrom(BytesRef bytes)
	{
		int length = bytes.length;
		int offset = bytes.offset;
		bytes.offset = 0;
		bytes.grow(length);
		int bufferIndex = offset >> 15;
		byte buffer[] = buffers[bufferIndex];
		int pos = offset & 0x7fff;
		int overflow = (pos + length) - 32768;
		do
		{
			if (overflow <= 0)
			{
				System.arraycopy(buffer, pos, bytes.bytes, bytes.offset, bytes.length);
				bytes.length = length;
				bytes.offset = 0;
				break;
			}
			int bytesToCopy = length - overflow;
			System.arraycopy(buffer, pos, bytes.bytes, bytes.offset, bytesToCopy);
			pos = 0;
			bytes.length -= bytesToCopy;
			bytes.offset += bytesToCopy;
			buffer = buffers[++bufferIndex];
			overflow -= 32768;
		} while (true);
		return bytes;
	}

	public final void writePool(DataOutput out)
		throws IOException
	{
		int bytesOffset = byteOffset;
		int block = 0;
		for (; bytesOffset > 0; bytesOffset -= 32768)
			out.writeBytes(buffers[block++], 32768);

		out.writeBytes(buffers[block], byteUpto);
	}

}
