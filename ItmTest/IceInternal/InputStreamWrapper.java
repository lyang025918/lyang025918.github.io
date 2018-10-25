// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputStreamWrapper.java

package IceInternal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

// Referenced classes of package IceInternal:
//			BasicStream, Buffer

public class InputStreamWrapper extends InputStream
{

	private BasicStream _s;
	private int _markPos;

	public InputStreamWrapper(int size, BasicStream s)
	{
		_s = s;
		_markPos = 0;
	}

	public int read()
		throws IOException
	{
		return _s.getBuffer().b.get();
		Exception ex;
		ex;
		throw new IOException(ex.toString());
	}

	public int read(byte b[])
		throws IOException
	{
		return read(b, 0, b.length);
	}

	public int read(byte b[], int offset, int count)
		throws IOException
	{
		try
		{
			_s.getBuffer().b.get(b, offset, count);
		}
		catch (Exception ex)
		{
			throw new IOException(ex.toString());
		}
		return count;
	}

	public int available()
	{
		return _s.getBuffer().b.remaining();
	}

	public void mark(int readlimit)
	{
		_markPos = _s.pos();
	}

	public void reset()
		throws IOException
	{
		_s.pos(_markPos);
	}

	public boolean markSupported()
	{
		return true;
	}

	public void close()
		throws IOException
	{
	}
}
