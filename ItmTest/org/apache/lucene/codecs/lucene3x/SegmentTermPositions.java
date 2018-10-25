// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentTermPositions.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			SegmentTermDocs, TermInfo, TermInfosReader

/**
 * @deprecated Class SegmentTermPositions is deprecated
 */

final class SegmentTermPositions extends SegmentTermDocs
{

	private IndexInput proxStream;
	private IndexInput proxStreamOrig;
	private int proxCount;
	private int position;
	private BytesRef payload;
	private int payloadLength;
	private boolean needToLoadPayload;
	private long lazySkipPointer;
	private int lazySkipProxCount;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/SegmentTermPositions.desiredAssertionStatus();

	public SegmentTermPositions(IndexInput freqStream, IndexInput proxStream, TermInfosReader tis, FieldInfos fieldInfos)
	{
		super(freqStream, tis, fieldInfos);
		lazySkipPointer = -1L;
		lazySkipProxCount = 0;
		proxStreamOrig = proxStream;
	}

	final void seek(TermInfo ti, Term term)
		throws IOException
	{
		super.seek(ti, term);
		if (ti != null)
			lazySkipPointer = ti.proxPointer;
		lazySkipProxCount = 0;
		proxCount = 0;
		payloadLength = 0;
		needToLoadPayload = false;
	}

	public final void close()
		throws IOException
	{
		super.close();
		if (proxStream != null)
			proxStream.close();
	}

	public final int nextPosition()
		throws IOException
	{
		if (indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
		{
			return 0;
		} else
		{
			lazySkip();
			proxCount--;
			return position += readDeltaPosition();
		}
	}

	private final int readDeltaPosition()
		throws IOException
	{
		int delta = proxStream.readVInt();
		if (currentFieldStoresPayloads)
		{
			if ((delta & 1) != 0)
				payloadLength = proxStream.readVInt();
			delta >>>= 1;
			needToLoadPayload = true;
		} else
		if (delta == -1)
			delta = 0;
		return delta;
	}

	protected final void skippingDoc()
		throws IOException
	{
		lazySkipProxCount += freq;
	}

	public final boolean next()
		throws IOException
	{
		lazySkipProxCount += proxCount;
		if (super.next())
		{
			proxCount = freq;
			position = 0;
			return true;
		} else
		{
			return false;
		}
	}

	public final int read(int docs[], int freqs[])
	{
		throw new UnsupportedOperationException("TermPositions does not support processing multiple documents in one call. Use TermDocs instead.");
	}

	protected void skipProx(long proxPointer, int payloadLength)
		throws IOException
	{
		lazySkipPointer = proxPointer;
		lazySkipProxCount = 0;
		proxCount = 0;
		this.payloadLength = payloadLength;
		needToLoadPayload = false;
	}

	private void skipPositions(int n)
		throws IOException
	{
		if (!$assertionsDisabled && indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
			throw new AssertionError();
		for (int f = n; f > 0; f--)
		{
			readDeltaPosition();
			skipPayload();
		}

	}

	private void skipPayload()
		throws IOException
	{
		if (needToLoadPayload && payloadLength > 0)
			proxStream.seek(proxStream.getFilePointer() + (long)payloadLength);
		needToLoadPayload = false;
	}

	private void lazySkip()
		throws IOException
	{
		if (proxStream == null)
			proxStream = proxStreamOrig.clone();
		skipPayload();
		if (lazySkipPointer != -1L)
		{
			proxStream.seek(lazySkipPointer);
			lazySkipPointer = -1L;
		}
		if (lazySkipProxCount != 0)
		{
			skipPositions(lazySkipProxCount);
			lazySkipProxCount = 0;
		}
	}

	public int getPayloadLength()
	{
		return payloadLength;
	}

	public BytesRef getPayload()
		throws IOException
	{
		if (payloadLength <= 0)
			return null;
		if (needToLoadPayload)
		{
			if (payload == null)
				payload = new BytesRef(payloadLength);
			else
				payload.grow(payloadLength);
			proxStream.readBytes(payload.bytes, payload.offset, payloadLength);
			payload.length = payloadLength;
			needToLoadPayload = false;
		}
		return payload;
	}

	public boolean isPayloadAvailable()
	{
		return needToLoadPayload && payloadLength > 0;
	}

}
