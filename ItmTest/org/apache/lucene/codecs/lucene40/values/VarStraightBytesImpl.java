// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VarStraightBytesImpl.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			DirectSource, Bytes

class VarStraightBytesImpl
{
	public static final class DirectVarStraightSource extends DirectSource
	{

		private final org.apache.lucene.util.packed.PackedInts.Reader index;

		protected int position(int docID)
			throws IOException
		{
			long offset = index.get(docID);
			data.seek(baseOffset + offset);
			long nextOffset = index.get(1 + docID);
			return (int)(nextOffset - offset);
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

		DirectVarStraightSource(IndexInput data, IndexInput index, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(data, type);
			index.readVLong();
			this.index = PackedInts.getDirectReader(index);
		}
	}

	private static final class VarStraightSource extends Bytes.BytesSourceBase
	{

		private final org.apache.lucene.util.packed.PackedInts.Reader addresses;

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			long address = addresses.get(docID);
			return data.fillSlice(bytesRef, address, (int)(addresses.get(docID + 1) - address));
		}

		public VarStraightSource(IndexInput datIn, IndexInput idxIn)
			throws IOException
		{
			super(datIn, idxIn, new PagedBytes(15), idxIn.readVLong(), org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT);
			addresses = PackedInts.getReader(idxIn);
		}
	}

	public static class VarStraightReader extends Bytes.BytesReaderBase
	{

		final int maxDoc;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return new VarStraightSource(cloneData(), cloneIndex());
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return new DirectVarStraightSource(cloneData(), cloneIndex(), getType());
		}

		public volatile org.apache.lucene.index.DocValues.Type getType()
		{
			return super.getType();
		}

		public volatile void close()
			throws IOException
		{
			super.close();
		}

		VarStraightReader(Directory dir, String id, int maxDoc, IOContext context)
			throws IOException
		{
			super(dir, id, "VarStraightBytesIdx", "VarStraightBytesDat", 0, true, context, org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT);
			this.maxDoc = maxDoc;
		}
	}

	static class Writer extends Bytes.BytesWriterBase
	{

		private long address;
		private int lastDocID;
		private long docToAddress[];
		private final ByteBlockPool pool;
		private IndexOutput datOut;
		private boolean merge;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/VarStraightBytesImpl.desiredAssertionStatus();

		private void fill(int docID, long nextAddress)
		{
			if (docID >= docToAddress.length)
			{
				int oldSize = docToAddress.length;
				docToAddress = ArrayUtil.grow(docToAddress, 1 + docID);
				bytesUsed.addAndGet((docToAddress.length - oldSize) * 4);
			}
			for (int i = lastDocID + 1; i < docID; i++)
				docToAddress[i] = nextAddress;

		}

		public void add(int docID, IndexableField value)
			throws IOException
		{
			BytesRef bytes = value.binaryValue();
			if (!$assertionsDisabled && bytes == null)
				throw new AssertionError();
			if (!$assertionsDisabled && merge)
				throw new AssertionError();
			if (bytes.length == 0)
			{
				return;
			} else
			{
				fill(docID, address);
				docToAddress[docID] = address;
				pool.copy(bytes);
				address += bytes.length;
				lastDocID = docID;
				return;
			}
		}

		protected void merge(DocValues readerIn, int docBase, int docCount, Bits liveDocs)
			throws IOException
		{
			boolean success;
			merge = true;
			datOut = getOrCreateDataOut();
			success = false;
			VarStraightReader reader;
			int maxDocs;
			if (liveDocs != null || !(readerIn instanceof VarStraightReader))
				break MISSING_BLOCK_LABEL_325;
			reader = (VarStraightReader)readerIn;
			maxDocs = reader.maxDoc;
			if (maxDocs == 0)
			{
				if (!success)
					IOUtils.closeWhileHandlingException(new Closeable[] {
						datOut
					});
				return;
			}
			IndexInput cloneIdx;
			if (lastDocID + 1 < docBase)
			{
				fill(docBase, address);
				lastDocID = docBase - 1;
			}
			cloneIdx = reader.cloneIndex();
			long numDataBytes;
			numDataBytes = cloneIdx.readVLong();
			org.apache.lucene.util.packed.PackedInts.ReaderIterator iter = PackedInts.getReaderIterator(cloneIdx, 1024);
			for (int i = 0; i < maxDocs; i++)
			{
				long offset = iter.next();
				lastDocID++;
				if (lastDocID >= docToAddress.length)
				{
					int oldSize = docToAddress.length;
					docToAddress = ArrayUtil.grow(docToAddress, 1 + lastDocID);
					bytesUsed.addAndGet((docToAddress.length - oldSize) * 4);
				}
				docToAddress[lastDocID] = address + offset;
			}

			address += numDataBytes;
			iter.close();
			IOUtils.close(new Closeable[] {
				cloneIdx
			});
			break MISSING_BLOCK_LABEL_272;
			Exception exception;
			exception;
			IOUtils.close(new Closeable[] {
				cloneIdx
			});
			throw exception;
			IndexInput cloneData = reader.cloneData();
			datOut.copyBytes(cloneData, numDataBytes);
			IOUtils.close(new Closeable[] {
				cloneData
			});
			break MISSING_BLOCK_LABEL_334;
			Exception exception1;
			exception1;
			IOUtils.close(new Closeable[] {
				cloneData
			});
			throw exception1;
			super.merge(readerIn, docBase, docCount, liveDocs);
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			break MISSING_BLOCK_LABEL_383;
			Exception exception2;
			exception2;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			throw exception2;
		}

		protected void mergeDoc(Field scratchField, org.apache.lucene.index.DocValues.Source source, int docID, int sourceDoc)
			throws IOException
		{
			if (!$assertionsDisabled && !merge)
				throw new AssertionError();
			if (!$assertionsDisabled && lastDocID >= docID)
				throw new AssertionError();
			source.getBytes(sourceDoc, bytesRef);
			if (bytesRef.length == 0)
			{
				return;
			} else
			{
				fill(docID, address);
				datOut.writeBytes(bytesRef.bytes, bytesRef.offset, bytesRef.length);
				docToAddress[docID] = address;
				address += bytesRef.length;
				lastDocID = docID;
				return;
			}
		}

		public void finish(int docCount)
			throws IOException
		{
			boolean success;
			IndexOutput datOut;
			success = false;
			if (!$assertionsDisabled && (merge || this.datOut != null) && (!merge || this.datOut == null))
				throw new AssertionError();
			datOut = getOrCreateDataOut();
			if (!merge)
				pool.writePool(datOut);
			success = true;
			if (success)
				IOUtils.close(new Closeable[] {
					datOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			pool.dropBuffersAndReset();
			break MISSING_BLOCK_LABEL_146;
			Exception exception;
			exception;
			if (success)
				IOUtils.close(new Closeable[] {
					datOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			pool.dropBuffersAndReset();
			throw exception;
			IndexOutput idxOut;
			success = false;
			idxOut = getOrCreateIndexOut();
			if (lastDocID == -1)
			{
				idxOut.writeVLong(0L);
				org.apache.lucene.util.packed.PackedInts.Writer w = PackedInts.getWriter(idxOut, docCount + 1, PackedInts.bitsRequired(0L), 0.2F);
				for (int i = 0; i < docCount + 1; i++)
					w.add(0L);

				w.finish();
			} else
			{
				fill(docCount, address);
				idxOut.writeVLong(address);
				org.apache.lucene.util.packed.PackedInts.Writer w = PackedInts.getWriter(idxOut, docCount + 1, PackedInts.bitsRequired(address), 0.2F);
				for (int i = 0; i < docCount; i++)
					w.add(docToAddress[i]);

				w.add(address);
				w.finish();
			}
			success = true;
			bytesUsed.addAndGet(-docToAddress.length * 4);
			docToAddress = null;
			if (success)
				IOUtils.close(new Closeable[] {
					idxOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					idxOut
				});
			break MISSING_BLOCK_LABEL_409;
			Exception exception1;
			exception1;
			bytesUsed.addAndGet(-docToAddress.length * 4);
			docToAddress = null;
			if (success)
				IOUtils.close(new Closeable[] {
					idxOut
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					idxOut
				});
			throw exception1;
		}

		public long ramBytesUsed()
		{
			return bytesUsed.get();
		}

		public int getValueSize()
		{
			return -1;
		}


		public Writer(Directory dir, String id, Counter bytesUsed, IOContext context)
		{
			super(dir, id, "VarStraightBytesIdx", "VarStraightBytesDat", 0, bytesUsed, context, org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT);
			lastDocID = -1;
			merge = false;
			pool = new ByteBlockPool(new org.apache.lucene.util.ByteBlockPool.DirectTrackingAllocator(bytesUsed));
			docToAddress = new long[1];
			pool.nextBuffer();
			bytesUsed.addAndGet(4L);
		}
	}


	static final String CODEC_NAME_IDX = "VarStraightBytesIdx";
	static final String CODEC_NAME_DAT = "VarStraightBytesDat";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	VarStraightBytesImpl()
	{
	}
}
