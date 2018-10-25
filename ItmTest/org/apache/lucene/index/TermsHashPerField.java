// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsHashPerField.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			InvertedDocConsumerPerField, DocInverterPerField, TermsHash, FieldInfo, 
//			DocumentsWriterPerThread, TermsHashConsumer, TermsHashConsumerPerField, ParallelPostingsArray, 
//			IntBlockPool, ByteSliceReader, FieldInvertState, IndexableField

final class TermsHashPerField extends InvertedDocConsumerPerField
{
	private static final class PostingsBytesStartArray extends org.apache.lucene.util.BytesRefHash.BytesStartArray
	{

		private final TermsHashPerField perField;
		private final Counter bytesUsed;

		public int[] init()
		{
			if (perField.postingsArray == null)
			{
				perField.postingsArray = perField.consumer.createPostingsArray(2);
				bytesUsed.addAndGet(perField.postingsArray.size * perField.postingsArray.bytesPerPosting());
			}
			return perField.postingsArray.textStarts;
		}

		public int[] grow()
		{
			ParallelPostingsArray postingsArray = perField.postingsArray;
			int oldSize = perField.postingsArray.size;
			postingsArray = perField.postingsArray = postingsArray.grow();
			bytesUsed.addAndGet(postingsArray.bytesPerPosting() * (postingsArray.size - oldSize));
			return postingsArray.textStarts;
		}

		public int[] clear()
		{
			if (perField.postingsArray != null)
			{
				bytesUsed.addAndGet(-(perField.postingsArray.size * perField.postingsArray.bytesPerPosting()));
				perField.postingsArray = null;
			}
			return null;
		}

		public Counter bytesUsed()
		{
			return bytesUsed;
		}

		private PostingsBytesStartArray(TermsHashPerField perField, Counter bytesUsed)
		{
			this.perField = perField;
			this.bytesUsed = bytesUsed;
		}

	}


	private static final int HASH_INIT_SIZE = 4;
	final TermsHashConsumerPerField consumer;
	final TermsHash termsHash;
	final TermsHashPerField nextPerField;
	final DocumentsWriterPerThread.DocState docState;
	final FieldInvertState fieldState;
	TermToBytesRefAttribute termAtt;
	BytesRef termBytesRef;
	final IntBlockPool intPool;
	final ByteBlockPool bytePool;
	final ByteBlockPool termBytePool;
	final int streamCount;
	final int numPostingInt;
	final FieldInfo fieldInfo;
	final BytesRefHash bytesHash;
	ParallelPostingsArray postingsArray;
	private final Counter bytesUsed;
	private boolean doCall;
	private boolean doNextCall;
	int intUptos[];
	int intUptoStart;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/TermsHashPerField.desiredAssertionStatus();

	public TermsHashPerField(DocInverterPerField docInverterPerField, TermsHash termsHash, TermsHash nextTermsHash, FieldInfo fieldInfo)
	{
		intPool = termsHash.intPool;
		bytePool = termsHash.bytePool;
		termBytePool = termsHash.termBytePool;
		docState = termsHash.docState;
		this.termsHash = termsHash;
		bytesUsed = termsHash.trackAllocations ? termsHash.docWriter.bytesUsed : Counter.newCounter();
		fieldState = docInverterPerField.fieldState;
		consumer = termsHash.consumer.addField(this, fieldInfo);
		PostingsBytesStartArray byteStarts = new PostingsBytesStartArray(this, bytesUsed);
		bytesHash = new BytesRefHash(termBytePool, 4, byteStarts);
		streamCount = consumer.getStreamCount();
		numPostingInt = 2 * streamCount;
		this.fieldInfo = fieldInfo;
		if (nextTermsHash != null)
			nextPerField = (TermsHashPerField)nextTermsHash.addField(docInverterPerField, fieldInfo);
		else
			nextPerField = null;
	}

	void shrinkHash(int targetSize)
	{
		bytesHash.clear(false);
	}

	public void reset()
	{
		bytesHash.clear(false);
		if (nextPerField != null)
			nextPerField.reset();
	}

	public void abort()
	{
		reset();
		if (nextPerField != null)
			nextPerField.abort();
	}

	public void initReader(ByteSliceReader reader, int termID, int stream)
	{
		if (!$assertionsDisabled && stream >= streamCount)
		{
			throw new AssertionError();
		} else
		{
			int intStart = postingsArray.intStarts[termID];
			int ints[] = intPool.buffers[intStart >> 13];
			int upto = intStart & 0x1fff;
			reader.init(bytePool, postingsArray.byteStarts[termID] + stream * ByteBlockPool.FIRST_LEVEL_SIZE, ints[upto + stream]);
			return;
		}
	}

	public int[] sortPostings(Comparator termComp)
	{
		return bytesHash.sort(termComp);
	}

	void start(IndexableField f)
	{
		termAtt = (TermToBytesRefAttribute)fieldState.attributeSource.getAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute);
		termBytesRef = termAtt.getBytesRef();
		consumer.start(f);
		if (nextPerField != null)
			nextPerField.start(f);
	}

	boolean start(IndexableField fields[], int count)
		throws IOException
	{
		doCall = consumer.start(fields, count);
		bytesHash.reinit();
		if (nextPerField != null)
			doNextCall = nextPerField.start(fields, count);
		return doCall || doNextCall;
	}

	public void add(int textStart)
		throws IOException
	{
		int termID = bytesHash.addByPoolOffset(textStart);
		if (termID >= 0)
		{
			if (numPostingInt + intPool.intUpto > 8192)
				intPool.nextBuffer();
			if (32768 - bytePool.byteUpto < numPostingInt * ByteBlockPool.FIRST_LEVEL_SIZE)
				bytePool.nextBuffer();
			intUptos = intPool.buffer;
			intUptoStart = intPool.intUpto;
			intPool.intUpto += streamCount;
			postingsArray.intStarts[termID] = intUptoStart + intPool.intOffset;
			for (int i = 0; i < streamCount; i++)
			{
				int upto = bytePool.newSlice(ByteBlockPool.FIRST_LEVEL_SIZE);
				intUptos[intUptoStart + i] = upto + bytePool.byteOffset;
			}

			postingsArray.byteStarts[termID] = intUptos[intUptoStart];
			consumer.newTerm(termID);
		} else
		{
			termID = -termID - 1;
			int intStart = postingsArray.intStarts[termID];
			intUptos = intPool.buffers[intStart >> 13];
			intUptoStart = intStart & 0x1fff;
			consumer.addTerm(termID);
		}
	}

	void add()
		throws IOException
	{
		int termID;
		int saved;
		try
		{
			termID = bytesHash.add(termBytesRef, termAtt.fillBytesRef());
			break MISSING_BLOCK_LABEL_104;
		}
		catch (org.apache.lucene.util.BytesRefHash.MaxBytesLengthExceededException e) { }
		if (docState.maxTermPrefix != null)
			break MISSING_BLOCK_LABEL_96;
		saved = termBytesRef.length;
		termBytesRef.length = Math.min(30, 32766);
		docState.maxTermPrefix = termBytesRef.toString();
		termBytesRef.length = saved;
		break MISSING_BLOCK_LABEL_96;
		Exception exception;
		exception;
		termBytesRef.length = saved;
		throw exception;
		consumer.skippingLongTerm();
		return;
		if (termID >= 0)
		{
			bytesHash.byteStart(termID);
			if (numPostingInt + intPool.intUpto > 8192)
				intPool.nextBuffer();
			if (32768 - bytePool.byteUpto < numPostingInt * ByteBlockPool.FIRST_LEVEL_SIZE)
				bytePool.nextBuffer();
			intUptos = intPool.buffer;
			intUptoStart = intPool.intUpto;
			intPool.intUpto += streamCount;
			postingsArray.intStarts[termID] = intUptoStart + intPool.intOffset;
			for (int i = 0; i < streamCount; i++)
			{
				int upto = bytePool.newSlice(ByteBlockPool.FIRST_LEVEL_SIZE);
				intUptos[intUptoStart + i] = upto + bytePool.byteOffset;
			}

			postingsArray.byteStarts[termID] = intUptos[intUptoStart];
			consumer.newTerm(termID);
		} else
		{
			termID = -termID - 1;
			int intStart = postingsArray.intStarts[termID];
			intUptos = intPool.buffers[intStart >> 13];
			intUptoStart = intStart & 0x1fff;
			consumer.addTerm(termID);
		}
		if (doNextCall)
			nextPerField.add(postingsArray.textStarts[termID]);
		return;
	}

	void writeByte(int stream, byte b)
	{
		int upto = intUptos[intUptoStart + stream];
		byte bytes[] = bytePool.buffers[upto >> 15];
		if (!$assertionsDisabled && bytes == null)
			throw new AssertionError();
		int offset = upto & 0x7fff;
		if (bytes[offset] != 0)
		{
			offset = bytePool.allocSlice(bytes, offset);
			bytes = bytePool.buffer;
			intUptos[intUptoStart + stream] = offset + bytePool.byteOffset;
		}
		bytes[offset] = b;
		intUptos[intUptoStart + stream]++;
	}

	public void writeBytes(int stream, byte b[], int offset, int len)
	{
		int end = offset + len;
		for (int i = offset; i < end; i++)
			writeByte(stream, b[i]);

	}

	void writeVInt(int stream, int i)
	{
		if (!$assertionsDisabled && stream >= streamCount)
			throw new AssertionError();
		for (; (i & 0xffffff80) != 0; i >>>= 7)
			writeByte(stream, (byte)(i & 0x7f | 0x80));

		writeByte(stream, (byte)i);
	}

	void finish()
		throws IOException
	{
		consumer.finish();
		if (nextPerField != null)
			nextPerField.finish();
	}

}
