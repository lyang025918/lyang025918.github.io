// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xStoredFieldsReader.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.codecs.StoredFieldsReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xSegmentInfoFormat

/**
 * @deprecated Class Lucene3xStoredFieldsReader is deprecated
 */

final class Lucene3xStoredFieldsReader extends StoredFieldsReader
	implements Cloneable, Closeable
{

	private static final int FORMAT_SIZE = 4;
	public static final String FIELDS_EXTENSION = "fdt";
	public static final String FIELDS_INDEX_EXTENSION = "fdx";
	static final int FORMAT_LUCENE_3_0_NO_COMPRESSED_FIELDS = 2;
	static final int FORMAT_LUCENE_3_2_NUMERIC_FIELDS = 3;
	public static final int FORMAT_CURRENT = 3;
	static final int FORMAT_MINIMUM = 2;
	public static final int FIELD_IS_BINARY = 2;
	private static final int _NUMERIC_BIT_SHIFT = 3;
	static final int FIELD_IS_NUMERIC_MASK = 56;
	public static final int FIELD_IS_NUMERIC_INT = 8;
	public static final int FIELD_IS_NUMERIC_LONG = 16;
	public static final int FIELD_IS_NUMERIC_FLOAT = 24;
	public static final int FIELD_IS_NUMERIC_DOUBLE = 32;
	private final FieldInfos fieldInfos;
	private final IndexInput fieldsStream;
	private final IndexInput indexStream;
	private int numTotalDocs;
	private int size;
	private boolean closed;
	private final int format;
	private int docStoreOffset;
	private final CompoundFileDirectory storeCFSReader;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xStoredFieldsReader.desiredAssertionStatus();

	public Lucene3xStoredFieldsReader clone()
	{
		ensureOpen();
		return new Lucene3xStoredFieldsReader(fieldInfos, numTotalDocs, size, format, docStoreOffset, fieldsStream.clone(), indexStream.clone());
	}

	public static void checkCodeVersion(Directory dir, String segment)
		throws IOException
	{
		IndexInput idxStream;
		String indexStreamFN = IndexFileNames.segmentFileName(segment, "", "fdx");
		idxStream = dir.openInput(indexStreamFN, IOContext.DEFAULT);
		int format = idxStream.readInt();
		if (format < 2)
			throw new IndexFormatTooOldException(idxStream, format, 2, 3);
		if (format > 3)
			throw new IndexFormatTooNewException(idxStream, format, 2, 3);
		idxStream.close();
		break MISSING_BLOCK_LABEL_78;
		Exception exception;
		exception;
		idxStream.close();
		throw exception;
	}

	private Lucene3xStoredFieldsReader(FieldInfos fieldInfos, int numTotalDocs, int size, int format, int docStoreOffset, IndexInput fieldsStream, IndexInput indexStream)
	{
		this.fieldInfos = fieldInfos;
		this.numTotalDocs = numTotalDocs;
		this.size = size;
		this.format = format;
		this.docStoreOffset = docStoreOffset;
		this.fieldsStream = fieldsStream;
		this.indexStream = indexStream;
		storeCFSReader = null;
	}

	public Lucene3xStoredFieldsReader(Directory d, SegmentInfo si, FieldInfos fn, IOContext context)
		throws IOException
	{
		String segment;
		int docStoreOffset;
		int size;
		boolean success;
		segment = Lucene3xSegmentInfoFormat.getDocStoreSegment(si);
		docStoreOffset = Lucene3xSegmentInfoFormat.getDocStoreOffset(si);
		size = si.getDocCount();
		success = false;
		fieldInfos = fn;
		if (docStoreOffset != -1 && Lucene3xSegmentInfoFormat.getDocStoreIsCompoundFile(si))
			d = storeCFSReader = new CompoundFileDirectory(si.dir, IndexFileNames.segmentFileName(segment, "", "cfx"), context, false);
		else
			storeCFSReader = null;
		fieldsStream = d.openInput(IndexFileNames.segmentFileName(segment, "", "fdt"), context);
		String indexStreamFN = IndexFileNames.segmentFileName(segment, "", "fdx");
		indexStream = d.openInput(indexStreamFN, context);
		format = indexStream.readInt();
		if (format < 2)
			throw new IndexFormatTooOldException(indexStream, format, 2, 3);
		if (format > 3)
			throw new IndexFormatTooNewException(indexStream, format, 2, 3);
		long indexSize = indexStream.length() - 4L;
		if (docStoreOffset != -1)
		{
			this.docStoreOffset = docStoreOffset;
			this.size = size;
			if (!$assertionsDisabled && (int)(indexSize / 8L) < size + this.docStoreOffset)
				throw new AssertionError((new StringBuilder()).append("indexSize=").append(indexSize).append(" size=").append(size).append(" docStoreOffset=").append(docStoreOffset).toString());
		} else
		{
			this.docStoreOffset = 0;
			this.size = (int)(indexSize >> 3);
			if (this.size != si.getDocCount())
				throw new CorruptIndexException((new StringBuilder()).append("doc counts differ for segment ").append(segment).append(": fieldsReader shows ").append(this.size).append(" but segmentInfo shows ").append(si.getDocCount()).toString());
		}
		numTotalDocs = (int)(indexSize >> 3);
		success = true;
		if (!success)
			try
			{
				close();
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_412;
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
				fieldsStream, indexStream, storeCFSReader
			});
			closed = true;
		}
	}

	private void seekIndex(int docID)
		throws IOException
	{
		indexStream.seek(4L + (long)(docID + docStoreOffset) * 8L);
	}

	public final void visitDocument(int n, StoredFieldVisitor visitor)
		throws CorruptIndexException, IOException
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
