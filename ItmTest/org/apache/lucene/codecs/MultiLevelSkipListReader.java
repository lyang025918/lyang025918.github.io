// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiLevelSkipListReader.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.BufferedIndexInput;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.MathUtil;

public abstract class MultiLevelSkipListReader
	implements Closeable
{
	private static final class SkipBuffer extends IndexInput
	{

		private byte data[];
		private long pointer;
		private int pos;

		public void close()
		{
			data = null;
		}

		public long getFilePointer()
		{
			return pointer + (long)pos;
		}

		public long length()
		{
			return (long)data.length;
		}

		public byte readByte()
		{
			return data[pos++];
		}

		public void readBytes(byte b[], int offset, int len)
		{
			System.arraycopy(data, pos, b, offset, len);
			pos += len;
		}

		public void seek(long pos)
		{
			this.pos = (int)(pos - pointer);
		}

		SkipBuffer(IndexInput input, int length)
			throws IOException
		{
			super((new StringBuilder()).append("SkipBuffer on ").append(input).toString());
			data = new byte[length];
			pointer = input.getFilePointer();
			input.readBytes(data, 0, length);
		}
	}


	protected int maxNumberOfSkipLevels;
	private int numberOfSkipLevels;
	private int numberOfLevelsToBuffer;
	private int docCount;
	private boolean haveSkipped;
	private IndexInput skipStream[];
	private long skipPointer[];
	private int skipInterval[];
	private int numSkipped[];
	protected int skipDoc[];
	private int lastDoc;
	private long childPointer[];
	private long lastChildPointer;
	private boolean inputIsBuffered;
	private final int skipMultiplier;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/MultiLevelSkipListReader.desiredAssertionStatus();

	protected MultiLevelSkipListReader(IndexInput skipStream, int maxSkipLevels, int skipInterval, int skipMultiplier)
	{
		numberOfLevelsToBuffer = 1;
		this.skipStream = new IndexInput[maxSkipLevels];
		skipPointer = new long[maxSkipLevels];
		childPointer = new long[maxSkipLevels];
		numSkipped = new int[maxSkipLevels];
		maxNumberOfSkipLevels = maxSkipLevels;
		this.skipInterval = new int[maxSkipLevels];
		this.skipMultiplier = skipMultiplier;
		this.skipStream[0] = skipStream;
		inputIsBuffered = skipStream instanceof BufferedIndexInput;
		this.skipInterval[0] = skipInterval;
		for (int i = 1; i < maxSkipLevels; i++)
			this.skipInterval[i] = this.skipInterval[i - 1] * skipMultiplier;

		skipDoc = new int[maxSkipLevels];
	}

	protected MultiLevelSkipListReader(IndexInput skipStream, int maxSkipLevels, int skipInterval)
	{
		this(skipStream, maxSkipLevels, skipInterval, skipInterval);
	}

	public int getDoc()
	{
		return lastDoc;
	}

	public int skipTo(int target)
		throws IOException
	{
		if (!haveSkipped)
		{
			loadSkipLevels();
			haveSkipped = true;
		}
		int level;
		for (level = 0; level < numberOfSkipLevels - 1 && target > skipDoc[level + 1]; level++);
		while (level >= 0) 
			if (target > skipDoc[level])
			{
				if (loadNextSkip(level));
			} else
			{
				if (level > 0 && lastChildPointer > skipStream[level - 1].getFilePointer())
					seekChild(level - 1);
				level--;
			}
		return numSkipped[0] - skipInterval[0] - 1;
	}

	private boolean loadNextSkip(int level)
		throws IOException
	{
		setLastSkipData(level);
		numSkipped[level] += skipInterval[level];
		if (numSkipped[level] > docCount)
		{
			skipDoc[level] = 0x7fffffff;
			if (numberOfSkipLevels > level)
				numberOfSkipLevels = level;
			return false;
		}
		skipDoc[level] += readSkipData(level, skipStream[level]);
		if (level != 0)
			childPointer[level] = skipStream[level].readVLong() + skipPointer[level - 1];
		return true;
	}

	protected void seekChild(int level)
		throws IOException
	{
		skipStream[level].seek(lastChildPointer);
		numSkipped[level] = numSkipped[level + 1] - skipInterval[level + 1];
		skipDoc[level] = lastDoc;
		if (level > 0)
			childPointer[level] = skipStream[level].readVLong() + skipPointer[level - 1];
	}

	public void close()
		throws IOException
	{
		for (int i = 1; i < skipStream.length; i++)
			if (skipStream[i] != null)
				skipStream[i].close();

	}

	public void init(long skipPointer, int df)
	{
		this.skipPointer[0] = skipPointer;
		docCount = df;
		if (!$assertionsDisabled && (skipPointer < 0L || skipPointer > skipStream[0].length()))
			throw new AssertionError((new StringBuilder()).append("invalid skip pointer: ").append(skipPointer).append(", length=").append(skipStream[0].length()).toString());
		Arrays.fill(skipDoc, 0);
		Arrays.fill(numSkipped, 0);
		Arrays.fill(childPointer, 0L);
		haveSkipped = false;
		for (int i = 1; i < numberOfSkipLevels; i++)
			skipStream[i] = null;

	}

	private void loadSkipLevels()
		throws IOException
	{
		if (docCount <= skipInterval[0])
			numberOfSkipLevels = 1;
		else
			numberOfSkipLevels = 1 + MathUtil.log(docCount / skipInterval[0], skipMultiplier);
		if (numberOfSkipLevels > maxNumberOfSkipLevels)
			numberOfSkipLevels = maxNumberOfSkipLevels;
		skipStream[0].seek(skipPointer[0]);
		int toBuffer = numberOfLevelsToBuffer;
		for (int i = numberOfSkipLevels - 1; i > 0; i--)
		{
			long length = skipStream[0].readVLong();
			skipPointer[i] = skipStream[0].getFilePointer();
			if (toBuffer > 0)
			{
				skipStream[i] = new SkipBuffer(skipStream[0], (int)length);
				toBuffer--;
				continue;
			}
			skipStream[i] = skipStream[0].clone();
			if (inputIsBuffered && length < 1024L)
				((BufferedIndexInput)skipStream[i]).setBufferSize((int)length);
			skipStream[0].seek(skipStream[0].getFilePointer() + length);
		}

		skipPointer[0] = skipStream[0].getFilePointer();
	}

	protected abstract int readSkipData(int i, IndexInput indexinput)
		throws IOException;

	protected void setLastSkipData(int level)
	{
		lastDoc = skipDoc[level];
		lastChildPointer = childPointer[level];
	}

}
