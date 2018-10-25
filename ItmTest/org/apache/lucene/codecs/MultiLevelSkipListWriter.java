// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiLevelSkipListWriter.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.store.RAMOutputStream;
import org.apache.lucene.util.MathUtil;

public abstract class MultiLevelSkipListWriter
{

	protected int numberOfSkipLevels;
	private int skipInterval;
	private int skipMultiplier;
	private RAMOutputStream skipBuffer[];
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/MultiLevelSkipListWriter.desiredAssertionStatus();

	protected MultiLevelSkipListWriter(int skipInterval, int skipMultiplier, int maxSkipLevels, int df)
	{
		this.skipInterval = skipInterval;
		this.skipMultiplier = skipMultiplier;
		if (df <= skipInterval)
			numberOfSkipLevels = 1;
		else
			numberOfSkipLevels = 1 + MathUtil.log(df / skipInterval, skipMultiplier);
		if (numberOfSkipLevels > maxSkipLevels)
			numberOfSkipLevels = maxSkipLevels;
	}

	protected MultiLevelSkipListWriter(int skipInterval, int maxSkipLevels, int df)
	{
		this(skipInterval, skipInterval, maxSkipLevels, df);
	}

	protected void init()
	{
		skipBuffer = new RAMOutputStream[numberOfSkipLevels];
		for (int i = 0; i < numberOfSkipLevels; i++)
			skipBuffer[i] = new RAMOutputStream();

	}

	protected void resetSkip()
	{
		if (skipBuffer == null)
		{
			init();
		} else
		{
			for (int i = 0; i < skipBuffer.length; i++)
				skipBuffer[i].reset();

		}
	}

	protected abstract void writeSkipData(int i, IndexOutput indexoutput)
		throws IOException;

	public void bufferSkip(int df)
		throws IOException
	{
		if (!$assertionsDisabled && df % skipInterval != 0)
			throw new AssertionError();
		int numLevels = 1;
		for (df /= skipInterval; df % skipMultiplier == 0 && numLevels < numberOfSkipLevels; df /= skipMultiplier)
			numLevels++;

		long childPointer = 0L;
		for (int level = 0; level < numLevels; level++)
		{
			writeSkipData(level, skipBuffer[level]);
			long newChildPointer = skipBuffer[level].getFilePointer();
			if (level != 0)
				skipBuffer[level].writeVLong(childPointer);
			childPointer = newChildPointer;
		}

	}

	public long writeSkip(IndexOutput output)
		throws IOException
	{
		long skipPointer = output.getFilePointer();
		if (skipBuffer == null || skipBuffer.length == 0)
			return skipPointer;
		for (int level = numberOfSkipLevels - 1; level > 0; level--)
		{
			long length = skipBuffer[level].getFilePointer();
			if (length > 0L)
			{
				output.writeVLong(length);
				skipBuffer[level].writeTo(output);
			}
		}

		skipBuffer[0].writeTo(output);
		return skipPointer;
	}

}
