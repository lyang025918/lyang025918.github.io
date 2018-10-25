// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntBlockPool.java

package org.apache.lucene.index;

import java.util.Arrays;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriterPerThread

final class IntBlockPool
{

	public int buffers[][];
	int bufferUpto;
	public int intUpto;
	public int buffer[];
	public int intOffset;
	private final DocumentsWriterPerThread docWriter;

	public IntBlockPool(DocumentsWriterPerThread docWriter)
	{
		buffers = new int[10][];
		bufferUpto = -1;
		intUpto = 8192;
		intOffset = -8192;
		this.docWriter = docWriter;
	}

	public void reset()
	{
		if (bufferUpto != -1)
		{
			if (bufferUpto > 0)
			{
				docWriter.recycleIntBlocks(buffers, 1, bufferUpto - 1);
				Arrays.fill(buffers, 1, bufferUpto, null);
			}
			bufferUpto = 0;
			intUpto = 0;
			intOffset = 0;
			buffer = buffers[0];
		}
	}

	public void nextBuffer()
	{
		if (1 + bufferUpto == buffers.length)
		{
			int newBuffers[][] = new int[(int)((double)buffers.length * 1.5D)][];
			System.arraycopy(buffers, 0, newBuffers, 0, buffers.length);
			buffers = newBuffers;
		}
		buffer = buffers[1 + bufferUpto] = docWriter.getIntBlock();
		bufferUpto++;
		intUpto = 0;
		intOffset += 8192;
	}
}
