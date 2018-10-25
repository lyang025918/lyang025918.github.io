// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Buffer.java

package IceInternal;

import Ice.MarshalException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Buffer
{

	public ByteBuffer b;
	public ByteBuffer _emptyBuffer;
	private int _size;
	private int _capacity;
	private int _maxCapacity;
	private boolean _direct;
	private int _shrinkCounter;

	public Buffer(int maxCapacity, boolean direct)
	{
		_emptyBuffer = ByteBuffer.allocate(0);
		b = _emptyBuffer;
		_size = 0;
		_capacity = 0;
		_maxCapacity = maxCapacity;
		_direct = direct;
	}

	public int size()
	{
		return _size;
	}

	public boolean empty()
	{
		return _size == 0;
	}

	public void clear()
	{
		b = _emptyBuffer;
		_size = 0;
		_capacity = 0;
	}

	public void expand(int n)
	{
		int sz = b != _emptyBuffer ? b.position() + n : n;
		if (sz > _size)
			resize(sz, false);
	}

	public void resize(int n, boolean reading)
	{
		if (n == 0)
			clear();
		else
		if (n > _capacity)
			reserve(n);
		_size = n;
		if (reading)
			b.limit(_size);
	}

	public void reset()
	{
		if (_size > 0 && _size * 2 < _capacity)
		{
			if (++_shrinkCounter > 2)
			{
				reserve(_size);
				_shrinkCounter = 0;
			}
		} else
		{
			_shrinkCounter = 0;
		}
		_size = 0;
		if (b != _emptyBuffer)
		{
			b.limit(b.capacity());
			b.position(0);
		}
	}

	private void reserve(int n)
	{
		if (n > _capacity)
		{
			_capacity = Math.max(n, Math.min(2 * _capacity, _maxCapacity));
			_capacity = Math.max(240, _capacity);
		} else
		if (n < _capacity)
			_capacity = n;
		else
			return;
		try
		{
			ByteBuffer buf;
			if (_direct)
				buf = ByteBuffer.allocateDirect(_capacity);
			else
				buf = ByteBuffer.allocate(_capacity);
			if (b == _emptyBuffer)
			{
				b = buf;
			} else
			{
				int pos = b.position();
				b.position(0);
				b.limit(Math.min(_capacity, b.capacity()));
				buf.put(b);
				b = buf;
				b.limit(b.capacity());
				b.position(pos);
			}
			b.order(ByteOrder.LITTLE_ENDIAN);
		}
		catch (OutOfMemoryError ex)
		{
			_capacity = b.capacity();
			throw new MarshalException("OutOfMemoryError occurred while allocating a ByteBuffer", ex);
		}
	}
}
