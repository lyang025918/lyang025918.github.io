// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompressionTools.java

package org.apache.lucene.document;

import java.io.ByteArrayOutputStream;
import java.util.zip.*;
import org.apache.lucene.util.*;

public class CompressionTools
{

	private CompressionTools()
	{
	}

	public static byte[] compress(byte value[], int offset, int length, int compressionLevel)
	{
		ByteArrayOutputStream bos;
		Deflater compressor;
		bos = new ByteArrayOutputStream(length);
		compressor = new Deflater();
		compressor.setLevel(compressionLevel);
		compressor.setInput(value, offset, length);
		compressor.finish();
		byte buf[] = new byte[1024];
		int count;
		for (; !compressor.finished(); bos.write(buf, 0, count))
			count = compressor.deflate(buf);

		compressor.end();
		break MISSING_BLOCK_LABEL_93;
		Exception exception;
		exception;
		compressor.end();
		throw exception;
		return bos.toByteArray();
	}

	public static byte[] compress(byte value[], int offset, int length)
	{
		return compress(value, offset, length, 9);
	}

	public static byte[] compress(byte value[])
	{
		return compress(value, 0, value.length, 9);
	}

	public static byte[] compressString(String value)
	{
		return compressString(value, 9);
	}

	public static byte[] compressString(String value, int compressionLevel)
	{
		BytesRef result = new BytesRef();
		UnicodeUtil.UTF16toUTF8(value, 0, value.length(), result);
		return compress(result.bytes, 0, result.length, compressionLevel);
	}

	public static byte[] decompress(BytesRef bytes)
		throws DataFormatException
	{
		return decompress(bytes.bytes, bytes.offset, bytes.length);
	}

	public static byte[] decompress(byte value[])
		throws DataFormatException
	{
		return decompress(value, 0, value.length);
	}

	public static byte[] decompress(byte value[], int offset, int length)
		throws DataFormatException
	{
		ByteArrayOutputStream bos;
		Inflater decompressor;
		bos = new ByteArrayOutputStream(length);
		decompressor = new Inflater();
		decompressor.setInput(value, offset, length);
		byte buf[] = new byte[1024];
		int count;
		for (; !decompressor.finished(); bos.write(buf, 0, count))
			count = decompressor.inflate(buf);

		decompressor.end();
		break MISSING_BLOCK_LABEL_80;
		Exception exception;
		exception;
		decompressor.end();
		throw exception;
		return bos.toByteArray();
	}

	public static String decompressString(byte value[])
		throws DataFormatException
	{
		return decompressString(value, 0, value.length);
	}

	public static String decompressString(byte value[], int offset, int length)
		throws DataFormatException
	{
		byte bytes[] = decompress(value, offset, length);
		CharsRef result = new CharsRef(bytes.length);
		UnicodeUtil.UTF8toUTF16(bytes, 0, bytes.length, result);
		return new String(result.chars, 0, result.length);
	}

	public static String decompressString(BytesRef bytes)
		throws DataFormatException
	{
		return decompressString(bytes.bytes, bytes.offset, bytes.length);
	}
}
