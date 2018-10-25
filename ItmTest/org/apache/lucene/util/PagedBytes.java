// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PagedBytes.java

package org.apache.lucene.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.store.*;

// Referenced classes of package org.apache.lucene.util:
//			BytesRef

public final class PagedBytes
{
	public final class PagedBytesDataOutput extends DataOutput
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/util/PagedBytes.desiredAssertionStatus();
		final PagedBytes this$0;

		public void writeByte(byte b)
		{
			if (upto == blockSize)
			{
				if (currentBlock != null)
				{
					blocks.add(currentBlock);
					blockEnd.add(Integer.valueOf(upto));
				}
				currentBlock = new byte[blockSize];
				upto = 0;
			}
			currentBlock[upto++] = b;
		}

		public void writeBytes(byte b[], int offset, int length)
		{
			if (!$assertionsDisabled && b.length < offset + length)
				throw new AssertionError();
			if (length == 0)
				return;
			if (upto == blockSize)
			{
				if (currentBlock != null)
				{
					blocks.add(currentBlock);
					blockEnd.add(Integer.valueOf(upto));
				}
				currentBlock = new byte[blockSize];
				upto = 0;
			}
			int offsetEnd = offset + length;
			do
			{
				int left = offsetEnd - offset;
				int blockLeft = blockSize - upto;
				if (blockLeft < left)
				{
					System.arraycopy(b, offset, currentBlock, upto, blockLeft);
					blocks.add(currentBlock);
					blockEnd.add(Integer.valueOf(blockSize));
					currentBlock = new byte[blockSize];
					upto = 0;
					offset += blockLeft;
				} else
				{
					System.arraycopy(b, offset, currentBlock, upto, left);
					upto+= = left;
					return;
				}
			} while (true);
		}

		public long getPosition()
		{
			if (currentBlock == null)
				return 0L;
			else
				return (long)(blocks.size() * blockSize + upto);
		}


		public PagedBytesDataOutput()
		{
			this$0 = PagedBytes.this;
			super();
		}
	}

	public final class PagedBytesDataInput extends DataInput
	{

		private int currentBlockIndex;
		private int currentBlockUpto;
		private byte currentBlock[];
		static final boolean $assertionsDisabled = !org/apache/lucene/util/PagedBytes.desiredAssertionStatus();
		final PagedBytes this$0;

		public PagedBytesDataInput clone()
		{
			PagedBytesDataInput clone = getDataInput();
			clone.setPosition(getPosition());
			return clone;
		}

		public long getPosition()
		{
			return (long)(currentBlockIndex * blockSize + currentBlockUpto);
		}

		public void setPosition(long pos)
		{
			currentBlockIndex = (int)(pos >> blockBits);
			currentBlock = (byte[])blocks.get(currentBlockIndex);
			currentBlockUpto = (int)(pos & (long)blockMask);
		}

		public byte readByte()
		{
			if (currentBlockUpto == blockSize)
				nextBlock();
			return currentBlock[currentBlockUpto++];
		}

		public void readBytes(byte b[], int offset, int len)
		{
			if (!$assertionsDisabled && b.length < offset + len)
				throw new AssertionError();
			int offsetEnd = offset + len;
			do
			{
				int blockLeft = blockSize - currentBlockUpto;
				int left = offsetEnd - offset;
				if (blockLeft < left)
				{
					System.arraycopy(currentBlock, currentBlockUpto, b, offset, blockLeft);
					nextBlock();
					offset += blockLeft;
				} else
				{
					System.arraycopy(currentBlock, currentBlockUpto, b, offset, left);
					currentBlockUpto += left;
					return;
				}
			} while (true);
		}

		private void nextBlock()
		{
			currentBlockIndex++;
			currentBlockUpto = 0;
			currentBlock = (byte[])blocks.get(currentBlockIndex);
		}

		public volatile DataInput clone()
		{
			return clone();
		}

		public volatile Object clone()
			throws CloneNotSupportedException
		{
			return clone();
		}


		PagedBytesDataInput()
		{
			this$0 = PagedBytes.this;
			super();
			currentBlock = (byte[])blocks.get(0);
		}
	}

	public static final class Reader
	{

		private final byte blocks[][];
		private final int blockEnds[];
		private final int blockBits;
		private final int blockMask;
		private final int blockSize;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/PagedBytes.desiredAssertionStatus();

		public BytesRef fillSlice(BytesRef b, long start, int length)
		{
			if (!$assertionsDisabled && length < 0)
				throw new AssertionError((new StringBuilder()).append("length=").append(length).toString());
			if (!$assertionsDisabled && length > blockSize + 1)
				throw new AssertionError();
			int index = (int)(start >> blockBits);
			int offset = (int)(start & (long)blockMask);
			b.length = length;
			if (blockSize - offset >= length)
			{
				b.bytes = blocks[index];
				b.offset = offset;
			} else
			{
				b.bytes = new byte[length];
				b.offset = 0;
				System.arraycopy(blocks[index], offset, b.bytes, 0, blockSize - offset);
				System.arraycopy(blocks[1 + index], 0, b.bytes, blockSize - offset, length - (blockSize - offset));
			}
			return b;
		}

		public BytesRef fill(BytesRef b, long start)
		{
			int index = (int)(start >> blockBits);
			int offset = (int)(start & (long)blockMask);
			byte block[] = b.bytes = blocks[index];
			if ((block[offset] & 0x80) == 0)
			{
				b.length = block[offset];
				b.offset = offset + 1;
			} else
			{
				b.length = (block[offset] & 0x7f) << 8 | block[1 + offset] & 0xff;
				b.offset = offset + 2;
				if (!$assertionsDisabled && b.length <= 0)
					throw new AssertionError();
			}
			return b;
		}

		public int fillAndGetIndex(BytesRef b, long start)
		{
			int index = (int)(start >> blockBits);
			int offset = (int)(start & (long)blockMask);
			byte block[] = b.bytes = blocks[index];
			if ((block[offset] & 0x80) == 0)
			{
				b.length = block[offset];
				b.offset = offset + 1;
			} else
			{
				b.length = (block[offset] & 0x7f) << 8 | block[1 + offset] & 0xff;
				b.offset = offset + 2;
				if (!$assertionsDisabled && b.length <= 0)
					throw new AssertionError();
			}
			return index;
		}

		public long fillAndGetStart(BytesRef b, long start)
		{
			int index = (int)(start >> blockBits);
			int offset = (int)(start & (long)blockMask);
			byte block[] = b.bytes = blocks[index];
			if ((block[offset] & 0x80) == 0)
			{
				b.length = block[offset];
				b.offset = offset + 1;
				start += 1L + (long)b.length;
			} else
			{
				b.length = (block[offset] & 0x7f) << 8 | block[1 + offset] & 0xff;
				b.offset = offset + 2;
				start += 2L + (long)b.length;
				if (!$assertionsDisabled && b.length <= 0)
					throw new AssertionError();
			}
			return start;
		}

		public BytesRef fillSliceWithPrefix(BytesRef b, long start)
		{
			int index = (int)(start >> blockBits);
			int offset = (int)(start & (long)blockMask);
			byte block[] = blocks[index];
			if (!$assertionsDisabled && offset > block.length - 1)
				throw new AssertionError();
			int length;
			if ((block[offset] & 0x80) == 0)
			{
				length = block[offset];
				offset++;
			} else
			if (offset == block.length - 1)
			{
				byte nextBlock[] = blocks[++index];
				length = (block[offset] & 0x7f) << 8 | nextBlock[0] & 0xff;
				offset = 1;
				block = nextBlock;
				if (!$assertionsDisabled && length <= 0)
					throw new AssertionError();
			} else
			{
				if (!$assertionsDisabled && offset >= block.length - 1)
					throw new AssertionError();
				length = (block[offset] & 0x7f) << 8 | block[1 + offset] & 0xff;
				offset += 2;
				if (!$assertionsDisabled && length <= 0)
					throw new AssertionError();
			}
			if (!$assertionsDisabled && length < 0)
				throw new AssertionError((new StringBuilder()).append("length=").append(length).toString());
			b.length = length;
			if (blockSize - offset >= length)
			{
				b.offset = offset;
				b.bytes = blocks[index];
			} else
			{
				b.bytes = new byte[length];
				b.offset = 0;
				System.arraycopy(blocks[index], offset, b.bytes, 0, blockSize - offset);
				System.arraycopy(blocks[1 + index], 0, b.bytes, blockSize - offset, length - (blockSize - offset));
			}
			return b;
		}

		public byte[][] getBlocks()
		{
			return blocks;
		}

		public int[] getBlockEnds()
		{
			return blockEnds;
		}


		public Reader(PagedBytes pagedBytes)
		{
			blocks = new byte[pagedBytes.blocks.size()][];
			for (int i = 0; i < blocks.length; i++)
				blocks[i] = (byte[])pagedBytes.blocks.get(i);

			blockEnds = new int[blocks.length];
			for (int i = 0; i < blockEnds.length; i++)
				blockEnds[i] = ((Integer)pagedBytes.blockEnd.get(i)).intValue();

			blockBits = pagedBytes.blockBits;
			blockMask = pagedBytes.blockMask;
			blockSize = pagedBytes.blockSize;
		}
	}


	private final List blocks = new ArrayList();
	private final List blockEnd = new ArrayList();
	private final int blockSize;
	private final int blockBits;
	private final int blockMask;
	private boolean didSkipBytes;
	private boolean frozen;
	private int upto;
	private byte currentBlock[];
	private static final byte EMPTY_BYTES[] = new byte[0];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/PagedBytes.desiredAssertionStatus();

	public PagedBytes(int blockBits)
	{
		blockSize = 1 << blockBits;
		this.blockBits = blockBits;
		blockMask = blockSize - 1;
		upto = blockSize;
	}

	public void copy(IndexInput in, long byteCount)
		throws IOException
	{
label0:
		{
			do
			{
				if (byteCount <= 0L)
					break label0;
				int left = blockSize - upto;
				if (left == 0)
				{
					if (currentBlock != null)
					{
						blocks.add(currentBlock);
						blockEnd.add(Integer.valueOf(upto));
					}
					currentBlock = new byte[blockSize];
					upto = 0;
					left = blockSize;
				}
				if ((long)left >= byteCount)
					break;
				in.readBytes(currentBlock, upto, left, false);
				upto = blockSize;
				byteCount -= left;
			} while (true);
			in.readBytes(currentBlock, upto, (int)byteCount, false);
			upto += byteCount;
		}
	}

	public void copy(BytesRef bytes)
	{
label0:
		{
			int byteCount = bytes.length;
			int bytesUpto = bytes.offset;
			do
			{
				if (byteCount <= 0)
					break label0;
				int left = blockSize - upto;
				if (left == 0)
				{
					if (currentBlock != null)
					{
						blocks.add(currentBlock);
						blockEnd.add(Integer.valueOf(upto));
					}
					currentBlock = new byte[blockSize];
					upto = 0;
					left = blockSize;
				}
				if (left >= byteCount)
					break;
				System.arraycopy(bytes.bytes, bytesUpto, currentBlock, upto, left);
				upto = blockSize;
				byteCount -= left;
				bytesUpto += left;
			} while (true);
			System.arraycopy(bytes.bytes, bytesUpto, currentBlock, upto, byteCount);
			upto += byteCount;
		}
	}

	public void copy(BytesRef bytes, BytesRef out)
	{
		int left = blockSize - upto;
		if (bytes.length > left || currentBlock == null)
		{
			if (currentBlock != null)
			{
				blocks.add(currentBlock);
				blockEnd.add(Integer.valueOf(upto));
				didSkipBytes = true;
			}
			currentBlock = new byte[blockSize];
			upto = 0;
			left = blockSize;
			if (!$assertionsDisabled && bytes.length > blockSize)
				throw new AssertionError();
		}
		out.bytes = currentBlock;
		out.offset = upto;
		out.length = bytes.length;
		System.arraycopy(bytes.bytes, bytes.offset, currentBlock, upto, bytes.length);
		upto += bytes.length;
	}

	public Reader freeze(boolean trim)
	{
		if (frozen)
			throw new IllegalStateException("already frozen");
		if (didSkipBytes)
			throw new IllegalStateException("cannot freeze when copy(BytesRef, BytesRef) was used");
		if (trim && upto < blockSize)
		{
			byte newBlock[] = new byte[upto];
			System.arraycopy(currentBlock, 0, newBlock, 0, upto);
			currentBlock = newBlock;
		}
		if (currentBlock == null)
			currentBlock = EMPTY_BYTES;
		blocks.add(currentBlock);
		blockEnd.add(Integer.valueOf(upto));
		frozen = true;
		currentBlock = null;
		return new Reader(this);
	}

	public long getPointer()
	{
		if (currentBlock == null)
			return 0L;
		else
			return (long)blocks.size() * (long)blockSize + (long)upto;
	}

	public long copyUsingLengthPrefix(BytesRef bytes)
	{
		if (bytes.length >= 32768)
			throw new IllegalArgumentException((new StringBuilder()).append("max length is 32767 (got ").append(bytes.length).append(")").toString());
		if (upto + bytes.length + 2 > blockSize)
		{
			if (bytes.length + 2 > blockSize)
				throw new IllegalArgumentException((new StringBuilder()).append("block size ").append(blockSize).append(" is too small to store length ").append(bytes.length).append(" bytes").toString());
			if (currentBlock != null)
			{
				blocks.add(currentBlock);
				blockEnd.add(Integer.valueOf(upto));
			}
			currentBlock = new byte[blockSize];
			upto = 0;
		}
		long pointer = getPointer();
		if (bytes.length < 128)
		{
			currentBlock[upto++] = (byte)bytes.length;
		} else
		{
			currentBlock[upto++] = (byte)(0x80 | bytes.length >> 8);
			currentBlock[upto++] = (byte)(bytes.length & 0xff);
		}
		System.arraycopy(bytes.bytes, bytes.offset, currentBlock, upto, bytes.length);
		upto += bytes.length;
		return pointer;
	}

	public PagedBytesDataInput getDataInput()
	{
		if (!frozen)
			throw new IllegalStateException("must call freeze() before getDataInput");
		else
			return new PagedBytesDataInput();
	}

	public PagedBytesDataOutput getDataOutput()
	{
		if (frozen)
			throw new IllegalStateException("cannot get DataOutput after freeze()");
		else
			return new PagedBytesDataOutput();
	}












}
