// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexInput.java

package org.apache.lucene.store;

import java.io.Closeable;
import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			DataInput

public abstract class IndexInput extends DataInput
	implements Cloneable, Closeable
{

	private final String resourceDescription;

	protected IndexInput(String resourceDescription)
	{
		if (resourceDescription == null)
		{
			throw new IllegalArgumentException("resourceDescription must not be null");
		} else
		{
			this.resourceDescription = resourceDescription;
			return;
		}
	}

	public abstract void close()
		throws IOException;

	public abstract long getFilePointer();

	public abstract void seek(long l)
		throws IOException;

	public abstract long length();

	public String toString()
	{
		return resourceDescription;
	}

	public IndexInput clone()
	{
		return (IndexInput)super.clone();
	}

	public volatile DataInput clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
