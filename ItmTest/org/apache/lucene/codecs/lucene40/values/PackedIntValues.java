// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PackedIntValues.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.DocValuesArraySource;
import org.apache.lucene.document.StraightBytesDocValuesField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			FixedStraightBytesImpl

class PackedIntValues
{
	static class PackedIntsSource extends org.apache.lucene.index.DocValues.Source
	{

		private final long minValue;
		private final long defaultValue;
		private final org.apache.lucene.util.packed.PackedInts.Reader values;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/PackedIntValues.desiredAssertionStatus();

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			ref.grow(8);
			DocValuesArraySource.copyLong(ref, getInt(docID));
			return ref;
		}

		public long getInt(int docID)
		{
			if (!$assertionsDisabled && docID < 0)
			{
				throw new AssertionError();
			} else
			{
				long value = values.get(docID);
				return value != defaultValue ? minValue + value : 0L;
			}
		}


		public PackedIntsSource(IndexInput dataIn, boolean direct)
			throws IOException
		{
			super(org.apache.lucene.index.DocValues.Type.VAR_INTS);
			minValue = dataIn.readLong();
			defaultValue = dataIn.readLong();
			values = direct ? PackedInts.getDirectReader(dataIn) : PackedInts.getReader(dataIn);
		}
	}

	static class PackedIntsReader extends DocValues
	{

		private final IndexInput datIn;
		private final byte type;
		private final int numDocs;
		private final DocValuesArraySource values;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			boolean success;
			IndexInput input;
			success = false;
			input = null;
			org.apache.lucene.index.DocValues.Source source1;
			input = datIn.clone();
			org.apache.lucene.index.DocValues.Source source;
			if (values == null)
				source = new PackedIntsSource(input, false);
			else
				source = values.newFromInput(input, numDocs);
			success = true;
			source1 = source;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					input, datIn
				});
			return source1;
			Exception exception;
			exception;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					input, datIn
				});
			throw exception;
		}

		public void close()
			throws IOException
		{
			super.close();
			datIn.close();
		}

		public org.apache.lucene.index.DocValues.Type getType()
		{
			return org.apache.lucene.index.DocValues.Type.VAR_INTS;
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return ((org.apache.lucene.index.DocValues.Source) (values == null ? new PackedIntsSource(datIn.clone(), true) : new FixedStraightBytesImpl.DirectFixedStraightSource(datIn.clone(), 8, org.apache.lucene.index.DocValues.Type.FIXED_INTS_64)));
		}

		protected PackedIntsReader(Directory dir, String id, int numDocs, IOContext context)
			throws IOException
		{
			boolean success;
			datIn = dir.openInput(IndexFileNames.segmentFileName(id, "dv", "dat"), context);
			this.numDocs = numDocs;
			success = false;
			CodecUtil.checkHeader(datIn, "PackedInts", 0, 0);
			type = datIn.readByte();
			values = type != 1 ? null : DocValuesArraySource.forType(org.apache.lucene.index.DocValues.Type.FIXED_INTS_64);
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datIn
				});
			break MISSING_BLOCK_LABEL_124;
			Exception exception;
			exception;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datIn
				});
			throw exception;
		}
	}

	static class PackedIntsWriter extends FixedStraightBytesImpl.FixedBytesWriterBase
	{

		private long minValue;
		private long maxValue;
		private boolean started;
		private int lastDocId;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/PackedIntValues.desiredAssertionStatus();

		public void finish(int docCount)
			throws IOException
		{
			boolean success;
			IndexOutput dataOut;
			success = false;
			dataOut = getOrCreateDataOut();
			if (!started)
				minValue = maxValue = 0L;
			long delta = maxValue - minValue;
			if (delta > (maxValue < 0L || minValue > 0L ? 0x7ffffffffffffffeL : 0x7fffffffffffffffL) || delta < 0L)
				break MISSING_BLOCK_LABEL_120;
			dataOut.writeByte((byte)0);
			writePackedInts(dataOut, docCount);
			resetPool();
			if (success)
				IOUtils.close(new Closeable[] {
					dataOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					dataOut
				});
			return;
			dataOut.writeByte((byte)1);
			writeData(dataOut);
			writeZeros(docCount - (lastDocID + 1), dataOut);
			success = true;
			resetPool();
			if (success)
				IOUtils.close(new Closeable[] {
					dataOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					dataOut
				});
			break MISSING_BLOCK_LABEL_219;
			Exception exception;
			exception;
			resetPool();
			if (success)
				IOUtils.close(new Closeable[] {
					dataOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					dataOut
				});
			throw exception;
		}

		private void writePackedInts(IndexOutput datOut, int docCount)
			throws IOException
		{
			datOut.writeLong(minValue);
			long defaultValue = maxValue < 0L || minValue > 0L ? ++maxValue - minValue : 0L - minValue;
			datOut.writeLong(defaultValue);
			org.apache.lucene.util.packed.PackedInts.Writer w = PackedInts.getWriter(datOut, docCount, PackedInts.bitsRequired(maxValue - minValue), 0.2F);
			for (int i = 0; i < lastDocID + 1; i++)
			{
				set(bytesRef, i);
				byte bytes[] = bytesRef.bytes;
				int offset = bytesRef.offset;
				long asLong = (long)(bytes[offset + 0] & 0xff) << 56 | (long)(bytes[offset + 1] & 0xff) << 48 | (long)(bytes[offset + 2] & 0xff) << 40 | (long)(bytes[offset + 3] & 0xff) << 32 | (long)(bytes[offset + 4] & 0xff) << 24 | (long)(bytes[offset + 5] & 0xff) << 16 | (long)(bytes[offset + 6] & 0xff) << 8 | (long)(bytes[offset + 7] & 0xff);
				w.add(asLong != 0L ? asLong - minValue : defaultValue);
			}

			for (int i = lastDocID + 1; i < docCount; i++)
				w.add(defaultValue);

			w.finish();
		}

		public void add(int docID, IndexableField docValue)
			throws IOException
		{
			long v = docValue.numericValue().longValue();
			if (!$assertionsDisabled && lastDocId >= docID)
				throw new AssertionError();
			if (!started)
			{
				started = true;
				minValue = maxValue = v;
			} else
			if (v < minValue)
				minValue = v;
			else
			if (v > maxValue)
				maxValue = v;
			lastDocId = docID;
			DocValuesArraySource.copyLong(bytesRef, v);
			bytesSpareField.setBytesValue(bytesRef);
			super.add(docID, bytesSpareField);
		}


		protected PackedIntsWriter(Directory dir, String id, Counter bytesUsed, IOContext context)
		{
			super(dir, id, "PackedInts", 0, bytesUsed, context, org.apache.lucene.index.DocValues.Type.VAR_INTS);
			lastDocId = -1;
			bytesRef = new BytesRef(8);
		}
	}


	private static final String CODEC_NAME = "PackedInts";
	private static final byte PACKED = 0;
	private static final byte FIXED_64 = 1;
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	PackedIntValues()
	{
	}
}
