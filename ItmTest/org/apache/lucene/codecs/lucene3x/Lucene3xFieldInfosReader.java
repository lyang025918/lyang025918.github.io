// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xFieldInfosReader.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import org.apache.lucene.codecs.FieldInfosReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

/**
 * @deprecated Class Lucene3xFieldInfosReader is deprecated
 */

class Lucene3xFieldInfosReader extends FieldInfosReader
{

	static final String FIELD_INFOS_EXTENSION = "fnm";
	static final int FORMAT_START = -2;
	static final int FORMAT_OMIT_POSITIONS = -3;
	static final int FORMAT_MINIMUM = -2;
	static final int FORMAT_CURRENT = -3;
	static final byte IS_INDEXED = 1;
	static final byte STORE_TERMVECTOR = 2;
	static final byte OMIT_NORMS = 16;
	static final byte STORE_PAYLOADS = 32;
	static final byte OMIT_TERM_FREQ_AND_POSITIONS = 64;
	static final byte OMIT_POSITIONS = -128;

	Lucene3xFieldInfosReader()
	{
	}

	public FieldInfos read(Directory directory, String segmentName, IOContext iocontext)
		throws IOException
	{
		String fileName;
		IndexInput input;
		boolean success;
		fileName = IndexFileNames.segmentFileName(segmentName, "", "fnm");
		input = directory.openInput(fileName, iocontext);
		success = false;
		FieldInfos fieldinfos;
		int format = input.readVInt();
		if (format > -2)
			throw new IndexFormatTooOldException(input, format, -2, -3);
		if (format < -3)
			throw new IndexFormatTooNewException(input, format, -2, -3);
		int size = input.readVInt();
		FieldInfo infos[] = new FieldInfo[size];
		for (int i = 0; i < size; i++)
		{
			String name = input.readString();
			int fieldNumber = i;
			byte bits = input.readByte();
			boolean isIndexed = (bits & 1) != 0;
			boolean storeTermVector = (bits & 2) != 0;
			boolean omitNorms = (bits & 0x10) != 0;
			boolean storePayloads = (bits & 0x20) != 0;
			org.apache.lucene.index.FieldInfo.IndexOptions indexOptions;
			if (!isIndexed)
				indexOptions = null;
			else
			if ((bits & 0x40) != 0)
				indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY;
			else
			if ((bits & 0xffffff80) != 0)
			{
				if (format <= -3)
					indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS;
				else
					throw new CorruptIndexException((new StringBuilder()).append("Corrupt fieldinfos, OMIT_POSITIONS set but format=").append(format).append(" (resource: ").append(input).append(")").toString());
			} else
			{
				indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
			}
			if (indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
				storePayloads = false;
			infos[i] = new FieldInfo(name, isIndexed, fieldNumber, storeTermVector, omitNorms, storePayloads, indexOptions, null, !isIndexed || omitNorms ? null : org.apache.lucene.index.DocValues.Type.FIXED_INTS_8, Collections.emptyMap());
		}

		if (input.getFilePointer() != input.length())
			throw new CorruptIndexException((new StringBuilder()).append("did not read all bytes from file \"").append(fileName).append("\": read ").append(input.getFilePointer()).append(" vs size ").append(input.length()).append(" (resource: ").append(input).append(")").toString());
		FieldInfos fieldInfos = new FieldInfos(infos);
		success = true;
		fieldinfos = fieldInfos;
		if (success)
			input.close();
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		return fieldinfos;
		Exception exception;
		exception;
		if (success)
			input.close();
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		throw exception;
	}
}
