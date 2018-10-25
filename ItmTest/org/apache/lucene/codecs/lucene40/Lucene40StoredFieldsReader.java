// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40StoredFieldsReader.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.StoredFieldsReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40StoredFieldsWriter

public final class Lucene40StoredFieldsReader extends StoredFieldsReader
	implements Cloneable, Closeable
{

	private final FieldInfos fieldInfos;
	private final IndexInput fieldsStream;
	private final IndexInput indexStream;
	private int numTotalDocs;
	private int size;
	private boolean closed;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40StoredFieldsReader.desiredAssertionStatus();

	public Lucene40StoredFieldsReader clone()
	{
		ensureOpen();
		return new Lucene40StoredFieldsReader(fieldInfos, numTotalDocs, size, fieldsStream.clone(), indexStream.clone());
	}

	private Lucene40StoredFieldsReader(FieldInfos fieldInfos, int numTotalDocs, int size, IndexInput fieldsStream, IndexInput indexStream)
	{
		this.fieldInfos = fieldInfos;
		this.numTotalDocs = numTotalDocs;
		this.size = size;
		this.fieldsStream = fieldsStream;
		this.indexStream = indexStream;
	}

	public Lucene40StoredFieldsReader(Directory d, SegmentInfo si, FieldInfos fn, IOContext context)
		throws IOException
	{
		String segment;
		boolean success;
		segment = si.name;
		success = false;
		fieldInfos = fn;
		fieldsStream = d.openInput(IndexFileNames.segmentFileName(segment, "", "fdt"), context);
		String indexStreamFN = IndexFileNames.segmentFileName(segment, "", "fdx");
		indexStream = d.openInput(indexStreamFN, context);
		CodecUtil.checkHeader(indexStream, "Lucene40StoredFieldsIndex", 0, 0);
		CodecUtil.checkHeader(fieldsStream, "Lucene40StoredFieldsData", 0, 0);
		if (!$assertionsDisabled && Lucene40StoredFieldsWriter.HEADER_LENGTH_DAT != fieldsStream.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && Lucene40StoredFieldsWriter.HEADER_LENGTH_IDX != indexStream.getFilePointer())
			throw new AssertionError();
		long indexSize = indexStream.length() - Lucene40StoredFieldsWriter.HEADER_LENGTH_IDX;
		size = (int)(indexSize >> 3);
		if (size != si.getDocCount())
			throw new CorruptIndexException((new StringBuilder()).append("doc counts differ for segment ").append(segment).append(": fieldsReader shows ").append(size).append(" but segmentInfo shows ").append(si.getDocCount()).toString());
		numTotalDocs = (int)(indexSize >> 3);
		success = true;
		if (!success)
			try
			{
				close();
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_273;
		Exception exception;
		exception;
		if (!success)
			try
			{
				close();
			}
			catch (Throwable t) { }
		throw exception;
	}

	private void ensureOpen()
		throws AlreadyClosedException
	{
		if (closed)
			throw new AlreadyClosedException("this FieldsReader is closed");
		else
			return;
	}

	public final void close()
		throws IOException
	{
		if (!closed)
		{
			IOUtils.close(new Closeable[] {
				fieldsStream, indexStream
			});
			closed = true;
		}
	}

	public final int size()
	{
		return size;
	}

	private void seekIndex(int docID)
		throws IOException
	{
		indexStream.seek(Lucene40StoredFieldsWriter.HEADER_LENGTH_IDX + (long)docID * 8L);
	}

	public final void visitDocument(int n, StoredFieldVisitor visitor)
		throws IOException
	{
		seekIndex(n);
		fieldsStream.seek(indexStream.readLong());
		int numFields = fieldsStream.readVInt();
		for (int fieldIDX = 0; fieldIDX < numFields; fieldIDX++)
		{
			int fieldNumber = fieldsStream.readVInt();
			FieldInfo fieldInfo = fieldInfos.fieldInfo(fieldNumber);
			int bits = fieldsStream.readByte() & 0xff;
			if (!$assertionsDisabled && bits > 58)
				throw new AssertionError((new StringBuilder()).append("bits=").append(Integer.toHexString(bits)).toString());
			static class 1
			{

				static final int $SwitchMap$org$apache$lucene$index$StoredFieldVisitor$Status[];

				static 
				{
					$SwitchMap$org$apache$lucene$index$StoredFieldVisitor$Status = new int[org.apache.lucene.index.StoredFieldVisitor.Status.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$index$StoredFieldVisitor$Status[org.apache.lucene.index.StoredFieldVisitor.Status.YES.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$StoredFieldVisitor$Status[org.apache.lucene.index.StoredFieldVisitor.Status.NO.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$StoredFieldVisitor$Status[org.apache.lucene.index.StoredFieldVisitor.Status.STOP.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (1..SwitchMap.org.apache.lucene.index.StoredFieldVisitor.Status[visitor.needsField(fieldInfo).ordinal()])
			{
			case 1: // '\001'
				readField(visitor, fieldInfo, bits);
				break;

			case 2: // '\002'
				skipField(bits);
				break;

			case 3: // '\003'
				return;
			}
		}

	}

	private void readField(StoredFieldVisitor visitor, FieldInfo info, int bits)
		throws IOException
	{
		int numeric = bits & 0x38;
		if (numeric != 0)
		{
			switch (numeric)
			{
			case 8: // '\b'
				visitor.intField(info, fieldsStream.readInt());
				return;

			case 16: // '\020'
				visitor.longField(info, fieldsStream.readLong());
				return;

			case 24: // '\030'
				visitor.floatField(info, Float.intBitsToFloat(fieldsStream.readInt()));
				return;

			case 32: // ' '
				visitor.doubleField(info, Double.longBitsToDouble(fieldsStream.readLong()));
				return;
			}
			throw new CorruptIndexException((new StringBuilder()).append("Invalid numeric type: ").append(Integer.toHexString(numeric)).toString());
		}
		int length = fieldsStream.readVInt();
		byte bytes[] = new byte[length];
		fieldsStream.readBytes(bytes, 0, length);
		if ((bits & 2) != 0)
			visitor.binaryField(info, bytes);
		else
			visitor.stringField(info, new String(bytes, 0, bytes.length, IOUtils.CHARSET_UTF_8));
	}

	private void skipField(int bits)
		throws IOException
	{
		int numeric = bits & 0x38;
		if (numeric != 0)
		{
			switch (numeric)
			{
			case 8: // '\b'
			case 24: // '\030'
				fieldsStream.readInt();
				return;

			case 16: // '\020'
			case 32: // ' '
				fieldsStream.readLong();
				return;
			}
			throw new CorruptIndexException((new StringBuilder()).append("Invalid numeric type: ").append(Integer.toHexString(numeric)).toString());
		} else
		{
			int length = fieldsStream.readVInt();
			fieldsStream.seek(fieldsStream.getFilePointer() + (long)length);
			return;
		}
	}

	public final IndexInput rawDocs(int lengths[], int startDocID, int numDocs)
		throws IOException
	{
		seekIndex(startDocID);
		long startOffset = indexStream.readLong();
		long lastOffset = startOffset;
		for (int count = 0; count < numDocs;)
		{
			int docID = startDocID + count + 1;
			if (!$assertionsDisabled && docID > numTotalDocs)
				throw new AssertionError();
			long offset;
			if (docID < numTotalDocs)
				offset = indexStream.readLong();
			else
				offset = fieldsStream.length();
			lengths[count++] = (int)(offset - lastOffset);
			lastOffset = offset;
		}

		fieldsStream.seek(startOffset);
		return fieldsStream;
	}

	public volatile StoredFieldsReader clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
