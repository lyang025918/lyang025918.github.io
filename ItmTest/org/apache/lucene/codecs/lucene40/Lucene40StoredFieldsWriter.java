// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40StoredFieldsWriter.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.StoredFieldsWriter;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40StoredFieldsReader

public final class Lucene40StoredFieldsWriter extends StoredFieldsWriter
{

	static final int FIELD_IS_BINARY = 2;
	private static final int _NUMERIC_BIT_SHIFT = 3;
	static final int FIELD_IS_NUMERIC_MASK = 56;
	static final int FIELD_IS_NUMERIC_INT = 8;
	static final int FIELD_IS_NUMERIC_LONG = 16;
	static final int FIELD_IS_NUMERIC_FLOAT = 24;
	static final int FIELD_IS_NUMERIC_DOUBLE = 32;
	static final String CODEC_NAME_IDX = "Lucene40StoredFieldsIndex";
	static final String CODEC_NAME_DAT = "Lucene40StoredFieldsData";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;
	static final long HEADER_LENGTH_IDX = (long)CodecUtil.headerLength("Lucene40StoredFieldsIndex");
	static final long HEADER_LENGTH_DAT = (long)CodecUtil.headerLength("Lucene40StoredFieldsData");
	public static final String FIELDS_EXTENSION = "fdt";
	public static final String FIELDS_INDEX_EXTENSION = "fdx";
	private final Directory directory;
	private final String segment;
	private IndexOutput fieldsStream;
	private IndexOutput indexStream;
	private static final int MAX_RAW_MERGE_DOCS = 4192;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40StoredFieldsWriter.desiredAssertionStatus();

	public Lucene40StoredFieldsWriter(Directory directory, String segment, IOContext context)
		throws IOException
	{
		boolean success;
		if (!$assertionsDisabled && directory == null)
			throw new AssertionError();
		this.directory = directory;
		this.segment = segment;
		success = false;
		fieldsStream = directory.createOutput(IndexFileNames.segmentFileName(segment, "", "fdt"), context);
		indexStream = directory.createOutput(IndexFileNames.segmentFileName(segment, "", "fdx"), context);
		CodecUtil.writeHeader(fieldsStream, "Lucene40StoredFieldsData", 0);
		CodecUtil.writeHeader(indexStream, "Lucene40StoredFieldsIndex", 0);
		if (!$assertionsDisabled && HEADER_LENGTH_DAT != fieldsStream.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && HEADER_LENGTH_IDX != indexStream.getFilePointer())
			throw new AssertionError();
		success = true;
		if (!success)
			abort();
		break MISSING_BLOCK_LABEL_174;
		Exception exception;
		exception;
		if (!success)
			abort();
		throw exception;
	}

	public void startDocument(int numStoredFields)
		throws IOException
	{
		indexStream.writeLong(fieldsStream.getFilePointer());
		fieldsStream.writeVInt(numStoredFields);
	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			fieldsStream, indexStream
		});
		fieldsStream = indexStream = null;
		break MISSING_BLOCK_LABEL_47;
		Exception exception;
		exception;
		fieldsStream = indexStream = null;
		throw exception;
	}

	public void abort()
	{
		try
		{
			close();
		}
		catch (Throwable ignored) { }
		IOUtils.deleteFilesIgnoringExceptions(directory, new String[] {
			IndexFileNames.segmentFileName(segment, "", "fdt"), IndexFileNames.segmentFileName(segment, "", "fdx")
		});
	}

	public void writeField(FieldInfo info, IndexableField field)
		throws IOException
	{
		fieldsStream.writeVInt(info.number);
		int bits = 0;
		Number number = field.numericValue();
		BytesRef bytes;
		String string;
		if (number != null)
		{
			if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer))
				bits |= 8;
			else
			if (number instanceof Long)
				bits |= 0x10;
			else
			if (number instanceof Float)
				bits |= 0x18;
			else
			if (number instanceof Double)
				bits |= 0x20;
			else
				throw new IllegalArgumentException((new StringBuilder()).append("cannot store numeric type ").append(number.getClass()).toString());
			string = null;
			bytes = null;
		} else
		{
			bytes = field.binaryValue();
			if (bytes != null)
			{
				bits |= 2;
				string = null;
			} else
			{
				string = field.stringValue();
				if (string == null)
					throw new IllegalArgumentException((new StringBuilder()).append("field ").append(field.name()).append(" is stored but does not have binaryValue, stringValue nor numericValue").toString());
			}
		}
		fieldsStream.writeByte((byte)bits);
		if (bytes != null)
		{
			fieldsStream.writeVInt(bytes.length);
			fieldsStream.writeBytes(bytes.bytes, bytes.offset, bytes.length);
		} else
		if (string != null)
			fieldsStream.writeString(field.stringValue());
		else
		if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer))
			fieldsStream.writeInt(number.intValue());
		else
		if (number instanceof Long)
			fieldsStream.writeLong(number.longValue());
		else
		if (number instanceof Float)
			fieldsStream.writeInt(Float.floatToIntBits(number.floatValue()));
		else
		if (number instanceof Double)
			fieldsStream.writeLong(Double.doubleToLongBits(number.doubleValue()));
		else
			throw new AssertionError("Cannot get here");
	}

	public void addRawDocuments(IndexInput stream, int lengths[], int numDocs)
		throws IOException
	{
		long position = fieldsStream.getFilePointer();
		long start = position;
		for (int i = 0; i < numDocs; i++)
		{
			indexStream.writeLong(position);
			position += lengths[i];
		}

		fieldsStream.copyBytes(stream, position - start);
		if (!$assertionsDisabled && fieldsStream.getFilePointer() != position)
			throw new AssertionError();
		else
			return;
	}

	public void finish(FieldInfos fis, int numDocs)
	{
		if (HEADER_LENGTH_IDX + (long)numDocs * 8L != indexStream.getFilePointer())
			throw new RuntimeException((new StringBuilder()).append("fdx size mismatch: docCount is ").append(numDocs).append(" but fdx file size is ").append(indexStream.getFilePointer()).append(" file=").append(indexStream.toString()).append("; now aborting this merge to prevent index corruption").toString());
		else
			return;
	}

	public int merge(MergeState mergeState)
		throws IOException
	{
		int docCount = 0;
		int rawDocLengths[] = new int[4192];
		int idx = 0;
		for (Iterator i$ = mergeState.readers.iterator(); i$.hasNext();)
		{
			AtomicReader reader = (AtomicReader)i$.next();
			SegmentReader matchingSegmentReader = mergeState.matchingSegmentReaders[idx++];
			Lucene40StoredFieldsReader matchingFieldsReader = null;
			if (matchingSegmentReader != null)
			{
				org.apache.lucene.codecs.StoredFieldsReader fieldsReader = matchingSegmentReader.getFieldsReader();
				if (fieldsReader != null && (fieldsReader instanceof Lucene40StoredFieldsReader))
					matchingFieldsReader = (Lucene40StoredFieldsReader)fieldsReader;
			}
			if (reader.getLiveDocs() != null)
				docCount += copyFieldsWithDeletions(mergeState, reader, matchingFieldsReader, rawDocLengths);
			else
				docCount += copyFieldsNoDeletions(mergeState, reader, matchingFieldsReader, rawDocLengths);
		}

		finish(mergeState.fieldInfos, docCount);
		return docCount;
	}

	private int copyFieldsWithDeletions(MergeState mergeState, AtomicReader reader, Lucene40StoredFieldsReader matchingFieldsReader, int rawDocLengths[])
		throws IOException
	{
		int docCount = 0;
		int maxDoc = reader.maxDoc();
		Bits liveDocs = reader.getLiveDocs();
		if (!$assertionsDisabled && liveDocs == null)
			throw new AssertionError();
		if (matchingFieldsReader != null)
		{
			for (int j = 0; j < maxDoc;)
				if (!liveDocs.get(j))
				{
					j++;
				} else
				{
					int start = j;
					int numDocs = 0;
					do
					{
						j++;
						numDocs++;
						if (j >= maxDoc)
							break;
						if (liveDocs.get(j))
							continue;
						j++;
						break;
					} while (numDocs < 4192);
					IndexInput stream = matchingFieldsReader.rawDocs(rawDocLengths, start, numDocs);
					addRawDocuments(stream, rawDocLengths, numDocs);
					docCount += numDocs;
					mergeState.checkAbort.work(300 * numDocs);
				}

		} else
		{
			for (int j = 0; j < maxDoc; j++)
				if (liveDocs.get(j))
				{
					org.apache.lucene.document.Document doc = reader.document(j);
					addDocument(doc, mergeState.fieldInfos);
					docCount++;
					mergeState.checkAbort.work(300D);
				}

		}
		return docCount;
	}

	private int copyFieldsNoDeletions(MergeState mergeState, AtomicReader reader, Lucene40StoredFieldsReader matchingFieldsReader, int rawDocLengths[])
		throws IOException
	{
		int maxDoc = reader.maxDoc();
		int docCount = 0;
		if (matchingFieldsReader != null)
			while (docCount < maxDoc) 
			{
				int len = Math.min(4192, maxDoc - docCount);
				IndexInput stream = matchingFieldsReader.rawDocs(rawDocLengths, docCount, len);
				addRawDocuments(stream, rawDocLengths, len);
				docCount += len;
				mergeState.checkAbort.work(300 * len);
			}
		else
			for (; docCount < maxDoc; docCount++)
			{
				org.apache.lucene.document.Document doc = reader.document(docCount);
				addDocument(doc, mergeState.fieldInfos);
				mergeState.checkAbort.work(300D);
			}

		return docCount;
	}

}
