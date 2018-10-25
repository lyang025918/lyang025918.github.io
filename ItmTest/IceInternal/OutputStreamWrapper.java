// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputStreamWrapper.java

package IceInternal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

// Referenced classes of package IceInternal:
//			BasicStream, Buffer

public class OutputStreamWrapper extends OutputStream
{

	private BasicStream _s;
	private int _spos;
	private byte _bytes[];
	private int _pos;
	static final boolean $assertionsDisabled = !IceInternal/OutputStreamWrapper.desiredAssertionStatus();

	public OutputStreamWrapper(BasicStream s)
	{
		_s = s;
		_spos = s.pos();
		_bytes = new byte[254];
		_pos = 0;
	}

	public void write(int b)
		throws IOException
	{
		try
		{
			if (_bytes == null)
				break MISSING_BLOCK_LABEL_94;
			if (_pos < _bytes.length)
			{
				_bytes[_pos++] = (byte)b;
				return;
			}
		}
		catch (Exception ex)
		{
			throw new IOException(ex.toString());
		}
		_s.writeSize(255);
		if (_pos > 0)
		{
			_s.expand(_pos);
			_s.getBuffer().b.put(_bytes, 0, _pos);
		}
		_bytes = null;
		_s.expand(1);
		_s.getBuffer().b.put((byte)b);
		_pos++;
		break MISSING_BLOCK_LABEL_144;
	}

	public void write(byte b[])
		throws IOException
	{
		write(b, 0, b.length);
	}

	public void write(byte bytes[], int offset, int count)
		throws IOException
	{
		try
		{
			if (_bytes == null)
				break MISSING_BLOCK_LABEL_102;
			if (count <= _bytes.length - _pos)
			{
				System.arraycopy(bytes, offset, _bytes, _pos, count);
				_pos += count;
				return;
			}
		}
		catch (Exception ex)
		{
			throw new IOException(ex.toString());
		}
		_s.writeSize(255);
		if (_pos > 0)
		{
			_s.expand(_pos);
			_s.getBuffer().b.put(_bytes, 0, _pos);
		}
		_bytes = null;
		_s.expand(count);
		_s.getBuffer().b.put(bytes, offset, count);
		_pos += count;
		break MISSING_BLOCK_LABEL_155;
	}

	public void flush()
		throws IOException
	{
	}

	public void close()
		throws IOException
	{
		try
		{
			if (_bytes != null)
			{
				if (!$assertionsDisabled && _pos > _bytes.length)
					throw new AssertionError();
				_s.pos(_spos);
				_s.writeSize(_pos);
				_s.expand(_pos);
				_s.getBuffer().b.put(_bytes, 0, _pos);
				_bytes = null;
			} else
			{
				int currentPos = _s.pos();
				_s.pos(_spos);
				_s.writeSize(_pos);
				_s.pos(currentPos);
			}
		}
		catch (Exception ex)
		{
			throw new IOException(ex.toString());
		}
	}

}
