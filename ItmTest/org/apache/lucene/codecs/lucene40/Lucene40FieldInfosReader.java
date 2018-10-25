// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40FieldInfosReader.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.FieldInfosReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

public class Lucene40FieldInfosReader extends FieldInfosReader
{

	public Lucene40FieldInfosReader()
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
		CodecUtil.checkHeader(input, "Lucene40FieldInfos", 0, 0);
		int size = input.readVInt();
		FieldInfo infos[] = new FieldInfo[size];
		for (int i = 0; i < size; i++)
		{
			String name = input.readString();
			int fieldNumber = input.readVInt();
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
				indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS;
			else
			if ((bits & 4) != 0)
				indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
			else
				indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
			if (isIndexed && indexOptions.compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0)
				storePayloads = false;
			byte val = input.readByte();
			org.apache.lucene.index.DocValues.Type docValuesType = getDocValuesType((byte)(val & 0xf));
			org.apache.lucene.index.DocValues.Type normsType = getDocValuesType((byte)(val >>> 4 & 0xf));
			java.util.Map attributes = input.readStringStringMap();
			infos[i] = new FieldInfo(name, isIndexed, fieldNumber, storeTermVector, omitNorms, storePayloads, indexOptions, docValuesType, normsType, Collections.unmodifiableMap(attributes));
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

	private static org.apache.lucene.index.DocValues.Type getDocValuesType(byte b)
	{
		switch (b)
		{
		case 0: // '\0'
			return null;

		case 1: // '\001'
			return org.apache.lucene.index.DocValues.Type.VAR_INTS;

		case 2: // '\002'
			return org.apache.lucene.index.DocValues.Type.FLOAT_32;

		case 3: // '\003'
			return org.apache.lucene.index.DocValues.Type.FLOAT_64;

		case 4: // '\004'
			return org.apache.lucene.index.DocValues.Type.BYTES_FIXED_STRAIGHT;

		case 5: // '\005'
			return org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF;

		case 6: // '\006'
			return org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT;

		case 7: // '\007'
			return org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF;

		case 8: // '\b'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_16;

		case 9: // '\t'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_32;

		case 10: // '\n'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_64;

		case 11: // '\013'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_8;

		case 12: // '\f'
			return org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED;

		case 13: // '\r'
			return org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED;
		}
		throw new IllegalStateException((new StringBuilder()).append("unhandled indexValues type ").append(b).toString());
	}
}
