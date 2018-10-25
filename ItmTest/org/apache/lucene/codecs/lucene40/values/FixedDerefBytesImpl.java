// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FixedDerefBytesImpl.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.IOException;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			DirectSource, Bytes

class FixedDerefBytesImpl
{
	static final class DirectFixedDerefSource extends DirectSource
	{

		private final org.apache.lucene.util.packed.PackedInts.Reader index;
		private final int size;

		protected int position(int docID)
			throws IOException
		{
			data.seek(baseOffset + index.get(docID) * (long)size);
			return size;
		}

		DirectFixedDerefSource(IndexInput data, IndexInput index, int size, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(data, type);
			this.size = size;
			this.index = PackedInts.getDirectReader(index);
		}
	}

	static final class FixedDerefSource extends Bytes.BytesSourceBase
	{

		private final int size;
		private final org.apache.lucene.util.packed.PackedInts.Reader addresses;

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			return data.fillSlice(bytesRef, addresses.get(docID) * (long)size, size);
		}

		protected FixedDerefSource(IndexInput datIn, IndexInput idxIn, int size, long numValues)
			throws IOException
		{
			super(datIn, idxIn, new PagedBytes(15), (long)size * numValues, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF);
			this.size = size;
			addresses = PackedInts.getReader(idxIn);
		}
	}

	public static class FixedDerefReader extends Bytes.BytesReaderBase
	{

		private final int size;
		private final int numValuesStored;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return new FixedDerefSource(cloneData(), cloneIndex(), size, numValuesStored);
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return new DirectFixedDerefSource(cloneData(), cloneIndex(), size, getType());
		}

		public int getValueSize()
		{
			return size;
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

		FixedDerefReader(Directory dir, String id, int maxDoc, IOContext context)
			throws IOException
		{
			super(dir, id, "FixedDerefBytesIdx", "FixedDerefBytesDat", 0, true, context, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF);
			size = datIn.readInt();
			numValuesStored = idxIn.readInt();
		}
	}

	public static class Writer extends Bytes.DerefBytesWriterBase
	{

		protected void finishInternal(int docCount)
			throws IOException
		{
			int numValues = hash.size();
			IndexOutput datOut = getOrCreateDataOut();
			datOut.writeInt(size);
			if (size != -1)
			{
				BytesRef bytesRef = new BytesRef(size);
				for (int i = 0; i < numValues; i++)
				{
					hash.get(i, bytesRef);
					datOut.writeBytes(bytesRef.bytes, bytesRef.offset, bytesRef.length);
				}

			}
			IndexOutput idxOut = getOrCreateIndexOut();
			idxOut.writeInt(numValues);
			writeIndex(idxOut, docCount, numValues, docToEntry);
		}

		public volatile void finish(int x0)
			throws IOException
		{
			super.finish(x0);
		}

		public volatile int getValueSize()
		{
			return super.getValueSize();
		}

		public volatile void add(int x0, IndexableField x1)
			throws IOException
		{
			super.add(x0, x1);
		}

		public Writer(Directory dir, String id, Counter bytesUsed, IOContext context)
		{
			super(dir, id, "FixedDerefBytesIdx", "FixedDerefBytesDat", 0, bytesUsed, context, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF);
		}
	}


	static final String CODEC_NAME_IDX = "FixedDerefBytesIdx";
	static final String CODEC_NAME_DAT = "FixedDerefBytesDat";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	FixedDerefBytesImpl()
	{
	}
}
