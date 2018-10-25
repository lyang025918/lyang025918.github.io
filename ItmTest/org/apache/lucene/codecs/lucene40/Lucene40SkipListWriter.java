// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40SkipListWriter.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.codecs.MultiLevelSkipListWriter;
import org.apache.lucene.store.IndexOutput;

public class Lucene40SkipListWriter extends MultiLevelSkipListWriter
{

	private int lastSkipDoc[];
	private int lastSkipPayloadLength[];
	private int lastSkipOffsetLength[];
	private long lastSkipFreqPointer[];
	private long lastSkipProxPointer[];
	private IndexOutput freqOutput;
	private IndexOutput proxOutput;
	private int curDoc;
	private boolean curStorePayloads;
	private boolean curStoreOffsets;
	private int curPayloadLength;
	private int curOffsetLength;
	private long curFreqPointer;
	private long curProxPointer;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40SkipListWriter.desiredAssertionStatus();

	public Lucene40SkipListWriter(int skipInterval, int numberOfSkipLevels, int docCount, IndexOutput freqOutput, IndexOutput proxOutput)
	{
		super(skipInterval, numberOfSkipLevels, docCount);
		this.freqOutput = freqOutput;
		this.proxOutput = proxOutput;
		lastSkipDoc = new int[numberOfSkipLevels];
		lastSkipPayloadLength = new int[numberOfSkipLevels];
		lastSkipOffsetLength = new int[numberOfSkipLevels];
		lastSkipFreqPointer = new long[numberOfSkipLevels];
		lastSkipProxPointer = new long[numberOfSkipLevels];
	}

	public void setSkipData(int doc, boolean storePayloads, int payloadLength, boolean storeOffsets, int offsetLength)
	{
		if (!$assertionsDisabled && !storePayloads && payloadLength != -1)
			throw new AssertionError();
		if (!$assertionsDisabled && !storeOffsets && offsetLength != -1)
			throw new AssertionError();
		curDoc = doc;
		curStorePayloads = storePayloads;
		curPayloadLength = payloadLength;
		curStoreOffsets = storeOffsets;
		curOffsetLength = offsetLength;
		curFreqPointer = freqOutput.getFilePointer();
		if (proxOutput != null)
			curProxPointer = proxOutput.getFilePointer();
	}

	public void resetSkip()
	{
		super.resetSkip();
		Arrays.fill(lastSkipDoc, 0);
		Arrays.fill(lastSkipPayloadLength, -1);
		Arrays.fill(lastSkipOffsetLength, -1);
		Arrays.fill(lastSkipFreqPointer, freqOutput.getFilePointer());
		if (proxOutput != null)
			Arrays.fill(lastSkipProxPointer, proxOutput.getFilePointer());
	}

	protected void writeSkipData(int level, IndexOutput skipBuffer)
		throws IOException
	{
		int delta = curDoc - lastSkipDoc[level];
		if (curStorePayloads || curStoreOffsets)
		{
			if (!$assertionsDisabled && !curStorePayloads && curPayloadLength != lastSkipPayloadLength[level])
				throw new AssertionError();
			if (!$assertionsDisabled && !curStoreOffsets && curOffsetLength != lastSkipOffsetLength[level])
				throw new AssertionError();
			if (curPayloadLength == lastSkipPayloadLength[level] && curOffsetLength == lastSkipOffsetLength[level])
			{
				skipBuffer.writeVInt(delta << 1);
			} else
			{
				skipBuffer.writeVInt(delta << 1 | 1);
				if (curStorePayloads)
				{
					skipBuffer.writeVInt(curPayloadLength);
					lastSkipPayloadLength[level] = curPayloadLength;
				}
				if (curStoreOffsets)
				{
					skipBuffer.writeVInt(curOffsetLength);
					lastSkipOffsetLength[level] = curOffsetLength;
				}
			}
		} else
		{
			skipBuffer.writeVInt(delta);
		}
		skipBuffer.writeVInt((int)(curFreqPointer - lastSkipFreqPointer[level]));
		skipBuffer.writeVInt((int)(curProxPointer - lastSkipProxPointer[level]));
		lastSkipDoc[level] = curDoc;
		lastSkipFreqPointer[level] = curFreqPointer;
		lastSkipProxPointer[level] = curProxPointer;
	}

}
