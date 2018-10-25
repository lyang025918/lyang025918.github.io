// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40SkipListReader.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.codecs.MultiLevelSkipListReader;
import org.apache.lucene.store.IndexInput;

public class Lucene40SkipListReader extends MultiLevelSkipListReader
{

	private boolean currentFieldStoresPayloads;
	private boolean currentFieldStoresOffsets;
	private long freqPointer[];
	private long proxPointer[];
	private int payloadLength[];
	private int offsetLength[];
	private long lastFreqPointer;
	private long lastProxPointer;
	private int lastPayloadLength;
	private int lastOffsetLength;

	public Lucene40SkipListReader(IndexInput skipStream, int maxSkipLevels, int skipInterval)
	{
		super(skipStream, maxSkipLevels, skipInterval);
		freqPointer = new long[maxSkipLevels];
		proxPointer = new long[maxSkipLevels];
		payloadLength = new int[maxSkipLevels];
		offsetLength = new int[maxSkipLevels];
	}

	public void init(long skipPointer, long freqBasePointer, long proxBasePointer, int df, 
			boolean storesPayloads, boolean storesOffsets)
	{
		super.init(skipPointer, df);
		currentFieldStoresPayloads = storesPayloads;
		currentFieldStoresOffsets = storesOffsets;
		lastFreqPointer = freqBasePointer;
		lastProxPointer = proxBasePointer;
		Arrays.fill(freqPointer, freqBasePointer);
		Arrays.fill(proxPointer, proxBasePointer);
		Arrays.fill(payloadLength, 0);
		Arrays.fill(offsetLength, 0);
	}

	public long getFreqPointer()
	{
		return lastFreqPointer;
	}

	public long getProxPointer()
	{
		return lastProxPointer;
	}

	public int getPayloadLength()
	{
		return lastPayloadLength;
	}

	public int getOffsetLength()
	{
		return lastOffsetLength;
	}

	protected void seekChild(int level)
		throws IOException
	{
		super.seekChild(level);
		freqPointer[level] = lastFreqPointer;
		proxPointer[level] = lastProxPointer;
		payloadLength[level] = lastPayloadLength;
		offsetLength[level] = lastOffsetLength;
	}

	protected void setLastSkipData(int level)
	{
		super.setLastSkipData(level);
		lastFreqPointer = freqPointer[level];
		lastProxPointer = proxPointer[level];
		lastPayloadLength = payloadLength[level];
		lastOffsetLength = offsetLength[level];
	}

	protected int readSkipData(int level, IndexInput skipStream)
		throws IOException
	{
		int delta;
		if (currentFieldStoresPayloads || currentFieldStoresOffsets)
		{
			delta = skipStream.readVInt();
			if ((delta & 1) != 0)
			{
				if (currentFieldStoresPayloads)
					payloadLength[level] = skipStream.readVInt();
				if (currentFieldStoresOffsets)
					offsetLength[level] = skipStream.readVInt();
			}
			delta >>>= 1;
		} else
		{
			delta = skipStream.readVInt();
		}
		freqPointer[level] += skipStream.readVInt();
		proxPointer[level] += skipStream.readVInt();
		return delta;
	}
}
