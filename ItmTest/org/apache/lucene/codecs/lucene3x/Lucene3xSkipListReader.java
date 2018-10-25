// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xSkipListReader.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.codecs.MultiLevelSkipListReader;
import org.apache.lucene.store.IndexInput;

/**
 * @deprecated Class Lucene3xSkipListReader is deprecated
 */

final class Lucene3xSkipListReader extends MultiLevelSkipListReader
{

	private boolean currentFieldStoresPayloads;
	private long freqPointer[];
	private long proxPointer[];
	private int payloadLength[];
	private long lastFreqPointer;
	private long lastProxPointer;
	private int lastPayloadLength;

	public Lucene3xSkipListReader(IndexInput skipStream, int maxSkipLevels, int skipInterval)
	{
		super(skipStream, maxSkipLevels, skipInterval);
		freqPointer = new long[maxSkipLevels];
		proxPointer = new long[maxSkipLevels];
		payloadLength = new int[maxSkipLevels];
	}

	public void init(long skipPointer, long freqBasePointer, long proxBasePointer, int df, 
			boolean storesPayloads)
	{
		super.init(skipPointer, df);
		currentFieldStoresPayloads = storesPayloads;
		lastFreqPointer = freqBasePointer;
		lastProxPointer = proxBasePointer;
		Arrays.fill(freqPointer, freqBasePointer);
		Arrays.fill(proxPointer, proxBasePointer);
		Arrays.fill(payloadLength, 0);
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

	protected void seekChild(int level)
		throws IOException
	{
		super.seekChild(level);
		freqPointer[level] = lastFreqPointer;
		proxPointer[level] = lastProxPointer;
		payloadLength[level] = lastPayloadLength;
	}

	protected void setLastSkipData(int level)
	{
		super.setLastSkipData(level);
		lastFreqPointer = freqPointer[level];
		lastProxPointer = proxPointer[level];
		lastPayloadLength = payloadLength[level];
	}

	protected int readSkipData(int level, IndexInput skipStream)
		throws IOException
	{
		int delta;
		if (currentFieldStoresPayloads)
		{
			delta = skipStream.readVInt();
			if ((delta & 1) != 0)
				payloadLength[level] = skipStream.readVInt();
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
