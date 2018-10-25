// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexOutput.java

package mylib.file.store;

import java.io.Closeable;
import java.io.IOException;

// Referenced classes of package mylib.file.store:
//			DataOutput

public abstract class IndexOutput extends DataOutput
	implements Closeable
{

	public IndexOutput()
	{
	}

	public abstract void flush()
		throws IOException;

	public abstract void close()
		throws IOException;

	public abstract long getFilePointer();

	public abstract void seek(long l)
		throws IOException;

	public abstract long length()
		throws IOException;

	public void setLength(long l)
		throws IOException
	{
	}
}
