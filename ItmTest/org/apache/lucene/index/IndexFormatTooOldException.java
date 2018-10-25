// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexFormatTooOldException.java

package org.apache.lucene.index;

import org.apache.lucene.store.DataInput;

// Referenced classes of package org.apache.lucene.index:
//			CorruptIndexException

public class IndexFormatTooOldException extends CorruptIndexException
{

	static final boolean $assertionsDisabled = !org/apache/lucene/index/IndexFormatTooOldException.desiredAssertionStatus();

	public IndexFormatTooOldException(String resourceDesc, String version)
	{
		super((new StringBuilder()).append("Format version is not supported (resource: ").append(resourceDesc).append("): ").append(version).append(". This version of Lucene only supports indexes created with release 3.0 and later.").toString());
		if (!$assertionsDisabled && resourceDesc == null)
			throw new AssertionError();
		else
			return;
	}

	public IndexFormatTooOldException(DataInput in, String version)
	{
		this(in.toString(), version);
	}

	public IndexFormatTooOldException(String resourceDesc, int version, int minVersion, int maxVersion)
	{
		super((new StringBuilder()).append("Format version is not supported (resource: ").append(resourceDesc).append("): ").append(version).append(" (needs to be between ").append(minVersion).append(" and ").append(maxVersion).append("). This version of Lucene only supports indexes created with release 3.0 and later.").toString());
		if (!$assertionsDisabled && resourceDesc == null)
			throw new AssertionError();
		else
			return;
	}

	public IndexFormatTooOldException(DataInput in, int version, int minVersion, int maxVersion)
	{
		this(in.toString(), version, minVersion, maxVersion);
	}

}
