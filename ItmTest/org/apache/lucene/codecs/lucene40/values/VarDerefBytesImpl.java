// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VarDerefBytesImpl.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.IOException;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			DirectSource, Bytes

class VarDerefBytesImpl
{
	static final class DirectVarDerefSource extends DirectSource
	{

		private final org.apache.lucene.util.packed.PackedInts.Reader index;

		protected int position(int docID)
			throws IOException
		{
			data.seek(baseOffset + index.get(docID));
			byte sizeByte = data.readByte();
			if ((sizeByte & 0x80) == 0)
				return sizeByte;
			else
				return (sizeByte & 0x7f) << 8 | data.readByte() & 0xff;
		}

		DirectVarDerefSource(IndexInput data, IndexInput index, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(data, type);
			this.index = PackedInts.getDirectReader(index);
		}
	}

	static final class VarDerefSource extends Bytes.BytesSourceBase
	{

		private final org.apache.lucene.util.packed.PackedInts.Reader addresses;

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			return data.fillSliceWithPrefix(bytesRef, addresses.get(docID));
		}

		public VarDerefSource(IndexInput datIn, IndexInput idxIn, long totalBytes)
			throws IOException
		{
			super(datIn, idxIn, new PagedBytes(15), totalBytes, org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF);
			addresses = PackedInts.getReader(idxIn);
		}
	}

	public static class VarDerefReader extends Bytes.BytesReaderBase
	{

		private final long totalBytes;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return new VarDerefSource(cloneData(), cloneIndex(), totalBytes);
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return new DirectVarDerefSource(cloneData(), cloneIndex(), getType());
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

		VarDerefReader(Directory dir, String id, int maxDoc, IOContext context)
			throws IOException
		{
			super(dir, id, "VarDerefBytesIdx", "VarDerefBytesDat", 0, true, context, org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF);
			totalBytes = idxIn.readLong();
		}
	}

	static class Writer extends Bytes.DerefBytesWriterBase
	{

		protected void checkSize(BytesRef bytesref)
		{
		}

		public void finishInternal(int docCount)
			throws IOException
		{
			fillDefault(docCount);
			int size = hash.size();
			long addresses[] = new long[size];
			IndexOutput datOut = getOrCreateDataOut();
			int addr = 0;
			BytesRef bytesRef = new BytesRef();
			for (int i = 0; i < size; i++)
			{
				hash.get(i, bytesRef);
				addresses[i] = addr;
				addr += writePrefixLength(datOut, bytesRef) + bytesRef.length;
				datOut.writeBytes(bytesRef.bytes, bytesRef.offset, bytesRef.length);
			}

			IndexOutput idxOut = getOrCreateIndexOut();
			idxOut.writeLong(addr);
			writeIndex(idxOut, docCount, addresses[addresses.length - 1], addresses, docToEntry);
		}

		public Writer(Directory dir, String id, Counter bytesUsed, IOContext context)
		{
			super(dir, id, "VarDerefBytesIdx", "VarDerefBytesDat", 0, bytesUsed, context, org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF);
			size = 0;
		}
	}


	static final String CODEC_NAME_IDX = "VarDerefBytesIdx";
	static final String CODEC_NAME_DAT = "VarDerefBytesDat";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	VarDerefBytesImpl()
	{
	}
}
