// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FixedStraightBytesImpl.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StraightBytesDocValuesField;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			DirectSource, Bytes

class FixedStraightBytesImpl
{
	public static final class DirectFixedStraightSource extends DirectSource
	{

		private final int size;

		protected int position(int docID)
			throws IOException
		{
			data.seek(baseOffset + (long)size * (long)docID);
			return size;
		}

		public volatile double getFloat(int x0)
		{
			return super.getFloat(x0);
		}

		public volatile long getInt(int x0)
		{
			return super.getInt(x0);
		}

		public volatile BytesRef getBytes(int x0, BytesRef x1)
		{
			return super.getBytes(x0, x1);
		}

		DirectFixedStraightSource(IndexInput input, int size, org.apache.lucene.index.DocValues.Type type)
		{
			super(input, type);
			this.size = size;
		}
	}

	private static final class FixedStraightSource extends Bytes.BytesSourceBase
	{

		private final int size;

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			return data.fillSlice(bytesRef, (long)size * (long)docID, size);
		}

		public FixedStraightSource(IndexInput datIn, int size, int maxDoc, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(datIn, null, new PagedBytes(15), size * maxDoc, type);
			this.size = size;
		}
	}

	private static final class SingleByteSource extends org.apache.lucene.index.DocValues.Source
	{

		private final byte data[];

		public boolean hasArray()
		{
			return true;
		}

		public Object getArray()
		{
			return data;
		}

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			bytesRef.length = 1;
			bytesRef.bytes = data;
			bytesRef.offset = docID;
			return bytesRef;
		}

		public SingleByteSource(IndexInput datIn, int maxDoc)
			throws IOException
		{
			super(org.apache.lucene.index.DocValues.Type.BYTES_FIXED_STRAIGHT);
			data = new byte[maxDoc];
			datIn.readBytes(data, 0, data.length, false);
			IOUtils.close(new Closeable[] {
				datIn
			});
			break MISSING_BLOCK_LABEL_57;
			Exception exception;
			exception;
			IOUtils.close(new Closeable[] {
				datIn
			});
			throw exception;
		}
	}

	public static class FixedStraightReader extends Bytes.BytesReaderBase
	{

		protected final int size;
		protected final int maxDoc;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return ((org.apache.lucene.index.DocValues.Source) (size != 1 ? new FixedStraightSource(cloneData(), size, maxDoc, type) : new SingleByteSource(cloneData(), maxDoc)));
		}

		public void close()
			throws IOException
		{
			datIn.close();
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return new DirectFixedStraightSource(cloneData(), size, getType());
		}

		public int getValueSize()
		{
			return size;
		}

		public volatile org.apache.lucene.index.DocValues.Type getType()
		{
			return super.getType();
		}

		FixedStraightReader(Directory dir, String id, int maxDoc, IOContext context)
			throws IOException
		{
			this(dir, id, "FixedStraightBytes", 0, maxDoc, context, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_STRAIGHT);
		}

		protected FixedStraightReader(Directory dir, String id, String codecNameDat, int version, int maxDoc, IOContext context, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(dir, id, null, codecNameDat, version, false, context, type);
			size = datIn.readInt();
			this.maxDoc = maxDoc;
		}
	}

	static class Writer extends FixedBytesWriterBase
	{

		private boolean hasMerged;
		private IndexOutput datOut;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/FixedStraightBytesImpl.desiredAssertionStatus();

		protected void merge(DocValues readerIn, int docBase, int docCount, Bits liveDocs)
			throws IOException
		{
			boolean success;
			datOut = getOrCreateDataOut();
			success = false;
			FixedStraightReader reader;
			int maxDocs;
			if (!hasMerged && size != -1)
				datOut.writeInt(size);
			if (liveDocs != null || !tryBulkMerge(readerIn))
				break MISSING_BLOCK_LABEL_275;
			reader = (FixedStraightReader)readerIn;
			maxDocs = reader.maxDoc;
			if (maxDocs == 0)
			{
				if (!success)
					IOUtils.closeWhileHandlingException(new Closeable[] {
						datOut
					});
				hasMerged = true;
				return;
			}
			IndexInput cloneData;
			if (size == -1)
			{
				size = reader.size;
				datOut.writeInt(size);
			} else
			if (size != reader.size)
				throw new IllegalArgumentException((new StringBuilder()).append("expected bytes size=").append(size).append(" but got ").append(reader.size).toString());
			if (lastDocID + 1 < docBase)
			{
				fill(datOut, docBase);
				lastDocID = docBase - 1;
			}
			cloneData = reader.cloneData();
			datOut.copyBytes(cloneData, size * maxDocs);
			IOUtils.close(new Closeable[] {
				cloneData
			});
			break MISSING_BLOCK_LABEL_261;
			Exception exception;
			exception;
			IOUtils.close(new Closeable[] {
				cloneData
			});
			throw exception;
			lastDocID += maxDocs;
			break MISSING_BLOCK_LABEL_284;
			super.merge(readerIn, docBase, docCount, liveDocs);
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			hasMerged = true;
			break MISSING_BLOCK_LABEL_343;
			Exception exception1;
			exception1;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			hasMerged = true;
			throw exception1;
		}

		protected boolean tryBulkMerge(DocValues docValues)
		{
			return docValues instanceof FixedStraightReader;
		}

		protected void mergeDoc(Field scratchField, org.apache.lucene.index.DocValues.Source source, int docID, int sourceDoc)
			throws IOException
		{
			if (!$assertionsDisabled && lastDocID >= docID)
				throw new AssertionError();
			setMergeBytes(source, sourceDoc);
			if (size == -1)
			{
				size = bytesRef.length;
				datOut.writeInt(size);
			}
			if (!$assertionsDisabled && size != bytesRef.length)
				throw new AssertionError((new StringBuilder()).append("size: ").append(size).append(" ref: ").append(bytesRef.length).toString());
			if (lastDocID + 1 < docID)
				fill(datOut, docID);
			datOut.writeBytes(bytesRef.bytes, bytesRef.offset, bytesRef.length);
			lastDocID = docID;
		}

		protected void setMergeBytes(org.apache.lucene.index.DocValues.Source source, int sourceDoc)
		{
			source.getBytes(sourceDoc, bytesRef);
		}

		private void fill(IndexOutput datOut, int docID)
			throws IOException
		{
			if (!$assertionsDisabled && size < 0)
			{
				throw new AssertionError();
			} else
			{
				writeZeros(docID - (lastDocID + 1), datOut);
				return;
			}
		}

		public void finish(int docCount)
			throws IOException
		{
			boolean success = false;
			if (!hasMerged)
			{
				if (!$assertionsDisabled && datOut != null)
					throw new AssertionError();
				datOut = getOrCreateDataOut();
				if (size == -1)
				{
					datOut.writeInt(0);
				} else
				{
					datOut.writeInt(size);
					writeData(datOut);
				}
				if (lastDocID + 1 < docCount)
					fill(datOut, docCount);
			} else
			{
				if (!$assertionsDisabled && datOut == null)
					throw new AssertionError();
				if (size == -1)
					datOut.writeInt(0);
				else
					fill(datOut, docCount);
			}
			success = true;
			resetPool();
			if (success)
				IOUtils.close(new Closeable[] {
					datOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			break MISSING_BLOCK_LABEL_233;
			Exception exception;
			exception;
			resetPool();
			if (success)
				IOUtils.close(new Closeable[] {
					datOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			throw exception;
		}


		public Writer(Directory dir, String id, Counter bytesUsed, IOContext context)
		{
			super(dir, id, "FixedStraightBytes", 0, bytesUsed, context);
		}

		public Writer(Directory dir, String id, String codecNameDat, int version, Counter bytesUsed, IOContext context)
		{
			super(dir, id, codecNameDat, version, bytesUsed, context);
		}
	}

	static abstract class FixedBytesWriterBase extends Bytes.BytesWriterBase
	{

		protected final StraightBytesDocValuesField bytesSpareField;
		protected int lastDocID;
		protected int size;
		private final int byteBlockSize = 32768;
		private final ByteBlockPool pool;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/FixedStraightBytesImpl.desiredAssertionStatus();

		public void add(int docID, IndexableField value)
			throws IOException
		{
			BytesRef bytes = value.binaryValue();
			if (!$assertionsDisabled && bytes == null)
				throw new AssertionError();
			if (!$assertionsDisabled && lastDocID >= docID)
				throw new AssertionError();
			if (size == -1)
			{
				if (bytes.length > 32768)
					throw new IllegalArgumentException("bytes arrays > 32768 are not supported");
				size = bytes.length;
			} else
			if (bytes.length != size)
				throw new IllegalArgumentException((new StringBuilder()).append("byte[] length changed for BYTES_FIXED_STRAIGHT type (before=").append(size).append(" now=").append(bytes.length).toString());
			if (lastDocID + 1 < docID)
				advancePool(docID);
			pool.copy(bytes);
			lastDocID = docID;
		}

		private final void advancePool(int docID)
		{
			long numBytes;
			for (numBytes = (docID - (lastDocID + 1)) * size; numBytes > 0L;)
				if (numBytes + (long)pool.byteUpto < 32768L)
				{
					pool.byteUpto += numBytes;
					numBytes = 0L;
				} else
				{
					numBytes -= 32768 - pool.byteUpto;
					pool.nextBuffer();
				}

			if (!$assertionsDisabled && numBytes != 0L)
				throw new AssertionError();
			else
				return;
		}

		protected void set(BytesRef ref, int docId)
		{
			if (!$assertionsDisabled && 32768 % size != 0)
			{
				throw new AssertionError((new StringBuilder()).append("BYTE_BLOCK_SIZE (32768) must be a multiple of the size: ").append(size).toString());
			} else
			{
				ref.offset = docId * size;
				ref.length = size;
				pool.deref(ref);
				return;
			}
		}

		protected void resetPool()
		{
			pool.dropBuffersAndReset();
		}

		protected void writeData(IndexOutput out)
			throws IOException
		{
			pool.writePool(out);
		}

		protected void writeZeros(int num, IndexOutput out)
			throws IOException
		{
			byte zeros[] = new byte[size];
			for (int i = 0; i < num; i++)
				out.writeBytes(zeros, zeros.length);

		}

		public int getValueSize()
		{
			return size;
		}


		protected FixedBytesWriterBase(Directory dir, String id, String codecNameDat, int version, Counter bytesUsed, IOContext context)
		{
			this(dir, id, codecNameDat, version, bytesUsed, context, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_STRAIGHT);
		}

		protected FixedBytesWriterBase(Directory dir, String id, String codecNameDat, int version, Counter bytesUsed, IOContext context, org.apache.lucene.index.DocValues.Type type)
		{
			super(dir, id, null, codecNameDat, version, bytesUsed, context, type);
			bytesSpareField = new StraightBytesDocValuesField("", new BytesRef(), true);
			lastDocID = -1;
			size = -1;
			pool = new ByteBlockPool(new org.apache.lucene.util.ByteBlockPool.DirectTrackingAllocator(bytesUsed));
			pool.nextBuffer();
		}
	}


	static final String CODEC_NAME = "FixedStraightBytes";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	FixedStraightBytesImpl()
	{
	}
}
