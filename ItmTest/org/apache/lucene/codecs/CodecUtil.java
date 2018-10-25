// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CodecUtil.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.BytesRef;

public final class CodecUtil
{

	public static final int CODEC_MAGIC = 0x3fd76c17;

	private CodecUtil()
	{
	}

	public static void writeHeader(DataOutput out, String codec, int version)
		throws IOException
	{
		BytesRef bytes = new BytesRef(codec);
		if (bytes.length != codec.length() || bytes.length >= 128)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("codec must be simple ASCII, less than 128 characters in length [got ").append(codec).append("]").toString());
		} else
		{
			out.writeInt(0x3fd76c17);
			out.writeString(codec);
			out.writeInt(version);
			return;
		}
	}

	public static int headerLength(String codec)
	{
		return 9 + codec.length();
	}

	public static int checkHeader(DataInput in, String codec, int minVersion, int maxVersion)
		throws IOException
	{
		int actualHeader = in.readInt();
		if (actualHeader != 0x3fd76c17)
			throw new CorruptIndexException((new StringBuilder()).append("codec header mismatch: actual header=").append(actualHeader).append(" vs expected header=").append(0x3fd76c17).append(" (resource: ").append(in).append(")").toString());
		else
			return checkHeaderNoMagic(in, codec, minVersion, maxVersion);
	}

	public static int checkHeaderNoMagic(DataInput in, String codec, int minVersion, int maxVersion)
		throws IOException
	{
		String actualCodec = in.readString();
		if (!actualCodec.equals(codec))
			throw new CorruptIndexException((new StringBuilder()).append("codec mismatch: actual codec=").append(actualCodec).append(" vs expected codec=").append(codec).append(" (resource: ").append(in).append(")").toString());
		int actualVersion = in.readInt();
		if (actualVersion < minVersion)
			throw new IndexFormatTooOldException(in, actualVersion, minVersion, maxVersion);
		if (actualVersion > maxVersion)
			throw new IndexFormatTooNewException(in, actualVersion, minVersion, maxVersion);
		else
			return actualVersion;
	}
}
