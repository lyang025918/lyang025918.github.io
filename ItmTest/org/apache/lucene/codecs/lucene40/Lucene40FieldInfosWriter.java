// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40FieldInfosWriter.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.FieldInfosWriter;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

public class Lucene40FieldInfosWriter extends FieldInfosWriter
{

	static final String FIELD_INFOS_EXTENSION = "fnm";
	static final String CODEC_NAME = "Lucene40FieldInfos";
	static final int FORMAT_START = 0;
	static final int FORMAT_CURRENT = 0;
	static final byte IS_INDEXED = 1;
	static final byte STORE_TERMVECTOR = 2;
	static final byte STORE_OFFSETS_IN_POSTINGS = 4;
	static final byte OMIT_NORMS = 16;
	static final byte STORE_PAYLOADS = 32;
	static final byte OMIT_TERM_FREQ_AND_POSITIONS = 64;
	static final byte OMIT_POSITIONS = -128;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40FieldInfosWriter.desiredAssertionStatus();

	public Lucene40FieldInfosWriter()
	{
	}

	public void write(Directory directory, String segmentName, FieldInfos infos, IOContext context)
		throws IOException
	{
		IndexOutput output;
		boolean success;
		String fileName = IndexFileNames.segmentFileName(segmentName, "", "fnm");
		output = directory.createOutput(fileName, context);
		success = false;
		CodecUtil.writeHeader(output, "Lucene40FieldInfos", 0);
		output.writeVInt(infos.size());
		FieldInfo fi;
		for (Iterator i$ = infos.iterator(); i$.hasNext(); output.writeStringStringMap(fi.attributes()))
		{
			fi = (FieldInfo)i$.next();
			org.apache.lucene.index.FieldInfo.IndexOptions indexOptions = fi.getIndexOptions();
			byte bits = 0;
			if (fi.hasVectors())
				bits |= 2;
			if (fi.omitsNorms())
				bits |= 0x10;
			if (fi.hasPayloads())
				bits |= 0x20;
			if (fi.isIndexed())
			{
				bits |= 1;
				if (!$assertionsDisabled && indexOptions.compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0 && fi.hasPayloads())
					throw new AssertionError();
				if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
					bits |= 0x40;
				else
				if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS)
					bits |= 4;
				else
				if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS)
					bits |= 0x80;
			}
			output.writeString(fi.name);
			output.writeVInt(fi.number);
			output.writeByte(bits);
			byte dv = docValuesByte(fi.getDocValuesType());
			byte nrm = docValuesByte(fi.getNormType());
			if (!$assertionsDisabled && ((dv & 0xfffffff0) != 0 || (nrm & 0xfffffff0) != 0))
				throw new AssertionError();
			byte val = (byte)(0xff & (nrm << 4 | dv));
			output.writeByte(val);
		}

		success = true;
		if (success)
			output.close();
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				output
			});
		break MISSING_BLOCK_LABEL_400;
		Exception exception;
		exception;
		if (success)
			output.close();
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				output
			});
		throw exception;
	}

	public byte docValuesByte(org.apache.lucene.index.DocValues.Type type)
	{
		if (type == null)
			return 0;
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[org.apache.lucene.index.DocValues.Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.VAR_INTS.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_32.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_64.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_STRAIGHT.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_16.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_32.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_64.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_8.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED.ordinal()] = 13;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
			return 1;

		case 2: // '\002'
			return 2;

		case 3: // '\003'
			return 3;

		case 4: // '\004'
			return 4;

		case 5: // '\005'
			return 5;

		case 6: // '\006'
			return 6;

		case 7: // '\007'
			return 7;

		case 8: // '\b'
			return 8;

		case 9: // '\t'
			return 9;

		case 10: // '\n'
			return 10;

		case 11: // '\013'
			return 11;

		case 12: // '\f'
			return 12;

		case 13: // '\r'
			return 13;
		}
		throw new IllegalStateException((new StringBuilder()).append("unhandled indexValues type ").append(type).toString());
	}

}
